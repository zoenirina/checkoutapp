/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Config;

/**
 *
 * @author ZOENIRINA
 */
public class ConfigDAO   extends DAO<Config>{

    final static String SQL_INSERT = "INSERT INTO clients (nom, prenom, email1, email2, tel1, tel2, tel3, adresse, NIF, stat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM config ";
    final static String SQL_UPDATE = "UPDATE config SET DBuserName = ?, DBpassWord = ?, DBpath = ?, codeRecuperation = ?, DBName = ?, pathSavePDF = ?, devise = ? WHERE idConfig = 1";
    final static String SQL_DELETE = "DELETE FROM clients WHERE id = ?";
    final static String SQL_FIND = SQL_SELECT+" WHERE idConfig=? ";
    final static String SQL_WHERE_NOM = SQL_SELECT+" WHERE nom like ? OR prenom like ?";
    
    public ConfigDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Config> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Config obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Config obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Config conf) {
        int i=0;
        
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, conf.getDBuserName());
            stm.setString(2, conf.getDBpassWord());
            stm.setString(3, conf.getDBpath());
            stm.setString(4, conf.getCodeRecuperation());
            stm.setString(5, conf.getDBName());
            stm.setString(6, conf.getPathSavePDF());
            stm.setString(7, conf.getDevise());
            i=stm.executeUpdate(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(ConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        try {
                if(stm != null)stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
            return i != 0;
    }

    @Override
    public Config find(int id) {
   Config config = new Config(); 
    try {
        stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
        if(result.next()){
        config = new Config(id, result.getString("DBuserName"), result.getString("DBpassWord"), result.getString("DBpath"), result.getString("codeRecuperation"), result.getString("DBName"), result.getString("pathSavePDF"),result.getString("devise"));
        }
    } catch (SQLException e) {
    }finally{
    try {
        if(result != null)result.close();
        if(stm != null)stm.close();
    } catch (SQLException ex) {
        Logger.getLogger(ConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    return config;   
    }
    
}
