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
public class Paiement {

    public Paiement(int id, String datePaiement, String refPaiement, int idFacture, int idClient, float montantRecu, float restePaye) {
        this.datePaiement=datePaiement;
        this.refPaiement=refPaiement;
        this.idFacture=idFacture;
        this.idClient=idClient;
        this.montantRecu=montantRecu;
        this.restePaye=restePaye;
        this.datePaiement=datePaiement;
        this.datePaiement=datePaiement;
        this.datePaiement=datePaiement;
        
        this.id = id;
    }

    public Paiement() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getRefPaiement() {
        return refPaiement;
    }

    public void setRefPaiement(String refPaiement) {
        this.refPaiement = refPaiement;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public float getMontantRecu() {
        return montantRecu;
    }

    public void setMontantRecu(float montantRecu) {
        this.montantRecu = montantRecu;
    }

    public float getRestePaye() {
        return restePaye;
    }

    public void setRestePaye(float restePaye) {
        this.restePaye = restePaye;
    }
    
    private int id   = 0;
    private String datePaiement   = "";
    private String refPaiement  = "";
    private int idFacture   = 0;
    private int idClient  = 0;
    private float montantRecu  = 0.0f;
    private float restePaye   = 0.0f;
    
    private Facture facture=null;
    private Personne client = null;

    /**
     * @return the facture
     */
    public Facture getFacture() {
        return facture;
    }

    /**
     * @param facture the facture to set
     */
    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    /**
     * @return the client
     */
    public Personne getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Personne client) {
        this.client = client;
    }
    
}
