package controller.DAO.mySQL;

import controller.DAO.DAODB;
import model.ComAutonoma;

import java.util.List;

public class ComAutonomaDAO implements DAODB<ComAutonoma> {

    //TODO: Fer tots els mètodes i posar CA

    @Override
    public boolean create(ComAutonoma CA) {
        // INSERT SQL
        String query = "INSERT INTO candidatures (eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulacio_nacional) VALUES (?,?,?,?,?,?,?)";

        // Escrivim a la BD amb els valors de la candidatura passada per paràmetres
        int r = DBMySQLManager.write(query,
                CA.getNom(),
                CA.getCodiIne());

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean read(ComAutonoma CA) {
        // Obtenim una candidatura de la BD amb el mateix id que la candidatura passada per paràmetres
        ComAutonoma CAr = readById(CA.getId());

        // Si no existeix, retornem false
        if (CAr == null) return false;

        // Si existeix, actualitzem la candidatura passada per paràmetres amb els valors de la candidatura de la BD
        CA.set(CAr.getNom(),CAr.getCodiIne());

        // i retornem true
        return true;
    }

    @Override
    public ComAutonoma readById(Object id) {

        // SELECT SQL
        String query = "SELECT nom, codi_ine FROM comunitats_autonomes WHERE comunitat_aut_id=?";

        // Llegim de la BD amb l'id de la candidatura passada per paràmetres
        List<Object[]> r = DBMySQLManager.read(query, id);

        // Si no existeix, retornem null
        if (r.size() != 1L) return null;
        //
        //        // Si existeix, retornem la candidatura
        Object[] row = r.iterator().next();

        String nom = (String)row[0];
        String codi_ine = (String)row[1];

        return new ComAutonoma((int)id, nom, codi_ine);

    }

    @Override
    public boolean update(ComAutonoma CA) {
        // UPDATE SQL
        String query = "UPDATE comunitats_autonomes SET nom=?,codi_ine=? WHERE comunitat_aut_id=?";

        // Actualitzem la BD amb els valors de la candidatura passada per paràmetres
        String nom_curt = CA.getNom();
        String codi_ine = CA.getCodiIne();
        long comunitat_aut_id = CA.getId();

        int r = DBMySQLManager.write(query, codi_ine, comunitat_aut_id);

        // Si s'ha modificat alguna fila, retornem true
        return r > 0;
    }

    @Override
    public boolean update(ComAutonoma comAutonoma, String... camps) {
        return false;
    }

    @Override
    public boolean delete(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public List<ComAutonoma> search(String camp, Object valor) {
        return null;
    }

    @Override
    public boolean exists(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<ComAutonoma> all() {
        return null;
    }
}
