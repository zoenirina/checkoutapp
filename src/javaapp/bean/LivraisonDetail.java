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
public class LivraisonDetail {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommandeDetail() {
        return idCommandeDetail;
    }

    public void setIdCommandeDetail(int idCommandeDetail) {
        this.idCommandeDetail = idCommandeDetail;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantiteRecu() {
        return quantiteRecu;
    }

    public void setQuantiteRecu(int quantiteRecu) {
        this.quantiteRecu = quantiteRecu;
    }

    public int getQuantiteValide() {
        return quantiteValide;
    }

    public void setQuantiteValide(int quantiteValide) {
        this.quantiteValide = quantiteValide;
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

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    private int id  = 0;
    private int idCommandeDetail   = 0;
    private int idProduit   = 0;
    private int quantiteRecu  = 0;
    private int quantiteValide  = 0;
    private float montant  = 0.0f;
    private String devise = "";
    private int idLivraison  = 0;
    private String description  = "";
    private String dateLivraison = "";
    private String status  = "";
    
    private Produit produit  = null;
    private CommandeDetail commandeDetail  = null;

    /**
     * @return the produit
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * @param produit the produit to set
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    /**
     * @return the commandeDetail
     */
    public CommandeDetail getCommandeDetail() {
        return commandeDetail;
    }

    /**
     * @param commandeDetail the commandeDetail to set
     */
    public void setCommandeDetail(CommandeDetail commandeDetail) {
        this.commandeDetail = commandeDetail;
    }
       
}
