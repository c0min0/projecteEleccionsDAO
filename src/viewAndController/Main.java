package viewAndController;

import model.DAO.MySQL.PersonaDAO;
import model.Persona;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Menu.initMenu();
        prova();
    }

    private static void prova() {
        /*Persona p = new Persona(null, null, null, null, null, "99999999");

        PersonaDAO pDAO = new PersonaDAO();
        if (pDAO.create(p)) System.out.println("OK!");
        else System.out.println("KO!");
        */


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
