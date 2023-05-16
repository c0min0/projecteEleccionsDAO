package controller.comAutonoma;

import controller.Controller;
import controller.DAO.mySQL.ComAutonomaDAO;
import model.ComAutonoma;

import java.util.HashMap;
import java.util.List;

import static controller.Missatges.MSG_AFEGIR_CAMPS;
import static controller.Missatges.MSG_CAMPS_OBLIG;
import static controller.Missatges.MSG_MOSTRAR_DADES;
import static controller.Missatges.MSG_NO_RESULT;
import static controller.candidatura.MissatgesCandidatura.*;
import static controller.comAutonoma.DataValidatorComAutonoma.*;
import static controller.comAutonoma.MissatgesComAutonoma.*;
import static view.Print.print;
import static view.Print.println;

public class ControllerComAutonoma extends Controller {

    // MENÚ

    /**
     * Executa el menú CRUD de la taula comunitats_autonomes.
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
        if (accio.equals("cercar")) pregunta = "Sobre quin camp vols cercar la o les comunitat/s autònoma/es?";
        else pregunta = "Quin camp vols " + accio + "?";

        final String OP1 = "comunitat_aut_id TINYINT UNSIGNED";
        final String OP2 = "nom VARCHAR(45)";
        final String OP3 = "codi_ine CHAR(2)";
        final String ESCAPE = "Torna enrrere";

        // Demanem el camp sobre el qual es vol realitzar l'acció
        switch (obtenirOpMenu(pregunta, ESCAPE, OP1, OP2, OP3)) {
            case 1 -> resposta = "comunitat_aut_id";
            case 2 -> resposta = "nom";
            case 3 -> resposta = "codi_ine";
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
                    + camp.toUpperCase() + " de la comunitat autònoma que vols "
                    + accio.toUpperCase() + " (0 per tornar enrrere): ").trim();

            // Si l'usuari vol tornar enrrere retornem null
            if (resposta.equals("0")) return null;

            String errorMsg = "El " + camp + " ha de ";

            // Construïm el missatge d'error i comprovem
            // que el valor sigui vàlid depenent del camp
            switch (camp) {
                case "comunitat_aut_id" -> {
                    condicio = isComAutonomaId(resposta);
                    errorMsg += CA_ID_CONDITION;
                }
                case "nom" -> {
                    condicio = isNomCA(resposta);
                    errorMsg += CA_NOM_CONDITION;
                }
                case "codi_ine" -> {
                    condicio = isCodiIneCA(resposta);
                    errorMsg += CA_CODI_INE_CONDITION;
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
    static List<ComAutonoma> cercar() {

        List<ComAutonoma> resultat;

        do {
            // Demanem el camp sobre el qual es vol cercar
            String camp = obtenirCamp("cercar");
            if (camp == null) return null;

            // Demanem el valor del camp
            String resposta = obtenirValorDelCamp(camp, "cercar");
            if (resposta == null) return null;

            // Cerquem les CAs amb el camp i valor especificats
            resultat = new ComAutonomaDAO().search(camp, resposta);

            // Mostrem el resultat de la cerca
            if (resultat != null) {

                // Mostrem les candidatures trobades
                for (ComAutonoma c : resultat) println(">> " + c);

                // Mostrem el recompte de CAs trobades
                println("S'han trobat " + resultat.size() + " comunitats aautònomes amb aquest " + camp + ".");
            }

            // Si no s'ha trobat cap comunitat autònoma, informem a l'usuari
            else println("No s'ha trobat cap comunitat autònoma amb aquest " + camp + ".");

            // Preguntem a l'usuari si vol cercar unes altres CAs
        } while (obtenirRespostaSN("Vols CERCAR unes altres comunitats autònomes? (S/N): "));

        // Retornem el resultat de la cerca
        // (per quan es cridi des d'altres mètodes que necessitin el resultat)
        return resultat;
    }

    /**
     * Executa el procés d'inserció de candidatures.
     */
    static void inserir() {

        // Camps obligatoris
        String codi_ine;

        // HashMap amb els camps i valors que s'introduiran a la BD
        HashMap<String, String> campsInserits = new HashMap<>();

        // Primer introduïm els camps obligatoris
        println(MSG_CAMPS_OBLIG + "(codi_ine).");
        codi_ine = obtenirValorDelCamp("codi_ine", "inserir");

        // Si l'usuari no vol introduïr els camps obligatoris, cancelem operació
        if (codi_ine == null) return;

        // Afegim els camps obligatoris al HashMap
        campsInserits.put("codi_ine", codi_ine);

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

        // Preguntem a l'usuari si vol inserir la comunitat autònoma
        if (obtenirRespostaSN("ESTÀS SEGUR que vols inserir una comunitat autònoma amb les dades introduïdes? (S/N): ")) {

            // Construïm l'objecte ComAutonoma amb les dades introduïdes
            ComAutonoma ca = new ComAutonoma(
                    Integer.parseInt(campsInserits.get("comunitat_aut_id")),
                    campsInserits.get("nom"),
                    campsInserits.get("codi_ine"));

            // Inserim la comunitat autònoma a la base de dades
            if (new ComAutonomaDAO().create(ca)) println("Comunitat autònoma afegida amb èxit!");
            else println("No s'ha pogut afegir la comunitat autònoma.");
        }
    }

    /**
     * Executa el procés de modificació de comunitats autònomes.
     */
    static void modificar() {

        // Cerquem CAs a modificar
        List<ComAutonoma> resultatCerca = cercar();

        // Comprovem si el resultat està buit
        if (resultatCerca == null) {
            println(MSG_NO_RESULT + "actualitzar cap comunitat autònoma.");
            return;
        }

        // Demanem quins camps es volen modificar
        HashMap<String,String> campsModificats = obtenirCampsIValors("modificar");

        // Comprovem si s'ha cancel·lat l'acció
        if (campsModificats == null) return;

        // Modifiquem els camps de les CAs trobades amb la cerca
        for (ComAutonoma ca : resultatCerca) {

            for (String camp : campsModificats.keySet()) {

                switch (camp) {
                    case "comunitat_aut_id" -> ca.setId(Integer.parseInt(campsModificats.get(camp)));
                    case "nom" -> ca.setNom(campsModificats.get(camp));
                    case "codi_ine" -> ca.setCodiIne(campsModificats.get(camp));
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
        if (obtenirRespostaSN("ESTÀS SEGUR que vols actualitzar aquesta/es comunitat/s autònoma/es amb les dades introduïdes? (S/N): ")) {

            // Obtenim els camps a modificar
            String[] campsAfectats = campsModificats.keySet().toArray(new String[0]);

            // Actualitzem les CAs a la base de dades
            boolean updCorrecte = true;

            for (ComAutonoma ca : resultatCerca) {
                // Si no s'ha pogut actualitzar la CA, mostrem el missatge
                if (!new ComAutonomaDAO().update(ca, campsAfectats)) {
                    updCorrecte = false;
                    println("No s'ha pogut actualitzar la comunitat autònoma amb id " + ca.getId() + ".");
                }
            }

            // Si s'han actualitzat correctament totes les CAs, mostrem el missatge
            if (updCorrecte) println("S'han actualitzat correctament totes les comunitats autònomes.");
        }
    }

    /**
     * Executa el procés d'eliminació de comunitats autònomes.
     */
    static void eliminar() {

        // Demanem les candidatures a eliminar
        List<ComAutonoma> resultatCerca = cercar();

        // Comprovem si el codi està buit
        if (resultatCerca == null) {
            println(MSG_NO_RESULT + "eliminar.");
            return;
        }

        // Demanem confirmació per eliminar les CAs
        if (obtenirRespostaSN("Estàs segur que vols eliminar la/es comunitat/s autònoma/es trobada/es (S/N)?: ")) {

            // Eliminem CA de la BD
            boolean delCorrecte = true;

            for (ComAutonoma ca : resultatCerca) {

                // Si no s'ha pogut eliminar la CA, mostrem el missatge
                if (!new ComAutonomaDAO().delete(ca)) {
                    delCorrecte = false;
                    println("No s'ha pogut eliminar la comunitat autònoma amb id " + ca.getId() + ".");
                }
            }

            // Si s'han eliminat correctament totes les CAs, mostrem el missatge
            if (delCorrecte) println("S'han eliminat correctament les comunitats autònomes.");
        }
    }

    /**
     * Executa el procés per llistar els registres de la taula comunitats_autonomes.
     */
    static void llistar() {

        // Obtenim totes les CAs de la base de dades
        List<ComAutonoma> resultat = new ComAutonomaDAO().all();

        // Si la taula conté registres,
        if (resultat.size() != 0) {

            // Mostrem els registres
            for (ComAutonoma ca : resultat) println(">> " + ca);

            // Mostrem el total de registres
            println("S'han trobat " + resultat.size() + " comunitats autònomes.");

        }
        // Si la taula està buida, mostrem el missatge
        else println("No s'ha trobat cap comunitat autònoma.");
    }

    /**
     * Executa el procés per mostrar el recompte de la taula comunitats_autonomes.
     */
    static void ferRecompte() {

        // Obtenim el recompte de CAs de la base de dades
        long recompte = new ComAutonomaDAO().count();

        // Mostrem el recompte
        println("Hi ha " + recompte + " comunitats autònomes a la base de dades.");
    }


}