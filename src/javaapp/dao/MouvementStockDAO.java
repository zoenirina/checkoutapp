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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.MouvementStock;
import javaapp.factory.DAOFactory;

/**
 *
 * @author ZOENIRINA
 */
public class MouvementStockDAO extends DAO<MouvementStock> {
    String sens = null;
    private final MagasinDAO magDAO;
    private final String SQL_ORDER = " ORDER BY dateMouvement DESC";
    private final ProduitDAO produitDAO;
    private TypeMouvementDAO typeDAO;
        
    public MouvementStockDAO(Connection conn, String sens) {
        super(conn);
        this.sens = sens;
        magDAO = DAOFactory.getMagasinDAO();
        produitDAO = DAOFactory.getProduitDAO();
        typeDAO = DAOFactory.getTypeMouvementDAO();
    }

    public MouvementStockDAO(Connection conn) {
        super(conn);  
        magDAO = DAOFactory.getMagasinDAO();
        produitDAO = DAOFactory.getProduitDAO();
    }

    @Override
    public List<MouvementStock> select() {
        List<MouvementStock> mouvements = new ArrayList<>();
        String query = ( sens.equals("Entrée")) ? "SELECT * FROM mouvementStock where sens='Entrée'" :
                "SELECT * FROM mouvementStock where sens='Sortie'";
        query+=SQL_ORDER;
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                mouvements.add(getMouvementStock(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return mouvements;
    }
    
    public List<MouvementStock> selectIN() {
        String query = "SELECT * FROM mouvementStock WHERE sens ='Entrée'  ";
        return selectWhere(query);
    }
    
    public List<MouvementStock> selectOUT() {
        String query = "SELECT * FROM mouvementStock WHERE sens = 'Sortie' ";
        return selectWhere(query);
    }
    
    public List<MouvementStock> selectEtatStock() {
        List<MouvementStock> mouvements = new ArrayList<>();
        String query =""; 
                try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                MouvementStock mouvement = new MouvementStock();
                mouvement.setId(result.getInt("id"));
                mouvement.setDescription(result.getString("description"));
                // Set other properties similarly
                mouvements.add(mouvement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return mouvements;
    }
    
    @Override
    public boolean create(MouvementStock obj) {
        String query = "INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, idTypeMouvement, sens, idMagasin, idExercice, idCreateur, idCommandeDet) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, obj.getDescription());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setString(4, obj.getDateMouvement());
            stm.setInt(5, obj.getIdTypeMouvement());
            stm.setString(6, sens);//obj.getSens()
            stm.setInt(7, obj.getIdMagasin());
            stm.setInt(8, obj.getIdExercice());
            stm.setInt(9, obj.getIdCreateur());
            stm.setInt(10, obj.getIdCommandeDet());
            stm.setInt(11, obj.getIdCreateur());
            stm.setInt(12, obj.getIdCommandeDet());
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }
    
    public boolean createOUT(MouvementStock obj) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, sens, idMagasin, idExercice, idCreateur, idSourceSortie, estValide) " +
            " VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE status=1), ?, ?)");

            stm.setString(1, obj.getDescription());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setString(4, obj.getDateMouvement());
            stm.setString(5, "Sortie");
            stm.setInt(6, obj.getIdMagasin());
            stm.setInt(7, obj.getIdSourceSortie());
            stm.setString(8, obj.getEstValide());
            
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }
    
        public boolean updateOUT(MouvementStock obj) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            stm = conn.prepareStatement("UPDATE mouvementStock set description=?, idProduit=?, quantite=?, dateMouvement=?, sens=?, idMagasin=?, "
                    + "idExercice=(SELECT id FROM exercice where status=1), idCreateur=(SELECT idUtilisateur from utilisateur WHERE status=1), estValide=? WHERE idSourceSortie=?" );
//                               "VALUES (?, ?, ?, ?, ?, ?, , (SELECT idUtilisateur from utilisateur WHERE status=1), ?, ?)");

            stm.setString(1, obj.getDescription());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setString(4, obj.getDateMouvement());
            stm.setString(5, "Sortie");
            stm.setInt(6, obj.getIdMagasin());
            stm.setString(7, obj.getEstValide());
            stm.setInt(8, obj.getIdSourceSortie());
            
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }

    public boolean createIN(MouvementStock obj) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            stm = conn.prepareStatement("INSERT INTO mouvementStock (description, idProduit, quantite, dateMouvement, sens, idMagasin, idExercice, idCreateur, idSourceEntree, estValide) " +
            " VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM exercice where status=1), (SELECT idUtilisateur from utilisateur WHERE status=1), ?, ?)");

            stm.setString(1, obj.getDescription());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setString(4, obj.getDateMouvement());
            stm.setString(5, "Entrée");
            stm.setInt(6, obj.getIdMagasin());
            stm.setInt(7, obj.getIdSourceEntree());
            stm.setString(8, obj.getEstValide());
            
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }
    
    public boolean updateIN(MouvementStock obj) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            stm = conn.prepareStatement("UPDATE mouvementStock set description=?,  quantite=?, dateMouvement=?, sens=?,  "
            + " idExercice=(SELECT id FROM exercice where status=1), idCreateur=(SELECT idUtilisateur from utilisateur WHERE status=1), estValide=? WHERE idSourceEntree=?" );

            stm.setString(1, obj.getDescription());
//            stm.setInt(2, obj.getIdProduit());
            stm.setInt(2, obj.getQuantite());
            stm.setString(3, obj.getDateMouvement());
            stm.setString(4, "Entrée");
//            stm.setInt(5, obj.getIdMagasin());
            stm.setString(5, obj.getEstValide());
            stm.setInt(6, obj.getIdSourceEntree());
            
            int rowsInserted = stm.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }
    
    @Override
    public boolean delete(MouvementStock obj) {
        String query = "DELETE FROM mouvementStock WHERE id = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, obj.getId());
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }

    public boolean deleteOUT(MouvementStock obj) {
        String query = "DELETE FROM mouvementStock WHERE idSourceOUT = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, obj.getId());
            int rowsDeleted = stm.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }
    
    @Override
    public boolean update(MouvementStock obj) {
        String query = "UPDATE mouvementStock SET description = ?, idProduit = ?, quantite = ?, dateMouvement = ?, idTypeMouvement = ?, sens = ?, "
                + "idMagasin = ?, idExercice = ?, idCreateur = ?, idCommandeDet = ? WHERE id = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, obj.getDescription());
            stm.setInt(2, obj.getIdProduit());
            stm.setInt(3, obj.getQuantite());
            stm.setString(4, obj.getDateMouvement());
            stm.setInt(5, obj.getIdTypeMouvement());
            stm.setString(6, sens);
            stm.setInt(7, obj.getIdMagasin());
            stm.setInt(8, obj.getIdExercice());
            stm.setInt(9, obj.getIdCreateur());
            stm.setInt(10, obj.getIdCommandeDet());
            stm.setInt(11, obj.getId());
            int rowsUpdated = stm.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeRessources();
        }
    }

    @Override
    public MouvementStock find(int id) {
        String query = "SELECT * FROM mouvementStock WHERE id = ?";
        MouvementStock mouvement = null;
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                mouvement = getMouvementStock(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return mouvement;
    }
    
    public List<MouvementStock> filterINBetweenDate(String date1, String date2) throws SQLException {
        return filterBetweenDate("Entrée", date1, date2);
    }
    
    public List<MouvementStock> filterOUTBetweenDate(String date1, String date2) throws SQLException {
         return filterBetweenDate("Sortie", date1, date2);
    } 
    
    public List<MouvementStock> filterBetweenDate(String sens, String date1, String date2){
        String query = " SELECT * FROM mouvementStock WHERE sens = '"+sens+"' AND dateMouvement BETWEEN '"+date1+"' AND '"+date2+"' ";
        return selectWhere(query);
    }
 
     public List<MouvementStock> filterINByDate(String date1) throws SQLException {
        return filterByDate("Entrée", date1);
    }
    public List<MouvementStock> filterOUTByDate(String date1) throws SQLException {
        return filterByDate("Entrée", date1);
    }
    
    public List<MouvementStock> filterOUTByDate(String date1, String date2) throws SQLException {
         return filterByDate("Sortie", date1);
    } 
    
  public List<MouvementStock> filterByDate(String sens, String date1){
        String query = "SELECT * FROM mouvementStock WHERE sens = '"+sens+"' AND dateMouvement like '"+date1+"%'";
        return selectWhere(query);
    }
    


    
    public List<MouvementStock> find(String refProduit) {
        
        List<MouvementStock> mouvements = new ArrayList<>();
        
        String query = "SELECT m.id AS idMagasin,id , m.refMagasin, m.labelle AS nomMagasin,  (SELECT COALESCE(SUM(CASE WHEN ms.sens = 'Entrée' THEN ms.quantite ELSE 0 END), 0) "
                + "-  COALESCE(SUM(CASE WHEN ms.sens = 'Sortie' THEN ms.quantite ELSE 0 END), 0) AS quantiteStock FROM mouvementStock ms "
                + "WHERE ms.idProduit in (SELECT id FROM produits WHERE refProduit=?)  AND ms.idMagasin = m.id) AS quantiteStock FROM magasin m "
                ;
        try {
            stm = conn.prepareStatement(query);
            stm.setString(1, refProduit);
            result = stm.executeQuery();
            
            while (result.next()) {
                 MouvementStock mouvement = new MouvementStock();
                mouvement.setId(result.getInt("id"));
//                mouvement.setIdSourceSortie(result.getInt("idSourceSortie"));
                mouvement.setIdMagasin(result.getInt("idMagasin"));
                mouvement.setMagasin(magDAO.find(result.getInt("idMagasin")));
                mouvement.setQuantiteStock(result.getInt("quantiteStock"));//
                mouvement.setRefMagasin(result.getString("refMagasin"));
                mouvements.add(mouvement);
            }
            
            return mouvements;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return null;
    }
    
 private MouvementStock getMouvementStock(ResultSet result) throws SQLException {
        MouvementStock mouvement = new MouvementStock();
        mouvement.setId(result.getInt("id"));
        mouvement.setDescription(result.getString("description"));
        mouvement.setIdProduit(result.getInt("idProduit"));
        mouvement.setQuantite(result.getInt("quantite"));
        mouvement.setDateMouvement(result.getString("dateMouvement"));
        mouvement.setIdTypeMouvement(result.getInt("idTypeMouvement"));
        mouvement.setSens(result.getString("sens"));
        mouvement.setIdMagasin(result.getInt("idMagasin"));
        mouvement.setIdExercice(result.getInt("idExercice"));
        mouvement.setIdCreateur(result.getInt("idCreateur"));
        mouvement.setIdCommandeDet(result.getInt("idCommandeDet"));
        mouvement.setIdSourceEntree(result.getInt("idSourceEntree"));
        mouvement.setIdSourceSortie(result.getInt("idSourceSortie"));
        mouvement.setMagasin(magDAO.find(result.getInt("idMagasin")));
        mouvement.setProduit(produitDAO.find(result.getInt("idProduit")));
        if(result.getString("idTypeMouvement") != null)mouvement.setTypeMouvement(typeDAO.find(result.getInt("idTypeMouvement")));
        
        return mouvement;
    }

    private void fillStatement(PreparedStatement stm, MouvementStock obj) throws SQLException {
        stm.setString(1, obj.getDescription());
        stm.setInt(2, obj.getIdProduit());
        stm.setInt(3, obj.getQuantite());
        stm.setString(4, obj.getDateMouvement());
        stm.setInt(5, obj.getIdTypeMouvement());
        stm.setString(6, obj.getSens());
        stm.setInt(7, obj.getIdMagasin());
        stm.setInt(8, obj.getIdExercice());
        stm.setInt(9, obj.getIdCreateur());
        stm.setInt(10, obj.getIdCommandeDet());
    }
    
        public void closeRessources(){
            try {
                if(stm != null)stm.close();
//                if(conn != null)conn.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(MouvementStockDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public MouvementStock findOUT(int id) {
    String query = "SELECT * FROM mouvementStock WHERE idSourceSortie = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                MouvementStock mouvement = getMouvementStock(result);
                return mouvement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return null;
    }
    
     public MouvementStock findIN(int id) {
    String query = "SELECT * FROM mouvementStock WHERE idSourceEntree = ? ";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                MouvementStock mouvement = getMouvementStock(result);
                return mouvement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return null;
    }

     public List<MouvementStock> filterINByRef(int idSourceRef) throws SQLException {
        return filterByRef("Entrée", idSourceRef);
    }
    
    public List<MouvementStock> filterOUTByRef(int idSourceRef) throws SQLException {
         return filterByRef("Sortie", idSourceRef);
    } 
    
  public List<MouvementStock> filterByRef(String sens, int idSourceRef){
        String query = "SELECT * FROM mouvementStock WHERE sens = '"+sens+"' AND idSourceReference = "+idSourceRef;
        return selectWhere(query);
    }

    public List<MouvementStock> filterOUTByMagasinAndDate(int idMagasin, String date1, String date2) {
         String query = "SELECT * FROM mouvementStock WHERE sens = 'Sortie' AND idMagasin = "+idMagasin+"  AND dateMouvement BETWEEN '"+date1+"' AND  '"+date2+"' ";
        return selectWhere(query);
    }
    
    public List<MouvementStock> filterINByMagasinAndDate(int idMagasin, String date1, String date2) {
         String query = "SELECT * FROM mouvementStock WHERE sens = 'Entrée' AND idMagasin = "+idMagasin+"  AND dateMouvement BETWEEN '"+date1+"' AND  '"+date2+"' ";
        return selectWhere(query);
    }

    public List<MouvementStock> filterINByMagasin(int idMagasin) {
        String query = "SELECT * FROM mouvementStock WHERE sens = 'Entrée' AND idMagasin = "+idMagasin+" ";
        return selectWhere(query);
    }
    
    public List<MouvementStock> filterOUTByMagasin(int idMagasin) {
        String query = "SELECT * FROM mouvementStock WHERE sens = 'Sortie' AND idMagasin = "+idMagasin+"";
        return selectWhere(query);
    }
    
    private List<MouvementStock> selectWhere(String query){
        List<MouvementStock> mouvements = new ArrayList<>();
        try {
            stm = conn.prepareStatement(query+" AND idExercice = (SELECT id FROM exercice WHERE status = 1 ) AND estValide='Oui' "+ SQL_ORDER);
            result = stm.executeQuery();
            while (result.next()) {
                mouvements.add(getMouvementStock(result));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return mouvements;
    }
}
