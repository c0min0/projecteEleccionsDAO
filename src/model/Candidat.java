package model;

public class Candidat {
    long candidat_id;
    long candidatura_id;
    long persona_id;
    long provincia_id;
    int num_ordre;
    char tipus;

    public Candidat(long candidat_id, long candidatura_id, long persona_id, long provincia_id, int num_ordre, char tipus) {
        this.candidat_id = candidat_id;
        this.candidatura_id = candidatura_id;
        this.persona_id = persona_id;
        this.provincia_id = provincia_id;
        this.num_ordre = num_ordre;
        this.tipus = tipus;
    }

    public long getCandidat_id() {
        return candidat_id;
    }

    public void setCandidat_id(long candidat_id) {
        this.candidat_id = candidat_id;
    }

    public long getCandidatura_id() {
        return candidatura_id;
    }

    public void setCandidatura_id(long candidatura_id) {
        this.candidatura_id = candidatura_id;
    }

    public long getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(long persona_id) {
        this.persona_id = persona_id;
    }

    public long getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(long provincia_id) {
        this.provincia_id = provincia_id;
    }

    public int getNum_ordre() {
        return num_ordre;
    }

    public void setNum_ordre(int num_ordre) {
        this.num_ordre = num_ordre;
    }

    public char getTipus() {
        return tipus;
    }

    public void setTipus(char tipus) {
        this.tipus = tipus;
    }
}
