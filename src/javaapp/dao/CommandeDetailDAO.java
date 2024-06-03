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
import javaapp.bean.CommandeDetail;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class CommandeDetailDAO   extends DAO<CommandeDetail>   {

    final static String SQL_INSERT = "INSERT INTO commandeDetails (idCommande, idProduit, quantite, montant, devise, description) VALUES (?, ?, ?, ?,(SELECT devise FROM config WHERE idConfig=1),?)";
    final static String SQL_SELECT = "SELECT * FROM commandeDetails ";
    final static String SQL_UPDATE = "UPDATE commandeDetails SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    final static String SQL_DELETE = "DELETE FROM commandeDetails WHERE idCommande = ? AND idProduit = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE idCommande=? ";
    final static String SQL_FIND_REF = SQL_SELECT+" WHERE idCommande=? ";
    final ProduitDAO produitDAO;
  
    public CommandeDetailDAO(Connection conn) {
        super(conn);
        produitDAO = DAOFactory.getProduitDAO();
//        cmdDAO = DAOFactory.getCommandeDetailDAOImpl();
    }

    @Override
    public List<CommandeDetail> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List <CommandeDetail> select (int id) {
        List <CommandeDetail> comDetails = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
            
            result = stm.executeQuery();
            while(result.next()){
            CommandeDetail comDetail = getCommDet(result);
            
//            id, result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status"));
            comDetails.add(comDetail);
            }
        } catch (SQLException e) {
        }finally {
           closeRessources();
        }
        return comDetails; 
    }

    @Override
    public boolean create(CommandeDetail obj) {
        int affectedRow = 0;
    try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setInt(1, obj.getIdCommande());
            stm.setInt(2, obj.getProduit().getId());
            stm.setInt(3, obj.getQuantite());
            stm.setFloat(4, obj.getMontant());
            stm.setString(5, obj.getDescription());
            affectedRow = stm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally {
           closeRessources();
        }
    return affectedRow > 0;
    }

    public boolean delete(int idCommande, int idProduit) {
        int rowAffected=0;
        try {
	stm = conn.prepareStatement(SQL_DELETE);
        stm.setInt(1, idCommande);
        stm.setInt(2, idProduit);
	rowAffected = stm.executeUpdate();
        }catch(SQLException ex) {
	    Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeRessources();
        }
        return rowAffected != 0; 
    }

    @Override
    public boolean update(CommandeDetail obj) {
    String SQL_UPDATE = "UPDATE commandeDetails SET idCommande = ?, idProduit = ?, quantite = ?, montant = ?, devise = ?, description = ?, remise = ? WHERE id = ?";
    try {
        stm = conn.prepareStatement(SQL_UPDATE);
        stm.setInt(1, obj.getIdCommande());
        stm.setInt(2, obj.getIdProduit());
        stm.setInt(3, obj.getQuantite());
        stm.setFloat(4, obj.getMontant());
        stm.setString(5, obj.getDevise());
        stm.setString(6, obj.getDescription());
        stm.setFloat(7, obj.getRemise());
        stm.setInt(8, obj.getId());
        stm.executeUpdate();
        stm.close();
        return true;
    } catch (SQLException ex) {
        Logger.getLogger(CommandeDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
    }

    @Override
    public CommandeDetail find(int id) {
    CommandeDetail comDetail = null;
        try {
            stm = conn.prepareStatement(SQL_FIND_REF);
            stm.setInt(1, id);
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
    
    public CommandeDetail findById(int id) {
    CommandeDetail comDetail = null;
        try {
            stm = conn.prepareStatement(SQL_SELECT+" WHERE id=? ");
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                comDetail = getCommDet(result);
            }
            stm.close();
            result.close();
        } catch (SQLException e) {
            Logger.getLogger(CommandeDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally {
           closeRessources();
        }
        return comDetail;
    }
    
    public CommandeDetail findByCommandeProduit(int idCommande, String refProduit) {
    CommandeDetail comDetail = null;
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

    @Override
    public boolean delete(CommandeDetail obj) {
    try {
            stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, obj.getId());
            stm.executeUpdate();
            stm.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDetailDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void closeRessources(){
        try {
            if(stm != null)stm.close();
//            if(conn != null)conn.close();
            if(result != null)result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private CommandeDetail getCommDet(ResultSet result) throws SQLException{
        
        CommandeDetail comDetail  = new CommandeDetail();
        comDetail.setId(result.getInt("id"));
        comDetail.setIdCommande(result.getInt("idCommande"));
        comDetail.setIdProduit(result.getInt("idProduit"));
        comDetail.setMontant(result.getFloat("montant"));
        comDetail.setQuantite(result.getInt("quantite"));
        comDetail.setDescription(result.getString("description"));
        comDetail.setRemise(result.getFloat("remise"));
        comDetail.setProduit(produitDAO.find(result.getInt("idProduit")));
        
        return comDetail;
    
    }

   
}
