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
public class CommandeDetail {

    /**
     * @return the quantiteLivree
     */
    public int getQuantiteLivree() {
        return quantiteLivree;
    }

    /**
     * @param quantiteLivree the quantiteLivree to set
     */
    public void setQuantiteLivree(int quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }

    /**
     * @return the quantiteRestante
     */
    public int getQuantiteRestante() {
        return quantiteRestante;
    }

    /**
     * @param quantiteRestante the quantiteRestante to set
     */
    public void setQuantiteRestante(int quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    private int id = 0;
    private int idProduit = 0;
    private int quantite = 0;
    private Float montant = 0.0f;
    private String devise = "";
    private int idCommande = 0;
    private String description = "";
    private Produit produit = null;
    private float remise  = 0.0f;
    
    private int quantiteLivree = 0;
    private int quantiteRestante = 0;
    
    public CommandeDetail (){}
    
    public CommandeDetail (int id, int idProduit, int quantite, Float montant, String devise, int idCommande, String description){}
    
    public CommandeDetail (int id, Produit produit, int quantite, Float montant, String devise, int idCommande, String description){}

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    
}