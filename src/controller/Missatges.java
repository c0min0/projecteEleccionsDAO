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

    // Condicions genèriques
    public static final String VARCHAR30_CONDITION = "ser un text de màxim 30 caràcters.";

    public static final String NOM_CONDITION2 = "ser un text.";
    public static final String ID_CONDITION = "ser un número enter positiu.";
    public static final String DATE_CONDITION = "ser una data amb el format dd/mm/aaaa.";

}
