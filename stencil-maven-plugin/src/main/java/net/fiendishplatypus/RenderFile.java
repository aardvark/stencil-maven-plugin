package net.fiendishplatypus;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

public class RenderFile {
  @Parameter(property = "stencil.template")
  private File template;

  @Parameter(property = "stencil.context")
  private File context;

  @Parameter(property = "stencil.output")
  private File output;

  public File getTemplate() {
    return template;
  }

  public File getContext() {
    return context;
  }

  public File getOutput() {
    return output;
  }
}
