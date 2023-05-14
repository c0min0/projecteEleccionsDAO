package controller.candidatura;

import controller.Controller;

import controller.DAO.mySQL.CandidaturaDAO;
import model.Candidatura;

import java.util.HashMap;
import java.util.List;

import static controller.Missatges.*;
import static controller.candidatura.DataValidatorCandidatura.*;
import static controller.candidatura.MissatgesCandidatura.*;
import static view.Print.*;

public class ControllerCandidatura extends Controller {

    // MENÚ

    /**
     * Executa el menú CRUD de la taula candidatures
     */
    public static void menuCRUD() {

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

        } while (obtenirRespostaSN(MSG_REPEAT_CANDIDATURES));
    }

    // MÈTODES AUXILIARS

    /**
     * Demana a l'usuari el camp sobre el qual vol realitzar l'acció passada per paràmetres.
     * @param accio Acció que es vol realitzar
     * @return Camp sobre el qual es vol realitzar l'acció
     */
    private static String obtenirCamp(String accio) {

        String pregunta, resposta = null;

        // Segons l'acció formulem la pregunta diferent
        if (accio.equals("cercar")) pregunta = "Sobre quin camp vols cercar la o les candidatura/es?";
        else pregunta = "Quin camp vols " + accio + "?";

        final String OP1 = "candidatura_id INT UNSIGNED";
        final String OP2 = "eleccio_id TINYINT UNSIGNED";
        final String OP3 = "codi_candidatura CHAR(6)";
        final String OP4 = "nom_curt VARCHAR(50)";
        final String OP5 = "nom_llarg VARCHAR(150)";
        final String OP6 = "codi_candidatura_provincia CHAR(6)";
        final String OP7 = "codi_candidatura_ca CHAR(6)";
        final String OP8 = "codi_candidatura_nacional CHAR(6)";
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
                    + camp.toUpperCase() + " de la candidatura que vols " + accio.toUpperCase() + " (0 per tornar enrrere): ").trim();

            // Si l'usuari vol tornar enrrere retornem null
            if (resposta.equals("0")) return null;

            String errorMsg = "El " + camp + " ha de ";

            // Construïm el missatge d'error i comprovem
            // que el valor sigui vàlid depenent del camp
            switch (camp) {
                case "candidatura_id" -> {
                    condicio = isCandidaturaId(resposta);
                    errorMsg = CANDIDATURA_ID_CONDITION;
                }
                case "eleccio_id" -> {
                    condicio = isEleccioId(resposta);
                    errorMsg += ELECCIO_ID_CONDITION;
                }
                case "codi_candidatura" -> {
                    condicio = isCodiCandidatura(resposta);
                    errorMsg += CODI_CANDIDATURA_CONDITION;
                }
                case "nom_curt" -> {
                    condicio = isNomCurt(resposta);
                    errorMsg += NOM_CURT_CONDITION;
                }
                case "nom_llarg" -> {
                    condicio = isNomLlarg(resposta);
                    errorMsg += NOM_LLARG_CONDITION;
                }
                case "codi_acumulacio_provincia" -> {
                    condicio = isCodiAcProv(resposta);
                    errorMsg += CODIAC_PROV_CONDITON;
                }
                case "codi_acumulacio_ca" -> {
                    condicio = isCodiAcCA(resposta);
                    errorMsg += CODIAC_CA_CONDITON;
                }
                case "codi_acumulacio_nacional" -> {
                    condicio = isCodiAcNacional(resposta);
                    errorMsg += CODIAC_NACIONAL_CONDITON;
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
            if (camp == null) return camps;

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
     * Executa el procés de cerca de la taula candidatures.
     * @return Llista de candidatures trobades amb la cerca.
     */
    static List<Candidatura> cercar() {

        List<Candidatura> resultat;

        do {
            // Demanem el camp sobre el qual es vol cercar
            String camp = obtenirCamp("cercar");
            if (camp == null) return null;

            // Demanem el valor del camp
            String resposta = obtenirValorDelCamp(camp, "cercar");
            if (resposta == null) return null;

            // Cerquem les candidatures amb el camp i valor especificats
            resultat = new CandidaturaDAO().search(camp, resposta);

            // Mostrem el resultat de la cerca
            if (resultat != null) {

                // Mostrem les candidatures trobades
                for (Candidatura c : resultat) println(">> " + c);

                // Mostrem el recompte de candidatures trobades
                println("S'han trobat " + resultat.size() + " candidatures amb aquest " + camp + ".");
            }

            // Si no s'ha trobat cap candidatura, informem a l'usuari
            else println("No s'ha trobat cap candidatura amb aquest " + camp + ".");

            // Preguntem a l'usuari si vol cercar unes altres candidatures
        } while (obtenirRespostaSN("Vols CERCAR unes altres candidatures? (S/N): "));

        // Retornem el resultat de la cerca
        // (per quan es cridi des d'altres mètodes que necessitin el resultat)
        return resultat;
    }

    /**
     * Executa el procés d'inserció de candidatures.
     */
    static void inserir() {

        // Camps obligatoris
        String eleccio_id;

        // HashMap amb els camps i valors que s'introduiran a la BD
        HashMap<String, String> campsInserits = new HashMap<>();

        // Primer introduïm els camps obligatoris
        println(MSG_CAMPS_OBLIG + "(eleccio_id).");
        eleccio_id = obtenirValorDelCamp("eleccio_id", "inserir");

        // Si l'usuari no vol introduïr els camps obligatoris, cancelem operació
        if (eleccio_id == null) return;

        // Afegim els camps obligatoris al HashMap
        campsInserits.put("eleccio_id", eleccio_id);

        // Demanem si vol inserir els camps opcionals o modificar els que ha introduït
        if (obtenirRespostaSN(MSG_AFEGIR_CAMPS)) {

            // Demanem la resta de camps
            HashMap<String, String> campsExtra = obtenirCampsIValors("inserir o modificar");

            // Si hi ha camps a inserir, els afegim al HashMap
            if (campsExtra != null) campsInserits.putAll(campsExtra);
        }

        // Mostrem les dades introduïdes a l'usuari
        println(MSG_MOSTRAR_DADES);
        for (String camp : campsInserits.keySet()) {
            print(camp + ": " + campsInserits.get(camp));
        }
        print("");

        // Preguntem a l'usuari si vol inserir la candidatura
        if (obtenirRespostaSN("ESTÀS SEGUR que vols inserir una candidatura amb les dades introduïdes? (S/N): ")) {

            // Construïm l'objecte candidatura amb les dades introduïdes
            Candidatura c = new Candidatura(
                    Integer.parseInt(campsInserits.get("eleccio_id")),
                    campsInserits.get("codi_candidatura"),
                    campsInserits.get("nom_curt"),
                    campsInserits.get("nom_llarg"),
                    campsInserits.get("codi_acumulacio_provincia"),
                    campsInserits.get("codi_acumulacio_ca"),
                    campsInserits.get("codi_acumulacio_nacional"));

            // Inserim la candidatura a la base de dades
            if (new CandidaturaDAO().create(c)) println("Candidatura afegida amb èxit!");
            else println("No s'ha pogut afegir la candidatura.");
        }
    }

    /**
     * Executa el procés de modificació de candidatures.
     */
    static void modificar() {

        // Cerquem candidatures a modificar
        List<Candidatura> resultatCerca = cercar();

        // Comprovem si el resultat està buit
        if (resultatCerca == null) {
            println(MSG_NO_RESULT + "actualitzar cap candidatura.");
            return;
        }

        // Demanem quins camps es volen modificar
        HashMap<String,String> campsModificats = obtenirCampsIValors("modificar");

        // Comprovem si s'ha cancel·lat l'acció
        if (campsModificats == null) return;

        // Modifiquem els camps de les candidatures trobades amb la cerca
        for (Candidatura c : resultatCerca) {

            for (String camp : campsModificats.keySet()) {

                switch (camp) {
                    case "eleccio_id" -> c.setEleccioId(Long.parseLong(campsModificats.get(camp)));
                    case "codi_candidatura" -> c.setCodiCandidatura(campsModificats.get(camp));
                    case "nom_curt" -> c.setNomCurt(campsModificats.get(camp));
                    case "nom_llarg" -> c.setNomLlarg(campsModificats.get(camp));
                    case "codi_acumulacio_provincia" -> c.setCodiAcumulacioProvincia(campsModificats.get(camp));
                    case "codi_acumulacio_ca" -> c.setCodiAcumulacioCA(campsModificats.get(camp));
                    case "codi_acumulacio_nacional" -> c.setCodiAcumulacioNacional(campsModificats.get(camp));
                }
            }
        }

        // Mostrem les dades introduïdes a l'usuari
        println(MSG_MOSTRAR_DADES);
        for (String camp : campsModificats.keySet()) {
            print(camp + ": " + campsModificats.get(camp));
        }
        print("");

        // Missatge de confirmació
        if (obtenirRespostaSN("ESTÀS SEGUR que vols actualitzar aquesta/es candidatura/es amb les dades introduïdes? (S/N): ")) {

            // Obtenim els camps a modificar
            String[] campsAfectats = campsModificats.keySet().toArray(new String[0]);

            // Actualitzem les candidatures a la base de dades
            boolean updCorrecte = true;

            for (Candidatura c : resultatCerca) {
                // Si no s'ha pogut actualitzar la candidatura, mostrem el missatge
                if (!new CandidaturaDAO().update(c, campsAfectats)) {
                    updCorrecte = false;
                    println("No s'ha pogut actualitzar la candidatura amb id " + c.getId() + ".");
                }
            }

            // Si s'han actualitzat correctament totes les candidatures, mostrem el missatge
            if (updCorrecte) println("S'han actualitzat correctament totes les candidatures.");
        }
    }

    /**
     * Executa el procés d'eliminació de candidatures
     */
    static void eliminar() {

        // Demanem les candidatures a eliminar
        List<Candidatura> resultatCerca = cercar();

        // Comprovem si el codi està buit
        if (resultatCerca == null) {
            println(MSG_NO_RESULT + "eliminar.");
            return;
        }

        // Demanem confirmació per eliminar les candidatures
        if (obtenirRespostaSN("Estàs segur que vols eliminar la/es candidatura/es trobada/es (S/N)?: ")) {

            // Eliminem candidatures de la BD
            boolean delCorrecte = true;

            for (Candidatura c : resultatCerca) {

                // Si no s'ha pogut eliminar la candidatura, mostrem el missatge
                if (!new CandidaturaDAO().delete(c)) {
                    delCorrecte = false;
                    println("No s'ha pogut eliminar la candidatura amb id " + c.getId() + ".");
                }
            }

            // Si s'han eliminat correctament totes les candidatures, mostrem el missatge
            if (delCorrecte) println("S'han eliminat correctament les candidatures.");
        }

    }

    /**
     * Executa el procés per llistar els registres de la taula candidatures.
     */
    static void llistar() {

        // Obtenim totes les candidatures de la base de dades
        List<Candidatura> resultat = new CandidaturaDAO().all();

        // Si la taula conté registres,
        if (resultat.size() != 0) {

            // Mostrem els registres
            for (Candidatura c : resultat) println(">> " + c);

            // Mostrem el total de registres
            println("S'han trobat " + resultat.size() + " candidatures.");

        }
        // Si la taula està buida, mostrem el missatge
        else println("No s'ha trobat cap candidatura.");
    }

    /**
     * Executa el procés per mostrar el recompte de la taula candidatures.
     */
    static void ferRecompte() {

        // Obtenim el recompte de candidatures de la base de dades
        long recompte = new CandidaturaDAO().count();

        // Mostrem el recompte
        println("Hi ha " + recompte + " candidatures a la base de dades.");
    }

}
