package model.DAO;

import model.Candidatura;

import java.sql.Date;
import java.util.List;

public class CandidaturaDAO implements DAODB<Candidatura> {
    @Override
    public boolean create(Candidatura candidatura) {
        String query = "INSERT INTO candidatures(candidatura_id,eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional) VALUES (?,?,?,?,?,?,?,?)";
        int r = Menu.write(query, candidatura.getCandidatura_id(), candidatura.getEleccio_id(), candidatura.getCodi_candidatura(), candidatura.getNom_curt(),candidatura.getNom_llarg(),candidatura.getCodi_acumulacio_provincia(),candidatura.getCodi_acumulacio_ca(),candidatura.getCodi_acumulacio_nacional());
        return r > 0;
        return false;
    }

    @Override
    public boolean read(Candidatura candidatura) {
        String query = "SELECT nom,dn,dep FROM candidatres WHERE id=?";
        List<Object[]> r = SQLRW.read(query, candidatura_id);
        if (r == null || r.size() != 1) return Boolean.parseBoolean(null);
        Object[] o = r.iterator().next();
        return new Candidatura(id, (String)o[0], Date.valueOf((String)o[1]), (int)o[2]);
    }
    @Override
    public boolean read(int id) {
        String query = "SELECT nom,dn,dep FROM " + taula + " WHERE id=?";
        List<Object[]> r = SQLRW.read(query, id);
        if (r == null || r.size() != 1) return null;
        Object[] o = r.iterator().next();
        return new Empleat(id, (String)o[0], Date.valueOf((String)o[1]), (int)o[2]);
    }

    @Override
    public int update(Candidatura candidatura, String opcio) {
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
