
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

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.Optional;

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

  public Optional<File> getContext() {
    return Optional.ofNullable(context);
  }

  public File getOutput() {
    return output;
  }
}
