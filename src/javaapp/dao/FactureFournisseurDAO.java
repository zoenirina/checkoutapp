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
import javaapp.bean.FactureFournisseur;
import javaapp.component.DateConverter;
import javaapp.factory.DAOFactory;

/**
 *
 * @autor ZOENIRINA
 */
public class FactureFournisseurDAO extends DAO<FactureFournisseur> {

    final static String SQL_INSERT = "INSERT INTO factureFournisseur (dateFacture, refFacture, idReception, idFournisseur, montant, devise, description, pj) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    protected String SQL_SELECT ="SELECT f.*, fo.* ,   f.montant - COALESCE(SUM(p.montantRecu), 0) AS resteAPayer , " +
"       CASE    WHEN COALESCE(SUM(p.montantRecu), 0) >= f.montant THEN 'Payée' WHEN COALESCE(SUM(p.montantRecu), 0) = 0 THEN 'Non payée'  ELSE 'Payée partiellement'  END AS etat FROM factureFournisseur f " +
"   LEFT JOIN paiementFournisseur p ON f.id = p.idFactureFournisseur left join fournisseurs fo on f.idFournisseur = fo.id ";
    protected String SQL_GROUP = "  GROUP BY f.id ";
    protected String SQL_ORDER = " ORDER BY f.dateFacture DESC";
    protected String SQL_UPDATE = "UPDATE factureFournisseur SET dateFacture = ?, refFacture = ?, idReception = ?, idFournisseur = ?, montant = ?, devise = ?, description = ?, pj = ?  WHERE id = ?;";//"UPDATE factures SET dateFacture=?, refFacture=?, idLivraison=?, idFournisseur=?, montant=?, devise=?, description=? WHERE id = ?";
    protected String SQL_FIND = SQL_SELECT+" WHERE f.id =? ";
    protected String SQL_FIND_REF= SQL_SELECT+" WHERE f.refFacture = ? ";
    final static String SQL_DELETE = "DELETE FROM factureFournisseur WHERE id = ?";
    private final String SQL_FIND_IDLIV = SQL_SELECT+" WHERE f.idReception = ? ";
        
        private int generatedKey=0;
        private final ReceptionDAO receptionDAO;
        private final PersonneDAO fournisseurDAO;
        
    public FactureFournisseurDAO(Connection conn) {
        super(conn);
        receptionDAO = DAOFactory.getReceptionDAO();
        fournisseurDAO =DAOFactory.getFournisseurDAO();
    }

    @Override
    public List<FactureFournisseur> select() {
        List<FactureFournisseur> factures = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_SELECT+SQL_ORDER);
             result = stm.executeQuery();
            while (result.next()) {
                FactureFournisseur facture = getFactureFournisseur(result);
                factures.add(facture);
            }
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
           closeResources();
        }
        return factures;
    }

    public List<FactureFournisseur> select(String query) {
        return selectWhere(SQL_SELECT + SQL_GROUP + SQL_ORDER);
    }
    
    public List<FactureFournisseur> selectPaye(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer = 0; "+ SQL_ORDER);
    }
    
    public List<FactureFournisseur> selectNonPaye(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer = f.montant OR resteAPayer IS NULL "+ SQL_ORDER);
    }
    
    public List <FactureFournisseur> selectPayePartiellement(String query) {
        return selectWhere(SQL_SELECT+query+ SQL_GROUP +" HAVING resteAPayer > 0 AND resteAPayer < f.montant "+ SQL_ORDER);
    }

    
    @Override
    public boolean create(FactureFournisseur obj) {
        try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, obj.getDateFacture());
            stm.setString(2, obj.getRefFacture());
            stm.setInt(3, obj.getIdReception());
            stm.setInt(4, obj.getIdFournisseur());
            stm.setFloat(5, obj.getMontant());
            stm.setString(6, obj.getDevise());
            stm.setString(7, obj.getDescription());
            stm.setString(8, obj.getPj());
            stm.executeUpdate();
            
            result = stm.getGeneratedKeys();
            if (result.next()) {
                setGeneratedKey(result.getInt(1));
                FactureFournisseur facture = this.find(getGeneratedKey());
                facture.setRefFacture("FAP-"+DateConverter.getCurrentDate()+ getGeneratedKey());
                this.update(facture);
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour la facture.");
            }
        
            return true;
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean update(FactureFournisseur obj) {
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, obj.getDateFacture());
            stm.setString(2, obj.getRefFacture());
            stm.setInt(3, obj.getIdReception());
            stm.setInt(4, obj.getIdFournisseur());
            stm.setFloat(5, obj.getMontant());
            stm.setString(6, obj.getDevise());
            stm.setString(7, obj.getDescription());
            stm.setString(8, obj.getPj());
            stm.setInt(9, obj.getId());
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean delete(FactureFournisseur obj) {
        return delete(obj.getId());
    }

    public boolean delete(int id) {
        try (PreparedStatement statement = conn.prepareStatement(SQL_DELETE)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public FactureFournisseur find(int id) {
        FactureFournisseur facture = null;
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                facture = getFactureFournisseur(result);
            }
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return facture;
    }

    public FactureFournisseur findByRef(String refFacture) {
        FactureFournisseur facture = null;
        try {
            stm = conn.prepareStatement(SQL_FIND_REF);
            stm.setString(1, refFacture);
            result = stm.executeQuery();
            if (result.next()) {
                facture = getFactureFournisseur(result);
            }
        } catch (SQLException e) {
            Logger.getLogger(FactureFournisseurDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
           closeResources();
        }
        return facture;
    }

    private FactureFournisseur getFactureFournisseur(ResultSet resultSet) throws SQLException {
        FactureFournisseur facture = new FactureFournisseur();
        facture.setId(resultSet.getInt("id"));
        facture.setDateFacture(resultSet.getString("dateFacture"));
        facture.setRefFacture(resultSet.getString("refFacture"));
        facture.setIdReception(resultSet.getInt("idReception"));
        facture.setReception( receptionDAO.find(resultSet.getInt("idReception")) );
        facture.setIdFournisseur(resultSet.getInt("idFournisseur"));
        facture.setMontant(resultSet.getFloat("montant"));
        facture.setDevise(resultSet.getString("devise"));
        facture.setDescription(resultSet.getString("description"));
        facture.setPj(resultSet.getString("pj"));//
        facture.setStatus(resultSet.getString("etat"));
        facture.setFournisseur( fournisseurDAO.find(result.getInt("idFournisseur")));
        return facture;
    }

    private List<FactureFournisseur> selectWhere(String query) {
    List<FactureFournisseur> facList = new ArrayList<>();
        try {
	stm = conn.prepareStatement(query);
	result = stm.executeQuery();
        
	while(result.next()) {
            FactureFournisseur fac = getFactureFournisseur(result);
            facList.add(fac);
        }
       return facList;
	}catch(SQLException ex) {
	    Logger.getLogger(FactureDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeResources();
        }
        return null;
    }

      private void closeResources() {
        try {
            if(stm != null)stm.close();
            if(result != null)result.close();
//            if(conn != null)conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FactureFournisseur findBy(int idLivraison) {
        FactureFournisseur fac = new FactureFournisseur();
        try  {
            stm = conn.prepareStatement(SQL_FIND_IDLIV);
            stm.setInt(1, idLivraison);
            result = stm.executeQuery();
            if (result.next()) {
                fac = getFactureFournisseur(result);
            }
            return fac;
        } catch (SQLException e) {
        }finally{
            closeResources();
        }
        return null;
    }
      
    public int getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(int generatedKey) {
        this.generatedKey = generatedKey;
    }
    
     public int getCountAPayer(){
        String sql = "SELECT COUNT(f.id) as size from factureFournisseur f where f.montant > COALESCE(SUM(p.montantRecu), 0) "
                + "  LEFT JOIN paiementFournisseur p ON f.id = p.idFactureFournisseur";
        try  {
            stm = conn.prepareStatement(sql);
            result = stm.executeQuery();
                if (result.next()) {
                    return result.getInt("size");
                }
            
        } catch (SQLException e) {
        }finally{
            closeResources();
        }
        return 0;
    }
    
}
