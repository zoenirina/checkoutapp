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
public class ClassementProduit {

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantiteTotaleVendue() {
        return quantiteTotaleVendue;
    }

    public void setQuantiteTotaleVendue(int quantiteTotaleVendue) {
        this.quantiteTotaleVendue = quantiteTotaleVendue;
    }
   private Produit produit;
   private int quantiteTotaleVendue;
   
}
