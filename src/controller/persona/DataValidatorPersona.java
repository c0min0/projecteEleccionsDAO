package controller.persona;

import controller.DataValidator;

/**
 * Classe que conté els validadors de dades de la taula persones.
 */
public class DataValidatorPersona extends DataValidator {

    /**
     * Comprova si un String és un valor vàlid pel camp nom de la taula persones.
     * @param nom String a comprovar.
     * @return True si és un valor vàlid pel camp nom, false si no ho és.
     */
    static boolean isNom(String nom) {
        return isVarchar30(nom);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp cog1 de la taula persones.
     * @param cog1 String a comprovar.
     * @return True si és un valor vàlid pel camp primer cognomcog1, false si no ho és.
     */
    static boolean isCog1(String cog1) {
        return isVarchar30(cog1);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp cog2 de la taula persones.
     * @param cog2 String a comprovar.
     * @return True si és un valor vàlid pel camp cog2, false si no ho és.
     */
    static boolean isCog2(String cog2) {
        return isVarchar30(cog2);
    }

    /**
     * Comprova si un String és un valor vàlid pel camp sexe de la taula persones.
     * @param sexe String a comprovar.
     * @return True si és un valor vàlid pel camp sexe, false si no ho és.
     */
    static boolean isSexe(String sexe) {
        return sexe.equals("M") || sexe.equals("F") || sexe.equals("m") || sexe.equals("f");
    }

    /**
     * Comprova si un String és un valor vàlid pel camp data_naixement de la taula persones.
     * @param dataNaix String a comprovar.
     * @return True si és un valor vàlid pel camp data_naixement, false si no ho és.
     */
    static boolean isDataNaix(String dataNaix) {
        return isDate(dataNaix);
    }

    /**
     * Comprova si un String és un valor vàlid pel dni de la taula persones.
     * @param dni String a comprovar.
     * @return True si és un valor vàlid pel camp dni, false si no ho és.
     */
    static boolean isDNI(String dni) {
        return isChar8(dni);
    }
}
