package exercicis.taules.DAO;

import exercicis.taules.Persona;

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
    public boolean update(Persona persona) {
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
