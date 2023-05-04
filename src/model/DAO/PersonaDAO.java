package model.DAO;

import model.Persona;

import java.sql.Date;
import java.util.List;

public class PersonaDAO implements DAODB<Persona>{
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
       // int r = SQLRW.update(query, persona.getPersona_id());
    //    return r > 0;
        return false;
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
