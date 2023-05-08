package viewAndController;

import model.DAO.MySQL.PersonaDAO;
import model.Persona;

public class Main {
    public static void main(String[] args) {
        //Menu.initMenu();
        prova();
    }

    private static void prova() {
        /*Persona p = new Persona(0, null, null, null, null, null, "99999999");

        PersonaDAO pDAO = new PersonaDAO();
        if (pDAO.create(p)) System.out.println("OK!");
        else System.out.println("KO!");*/



       /* PersonaDAO pDAO = new PersonaDAO();

        Persona p = new Persona(1, null, null, null, null, null, null);

        if (pDAO.read(p)) System.out.println(p.toString());
        else System.out.println("KO!");*/

        int i = 0;
        Object[] o = new Object[1];
        o[0] = i;

        if (o[0] == null) System.out.println("null");
        else System.out.println("no null");




        /*PersonaDAO pDAO = new PersonaDAO();
        Persona p = pDAO.readById(1);

        p.setNom("Hola");
        p.setCog1("ke ase");

        pDAO.update(p);*/
    }
}
