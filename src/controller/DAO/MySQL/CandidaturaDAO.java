package controller.DAO.MySQL;

import model.Candidatura;
import model.DAO.DAODB;
import model.Candidatura;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CandidaturaDAO implements DAODB<Candidatura, Long> {
    @Override
    public boolean create(Candidatura c) {
        // INSERT SQL
        String query = "INSERT INTO candidats (eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional) VALUES (?,?,?,?,?)";

        // Escrivim a la BD amb els valors de la candidatura passada per paràmetres
        int r = DBMySQLManager.write(query,
                c.getEleccioId(),
                c.getCodiCandidatura(),
                c.getNomCurt(),
                c.getNomLlarg(),
                c.getCodiAcumulacioProvincia(),
                c.getCodiAcumulacioCA(),
                c.getCodiAcumulacioNacional());

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean read(Candidatura c) {
        // Obtenim una candidatura de la BD amb el mateix id que la candidatura passada per paràmetres
        Candidatura cdr = readById(c.getId());

        // Si no existeix, retornem false
        if (cdr == null) return false;

        // Si existeix, actualitzem la candidatura passada per paràmetres amb els valors de la candidatura de la BD
        c.set(cdr.getEleccioId(),cdr.getCodiCandidatura(),cdr.getNomCurt(), cdr.getNomLlarg(), cdr.getCodiAcumulacioProvincia(), cdr.getCodiAcumulacioCA(), cdr.getCodiAcumulacioNacional());

        // i retornem true
        return true;

    }

    @Override
    public Candidatura readById(Long id) {
        // SELECT SQL
        String query = "SELECT eleccio_id, codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional FROM candidatures WHERE candidatura_id=?";

        // Llegim de la BD amb l'id de la candidatura passada per paràmetres
        List<Object[]> r = DBMySQLManager.read(query, id);

        // Si no existeix, retornem null
        if (r.size() != 1L) return null;

        // Si existeix, retornem la candidatura
        Object[] row = r.iterator().next();

        Long eleccio_id = (Long)row[0];
        String codi_candidatura = (String)row[1];
        String nom_curt = (String)row[2];
        String nom_llarg = (String)row[3];
        String codi_acumulacio_provincia = (String)row[4];
        String codi_acumulacio_ca = (String)row[5];
        String codi_acumulacio_nacional = (String)row[6];

        return new Candidatura(id, eleccio_id, codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional);
    }

    @Override
    public boolean update(Candidatura c) {
        // UPDATE SQL
        String query = "UPDATE candidatures SET eleccio_id=?,codi_candidatura=? ,nom_curt=?,nom_llarg=?,codi_acumulacio_provincia=?,codi_acumulacio_ca=?,codi_acumulacio_nacional=? WHERE candidatura_id=?";

        // Actualitzem la BD amb els valors de la candidatura passada per paràmetres
        Long eleccio_id = c.getEleccioId();
        String codi_candidatura = c.getCodiCandidatura();
        String nom_curt = c.getNomCurt();
        String nom_llarg = c.getNomLlarg();
        String codi_acumulacio_provincia = c.getCodiAcumulacioProvincia();
        String codi_acumulacio_ca = c.getCodiAcumulacioCA();
        String codi_acumulacio_nacional = c.getCodiAcumulacioNacional();
        long candidatura_id = c.getId();

        int r = DBMySQLManager.write(query, eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional, candidatura_id);

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean update(Candidatura c, String... camps) {
        // Creem un array de paràmetres de la mida de la quantitat de camps + 1 (per l'id)
        Object[] params = new Object[camps.length + 1];

        // Construïm la query
        StringBuilder query = new StringBuilder("UPDATE candidatures SET ");

        // Per cada camp,
        for (int i = 0; i < camps.length; i++) {

            // afegim el camp a la query
            query.append(camps[i]).append("=?");

            // i una coma si no és l'últim camp
            if (i < camps.length - 1) query.append(",");

            // També afegim el valor del camp a l'array de paràmetres
            switch (camps[i]) {
                case "eleccio_id" -> params[i] = c.getEleccioId();
                case "codi_candidatura" -> params[i] = c.getCodiCandidatura();
                case "nom_curt" -> params[i] = c.getNomCurt();
                case "nom_llarg" -> params[i] = c.getNomLlarg();
                case "codi_acumulacio_provincia" -> params[i] = c.getCodiAcumulacioProvincia();
                case "codi_acumulacio_ca" -> params[i] = c.getCodiAcumulacioCA();
                case "codi_acumulacio_nacional" -> params[i] = c.getCodiAcumulacioNacional();
                default -> throw new IllegalArgumentException("El camp " + camps[i] + " no existeix");
            }
        }

        // Afegim l'id al final de la query
        query.append(" WHERE candidatura_id=?");
        params[params.length - 1] = c.getId();

        // Executem la query
        int r = DBMySQLManager.write(query.toString(), params);

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean delete(Candidatura c) {
        // DELETE SQL
        String query = "DELETE FROM candidatures WHERE candidatura_id=?";

        int r = DBMySQLManager.write(query, c.getId());
        //
        return r > 0;
    }

    @Override
    public boolean exists(Candidatura c) {
        return read(c);
    }

    @Override
    public long count() {
        // SELECT SQL
        String query = "SELECT COUNT(*) FROM candidatures";

        //
        List<Object[]> r = DBMySQLManager.read(query);

        // Si la llargada no es 1, retornem -1
        if (r.size() != 1) return -1;

        Object[] o = r.iterator().next();

        return (long)o[0];
    }

    @Override
    public List<Candidatura> all() {
        List<Candidatura> l = new LinkedList<>();

        String query = "SELECT candidatura_id, eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional FROM candidatures";
        List<Object[]> r = DBMySQLManager.read(query);

        Iterator<Object[]> it = r.iterator();
        while (it.hasNext()) {
            Object[] row = it.next();
            long id = (long)row[0];
            long eleccio_id = (Long) row[1];
            String codi_candidatura = (String)row[2];
            String nom_curt = (String)row[3];
            String nom_llarg = (String)row[4];
            String codi_acumulacio_provincia = (String)row[5];
            String codi_acumulacio_ca = (String)row[6];
            String codi_acumulacio_nacional = (String)row[7];


            l.add(new Candidatura(id, eleccio_id, codi_candidatura ,nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca,codi_acumulacio_nacional));
        }
        // Retornem
        return l;
    }
}
