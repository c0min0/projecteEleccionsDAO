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

    public long getId() {
        return candidat_id;
    }

    public void setId(long candidat_id) {
        this.candidat_id = candidat_id;
    }

    public long getCandidaturaId() {
        return candidatura_id;
    }

    public void setCandidaturaId(long candidatura_id) {
        this.candidatura_id = candidatura_id;
    }

    public long getPersonaId() {
        return persona_id;
    }

    public void setPersonaId(long persona_id) {
        this.persona_id = persona_id;
    }

    public long getProvinciaId() {
        return provincia_id;
    }

    public void setProvinciaId(long provincia_id) {
        this.provincia_id = provincia_id;
    }

    public int getNumOrdre() {
        return num_ordre;
    }

    public void setNumOrdre(int num_ordre) {
        this.num_ordre = num_ordre;
    }

    public char getTipus() {
        return tipus;
    }

    public void setTipus(char tipus) {
        this.tipus = tipus;
    }
}
