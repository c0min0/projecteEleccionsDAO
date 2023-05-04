package model.DAO;

import model.Persona;

import java.util.List;

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
