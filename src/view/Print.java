package view;

import model.Persona;

/**
 * Classe que conté els mètodes per imprimir per pantalla
 */
public class Print {

    /**
     * Mètode que imprimeix per pantalla el text passat per paràmetre
     */
    public static void print(String text) {
        System.out.print("\n" + text);
    }

    /**
     * Mètode que imprimeix per pantalla el text passat per paràmetre amb salts de línia
     */
    public static void println(String text) {
        System.out.println("\n" + text);
    }
}
