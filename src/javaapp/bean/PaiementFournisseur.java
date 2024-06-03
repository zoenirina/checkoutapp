/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

import java.util.Date;

/**
 * Bean class for PaiementFournisseur.
 */
public class PaiementFournisseur {

    private int id;
    private String datePaiement;
    private String refPaiement;
    private int idFactureFournisseur;
    private int idFournisseur;
    private float montantRecu;
    private float restePaye;
    private FactureFournisseur factureFournisseur;
    private Personne fournisseur;

public PaiementFournisseur(int id, String datePaiement, String refPaiement, int idFactureFournisseur, int idFournisseur, float montantRecu, float restePaye) {
        this.datePaiement=datePaiement;
        this.refPaiement=refPaiement;
        this.idFactureFournisseur=idFactureFournisseur;
        this.idFournisseur=idFournisseur;
        this.montantRecu=montantRecu;
        this.restePaye=restePaye;
//        this.datePaiement=datePaiement;
//        this.datePaiement=datePaiement;
//        this.datePaiement=datePaiement;
        
        this.id = id;
    }

    public PaiementFournisseur() {
    }
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

    public int getIdFactureFournisseur() {
        return idFactureFournisseur;
    }

    public void setIdFactureFournisseur(int idFactureFournisseur) {
        this.idFactureFournisseur = idFactureFournisseur;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
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

    public Personne getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Personne fournisseur) {
        this.fournisseur = fournisseur;
    }

    /**
     * @return the factureFournisseur
     */
    public FactureFournisseur getFactureFournisseur() {
        return factureFournisseur;
    }

    /**
     * @param factureFournisseur the factureFournisseur to set
     */
    public void setFactureFournisseur(FactureFournisseur factureFournisseur) {
        this.factureFournisseur = factureFournisseur;
    }
}
