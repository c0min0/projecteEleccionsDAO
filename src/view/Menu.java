package view;

import controller.DAO.MySQL.PersonaDAO;
import model.Persona;

import java.util.Scanner;

public class Menu {
    static Scanner scan = new Scanner(System.in);
    static void initMenu() {
        String opcio;
        boolean opIncorrecte, fi = false;

        do {
            do {
                System.out.println("1. Canviar dades a la taula persones");
                System.out.println("2. Afegir nous registres a la taula candidatures");
                System.out.println("0. Sortir");
                System.out.print("\nTria una opció: ");

                opcio = scan.nextLine().trim();

                opIncorrecte = !opcio.equals("1") && !opcio.equals("2") && !opcio.equals("0");

                if (opIncorrecte) System.out.println("Opció incorrecte, torna a provar-ho.\n");

            } while (opIncorrecte);

            switch (opcio) {
                case "1":
                    updPersonaMenu();
                    break;
                case "2":
                    insCandidaturaMenu();
                    break;
                case "0":
                    return;
            }

            do {
                System.out.print("\nVols realitzar alguna opció més (S/N): ");
                opcio = scan.nextLine().trim().toUpperCase();

                opIncorrecte = !opcio.equals("S") && !opcio.equals("N");

                if (opIncorrecte) System.out.println("Opció incorrecte, torna a provar-ho.\n");
            } while (opIncorrecte);

            fi = opcio.equals("N");

        } while (!fi);

        System.out.println("Adeu!");
    }

    private static void updPersonaMenu() {
        String opcio;
        int id;
        boolean opIncorrecte = false, fi;

        do {
            do {
                System.out.print("Intodruïu l'id de la persona que vols modificar: ");
                opcio = scan.nextLine().trim();

                try {
                    id = Integer.parseInt(opcio);
                } catch (Exception e) {
                    System.out.println("Opció incorrecte, torna a provar-ho.\n");
                    opIncorrecte = true;
                }

            } while (opIncorrecte);

            do {
                System.out.println("\nQuin camp vols modificar?");
                System.out.println("1. nom VARCHAR(30)");
                System.out.println("2. cog1 VARCHAR(30)");
                System.out.println("3. cog2 VARCHAR(30)");
                System.out.println("4. sexe ENUM('M','F')");
                System.out.println("5. data_naixement DATE");
                System.out.println("0. Tornar al menú principal");
                System.out.print("Triar camp: ");

                opcio = scan.nextLine().trim();

                opIncorrecte = !opcio.equals("1") && !opcio.equals("2")
                        && !opcio.equals("3") && !opcio.equals("4") && !opcio.equals("5");

            } while (opIncorrecte);

            switch (opcio) {
                case "1":
                    //TODO: update nom
                    System.out.println("Opcio amb el nom");
                    break;
                case "2":
                    //TODO: update cog1
                    System.out.println("Opcio amb el cognom1");
                    break;
                case "3":
                    //TODO: update cog2
                    System.out.println("Opcio amb el cognom2");
                    break;
                case "4":
                    //TODO: update sexe
                    System.out.println("Opcio amb el sexe");
                    break;
                case "5":
                    //TODO: update data_naixement
                    System.out.println("Opcio amb la data de naixement");
                    break;
                case "0":
                    return;
            }

            do {
                System.out.print("\nVols realitzar algun update més sobre aquest registre? (S/N): ");
                opcio = scan.nextLine().trim().toUpperCase();

                opIncorrecte = !opcio.equals("S") && !opcio.equals("N");

                if (opIncorrecte) System.out.println("Opció incorrecte, torna a provar-ho.\n");
            } while (opIncorrecte);

            fi = opcio.equals("N");

        } while (!fi);
    }

    private static void insCandidaturaMenu() {
        String opcio;
        boolean opIncorrecte = false, fi = false;

        do {
            int candidatura_id, eleccio_id;
            String codi_candidatura, nom_curt, nom_llarg, codi_acumulacio_provincia, codi_acumulacio_ca, codi_acumulario_nacional;
            System.out.println("La taula candidatures consta dels següents camps:");
            System.out.println("`candidatura_id` INT UNSIGNED NOT NULL AUTO_INCREMENT");
            System.out.println("`eleccio_id` TINYINT UNSIGNED NOT NULL");
            System.out.println("`codi_candidatura` CHAR(6) NULL");
            System.out.println("`nom_curt` VARCHAR(50) NULL COMMENT 'Sigles de la candidatura'");
            System.out.println("`nom_llarg` VARCHAR(150) NULL COMMENT 'Nom llarg de la candidatura (denominació)'");
            System.out.println("`codi_acumulacio_provincia` CHAR(6) NULL COMMENT 'Codi de la candidatura d'acumulació a nivell provincial.'");
            System.out.println("`codi_acumulacio_ca` CHAR(6) NULL COMMENT 'Codi de la candidatura d'acumulació a nivell de comunitat autònoma'");
            System.out.println("`codi_acumulario_nacional` CHAR(6) NULL");
            System.out.println("`codi_acumulacio_provincia` CHAR(6) NULL COMMENT 'Codi de la candidatura d'acumulació a nivell provincial.'");

            System.out.print("\nIntrodueïu la candidatura_id: ");
            candidatura_id = scan.nextInt();
            scan.nextLine();

            System.out.print("\nIntrodueïu la eleccio_id: ");
            eleccio_id = scan.nextInt();
            scan.nextLine();

            System.out.print("\nIntrodueïu el codi_candidatura: ");
            codi_candidatura = scan.nextLine();

            System.out.print("\nIntrodueïu el nom_curt: ");
            nom_curt = scan.nextLine();

            System.out.print("\nIntrodueïu el nom_llarg: ");
            nom_llarg = scan.nextLine();

            System.out.print("\nIntrodueïu el codi_acumulacio_provincia: ");
            codi_acumulacio_provincia = scan.nextLine();

            System.out.print("\nIntrodueïu el codi_acumulacio_ca: ");
            codi_acumulacio_ca = scan.nextLine();

            System.out.print("\nIntrodueïu el codi_acumulario_nacional: ");
            codi_acumulario_nacional = scan.nextLine();

            //TODO: insert candidatura

            do {
                System.out.print("\nVols realitzar algun alre insert? (S/N): ");
                opcio = scan.nextLine().trim().toUpperCase();

                opIncorrecte = !opcio.equals("S") && !opcio.equals("N");

                if (opIncorrecte) System.out.println("Opció incorrecte, torna a provar-ho.\n");
            } while (opIncorrecte);

            fi = opcio.equals("N");

        } while (!fi);
    }
}
