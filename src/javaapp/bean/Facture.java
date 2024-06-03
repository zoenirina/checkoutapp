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
public class Facture {

    /**
     * @return the refCommande
     */
    public String getRefCommande() {
        return refCommande;
    }

    /**
     * @param refCommande the refCommande to set
     */
    public void setRefCommande(String refCommande) {
        this.refCommande = refCommande;
    }

    /**
     * @return the idLivraison
     */
    public int getIdLivraison() {
        return idLivraison;
    }

    /**
     * @param idLivraison the idLivraison to set
     */
    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    /**
     * @return the idClient
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * @param idClient the idClient to set
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public String getRefFacture() {
        return refFacture;
    }

    public void setRefFacture(String refFacture) {
        this.refFacture = refFacture;
    }

    public int getIdReception() {
        return idReception;
    }

    public void setIdReception(int idReception) {
        this.idReception = idReception;
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

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }
    
    private int id  = 0;
    private String dateFacture  = "";
    private String refFacture = "";
    private String refCommande = "";
    private int idReception  = 0;
    private int idLivraison  = 0;
    private int idFournisseur  = 0;
    private int idClient  = 0;
    private Personne personne= null;
    private Livraison livraison = null;
    private float montant  = 0.0f;
    private String devise  = "";
    private String description = "";
    private String pj  = "";
    private String status  = "";

    /**
     * @return the personne
     */
    public Personne getPersonne() {
        return personne;
    }

    /**
     * @param personne the personne to set
     */
    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the livraison
     */
    public Livraison getLivraison() {
        return livraison;
    }

    /**
     * @param livraison the livraison to set
     */
    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public void setAdresse(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
