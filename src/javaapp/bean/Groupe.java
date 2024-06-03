package javaapp.bean;

public class Groupe {
    private int idGroupe;
    private String nomGroupe;
    private String description;
    private int idDroit;
    private DroitAcces droitAcces = null;

    public Groupe() {
    }

    public Groupe(int idGroupe, String nomGroupe, String description, int idDroit) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
        this.description = description;
        this.idDroit = idDroit;
    }

    public int getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(int idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdDroit() {
        return idDroit;
    }

    public void setIdDroit(int idDroit) {
        this.idDroit = idDroit;
    }

    /**
     * @return the droitAcces
     */
    public DroitAcces getDroitAcces() {
        return droitAcces;
    }

    /**
     * @param droitAcces the droitAcces to set
     */
    public void setDroitAcces(DroitAcces droitAcces) {
        this.droitAcces = droitAcces;
    }
}
