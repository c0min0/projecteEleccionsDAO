package controller.DAO.MySQL;

import model.DAO.DAODB;
import model.Persona;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PersonaDAO implements DAODB<Persona, Long> {
    @Override
    public boolean create(Persona p) {
        String query = "INSERT INTO persones (nom,cog1,cog2,sexe,data_naixement,dni) VALUES (?,?,?,?,?,?)";
        int r = DBMySQLManager.write(query,
                                        p.getNom(),
                                        p.getCog1(),
                                        p.getCog2(),
                                        p.getSexe(),
                                        p.getDataNaixement(),
                                        p.getDni());
        return r > 0;
    }

    @Override
    public boolean read (Persona p) {
        Persona pr = readById(p.getId());
        if (pr == null) return false;
        p.set(pr.getNom(), pr.getCog1(), pr.getCog2(), pr.getSexe(), pr.getDataNaixement(), pr.getDni());
        return true;
    }

    public Persona readById (Long id) {
        String query = "SELECT nom,cog1,cog2,sexe,data_naixement,dni FROM persones WHERE persona_id=?";
        List<Object[]> r = DBMySQLManager.read(query, id);
        if (r.size() != 1L) return null;
        Object[] row = r.iterator().next();

        String nom = (String)row[0];
        String cog1 = (String)row[1];
        String cog2 = (String)row[2];
        String sexe = (String)row[3];
        Date dataNaixement = (row[4] == null ? null : (Date) row[4]);
        String dni = (String)row[5];

        return new Persona(id, nom, cog1, cog2, sexe, dataNaixement, dni);
    }

    @Override
    public boolean update(Persona p) {
        String query = "UPDATE persones SET nom=?,cog1=?,cog2=?,sexe=?,data_naixement=?,dni=? WHERE persona_id=?";
        String nom = p.getNom();
        String cog1 = p.getCog1();
        String cog2 = p.getCog2();
        String sexe = String.valueOf(p.getSexe());
        Date dataNaixement = p.getDataNaixement();
        String dni = p.getDni();
        long persona_id = p.getId();

        int r = DBMySQLManager.write(query, nom, cog1, cog2, sexe, dataNaixement, dni, persona_id);
        return r > 0;
    }

    @Override
    public boolean update(Persona p, String... camps) {
        Object[] params = new Object[camps.length + 1];

        StringBuilder query = new StringBuilder("UPDATE persones SET ");

        for (int i = 0; i < camps.length; i++) {
            query.append(camps[i]).append("=?");
            if (i < camps.length - 1) query.append(",");

            switch (camps[i]) {
                case "nom" -> params[i] = p.getNom();
                case "cog1" -> params[i] = p.getCog1();
                case "cog2" -> params[i] = p.getCog2();
                case "sexe" -> params[i] = p.getSexe();
                case "data_naixement" -> params[i] = p.getDataNaixement();
                case "dni" -> params[i] = p.getDni();
                default -> throw new IllegalArgumentException("El camp " + camps[i] + " no existeix");
            }

        }
        query.append(" WHERE persona_id=?");
        params[params.length - 1] = p.getId();

        int r = DBMySQLManager.write(query.toString(), params);

        return r > 0;
    }

    @Override
    public boolean delete(Persona p) {
        String query = "DELETE FROM persones WHERE persona_id=?";
        int r = DBMySQLManager.write(query, p.getId());
        return r > 0;
    }

    @Override
    public boolean exists(Persona p) {
        return read(p);
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM persones";
        List<Object[]> r = DBMySQLManager.read(query);

        if (r.size() != 1) return -1;

        Object[] o = r.iterator().next();

        return (long)o[0];
    }

    @Override
    public List<Persona> all() {
        List<Persona> l = new LinkedList<>();

        String query = "SELECT persona_id,nom,cog1,cog2,sexe,data_naixement,dni FROM persones";
        List<Object[]> r = DBMySQLManager.read(query);

        Iterator<Object[]> it = r.iterator();
        while (it.hasNext()) {
            Object[] row = it.next();
            long id = (long)row[0];
            String nom = (String)row[1];
            String cog1 = (String)row[2];
            String cog2 = (String)row[3];
            String sexe = (String)row[4];
            Date dataNaixement = (row[5] == null ? null : (Date) row[5]);
            String dni = (String)row[6];

            l.add(new Persona(id, nom, cog1, cog2, sexe, dataNaixement, dni));
        }
        return l;
    }
}
