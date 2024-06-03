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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.UniteStockage;

/**
 *
 * @author ZOENIRINA
 */
public class UniteStockageDAO  extends DAO<UniteStockage> {

    private static final String SQL_INSERT = "INSERT INTO uniteStockage (unite, poids, capacite, volume, longueur, package, labelle, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM uniteStockage ";
    private static final String SQL_UPDATE = "UPDATE uniteStockage SET unite = ?, poids = ?, capacite = ?, volume = ?, longueur = ?, package = ?, labelle = ?, description = ? WHERE id = ?";
    private static final String SQL_FIND = SQL_SELECT+" WHERE id = ? ";
    
//        private ResultSet result;
//    private PreparedStatement stm;
    
    private int idGenerated=0;
    
    public UniteStockageDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<UniteStockage> select() {
       List<UniteStockage> uniteStockage= new ArrayList<>();
        try {
	stm = conn.prepareStatement(SQL_SELECT);
	result = stm.executeQuery();
	while(result.next()) {
            uniteStockage.add(new UniteStockage(result.getInt("id"), result.getInt("unite"), result.getFloat("poids"), result.getFloat("capacite"), result.getFloat("volume"), result.getFloat("longueur"), result.getInt("packageU"),result.getString("labelle"), result.getString("description")));
        }
	}catch(SQLException ex) {
	    Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return uniteStockage;
    
    }

    @Override
    public boolean create(UniteStockage uniteStockage) {
        int affectedRows = 0;
        try {
            stm = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
             stm.setInt(1, (int) uniteStockage.getUnite() ); // Unite
                   stm.setFloat(2, uniteStockage.getPoids()); // Poids
                   stm.setFloat(3, uniteStockage.getCapacite()); // Capacité
                   stm.setFloat(4, uniteStockage.getVolume()); // Volume
                   stm.setFloat(5, uniteStockage.getLongueur()); // Longueur
                   stm.setInt(6, uniteStockage.getPackageU()); // Package
                   stm.setString(7, uniteStockage.getLabelle()); // Labelle
                   stm.setString(8, uniteStockage.getDescription()); // Description

                 affectedRows = stm.executeUpdate();
    if (affectedRows > 0) {
        try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                setIdGenerated(generatedKeys.getInt(1));
            }
            
             if(generatedKeys != null)generatedKeys.close();
        }}
        } catch (SQLException ex) {
            Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

                  
        return affectedRows > 0;
    }

    @Override
    public boolean delete(UniteStockage uniteStockage) {
        return false;
    
    }

    @Override
    public boolean update(UniteStockage uniteStockage) {
    int affectedRows = 0;
        try {
            stm = conn.prepareStatement(SQL_UPDATE);
            stm.setInt(1, (int) uniteStockage.getUnite() ); // Unite
            stm.setFloat(2, uniteStockage.getPoids()); // Poids
            stm.setFloat(3, uniteStockage.getCapacite()); // Capacité
            stm.setFloat(4, uniteStockage.getVolume()); // Volume
            stm.setFloat(5, uniteStockage.getLongueur()); // Longueur
            stm.setInt(6, uniteStockage.getPackageU()); // Package
            stm.setString(7, uniteStockage.getLabelle()); // Labelle
            stm.setString(8, uniteStockage.getDescription()); // Description
            stm.setInt(9, uniteStockage.getId());

            affectedRows = stm.executeUpdate();
        
        } catch (SQLException ex) {
            Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
     return affectedRows > 0;
    }

    @Override
    public UniteStockage find(int id) {
     UniteStockage uniteStockage= new UniteStockage();
        try {
	stm = conn.prepareStatement(SQL_FIND);
        stm.setInt(1, id);
	result = stm.executeQuery();
	while(result.next()) {
            uniteStockage = new UniteStockage(result.getInt("id"), result.getInt("unite"), result.getFloat("poids"), result.getFloat("capacite"), result.getFloat("volume"), result.getFloat("longueur"), result.getInt("package"),result.getString("labelle"), result.getString("description"));

        }
	}catch(SQLException ex) {
	    Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            try {
                if(stm != null)stm.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(UniteStockageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return uniteStockage;
    }

    public int getIdGenerated() {
        return idGenerated;
    }

    public void setIdGenerated(int idGenerated) {
        this.idGenerated = idGenerated;
    }
    
}
