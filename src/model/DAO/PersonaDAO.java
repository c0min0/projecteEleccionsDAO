package model.DAO;

import model.Persona;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PersonaDAO implements DAODB<Persona> {
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
        Persona pr = read(p.getId());
        if (pr == null) return false;
        p.set(pr.getNom(), pr.getCog1(), pr.getCog2(), pr.getSexe(), pr.getDataNaixement(), pr.getDni());
        return true;
    }

    public Persona read (int id) {
        String query = "SELECT nom,cog1,cog2,sexe,data_naixement,dni FROM persones WHERE id=?";
        List<Object[]> r = DBMySQLManager.read(query, id);
        if (r == null || r.size() != 1) return null;
        Object[] row = r.iterator().next();
        return new Persona(id, (String)row[0], (String)row[1], (String)row[2],
                ((String)row[3]).charAt(0), Date.valueOf((String)row[4]), (String)row[5]);
    }

    @Override
    public boolean update(Persona p) {
        String query = "UPDATE persones SET nom=?,cog1=?,cog2=?,sexe=?,data_naixement=?,dni=? WHERE id=?";
        String[] nom = {p.getNom(), "String"};
        String[] cog1 = {p.getCog1(), "String"};
        String[] cog2 = {p.getCog2(), "String"};
        String[] sexe = {String.valueOf(p.getSexe()), "String"};
        String[] dataNaixement = {p.getDataNaixement().toString(), "Date"};
        String[] dni = {p.getDni(), "String"};
        int r = DBMySQLManager.write(query, nom, cog1, cog2, sexe, dataNaixement, dni);
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
                case "sexe" -> params[i] = String.valueOf(p.getSexe());
                case "data_naixement" -> params[i] = p.getDataNaixement();
                case "dni" -> params[i] = p.getDni();
                default -> throw new IllegalArgumentException("El camp " + camps[i] + " no existeix");
            }

        }
        query.append(" WHERE id=?");

        int r = DBMySQLManager.write(query.toString(), params);

        return r > 0;
    }

    @Override
    public boolean delete(Persona p) {
        String query = "DELETE FROM persones WHERE id=?";
        int r = DBMySQLManager.write(query, p.getId());
        return r > 0;
    }

    @Override
    public boolean exists(Persona p) {
        return read(p);
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM persones";
        List<Object[]> r = DBMySQLManager.read(query);

        if (r == null || r.size() != 1) return -1;

        Object[] o = r.iterator().next();

        return (int)o[0];
    }

    @Override
    public List<Persona> all() {
        List<Persona> l = new LinkedList<>();

        String query = "SELECT id,nom,cog1,cog2,sexe,data_naixement,dni FROM persones";
        List<Object[]> r = DBMySQLManager.read(query);

        Iterator<Object[]> it = r.iterator();
        while (it.hasNext()) {
            Object[] row = it.next();
            l.add(new Persona((int)row[0], (String)row[1], (String)row[2], (String)row[3],
                    ((String)row[4]).charAt(0), Date.valueOf((String)row[5]), (String)row[6]));
        }
        return l;
    }
}
