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
import javaapp.bean.EntreeCaisse;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class EntreeCaisseDAO  extends DAO<EntreeCaisse>{
final static String SQL_SELECT = " select ec.*,utilisateur.nomUtilisateur, tm.* from entree_caisse ec inner join utilisateur on ec.idCreateur=utilisateur.idUtilisateur "
            + "left join type_mouvement tm on ec.idType= tm.code_type where idExercice in (select id from exercice where status=1) ";

    final static String SQL_ORDER = " ORDER BY dateEntree DESC";
 
    public EntreeCaisseDAO(Connection conn) {
        super(conn);
    }


    @Override
    public boolean create(EntreeCaisse entree) {
    String sql = "INSERT INTO entree_caisse (dateEntree, idType, idSourceReference, montant, devise, idCreateur, idExercice, description)"
            + " VALUES (?, ?, ?, ?, (SELECT devise from config where idConfig=1), (select idUtilisateur from utilisateur where status = 1), (select id from exercice where status=1) , ?)";
       int i = 0; 
        try {
             stm = conn.prepareStatement(sql, stm.RETURN_GENERATED_KEYS) ;
            stm.setString(1, entree.getDateEntree());
            stm.setInt(2, entree.getIdType());
            stm.setInt(3, entree.getIdSourceReference());
            stm.setFloat(4, entree.getMontant());
            stm.setString(5, entree.getDescription());
            i = stm.executeUpdate();
        }catch(Exception ex){
        System.out.print(ex);
        }
        return i != 0;
    }


    @Override
    public boolean delete(EntreeCaisse entreCaisse) {
    String sql = "DELETE FROM entree_caisse WHERE id = ?";
        int i = 0;
        try {
             stm = conn.prepareStatement(sql);
            stm.setInt(1, entreCaisse.getId());
            i = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i!= 0;
    }

    @Override
    public boolean update(EntreeCaisse entree) {
     String sql = "UPDATE entree_caisse SET dateEntree = ?, idType = ?, idSourceReference = ?,  montant = ?,  description = ? WHERE id = ?";
        int i = 0;
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, entree.getDateEntree());
            stm.setInt(2, entree.getIdType());
            stm.setInt(3, entree.getIdSourceReference());
            stm.setFloat(4, entree.getMontant());
            stm.setString(5, entree.getDescription());
            stm.setInt(6, entree.getId());
            i = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i != 0;
    }

    @Override
    public List<EntreeCaisse> select() {
    String sql = SQL_SELECT +SQL_ORDER;
        List<EntreeCaisse> entrees = new ArrayList<>();
        try {
            stm = conn.prepareStatement(sql);            
            result = stm.executeQuery();
            
                while (result.next()) {
                    entrees.add( mapResultSetToEntreeCaisse(result) );
                }
            } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return entrees;
    }

    @Override
    public EntreeCaisse find(int id) {
     String sql = SQL_SELECT+" AND id = ?";
        
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            
            result = stm.executeQuery();
                if (result.next()) {
                    return mapResultSetToEntreeCaisse(result);
                }
            } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public List<EntreeCaisse> filterBetweenDates(String startDate, String endDate) {
        String sql = SQL_SELECT +" AND dateEntree BETWEEN ? AND ?"+SQL_ORDER;
        List<EntreeCaisse> entrees = new ArrayList<>();
        
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            
            result = stm.executeQuery();
                if (result.next()) {
                    entrees.add( mapResultSetToEntreeCaisse(result) );
                }
        }catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrees;
    }
    public List<EntreeCaisse> filterDates(String startDate) {
        String sql = SQL_SELECT +" AND dateEntree like '"+startDate+"%' "+SQL_ORDER;
        List<EntreeCaisse> entrees = new ArrayList<>();
        
        try {
            stm = conn.prepareStatement(sql);
//            stm.setString(1, startDate);
            
            result = stm.executeQuery();
                if (result.next()) {
                    entrees.add( mapResultSetToEntreeCaisse(result) );
                }
        }catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrees;
    }
    public List<EntreeCaisse> findBySourceReference(String sourceReference) {
    String sql = SQL_SELECT + " AND idSourceReference = ?"+SQL_ORDER;
    List<EntreeCaisse> entrees = new ArrayList<>();
    
    try {
        stm = conn.prepareStatement(sql);
        stm.setString(1, sourceReference);
        
        result = stm.executeQuery();
        while (result.next()) {
            entrees.add(mapResultSetToEntreeCaisse(result));
        }
    } catch (SQLException ex) {
        Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return entrees;
}

    private EntreeCaisse mapResultSetToEntreeCaisse(ResultSet result) throws SQLException {
        EntreeCaisse ec =  new EntreeCaisse(
            result.getInt("id"),
            result.getString("dateEntree"),
            result.getInt("idType"),
            result.getInt("idSourceReference"),
            result.getFloat("montant"),
            result.getString("devise"),
            result.getInt("idCreateur"),
            result.getInt("idExercice"),
            result.getString("description")
        );
        ec.setCreateur(DAOFactory.getUserDAO().find( result.getInt("idCreateur") ));
        if(result.getString("idType") != null)ec.setTypeMouvement(DAOFactory.getTypeMouvementDAO().find(result.getInt("idType")));
        
        return ec;
    }
}
