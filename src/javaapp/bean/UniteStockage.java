/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

/**
 *
 * @author ASUS
 */
public class UniteStockage {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnite() {
        return unite;
    }

    public void setUnite(int unite) {
        this.unite = unite;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public float getCapacite() {
        return capacite;
    }

    public void setCapacite(float capacite) {
        this.capacite = capacite;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public int getPackageU() {
        return packageU;
    }

    public void setPackageU(int packageU) {
        this.packageU = packageU;
    }

    public String getLabelle() {
        return labelle;
    }

    public void setLabelle(String labelle) {
        this.labelle = labelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public UniteStockage(){}
    public UniteStockage(int id,int unite,float poids,float capacite, float volume, float longueur, int packageU, String labelle, String description ){
    this.id=id;
    this.unite=unite;
    this.poids=poids;
    this.capacite=capacite;
    this.volume=volume;
    this.longueur=longueur;
    this.packageU=packageU;
    this.labelle=labelle;
    this.description=description;
    }
    
    private int id = 0;
    private int unite = 0;
    private float poids = 0.0f;
    private float capacite = 0.0f;
    private float volume = 0.0f;
    private float longueur = 0.0f;
    private int packageU = 0;
    private String labelle = "";
    private String description = "";

   
}
