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
import javaapp.bean.ReceptionDetail;
import javaapp.factory.DAOFactory;

/**
 *
 * @autor ZOENIRINA
 */
public class ReceptionDetailDAO extends DAO<ReceptionDetail> {

    final static String SQL_INSERT = "INSERT INTO receptionDetails (idCommandeDetailFournisseur, idProduit, quantiteRecu, quantiteValide, montant, devise, idReception, description, dateReception, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM receptionDetails";
    final static String SQL_UPDATE = "UPDATE receptionDetails SET idCommandeDetailFournisseur = ?, idProduit = ?, quantiteRecu = ?, quantiteValide = ?, montant = ?, devise = ?, idReception = ?, description = ?, dateReception = ?, status = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM receptionDetails WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE id = ?";
    
    private int generatedKey = 0;
    private CommandeDetailFournisseurDAO commDetFournisseurDAO;
    private ProduitDAO produitDAO;
    public ReceptionDetailDAO(Connection conn) {
        super(conn);
        commDetFournisseurDAO = DAOFactory.getCommandeDetailFournisseurDAO();
        produitDAO = DAOFactory.getProduitDAO();
    }

    @Override
    public List<ReceptionDetail> select() {
        List<ReceptionDetail> receptionDetails = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ReceptionDetail receptionDetail = getReceptionDetail(resultSet);
                receptionDetails.add(receptionDetail);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReceptionDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return receptionDetails;
    }
    public List<ReceptionDetail> select(int idLivraison) {
    String sql = "SELECT * FROM receptionDetails WHERE idReception = ?";
        List<ReceptionDetail> recepDetailsList = new ArrayList<>();

        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
            
            while (result.next()) {
                ReceptionDetail recepDetails = getReceptionDetail(result);
                recepDetailsList.add(recepDetails);
            }
            } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return recepDetailsList;
    }
    
    @Override
    public boolean create(ReceptionDetail obj) {
        try {
            stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setInt(1, obj.getIdCommandeDetailFournisseur());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantiteRecu());
            stm.setInt(4, obj.getQuantiteValide());
            stm.setFloat(5, obj.getMontant());
            stm.setString(6, obj.getDevise());
            stm.setInt(7, obj.getIdReception());
            stm.setString(8, obj.getDescription());
            stm.setString(9, obj.getDateReception());
            stm.setString(10, obj.getStatus());
            stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1));
            }
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ReceptionDetailDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean update(ReceptionDetail obj) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, obj.getIdCommandeDetailFournisseur());
            statement.setInt(2, obj.getIdProduit());
            statement.setInt(3, obj.getQuantiteRecu());
            statement.setInt(4, obj.getQuantiteValide());
            statement.setFloat(5, obj.getMontant());
            statement.setString(6, obj.getDevise());
            statement.setInt(7, obj.getIdReception());
            statement.setString(8, obj.getDescription());
            statement.setString(9, obj.getDateReception());
            statement.setString(10, obj.getStatus());
            statement.setInt(11, obj.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ReceptionDetailDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean delete(ReceptionDetail obj) {
        return delete(obj.getId());
    }

    public boolean delete(int id) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(ReceptionDetailDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public ReceptionDetail find(int id) {
        ReceptionDetail receptionDetail = null;
        try (PreparedStatement statement = conn.prepareStatement(SQL_FIND)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                receptionDetail = getReceptionDetail(resultSet);
            }
        } catch (SQLException e) {
            Logger.getLogger(ReceptionDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return receptionDetail;
    }

    private ReceptionDetail getReceptionDetail(ResultSet resultSet) throws SQLException {
        ReceptionDetail receptionDetail = new ReceptionDetail();
        receptionDetail.setId(resultSet.getInt("id"));
        receptionDetail.setIdCommandeDetailFournisseur(resultSet.getInt("idCommandeDetailFournisseur"));
        receptionDetail.setIdProduit(resultSet.getInt("idProduit"));
        receptionDetail.setQuantiteRecu(resultSet.getInt("quantiteRecu"));
        receptionDetail.setQuantiteValide(resultSet.getInt("quantiteValide"));
        receptionDetail.setMontant(resultSet.getFloat("montant"));
        receptionDetail.setDevise(resultSet.getString("devise"));
        receptionDetail.setIdReception(resultSet.getInt("idReception"));
        receptionDetail.setDescription(resultSet.getString("description"));
        receptionDetail.setDateReception(resultSet.getString("dateReception"));
        receptionDetail.setStatus(resultSet.getString("status"));
        receptionDetail.setProduit(produitDAO.find((resultSet.getInt("idProduit"))));
        receptionDetail.setCommandeDetail(commDetFournisseurDAO.find(resultSet.getInt("idCommandeDetailFournisseur")));
        return receptionDetail;
    }

    public boolean delete(int idReception, int idProduit) {
        String SQL = "DELETE FROM receptionDetails WHERE idReception = ? AND idProduit = ?";
        int rowAffected = 0;
            try {
            stm = conn.prepareStatement(SQL);
            stm.setInt(1, idReception);
            stm.setInt(2, idProduit);
            rowAffected = stm.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
            return rowAffected == 1;
    }

    public ReceptionDetail findByReceptionProduit(int idReception, String refProduit) {
        String sql = "SELECT * FROM receptionDetails WHERE idReception = ? and idProduit= (select id from produits WHERE refProduit = ?)";
        ReceptionDetail receptionDetails = null;
        try{    
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idReception);
            stm.setString(2, refProduit);
            result = stm.executeQuery();
            
            while (result.next()) {
                receptionDetails = getReceptionDetail(result);
            }

            
        
        } catch (SQLException ex) {
            Logger.getLogger(LivraisonDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return receptionDetails;
    }

    /**
     * @return the generatedKey
     */
    public int getGeneratedKey() {
        return generatedKey;
    }

    /**
     * @param generatedKey the generatedKey to set
     */
    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
}
