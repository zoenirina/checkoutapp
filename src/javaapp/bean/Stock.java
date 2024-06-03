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
public class Stock {

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public int getQuantiteReserve() {
        return quantiteReserve;
    }

    public void setQuantiteReserve(int quantiteReserve) {
        this.quantiteReserve = quantiteReserve;
    }
    private Produit produit = null;
    private int quantiteStock = 0;
    private int quantiteReserve = 0;
}
