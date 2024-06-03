/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

/**
 *
 * @author ASUS
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.TypeMouvement;

public class TypeMouvementDAO extends DAO<TypeMouvement> {

    // Constructeur pour initialiser la connexion
    public TypeMouvementDAO(Connection conn) {
        super(conn);
    }

    
    @Override
    public boolean create(TypeMouvement typeMouvement){
        String query = "INSERT INTO type_mouvement (code_type, type, description) VALUES (?, ?, ?)";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, typeMouvement.getCodeType());
            stm.setString(2, typeMouvement.getType());
            stm.setString(3, typeMouvement.getDescription());
            return stm.executeUpdate() > 0;
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    @Override
    public TypeMouvement find(int codeType){
        String query = "SELECT * FROM type_mouvement WHERE code_type = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, codeType);
            result = stm.executeQuery();
            if (result.next()) {
                return new TypeMouvement(
                    result.getInt("code_type"),
                    result.getString("type"),
                    result.getString("description")
                );
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
 public TypeMouvement find(String type){
        String query = "SELECT * FROM type_mouvement WHERE type = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, type);
            result = stm.executeQuery();
            if (result.next()) {
                return new TypeMouvement(
                    result.getInt("code_type"),
                    result.getString("type"),
                    result.getString("description")
                );
            }
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // Méthode pour obtenir tous les TypeMouvement
    @Override
    public List<TypeMouvement> select() {
        List<TypeMouvement> typeMouvements = new ArrayList<>();
        String query = "SELECT * FROM type_mouvement";
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                typeMouvements.add(new TypeMouvement(
                    result.getInt("code_type"),
                    result.getString("type"),
                    result.getString("description")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeMouvements;
    }

    // Méthode pour mettre à jour un TypeMouvement
    @Override
    public boolean update(TypeMouvement typeMouvement){
        String query = "UPDATE type_mouvement SET type = ?, description = ? WHERE code_type = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, typeMouvement.getType());
            stm.setString(2, typeMouvement.getDescription());
            stm.setInt(3, typeMouvement.getCodeType());
            return stm.executeUpdate() > 0;
        }catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Méthode pour supprimer un TypeMouvement
    @Override
    public boolean delete(TypeMouvement typeMouvement) {
        String query = "DELETE FROM type_mouvement WHERE code_type = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, typeMouvement.getCodeType());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TypeMouvementDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

