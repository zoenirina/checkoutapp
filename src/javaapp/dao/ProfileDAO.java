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
import javaapp.bean.Profile;


/**
 *
 * @author ZOENIRINA
 */
public class ProfileDAO extends DAO<Profile> {

    final static String SQL_INSERT = "INSERT INTO profile (nom, prenom, fonction, dateNaiss, adresse, tel, idUtilisateur) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_SELECT = "SELECT * FROM profile";
    final static String SQL_UPDATE = "UPDATE profile SET nom = ?, prenom = ?, fonction = ?, dateNaiss = ?, adresse = ?, tel = ?, idUtilisateur = ? WHERE idProfile = ?";
    final static String SQL_DELETE = "DELETE FROM profile WHERE idProfile = ?";
    final static String SQL_FIND = SQL_SELECT + " WHERE idProfile = ?";
    final static String SQL_WHERE_NOM = SQL_SELECT + " WHERE nom LIKE ? OR prenom LIKE ?";
    private final String SQL_FIND_BY_USER = SQL_SELECT + " WHERE idUtilisateur = ? ";

    public ProfileDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Profile> select() {
        List<Profile> profiles = new ArrayList<>();
        try { stm = conn.prepareStatement(SQL_SELECT);
            result = stm.executeQuery();
            while (result.next()) {
                profiles.add(map(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    @Override
    public boolean create(Profile obj) {
        try {
            stm = this.conn.prepareStatement(SQL_INSERT);
            stm.setString(1, obj.getNom());
            stm.setString(2, obj.getPrenom());
            stm.setString(3, obj.getFonction());
            stm.setString(4, obj.getDateNaiss());
            stm.setString(5, obj.getAdresse());
            stm.setString(6, obj.getTel());
            stm.setInt(7, obj.getIdUtilisateur());

            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Profile obj) {
        try { stm = this.conn.prepareStatement(SQL_DELETE);
            stm.setInt(1, obj.getIdProfile());
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Profile obj) {
        try { stm = this.conn.prepareStatement(SQL_UPDATE);
            stm.setString(1, obj.getNom());
            stm.setString(2, obj.getPrenom());
            stm.setString(3, obj.getFonction());
            stm.setString(4, obj.getDateNaiss());
            stm.setString(5, obj.getAdresse());
            stm.setString(6, obj.getTel());
            stm.setInt(7, obj.getIdUtilisateur());
            stm.setInt(8, obj.getIdProfile());

            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Profile find(int id) {
        Profile profile = null;
        try {
            stm = this.conn.prepareStatement(SQL_FIND);
            stm.setInt(1, id);
             result = stm.executeQuery();
                if (result.next()) {
                    profile = map(result);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    public List<Profile> findByName(String name) {
        List<Profile> profiles = new ArrayList<>();
        try { stm = this.conn.prepareStatement(SQL_WHERE_NOM);
            stm.setString(1, "'%" + name + "%'");
            stm.setString(2, "'%" + name + "%'");
            result = stm.executeQuery();
                while (result.next()) {
                    profiles.add(map(result));
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    private Profile map(ResultSet result) throws SQLException {
        Profile profile = new Profile();
        profile.setIdProfile(result.getInt("idProfile"));
        profile.setNom(result.getString("nom"));
        profile.setPrenom(result.getString("prenom"));
        profile.setFonction(result.getString("fonction"));
        profile.setDateNaiss(result.getString("dateNaiss"));
        profile.setAdresse(result.getString("adresse"));
        profile.setTel(result.getString("tel"));
        profile.setIdUtilisateur(result.getInt("idUtilisateur"));
        return profile;
    }

    public Profile findByUtilisateur(int idUtilisateur) {
        Profile profile = null;
        try {
            stm = this.conn.prepareStatement(SQL_FIND_BY_USER);
            stm.setInt(1, idUtilisateur);
             result = stm.executeQuery();
                if (result.next()) {
                    profile = map(result);
                }
        } catch (SQLException e) {
        }
        return profile;
    }

    public List<Profile> filter(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
