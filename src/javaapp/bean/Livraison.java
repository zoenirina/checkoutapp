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
public class Livraison {

    /**
     * @return the dateLivraison
     */
    public String getDateLivraison() {
        return dateLivraison;
    }

    /**
     * @param dateLivraison the dateLivraison to set
     */
    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    /**
     * @return the refLivraison
     */
    public String getRefLivraison() {
        return refLivraison;
    }

    /**
     * @param refLivraison the refLivraison to set
     */
    public void setRefLivraison(String refLivraison) {
        this.refLivraison = refLivraison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getRefOperation() {
        return refOperation;
    }

    public void setRefOperation(String refOperation) {
        this.refOperation = refOperation;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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

    public List<LivraisonDetail> getLivraisonDetails() {
        return livraisonDetails;
    }

    public void setLivraisonDetails(List<LivraisonDetail> livraisonDetails) {
        this.livraisonDetails = livraisonDetails;
    }
    
    private int id  = 0;
    private String dateLivraison = "";
    private String refLivraison = "";
    private String dateOperation = "";
    private String refOperation = "";
    private int idCommande = 0;
    private int idClient  = 0;
    private float montant = 0.0f;
    private String devise = "";
    private String description = "";
    private float frais = 0.0f;
    private Personne client = null;
    private String status = "";
    private String typeLivraison= "Globale";
    private List<LivraisonDetail> livraisonDetails = null;

    public Personne getClient() {
        return client;
    }

    public void setClient(Personne client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(String typeLivraison) {
        this.typeLivraison = typeLivraison;
    }
}
