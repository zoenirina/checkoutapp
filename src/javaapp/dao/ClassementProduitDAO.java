/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javaapp.bean.ClassementProduit;
import javaapp.bean.Produit;

/**
 *
 * @author ZOENIRINA
 */
public class ClassementProduitDAO {
  protected Connection conn = null;
  protected ResultSet result;
  protected PreparedStatement stm;
    
    public ClassementProduitDAO(Connection conn) {
        this.conn=conn;    
    }
    
      public List<ClassementProduit> getTopProduits(String query) {
        List<ClassementProduit> classementProduits = new ArrayList<>();

        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();

            while (result.next()) {
                
                Produit produit = new Produit();
                produit.setDesignation(result.getString("nomProduit"));
                produit.setId(result.getInt("idProduit"));
                produit.setRefProduit(result.getString("refProduit"));
                
                ClassementProduit classementProduit = new ClassementProduit();
                classementProduit.setProduit(produit);
                classementProduit.setQuantiteTotaleVendue(result.getInt("quantiteTotaleVendue"));

                classementProduits.add(classementProduit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classementProduits;
    }

    public List<ClassementProduit> getTopProduitsMoisEnCours() {
        String query = "SELECT " +
                       "    p.id AS idProduit, " +
                       "    p.designation AS nomProduit,  p.refProduit AS refProduit, " +
                       "    SUM(ms.quantite) AS quantiteTotaleVendue " +
                       "FROM " +
                       "    mouvementStock ms " +
                       "JOIN " +
                       "    produits p ON ms.idProduit = p.id " +
                       "WHERE " +
                       "    ms.sens = 'Sortie' AND " +
                       "    ms.estValide = 'Oui' AND " +
                       "    strftime('%Y-%m', ms.dateMouvement) = strftime('%Y-%m', 'now') " +
                       "GROUP BY " +
                       "    ms.idProduit " +
                       "ORDER BY " +
                       "    quantiteTotaleVendue DESC " +
                       "LIMIT 3;";
        return getTopProduits(query);
    }

    public List<ClassementProduit> getTopProduitsAnneeEnCours() {
        String query = "SELECT " +
                       "    p.id AS idProduit, " +
                       "    p.designation AS nomProduit, p.refProduit AS refProduit, " +
                       "    SUM(ms.quantite) AS quantiteTotaleVendue " +
                       "FROM " +
                       "    mouvementStock ms " +
                       "JOIN " +
                       "    produits p ON ms.idProduit = p.id " +
                       "WHERE " +
                       "    ms.sens = 'Sortie' AND " +
                       "    ms.estValide = 'Oui' AND " +
                       "    strftime('%Y', ms.dateMouvement) = strftime('%Y', 'now') " +
                       "GROUP BY " +
                       "    ms.idProduit " +
                       "ORDER BY " +
                       "    quantiteTotaleVendue DESC " +
                       "LIMIT 3;";
        return getTopProduits(query);
    }
    
}
