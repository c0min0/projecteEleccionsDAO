package controller.DAO.mySQL;

import controller.DAO.DAODB;
import model.Candidatura;

import java.util.LinkedList;
import java.util.List;

public class CandidaturaDAO implements DAODB<Candidatura> {

    /**
     * Insereix una candidatura a la BD amb els valors de la candidatura passada per paràmetres.
     * @param c Candidatura a inserir.
     * @return true si s'ha inserit correctament, false si no.
     */
    @Override
    public boolean create(Candidatura c) {
        // INSERT SQL
        String query = "INSERT INTO candidatures (eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional) VALUES (?,?,?,?,?,?,?)";

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

    /**
     * Actualitza la candidatura passada per paràmetres amb
     * els valors de la candidatura de la BD amb el mateix id i eleccio_id.
     * @param c Candidatura a actualitzar.
     * @return true si s'ha actualitzat correctament,
     * false si no existeix a la BD.
     */
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

    /**
     * Retorna un objecte Candidatura amb els valors del registre
     * de la taula candidatures. Per fer-ho utilitza la candidatura_id passada per paràmetres.
     * @param id cal passar-li la candidatura_id i l'eleccio_id en aquest ordre.
     * @return Candidatura amb els valors del registre de la taula
     * candidatures amb la candidatura_id i l'eleccio_id passats per paràmetres o null si no existeix.
     */
    @Override
    public Candidatura readById (Object id) {

        // SELECT SQL
        String query = "SELECT codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional FROM candidatures WHERE candidatura_id=?";

        // Llegim de la BD amb l'id de la candidatura passada per paràmetres
        List<Object[]> r = DBMySQLManager.read(query, id);

        // Si no existeix, retornem null
        if (r.size() != 1L) return null;

        // Si existeix, retornem la candidatura
        Object[] row = r.iterator().next();

        String codi_candidatura = (String)row[1];
        String nom_curt = (String)row[2];
        String nom_llarg = (String)row[3];
        String codi_acumulacio_provincia = (String)row[4];
        String codi_acumulacio_ca = (String)row[5];
        String codi_acumulacio_nacional = (String)row[6];

        return new Candidatura((long)id, codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional);
    }

    /**
     * Actualitza la candidatura de la BD amb el mateix id que la candidatura passada
     * per paràmetres amb els valors de la candidatura passada per paràmetres.
     * @param c Candidatura a actualitzar.
     * @return true si s'ha actualitzat correctament, false si no.
     */
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

    /**
     * Actualitza només els camps passats per paràmetre de la candidatura
     * de la BD amb el mateix id que la candidatura passada per paràmetres
     * amb els valors de la candidatura passada per paràmetres.
     * @param c Persona a actualitzar.
     * @param camps Camps a actualitzar.
     * @return true si s'ha actualitzat correctament, false si no.
     */
    @Override
    public boolean update(Candidatura c, String... camps) {
        // Creem un array de paràmetres de la mida de la quantitat de camps + 1 (per la candidatura_id)
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

    /**
     * Elimina la candidatura passada per paràmetres de la BD.
     * @param c Candidatura a eliminar.
     * @return true si s'ha eliminat correctament, false si no.
     */
    @Override
    public boolean delete(Candidatura c) {
        // DELETE SQL
        String query = "DELETE FROM candidatures WHERE candidatura_id=?";

        // Eliminem de la BD la candidatura passada per paràmetres
        int r = DBMySQLManager.write(query, c.getId());

        // Si s'ha eliminat alguna fila, retornem true
        return r > 0;
    }

    /**
     * Cerca candidatures a la BD amb el valor del camp passat per paràmetres.
     * @param camp camp pel qual es vol cercar.
     * @param valor valor del camp.
     * @return llista de candidatures trobades a la cerca.
     */
    @Override
    public List<Candidatura> search(String camp, Object valor) {
        // Creem una llista buida de Candidatures
        List<Candidatura> l = new LinkedList<>();

        // Query per obtenir totes les candidatures amb el valor del camp passat per paràmetres
        String query = "SELECT * FROM candidatures WHERE " + camp + "=?";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query, valor);

        // Per cada registre
        for (Object[] row : r) {

            // Obtenim les dades de la candidatura
            long id = (long) row[0];
            int eleccio_id = (int) row[1];
            String codi_candidatura = (String) row[2];
            String nom_curt = (String) row[3];
            String nom_llarg = (String) row[4];
            String codi_acumulacio_provincia = (String) row[5];
            String codi_acumulacio_ca = (String) row[6];
            String codi_acumulacio_nacional = (String) row[7];

            // Afegim la candidatura a la llista
            l.add(new Candidatura(id, eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional));
        }

        // Retornem la llista
        return l;
    }

    /**
     * Comprova si una candidatura existeix a la BD.
     * @param c Candidatura a comprovar.
     * @return true si la candidatura existeix a la BD, false si no.
     */
    @Override
    public boolean exists(Candidatura c) {
        return read(c);
    }

    /**
     * Fa el recompte de registres a la taula candidatures.
     * @return nombre de registres de la taula candidatures.
     */
    @Override
    public long count() {
        // Query per comptar el nombre de files de la taula
        String query = "SELECT COUNT(*) FROM candidatures";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query);

        // Si la llista no te un sol element, retornem -1 (s'ha produit un error)
        if (r.size() != 1) return -1;

        // Retornem el nombre de files de la taula
        Object[] o = r.iterator().next();
        return (long)o[0];
    }

    /**
     * Retorna totes les candidatures de la BD.
     * @return llista de candidatures de la BD.
     */
    @Override
    public List<Candidatura> all() {
        // Creem una llista buida de persones
        List<Candidatura> l = new LinkedList<>();

        // Query per obtenir totes les candidatures
        String query = "SELECT candidatura_id, eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional FROM candidatures";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query);

        // Per cada registre
        for (Object[] row : r) {

            // Obtenim les dades de la candidatura
            long id = (long) row[0];
            int eleccio_id = (int) row[1];
            String codi_candidatura = (String) row[2];
            String nom_curt = (String) row[3];
            String nom_llarg = (String) row[4];
            String codi_acumulacio_provincia = (String) row[5];
            String codi_acumulacio_ca = (String) row[6];
            String codi_acumulacio_nacional = (String) row[7];

            // Afegim la candidatura a la llista
            l.add(new Candidatura(id, eleccio_id, codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulacio_nacional));
        }

        // Retornem la llista
        return l;
    }
}
