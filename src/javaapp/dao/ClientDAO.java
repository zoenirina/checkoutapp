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
import javaapp.bean.Client;

/**
 *
 * @author ZOENIRINA
 */
public class ClientDAO  extends DAO<Client> {
    private static final String SQL_INSERT = "INSERT INTO clients (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM clients ";
    private static final String SQL_UPDATE = "UPDATE clients SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    private static final String SQL_FIND = SQL_SELECT+" WHERE id=? ";
    private static final String SQL_FILTER = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
   
    private ResultSet result;
    private PreparedStatement stm;
    
    public ClientDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Client> select(){
       List<Client> clients= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
            clients.add(new Client(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return clients;
    }
    
    @Override
    public boolean create(Client client) {
    int affectedRows = 0;
         try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, client.getNom());
            stm.setString(2, client.getPrenom());
            stm.setString(3, client.getEmail1());
            stm.setString(4, client.getEmail2());
            stm.setString(5, client.getTel1());
            stm.setString(6, client.getTel2());
            stm.setString(7, client.getTel3());
            stm.setString(8, client.getAdresse());
            stm.setString(9, client.getNIF());
            stm.setString(10, client.getStat());
            stm.setInt(11, client.getId());  // Assurez-vous que getIdSelected() récupère bien l'identifiant du client
            affectedRows = stm.executeUpdate();
//                        JOptionPane.showMessageDialog(null, "Modifications enregistrées", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
        } finally {
            try {
                if(stm != null)stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return affectedRows > 0;
     
    }

    @Override
    public boolean delete(Client client) {
        int affectedRows = 0;
        
        try {
            stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, client.getId());
            affectedRows = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(stm != null)stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
    return affectedRows > 0;
    }

    @Override
    public boolean update(Client client) {
        int affectedRows = 0;
         try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, client.getNom());
            stm.setString(2, client.getPrenom());
            stm.setString(3, client.getEmail1());
            stm.setString(4, client.getEmail2());
            stm.setString(5, client.getTel1());
            stm.setString(6, client.getTel2());
            stm.setString(7, client.getTel3());
            stm.setString(8, client.getAdresse());
            stm.setString(9, client.getNIF());
            stm.setString(10, client.getStat());
            stm.setInt(11, client.getId());  
            affectedRows = stm.executeUpdate();
        } catch (SQLException e) {
        } finally {
            try {
                if(stm != null)stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return affectedRows > 0;
    }

    @Override
    public Client find(int id) {
    Client client = new Client(); 
    try {
        stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
        if(result.next()){
        client = new Client(id, result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status"));
        }
    } catch (SQLException e) {
    }finally{
    try {
        if(result != null)result.close();
    } catch (SQLException ex) {
        Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    return client;   
    }
  
    public List<Client> filter(String nom){
      List<Client> clients= new ArrayList<>();
      try {
	stm = conn.prepareStatement(SQL_FILTER);
        stm.setString(1, nom);
        stm.setString(2, nom);
	result = stm.executeQuery();
	
	while(result.next()) {
            clients.add(new Client(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      return clients;
    }
}   

