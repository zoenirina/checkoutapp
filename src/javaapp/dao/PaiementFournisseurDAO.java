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
import javaapp.bean.PaiementFournisseur;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 * DAO class for PaiementFournisseur.
 */
public class PaiementFournisseurDAO extends DAO<PaiementFournisseur> {

    final static String SQL_INSERT = "INSERT INTO paiementFournisseur (datePaiement, refPaiement, idFactureFournisseur, idFournisseur, montantRecu, restePaye) VALUES (?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM paiementFournisseur";
    final static String SQL_UPDATE = "UPDATE paiementFournisseur SET datePaiement = ?, refPaiement = ?, idFactureFournisseur = ?, idFournisseur = ?, montantRecu = ?, restePaye = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM paiementFournisseur WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE id = ?";
    final static String SQL_FIND_BY_IDFACTURE = SQL_SELECT + " WHERE idFactureFournisseur = ?";
    final static String SQL_FIND_BY_REFERENCE = SQL_SELECT + " WHERE refPaiement = ?";
    final static String SQL_ORDER = " ORDER BY datePaiement DESC";
    final static String SQL_FIND_BETWEEN_DATES = SQL_SELECT + " WHERE datePaiement BETWEEN ? AND ?";
//    final static String SQL_ORDER = " ORDER BY datePaiement";
    private int generatedKey = 0;
    
    public PaiementFournisseurDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<PaiementFournisseur> select() {
        List<PaiementFournisseur> paiements = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT+SQL_ORDER);
             result = stm.executeQuery();
            while (result.next()) {
                PaiementFournisseur paiement = getPaiement(result);
                paiements.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return paiements;
    }

    public PaiementFournisseur findByFacture(int idFactureFournisseur) {
        PaiementFournisseur paiement = new PaiementFournisseur();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_IDFACTURE);
            stm.setInt(1, idFactureFournisseur);
            result = stm.executeQuery();
                if (result.next()) {
                    paiement = getPaiement(result);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiement;
    }
    
    public PaiementFournisseur findByRefPaiement(String refPaiement) {
        PaiementFournisseur paiement = new PaiementFournisseur();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_REFERENCE);
            stm.setString(1, refPaiement);
            result = stm.executeQuery();
                if (result.next()) {
                    paiement = getPaiement(result);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiement;
    }
    
    @Override
    public boolean create(PaiementFournisseur paiement) {
        int affectedRow = 0;
        try {
            stm = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, paiement.getDatePaiement());
            stm.setString(2, paiement.getRefPaiement());
            stm.setInt(3, paiement.getIdFactureFournisseur());
            stm.setInt(4, paiement.getIdFournisseur());
            stm.setFloat(5, paiement.getMontantRecu());
            stm.setFloat(6, paiement.getRestePaye());
            affectedRow = stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
                if (result.next()) {
                    setGeneratedKey(result.getInt(1));
                    PaiementFournisseur paie = this.find(getGeneratedKey());
                    paie.setRefPaiement("PAY-" + DateConverter.getCurrentDate()+"F"+getGeneratedKey());
                    this.update(paie);
                    
                    System.out.println("Paiement fournisseur ajouté avec succès !");
                } else {
                    System.out.println("Erreur lors de la récupération de l'ID généré.");
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return affectedRow > 0;
    }

    @Override
    public boolean delete(PaiementFournisseur paiement) {
        return delete(paiement.getId());
    }

    public boolean delete(int id) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(PaiementFournisseur paiement) {
        boolean result = false;
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, paiement.getDatePaiement());
            stm.setString(2, paiement.getRefPaiement());
            stm.setInt(3, paiement.getIdFactureFournisseur());
            stm.setInt(4, paiement.getIdFournisseur());
            stm.setFloat(5, paiement.getMontantRecu());
            stm.setFloat(6, paiement.getRestePaye());
            stm.setInt(7, paiement.getId());

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                result = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return result;
    }
    
    @Override
    public PaiementFournisseur find(int id) {
        PaiementFournisseur paiement = new PaiementFournisseur();
        try (PreparedStatement statement = conn.prepareStatement(SQL_FIND)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    paiement = getPaiement(resultSet);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return paiement;
    }
    
    private PaiementFournisseur getPaiement(ResultSet result) throws SQLException {
        PaiementFournisseur paiement = new PaiementFournisseur();
        paiement = new PaiementFournisseur (
            result.getInt("id"),
            result.getString("datePaiement"),
            result.getString("refPaiement"),
            result.getInt("idFactureFournisseur"),
            result.getInt("idFournisseur"),
            result.getFloat("montantRecu"),
            result.getFloat("restePaye")
        );
            paiement.setFactureFournisseur(DAOFactory.getFactureFournisseurDAO().find(result.getInt("idFactureFournisseur")));
            paiement.setFournisseur(DAOFactory.getFournisseurDAO().find(result.getInt("idFournisseur")));
        return paiement;
    }
    
    public float getMontantVerse(int idFactureFournisseur){
        float i = 0.0f;
        String sql = "SELECT SUM(montantRecu) as totalVerse FROM paiementFournisseur WHERE idFactureFournisseur = ?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idFactureFournisseur);
             result = stm.executeQuery();
            while (result.next()) {
                i = result.getFloat("totalVerse");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeResources();
        }
        return i;
    }
    
    private void closeResources() {
        try {
            if(stm != null)stm.close();
            if(result != null)result.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    public int getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
     public List<PaiementFournisseur> findBetweenDates(String startDate, String endDate) {
    List<PaiementFournisseur> PaiementFournisseurList = new ArrayList<>();
        try {
            stm = conn.prepareStatement( SQL_FIND_BETWEEN_DATES +SQL_ORDER);
            stm.setString(1,  startDate);
            stm.setString(2,  endDate);
            result = stm.executeQuery();

            while (result.next()) {
                PaiementFournisseur paiement = getPaiement(result);
                PaiementFournisseurList.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementFournisseurDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return PaiementFournisseurList;
    }

    public List<PaiementFournisseur> filter(String keySearch) {
        List<PaiementFournisseur> paiementList = new ArrayList<>();
         PaiementFournisseur paiement = new PaiementFournisseur();
            try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE refPaiement like '%"+keySearch+"%'  OR idFacture in (SELECT id from factures WHERE refFacture like '%"+keySearch+"%' ) "+SQL_ORDER);

             result = stm.executeQuery();
            while (result.next()) {
                paiement = getPaiement(result);
                paiementList.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return paiementList;
    }
}
