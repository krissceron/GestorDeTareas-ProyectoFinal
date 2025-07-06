package com.ejemplo;

/**
 * Main para ejecucion del sistema
 */
public final class Main {
  /**
  * Constructor privado para prevenir instanciación de clase utilitaria.
  */
  private Main() {
    // Ocultar constructor
  }

  public static void main(final String[] args) {
    final ConsoleApp app = new ConsoleApp();
    app.run();
  }
}