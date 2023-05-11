package controller.DAO.MySQL;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

public class DBMySQLManager {
    // Ips
    String ipLocalVic = "192.168.75.129";
    String ipIsard = "10.2.179.196";
    String getIpLocalBen = "192.168.136.129";

    // Propietats
    private static Connection conn = null;
    private String driver = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
    private String url;
    private String usuari ="perepi";
    private String contrasenya = "pastanaga";
    private String host = getIpLocalBen; //IP de qui executi el programa
    private String base_dades = "practicaDAO_VicBen"; // PROVA: eleccions2017, BONA: eleccions2016

    // Constructors
    private DBMySQLManager(){

        this.url = "jdbc:mysql://" + host + ":3306/" + base_dades + "?serverTimezone=UTC";

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuari, contrasenya);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    // Mètodes

    /**
     * Obté la connexió a la base de dades
     * @return connexió a la base de dades
     * @throws SQLException si no es pot connectar
     */
    public static Connection getConnection() throws SQLException {

        if (conn == null){
            new DBMySQLManager();
        } else if (conn.isClosed()){
            new DBMySQLManager();
        }

        return conn;
    }

    /**
     * Tanca la connexió a la base de dades
     * @throws SQLException si no es pot tancar
     */
    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Llegeix de la base de dades
     * @param query consulta SQL
     * @param params paràmetres de la consulta
     * @return llista d'objectes amb els resultats de la consulta.
     * Cada element de la llista és un array d'objectes amb els valors de cada columna
     */
    public static List<Object[]> read (String query, Object ... params) {
        List<Object[]> result = new LinkedList<>();

        try {
            // Obtenim la connexió
            Connection con = DBMySQLManager.getConnection();

            // Preparem la consulta
            PreparedStatement ps = con.prepareStatement(query);

            // Per cada paràmetre assignem el valor corresponent segons el tipus
            for (int i = 0; i < params.length; i++) {

                switch (params[i].getClass().getSimpleName()) {
                    case "Character" -> ps.setString(i + 1, params[i].toString());
                    case "String" -> ps.setString(i + 1, (String) params[i]);
                    case "Integer" -> ps.setInt(i + 1, (Integer) params[i]);
                    case "Long" -> ps.setLong(i + 1, (Long) params[i]);
                    case "Float" -> ps.setFloat(i + 1, (Float) params[i]);
                    case "Double" -> ps.setDouble(i + 1, (Double) params[i]);
                    case "Date" -> ps.setDate(i + 1, (Date) params[i]);
                }
            }

            // Executem la consulta
            ResultSet rs = ps.executeQuery();
            //TODO: provar el .getObject()

            // Obtenim el número de columnes
            int l = rs.getMetaData().getColumnCount();

            // Per cada registre
            while(rs.next()) {
                // Creem un array d'objectes amb el número de columnes
                Object[] row = new Object[l];

                // Per cada columna del registre
                for (int i = 0; i < l; i++) {
                    // Afegim el valor de la columna a l'array
                    row[i] = rs.getObject(i + 1);
                }

                // Afegim l'array d'objectes a la llista
                result.add(row);
            }
            // Tanquem els recursos
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } finally {
            try {
                // Tanquem la connexió
                DBMySQLManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Escriu a la base de dades
     * @param query consulta SQL
     * @param params paràmetres de la consulta
     * @return nombre de registres afectats
     */
    public static int write (String query, Object ... params) {
        //resultat de la consulta
        int result = 0;

        try {
            // Obtenim la connexió
            Connection con = DBMySQLManager.getConnection();

            // Preparem la consulta
            PreparedStatement ps = con.prepareStatement(query);

            // Per cada paràmetre
            for (int i = 0; i < params.length; i++) {

                // Si el paràmetre és null, assignem segons el tipus NULL
                // Això és un filtre per evitar excepcions en fer param[i].getClass()
                if (params[i] == null) ps.setNull(i + 1, Types.NULL);

                // Si el paràmetre conté la cadena "null", assignat per la lectura d'un camp NULL a la base de dades,
                    // assignem el tipus NULL
                else if (params[i].equals("null")) ps.setNull(i + 1, Types.NULL);

                // Si no, assignem el valor corresponent segons el tipus
                else {
                    switch (params[i].getClass().getSimpleName()) {
                        case "Character" -> ps.setString(i + 1, params[i].toString());
                        case "String" -> ps.setString(i + 1, (String) params[i]);
                        case "Integer" -> ps.setInt(i + 1, (Integer) params[i]);
                        case "Long" -> ps.setLong(i + 1, (Long) params[i]);
                        case "Float" -> ps.setFloat(i + 1, (Float) params[i]);
                        case "Double" -> ps.setDouble(i + 1, (Double) params[i]);
                        case "Date" -> ps.setDate(i + 1, (Date) params[i]);
                    }
                }
            }

            // Executem la consulta
            result = ps.executeUpdate();

            // Tanquem el PreparedStatement
            ps.close();

        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } finally {
            try {
                // Tanquem la connexió
                DBMySQLManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}