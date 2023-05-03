package exercicis.taules.DAO;

import exercicis.taules.Municipi;

import java.util.List;

public class MunicipiDAO implements DAODB<Municipi> {
    @Override
    public boolean create(Municipi municipi) {
        return false;
    }

    @Override
    public boolean read(Municipi municipi) {
        return false;
    }

    @Override
    public boolean update(Municipi municipi) {
        return false;
    }

    @Override
    public boolean delete(Municipi municipi) {
        return false;
    }

    @Override
    public boolean exists(Municipi municipi) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Municipi> all() {
        return null;
    }
}
