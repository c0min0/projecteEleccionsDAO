package controller.persona;

import controller.Controller;
import controller.DAO.mySQL.PersonaDAO;
import controller.DataConverter;
import model.Persona;

import java.util.HashMap;
import java.util.List;

import static controller.Missatges.*;
import static controller.persona.DataValidatorPersona.*;
import static controller.persona.MissatgesPersona.*;
import static view.Print.*;

public class ControllerPersona extends Controller {
    // MENÚ

    /**
     * Executa el menú CRUD de la taula persones
     */
    public static void menuCRUDPersones() {

        do {

            switch (obtenirOpMenuCRUD()) {
                case 1 -> cercar();
                case 2 -> inserir();
                case 3 -> modificar();
                case 4 -> eliminar();
                case 5 -> llistar();
                case 6 -> ferRecompte();
                case 0 -> {
                    return;
                }
            }

        } while (obtenirRespostaSN(MSG_REPEAT_PERSONES));
    }

    // MÈTODES AUXILIARS

    /**
     * Demana a l'usuari el camp sobre el què vol realitzar l'acció passada per paràmetres.
     * @param accio Acció que es vol realitzar
     * @return Camp sobre el què es vol realitzar l'acció
     */
    private static String obtenirCamp (String accio) {

        String pregunta, resposta = null;

        // Segons l'acció formulem la pregunta diferent
        if (accio.equals("cercar")) pregunta = "Sobre quin camp vols cercar la o les persona/es?";
        else pregunta = "Quin camp vols " + accio + "?";

        final String OP1 = "persona_id INT UNSIGNED";
        final String OP2 = "nom VARCHAR(30)";
        final String OP3 = "cog1 VARCHAR(30)";
        final String OP4 = "cog2 VARCHAR(30)";
        final String OP5 = "sexe ENUM('M','F')";
        final String OP6 = "data_naixement DATE";
        final String OP7 = "dni VARCHAR(8)";
        final String ESCAPE = "Torna enrrere";

        // Demanem el camp sobre el qual es vol realitzar l'acció
        switch (obtenirOpMenu(pregunta, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7)) {
            case 1 -> resposta = "persona_id";
            case 2 -> resposta = "nom";
            case 3 -> resposta = "cog1";
            case 4 -> resposta = "cog2";
            case 5 -> resposta = "sexe";
            case 6 -> resposta = "data_naixement";
            case 7 -> resposta = "dni";
        }

        // Retornem el camp escollit
        return resposta;
    }

    /**
     * Demana a l'usuari el valor del camp sobre el qual vol realitzar l'acció
     * passada per paràmetres fins que aquest valor sigui vàlid.
     * @param camp Camp sobre el qual es vol realitzar l'acció
     * @param accio Acció que es vol realitzar
     * @return Resposta vàlida de l'usuari
     */
    private static String obtenirValorDelCamp(String camp, String accio) {

        String resposta;

        boolean condicio = false;

        do {
            // Demanem el valor del camp
            resposta = obtenirResposta("Introdueix el valor del camp "
                    + camp.toUpperCase() + " de la persona que vols " + accio.toUpperCase() + " (0 per tornar enrrere): ").trim();

            // Si l'usuari vol tornar enrrere retornem null
            if (resposta.equals("0")) return null;

            String errorMsg = "El " + camp + " ha de ";

            // Construïm el missatge d'error
            // i comprovem que el valor sigui vàlid
            // depenent del camp
            switch (camp) {
                case "persona_id" -> {
                    condicio = isPersonaId(resposta);
                    errorMsg = ID_CONDITION;
                }
                case "nom" -> {
                    condicio = isNom(resposta);
                    errorMsg += NOM_CONDITION;
                }
                case "cog1" -> {
                    condicio = isCog1(resposta);
                    errorMsg += COG1_CONDITION;
                }
                case "cog2" -> {
                    condicio = isCog2(resposta);
                    errorMsg += COG2_CONDITION;
                }
                case "sexe" -> {
                    condicio = isSexe(resposta);
                    errorMsg += SEXE_CONDITION;
                }
                case "data_naixement" -> {
                    condicio = isDataNaix(resposta);
                    errorMsg += DATA_NAIX_CONDITION;
                }
                case "dni" -> {
                    condicio = isDNI(resposta);
                    errorMsg += DNI_CONDITION;
                }
            }

            // Si el valor no és vàlid, mostrem el missatge d'error
            if (!condicio) println(errorMsg);

            // Si el valor no és vàlid repetim el bucle
        } while (!condicio);

        // Retornem el valor del camp
        return resposta;
    }

    /**
     * Executa el procés per demanar a l'usuari els camps i els valors d'aquests
     * sobre els que vol realitzar l'acció passada per paràmetres.
     * @param accio Acció que es vol realitzar
     * @return HashMap amb els camps a modificar com a key i els nous valors com a value
     * o null si no hi ha camps a modificar.
     */
    private static HashMap<String, String> obtenirCampsIValors(String accio) {

        HashMap<String, String> camps = new HashMap<>();

        do {
            // Demanem el camp a modificar
            String camp = obtenirCamp(accio);

            // Si l'usuari no vol modificar cap camp, retornem null
            if (camp == null) return null;

            // Demanem el nou valor del camp
            String resposta = obtenirValorDelCamp(camp, accio);

            // Afegim el camp i el nou valor al HashMap si l'usuari no vol cancel·lar l'acció
            if (resposta != null) camps.put(camp, resposta);

            // Preguntem si vol modificar algun altre camp
        } while (obtenirRespostaSN("Vols " + accio.toUpperCase() + " algun altre camp (S/N)?: "));

        // Retornem el HashMap amb els camps a modificar i els nous valors o null si no n'hi han
        return camps.size() > 0 ? camps : null;
    }

    // MÈTODES CRUD

    /**
     * Executa el procés de cerca de la taula persones.
     * @return Llista de persones trobades amb la cerca.
     */
    static List<Persona> cercar() {

        List<Persona> resultat;

        do {
            // Demanem el camp sobre el qual es vol cercar
            String camp = obtenirCamp("cercar");
            if (camp == null) return null;

            // Demanem el valor del camp
            String resposta = obtenirValorDelCamp(camp, "cercar");

            // Cerquem les persones amb el camp i valor especificats
            resultat = new PersonaDAO().search(camp, resposta);

            // Mostrem el resultat de la cerca
            if (resultat != null) {

                // Mostrem les persones trobades
                for (Persona p : resultat) println(">> " + p);

                // Mostrem el recompte de persones trobades
                println("S'han trobat " + resultat.size() + " persones amb aquest " + camp + ".");
            }

            // Si no s'ha trobat cap persona, informem a l'usuari
            else println("No s'ha trobat cap persona amb aquest " + camp + ".");

            // Preguntem a l'usuari si vol cercar unes altres persones
        } while (obtenirRespostaSN("Vols CERCAR unes altres persones? (S/N): "));

        // Retornem el resultat de la cerca
        // (per quan es cridi des d'altres mètodes que necessitin el resultat)
        return resultat;
    }

    /**
     * Executa el procés d'inserció de persones
     */
    static void inserir() {

        // Camps obligatoris
        String  dni;

        // HashMap amb els camps i valors que s'introduiran a la BD
        HashMap<String, String> campsInserits = new HashMap<>();

        // Primer introduïm els camps obligatoris
        println("Primer cal introduïr els camps obligatoris (dni).");
        dni = obtenirValorDelCamp("dni", "inserir");

        // Si l'usuari no vol introduïr els camps obligatoris, cancelem operació
        if (dni == null) return;

        // Afegim els camps obligatoris al HashMap
        campsInserits.put("dni", dni);

        // Demanem si vol inserir els camps opcionals o modificar els que ha introduït
        if (obtenirRespostaSN("Vols inserir algun camp més o modificar els que has introduït? (S/N): ")) {

            // Demanem la resta de camps
            HashMap<String, String> campsExtra = obtenirCampsIValors("inserir o modificar");

            // Si hi ha camps a inserir, els afegim al HashMap
            if (campsExtra != null) campsInserits.putAll(campsExtra);
        }

        // Mostrem les dades introduïdes a l'usuari
        println("Les dades introduïdes són les següents:");
        for (String camp : campsInserits.keySet()) {
            print(camp + ": " + campsInserits.get(camp));
        }
        print("");

        // Preguntem a l'usuari si vol inserir la persona
        if (obtenirRespostaSN("ESTÀS SEGUR que vols inserir una persona amb les dades introduïdes? (S/N): ")) {

            // Construïm l'objecte persona amb les dades introduïdes
            Persona p = new Persona(campsInserits.get("nom"),
                    campsInserits.get("cog1"),
                    campsInserits.get("cog2"),
                    campsInserits.get("sexe"),
                    DataConverter.toDate(campsInserits.get("data_naixement")),
                    campsInserits.get("dni"));

            // Inserim la persona a la base de dades
            if (new PersonaDAO().create(p)) println("Persona afegida amb èxit!");
            else println("No s'ha pogut afegir la persona.");
        }
    }

    /**
     * Executa el procés de modificació de persones
     */
    static void modificar() {

        // Cerquem persones a modificar
        List<Persona> resultatCerca = cercar();

        // Comprovem si el resultat està buit
        if (resultatCerca == null) {
            println("Sense resultat de cerca no es pot actualitzar cap persona.");
            return;
        }

        // Demanem quins camps es volen modificar
        HashMap <String,String> campsModificats = obtenirCampsIValors("modificar");

        // Comprovem si s'ha cancel·lat l'acció
        if (campsModificats == null) return;

        // Modifiquem els camps de les persones trobades amb la cerca
        for (Persona p : resultatCerca) {

            for (String camp : campsModificats.keySet()) {

                switch (camp) {
                    case "nom" -> p.setNom(campsModificats.get(camp));
                    case "cog1" -> p.setCog1(campsModificats.get(camp));
                    case "cog2" -> p.setCog2(campsModificats.get(camp));
                    case "sexe" -> p.setSexe(campsModificats.get(camp));
                    case "data_naixement" -> p.setDataNaixement(DataConverter.toDate(campsModificats.get(camp)));
                    case "dni" -> p.setDni(campsModificats.get(camp));
                }
            }
        }

        // Mostrem les dades introduïdes a l'usuari
        println("Les dades introduïdes són les següents:");
        for (String camp : campsModificats.keySet()) {
            print(camp + ": " + campsModificats.get(camp));
        }
        print("");

        // Missatge de confirmació
        if (obtenirRespostaSN("ESTÀS SEGUR que vols actualitzar aquesta/es persona/es amb les dades introduïdes? (S/N): ")) {

            // Obtenim els camps a modificar
            String[] campsAfectats = campsModificats.keySet().toArray(new String[0]);

            // Actualitzem les persones a la base de dades
            boolean updCorrecte = true;

            for (Persona p : resultatCerca) {
                // Si no s'ha pogut actualitzar la persona, mostrem el missatge
                if (!new PersonaDAO().update(p, campsAfectats)) {
                    updCorrecte = false;
                    println("No s'ha pogut actualitzar la persona amb id " + p.getId() + ".");
                }
            }

            // Si s'han actualitzat correctament totes les persones, mostrem el missatge
            if (updCorrecte) println("S'han actualitzat correctament totes les persones.");
        }
    }

    /**
     * Executa el procés d'eliminació de persones
     */
    static void eliminar() {

        // Demanem les persones a eliminar
        List<Persona> resultatCerca = cercar();

        // Comprovem si el codi està buit
        if (resultatCerca == null) {
            println("Sense resultat de cera no es pot eliminar.");
            return;
        }

        // Demanem confirmació per eliminar les persones
        if (obtenirRespostaSN("Estàs segur que vols eliminar la/es persona/es trobada/es (S/N)?: ")) { //TODO: explicar també que s'eliminaran els candidats associats. Amb els updates també passa?

            // Eliminem persones de la BD
            boolean delCorrecte = true;

            for (Persona p : resultatCerca) {

                // Si no s'ha pogut eliminar la persona, mostrem el missatge
                if (!new PersonaDAO().delete(p)) {
                    delCorrecte = false;
                    println("No s'ha pogut eliminar la persona amb id " + p.getId() + ".");
                }
            }

            // Si s'han eliminat correctament totes les persones, mostrem el missatge
            if (delCorrecte) println("S'han eliminat correctament les persones.");
        }

    }

    /**
     * Executa el procés per llistar els registres de la taula persones
     */
    static void llistar() {

        // Obtenim totes les persones de la base de dades
        List<Persona> resultat = new PersonaDAO().all();

        // Si la taula conté registres,
        if (resultat.size() != 0) {

            // Mostrem els registres
            for (Persona p : resultat) println(">> " + p);

            // Mostrem el total de registres
            println("S'han trobat " + resultat.size() + " persones.");

        }
        // Si la taula està buida, mostrem el missatge
        else println("No s'ha trobat cap persona.");
    }


    /**
     * Executa el procés per mostrar el recompte de la taula persones
     */
    static void ferRecompte() {

        // Obtenim el recompte de persones de la base de dades
        long recompte = new PersonaDAO().count();

        // Mostrem el recompte
        println("Hi ha " + recompte + " persones a la base de dades.");
    }
}
