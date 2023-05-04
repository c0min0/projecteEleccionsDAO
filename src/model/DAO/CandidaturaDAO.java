package model.DAO;

import model.Candidatura;

import java.util.List;

public class CandidaturaDAO implements DAODB<Candidatura> {
    @Override
    public boolean create(Candidatura candidatura) {
        String query = "INSERT INTO candidatures(candidatura_id,eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional) VALUES (?,?,?,?,?,?,?,?)";
        //int r = SQLRW.write(query, candidatura.getCandidatura_id(), candidatura.getCodi_candidatura(), candidatura.getNom_curt(),candidatura.getNom_llarg(),candidatura.getCodi_acumulacio_provincia(),candidatura.getCodi_acumulacio_ca(),candidatura.getCodi_acumulacio_nacional());
        //return r > 0;
        return false;
    }

    @Override
    public boolean read(Candidatura candidatura) {
        return false;
    }

    @Override
    public boolean update(Candidatura candidatura, String opcio) {
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
