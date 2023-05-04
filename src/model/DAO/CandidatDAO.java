package model.DAO;

import model.Candidat;

import java.util.List;

public class CandidatDAO implements DAODB<Candidat> {
    @Override
    public boolean create(Candidat candidat) {
        return false;
    }

    @Override
    public boolean read(Candidat candidat) {
        return false;
    }

    @Override
    public boolean update(Candidat candidat) {
        return false;
    }

    @Override
    public boolean delete(Candidat candidat) {
        return false;
    }

    @Override
    public boolean exists(Candidat candidat) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Candidat> all() {
        return null;
    }
}
