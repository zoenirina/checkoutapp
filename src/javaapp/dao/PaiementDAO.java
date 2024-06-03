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
import javaapp.bean.Paiement;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class PaiementDAO  extends DAO<Paiement>  {

    final static String SQL_INSERT = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) VALUES (?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM paiements ";
    final static String SQL_UPDATE = "UPDATE clients SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE id = ? ";
    final static String SQL_FIND_BY_IDFACTURE = SQL_SELECT+" WHERE idFacture = ? ";
    final static String SQL_WHERE_NOM = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
    final static String SQL_FIND_BY_REFERENCE = SQL_SELECT+" WHERE refPaiement = ? ";
    final static String SQL_ORDER = " ORDER BY datePaiement DESC";
    private int generatedKey=0;
    final static String SQL_FIND_BETWEEN_DATES = SQL_SELECT + " WHERE datePaiement BETWEEN ? AND ?";
        private final FactureDAO factureDAO;
        
    public PaiementDAO(Connection conn) {
        super(conn);
        factureDAO = DAOFactory.getFactureDAO();
    }

    @Override
    public List<Paiement> select() {
    List<Paiement> paiements = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT+ SQL_ORDER);
             result = stm.executeQuery();
            while (result.next()) {
                Paiement paiement = getPaiement(result);
                paiements.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiements;
    }

    public Paiement findByFacture(int idFacture) {
    Paiement paiement = new Paiement();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_IDFACTURE);
            stm.setInt(1, idFacture);
             result = stm.executeQuery();
            while (result.next()) {
                paiement = getPaiement(result);
//                paiements.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiement;
    }
    
    public Paiement findByRefPaiement(String refPaiement) {
        Paiement paiement = new Paiement();
        try {
            stm = conn.prepareStatement(SQL_FIND_BY_REFERENCE);
            stm.setString(1, refPaiement);
             result = stm.executeQuery();
            while (result.next()) {
                paiement = getPaiement(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiement;
    }
    
    @Override
    public boolean create(Paiement paiement){
//        String query = "INSERT INTO paiements (datePaiement, refPaiement, idFacture, idClient, montantRecu, restePaye) VALUES (?, ?, ?, ?, ?, ?)";
int affectedRow = 0;

try{
            stm = conn.prepareStatement( SQL_INSERT , PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, paiement.getDatePaiement());
            stm.setString(2, " ");
            stm.setInt(3, paiement.getIdFacture());
            stm.setInt(4, paiement.getIdClient());
            stm.setFloat(5, paiement.getMontantRecu());
            stm.setFloat(6, paiement.getRestePaye());
            affectedRow = stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1)) ;
                Paiement paie = this.find(getGeneratedKey());
                paie.setRefPaiement("PAY-"+DateConverter.getCurrentDate()+ "C"+getGeneratedKey());
                this.update(paie);
                
                System.out.println("Commande ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de la récupération de l'ID généré.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeResources();
        }
        return affectedRow > 0;
    }

    @Override
    public boolean delete(Paiement obj) {
        return false;
    
    }

    @Override
    public boolean update(Paiement obj) {
        boolean result = false;
        String sql = "UPDATE paiements SET datePaiement=?, refPaiement=?, idFacture=?, idClient=?, montantRecu=?, restePaye=? WHERE id=?";

        try {
            stm = conn.prepareStatement(sql);

            stm.setString(1, obj.getDatePaiement());
            stm.setString(2, obj.getRefPaiement());
            stm.setInt(3, obj.getIdFacture());
            stm.setInt(4, obj.getIdClient());
            stm.setFloat(5, obj.getMontantRecu());
            stm.setFloat(6, obj.getRestePaye());
            stm.setInt(7, obj.getId());

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated > 0) {
                result = true; // Indique que l'opération a réussi
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }
    

    @Override
    public Paiement find(int id) {
    Paiement paiement = new Paiement();
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
             result = stm.executeQuery();
            while (result.next()) {
                paiement = getPaiement(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paiement;
    }
    
    private Paiement getPaiement(ResultSet result) throws SQLException{
    Paiement paiement = new Paiement();
    
                paiement = new Paiement(
                result.getInt("id"),
                result.getString("datePaiement"),
                result.getString("refPaiement"),
                result.getInt("idFacture"),
                result.getInt("idClient"),
                result.getFloat("montantRecu"),
                result.getFloat("restePaye"));
                paiement.setFacture(factureDAO.find(result.getInt("idFacture")));
                paiement.setClient(DAOFactory.getClientDAO().find(result.getInt("idClient")));
        return paiement;
                
    }
    public float getMontantVerse(int idFacture){
        float i = 0.0f;
        String sql = "SELECT SUM(montantRecu) as totalVerse FROM paiements WHERE idFacture = ?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, idFacture);
             result = stm.executeQuery();
            while (result.next()) {
                i = result.getFloat("totalVerse");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<Paiement> findBetweenDates(String startDate, String endDate) {
    List<Paiement> paiementList = new ArrayList<>();
        try {
            stm = conn.prepareStatement( SQL_FIND_BETWEEN_DATES +SQL_ORDER);
            stm.setString(1,  startDate);
            stm.setString(2,  endDate);
            result = stm.executeQuery();

            while (result.next()) {
                Paiement paiement = getPaiement(result);
                paiementList.add(paiement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaiementDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return paiementList;
    }

    public List<Paiement> filter(String keySearch) {
        List<Paiement> paiementList = new ArrayList<>();
         Paiement paiement = new Paiement();
            try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE refPaiement like '%"+keySearch+"%'  OR idFacture in (SELECT id from factures WHERE refFacture like '%"+keySearch+"%' ) "+SQL_ORDER);
//            stm.setString(1, " '%"+keySearch+"%' " );
//            stm.setString(2, " '%"+keySearch+"%' " );
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
