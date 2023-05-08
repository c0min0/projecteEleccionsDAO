package model;

import java.sql.Date;

public class Persona {
    int persona_id;
    String nom;
    String cog1;
    String cog2;
    String sexe;
    Date data_naixement;

    /** No és el dni, és una cadena única generada per nosaltres
     * a partir de la informació dels documents, ja que el DNI no
     * es pot emmagatzemar segons la llei (CHAR(8)).
     */
    String dni;


    public Persona(int persona_id, String nom, String cog1, String cog2, String sexe, Date data_naixement, String dni) {
        this.persona_id = persona_id;
        this.nom = nom;
        this.cog1 = cog1;
        this.cog2 = cog2;
        this.sexe = sexe;
        this.data_naixement = data_naixement;
        this.dni = dni;
    }

    public void set (String nom, String cog1, String cog2, String sexe, Date data_naixement, String dni) {
        this.nom = nom;
        this.cog1 = cog1;
        this.cog2 = cog2;
        this.sexe = sexe;
        this.data_naixement = data_naixement;
        this.dni = dni;
    }

    public int getId() {
        return persona_id;
    }

    public void setId(int persona_id) {
        this.persona_id = persona_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCog1() {
        return cog1;
    }

    public void setCog1(String cog1) {
        this.cog1 = cog1;
    }

    public String getCog2() {
        return cog2;
    }

    public void setCog2(String cog2) {
        this.cog2 = cog2;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDataNaixement() {
        return data_naixement;
    }

    public void setDataNaixement(Date data_naixement) {
        this.data_naixement = data_naixement;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "persona_id=" + persona_id +
                ", nom='" + nom + '\'' +
                ", cog1='" + cog1 + '\'' +
                ", cog2='" + cog2 + '\'' +
                ", sexe=" + sexe +
                ", data_naixement=" + data_naixement +
                ", dni='" + dni + '\'' +
                '}';
    }
}
