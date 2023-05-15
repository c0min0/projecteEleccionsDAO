package controller;

/**
 * Classe que conté el catàleg de missatges que es mostren per pantalla.
 */
public class Missatges {
    // Missatges d'error
    public static final String MSG_ERR_OP = "Opció incorrecte, torna a provar-ho.";

    // Missatge d'inici
    public static final String MSG_INIT = "Benvingut al programa per gestionar la base de dades de les eleccions!";

    // Missatges per repetir acció
    public static final String MSG_REPEAT_TAULA = "Vols realitzar alguna tasca més sobre una altra TAULA (S/N)?: ";
    public static final String MSG_REPEAT_CRUD = "Vols realitzar alguna tasca més sobre la taula ";

    // Missatge per introduïr els camps obligatoris
    public static final String MSG_CAMPS_OBLIG = "Primer cal introduïr els camps obligatoris ";

    // Missatge per afegir més camps en inserir registres
    public static final String MSG_AFEGIR_CAMPS = "Vols INSERIR algun camp més o MODIFICAR els que has introduït? (S/N): ";

    // Missatge per mostrar les dades introduïdes
    public static final String MSG_MOSTRAR_DADES = "Les dades introduïdes són les següents:";

    //Missatge per avisar que sense resultat de cerca no es pot fer l'acció
    public static final String MSG_NO_RESULT = "Sense resultat de cerca no es pot ";

    // Condicions genèriques
    public static final String INT_CONDITION = "ser un número enter positiu.";
    public static final String LONG_CONDITION = INT_CONDITION;
    public static final String VARCHAR30_CONDITION = "ser un text de màxim 30 caràcters.";
    public static final String VARCHAR45_CONDITION = "ser un text de màxim 45 caràcters.";
    public static final String VARCHAR50_CONDITION = "ser un text de màxim 50 caràcters.";
    public static final String VARCHAR150_CONDITION = "ser un text de màxim 150 caràcters.";
    public static final String CHAR2_CONDITION = "ser un text de 2 caràcters.";
    public static final String CHAR6_CONDITION = "ser un text de 6 caràcters.";
    public static final String CHAR8_CONDITION = "ser un text de 8 caràcters.";
    public static final String DATE_CONDITION = "ser una data amb el format dd/mm/aaaa.";

}
