package model.DAO;

import model.Candidatura;

import java.util.List;

public class CandidaturaDAO implements DAODB<Candidatura> {
    @Override
    public boolean create(Candidatura candidatura) {
        return false;
    }

    @Override
    public boolean read(Candidatura candidatura) {
        return false;
    }

    @Override
    public boolean update(Candidatura candidatura) {
        return false;
    }

    @Override
    public boolean delete(Candidatura candidatura) {
        return false;
    }

    @Override
    public boolean exists(Candidatura candidatura) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Candidatura> all() {
        return null;
    }
}
