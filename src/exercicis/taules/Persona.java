package exercicis.taules;

public class Persona {
    int persona_id;
    String nom;
    String cog1;
    String cog2;

    /** No és el dni, és una cadena única generada per nosaltres
     * a partir de la informació dels documents, ja que el DNI no
     * es pot emmagatzemar segons la llei.
     */
    String dni;

    public Persona(int persona_id, String nom, String cog1, String cog2, String dni) {
        this.persona_id = persona_id;
        this.nom = nom;
        this.cog1 = cog1;
        this.cog2 = cog2;
        this.dni = dni;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
