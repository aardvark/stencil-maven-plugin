/*
 * Copyright (c) 2019 - 2020 Evgeniy A. Latuhin [and others]
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 */

package net.fiendishplatypus.stencil.plugin;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import net.fiendishplatypus.stencil.plugin.RenderFile;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Mojo(name = "renderFile")
public class RenderTemplateMojo extends AbstractMojo {

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  @Parameter(property = "renderFiles")
  private List<RenderFile> renderFiles;

  public void execute() throws MojoExecutionException {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("stencil.core"));
    require.invoke(Clojure.read("stencil.loader"));

    IFn registerTemplate = Clojure.var("stencil.loader", "register-template");
    IFn render = Clojure.var("stencil.core", "render-file");

    for (RenderFile renderFile : renderFiles) {
      File template = renderFile.getTemplate();
      Optional<File> context = renderFile.getContext();
      File output = renderFile.getOutput();
      info("Using template file: " + template.getPath());

      registerTemplate.invoke(template.getPath(), loadTemplateToString(template));

      info("Using context file: " + context.map(File::getPath).orElse("none"));
      info("Using output file: " + output.getPath());

      Properties properties = project.getProperties();
      properties.put("project.version", project.getVersion());

      String propString = propertiesMap(properties);

      String contextString;
      if (context.isPresent()) {
        contextString = loadTemplateContextToString(context.get());
      } else {
        contextString = "{}";
      }

      debug("Template context: " + contextString);
      Object contextMap = Clojure.read(contextString);

      debug("Maven properties: " + propString);
      Object propertyMap = Clojure.read(propString);

      Object mergedMap = Clojure.var("clojure.core", "merge").invoke(propertyMap, contextMap);
      debug("Merged[properties + context]: " + mergedMap.toString());

      String res = (String) render.invoke(template.getPath(), mergedMap);

      try {
        Files.writeString(output.toPath(), res, StandardOpenOption.CREATE);
      } catch (IOException e) {
        throw new MojoExecutionException("Can't create output file due to exception.", e);
      }
    }
  }

  private String loadTemplateToString(File templateFile) throws MojoExecutionException {
    String template = "";
    try {
      template = Files.readString(templateFile.toPath());
    } catch (NoSuchFileException e) {
      info("Template file declared [" + templateFile.getPath() + "], but not found. Output will be empty file.");
    } catch (IOException e) {
      throw new MojoExecutionException("Can't render template file due to exception.", e);
    }
    return template;
  }

  private String loadTemplateContextToString(File ctx) throws MojoExecutionException {
    if (ctx == null) {
      return "{}";
    }

    String contextString;
    try {
      contextString = Files.readString(ctx.toPath());
    } catch (NoSuchFileException e) {
      info("Context file declared [" + ctx.getPath() + "], but not found. Will use empty context for a template.");
      contextString = "{}";
    } catch (Exception e) {
      throw new MojoExecutionException("Can't load context file due to exception.", e);
    }
    return contextString;
  }

  private String propertiesMap(Properties projectProperties) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (Map.Entry<Object, Object> entry : projectProperties.entrySet()) {
      Object k = entry.getKey();
      Object v = entry.getValue();
      String escapedKey = ((String) k).replaceAll("\\.", "_");
      String value = (String) v;
      String kv = "\"" + escapedKey + "\"" + " \"" + value + "\" ";
      sb.append(kv);
    }
    sb.append("}");

    return sb.toString();
  }

  private void info(String s) {
    getLog().info(s);
  }

  private void debug(String s) {
    getLog().debug(s);
  }

}
