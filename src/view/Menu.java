package view;

import controller.PersonaController;

import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    static Scanner scan = new Scanner(System.in);

    /**
     * Constant que conté el missatge d'error quan l'usuari introdueix una opció incorrecta
     */
    static final String OP_INCORRECTE = "Opció incorrecte, torna a provar-ho.\n";


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
            System.out.println("\n" + pregunta);
            for (int i = 0; i < opcio.length; i++) {
                System.out.println((i + 1) + ". " + opcio[i]);
            }
            System.out.println("0. " + escape);

            // Demanem opcio a l'usuari
            System.out.print("\nTria una opció: ");

            opTriada = scan.nextLine().trim();

            // Retornem l'opció triada
            for (int i = 0; i < opcio.length; i++) {
                if (opTriada.equals(String.valueOf(i))) return i;
            }

            // Si no és cap de les opcions, tornem a fer el bucle
            System.out.println(OP_INCORRECTE);

        } while (true);
    }

    /**
     * Demana una pregunta i retorna la resposta
     *
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static String generatePregunta(String pregunta) {
        System.out.print("\n" + pregunta);
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
            opcio = generatePregunta(pregunta);

            // Si és S o N, retornem 1 o 0 respectivament
            if (opcio.equals("S")) return true;
            else if (opcio.equals("N")) return false;

                // Si no és cap de les opcions, tornem a fer el bucle
            else System.out.println(OP_INCORRECTE);

        } while (true);
    }

    /**
     * Menú inicial que pregunta sobre quina taula volem actuar
     */
    static void initMenu() {

        final String PREGUNTA = "Sobre quina taula vols actuar?";
        final String OP1 = "Persones";
        final String OP2 = "Candidatures";
        final String OP3 = "Comunitats autònomes";
        final String ESCAPE = "Sortir";

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

        System.out.println("Adeu!");
    }

    /**
     * Crea el menú estàndard per a les operacions CRUD
     *
     * @return Opció triada per l'usuari
     */
    static int generateMenuCRUD() {
        final String PREGUNTA = "Quina acció vols realitzar?";
        final String INSERIR = "Inserir";
        final String MODIFICAR = "Modificar";
        final String ELIMINAR = "Eliminar";
        final String LLISTAR = "Llistar";
        final String CERCAR = "Cercar";
        final String FER_RECOMPTE = "Fer recompte";
        final String ESCAPE = "Tornar al menú principal";

        return generateMenu(PREGUNTA, ESCAPE, INSERIR, MODIFICAR, ELIMINAR, LLISTAR, CERCAR, FER_RECOMPTE);
    }

    /**
     * Executa el menú CRUD de la taula persones
     */
    private static void menuPersones() {

        do {

            switch (generateMenuCRUD()) {
                case 1 -> PersonaController.insertPersona();
                case 2 -> PersonaController.updPersona();
                //TODO: case 3 -> delPersonaMenu();
                //TODO: case 4 -> llistarPersonesMenu();
                case 5 -> PersonaController.searchPersona();
                //TODO: case 6 -> recomptePersonesMenu();
                case 0 -> {
                    return;
                }
            }

        } while (generatePreguntaSN("Vols realitzar alguna tasca més sobre la taula persones (S/N)?: "));

        System.out.println("Adeu!");

    }

    /**
     * Executa el menú de modificació de camps de la taula persones
     *
     * @return HashMap amb els camps a modificar i els seus valors
     */
    public static HashMap<String, String> menuPersonesUpdCampsAModificar() {
        HashMap<String, String> camps = new HashMap<>();

        final String PREGUNTA = "Quin camp vols modificar?";
        final String OP1 = "nom VARCHAR(30)";
        final String OP2 = "cog1 VARCHAR(30)";
        final String OP3 = "cog2 VARCHAR(30)";
        final String OP4 = "sexe ENUM('M','F')";
        final String OP5 = "data_naixement DATE";
        final String OP6 = "dni CHAR(8)";
        final String OP7 = "Tots els camps";
        final String ESCAPE = "Surt";

        do {

            switch (generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7)) {
                case 1 -> {
                    camps.put("nom", generatePregunta("Introdueix el nou nom: "));
                }
                case 2 -> {
                    camps.put("cog1", generatePregunta("Introdueix el nou cognom1: "));
                }
                case 3 -> {
                    camps.put("cog2", generatePregunta("Introdueix el nou cognom2: "));
                }
                case 4 -> {
                    String resposta = generatePregunta("Introdueix el nou sexe (M/F): ");
                    boolean sexeIncorrecte;
                    do {
                        sexeIncorrecte = false;

                        if (PersonaController.isSexe(resposta)) camps.put("sexe", resposta);

                        else {
                            sexeIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (sexeIncorrecte);
                }
                case 5 -> {
                    String resposta = generatePregunta("Introdueix la nova data de naixement (dd/mm/aaaa): ");
                    boolean dataIncorrecte;
                    do {
                        dataIncorrecte = false;

                        if (PersonaController.isDate(resposta)) camps.put("data_naixement", resposta);

                        else {
                            dataIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (dataIncorrecte);
                }
                case 6 -> {
                    String resposta = generatePregunta("Introdueix el nou DNI (8 caràcters): ");
                    boolean dniIncorrecte;
                    do {
                        dniIncorrecte = false;

                        if (PersonaController.isDNI(resposta)) camps.put("dni", resposta);

                        else {
                            dniIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (dniIncorrecte);
                }
                case 7 -> {
                    camps.put("nom", generatePregunta("Introdueix el nou nom: "));
                    camps.put("cog1", generatePregunta("Introdueix el nou cognom1: "));
                    camps.put("cog2", generatePregunta("Introdueix el nou cognom2: "));

                    String respSexe = generatePregunta("Introdueix el nou sexe (M/F): ");
                    boolean sexeIncorrecte;
                    do {
                        sexeIncorrecte = false;

                        if (PersonaController.isSexe(respSexe)) camps.put("sexe", respSexe);

                        else {
                            sexeIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (sexeIncorrecte);

                    String respDataNaix = generatePregunta("Introdueix la nova data de naixement (dd/mm/aaaa): ");
                    boolean dataIncorrecte;
                    do {
                        dataIncorrecte = false;

                        if (PersonaController.isDate(respDataNaix)) camps.put("data_naixement", respDataNaix);

                        else {
                            dataIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (dataIncorrecte);

                    String respDNI = generatePregunta("Introdueix el nou DNI (8 caràcters): ");
                    boolean dniIncorrecte;
                    do {
                        dniIncorrecte = false;

                        if (PersonaController.isDNI(respDNI)) camps.put("dni", respDNI);

                        else {
                            dniIncorrecte = true;
                            System.out.println(OP_INCORRECTE);
                        }

                    } while (dniIncorrecte);
                }
                case 0 -> {
                    return null;
                }
            }

        } while (generatePreguntaSN("\nVols actualitzar algún camp més sobre aquest registre? (S/N): "));

        return camps;
    }
}
