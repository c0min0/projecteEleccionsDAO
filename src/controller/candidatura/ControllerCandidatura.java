package controller.candidatura;

import controller.Controller;

import controller.Controller;
import controller.DAO.mySQL.CandidaturaDAO;
import controller.DAO.mySQL.PersonaDAO;
import controller.DataConverter;
import controller.Missatges;
import model.Candidatura;
import model.Persona;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static controller.DataValidator.isId;
import static controller.Missatges.*;
import static controller.candidatura.DataValidatorCandidatura.*;
import static controller.candidatura.MissatgesCandidatura.*;
import static view.Print.*;
public class ControllerCandidatura extends Controller {
    public static void menuCRUDCandidatures() {

        do {

            switch (obtenirOpMenuCRUD()) {
                case 1 -> cercar();
                case 2 -> inserir();
                //case 3 -> modificar();
                //case 4 -> eliminar();
                //case 5 -> llistar();
                //case 6 -> ferRecompte();
                case 0 -> {
                    return;
                }
            }

        } while (obtenirRespostaSN(Missatges.MSG_REPEAT_CANDIDATURES));
    }
    static List<Candidatura> cercar() {

        List<Candidatura> resultat;

        do {
            // Demanem el camp sobre el qual es vol cercar
            String camp = obtenirCamp("cercar");
            if (camp == null) return null;

            // Demanem el valor del camp
            String resposta = obtenirValorDelCamp(camp, "cercar");

            // Cerquem les persones amb el camp i valor especificats
            resultat = new CandidaturaDAO().search(camp, resposta);

            // Mostrem el resultat de la cerca
            if (resultat != null) {

                // Mostrem les persones trobades
                for (Candidatura c : resultat) println(">> " + c);

                // Mostrem el recompte de persones trobades
                println("S'han trobat " + resultat.size() + " candidatures amb aquest " + camp + ".");
            }

            // Si no s'ha trobat cap persona, informem a l'usuari
            else println("No s'ha trobat cap candidatura amb aquest " + camp + ".");

            // Preguntem a l'usuari si vol cercar unes altres persones
        } while (obtenirRespostaSN("Vols CERCAR unes altres candidatures? (S/N): "));

        // Retornem el resultat de la cerca
        // (per quan es cridi des d'altres mètodes que necessitin el resultat)
        return resultat;
    }
    private static String obtenirCamp (String accio) {

        String pregunta, resposta = null;

        // Segons l'acció formulem la pregunta diferent
        if (accio.equals("cercar")) pregunta = "Sobre quin camp vols cercar la o les candidatura/es?";
        else pregunta = "Quin camp vols " + accio + "?";

        final String OP1 = "verdadero INT UNSIGNED";
        final String OP2 = "nom VARCHAR(30)";
        final String OP3 = "cog1 VARCHAR(30)";
        final String OP4 = "cog2 VARCHAR(30)";
        final String OP5 = "sexe ENUM('M','F')";
        final String OP6 = "data_naixement DATE";
        final String OP7 = "dni VARCHAR(8)";
        final String OP8 = "nacional VARCHAR(8)";
        final String ESCAPE = "Torna enrrere";

        // Demanem el camp sobre el qual es vol realitzar l'acció
        switch (obtenirOpMenu(pregunta, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7, OP8)) {
            case 1 -> resposta = "candidatura_id";
            case 2 -> resposta = "eleccio_id";
            case 3 -> resposta = "codi_candidatura";
            case 4 -> resposta = "nom_curt";
            case 5 -> resposta = "nom_llarg";
            case 6 -> resposta = "codi_acumulacio_provincia";
            case 7 -> resposta = "codi_acumulacio_ca";
            case 8 -> resposta = "codi_acumulacio_nacional";

        }

        // Retornem el camp escollit
        return resposta;
    }
    private static String obtenirValorDelCamp(String camp, String accio) {

        String resposta;

        boolean condicio = false;

        do {
            // Demanem el valor del camp
            resposta = obtenirResposta("Introdueix el valor del camp "
                    + camp.toUpperCase() + " de la candidatura que vols " + accio.toUpperCase() + " (0 per tornar enrrere): ").trim();

            // Si l'usuari vol tornar enrrere retornem null
            if (resposta.equals("0")) return null;

            String errorMsg = "El " + camp + " ha de ";

            // Construïm el missatge d'error
            // i comprovem que el valor sigui vàlid
            // depenent del camp
            switch (camp) {
                case "candidatura_id" -> {
                    condicio = isId(resposta);
                    errorMsg = ID_CONDITION;
                }
                case "eleccio_id" -> {
                    condicio = isLong(resposta);
                    errorMsg += ID_CONDITION;
                }
                case "codi_candidatura" -> {
                    condicio = isChar6(resposta);
                    errorMsg += ID_CONDITION;
                }
                case "nom_curt" -> {
                    condicio = isNomCurt(resposta);
                    errorMsg += NOM_CONDITION2;
                }
                case "nom_llarg" -> {
                    condicio = isNomLlarg(resposta);
                    errorMsg += NOM_CONDITION2;
                }
                case "codi_acumulacio_provincia" -> {
                    condicio = isChar6(resposta);
                    errorMsg += ID_CONDITION;
                }
                case "codi_acumulacio_ca" -> {
                    condicio = isChar6(resposta);
                    errorMsg += ID_CONDITION;
                }
                 case "codi_acumulacio_nacional" -> {
                    condicio = isChar6(resposta);
                    errorMsg += ID_CONDITION;
                }


            }

            // Si el valor no és vàlid, mostrem el missatge d'error
            if (!condicio) println(errorMsg);

            // Si el valor no és vàlid repetim el bucle
        } while (!condicio);

        // Retornem el valor del camp
        return resposta;
    }
    static void inserir() {

        // Camps obligatoris
        String  dni;

        // HashMap amb els camps i valors que s'introduiran a la BD
        HashMap<String, String> campsInserits = new HashMap<>();

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
        if (obtenirRespostaSN("ESTÀS SEGUR que vols inserir una candidatura amb les dades introduïdes? (S/N): ")) {

            // Construïm l'objecte persona amb les dades introduïdes
            Candidatura c = new Candidatura(
                    Long.parseLong(campsInserits.get ("eleccio_id")),
                    campsInserits.get("codi_candidatura"),
                    campsInserits.get("nom_curt"),
                    campsInserits.get("nom_llarg"),
                    campsInserits.get("codi_acumulacio_provincia"),
                    campsInserits.get("codi_acumulacio_ca"),
                    campsInserits.get("codi_acumulacio_nacional"));

            // Inserim la persona a la base de dades
            if (new CandidaturaDAO().create(c)) println("Candidatura afegida amb èxit!");
            else println("No s'ha pogut afegir la Candidatura.");
        }
    }
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


}
