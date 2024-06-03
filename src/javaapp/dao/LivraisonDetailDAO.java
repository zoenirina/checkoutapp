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
import javaapp.bean.LivraisonDetail;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ASUS
 */
public class LivraisonDetailDAO extends DAO<LivraisonDetail>{

    private int generatedKey = 0;
    private final CommandeDetailDAO commDetDAO;
    private final ProduitDAO produitDAO;
    
    public LivraisonDetailDAO(Connection conn) {
        super(conn);
        commDetDAO = DAOFactory.getCommandeDetailDAO();
        produitDAO = DAOFactory.getProduitDAO();
    }
    
    /**
     *
     * @param livraisonDetails
     * @return
     * @throws SQLException
     */
    @Override
   public boolean create(LivraisonDetail livraisonDetails){
        String sql = "INSERT INTO livraisonDetails (idCommandeDetail, idProduit, quantiteRecu, quantiteValide, montant, "
                + "devise, idLivraison, description, dateLivraison, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int affectedRow = 0;
        try {
            stm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            stm.setInt(1, livraisonDetails.getIdCommandeDetail());
            stm.setInt(2, livraisonDetails.getIdProduit());
            stm.setInt(3, livraisonDetails.getQuantiteRecu());
            stm.setInt(4, livraisonDetails.getQuantiteValide());
            stm.setFloat(5, livraisonDetails.getMontant());
            stm.setString(6, livraisonDetails.getDevise());
            stm.setInt(7, livraisonDetails.getIdLivraison());
            stm.setString(8, livraisonDetails.getDescription());
            stm.setString(9, livraisonDetails.getDateLivraison());
            stm.setString(10, livraisonDetails.getStatus());
            affectedRow = stm.executeUpdate();

            result = stm.getGeneratedKeys();
                if (result.next()) {
                setGeneratedKey(result.getInt(1));
                }
                
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return affectedRow != 0; 
    }

    @Override
    public boolean update(LivraisonDetail livraisonDetails){
        String sql = "UPDATE livraisonDetails SET idCommandeDetail = ?, idProduit = ?, quantiteRecu = ?, quantiteValide = ?, montant = ?, devise = ?, idLivraison = ?, description = ?, dateLivraison = ?, status = ? WHERE id = ?";
        int affectedRow = 0;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, livraisonDetails.getIdCommandeDetail());
            stm.setInt(2, livraisonDetails.getIdProduit());
            stm.setInt(3, livraisonDetails.getQuantiteRecu());
            stm.setInt(4, livraisonDetails.getQuantiteValide());
            stm.setFloat(5, livraisonDetails.getMontant());
            stm.setString(6, livraisonDetails.getDevise());
            stm.setInt(7, livraisonDetails.getIdLivraison());
            stm.setString(8, livraisonDetails.getDescription());
            stm.setString(9, livraisonDetails.getDateLivraison());
            stm.setString(10, livraisonDetails.getStatus());
            stm.setInt(11, livraisonDetails.getId());
            
            affectedRow = stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRow != 0; 
    }

    public LivraisonDetail findById(int idLivraison){
        String sql = "SELECT * FROM livraisonDetails WHERE idLivraison = ?";
        LivraisonDetail livraisonDetails = new LivraisonDetail();
        
        try{    
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
            
            while (result.next()) {

                livraisonDetails.setId(result.getInt("id"));
                livraisonDetails.setIdCommandeDetail(result.getInt("idCommandeDetail"));
                livraisonDetails.setIdProduit(result.getInt("idProduit"));
                livraisonDetails.setQuantiteRecu(result.getInt("quantiteRecu"));
                livraisonDetails.setQuantiteValide(result.getInt("quantiteValide"));
                livraisonDetails.setMontant(result.getFloat("montant"));
                livraisonDetails.setDevise(result.getString("devise"));
                livraisonDetails.setIdLivraison(result.getInt("idLivraison"));
                livraisonDetails.setDescription(result.getString("description"));
                livraisonDetails.setDateLivraison(result.getString("dateLivraison"));
                livraisonDetails.setStatus(result.getString("status"));
            }

            return livraisonDetails;
        
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }


    @Override
    public List<LivraisonDetail> select() {
    String sql = "SELECT * FROM livraisonDetails WHERE dateLivraison BETWEEN ? AND ?";
        List<LivraisonDetail> livraisonDetailsList = new ArrayList<>();

        try {
            stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                LivraisonDetail livraisonDetails = new LivraisonDetail();
                livraisonDetails.setId(rs.getInt("id"));
                livraisonDetails.setIdCommandeDetail(rs.getInt("idCommandeDetail"));
                livraisonDetails.setIdProduit(rs.getInt("idProduit"));
                livraisonDetails.setQuantiteRecu(rs.getInt("quantiteRecu"));
                livraisonDetails.setQuantiteValide(rs.getInt("quantiteValide"));
                livraisonDetails.setMontant(rs.getFloat("montant"));
                livraisonDetails.setDevise(rs.getString("devise"));
                livraisonDetails.setIdLivraison(rs.getInt("idLivraison"));
                livraisonDetails.setDescription(rs.getString("description"));
                livraisonDetails.setDateLivraison(rs.getString("dateLivraison"));
                livraisonDetails.setStatus(rs.getString("status"));

                livraisonDetailsList.add(livraisonDetails);
            }
            } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return livraisonDetailsList;
    }

    @Override
    public boolean delete(LivraisonDetail ld) {
     String sql = "DELETE FROM livraisonDetails WHERE id = ?";
        int i = 0;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, ld.getId());
            i = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i != 0;
    }
    
    public boolean delete(int id) {
     String sql = "DELETE FROM livraisonDetails WHERE id = ?";
        int i = 0;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            i = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return i != 0;
    }

public boolean delete(int idLivraison, int idProduit) {
         String SQL_DELETE = "DELETE FROM commandeDetails WHERE idCommande = ? AND idProduit = ?";
         int rowAffected=0;
            try {
            stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, idLivraison);
            stm.setInt(2, idProduit);
            rowAffected = stm.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
            return rowAffected == 1;
    }

    @Override
    public LivraisonDetail find(int id) {
    String sql = "SELECT * FROM livraisonDetails WHERE id= ?";
        LivraisonDetail livraisonDetail = null;

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            result = stm.executeQuery();
            
            if (result.next()) {
                livraisonDetail = getLivDet(result);
            }
            } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return livraisonDetail;
    }

    public int getGeneratedKey() {
        return generatedKey;
    }

    /**
     * @param generatedKey the generatedKey to set
     */
    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
    public List<LivraisonDetail> select(int idLivraison) {
    String sql = "SELECT * FROM livraisonDetails WHERE idLivraison= ?";
        List<LivraisonDetail> livraisonDetailsList = new ArrayList<>();

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
            
            while (result.next()) {
                LivraisonDetail livraisonDetails = getLivDet(result);
                livraisonDetailsList.add(livraisonDetails);
            }
            } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return livraisonDetailsList;
    }
    
    private LivraisonDetail getLivDet(ResultSet result) throws SQLException{
        
        LivraisonDetail livDet = new LivraisonDetail();
        livDet.setId(result.getInt("id"));
        livDet.setIdCommandeDetail(result.getInt("idCommandeDetail"));
        livDet.setIdProduit(result.getInt("idProduit"));
        livDet.setQuantiteRecu(result.getInt("quantiteRecu"));
        livDet.setQuantiteValide(result.getInt("quantiteValide"));
        livDet.setMontant(result.getFloat("montant"));
        livDet.setDevise(result.getString("devise"));
        livDet.setIdLivraison(result.getInt("idLivraison"));
        livDet.setDescription(result.getString("description"));
        livDet.setDateLivraison(result.getString("dateLivraison"));
        livDet.setStatus(result.getString("status"));
        livDet.setProduit(produitDAO.find(result.getInt("idProduit")));
        livDet.setCommandeDetail(commDetDAO.findById(result.getInt("idCommandeDetail")));
        
        return livDet;
    }

    public LivraisonDetail findByCommandeProduit(int idLivraison, String refProduit) {
        String sql = "SELECT * FROM livraisonDetails WHERE idLivraison = ? and idProduit= (select id from produits WHERE refProduit = ?)";
        LivraisonDetail livraisonDetails = new LivraisonDetail();
        
        try{    
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idLivraison);
            stm.setString(2, refProduit);
            result = stm.executeQuery();
            
            while (result.next()) {
                livraisonDetails = getLivDet(result);
            }

            return livraisonDetails;
        
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
   
    }
    
}
