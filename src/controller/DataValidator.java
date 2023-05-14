package controller;

/**
 * Classe que conté els validadors de dades genèrics.
 */
public class DataValidator {
    /**
     * Comprova si un String és un long.
     * @param str String a comprovar.
     * @return True si és un long, false si no ho és.
     */
    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Comprova si un String és un VARCHAR(30).
     * @param str String a comprovar.
     * @return True si és un VARCHAR(30), false si no ho és.
     */
    public static boolean isVarchar30(String str) {
        return str.length() <= 30;
    }
    public static boolean isChar6(String str) {
        return str.length() <= 6;
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
     * Comprova si un String és un id vàlid.
     * @param str String a comprovar.
     * @return True si és un id vàlid, false si no ho és.
     */
    public static boolean isId(String str) {
        return isLong(str);
    }

}
