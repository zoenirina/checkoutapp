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

    public int getIdSourceSortie() {
        return idSourceSortie;
    }

    public void setIdSourceSortie(int idSourceSortie) {
        this.idSourceSortie = idSourceSortie;
    }

    public int getIdSourceEntree() {
        return idSourceEntree;
    }

    public void setIdSourceEntree(int idSourceEntree) {
        this.idSourceEntree = idSourceEntree;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantiteReserve() {
        return quantiteReserve;
    }

    public void setQuantiteReserve(int quantiteReserve) {
        this.quantiteReserve = quantiteReserve;
    }

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
    
    private Magasin magasin = null;
    private String refMagasin = "";
    private int quantiteStock = 0;
    private Produit produit= null;
    private int quantiteReserve = 0;
    private int idSourceSortie = 0;
    private int idSourceEntree = 0;
    private String estValide = "Oui";
    private TypeMouvement typeMouvement;

    /**
     * @return the magasin
     */
    public Magasin getMagasin() {
        return magasin;
    }

    /**
     * @param magasin the magasin to set
     */
    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    /**
     * @return the quantiteStock
     */
    public int getQuantiteStock() {
        return quantiteStock;
    }

    /**
     * @param quantiteStock the quantiteStock to set
     */
    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    /**
     * @return the refMagasin
     */
    public String getRefMagasin() {
        return refMagasin;
    }

    /**
     * @param refMagasin the refMagasin to set
     */
    public void setRefMagasin(String refMagasin) {
        this.refMagasin = refMagasin;
    }

    /**
     * @return the estValide
     */
    public String getEstValide() {
        return estValide;
    }

    /**
     * @param estValide the estValide to set
     */
    public void setEstValide(String estValide) {
        this.estValide = estValide;
    }

    /**
     * @return the typeMouvement
     */
    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    /**
     * @param typeMouvement the typeMouvement to set
     */
    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }
}
