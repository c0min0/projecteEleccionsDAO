package model;

public class Provincia {
    long provincia_id;
    long comunitat_aut_id;
    String nom;
    String codi_ine;
    int num_escons;

    public Provincia(long provincia_id, long comunitat_aut_id, String nom, String codi_ine, int num_escons) {
        this.provincia_id = provincia_id;
        this.comunitat_aut_id = comunitat_aut_id;
        this.nom = nom;
        this.codi_ine = codi_ine;
        this.num_escons = num_escons;
    }

    public long getProvincia_id() {
        return provincia_id;
    }

    public void setProvincia_id(long provincia_id) {
        this.provincia_id = provincia_id;
    }

    public long getComunitat_aut_id() {
        return comunitat_aut_id;
    }

    public void setComunitat_aut_id(long comunitat_aut_id) {
        this.comunitat_aut_id = comunitat_aut_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodi_ine() {
        return codi_ine;
    }

    public void setCodi_ine(String codi_ine) {
        this.codi_ine = codi_ine;
    }

    public int getNum_escons() {
        return num_escons;
    }

    public void setNum_escons(int num_escons) {
        this.num_escons = num_escons;
    }
}
