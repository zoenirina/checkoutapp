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
import javaapp.bean.SortieCaisse;
import javaapp.factory.DAOFactory;

public class SortieCaisseDAO  extends DAO <SortieCaisse>{
    private final UserDAO userDAO;
    private final String SQL_SELECT = "SELECT * from sortie_caisse where idExercice in (select id from exercice where status=1) ";
    private final String SQL_ORDER =" ORDER BY dateSortie DESC";

    public SortieCaisseDAO(Connection conn) {
        super(conn);
        userDAO = DAOFactory.getUserDAO();
    }

    @Override
    public boolean create(SortieCaisse sortieCaisse){
        String sql = "INSERT INTO sortie_caisse(id, idSourceReference, montant, devise, idCreateur, idExercice, dateSortie, idType, description) "
                   + "VALUES (?, ?, ?, (SELECT devise FROM config WHERE idConfig=1), (SELECT idUtilisateur FROM utilisateur WHERE status=1), ?, ?, (SELECT code_type FROM type_mouvement WHERE type=?), ?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, null);
            stm.setInt(2, sortieCaisse.getIdSourceReference());
            stm.setFloat(3, sortieCaisse.getMontant());
            stm.setInt(4, sortieCaisse.getIdExercice());
            stm.setString(5, sortieCaisse.getDateSortie());
            stm.setInt(6, sortieCaisse.getIdType());
            stm.setString(7, sortieCaisse.getDescription());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SortieCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(SortieCaisse sortieCaisse){
        String sql = "UPDATE sortie_caisse SET description=?, idSourceReference=?, montant=?, devise=(SELECT devise FROM config WHERE idConfig=1), idExercice=?, idType=(SELECT code_type FROM type_mouvement WHERE type=?) WHERE id=?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, sortieCaisse.getDescription());
            stm.setInt(2, sortieCaisse.getIdSourceReference());
            stm.setFloat(3, sortieCaisse.getMontant());
            stm.setInt(4, sortieCaisse.getIdExercice());
            stm.setInt(5, sortieCaisse.getIdType());
            stm.setInt(6, sortieCaisse.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SortieCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int id){
        String sql = "DELETE FROM sortie_caisse WHERE id=?";
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SortieCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<SortieCaisse> filterByDate(String date1, String date2) {
        List<SortieCaisse> sortieCaisses = new ArrayList<>();
        String sql = date2 == null
                ? "SELECT * FROM sortie_caisse WHERE dateSortie LIKE ? AND idExercice IN (SELECT id FROM exercice WHERE status=1)"
                : "SELECT * FROM sortie_caisse WHERE dateSortie BETWEEN ? AND ? AND idExercice IN (SELECT id FROM exercice WHERE status=1) ORDER BY dateSortie DESC";
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, date1 + '%');
            if (date2 != null) {
                stm.setString(2, date2);
            }
            result = stm.executeQuery();
            while (result.next()) {
                sortieCaisses.add(mapResultSetToSortieCaisse(result));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SortieCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sortieCaisses;
    }

    private SortieCaisse mapResultSetToSortieCaisse(ResultSet resultSet) throws SQLException{
        SortieCaisse sortieCaisse = new SortieCaisse();
        sortieCaisse.setId(resultSet.getInt("id"));
        sortieCaisse.setIdSourceReference(resultSet.getInt("idSourceReference"));
        sortieCaisse.setMontant(resultSet.getFloat("montant"));
        sortieCaisse.setIdExercice(resultSet.getInt("idExercice"));
        sortieCaisse.setDateSortie(resultSet.getString("dateSortie"));
        sortieCaisse.setIdType(resultSet.getInt("idType"));
        sortieCaisse.setDescription(resultSet.getString("description"));
        sortieCaisse.setCreateur(userDAO.find( resultSet.getInt("idCreateur") ));
        if(resultSet.getString("idType") != null) sortieCaisse.setTypeMouvement(DAOFactory.getTypeMouvementDAO().find(resultSet.getInt("idType")));
        return sortieCaisse;
    }

    @Override
    public List<SortieCaisse> select() {
    String sql = SQL_SELECT +SQL_ORDER;
        List<SortieCaisse> sortieCaisses = new ArrayList<>();
        try {
            stm = conn.prepareStatement(sql);            
            result = stm.executeQuery();
                while (result.next()) {
                    sortieCaisses.add( mapResultSetToSortieCaisse(result) );
                }
            } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sortieCaisses;
    }

    @Override
    public boolean delete(SortieCaisse obj) {
     String sql = "DELETE FROM sortie_caisse WHERE id = ?";
        int i = 0;
        try {
             stm = conn.prepareStatement(sql);
            stm.setInt(1, obj.getId());
            i = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i!= 0;
    }

    @Override
    public SortieCaisse find(int id) {
   String sql = SQL_SELECT+" AND id = ?";
        
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            
            result = stm.executeQuery();
                if (result.next()) {
                    return mapResultSetToSortieCaisse(result);
                }
            } catch (SQLException ex) {
            Logger.getLogger(EntreeCaisseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
     public float getSoldeActuel(){
        float soldeActuel=0;  
        try {
            stm = conn.prepareStatement("SELECT (select soldeInitiale from exercice where status=1)+(SELECT sum(entree_caisse.montant) FROM entree_caisse, exercice  where entree_caisse.idExercice=exercice.id and exercice.status=1 ) - (SELECT sum(sortie_caisse.montant) FROM sortie_caisse, exercice  where sortie_caisse.idExercice=exercice.id and exercice.status=1 )  as soldeInitiale");
            result = stm.executeQuery();
            while (result.next()) {
                soldeActuel=result.getFloat("soldeInitiale");
            }
        } catch (SQLException e) {}
        
        return soldeActuel;
    
        }
}
