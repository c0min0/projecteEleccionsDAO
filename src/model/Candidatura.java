package model;

public class Candidatura {
    long candidatura_id;
    long eleccio_id;
    String codi_candidatura;
    String nom_curt;
    String nom_llarg;
    String codi_acumulacio_provincia;
    String codi_acumulacio_ca;
    String codi_acumulacio_nacional;

    public Candidatura(long candidatura_id, long eleccio_id, String codi_candidatura, String nom_curt, String nom_llarg, String codi_acumulacio_provincia, String codi_acumulacio_ca, String codi_acumulacio_nacional) {
        this.candidatura_id = candidatura_id;
        this.eleccio_id = eleccio_id;
        this.codi_candidatura = codi_candidatura;
        this.nom_curt = nom_curt;
        this.nom_llarg = nom_llarg;
        this.codi_acumulacio_provincia = codi_acumulacio_provincia;
        this.codi_acumulacio_ca = codi_acumulacio_ca;
        this.codi_acumulacio_nacional = codi_acumulacio_nacional;
    }

    public Candidatura(long candidatura_id) {
        this.candidatura_id = candidatura_id;
    }


    public void set(long eleccio_id,String codi_candidatura, String nom_curt, String nom_llarg, String codi_acumulacio_provincia, String codi_acumulacio_ca, String codi_acumulacio_nacional) {
        this.eleccio_id = eleccio_id;
        this.codi_candidatura = codi_candidatura;
        this.nom_curt = nom_curt;
        this.nom_llarg = nom_llarg;
        this.codi_acumulacio_provincia = codi_acumulacio_provincia;
        this.codi_acumulacio_ca = codi_acumulacio_ca;
        this.codi_acumulacio_nacional = codi_acumulacio_nacional;
    }
    public Candidatura(long eleccio_id,String codi_candidatura, String nom_curt, String nom_llarg, String codi_acumulacio_provincia, String codi_acumulacio_ca, String codi_acumulacio_nacional) {
        this.eleccio_id = eleccio_id;
        this.codi_candidatura = codi_candidatura;
        this.nom_curt = nom_curt;
        this.nom_llarg = nom_llarg;
        this.codi_acumulacio_provincia = codi_acumulacio_provincia;
        this.codi_acumulacio_ca = codi_acumulacio_ca;
        this.codi_acumulacio_nacional = codi_acumulacio_nacional;
    }


    public long getId() {
        return candidatura_id;
    }

    public void setId(long candidatura_id) {
        this.candidatura_id = candidatura_id;
    }

    public long getEleccioId() {
        return eleccio_id;
    }

    public void setEleccioId(long eleccio_id) {
        this.eleccio_id = eleccio_id;
    }

    public String getCodiCandidatura() {
        return codi_candidatura;
    }

    public void setCodiCandidatura(String codi_candidatura) {
        this.codi_candidatura = codi_candidatura;
    }

    public String getNomCurt() {
        return nom_curt;
    }

    public void setNomCurt(String nom_curt) {
        this.nom_curt = nom_curt;
    }

    public String getNomLlarg() {
        return nom_llarg;
    }

    public void setNomLlarg(String nom_llarg) {
        this.nom_llarg = nom_llarg;
    }

    public String getCodiAcumulacioProvincia() {
        return codi_acumulacio_provincia;
    }

    public void setCodiAcumulacioProvincia(String codi_acumulacio_provincia) {
        this.codi_acumulacio_provincia = codi_acumulacio_provincia;
    }

    public String getCodiAcumulacioCA() {
        return codi_acumulacio_ca;
    }

    public void setCodiAcumulacioCA(String codi_acumulacio_ca) {
        this.codi_acumulacio_ca = codi_acumulacio_ca;
    }

    public String getCodiAcumulacioNacional() {
        return codi_acumulacio_nacional;
    }

    public void setCodiAcumulacioNacional(String codi_acumulacio_nacional) {
        this.codi_acumulacio_nacional = codi_acumulacio_nacional;
    }

    @Override
    public String toString() {
        return "Candidatura{" +
                "candidatura_id=" + candidatura_id +
                ", eleccio_id=" + eleccio_id +
                ", codi_candidatura='" + codi_candidatura + '\'' +
                ", nom_curt='" + nom_curt + '\'' +
                ", nom_llarg='" + nom_llarg + '\'' +
                ", codi_acumulacio_provincia='" + codi_acumulacio_provincia + '\'' +
                ", codi_acumulacio_ca='" + codi_acumulacio_ca + '\'' +
                ", codi_acumulacio_nacional='" + codi_acumulacio_nacional + '\'' +
                '}';
    }
}
