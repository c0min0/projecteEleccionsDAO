package controller;

import java.sql.Date;

/**
 * Classe que conté mètodes per convertir dades.
 */
public class DataConverter {
    /**
     * Converteix una data en format String (dd/mm/aaa) a un objecte Date.
     * @param data Data en format (dd/mm/aaaa).
     * @return Data en format Date.
     */
    public static Date toDate(String data) {
        if (data == null) return null;
        String[] parts = data.split("/");
        int dia = Integer.parseInt(parts[0]);
        int mes = Integer.parseInt(parts[1]);
        int any = Integer.parseInt(parts[2]);

        // Cal restar 1900 a l'any i 1 al mes perquè Date comença a comptar des de 1900 i el mes 0.
        return new Date(any - 1900, mes - 1, dia);
    }

    /**
     * Converteix una data en format Date a un String (dd/mm/aaaa).
     * @param data Data en format Date.
     * @return Data en format String (dd/mm/aaaa).
     */
    public static String toString(Date data) {
        return data.getDate() + "/" + (data.getMonth() + 1) + "/" + (data.getYear() + 1900);
    }
}
