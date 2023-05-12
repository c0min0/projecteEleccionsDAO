package controller;

import view.Print;

import java.sql.Date;
import java.util.Scanner;

import static view.Print.println;

public class Controller {
    /**
     * Scanner per llegir dades introduïdes per l'usuari
     */
    static Scanner scan = new Scanner(System.in);

    /**
     * Constant que conté el missatge d'error quan l'usuari introdueix una opció incorrecta
     */
    public static final String ERR_MSG_OP = "Opció incorrecte, torna a provar-ho.";

    /**
     * Constant que conté el missatge d'error quan l'usuari introdueix un id incorrecte
     */
    public static final String ERR_MSG_ID = "L'id ha de ser un número enter positiu.";

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

    /**
     * Comprova si un String és un id vàlid.
     * @param str String a comprovar.
     * @return True si és un id vàlid, false si no ho és.
     */
    public static boolean isId(String str) {
        return isLong(str);
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
    public static Date convertToDate(String data) {
        String[] parts = data.split("/");
        int dia = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]);
        int any = Integer.parseInt(parts[2]);
        return new Date(any - 1900, mes - 1, dia);
    }

    /**
     * Converteix una data en format Date a un String (dd/mm/aaaa).
     * @param data Data en format Date.
     * @return Data en format String (dd/mm/aaaa).
     */
    public static String convertToString(Date data) {
        return data.getDate() + "/" + (data.getMonth() + 1) + "/" + (data.getYear() + 1900);
    }

    /**
     * Demana una pregunta i retorna l'String de la resposta
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static String obtenirResposta(String pregunta) {
        Print.print(pregunta);
        return scan.nextLine().trim();
    }

    /**
     * Demana una pregunta booleana i retorna la resposta
     * o repeteix la pregunta fins que la resposta sigui correcta.
     * @param pregunta Pregunta a fer
     * @return Resposta de l'usuari
     */
    public static boolean obtenirRespostaSN(String pregunta) {
        String opcio;

        do {
            // Passem pregunta i obtenim la resposta
            opcio = obtenirResposta(pregunta).trim().toUpperCase();

            // Si és S o N, retornem 1 o 0 respectivament
            if (opcio.equals("S")) return true;
            else if (opcio.equals("N")) return false;

            // Si no és cap de les opcions, tornem a fer el bucle
            else println(ERR_MSG_OP);

        } while (true);
    }

    /**
     * Mètode que genera un menú amb les opcions passades per paràmetre
     * i retorna l'opció triada per l'usuari.
     * @param pregunta Pregunta a fer
     * @param escape Opció per sortir del menú
     * @param opcio Opcions a mostrar
     * @return Opció triada per l'usuari
     */
    public static int obtenirOpMenu (String pregunta, String escape, String... opcio) {

        String opTriada;

        do {

            // Fem la pregunta i mostrem les opcions
            println(pregunta);
            for (int i = 0; i < opcio.length; i++) {
                Print.print((i + 1) + ". " + opcio[i]);
            }
            Print.print("0. " + escape);

            // Demanem opcio a l'usuari
            Print.print("\nTria una opció: ");

            opTriada = scan.nextLine().trim();

            // Retornem l'opció triada
            for (int i = 1; i <= opcio.length; i++) {
                if (opTriada.equals(String.valueOf(i))) return i;
            }

            if (opTriada.equals("0")) return 0;

            // Si no és cap de les opcions, tornem a fer el bucle
            println(ERR_MSG_OP);

        } while (true);
    }
}
