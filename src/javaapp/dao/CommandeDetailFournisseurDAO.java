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
import javaapp.bean.CommandeDetailFournisseur;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class CommandeDetailFournisseurDAO   extends DAO<CommandeDetailFournisseur>   {
final static String SQL_INSERT = "INSERT INTO commandeDetailsFournisseur (idCommande, idProduit, quantite, montant, devise, description) VALUES (?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM commandeDetailsFournisseur";
    final static String SQL_UPDATE = "UPDATE commandeDetailsFournisseur SET idCommande = ?, idProduit = ?, quantite = ?, montant = ?, devise = ?, description = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM commandeDetailsFournisseur WHERE idCommande = ? AND idProduit = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE idCommande = ?";

    private int generatedKey=0;
    private  ProduitDAO produitDAO;
    
    public CommandeDetailFournisseurDAO(Connection conn) {
        super(conn);
        produitDAO = DAOFactory.getProduitDAO();
    }

    @Override
    public List<CommandeDetailFournisseur> select() {
        List<CommandeDetailFournisseur> comDetails = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT);
             result = stm.executeQuery();
            while (result.next()) {
                CommandeDetailFournisseur comDetail = getCommDet(result);
                comDetails.add(comDetail);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comDetails;
    }

    public List<CommandeDetailFournisseur> select(int idCommande) {
        List<CommandeDetailFournisseur> comDetails = new ArrayList<>();
        CommandeDetailFournisseur comDetail = null;
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, idCommande);
            result = stm.executeQuery();
            while (result.next()) {
                comDetail = getCommDet(result);
                comDetails.add(comDetail);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comDetails;
    }

    @Override
    public boolean create(CommandeDetailFournisseur obj) {
        try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setInt(1, obj.getIdCommande());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setFloat(4, obj.getMontant());
            stm.setString(5, obj.getDevise());
            stm.setString(6, obj.getDescription());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean delete(CommandeDetailFournisseur obj) {
        return delete(obj.getIdCommande(), obj.getIdProduit());
    }

    public boolean delete(int idCommande, int idProduit) {
        try {
            stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, idCommande);
            stm.setInt(2, idProduit);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean update(CommandeDetailFournisseur obj) {
        try {
            stm= conn.prepareStatement(SQL_UPDATE);
            stm.setInt(1, obj.getIdCommande());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setFloat(4, obj.getMontant());
            stm.setString(5, obj.getDevise());
            stm.setString(6, obj.getDescription());
            stm.setInt(7, obj.getId());
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public CommandeDetailFournisseur find(int id) {
        CommandeDetailFournisseur comDetail = null;
        try {
            stm = conn.prepareStatement(SQL_SELECT + " WHERE id = ?");
            stm.setInt(1, id);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                comDetail = getCommDet(resultSet);
            }
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comDetail;
    }

    private CommandeDetailFournisseur getCommDet(ResultSet resultSet) throws SQLException {
        CommandeDetailFournisseur comDetail = new CommandeDetailFournisseur();
        comDetail.setId(resultSet.getInt("id"));
        comDetail.setIdProduit(resultSet.getInt("idProduit"));
        comDetail.setQuantite(resultSet.getInt("quantite"));
        comDetail.setMontant(resultSet.getFloat("montant"));
        comDetail.setDevise(resultSet.getString("devise"));
        comDetail.setIdCommande(resultSet.getInt("idCommande"));
        comDetail.setDescription(resultSet.getString("description"));
        comDetail.setRemise(resultSet.getFloat("remise"));
        comDetail.setProduit(produitDAO.find(resultSet.getInt("idProduit")));
        return comDetail;
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

    public CommandeDetailFournisseur findByCommandeProduit(int idCommande, String refProduit) {
    CommandeDetailFournisseur comDetail = null;
        try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE idCommande=? and idProduit = (select id from produits WHERE refProduit=?)");
            stm.setInt(1, idCommande);
            stm.setString(2, refProduit);
            result = stm.executeQuery();
            if (result.next()) {
                comDetail = getCommDet(result);
            }
            stm.close();
            result.close();
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return comDetail;
    }
}
