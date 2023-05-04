package model.DAO;
import java.sql.*;
import java.util.List;

import java.sql.Connection;

import java.sql.Statement;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

public class DBMySQLManager {
    //Conexió
    static String ip = "10.2.179.196";

    // Propietats
    private static Connection conn = null;
    private String driver = "com.mysql.cj.jdbc.Driver"; //com.mysql.jdbc.Driver
    private String url;
    private String usuari ="perepi";
    private String contrasenya = "pastanaga";
    private String host = ip; //IP de qui executi el programa
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

    public static List<Object[]> read (String query, String[] ... paramAndType) {
        List<Object[]> result = null;

        for (String[] param : paramAndType) {
            boolean correctType = param[1].equals("String") || param[1].equals("Int")
                    || param[1].equals("Double") || param[1].equals("Date");

            if (param.length != 2 && correctType) throw new RuntimeException("Error: parametre incorrecte");
        }

        try {

            Connection con = DBMySQLManager.getConnection();

            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < paramAndType.length; i++) {

                switch (paramAndType[i][1]) {
                    case "String":
                        ps.setString(i + 1, paramAndType[i][0]);
                        break;
                    case "Int":
                        ps.setInt(i + 1, Integer.parseInt(paramAndType[i][0]));
                        break;
                    case "Double":
                        ps.setDouble(i + 1, Double.parseDouble(paramAndType[i][0]));
                        break;
                    case "Date":
                        ps.setDate(i + 1, Date.valueOf(paramAndType[i][0]));
                        break;
                }
            }

            ResultSet rs = ps.executeQuery();

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
        }

        return result;
    }
}