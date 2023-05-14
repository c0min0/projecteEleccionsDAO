package controller;

import controller.candidatura.ControllerCandidatura;
import controller.persona.ControllerPersona;
import view.Print;

import java.util.Scanner;

import static controller.Missatges.MSG_INIT;
import static controller.Missatges.MSG_REPEAT_TAULA;
import static view.Print.*;
import static view.Print.println;

/**
 * Classe que conté els mètodes de control auxiliars comuns i els menús genèrics de l'aplicació
 */
public class Controller {
    static Scanner scan = new Scanner(System.in);

    // MÈTODES AUXILIARS

    /**
     * Demana una pregunta i retorna l'String de la resposta
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static String obtenirResposta(String pregunta) {
        print(pregunta);
        return scan.nextLine().trim();
    }

    /**
     * Demana una pregunta booleana i retorna la resposta
     * o repeteix la pregunta fins que la resposta sigui correcta.
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static boolean obtenirRespostaSN(String pregunta) {
        String opcio;
        do {
            // Passem pregunta i obtenim la resposta
            opcio = obtenirResposta(pregunta).trim().toUpperCase();

            // Si és S o N, retornem TRUE o FALSE respectivament
            if (opcio.equals("S")) return true;
            else if (opcio.equals("N")) return false;

            // Si no és cap de les opcions, tornem a fer el bucle
            else println(Missatges.MSG_ERR_OP);

        } while (true);
    }

    /**
     * Mètode que genera un menú amb les opcions passades per paràmetre
     * i retorna l'opció triada per l'usuari.
     * @param pregunta Pregunta a fer
     * @param escape Opció per sortir del menú
     * @param opcio Opcions a mostrar
     * @return Opció triada per l'usuari
     */
    public static int obtenirOpMenu (String pregunta, String escape, String... opcio) {

        String opTriada;

        do {

            // Fem la pregunta
            println(pregunta);

            // Mostrem les opcions
            for (int i = 0; i < opcio.length; i++) {
                print((i + 1) + ". " + opcio[i]);
            }
            print("0. " + escape);

            // Demanem opcio a l'usuari
            print("\nTria una opció: ");

            opTriada = scan.nextLine().trim();

            // Retornem l'opció triada
            for (int i = 1; i <= opcio.length; i++) {
                if (opTriada.equals(String.valueOf(i))) return i;
            }

            if (opTriada.equals("0")) return 0;

            // Si no és cap de les opcions, tornem a fer el bucle
            Print.println(Missatges.MSG_ERR_OP);

        } while (true);
    }

    // MENÚS

    /**
     * Demana a l'usuari sobre quina taula vol actuar
     * @return Opció triada per l'usuari
     */
    private static int obtenirOpMenuInicial() {
        final String PREGUNTA = "Sobre quina taula vols actuar?";
        final String OP1 = "Persones";
        final String OP2 = "Candidatures";
        final String OP3 = "Comunitats autònomes";
        final String ESCAPE = "Sortir";

        return obtenirOpMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3);
    }

    /**
     * Executa el menú inicial que pregunta sobre quina taula volem actuar
     */
    static void menuInicial() {

        println(MSG_INIT);

        do {

            switch (obtenirOpMenuInicial()) {
                case 1 -> ControllerPersona.menuCRUD();
                case 2 -> ControllerCandidatura.menuCRUD();
                //TODO: case 3 -> menuCRUDComunitatsAutonomes();
                case 0 -> {
                    println("Fins la propera!");
                    return;
                }
            }

        } while (obtenirRespostaSN(MSG_REPEAT_TAULA));

        println("Fins la propera!");
    }

    /**
     * Crea el menú estàndard per a les operacions CRUD
     * @return Opció triada per l'usuari
     */
    protected static int obtenirOpMenuCRUD() {
        final String PREGUNTA = "Quina acció vols realitzar?";
        final String CERCAR = "Cercar";
        final String INSERIR = "Inserir";
        final String MODIFICAR = "Modificar";
        final String ELIMINAR = "Eliminar";
        final String LLISTAR = "Llistar";
        final String FER_RECOMPTE = "Fer recompte";
        final String ESCAPE = "Torna enrere";

        return obtenirOpMenu(PREGUNTA, ESCAPE, CERCAR, INSERIR, MODIFICAR, ELIMINAR, LLISTAR, FER_RECOMPTE);
    }
}
