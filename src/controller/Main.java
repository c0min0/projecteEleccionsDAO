package controller;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;

import java.util.List;

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
        provaPersones();
        //provaCandidatures();
        provaReadPersones();
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
        /*try {
            Connection conn = DBMySQLManager.getConnection();
            Persona p = new Persona(null, null, null, null, null, "88818888");

            PersonaDAO pDAO = new PersonaDAO();
            if (pDAO.create(p)) System.out.println("OK!");
            else System.out.println("KO!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */
        // READ
        CandidaturaDAO cDAO = new CandidaturaDAO();

        Candidatura c = new Candidatura(1L);
        System.out.println(cDAO.count());
        CandidaturaDAO pDAO3 = new CandidaturaDAO();
        if (pDAO3.exists(c)){
            System.out.println("PERFECTO");
        }

        if (cDAO.read(c)) {
            System.out.println("READ COMPLETADO");
            System.out.println(c);
        }
        else System.out.println("KO!");



// READBYID
        /*CandidaturaDAO cDAO2 = new CandidaturaDAO();
        Candidatura c2 = cDAO2.readById(1);

        c2.setNomCurt("Prueba");
        c2.setNomLlarg("superada");

        cDAO2.update(c2);
        System.out.println("READBYID I UPDATE COMPLETADO");


         */

    }
    private static void provaReadPersones(){

        PersonaDAO pDAO = new PersonaDAO();

        Persona p = new Persona(1);

        if (pDAO.read(p)) System.out.println(p);
        else System.out.println("KO!");




        System.out.println(pDAO.count());
        int i = 0;
        Object[] o = new Object[1];
        o[0] = i;

        if (o[0] == null) System.out.println("null");
        else System.out.println("no null");




        /*PersonaDAO pDAO = new PersonaDAO();
        Persona p = pDAO.readById(1);

        p.setNom("Prueba");
        p.setCog1("superada");

        pDAO.update(p);

         */



       // pDAO.delete(p);




        CandidaturaDAO pDAO2 = new CandidaturaDAO();
        List<Candidatura> llista = pDAO2.all();

        for (Candidatura p2 : llista) {
            System.out.println(p2);

        }





        /*Persona p3 = new PersonaDAO().readById(1);
        System.out.println(p3);
         */
    }
}
