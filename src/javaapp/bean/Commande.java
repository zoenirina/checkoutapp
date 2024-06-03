/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

import java.util.List;

/**
 *
 * @author ZOENIRINA
 */
public class Commande {

    /**
     * @return the avoirFacture
     */
    public String getAvoirFacture() {
        return avoirFacture;
    }

    /**
     * @param avoirFacture the avoirFacture to set
     */
    public void setAvoirFacture(String avoirFacture) {
        this.avoirFacture = avoirFacture;
    }

    /**
     * @return the montantTTC
     */
    public float getMontantTTC() {
        return montantTTC;
    }

    /**
     * @param montantTTC the montantTTC to set
     */
    public void setMontantTTC(float montantTTC) {
        this.montantTTC = montantTTC;
    }

    /**
     * @return the montantHT
     */
    public float getMontantHT() {
        return montantHT;
    }

    /**
     * @param montantHT the montantHT to set
     */
    public void setMontantHT(float montantHT) {
        this.montantHT = montantHT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getRefCommande() {
        return refCommande;
    }

    public void setRefCommande(String refCommande) {
        this.refCommande = refCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }
   
//    public Personne getClient() {
//        return personne;
//    }
//
//    public void setClient(Personne personne) {
//        this.personne = personne;
//    }

    private int id = 0;
    private String dateCommande = "";

    private String refCommande = "";
//    private Personne personne = null;
    private int quantite = 0;
    private float montant = 0.0f;
    
    private String devise = "";
    private int idCreateur = 0;
    private String description = "";
    private String pj = "";
    private String status = "";
    
    // nouveau attribut
    private String avoirFacture = "Non";
    private float montantTTC = 0.0f;
    private float montantHT = 0.0f;
    private float remise = 0.0f;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

}
