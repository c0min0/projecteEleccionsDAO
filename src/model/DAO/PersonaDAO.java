package model.DAO;

import model.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.apache.tools.ant.util.FileUtils.close;
import static recursos.exercicisProgBD.importacio.DBMySQLManager.getConnection;

public class PersonaDAO implements DAODB<Persona>{
    @Override
    public boolean create(Persona persona) {
return false;
    }

    @Override
    public boolean read(Persona persona) {
        return false;
    }

    @Override
    public int update(Persona persona, String opcio) {
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

            stmt.setInt(1, persona.getPersona_id());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(conn);
        }
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
