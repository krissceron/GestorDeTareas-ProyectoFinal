package com.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Main para generar reportes
 */
public final class ReportesMain {
  /**
   * Constructor privado para prevenir instanciación de clase utilitaria.
   */
  private ReportesMain() {
      // Ocultar constructor
  }

  public static void main(final String[] args) {
    try (PrintWriter w = new PrintWriter("output/index.html", "UTF-8")) {
      w.println("<h1>Hello from Java CI/CD!</h1>");
      w.println("<p><a href=\"checkstyle.html\">Ver reporte Checkstyle</a></p>");
      w.println("<p><a href=\"pmd.html\">Ver reporte PMD</a></p>");
      w.println("<p><a href=\"jacoco/index.html\">Ver reporte JaCoCo</a></p>");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
