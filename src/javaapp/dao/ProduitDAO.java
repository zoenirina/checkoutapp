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
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Produit;
import javaapp.bean.UniteStockage;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author ZOENIRINA
 */
public class ProduitDAO extends DAO<Produit>{

    final static String SQL_INSERT = "INSERT INTO produits (designation, refProduit, idUnite, PU, dateCreation, dateModif) VALUES (?, ?, ?, ?, ?, ?)";
//    final static String SQL_SELECT = "SELECT  p.*,p.id AS idProduit, u.* FROM   produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";
    final static String SQL_SELECT = "SELECT  * FROM   produits ";
    final static String SQL_UPDATE = "UPDATE produits SET designation = ?, refProduit = ?, PU = ?, dateModif = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    final static String SQL_FILTER = SQL_SELECT+" WHERE refProduit like ? ";
    
//    private ResultSet result;
//    private PreparedStatement stm;
    
    private int idGenerated=0;
    public ProduitDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Produit> select() {
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           Produit p = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"));
            produits.add(p);
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produits;
    }

    public List<Produit> selectAll() {
    List<Produit> produits= new ArrayList<>();
    UniteStockageDAO unitDAO= DAOFactory.getUniteStockageDAO();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           Produit p = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"), unitDAO.find(result.getInt("idUnite")));
            produits.add(p);
           
            
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produits;
    }
    
    @Override
    public boolean create(Produit produit) {
    int affectedRows = 0;
    
    UniteStockageDAO uniteStockDAO = DAOFactory.getUniteStockageDAO();
    uniteStockDAO.create(produit.getUniteStockage());
                 
    int idUnite = uniteStockDAO.getIdGenerated();
    System.out.println("L'ID généré pour la nouvelle unité est : " + idUnite);
                    
         try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, produit.getDesignation());
            stm.setString(2,  produit.getRefProduit());
            stm.setInt(3,  idUnite); // Utilisation de l'ID d'unité généré
            stm.setFloat(4, produit.getPU()); // Prix unitaire
            stm.setString(5,  produit.getDateCreation()); // Date de création
            stm.setString(6, produit.getDateModif());
                               
            affectedRows = stm.executeUpdate();
              
        } catch (SQLException e) {
        } finally {
            try {
                if(stm != null)stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return (affectedRows != 0); 
    }

    @Override
    public boolean delete(Produit obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Produit produit) {
    int affectedRows = 0;
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, produit.getDesignation());
            stm.setString(2, produit.getRefProduit());
            stm.setFloat(3, produit.getPU()); // Nouveau prix
            stm.setString(4, produit.getDateModif()); // Date de modification
            stm.setInt(5, produit.getId()); // ID du produit à modifier
            affectedRows = stm.executeUpdate();
            
            UniteStockage u = produit.getUniteStockage();
            DAOFactory.getUniteStockageDAO().update(u);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (affectedRows != 0);
   
    }

    @Override
    public Produit find(int id) {
    Produit produit= new Produit();
    UniteStockageDAO unitDAO= DAOFactory.getUniteStockageDAO();
        try {
	stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           produit = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"), unitDAO.find(result.getInt("idUnite")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produit;
    }

    public Produit find(String refProduit) {
    Produit produit= new Produit();
    UniteStockageDAO unitDAO= DAOFactory.getUniteStockageDAO();
        try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE refProduit ?");
        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           produit = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"), unitDAO.find(result.getInt("idUnite")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produit;
    }

    public List<Produit> filter(String keySearch) {
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE designation like '%"+keySearch+"%' or refProduit like '%"+keySearch+"%'");
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           Produit p = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"));
            produits.add(p);
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produits;
    }
    
    public int getIdGenerated() {
        return idGenerated;
    }

    public void setIdGenerated(int idGenerated) {
        this.idGenerated = idGenerated;
    }
    
    
    
}
