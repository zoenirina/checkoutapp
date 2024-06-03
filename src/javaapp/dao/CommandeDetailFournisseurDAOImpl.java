package javaapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.CommandeDetailFournisseur;
import javaapp.factory.DAOFactory;

public class CommandeDetailFournisseurDAOImpl extends CommandeDetailFournisseurDAO {

    private static final String SELECT_ALL_QUERY = 
        "SELECT cdf.id AS commandeDetailFournisseurId, cdf.idCommande AS idComm, cdf.idProduit, cdf.remise AS remise, cdf.quantite AS quantiteCommandee, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN IFNULL(SUM(rd.quantiteRecu), 0) ELSE 0 END AS quantiteRecue, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN (cdf.quantite - IFNULL(SUM(rd.quantiteRecu), 0)) ELSE cdf.quantite END AS quantiteRestante " +
        "FROM commandeDetailsFournisseur cdf " +
        "LEFT JOIN receptionDetails rd ON cdf.id = rd.idCommandeDetailFournisseur " +
        "LEFT JOIN receptions r ON rd.idReception = r.id " +
        "GROUP BY cdf.id, cdf.idCommande, cdf.idProduit, cdf.quantite";

    private static final String SELECT_ALL_QUERY2 = 
        "SELECT cdf.id AS commandeDetailFournisseurId, cdf.idCommande AS idComm, cdf.idProduit, cdf.remise AS remise, cdf.quantite AS quantiteCommandee, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN IFNULL(SUM(rd.quantiteRecu), 0) ELSE 0 END AS quantiteRecue, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN (cdf.quantite - IFNULL(SUM(rd.quantiteRecu), 0)) ELSE cdf.quantite END AS quantiteRestante " +
        "FROM commandeDetailsFournisseur cdf " +
        "LEFT JOIN receptionDetails rd ON cdf.id = rd.idCommandeDetailFournisseur " +
        "LEFT JOIN receptions r ON rd.idReception = r.id " +
        "WHERE cdf.idCommande = ? " +
        "GROUP BY cdf.id, cdf.idCommande, cdf.idProduit, cdf.quantite";

    private static final String FIND_BY_ID_QUERY = 
        "SELECT cdf.id AS commandeDetailFournisseurId, cdf.idCommande AS idComm, cdf.idProduit, cdf.remise AS remise, cdf.quantite AS quantiteCommandee, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN IFNULL(SUM(rd.quantiteValide), 0) ELSE 0 END AS quantiteRecue, " +
        "CASE WHEN r.status IN ('Réçu', 'Validée') THEN (cdf.quantite - IFNULL(SUM(rd.quantiteValide), 0)) ELSE cdf.quantite END AS quantiteRestante " +
        "FROM commandeDetailsFournisseur cdf " +
        "LEFT JOIN receptionDetails rd ON cdf.id = rd.idCommandeDetailFournisseur " +
        "LEFT JOIN receptions r ON rd.idReception = r.id " +
        "WHERE cdf.id = ? " +
        "GROUP BY cdf.id, cdf.idCommande, cdf.idProduit, cdf.quantite";
    private final ProduitDAO produitDAO;
    public CommandeDetailFournisseurDAOImpl(Connection conn) {
        super(conn);
        produitDAO = DAOFactory.getProduitDAO();
    }

    public List<CommandeDetailFournisseur> selectAll() {
        List<CommandeDetailFournisseur> commandeDetails = new ArrayList<>();
        try { 
            stm = conn.prepareStatement(SELECT_ALL_QUERY);
            result = stm.executeQuery();
            while (result.next()) {
                commandeDetails.add(getCommDet(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return commandeDetails;
    }

    public List<CommandeDetailFournisseur> selectAll(int id) {
        List<CommandeDetailFournisseur> commandeDetails = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SELECT_ALL_QUERY2);
            stm.setInt(1, id);
            result = stm.executeQuery();
                while (result.next()) {
                    CommandeDetailFournisseur commDet = getCommDet(result);
                    commandeDetails.add(commDet);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return commandeDetails;
    }

    public CommandeDetailFournisseur findById(int id) {
        CommandeDetailFournisseur commandeDetail = null;
        try {
            stm = conn.prepareStatement(FIND_BY_ID_QUERY);
            stm.setInt(1, id);
            result = stm.executeQuery();
                if (result.next()) {
                    commandeDetail = getCommDet(result);
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return commandeDetail;
    }

    public boolean areAllDetailsDelivered(int commandeId) {
        String query = 
            "SELECT COUNT(*) AS total FROM commandeDetailsFournisseur cdf " +
            "LEFT JOIN receptionDetails rd ON cdf.id = rd.idCommandeDetailFournisseur " +
            "LEFT JOIN receptions r ON rd.idReception = r.id " +
            "WHERE cdf.idCommande = ? AND (r.status IN ('Réçu', 'Validée')) " +
            "GROUP BY cdf.id " +
            "HAVING SUM(rd.quantiteValide) < cdf.quantite";

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, commandeId);
            result = stm.executeQuery();
                return !result.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRessources();
        }
        return false;
    }

    private CommandeDetailFournisseur getCommDet(ResultSet result) throws SQLException {
        CommandeDetailFournisseur comDetail = new CommandeDetailFournisseur();
        comDetail.setId(result.getInt("commandeDetailFournisseurId"));
        comDetail.setIdCommande(result.getInt("idComm"));
        comDetail.setIdProduit(result.getInt("idProduit"));
        comDetail.setQuantite(result.getInt("quantiteCommandee"));
        comDetail.setQuantiteRecue(result.getInt("quantiteRecue"));
        comDetail.setQuantiteRestante(result.getInt("quantiteRestante"));
        comDetail.setRemise(result.getFloat("remise"));
        comDetail.setProduit(produitDAO.find(result.getInt("idProduit")));
        return comDetail;
    }
    
      public void closeRessources(){
            try {
                if(stm != null)stm.close();
//                if(conn != null)conn.close();
                if(result != null)result.close();
            } catch (SQLException ex) {
                Logger.getLogger(MagasinDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
