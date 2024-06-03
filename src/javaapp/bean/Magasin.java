/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

/**
 *
 * @author ZOENIRINA
 */
public class Magasin {
    private int id;
    private String refMagasin;
    private String labelle;
    private String adresse;
    private int capacite;
    private int idResponsable;
    private Profile profile;

    public Magasin(int id,String refMagasin, String labelle, String adresse, int capacite, int idResponsable) {
        this.id = id;
        this.refMagasin = refMagasin;
        this.labelle = labelle;
        this.adresse = adresse;
        this.capacite = capacite;
        this.idResponsable = idResponsable;
    }

    public Magasin() {
    
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getRefMagasin() {
        return refMagasin;
    }

    public String getLabelle() {
        return labelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRefMagasin(String refMagasin) {
        this.refMagasin = refMagasin;
    }

    public void setLabelle(String labelle) {
        this.labelle = labelle;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
