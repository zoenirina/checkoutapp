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
public class Produit {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRefProduit() {
        return refProduit;
    }

    public void setRefProduit(String refProduit) {
        this.refProduit = refProduit;
    }

//    public int getIdUnite() {
//        return idUnite;
//    }
//
//    public void setIdUnite(int idUnite) {
//        this.idUnite = idUnite;
//    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateModif() {
        return dateModif;
    }

    public void setDateModif(String dateModif) {
        this.dateModif = dateModif;
    }

    public int getIdEtat() {
        return idEtat;
    }

    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }
    
    private int id = 0;
    private String designation = "";
    private String refProduit = "";
//    private int idUnite = 0;
    private UniteStockage uniteStockage= new UniteStockage();
    private float PU = 0.0f;
    private String dateCreation = "";
    private String dateModif = "";
    private int idEtat = 0;
    private int idUnite = 0;
    

     //produit vide
    public  Produit (){}
    
    //produit avec les information sur l'unité de stockage
    public  Produit (int id, String designation, String refProduit , float PU, String dateCreation, String dateModif, int idEtat, UniteStockage uniteStockage){
    this.id=id;
    this.designation=designation;
    this.uniteStockage=uniteStockage;
    this.PU=PU;
    this.dateCreation=dateCreation;
    this.dateModif=dateModif;
    this.idEtat=idEtat;
    this.refProduit=refProduit;
    }
    
    //produit sans les information sur l'unité de stockage
    public  Produit (int id, String designation, String refProduit , float PU, String dateCreation, String dateModif, int idEtat){
    this.id=id;
    this.designation=designation;
    this.PU=PU;
    this.dateCreation=dateCreation;
    this.dateModif=dateModif;
    this.idEtat=idEtat;
    this.refProduit=refProduit;
    }
    
        public  Produit (int id, String designation, String refProduit , float PU, String dateCreation, String dateModif, int idEtat, int idUnite){
    this.id=id;
    this.designation=designation;
    this.PU=PU;
    this.dateCreation=dateCreation;
    this.dateModif=dateModif;
    this.idEtat=idEtat;
    this.idUnite=idUnite;
    this.refProduit=refProduit;
    }

    public float getPU() {
        return PU;
    }

    public void setPU(float PU) {
        this.PU = PU;
    }

    public UniteStockage getUniteStockage() {
        return uniteStockage;
    }

    public void setUniteStockage(UniteStockage uniteStockage) {
        this.uniteStockage = uniteStockage;
    }

    /**
     * @return the idUnite
     */
    public int getIdUnite() {
        return idUnite;
    }

    /**
     * @param idUnite the idUnite to set
     */
    public void setIdUnite(int idUnite) {
        this.idUnite = idUnite;
    }
}
