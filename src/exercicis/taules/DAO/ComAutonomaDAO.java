package exercicis.taules.DAO;

import exercicis.taules.ComAutonoma;

import java.util.List;

public class ComAutonomaDAO implements DAODB<ComAutonoma> {
    @Override
    public boolean create(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public boolean read(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public boolean update(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public boolean delete(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public boolean exists(ComAutonoma comAutonoma) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<ComAutonoma> all() {
        return null;
    }
}
