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
import javaapp.bean.User;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class UserDAO extends DAO<User>{

    public UserDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<User> select() {
    String query = "SELECT * FROM utilisateur";
        List<User> users = new ArrayList<>();
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                users.add(getUser(result));
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return users;
    }

    public List<User> getUserOrderByStatus() {
    String query = "SELECT * FROM utilisateur ORDER BY status desc";
        List<User> users = new ArrayList<>();
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                users.add(getUser(result));
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return users;
    }
    
    @Override
    public boolean create(User user) {
        int rowAffected = 0;
     String query = "INSERT INTO utilisateur (nomUtilisateur, idGroupe, status, dateCreation) VALUES (?, ?, ?, ?)";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, user.getNomUtilisateur());
            stm.setInt(2, user.getIdGroupe());
            stm.setString(3, user.getStatus());
            stm.setString(4, user.getDateCreation());
            return stm.executeUpdate() > 0;
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return false;
    }

    @Override
    public boolean delete(User obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User user) {
        int rowAffected = 0;
        String query = "UPDATE utilisateur SET nomUtilisateur=?, idGroupe=?, status=?, dateCreation=?  WHERE idUtilisateur=?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, user.getNomUtilisateur());
            stm.setInt(2, user.getIdGroupe());
            stm.setString(3, user.getStatus());
            stm.setString(4, user.getDateCreation());
            stm.setInt(5, user.getIdUtilisateur());
            rowAffected = stm.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            closeRessources();
        }
        return rowAffected != 0;
    }

    @Override
    public User find(int id) {
        String query = "SELECT * FROM utilisateur WHERE idUtilisateur = ?";
        User user = null;
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                user =  getUser(result);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return user;
    }
    
     public User find(String nomUtilisateur) {
        String query = "SELECT * FROM utilisateur WHERE nomUtilisateur = ?";
        User user = null;
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, nomUtilisateur);
            result = stm.executeQuery();
            if (result.next()) {
                user =  getUser(result);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return user;
    }
    
    public User getUserActive() {
        String query = "SELECT * FROM utilisateur WHERE status = 1";
        User user = null;
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            if (result.next()) {
                user =  getUser(result);
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeRessources();
        }
        return user;
    }
    public User getUser(ResultSet result) throws SQLException{
        
        User user =  new User();
        user.setIdUtilisateur(result.getInt("idUtilisateur"));
        user.setNomUtilisateur(result.getString("nomUtilisateur"));
        user.setIdGroupe(result.getInt("idGroupe"));
        user.setPassword(result.getString("password"));
        user.setStatus(result.getString("status"));
        user.setGroupe(DAOFactory.getGroupeDAO().find(result.getInt("idGroupe")));
        return user;
        
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
     
     public User authenticateUser(String username, String password) {
        String query = "SELECT * FROM utilisateur WHERE nomUtilisateur = ? AND password = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);
             result = stm.executeQuery();
                if (result.next()) {
                    return getUser(result);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
      public boolean disableStatus() {
         int affectedRow= 0;
          String query = "UPDATE utilisateur SET status = 0";
        try {
            stm = conn.prepareStatement(query);
            affectedRow =  stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            closeResources();
        }
        return affectedRow != 0;
    }
      
        public boolean activeStatus( int idUtilisateur) {
            int affectedRow= 0;
        String query = "UPDATE utilisateur SET status = 1 WHERE idUtilisateur = ?";
        try {
            stm = conn.prepareStatement(query);
//            stm.setInt(1, status);
            stm.setInt(1, idUtilisateur);
            affectedRow =  stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            closeResources();
        }
        return affectedRow != 0;
    }
      
         private void closeResources() {
        try {
            if (stm != null) stm.close();
            if (result != null) result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
