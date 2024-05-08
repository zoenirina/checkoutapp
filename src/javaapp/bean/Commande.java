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
public class Commande {

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

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
   
    
    private int id = 0;
    private String dateCommande = "";
    private String refCommande = "";
    private int idClient = 0;
    private int quantite = 0;
    private float montant = 0.0f;
    private String devise = "";
    private int idCreateur = 0;
    private String description = "";
    private String pj = "";
}
