package controller;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;
import view.Print;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

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
        return sexe.equals("M") || sexe.equals("F");
    }

    /**
     * Valida un DNI comprovant que tingui 8 caràcters.
     * @param dni DNI a validar.
     * @return True si el DNI té 8 caràcters, false si no.
     */
    private static boolean isDNI(String dni) {
        return dni.length() == 8;
    }

    //TODO: provar
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


        String camp = null, resposta = null, errorMsg = null;
        boolean condicio = false;

        do {
            do {
                switch (Print.generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7)) {
                    case 1 -> {
                        camp = "persona_id";
                        resposta = Print.generatePregunta("Introdueix l'id de la persona que vols cercar: ");
                        condicio = isLong(resposta);
                        errorMsg = ERR_MSG_ID;
                    }
                    case 2 -> {
                        camp = "nom";
                        resposta = Print.generatePregunta("Introdueix el nom de la persona que vols cercar: ");
                        condicio = isNom(resposta);
                        errorMsg = ERR_MSG_NOM;
                    }
                    case 3 -> {
                        camp = "cog1";
                        resposta = Print.generatePregunta("Introdueix el primer cognom de la persona que vols cercar: ");
                        condicio = isCog1(resposta);
                        errorMsg = ERR_MSG_COG1;
                    }
                    case 4 -> {
                        camp = "cog2";
                        resposta = Print.generatePregunta("Introdueix el segon cognom de la persona que vols cercar: ");
                        condicio = isCog2(resposta);
                        errorMsg = ERR_MSG_COG2;
                    }
                    case 5 -> {
                        camp = "sexe";
                        resposta = Print.generatePregunta("Introdueix el sexe de la persona que vols cercar (M/F): ");
                        condicio = isSexe(resposta);
                        errorMsg = ERR_MSG_SEXE;
                    }
                    case 6 -> {
                        camp = "data_naixement";
                        resposta = Print.generatePregunta("Introdueix la data de naixement de la persona que vols cercar (dd/mm/aaaa): ");
                        condicio = isDate(resposta);
                        errorMsg = ERR_MSG_DATA_NAIX;
                    }
                    case 7 -> {
                        camp = "dni";
                        resposta = Print.generatePregunta("Introdueix el dni de la persona que vols cercar: ");
                        condicio = isDNI(resposta);
                        errorMsg = ERR_MSG_DNI;
                    }
                    case 0 -> {
                        return resultat;
                    }
                }

                if (condicio) {
                    resultat = new PersonaDAO().search(camp, resposta);
                    if (resultat.size() > 0) {
                        Print.println("S'han trobat " + resultat.size() + " persones amb aquest " + camp + ":");
                        for (Persona p : resultat) {
                            Print.println(p);
                        }
                    } else Print.println("No s'ha trobat cap persona amb aquest " + camp + ".");
                }
                else Print.println(errorMsg);

            } while (!condicio);

        } while (Print.generatePreguntaSN("Vols trobar unes altres persones? (S/N): "));

        return resultat;
    }

    /**
     * Executa el procés d'inserció de persones
     */
    public static void inserir() {
        // Demanem a l'usuari les dades de la persona
        String nom = Print.generatePregunta("Quin és el nom de la persona?: ");
        String cog1 = Print.generatePregunta("Quin és el primer cognom de la persona?: ");
        String cog2 = Print.generatePregunta("Quin és el segon cognom de la persona?: ");
        String dni = demanarDniValid();
        String sexe = demanarSexeValid();
        Date data_naixement = demanarDataNaixValida();

        // Construïm l'objecte persona amb les dades introduïdes
        Persona p = new Persona(nom, cog1, cog2, sexe, data_naixement, dni);

        // Inserim la persona a la base de dades
        if (new PersonaDAO().create(p)) Print.println("Persona afegida amb èxit!");
        else Print.println("\nNo s'ha pogut afegir la persona.");
    }

    /**
     * Demana un sexe i el valida.
     * @return Sexe validat.
     */
    private static String demanarSexeValid() {
        String sexe;

        do {
            sexe = Print.generatePregunta("Quin és el sexe de la persona? (M/F): ").trim().toUpperCase();
            if (!isSexe(sexe)) Print.println("Sexe incorrecte, torna a provar-ho.");
        } while (!isSexe(sexe));

        return sexe;
    }

    /**
     * Demana un DNI i el valida.
     * @return DNI validat.
     */
    private static String demanarDniValid() {
        String dni;

        do {
            dni = Print.generatePregunta("Quin és el DNI de la persona? (Introdueix una referència de 8 caràcters): ");
            if (!isDNI(dni)) Print.println("DNI incorrecte, torna a provar-ho.");
        } while (!isDNI(dni));

        return dni;
    }

    /**
     * Demana una data de naixement i la valida.
     * @return Data de naixement validada.
     */
    private static Date demanarDataNaixValida() {
        String dataNaix;

        do {
            dataNaix = Print.generatePregunta("Quina és la data de naixement de la persona? (dd/mm/aaaa): ");
            if (!isDate(dataNaix)) Print.println("Data incorrecte, torna a provar-ho.");
        } while (!isDate(dataNaix));

        return convertirData(dataNaix);
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
                    case "data_naixement" -> p.setDataNaixement(convertirData(campsModificats.get(camp)));
                    case "dni" -> p.setDni(campsModificats.get(camp));
                }
            }
        }

        // Actualitzem persones a la BD
        boolean updCorrecte = true;

        String[] campsAModificar = campsModificats.keySet().toArray(new String[0]);

        for (Persona p : resultatCerca) {
            if (!new PersonaDAO().update(p, campsAModificar)) {
                updCorrecte = false;
                Print.println("No s'ha pogut actualitzar la persona amb id " + p.getId() + ".");
            }
        }

        if (updCorrecte) Print.println("\nS'han actualitzat correctament les persones.");
    }

    //TODO: provar amb valors incorrectes (fallava el bucle)
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

        String camp = null, resposta = null, errorMsg = null;
        boolean condicio = false;

        do {
            do {
                switch (Print.generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6)) {
                    case 1 -> {
                        camp = "nom";
                        resposta = Print.generatePregunta("Introdueix el nou nom: ");
                        condicio = isNom(resposta);
                        errorMsg = ERR_MSG_NOM;

                    }
                    case 2 -> {
                        camp = "cog1";
                        resposta = Print.generatePregunta("Introdueix el nou primer cognom: ");
                        condicio = isCog1(resposta);
                        errorMsg = ERR_MSG_COG1;
                    }
                    case 3 -> {
                        camp = "cog2";
                        resposta = Print.generatePregunta("Introdueix el nou segon cognom: ");
                        condicio = isCog2(resposta);
                        errorMsg = ERR_MSG_COG2;
                    }
                    case 4 -> {
                        camp = "sexe";
                        resposta = Print.generatePregunta("Introdueix el nou sexe (M/F): ");
                        condicio = isDate(resposta);
                        errorMsg = ERR_MSG_SEXE;
                    }
                    case 5 -> {
                        camp = "data_naixement";
                        resposta = Print.generatePregunta("Introdueix la nova data de naixement (dd/mm/aaaa): ");
                        condicio = isDate(resposta);
                        errorMsg = ERR_MSG_DATA_NAIX;
                    }
                    case 6 -> {
                        camp = "dni";
                        resposta = Print.generatePregunta("Introdueix el nou dni: ");
                        condicio = isDNI(resposta);
                        errorMsg = ERR_MSG_DNI;
                    }
                }

                if (condicio) camps.put(camp, resposta);
                else Print.println(errorMsg);

            } while (!condicio);

        } while (Print.generatePreguntaSN("Vols modificar algun altre camp (S/N)?: "));

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

    /**
     * Executa el procés per llistar els registres de la taula persones
     */
    public static void llistar() {
        List<Persona> resultat = new PersonaDAO().all();
        if (resultat.size() > 0) {
            Print.println("S'han trobat " + resultat.size() + " persones:");
            for (Persona p : resultat) {
                Print.println(p);
            }
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
