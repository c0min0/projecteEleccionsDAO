package controller.DAO.mySQL;

import controller.DAO.DAODB;
import model.ComAutonoma;

import java.util.LinkedList;
import java.util.List;

public class ComAutonomaDAO implements DAODB<ComAutonoma> {

    //TODO: Fer tots els mètodes i posar CA

    @Override
    public boolean create(ComAutonoma CA) {
        // INSERT SQL
        String query = "INSERT INTO comunitats_autonomes (nom,codi_ine) VALUES (?,?)";

        // Escrivim a la BD amb els valors de la comunitat_autonoma passada per paràmetres
        int r = DBMySQLManager.write(query,
                CA.getNom(),
                CA.getCodiIne());

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean read(ComAutonoma CA) {
        // Obtenim una comunitat_autonoma de la BD amb el mateix id que la comunitat_autonoma passada per paràmetres
        ComAutonoma CAr = readById(CA.getId());

        // Si no existeix, retornem false
        if (CAr == null) return false;

        // Si existeix, actualitzem la comunitat_autonoma passada per paràmetres amb els valors de la comunitat_autonoma de la BD
        CA.set(CAr.getNom(),CAr.getCodiIne());

        // i retornem true
        return true;
    }

    @Override
    public ComAutonoma readById(Object id) {

        // SELECT SQL
        String query = "SELECT nom, codi_ine FROM comunitats_autonomes WHERE comunitat_aut_id=?";

        // Llegim de la BD amb l'id de la comunitat_autonoma passada per paràmetres
        List<Object[]> r = DBMySQLManager.read(query, id);

        // Si no existeix, retornem null
        if (r.size() != 1L) return null;
        //
        //        // Si existeix, retornem la comunitat_autonoma
        Object[] row = r.iterator().next();

        String nom = (String)row[0];
        String codi_ine = (String)row[1];

        return new ComAutonoma((int)id, nom, codi_ine);

    }

    @Override
    public boolean update(ComAutonoma CA) {
        // UPDATE SQL
        String query = "UPDATE comunitats_autonomes SET nom=?,codi_ine=? WHERE comunitat_aut_id=?";

        // Actualitzem la BD amb els valors de la comunitat_autonoma passada per paràmetres
        String nom = CA.getNom();
        String codi_ine = CA.getCodiIne();
        int comunitat_aut_id = CA.getId();

        int r = DBMySQLManager.write(query, nom,codi_ine, comunitat_aut_id);

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean update(ComAutonoma CA, String... camps) {
        // Creem un array de paràmetres de la mida de la quantitat de camps + 1 (per la comunitat_aut_id)
        Object[] params = new Object[camps.length + 1];

        // Construïm la query
        StringBuilder query = new StringBuilder("UPDATE comunitats_autonomes SET ");

        // Per cada camp,
        for (int i = 0; i < camps.length; i++) {

            // afegim el camp a la query
            query.append(camps[i]).append("=?");

            // i una coma si no és l'últim camp
            if (i < camps.length - 1) query.append(",");

            // També afegim el valor del camp a l'array de paràmetres
            switch (camps[i]) {
                case "comunitat_aut_id" -> params[i] = CA.getId();
                case "nom" -> params[i] = CA.getNom();
                case "codi_ine" -> params[i] = CA.getCodiIne();
                default -> throw new IllegalArgumentException("El camp " + camps[i] + " no existeix");
            }
        }

        // Afegim l'id al final de la query
        query.append(" WHERE comunitat_aut_id=?");
        params[params.length - 1] = CA.getId();

        // Executem la query
        int r = DBMySQLManager.write(query.toString(), params);

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean delete(ComAutonoma CA) {
        // DELETE SQL
        String query = "DELETE FROM comunitats_autonomes WHERE comunitat_aut_id=?";

        // Eliminem de la BD la comunitat_autonoma passada per paràmetres
        int r = DBMySQLManager.write(query, CA.getId());

        // Si s'ha eliminat alguna fila, retornem true
        return r > 0;    }

    @Override
    public List<ComAutonoma> search(String camp, Object valor) {
        // Creem una llista buida de ComunitatsAutonomes
        List<ComAutonoma> l = new LinkedList<>();

        // Query per obtenir totes les ComunitatsAutonomes amb el valor del camp passat per paràmetres
        String query = "SELECT * FROM comunitats_autonomes WHERE " + camp + "=?";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query, valor);

        // Per cada registre
        for (Object[] row : r) {

            // Obtenim les dades de la comunitat_autonoma
            int comunitat_aut_id = (int) row[0];
            String nom = (String) row[1];
            String codi_ine = (String) row[2];

            // Afegim la comunitat_autonoma a la llista
            l.add(new ComAutonoma(comunitat_aut_id, nom, codi_ine));

        }

        // Retornem la llista
        return l;
    }
    /**
     * Comprova si una comunitat_autonoma existeix a la BD.
     * @param CA ComunitatAutonoma a comprovar.
     * @return true si la comunitat_autonoma existeix a la BD, false si no.
     */

    @Override
    public boolean exists(ComAutonoma CA) {
        return read(CA);
    }

    /**
     * Fa el recompte de registres a la taula comunitats_autonomes.
     * @return nombre de registres de la taula comunitats_autonomes.
     */

    @Override
    public long count() {
        // Query per comptar el nombre de files de la taula
        String query = "SELECT COUNT(*) FROM comunitats_autonomes";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query);

        // Si la llista no te un sol element, retornem -1 (s'ha produit un error)
        if (r.size() != 1) return -1;

        // Retornem el nombre de files de la taula
        Object[] o = r.iterator().next();
        return (long)o[0];
    }

    /**
     * Retorna totes les comunitats_autonomes de la BD.
     * @return llista de comunitats_autonomes de la BD.
     */
    @Override
    public List<ComAutonoma> all() {
        // Creem una llista buida de persones
        List<ComAutonoma> l = new LinkedList<>();

        // Query per obtenir totes les comunitats_autonomes de la BD
        String query = "SELECT comunitat_aut_id, nom, codi_ine FROM comunitats_autonomes";

        // Executem la query
        List<Object[]> r = DBMySQLManager.read(query);

        // Per cada registre
        for (Object[] row : r) {

            // Obtenim les dades de la comunitat_autonoma
            int comunitat_aut_id = (int) row[0];
            String nom = (String) row[1];
            String codi_ine = (String) row[2];

            // Afegim la comunitat_autonoma a la llista
            l.add(new ComAutonoma(comunitat_aut_id, nom,codi_ine));
        }

        // Retornem la llista
        return l;
    }
}
