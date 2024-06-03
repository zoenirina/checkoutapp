/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.DroitAcces;

public class DroitAccesDAO extends DAO<DroitAcces> {

    public DroitAccesDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(DroitAcces droitAcces){
        String query = "INSERT INTO droit_acces (id, niveau_droit) VALUES (?, ?)";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, droitAcces.getId());
            stm.setString(2, droitAcces.getNiveauDroit());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(DroitAcces droitAcces){
        String query = "UPDATE droit_acces SET niveau_droit = ? WHERE id = ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setString(1, droitAcces.getNiveauDroit());
            stm.setInt(2, droitAcces.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(DroitAcces d){
        String query = "DELETE FROM droit_acces WHERE id = ?";
        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, d.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public DroitAcces selectById(int id){
        String query = "SELECT * FROM droit_acces WHERE id = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    return new DroitAcces(result.getInt("id"), result.getString("niveau_droit"));
                }
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
     public DroitAcces selectByDroit(String droit){
        String query = "SELECT * FROM droit_acces WHERE niveau_droit = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, droit);
            result = stm.executeQuery();
                if (result.next()) {
                    return new DroitAcces(result.getInt("id"), result.getString("niveau_droit"));
                }
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public List<DroitAcces> select(){
        List<DroitAcces> droits = new ArrayList<>();
        String query = "SELECT * FROM droit_acces";
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery(query);
            while (result.next()) {
                droits.add(new DroitAcces(result.getInt("id"), result.getString("niveau_droit")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return droits;
    }

    @Override
    public DroitAcces find(int id) {
        String query = "SELECT * FROM droit_acces WHERE id = ? ";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    return new DroitAcces( result.getInt("id"), result.getString("niveau_droit") );
                }
        } catch (SQLException ex) {
            Logger.getLogger(DroitAccesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}

