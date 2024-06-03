/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Stock;
import javaapp.factory.DAOFactory;


/**
 *
 * @author ASUS
 */
public class StockDAO extends DAO<Stock> {
    final String SQL_SELECT = "SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non' ) AS quantiteReserve  FROM produits p ";
    private ProduitDAO produitDAO;
    public StockDAO(Connection conn) {
        super(conn);
        produitDAO = DAOFactory.getProduitDAO();
    }

    @Override
    public List<Stock> select() {
         List<Stock> stocks= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
            Stock stock = new Stock();
            stock.setProduit( produitDAO.find(result.getInt("idProduit")) );
            stock.setQuantiteStock(result.getInt("quantite_stock"));
            stock.setQuantiteReserve(result.getInt("quantiteReserve"));
            stocks.add(stock);
         }
	}catch(SQLException ex) {
	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return stocks;
    }
    
    public Stock find(String refProduit) {
        Stock stock = new Stock();
        try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE p.refProduit = ?  OR p.designation like '%"+refProduit+"%'  ");
        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {            
            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
            stock.setQuantiteStock(result.getInt("quantite_stock"));
            stock.setQuantiteReserve(result.getInt("quantiteReserve"));            
         }
	} catch(SQLException ex) {
	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return stock;
    }
    
    public Stock findByProduitMagasin(String refProduit, String refMagasin) {
        final String sql = "SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non'  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantiteReserve  FROM produits p  WHERE p.refProduit = ? ";

        Stock stock = new Stock();
        try {
	stm = conn.prepareStatement(sql);
        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {            
            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
            stock.setQuantiteStock(result.getInt("quantite_stock"));
            stock.setQuantiteReserve(result.getInt("quantiteReserve"));            
         }
	} catch(SQLException ex) {
	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return stock;
    }
    
    public Stock findByMagasin( String refMagasin) {
        final String sql = "SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non'  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantiteReserve  FROM produits p ";

        Stock stock = new Stock();
        try {
	stm = conn.prepareStatement(sql);
//        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {            
            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
            stock.setQuantiteStock(result.getInt("quantite_stock"));
            stock.setQuantiteReserve(result.getInt("quantiteReserve"));            
         }
	} catch(SQLException ex) {
	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return stock;
    }
    
    @Override
    public boolean create(Stock obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Stock obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Stock obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stock find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void closeRessources() {
    try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public List<Stock> filterByProduitMagasin(String refProduit, String refMagasin) {
//        List<Stock> stocks = new ArrayList<>();
        final String sql = "SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non'  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantiteReserve  FROM produits p  "
                + "WHERE p.refProduit like '%"+refProduit+"%' OR p.designation like '%"+refProduit+"%'  ";


return getStockWhere(sql);
    }

    public List<Stock> filterByProduit(String refProduit) {
//    List<Stock> stocks = new ArrayList<>();
        final String sql = " SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id   ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non' ) AS quantiteReserve  FROM produits p  WHERE p.refProduit like '%"+refProduit+"%'  OR p.designation like '%"+refProduit+"%'  ";

//        try {
//	stm = conn.prepareStatement(sql);
//	result = stm.executeQuery();
//	while(result.next()) {  
//            Stock stock = new Stock();
//            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
//            stock.setQuantiteStock(result.getInt("quantite_stock"));
//            stock.setQuantiteReserve(result.getInt("quantiteReserve"));  
//            stocks.add(stock);
//         }
//	} catch(SQLException ex) {
//	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
//	} finally {
//           closeRessources();
//        }
//        return stocks;
return getStockWhere(sql);
    }

    public List<Stock> filterByMagasin(String refMagasin) {
//        List<Stock> stocks = new ArrayList<>();
        final String sql = "SELECT p.id AS idProduit, p.designation, p.PU AS prix, p.refProduit,  (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
            + " FROM mouvementStock m   WHERE m.idProduit = p.id  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantite_stock,  (  SELECT COALESCE(SUM(m.quantite), 0)  FROM mouvementStock m  WHERE m.idProduit = p.id AND m.sens = 'Sortie' AND m.estValide = 'Non'  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantiteReserve  FROM produits p ";

        return getStockWhere(sql);
    }
    
    private List<Stock> getStockWhere(String sql){
        List<Stock> stocks = new ArrayList<>();
         try {
	stm = conn.prepareStatement(sql);
//        stm.setString(1, refProduit);
	result = stm.executeQuery();
	while(result.next()) {  
            Stock stock = new Stock();
            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
            stock.setQuantiteStock(result.getInt("quantite_stock"));
            stock.setQuantiteReserve(result.getInt("quantiteReserve"));  
            stocks.add(stock);
         }
	} catch(SQLException ex) {
	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
         return stocks;
    }
    
//    public int getQteStockInMagasin(String){
//    final String sql = " (   SELECT COALESCE(SUM(CASE WHEN m.sens = 'Entrée' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0) -  "
//            + " COALESCE(SUM(CASE WHEN m.sens = 'Sortie' AND m.estValide = 'Oui' THEN m.quantite ELSE 0 END), 0)  "
//            + " FROM mouvementStock m   WHERE m.idProduit = p.id  AND m.idMagasin= ( SELECT id from magasin WHERE refMagasin='"+refMagasin+"') ) AS quantite_stock, "
//            + " FROM produits p  WHERE p.refProduit = ? ";
//
//        Stock stock = new Stock();
//        try {
//	stm = conn.prepareStatement(sql);
//        stm.setString(1, refProduit);
//	result = stm.executeQuery();
//	while(result.next()) {            
//            stock.setProduit( produitDAO.find(result.getInt("idProduit")));
//            stock.setQuantiteStock(result.getInt("quantite_stock"));         
//         }
//	} catch(SQLException ex) {
//	    Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
//	} finally {
//           closeRessources();
//        }
//        return 0;
//    }
 
}
