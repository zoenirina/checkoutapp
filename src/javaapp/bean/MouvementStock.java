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
public class MouvementStock {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(String dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public int getIdTypeMouvement() {
        return idTypeMouvement;
    }

    public void setIdTypeMouvement(int idTypeMouvement) {
        this.idTypeMouvement = idTypeMouvement;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public int getIdMagasin() {
        return idMagasin;
    }

    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }

    public int getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(int idExercice) {
        this.idExercice = idExercice;
    }

    /**
     * @return the idCreateur
     */
    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public int getIdCommandeDet() {
        return idCommandeDet;
    }

    public void setIdCommandeDet(int idCommandeDet) {
        this.idCommandeDet = idCommandeDet;
    }
    private int id = 0;
    private String description = "";
    private int idProduit = 0;
    private int quantite = 0;
    private String dateMouvement = "";
    private int idTypeMouvement = 0;
    private String sens = "";
    private int idMagasin = 0;
    private int idExercice = 0;
    private int idCreateur = 0;
    private int idCommandeDet = 0;
}
