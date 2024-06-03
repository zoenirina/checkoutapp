package javaapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Exercice;

public class ExerciceDAO extends DAO<Exercice> {

    public ExerciceDAO(Connection conn) {
        super(conn);
    }

    @Override
    public List<Exercice> select() {
        String query = "SELECT * FROM exercice";
        List<Exercice> exercices = new ArrayList<>();
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            while (result.next()) {
                exercices.add(getExercice(result));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return exercices;
    }

    @Override
    public boolean create(Exercice exercice) {
        String query = "INSERT INTO exercice (status, description, moisDebut, anneeDebut, moisFin, anneeFin, soldeInitiale, dateCreation, idCreateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = conn.prepareStatement(query);
            stm.setBoolean(1, exercice.isStatus());
            stm.setString(2, exercice.getDescription());
            stm.setInt(3, exercice.getMoisDebut());
            stm.setInt(4, exercice.getAnneeDebut());
            stm.setInt(5, exercice.getMoisFin());
            stm.setInt(6, exercice.getAnneeFin());
            stm.setFloat(7, exercice.getSoldeInitiale());
            stm.setString(8, exercice.getDateCreation());
            stm.setInt(9, exercice.getIdCreateur());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }

    @Override
    public boolean delete(Exercice exercice) {
        String query = "DELETE FROM exercice WHERE id = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, exercice.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }
    @Override
    public boolean update(Exercice exercice) {
        String query = "UPDATE exercice SET status = ?, description = ?, moisDebut = ?, anneeDebut = ?, moisFin = ?, anneeFin = ?, soldeInitiale = ?, dateCreation = ?, idCreateur = ? WHERE id = ?";
        try {
            stm = conn.prepareStatement(query);
            stm.setBoolean(1, exercice.isStatus());
            stm.setString(2, exercice.getDescription());
            stm.setInt(3, exercice.getMoisDebut());
            stm.setInt(4, exercice.getAnneeDebut());
            stm.setInt(5, exercice.getMoisFin());
            stm.setInt(6, exercice.getAnneeFin());
            stm.setFloat(7, exercice.getSoldeInitiale());
            stm.setString(8, exercice.getDateCreation());
            stm.setInt(9, exercice.getIdCreateur());
            stm.setInt(10, exercice.getId());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return false;
    }

    @Override
    public Exercice find(int id) {
        String query = "SELECT * FROM exercice WHERE id = ?";
        Exercice exercice = null;
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            result = stm.executeQuery();
            if (result.next()) {
                exercice = getExercice(result);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return exercice;
    }
    
        public int getActiveExercice() {
        String query = "SELECT id FROM exercice WHERE status = 1";
        int i = 0;
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            if (result.next()) {
                i = result.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return i;
    }

    private Exercice getExercice(ResultSet result) throws SQLException {
        Exercice exercice = new Exercice();
        exercice.setId(result.getInt("id"));
        exercice.setStatus(result.getBoolean("status"));
        exercice.setDescription(result.getString("description"));
        exercice.setMoisDebut(result.getInt("moisDebut"));
        exercice.setAnneeDebut(result.getInt("anneeDebut"));
        exercice.setMoisFin(result.getInt("moisFin"));
        exercice.setAnneeFin(result.getInt("anneeFin"));
        exercice.setSoldeInitiale(result.getFloat("soldeInitiale"));
        exercice.setDateCreation(result.getString("dateCreation"));
        exercice.setIdCreateur(result.getInt("idCreateur"));
        return exercice;
    }

    private void closeResources() {
        try {
            if (stm != null) stm.close();
            if (result != null) result.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float getActiveExerciceSoldeInitiale() {
    String query = "SELECT (select soldeInitiale from exercice where status=1)+(SELECT sum(entree_caisse.montant) FROM entree_caisse, exercice  where entree_caisse.idExercice=exercice.id and exercice.status=1 ) - (SELECT sum(sortie_caisse.montant) FROM sortie_caisse, exercice  where sortie_caisse.idExercice=exercice.id and exercice.status=1 )  as soldeInitiale";
        float f = 0.0f;
        try {
            stm = conn.prepareStatement(query);
            result = stm.executeQuery();
            if (result.next()) {
                f = result.getInt("soldeInitiale");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExerciceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return f;
    }
}
