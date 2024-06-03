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
public class CommandeC extends Commande {

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
  
    public Personne getClient() {
        return client;
    }

    public void setClient(Personne personne) {
        this.client = personne;
    }
   
    public List<CommandeDetail> getCommandeDetails() {
        return commandeDetails;
    }

    public void setCommandeDetails(List<CommandeDetail> commandeDetails) {
        this.commandeDetails = commandeDetails;
    }
    
    private int idClient = 0;
    private String dateFinValidite = "";
    private Personne client = null;
    // nouveau attribut
    private List<CommandeDetail> commandeDetails = null;

    public String getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(String dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

}
