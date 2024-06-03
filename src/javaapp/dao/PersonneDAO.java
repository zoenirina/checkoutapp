/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Personne;

/**
 *
 * @author ASUS
 */
public class PersonneDAO  extends DAO<Personne> {
    
    public String TABLE_NAME= "";
    protected String SQL_INSERT = ""; //"INSERT INTO "+TABLE_NAME+" (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    protected String SQL_SELECT ="";// "SELECT * FROM "+TABLE_NAME;
    protected String SQL_UPDATE ="";// "UPDATE "+TABLE_NAME+" SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
    protected String SQL_DELETE ="";// "DELETE FROM "+TABLE_NAME+" WHERE id = ?";
    protected String SQL_FIND = "";
//    protected String SQL_FILTER = "";
   
//    protected ResultSet result;
//    protected PreparedStatement stm;
    
    public PersonneDAO(Connection conn, String TABLE_NAME) {
        super(conn);
        this.TABLE_NAME=TABLE_NAME;
        this.SQL_INSERT = "INSERT INTO "+TABLE_NAME+" (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.SQL_SELECT = "SELECT * FROM "+TABLE_NAME;
        this.SQL_UPDATE = "UPDATE "+TABLE_NAME+" SET nom = ?, prenom = ?, email1 = ?, email2 = ?, tel1 = ?, tel2 = ?, tel3 = ?, adresse = ?, NIF = ?, stat = ? WHERE id = ?";
        this.SQL_DELETE = "DELETE FROM "+TABLE_NAME+" WHERE id = ?";
        this.SQL_FIND = SQL_SELECT+" WHERE id=? ";
//        this.SQL_FILTER = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
        
    }
    public PersonneDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Personne> select(){
       List<Personne> Personnes= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
            Personnes.add(new Personne(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(PersonneDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
             closeResources();
        }
        return Personnes;
    }
    
    @Override
    public boolean create(Personne Personne) {
    int affectedRows = 0;
         try {
            stm = conn.prepareStatement(SQL_INSERT);
            stm.setString(1, Personne.getNom());
            stm.setString(2, Personne.getPrenom());
            stm.setString(3, Personne.getEmail1());
            stm.setString(4, Personne.getEmail2());
            stm.setString(5, Personne.getTel1());
            stm.setString(6, Personne.getTel2());
            stm.setString(7, Personne.getTel3());
            stm.setString(8, Personne.getAdresse());
            stm.setString(9, Personne.getNIF());
            stm.setString(10, Personne.getStat());
//            stm.setInt(11, Personne.getId());  // Assurez-vous que getIdSelected() récupère bien l'identifiant du Personne
            affectedRows = stm.executeUpdate();
        } catch (SQLException e) {
        } finally {
             closeResources();
        }
        return affectedRows != 0;
     
    }

    @Override
    public boolean delete(Personne Personne) {
        int affectedRows = 0;
        
        try {
            stm = conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, Personne.getId());
            affectedRows = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PersonneDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             closeResources();
        }
                
    return affectedRows != 0;
    }

    @Override
    public boolean update(Personne Personne) {
        int affectedRows = 0;
         try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, Personne.getNom());
            stm.setString(2, Personne.getPrenom());
            stm.setString(3, Personne.getEmail1());
            stm.setString(4, Personne.getEmail2());
            stm.setString(5, Personne.getTel1());
            stm.setString(6, Personne.getTel2());
            stm.setString(7, Personne.getTel3());
            stm.setString(8, Personne.getAdresse());
            stm.setString(9, Personne.getNIF());
            stm.setString(10, Personne.getStat());
            stm.setInt(11, Personne.getId());  
            affectedRows = stm.executeUpdate();
        } catch (SQLException e) {
        } finally {
              closeResources();
        }
        return affectedRows != 0;
    }

    @Override
    public Personne find(int id) {
    Personne Personne = new Personne(); 
    try {
        stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
        if(result.next()){
        Personne = new Personne(id, result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status"));
        }
    } catch (SQLException e) {
    }finally{
         closeResources();
    }
    return Personne;   
    }
  
    public List<Personne> filter(String nom) throws SQLException{
      List<Personne> Personnes= new ArrayList<>();
      try {
	stm = conn.prepareStatement(SQL_SELECT+" WHERE nom like '%"+nom+"%' OR prenom like '%"+nom+"%'");
//        stm.setString(1, nom);
//        stm.setString(2, nom);
	result = stm.executeQuery();
	
	while(result.next()) {
            Personnes.add(new Personne(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email1"), result.getString("email2"), result.getString("tel1"), result.getString("tel2"),result.getString("tel3"), result.getString("adresse"), result.getString("NIF"), result.getString("stat"), result.getString("status")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(PersonneDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
           closeResources();
        }
      return Personnes;
    }
    
     private void closeResources() {
        try {
            if(stm != null)stm.close();
            if(result != null)result.close();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeCDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}   

