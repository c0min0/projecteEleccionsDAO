package exercicis.taules.DAO;

import exercicis.taules.Provincia;

import java.util.List;

public class ProvinciaDAO implements DAODB<Provincia> {
    @Override
    public boolean create(Provincia provincia) {
        return false;
    }

    @Override
    public boolean read(Provincia provincia) {
        return false;
    }

    @Override
    public boolean update(Provincia provincia) {
        return false;
    }

    @Override
    public boolean delete(Provincia provincia) {
        return false;
    }

    @Override
    public boolean exists(Provincia provincia) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Provincia> all() {
        return null;
    }
}
