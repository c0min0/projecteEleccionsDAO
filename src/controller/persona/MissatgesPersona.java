package controller.persona;

import controller.Missatges;

public class MissatgesPersona extends Missatges {
    // Missatge per tornar a fer acció sobre la taula persones
    static final String MSG_REPEAT_PERSONES = MSG_REPEAT_CRUD + "PERSONES (S/N)?: ";

    // Missatge d'èxit

    // Condicions per als camps de la taula persones
    static final String PERSONA_ID_CONDITION = LONG_CONDITION;
    static final String NOM_CONDITION = VARCHAR30_CONDITION;
    static final String COG1_CONDITION = VARCHAR30_CONDITION;
    static final String COG2_CONDITION = VARCHAR30_CONDITION;
    static final String SEXE_CONDITION = "ser 'M' o 'F'.";
    static final String DNI_CONDITION = CHAR8_CONDITION;
    static final String DATA_NAIX_CONDITION = DATE_CONDITION;
}
