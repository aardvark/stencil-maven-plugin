package net.fiendishplatypus;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StencilRenderTest {
  @Test
  public void test_stencil_template_call() {
    IFn require = Clojure.var("clojure.core", "require");
    require.invoke(Clojure.read("stencil.core"));

    IFn renderString = Clojure.var("stencil.core", "render-string");
    String templateRes = (String) renderString.invoke("{{ a }}", Clojure.read("{:a \"test\"}"));

    assertEquals("test", templateRes);
  }
}