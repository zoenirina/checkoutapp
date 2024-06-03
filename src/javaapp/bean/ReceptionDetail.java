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
public class ReceptionDetail {

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
    public CommandeDetailFournisseur getCommandeDetail() {
        return commandeDetail;
    }

    /**
     * @param commandeDetail the commandeDetail to set
     */
    public void setCommandeDetail(CommandeDetailFournisseur commandeDetail) {
        this.commandeDetail = commandeDetail;
    }

    private int id;
    private int idCommandeDetailFournisseur;
    private int idProduit;
    private int quantiteRecu;
    private int quantiteValide;
    private float montant;
    private String devise;
    private int idReception;
    private String description;
    private String dateReception;
    private String status;
    private Produit produit  = null;
    private CommandeDetailFournisseur commandeDetail  = null;
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommandeDetailFournisseur() {
        return idCommandeDetailFournisseur;
    }

    public void setIdCommandeDetailFournisseur(int idCommandeDetailFournisseur) {
        this.idCommandeDetailFournisseur = idCommandeDetailFournisseur;
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

    public int getIdReception() {
        return idReception;
    }

    public void setIdReception(int idReception) {
        this.idReception = idReception;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateReception() {
        return dateReception;
    }

    public void setDateReception(String dateReception) {
        this.dateReception = dateReception;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}