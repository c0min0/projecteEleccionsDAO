package controller.Persona;

import controller.Missatges;

public class MissatgesPersona extends Missatges {
    static final String DATA_NAIX_CONDITION = DATE_CONDITION;
    static final String MSG_REPEAT_PERSONES = MSG_REPEAT_CRUD + "PERSONES (S/N)?: ";
    // Condicions per als camps de la taula persones
    static final String NOM_CONDITION = VARCHAR30_CONDITION;
    static final String COG1_CONDITION = VARCHAR30_CONDITION;
    static final String COG2_CONDITION = VARCHAR30_CONDITION;
    static final String SEXE_CONDITION = "ser 'M' o 'F'.";
    static final String DNI_CONDITION = "tenir 8 caràcters en aquesta base de dades.";
}
