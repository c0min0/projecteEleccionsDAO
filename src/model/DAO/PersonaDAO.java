package model.DAO;

import model.Persona;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static model.DAO.DBMySQLManager.getConnection;
import static org.apache.tools.ant.util.FileUtils.close;

public class PersonaDAO implements DAODB<Persona> {
    @Override
    public boolean create(Persona persona) {
        return false;
    }

    @Override
    public boolean read(Persona p) {
        Persona pr = read(p.getId());
        if (pr == null) return false;
        p.set(pr.getNom(), pr.getCog1(), pr.getCog2(), pr.getSexe(), pr.getDataNaixement(), pr.getDni());
        return true;
    }

    public Persona read(int id) {
        String query = "SELECT nom,cog1,cog2,sexe,data_naixement,dni FROM persones WHERE id=?";
        List<Object[]> r = DBMySQLManager.read(query, new String[]{String.valueOf(id), "Int"});
        if (r == null || r.size() != 1) return null;
        Object[] row = r.iterator().next();
        return new Persona(id, (String)row[0], (String)row[1], (String)row[2],
                ((String)row[3]).charAt(0), Date.valueOf((String)row[4]), (String)row[5]);
    }

    @Override
    public boolean update(Persona persona, String opcio) {
        String query = "UPDATE persones SET " + opcio + "= ? WHERE persona_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement(query);
            switch (opcio){
                case "nom":
                    if (persona.getNom().length() > 30) {
                        System.out.println("La llargada ha de ser de menys de 31");
                    break;
                    }
                    stmt.setString(1, persona.getNom());
                    break;
                case "cog1":
                    if (persona.getCog1().length() > 30) {
                        System.out.println("La llargada ha de ser de menys de 31");
                    }
                    stmt.setString(1, persona.getCog1());
                    break;
                case "cog2":
                    if (persona.getCog2().length() > 30) {
                        System.out.println("La llargada ha de ser de menys de 31");
                    }
                    stmt.setString(1, persona.getCog2());
                    break;
                case "valueN" :
                    // secuencia de sentencias.
                    break;
                default:
                    // Default secuencia de sentencias.
            }

            stmt.setInt(1, persona.getId());
            //return stmt.executeUpdate(); comentado para que no de error y poder hacer Commit & Push
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }
        return false; //esto está para que no de error el método y poder hacer Commit & Push
    }

    @Override
    public boolean delete(Persona persona) {
        return false;
    }

    @Override
    public boolean exists(Persona persona) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Persona> all() {
        return null;
    }
}
