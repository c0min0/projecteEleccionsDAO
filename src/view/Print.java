package view;

import model.Persona;

import java.util.Scanner;

public class Print {
    /**
     * Scanner per llegir dades introduïdes per l'usuari
     */
    static Scanner scan = new Scanner(System.in);

    /**
     * Constant que conté el missatge d'error quan l'usuari introdueix una opció incorrecta
     */
    public static final String OP_INCORRECTE = "Opció incorrecte, torna a provar-ho.\n";

    /**
     * Mètode que imprimeix per pantalla el text passat per paràmetre
     */
    public static void print(String text) {
        System.out.print(text);
    }

    /**
     * Mètode que imprimeix per pantalla el text passat per paràmetre amb salts de línia
     */
    public static void println(String text) {
        System.out.println("\n" + text);
    }

    /**
     * Mètode que imprimeix per pantalla la persona passada per paràmetre amb salts de línia
     */
    public static void println(Persona p) {
        System.out.println("\n" + p);
    }

    /**
     * Demana una pregunta i retorna la resposta
     *
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static String generatePregunta(String pregunta) {
        print("\n" + pregunta);
        return scan.nextLine().trim();
    }

    /**
     * Demana una pregunta booleana i retorna la resposta
     * o repeteix la pregunta fins que la resposta sigui correcta
     *
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static boolean generatePreguntaSN(String pregunta) {
        String opcio;

        do {
            // Passem pregunta i obtenim la resposta
            opcio = generatePregunta(pregunta).trim().toUpperCase();

            // Si és S o N, retornem 1 o 0 respectivament
            if (opcio.equals("S")) return true;
            else if (opcio.equals("N")) return false;

                // Si no és cap de les opcions, tornem a fer el bucle
            else println(OP_INCORRECTE);

        } while (true);
    }

    /**
     * Mètode que genera un menú amb les opcions passades per paràmetre
     * i retorna l'opció triada per l'usuari
     *
     * @param pregunta Pregunta a fer
     * @param opcio    Opcions a mostrar
     * @return Opció triada per l'usuari
     */
    public static int generateMenu(String pregunta, String escape, String... opcio) {

        String opTriada;

        do {

            // Fem la pregunta i mostrem les opcions
            println(pregunta);
            for (int i = 0; i < opcio.length; i++) {
                print((i + 1) + ". " + opcio[i] + "\n");
            }
            print("0. " + escape + "\n");

            // Demanem opcio a l'usuari
            print("\nTria una opció: ");

            opTriada = scan.nextLine().trim();

            // Retornem l'opció triada
            for (int i = 1; i <= opcio.length; i++) {
                if (opTriada.equals(String.valueOf(i))) return i;
            }

            if (opTriada.equals("0")) return 0;

            // Si no és cap de les opcions, tornem a fer el bucle
            println(OP_INCORRECTE);

        } while (true);
    }
}
