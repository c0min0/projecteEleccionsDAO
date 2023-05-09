package controller;

import controller.DAO.MySQL.CandidaturaDAO;
import controller.DAO.MySQL.DBMySQLManager;
import controller.DAO.MySQL.PersonaDAO;
import model.Candidatura;
import model.Persona;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Menu.initMenu();
        //provaPersones();
        provaCandidatures();
    }
    private static void provaCandidatures() {
        // INSERT

        try {
            Connection conn = DBMySQLManager.getConnection();
            Candidatura c = new Candidatura(1,null,null,null, null, null, null);

            CandidaturaDAO cDAO = new CandidaturaDAO();
            if (cDAO.create(c)) System.out.println("OK!");
            else System.out.println("KO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // UPDATE
        try {
            Connection conn = DBMySQLManager.getConnection();
            String eleccio_id = "eleccio_id";
            String nom_curt = "nom_curt";
            Candidatura c = new Candidatura(82,3,"1211212","Benito",null, null, null, null);

            CandidaturaDAO cDAO = new CandidaturaDAO();
            if (cDAO.update(c,eleccio_id,nom_curt)) System.out.println("OK!");
            else System.out.println("KO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void provaPersones() {
        try{
            Connection conn = DBMySQLManager.getConnection();
            Persona p = new Persona(null, null, null, null, null, "88818888");

            PersonaDAO pDAO = new PersonaDAO();
            if (pDAO.create(p)) System.out.println("OK!");
            else System.out.println("KO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        /*PersonaDAO pDAO = new PersonaDAO();

        Persona p = new Persona(1);

        if (pDAO.read(p)) System.out.println(p);
        else System.out.println("KO!");*/




        /*int i = 0;
        Object[] o = new Object[1];
        o[0] = i;

        if (o[0] == null) System.out.println("null");
        else System.out.println("no null");*/




        /*PersonaDAO pDAO = new PersonaDAO();
        Persona p = pDAO.readById(1);

        p.setNom("Hola");
        p.setCog1("ke ase");

        pDAO.update(p);*/



        //pDAO.delete(p);



        /*PersonaDAO pDAO = new PersonaDAO();
        List<Persona> llista = pDAO.all();

        for (Persona p : llista) {
            System.out.println(p);
        }*/



        /*Persona p = new PersonaDAO().readById(999999L);

        System.out.println(p);*/
    }
}
