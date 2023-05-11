package controller;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static view.Menu.*;

public class PersonaController extends Controller {
    /**
     * Demana un DNI i el valida.
     * @return DNI validat.
     */
    public static String demanarDniValid() {
        String dni;

        do {
            dni = generatePregunta("Quin és el DNI de la persona? (Introdueix una referència de 8 caràcters): ");
            if (!isDNI(dni)) System.out.println("DNI incorrecte, torna a provar-ho.");
        } while (!isDNI(dni));

        return dni;
    }

    /**
     * Valida un DNI comprovant que tingui 8 caràcters.
     * @param dni DNI a validar.
     * @return True si el DNI té 8 caràcters, false si no.
     */
    public static boolean isDNI(String dni) {
        return dni.length() == 8;
    }

    /**
     * Demana un sexe i el valida.
     * @return Sexe validat.
     */
    public static String demanarSexeValid() {
        String sexe;

        do {
            sexe = generatePregunta("Quin és el sexe de la persona? (M/F): ");
            if (!isSexe(sexe)) System.out.println("Sexe incorrecte, torna a provar-ho.");
        } while (!isSexe(sexe));

        return sexe;
    }

    /**
     * Valida un sexe comprovant que sigui M o F.
     * @param sexe Sexe a validar.
     * @return True si el sexe és M o F, false si no.
     */
    public static boolean isSexe(String sexe) {
        return sexe.equals("M") || sexe.equals("F");
    }

    /**
     * Demana una data de naixement i la valida.
     * @return Data de naixement validada.
     */
    public static Date demanarValidarDataNaix() {
        String dataNaix;

        do {
            dataNaix = generatePregunta("Quina és la data de naixement de la persona? (dd/mm/aaaa): ");
            if (!isDate(dataNaix)) System.out.println("Data incorrecte, torna a provar-ho.");
        } while (!isDate(dataNaix));

        return convertirData(dataNaix);
    }

    /**
     * Valida una data comprovant que tingui el format dd/mm/aaaa.
     * @param data Data a validar.
     * @return True si la data té el format dd/mm/aaaa, false si no.
     */
    public static boolean isDate(String data) {
        return data.matches("([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/(19|20)[0-9]{2}");
    }

    /**
     * Converteix una data en format String (dd/mm/aaa) a un objecte Date.
     * @param data Data en format (dd/mm/aaaa).
     * @return Data en format Date.
     */
    public static Date convertirData(String data) {
        String[] parts = data.split("/");
        int dia = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]);
        int any = Integer.parseInt(parts[2]);
        return new Date(any - 1900, mes - 1, dia);
    }

    /**
     * Executa el procés d'inserció de persones
     */
    public static void insertPersona() {
        // Demanem a l'usuari les dades de la persona
        String nom = generatePregunta("Quin és el nom de la persona?: ");
        String cog1 = generatePregunta("Quin és el primer cognom de la persona?: ");
        String cog2 = generatePregunta("Quin és el segon cognom de la persona?: ");
        String dni = demanarDniValid();
        String sexe = demanarSexeValid();
        Date data_naixement = demanarValidarDataNaix();
        // Construïm l'objecte persona amb les dades introduïdes
        Persona p = new Persona(nom, cog1, cog2, sexe, data_naixement, dni);

        // Creem l'objecte DAO per a poder fer la inserció
        PersonaDAO pDAO = new PersonaDAO();

        // Inserim la persona a la base de dades
        if (pDAO.create(p)) System.out.println("Persona afegida amb èxit!");
        else System.out.println("No s'ha pogut afegir la persona.");
    }

    /**
     * Executa el procés de cerca de persones
     */
    public static List<Persona> searchPersona() {
        List<Persona> resultat;

        final String  PREGUNTA = "Sobre quin camp vols cercar la o les persona/es?";
        final String OP1 = "persona_id INT UNSIGNED";
        final String OP2 = "nom VARCHAR(30)";
        final String OP3 = "cog1 VARCHAR(30)";
        final String OP4 = "cog2 VARCHAR(30)";
        final String OP5 = "sexe ENUM('M','F')";
        final String OP6 = "data_naixement DATE";
        final String OP7 = "dni VARCHAR(8)";
        final String ESCAPE = "Torna enrrere";


        do {
            resultat = null;
            switch (generateMenu(PREGUNTA, ESCAPE, OP1, OP2, OP3, OP4, OP5, OP6, OP7)) {
                case 1 -> {
                    // Cercar per persona_id
                    boolean idIncorrecte;
                    do {
                        idIncorrecte = false;
                        String sID = generatePregunta("Introdueix l'id de la persona que vols cercar: ");
                        if (PersonaController.isLong(sID)) {
                            long id = Long.parseLong(sID);
                            Persona p = new PersonaDAO().readById(id);
                            if (p != null) {
                                System.out.println("S'ha trobat una persona amb aquest id: " + p);
                                resultat = new ArrayList<>();
                                resultat.add(p);
                            } else System.out.println("No s'ha trobat cap persona amb aquest id.");

                        } else {
                            idIncorrecte = true;
                            System.out.println("Id incorrecte, torna a provar-ho.");
                        }
                    } while (idIncorrecte);
                }
                case 2 -> {
                    // Cercar per nom
                    String nom = generatePregunta("Introdueix el nom de la persona que vols cercar: ");
                    resultat = new PersonaDAO().search("nom", nom);
                    if (resultat.size() > 0) {
                        System.out.println("S'han trobat " + resultat.size() + " persones amb aquest nom:");
                        for (Persona p : resultat) {
                            System.out.println(p);
                        }
                    } else System.out.println("No s'ha trobat cap persona amb aquest nom.");
                }
                case 3 -> {
                    // Cercar per cog1
                    String cog1 = generatePregunta("Introdueix el primer cognom de la persona que vols cercar: ");
                    resultat = new PersonaDAO().search("cog1", cog1);
                    if (resultat.size() > 0) {
                        System.out.println("S'han trobat " + resultat.size() + " persones amb aquest primer cognom:");
                        for (Persona p : resultat) {
                            System.out.println(p);
                        }
                    } else System.out.println("No s'ha trobat cap persona amb aquest primer cognom.");
                }
                case 4 -> {
                    // Cercar per cog2
                    String cog2 = generatePregunta("Introdueix el segon cognom de la persona que vols cercar: ");
                    resultat = new PersonaDAO().search("cog2", cog2);
                    if (resultat.size() > 0) {
                        System.out.println("S'han trobat " + resultat.size() + " persones amb aquest segon cognom:");
                        for (Persona p : resultat) {
                            System.out.println(p);
                        }
                    } else System.out.println("No s'ha trobat cap persona amb aquest segon cognom.");
                }
                case 5 -> {
                    // Cercar per sexe
                    boolean sexeIncorrecte;
                    do {
                        sexeIncorrecte = false;
                        String sexe = generatePregunta("Introdueix el sexe de la persona que vols cercar (M/F): ");
                        if (isSexe(sexe)) {
                            resultat = new PersonaDAO().search("sexe", sexe);
                            if (resultat.size() > 0) {
                                System.out.println("S'han trobat " + resultat.size() + " persones amb aquest sexe:");
                                for (Persona p : resultat) {
                                    System.out.println(p);
                                }
                            } else System.out.println("No s'ha trobat cap persona amb aquest sexe.");
                        } else {
                            sexeIncorrecte = true;
                            System.out.println("Sexe incorrecte, torna a provar-ho.");
                        }
                    } while (sexeIncorrecte);
                }
                case 6 -> {
                    // Cercar per data_naixement
                    boolean dataIncorrecte;
                    do {
                        dataIncorrecte = false;
                        String d = generatePregunta("Introdueix la data de naixement de la persona que vols cercar (dd/mm/aaaa): ");
                        if (isDate(d)) {
                            Date data = convertirData(d);
                            resultat = new PersonaDAO().search("data_naixement", data);
                            if (resultat.size() > 0) {
                                System.out.println("S'han trobat " + resultat.size() + " persones amb aquesta data de naixement:");
                                for (Persona p : resultat) {
                                    System.out.println(p);
                                }
                            } else System.out.println("No s'ha trobat cap persona amb aquesta data de naixement.");
                        } else {
                            dataIncorrecte = true;
                            System.out.println("Data incorrecte, torna a provar-ho.");
                        }
                    } while (dataIncorrecte);
                }
                case 7 -> {
                    // Cercar per dni
                    boolean dniIncorrecte;
                    do {
                        dniIncorrecte = false;
                        String dni = generatePregunta("Introdueix el dni de la persona que vols cercar: ");
                        if (isDNI(dni)) {
                            resultat = new PersonaDAO().search("dni", dni);
                            if (resultat.size() > 0) {
                                System.out.println("S'han trobat " + resultat.size() + " persones amb aquest dni:");
                                for (Persona p : resultat) {
                                    System.out.println(p);
                                }
                            } else System.out.println("No s'ha trobat cap persona amb aquest dni.");
                        } else {
                            dniIncorrecte = true;
                            System.out.println("Dni incorrecte, torna a provar-ho.");
                        }
                    } while (dniIncorrecte);
                }
                case 0 -> {
                    // Sortir
                    return resultat;
                }
            }
        } while (generatePreguntaSN("Vols trobar unes altres persones? (S/N): "));

        return resultat;
    }

    /**
     * Executa el procés de modificació de persones
     */
    public static void updPersona() {
        // Cerquem persones a modificar
        List<Persona> resultatCerca;
        boolean resultatIncorrecte;
        do {
            resultatIncorrecte = false;
            resultatCerca = searchPersona();

            // Comprovem si el resultat està buit
            if (resultatCerca == null || resultatCerca.size() == 0) {
                System.out.println("No hi ha cap persona definida per actualitzar.");
                resultatIncorrecte = true;
            }

            // Preguntem si volen tornar a cercar
            if (!generatePreguntaSN("Vols tornar a cercar? (S/N): ")) {
                // Si no volen tornar a cercar i el resultat està buit, sortim
                if (resultatIncorrecte) {
                    return;
                }
                // Si no volen tornar a cercar i el resultat no està buit, continuem
                else break;
            }
            // Si volen tornar a cercar, repetim el procés
            else {
                resultatIncorrecte = true;
            }

        } while (resultatIncorrecte);

        // Modifiquem camps dels objectes Persona
        HashMap <String,String> camps = menuPersonesUpdCampsAModificar();
        if (camps == null) return;

        for (Persona p : resultatCerca) {
            for (String camp : camps.keySet()) {
                switch (camp) {
                    case "nom" -> p.setNom(camps.get(camp));
                    case "cog1" -> p.setCog1(camps.get(camp));
                    case "cog2" -> p.setCog2(camps.get(camp));
                    case "sexe" -> p.setSexe(camps.get(camp));
                    case "data_naixement" -> p.setDataNaixement(convertirData(camps.get(camp)));
                    case "dni" -> p.setDni(camps.get(camp));
                }
            }
        }

        // Actualitzem persones a la BD
        String[] campsAModificar = camps.keySet().toArray(new String[0]);
        boolean updCorrecte = true;
        for (Persona p : resultatCerca) {
            if (!new PersonaDAO().update(p, campsAModificar)) {
                updCorrecte = false;
                System.out.println("No s'ha pogut actualitzar la persona amb id " + p.getId() + ".");
            }
        }

        // Mostrem missatge d'èxit
        if (updCorrecte) System.out.println("\nS'han actualitzat correctament les persones.");
    }

    /**
     *
     */
    public static void delPersona() {
        // Cerquem persones a eliminar
        List<Persona> resultatCerca;
        boolean resultatIncorrecte;
        do {
            resultatIncorrecte = false;
            resultatCerca = searchPersona();

            if (resultatCerca == null || resultatCerca.size() == 0) {
                System.out.println("No hi ha cap persona definida per eliminar.");
                resultatIncorrecte = true;
            }

            if (generatePreguntaSN("Vols tornar a cercar? (S/N): ")) return;

        } while (resultatIncorrecte);

        // Eliminem persones de la BD
        boolean delCorrecte = true;
        for (Persona p : resultatCerca) {
            if (!new PersonaDAO().delete(p)) {
                delCorrecte = false;
                System.out.println("No s'ha pogut eliminar la persona amb id " + p.getId() + ".");
            }
        }

        // Mostrem missatge d'èxit
        if (delCorrecte) System.out.println("S'han eliminat correctament les persones.");
    }

    /**
     * Executa el procés per llistar els registres de la taula persones
     */
    public static void llistarPersones() {
        List<Persona> resultat = new PersonaDAO().all();
        if (resultat.size() > 0) {
            System.out.println("S'han trobat " + resultat.size() + " persones:");
            for (Persona p : resultat) {
                System.out.println(p);
            }
        } else System.out.println("No s'ha trobat cap persona.");
    }


    /**
     * Executa el procés per mostrar el recompte de la taula persones
     */
    public static void ferRecompte() {
        long recompte = new PersonaDAO().count();
        System.out.println("Hi ha " + recompte + " persones a la base de dades.");
    }
}
