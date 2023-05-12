package controller;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;
import view.Print;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static view.Menu.generateMenuCamps;
import static view.Print.*;

public class ControllerPersona extends Controller {

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
    private static String obtenirCampPersona(String camp, String accio) {
        String resposta;
        boolean condicio = false;

        do {
            resposta = obtenirResposta("Introdueix el camp " + camp + " de la persona que vols " + accio +": ").trim();

            String errorMsg = "El " + camp + " ha de ";

            switch (camp) {
                case "persona_id" -> {
                    condicio = isId(resposta);
                    errorMsg = ERR_MSG_ID;
                }
                case "nom" -> {
                    condicio = isNom(resposta);
                    errorMsg += "tenir menys de 30 caràcters.";
                }
                case "cog1" -> {
                    condicio = isCog1(resposta);
                    errorMsg += "tenir menys de 30 caràcters.";
                }
                case "cog2" -> {
                    condicio = isCog2(resposta);
                    errorMsg += "tenir menys de 30 caràcters.";
                }
                case "sexe" -> {
                    condicio = isSexe(resposta);
                    errorMsg += "ser 'M' o 'F'.";
                }
                case "data_naixement" -> {
                    condicio = isDate(resposta);
                    errorMsg += "tenir el format 'dd/mm/aaaa'.";
                }
                case "dni" -> {
                    condicio = isDNI(resposta);
                    errorMsg += "tenir 8 caràcters en aquesta base de dades.";
                }
            }

            if (!condicio) println(errorMsg);

        } while (!condicio);

        return resposta;
    }


    //TODO: Extreure pregunta camps de cerca i modificar
    /**
     * Executa el procés de cerca de la taula persones.
     * @return Llista de persones trobades amb la cerca.
     */
    public static List<Persona> cercar() {
        List<Persona> resultat = null;

        String camp = null;

        do {
            switch (generateMenuCamps("cercar")) {
                case 1 -> camp = "persona_id";
                case 2 -> camp = "nom";
                case 3 -> camp = "cog1";
                case 4 -> camp = "cog2";
                case 5 -> camp = "sexe";
                case 6 -> camp = "data_naixement";
                case 7 -> camp = "dni";
                case 0 -> {
                    return resultat;
                }
            }
            String resposta = obtenirCampPersona(camp, "cercar");

            resultat = new PersonaDAO().search(camp, resposta);
            if (resultat.size() > 0) {
                for (Persona p : resultat) {
                    Print.println(">> " + p);
                }
                Print.println("S'han trobat " + resultat.size() + " persones amb aquest " + camp + ".");
            } else Print.println("No s'ha trobat cap persona amb aquest " + camp + ".");

        } while (Controller.obtenirRespostaSN("Vols trobar unes altres persones? (S/N): "));

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

            nom = obtenirCampPersona("nom", accio);
            cog1 = obtenirCampPersona("cog1", accio);
            cog2 = obtenirCampPersona("cog2", accio);
            dni = obtenirCampPersona("dni", accio);
            sexe = obtenirCampPersona("sexe", accio);
            data_naixement = convertToDate(obtenirCampPersona("data_naixement", accio));

            dadesCorrectes = obtenirRespostaSN("Són correctes les dades introduïdes? (S/N): ");

        } while (!dadesCorrectes);

        if (obtenirRespostaSN("Estàs segur que vols inserir aquesta persona? (S/N): ")) {
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
        if (obtenirRespostaSN("Estàs segur que vols actualitzar aquestes persones? (S/N): ")) {
            boolean updCorrecte = true;

            String[] campsAfegtats = campsModificats.keySet().toArray(new String[0]);

            for (Persona p : resultatCerca) {
                if (!new PersonaDAO().update(p, campsAfegtats)) {
                    updCorrecte = false;
                    println("No s'ha pogut actualitzar la persona amb id " + p.getId() + ".");
                }
            }

            if (updCorrecte) println("S'han actualitzat correctament les persones.");
        }
    }

    /**
     * Executa el procés per modificacar de camps de la taula persones
     * i crea un HashMap amb els camps que vol modificar l'usuari i els nous valors.
     * @return HashMap amb els camps a modificar i els seus valors
     */
    private static HashMap<String, String> modificarCamps() {

        HashMap<String, String> camps = new HashMap<>();

        String camp = null;

        do {
            switch (generateMenuCamps("modificar")) {
                case 1 -> camp = "nom";
                case 2 -> camp = "cog1";
                case 3 -> camp = "cog2";
                case 4 -> camp = "sexe";
                case 5 -> camp = "data_naixement";
                case 6 -> camp = "dni";
                case 0 -> {
                    Print.println("No s'ha modificat cap camp.");
                    return null;
                }
            }

            String resposta = obtenirCampPersona(camp, "modificar");
            camps.put(camp, resposta);

        } while (Controller.obtenirRespostaSN("Vols modificar algun altre camp (S/N)?: "));

        return camps;
    }

    /**
     * Executa el procés d'eliminació de persones
     */
    public static void eliminar() {
        // Cerquem persones a eliminar
        List<Persona> resultatCerca = cercar();

        // Comprovem si el codi està buit
        if (resultatCerca == null || resultatCerca.size() == 0) {
            Print.println("Sense resultat de cera no es pot eliminar.");
            return;
        }

        if (obtenirRespostaSN("Estàs segur que vols eliminar les persones trobades (S/N)?: ")) {
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
