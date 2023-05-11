package view;

import controller.ControllerPersona;

import static view.Print.*;

public class Menu {
    /**
     * Menú inicial que pregunta sobre quina taula volem actuar
     */
     public static void initMenu() {

        final String PREGUNTA = "Sobre quina taula vols actuar?";
        final String OP1 = "Persones";
        final String OP2 = "Candidatures";
        final String OP3 = "Comunitats autònomes";
        final String ESCAPE = "Sortir";

        println("Benvingut al programa per gestionar la base de dades de les eleccions!\n");

        do {

            switch (generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3)) {
                case 1 -> menuPersones();
                //TODO: case 2 -> menuCandidatures();
                //TODO: case 3 -> menuComunitatsAutonomes();
                case 0 -> {
                    return;
                }
            }

        } while (generatePreguntaSN("Vols actuar sobre alguna altra taula (S/N)?: "));

        println("Fins la propera!");
    }

    /**
     * Executa el menú CRUD de la taula persones
     */
    private static void menuPersones() {

        do {

            switch (generateMenuCRUD()) {
                case 1 -> ControllerPersona.cercar();
                case 2 -> ControllerPersona.inserir();
                case 3 -> ControllerPersona.modificar();
                case 4 -> ControllerPersona.eliminar();
                case 5 -> ControllerPersona.llistar();
                case 6 -> ControllerPersona.ferRecompte();
                case 0 -> {
                    return;
                }
            }

        } while (generatePreguntaSN("Vols realitzar alguna tasca més sobre la taula persones (S/N)?: "));

    }

    /**
     * Crea el menú estàndard per a les operacions CRUD
     *
     * @return Opció triada per l'usuari
     */
    private static int generateMenuCRUD() {
        final String PREGUNTA = "Quina acció vols realitzar?";
        final String CERCAR = "Cercar";
        final String INSERIR = "Inserir";
        final String MODIFICAR = "Modificar";
        final String ELIMINAR = "Eliminar";
        final String LLISTAR = "Llistar";
        final String FER_RECOMPTE = "Fer recompte";
        final String ESCAPE = "Tornar al menú principal";

        return generateMenu(PREGUNTA, ESCAPE, CERCAR, INSERIR, MODIFICAR, ELIMINAR, LLISTAR, FER_RECOMPTE);
    }

}
