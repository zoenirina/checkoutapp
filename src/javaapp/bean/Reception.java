/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

import java.util.List;

public class Reception {
    private int id;
    private String dateReception;
    private String refReception;
    private int idCommande;
    private int idFournisseur;
    private float montant;
    private String devise;
    private String description;
    private float frais;
    private Personne fournisseur;  
    private String status;
    private String typeLivraison;
    private List<ReceptionDetail> receptionDetails = null;
    
    public Reception() {}

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateReception() {
        return dateReception;
    }

    public void setDateReception(String dateReception) {
        this.dateReception = dateReception;
    }

    public String getRefReception() {
        return refReception;
    }

    public void setRefReception(String refReception) {
        this.refReception = refReception;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getFrais() {
        return frais;
    }

    public void setFrais(float frais) {
        this.frais = frais;
    }

    public Personne getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Personne fournisseur) {
        this.fournisseur = fournisseur;
    }

    @Override
    public String toString() {
        return "Reception{" +
                "id=" + id +
                ", dateReception='" + dateReception + '\'' +
                ", refReception='" + refReception + '\'' +
                ", idCommande=" + idCommande +
                ", idFournisseur=" + idFournisseur +
                ", montant=" + montant +
                ", devise='" + devise + '\'' +
                ", description='" + description + '\'' +
                ", frais=" + frais +
                ", fournisseur=" + fournisseur +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the typeLivraison
     */
    public String getTypeLivraison() {
        return typeLivraison;
    }

    /**
     * @param typeLivraison the typeLivraison to set
     */
    public void setTypeLivraison(String typeLivraison) {
        this.typeLivraison = typeLivraison;
    }

    /**
     * @return the receptionDetails
     */
    public List<ReceptionDetail> getReceptionDetails() {
        return receptionDetails;
    }

    /**
     * @param receptionDetails the receptionDetails to set
     */
    public void setReceptionDetails(List<ReceptionDetail> receptionDetails) {
        this.receptionDetails = receptionDetails;
    }
}

