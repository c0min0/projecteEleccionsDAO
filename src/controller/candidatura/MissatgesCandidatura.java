package controller.candidatura;

import controller.Missatges;

public class MissatgesCandidatura extends Missatges {
    // Missatge per tornar a fer acció sobre la taula candidatures
    public static final String MSG_REPEAT_CANDIDATURES = MSG_REPEAT_CRUD + "CANDIDATURES (S/N)?: ";

    // Condicions per als camps de la taula candidatures
    static final String CANDIDATURA_ID_CONDITION = LONG_CONDITION;
    static final String ELECCIO_ID_CONDITION = INT_CONDITION;
    static final String CODI_CANDIDATURA_CONDITION = CHAR6_CONDITION;
    static final String NOM_CURT_CONDITION = VARCHAR50_CONDITION;
    static final String NOM_LLARG_CONDITION = VARCHAR150_CONDITION;
    static final String CODIAC_PROV_CONDITON = CHAR6_CONDITION;
    static final String CODIAC_CA_CONDITON = CHAR6_CONDITION;
    static final String CODIAC_NACIONAL_CONDITON = CHAR6_CONDITION;
}
