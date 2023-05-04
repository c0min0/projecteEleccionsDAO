package model.DAO;

import model.Provincia;

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
    public int update(Provincia provincia, String opcio) {
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
