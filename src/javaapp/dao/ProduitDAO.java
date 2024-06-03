/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Produit;
import javaapp.bean.UniteStockage;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class ProduitDAO extends DAO<Produit>{
 
    final static String SQL_INSERT = "INSERT INTO produits (designation, refProduit, idUnite, PU, dateCreation, dateModif, PUHT, TVA) VALUES (?, ?, ?, ?, ?, ?,?,?)";
//    final static String SQL_SELECT = "SELECT  p.*,p.id AS idProduit, u.* FROM   produits p LEFT JOIN uniteStockage u ON p.idUnite = u.id";
    final static String SQL_SELECT = "SELECT  * FROM   produits ";
    final static String SQL_UPDATE = "UPDATE produits SET designation = ?, refProduit = ?, PU = ?, dateModif = ?, PUHT = ?, TVA= ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    final static String SQL_FILTER = SQL_SELECT+" WHERE refProduit like ? ";
    
    private UniteStockageDAO uniteDAO;
    
    
    private int idGenerated=0;
    public ProduitDAO(Connection conn) {
        super(conn);
        uniteDAO = DAOFactory.getUniteStockageDAO();
    }

    @Override
    public List<Produit> select() {
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
            Produit p = getProduit(result);
            produits.add(p);
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return produits;
    }

    public List<Produit> selectAll() {
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           Produit p = getProduit(result);
           produits.add(p);
           
            
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeRessources();
        }
        return produits;
    }
    
    @Override
    public boolean create(Produit produit) {
    int affectedRows = 0;
    
//    UniteStockageDAO uniteStockDAO = DAOFactory.getUniteStockageDAO();
    uniteDAO.create(produit.getUniteStockage());
                 
    int idUnite = uniteDAO.getIdGenerated();
    System.out.println("L'ID généré pour la nouvelle unité est : " + idUnite);
                    
         try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, produit.getDesignation());
            stm.setString(2,  produit.getRefProduit());
            stm.setInt(3,  idUnite); // Utilisation de l'ID d'unité généré
            stm.setFloat(4, produit.getPU()); // Prix unitaire
            stm.setString(5,  produit.getDateCreation()); // Date de création
            stm.setString(6, produit.getDateModif());
            stm.setFloat(7, produit.getPUHT());
            stm.setFloat(8, produit.getTVA());
                               
            affectedRows = stm.executeUpdate();
              
        } catch (SQLException e) {
        } finally {
            closeRessources();
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
            stm.setFloat(5, produit.getPUHT());
            stm.setFloat(6, produit.getTVA());
            stm.setInt(7, produit.getId()); // ID du produit à modifier
           
            affectedRows = stm.executeUpdate();
            
            UniteStockage u = produit.getUniteStockage();
            uniteDAO.update(u);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (affectedRows != 0);
   
    }

    @Override
    public Produit find(int id) {
    Produit produit= new Produit();
//    UniteStockageDAO unitDAO= DAOFactory.getUniteStockageDAO();
        try {
	stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
           produit = getProduit(result);
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeRessources();
        }
        return produit;
    }

    public Produit find(String refProduit) {
    Produit produit = new Produit();
//    UniteStockageDAO unitDAO = DAOFactory.getUniteStockageDAO();
        try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE refProduit = ? ");
        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
            produit = getProduit(result);
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        
        return produit;
    }

    public int getIdByRef(String refProduit){
        try {
	stm = conn.prepareStatement("SELECT  id FROM   produits  WHERE refProduit = ? ");
        stm.setString(1, refProduit);
	result = stm.executeQuery();
	if(result.next()) {
            return result.getInt("id");
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return 0;
    }
    
    public List<Produit> filter(String keySearch) {
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE designation like '%"+keySearch+"%' or refProduit like '%"+keySearch+"%'");
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
            Produit p = getProduit(result); 
           produits.add(p);
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            closeRessources();
        }
        return produits;
    }
    
    public int getIdGenerated() {
        return idGenerated;
    }

    public void setIdGenerated(int idGenerated) {
        this.idGenerated = idGenerated;
    }
    
    public void closeRessources(){
    try {
                if(stm != null)stm.close();
                if(result != null)result.close();
//                if(conn != null)conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private Produit getProduit(ResultSet result) throws SQLException{
     Produit p = new Produit(result.getInt("id"), result.getString("designation"), result.getString("refProduit"), result.getFloat("PU"), result.getString("dateCreation"), result.getString("dateModif"), result.getInt("idEtat"));
            p.setPUHT(result.getFloat("PUHT"));
            p.setTVA(result.getFloat("TVA")); 
            
    return p;
    }
    
        public List<Produit> selectStock() {
            String sql = "";
    List<Produit> produits= new ArrayList<>();
        try {
	stm = conn.prepareStatement(sql);
	result = stm.executeQuery();
	while(result.next()) {
           //appel du UniteStockageDAO pour la recherche de l'unité de stockage
            Produit p = getProduit(result);
            produits.add(p);
         }
        
	}catch(SQLException ex) {
	    Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return produits;
    }
    
}
