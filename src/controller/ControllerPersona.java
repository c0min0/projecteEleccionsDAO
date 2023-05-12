package controller;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;
import view.Print;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static view.Print.*;

public class ControllerPersona extends Controller {
    // Missatges d'error pels camps de Persona mal introduïts.
    private static final String ERR_MSG_NOM = "El nom ha de tenir menys de 30 caràcters.";
    private static final String ERR_MSG_COG1 = "El primer cognom ha de tenir menys de 30 caràcters.";
    private static final String ERR_MSG_COG2 = "El segon cognom ha de tenir menys de 30 caràcters.";
    private static final String ERR_MSG_DNI = "El dni ha de tenir 8 caràcters en aquesta base de dades";
    private static final String ERR_MSG_SEXE = "El sexe ha de ser 'M' o 'F'.";
    private static final String ERR_MSG_DATA_NAIX = "La data ha de tenir el format 'dd/mm/aaaa'";

    /**
     * Valida un nom de persona segons la seva longitud.
     * @param nom Nom a validar.
     * @return True si el nom té menys de 30 caràcters, false si no.
     */
    private static boolean isNom (String nom) {
        return isVarchar30(nom);
    }

    /**
     * Valida un cognom de persona segons la seva longitud.
     * @param cog1 Cognom a validar.
     * @return True si el cognom té menys de 30 caràcters, false si no.
     */
    private static boolean isCog1 (String cog1) {
        return isVarchar30(cog1);
    }

    /**
     * Valida un segon cognom de persona segons la seva longitud.
     * @param cog2 Segon cognom a validar.
     * @return True si el segon cognom té menys de 30 caràcters, false si no.
     */
    private static boolean isCog2 (String cog2) {
        return isVarchar30(cog2);
    }

    /**
     * Valida un sexe comprovant que sigui M o F.
     * @param sexe Sexe a validar.
     * @return True si el sexe és M o F, false si no.
     */
    private static boolean isSexe(String sexe) {
        return sexe.equals("M") || sexe.equals("F") || sexe.equals("m") || sexe.equals("f");
    }

    /**
     * Valida un DNI comprovant que tingui 8 caràcters.
     * @param dni DNI a validar.
     * @return True si el DNI té 8 caràcters, false si no.
     */
    private static boolean isDNI(String dni) {
        return dni.length() == 8;
    }

    /**
     * Demana el camp passat per paràmetre i retorna la resposta de l'usuari
     * si aquesta és vàlida, si no, torna a demanar una resposta vàlida fins que ho sigui.
     * @param camp Camp que es demana a l'usuari
     * @return Resposta vàlida de l'usuari
     */
    private static String demanarCampValid(String camp, String accio) {
        String resposta = null, errorMsg = null;
        boolean condicio = false;
        do {
            switch (camp) {
                case "nom" -> {
                    resposta = generatePregunta("Introdueix el nom de la persona que vols " + accio +": ").trim();
                    condicio = isNom(resposta);
                    errorMsg = ERR_MSG_NOM;
                }
                case "cog1" -> {
                    resposta = generatePregunta("Introdueix el primer cognom de la persona que vols " + accio +": ").trim();
                    condicio = isCog1(resposta);
                    errorMsg = ERR_MSG_COG1;
                }
                case "cog2" -> {
                    resposta = generatePregunta("Introdueix el segon cognom de la persona que vols " + accio +": ").trim();
                    condicio = isCog2(resposta);
                    errorMsg = ERR_MSG_COG2;
                }
                case "sexe" -> {
                    resposta = generatePregunta("Introdueix el sexe de la persona que vols " + accio + " (M/F): ").trim();
                    condicio = isSexe(resposta);
                    errorMsg = ERR_MSG_SEXE;
                }
                case "data_naixement" -> {
                    resposta = generatePregunta("Introdueix la data de naixement de la persona que vols " + accio + " (dd/mm/aaaa): ").trim();
                    condicio = isDate(resposta);
                    errorMsg = ERR_MSG_DATA_NAIX;
                }
                case "dni" -> {
                    resposta = generatePregunta("Introdueix el dni de la persona que vols " + accio + ": ").trim();
                    condicio = isDNI(resposta);
                    errorMsg = ERR_MSG_DNI;
                }
            }

            if (!condicio) println(errorMsg);

        } while (!condicio);

        return resposta;
    }

    /**
     * Executa el procés de cerca de la taula persones.
     * @return Llista de persones trobades amb la cerca.
     */
    public static List<Persona> cercar() {
        List<Persona> resultat = null;

        final String  PREGUNTA = "Sobre quin camp vols cercar la o les persona/es?";
        final String OP1 = "persona_id INT UNSIGNED";
        final String OP2 = "nom VARCHAR(30)";
        final String OP3 = "cog1 VARCHAR(30)";
        final String OP4 = "cog2 VARCHAR(30)";
        final String OP5 = "sexe ENUM('M','F')";
        final String OP6 = "data_naixement DATE";
        final String OP7 = "dni VARCHAR(8)";
        final String ESCAPE = "Torna enrrere";


        String accio = "cercar", camp = null, resposta = null;

        do {
            switch (Print.generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7)) {
                case 1 -> {
                    camp = "persona_id";
                    resposta = demanarCampValid(camp, accio);
                }
                case 2 -> {
                    camp = "nom";
                    resposta = demanarCampValid(camp, accio);
                }
                case 3 -> {
                    camp = "cog1";
                    resposta = demanarCampValid(camp, accio);
                }
                case 4 -> {
                    camp = "cog2";
                    resposta = demanarCampValid(camp, accio);
                }
                case 5 -> {
                    camp = "sexe";
                    resposta = demanarCampValid(camp, accio);
                }
                case 6 -> {
                    camp = "data_naixement";
                    resposta = demanarCampValid(camp, accio);
                }
                case 7 -> {
                    camp = "dni";
                    resposta = demanarCampValid(camp, accio);
                }
                case 0 -> {
                    return resultat;
                }
            }

            resultat = new PersonaDAO().search(camp, resposta);
            if (resultat.size() > 0) {
                for (Persona p : resultat) {
                    Print.println(">> " + p);
                }
                Print.println("S'han trobat " + resultat.size() + " persones amb aquest " + camp + ".");
            } else Print.println("No s'ha trobat cap persona amb aquest " + camp + ".");

        } while (Print.generatePreguntaSN("Vols trobar unes altres persones? (S/N): "));

        return resultat;
    }

    /**
     * Executa el procés d'inserció de persones
     */
    public static void inserir() {
        String accio = "inserir";
        boolean dadesCorrectes = false;
        String nom, cog1, cog2, dni, sexe;
        Date data_naixement;

        // Demanem a l'usuari les dades de la persona
        do {
            if (!dadesCorrectes) println("Tornem-les a introduïr!");

            nom = demanarCampValid("nom", accio);
            cog1 = demanarCampValid("cog1", accio);
            cog2 = demanarCampValid("cog2", accio);
            dni = demanarCampValid("dni", accio);
            sexe = demanarCampValid("sexe", accio);
            data_naixement = convertToDate(demanarCampValid("data_naixement", accio));

            dadesCorrectes = generatePreguntaSN("Són correctes les dades introduïdes? (S/N): ");

        } while (!dadesCorrectes);

        if (generatePreguntaSN("Estàs segur que vols inserir aquesta persona? (S/N): ")) {
            // Construïm l'objecte persona amb les dades introduïdes
            Persona p = new Persona(nom, cog1, cog2, sexe, data_naixement, dni);

            // Inserim la persona a la base de dades
            if (new PersonaDAO().create(p)) Print.println("Persona afegida amb èxit!");
            else Print.println("\nNo s'ha pogut afegir la persona.");
        }
    }

    /**
     * Executa el procés de modificació de persones
     */
    public static void modificar() {
        // Cerquem persones a modificar
        List<Persona> resultatCerca = cercar();

        // Comprovem si el resultat està buit
        if (resultatCerca == null || resultatCerca.size() == 0) {
            Print.println("Sense resultat de cerca no es pot actualitzar cap persona.");
            return;
        }

        // Demanem quins camps es volen modificar
        HashMap <String,String> campsModificats = modificarCamps();
        if (campsModificats == null) return;

        // Modifiquem els camps dels objectes Persona
        for (Persona p : resultatCerca) {
            for (String camp : campsModificats.keySet()) {
                switch (camp) {
                    case "nom" -> p.setNom(campsModificats.get(camp));
                    case "cog1" -> p.setCog1(campsModificats.get(camp));
                    case "cog2" -> p.setCog2(campsModificats.get(camp));
                    case "sexe" -> p.setSexe(campsModificats.get(camp));
                    case "data_naixement" -> p.setDataNaixement(convertToDate(campsModificats.get(camp)));
                    case "dni" -> p.setDni(campsModificats.get(camp));
                }
            }
        }

        // Actualitzem persones a la BD
        boolean updCorrecte = true;

        String[] campsAfegtats = campsModificats.keySet().toArray(new String[0]);

        for (Persona p : resultatCerca) {
            if (!new PersonaDAO().update(p, campsAfegtats)) {
                updCorrecte = false;
                println("No s'ha pogut actualitzar la persona amb id " + p.getId() + ".");
            }
        }

        if (updCorrecte) println("\nS'han actualitzat correctament les persones.");
    }

    /**
     * Executa el menú de modificació de camps de la taula persones
     * @return HashMap amb els camps a modificar i els seus valors
     */
    private static HashMap<String, String> modificarCamps() {

        HashMap<String, String> camps = new HashMap<>();

        final String PREGUNTA = "Quin camp vols modificar?";
        final String OP1 = "nom VARCHAR(30)";
        final String OP2 = "cog1 VARCHAR(30)";
        final String OP3 = "cog2 VARCHAR(30)";
        final String OP4 = "sexe ENUM('M','F')";
        final String OP5 = "data_naixement DATE";
        final String OP6 = "dni CHAR(8)";
        final String ESCAPE = "Surt";

        String accio = "modificar", camp = null, resposta = null;

        do {
            switch (Print.generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6)) {
                case 1 -> {
                    camp = "nom";
                    resposta = demanarCampValid(camp, accio);
                }
                case 2 -> {
                    camp = "cog1";
                    resposta = demanarCampValid(camp, accio);
                }
                case 3 -> {
                    camp = "cog2";
                    resposta = demanarCampValid(camp, accio);
                }
                case 4 -> {
                    camp = "sexe";
                    resposta = demanarCampValid(camp, accio);
                }
                case 5 -> {
                    camp = "data_naixement";
                    resposta = demanarCampValid(camp, accio);
                }
                case 6 -> {
                    camp = "dni";
                    resposta = demanarCampValid(camp, accio);
                }
            }

            camps.put(camp, resposta);

        } while (Print.generatePreguntaSN("Vols modificar algun altre camp (S/N)?: "));

        /*do {
            do {
                switch (Print.generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6)) {
                    case 1 -> {
                        camp = "nom";
                        resposta = generatePregunta("Introdueix el nou nom: ");
                        condicio = isNom(resposta);
                        errorMsg = ERR_MSG_NOM;

                    }
                    case 2 -> {
                        camp = "cog1";
                        resposta = generatePregunta("Introdueix el nou primer cognom: ");
                        condicio = isCog1(resposta);
                        errorMsg = ERR_MSG_COG1;
                    }
                    case 3 -> {
                        camp = "cog2";
                        resposta = generatePregunta("Introdueix el nou segon cognom: ");
                        condicio = isCog2(resposta);
                        errorMsg = ERR_MSG_COG2;
                    }
                    case 4 -> {
                        camp = "sexe";
                        resposta = generatePregunta("Introdueix el nou sexe (M/F): ");
                        condicio = isDate(resposta);
                        errorMsg = ERR_MSG_SEXE;
                    }
                    case 5 -> {
                        camp = "data_naixement";
                        resposta = generatePregunta("Introdueix la nova data de naixement (dd/mm/aaaa): ");
                        condicio = isDate(resposta);
                        errorMsg = ERR_MSG_DATA_NAIX;
                    }
                    case 6 -> {
                        camp = "dni";
                        resposta = generatePregunta("Introdueix el nou dni: ");
                        condicio = isDNI(resposta);
                        errorMsg = ERR_MSG_DNI;
                    }
                }

                if (condicio) camps.put(camp, resposta);
                else Print.println(errorMsg);

            } while (!condicio);

        } while (Print.generatePreguntaSN("Vols modificar algun altre camp (S/N)?: "));*/

        return camps;
    }

    /**
     *
     */
    public static void eliminar() {
        // Cerquem persones a eliminar
        List<Persona> resultatCerca = cercar();

        // Comprovem si el codi està buit
        if (resultatCerca == null || resultatCerca.size() == 0) {
            Print.println("Sense resultat de cera no es pot eliminar.");
            return;
        }

        if (generatePreguntaSN("Estàs segur que vols eliminar les persones trobades (S/N)?: ")) {
            // Eliminem persones de la BD
            boolean delCorrecte = true;
            for (Persona p : resultatCerca) {
                if (!new PersonaDAO().delete(p)) {
                    delCorrecte = false;
                    Print.println("No s'ha pogut eliminar la persona amb id " + p.getId() + ".");
                }
            }
            // Mostrem missatge d'èxit
            if (delCorrecte) Print.println("S'han eliminat correctament les persones.");
        }

    }

    /**
     * Executa el procés per llistar els registres de la taula persones
     */
    public static void llistar() {
        List<Persona> resultat = new PersonaDAO().all();
        if (resultat.size() > 0) {
            for (Persona p : resultat) {
                Print.println(">> " + p);
            }
            Print.println("S'han trobat " + resultat.size() + " persones.");
        } else Print.println("No s'ha trobat cap persona.");
    }


    /**
     * Executa el procés per mostrar el recompte de la taula persones
     */
    public static void ferRecompte() {
        long recompte = new PersonaDAO().count();
        Print.println("Hi ha " + recompte + " persones a la base de dades.");
    }
}
