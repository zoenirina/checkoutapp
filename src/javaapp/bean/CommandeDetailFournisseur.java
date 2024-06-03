/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

public class CommandeDetailFournisseur {

    /**
     * @return the quantiteLivree
     */
   

    /**
     * @return the quantiteRestante
     */
    public int getQuantiteRestante() {
        return quantiteRestante;
    }

    /**
     * @param quantiteRestante the quantiteRestante to set
     */
    public void setQuantiteRestante(int quantiteRestante) {
        this.quantiteRestante = quantiteRestante;
    }
    private int id;
    private int idProduit;
    private int quantite;
    private float montant;
    private float remise;
    private String devise;
    private int idCommande;
    private String description;
    private Produit produit;  // Assuming you have a Produit class
    
        private int quantiteRecue = 0;
    private int quantiteRestante = 0;

    public CommandeDetailFournisseur() {}

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "CommandeDetailFournisseur{" +
                "id=" + id +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", montant=" + montant +
                ", devise='" + devise + '\'' +
                ", idCommande=" + idCommande +
                ", description='" + description + '\'' +
                ", produit=" + produit +
                '}';
    }

    /**
     * @return the remise
     */
    public float getRemise() {
        return remise;
    }

    /**
     * @param remise the remise to set
     */
    public void setRemise(float remise) {
        this.remise = remise;
    }

    /**
     * @return the quantiteRecue
     */
    public int getQuantiteRecue() {
        return quantiteRecue;
    }

    /**
     * @param quantiteRecue the quantiteRecue to set
     */
    public void setQuantiteRecue(int quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }
}
