package controller;

import java.sql.Date;

public class Controller {
    /**
     * Missatge d'error per a la validació de l'ID.
     */
    static final String ERR_MSG_ID = "L'id ha de ser un número enter positiu.";

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
     * Comprova si un String és un VARCHAR(30)
     * @param str String a comprovar.
     * @return True si és un VARCHAR(30), false si no ho és.
     */
    public static boolean isVarchar30(String str) {
        return str.length() <= 30;
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
     * Valida una data comprovant que tingui el format dd/mm/aaaa.
     * @param data Data a validar.
     * @return True si la data té el format dd/mm/aaaa, false si no.
     */
    public static boolean isDate(String data) {
        return data.matches("([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/(19|20)[0-9]{2}");
    }
}
