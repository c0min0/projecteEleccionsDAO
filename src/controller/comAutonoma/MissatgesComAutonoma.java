package controller.comAutonoma;

import controller.Missatges;

public class MissatgesComAutonoma extends Missatges {
    // Missatge per tornar a fer acció sobre la taula comunitats_autonomes
    public static final String MSG_REPEAT_CA = MSG_REPEAT_CRUD + "COMUNITATS AUTÒNOMES (S/N)?: ";

    // Condicions per als camps de la taula comunitats_autonomes
    public static final String CA_ID_CONDITION = INT_CONDITION;
    public static final String CA_NOM_CONDITION = VARCHAR45_CONDITION;
    public static final String CA_CODI_INE_CONDITION = CHAR2_CONDITION;
}
