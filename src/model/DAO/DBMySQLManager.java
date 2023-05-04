package model.DAO;
import java.sql.*;
import java.util.List;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

public class DBMySQLManager {
    // Propietats
    private static Connection conn = null;
    private String driver = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
    private String url;
    private String usuari ="perepi";
    private String contrasenya = "pastanaga";
    private String host = "10.2.179.196"; //IP de qui executi el programa
    private String base_dades = "eleccions2016"; // PROVA: eleccions2017, BONA: eleccions2016

    // Constructors
    private DBMySQLManager(){

        this.url = "jdbc:mysql://" + host + ":3306/" + base_dades;

        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuari, contrasenya);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    // Mètodes
    public static Connection getConnection() {

        if (conn == null){
            new DBMySQLManager();
        }

        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static List<Object[]> read (String query, Object ... params) {
        List<Object[]> result = null;

        try {

            Connection con = DBMySQLManager.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {

                switch (params[i].getClass().getSimpleName()) {
                    case "Character" -> ps.setString(i + 1, params[i].toString());
                    case "String" -> ps.setString(i + 1, (String) params[i]);
                    case "Integer" -> ps.setInt(i + 1, (Integer) params[i]);
                    case "Float" -> ps.setFloat(i + 1, (Float) params[i]);
                    case "Double" -> ps.setDouble(i + 1, (Double) params[i]);
                    case "Date" -> ps.setDate(i + 1, (Date) params[i]);
                }
            }

            ResultSet rs = ps.executeQuery(); //TODO: provar el .getObject()

            int l = rs.getMetaData().getColumnCount();

            while(rs.next()) {
                Object[] row = new Object[l];

                for (int i = 0; i < l; i++) {
                    row[i] = rs.getObject(i + 1);
                }

                result.add(row);
            }

            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBMySQLManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static int write (String query, Object ... params) {
        int result = 0;

        try {

            Connection con = DBMySQLManager.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {

                switch (params[i].getClass().getSimpleName()) {
                    case "Character" -> ps.setString(i + 1, params[i].toString());
                    case "String" -> ps.setString(i + 1, (String) params[i]);
                    case "Integer" -> ps.setInt(i + 1, (Integer) params[i]);
                    case "Float" -> ps.setFloat(i + 1, (Float) params[i]);
                    case "Double" -> ps.setDouble(i + 1, (Double) params[i]);
                    case "Date" -> ps.setDate(i + 1, (Date) params[i]);
                }
            }

            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBMySQLManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}