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
public class CommandeF extends Commande {

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public Personne getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Personne fournisseur) {
        this.fournisseur = fournisseur;
    }
//
//    public List<CommandeDetail> getCommandeDetails() {
//        return commandeDetails;
//    }
//
//    public void setCommandeDetails(List<CommandeDetail> commandeDetails) {
//        this.commandeDetails = commandeDetails;
//    }
    
    private int idFournisseur = 0;
    private String dateFinValidite = "";
    private Personne fournisseur = null;
    // nouveau attribut
    private List<CommandeDetailFournisseur> commandeDetails = null;

    public String getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(String dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    /**
     * @return the commandeDetails
     */
    public List<CommandeDetailFournisseur> getCommandeDetails() {
        return commandeDetails;
    }

    /**
     * @param commandeDetails the commandeDetails to set
     */
    public void setCommandeDetails(List<CommandeDetailFournisseur> commandeDetails) {
        this.commandeDetails = commandeDetails;
    }


}
