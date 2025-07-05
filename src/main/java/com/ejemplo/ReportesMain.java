package com.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;

public final class ReportesMain {
  private ReportesMain() {
    // Ocultar constructor
  }

  public static void main(final String[] args) {
    try (PrintWriter w = new PrintWriter("output/index.html", "UTF-8")) {
      w.println("<h1>Hello from Java CI/CD!</h1>");
      w.println("<p><a href=\"site/checkstyle.html\">Ver reporte Checkstyle</a></p>");
      w.println("<p><a href=\"site/pmd.html\">Ver reporte PMD</a></p>");
      w.println("<p><a href=\"site/jacoco/index.html\">Ver reporte JaCoCo</a></p>");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
