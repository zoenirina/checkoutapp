/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javaapp.bean.CommandeDetail;
import javaapp.factory.DAOFactory;

public class CommandeDetailDAOImpl extends CommandeDetailDAO {

//    private static final String SELECT_ALL_QUERY = 
//        "SELECT cd.id AS commandeDetailId, cd.idCommande, cd.idProduit, cd.remise AS remise, cd.quantite AS quantiteCommandee, " +
//        "IFNULL(SUM(ld.quantiteValide), 0) AS quantiteLivree,  IFNULL(SUM(ld.quantiteRecu), 0) AS quantiteLivree,  "
//            + "" +
//        "(cd.quantite - IFNULL(SUM(ld.quantiteValide), 0)) AS quantiteRestante " +
//        "FROM commandeDetails cd " +
//        "LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail " +
//        "GROUP BY cd.id, cd.idCommande, cd.idProduit, cd.quantite";
//    
        private static final String SELECT_ALL_QUERY = 
        "SELECT cd.id AS commandeDetailId, cd.idCommande AS idComm, cd.idProduit, cd.remise AS remise, cd.quantite AS quantiteCommandee, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN IFNULL(SUM(ld.quantiteRecu), 0) ELSE 0 END  quantiteLivree, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN (cd.quantite - IFNULL(SUM(ld.quantiteRecu), 0))  ELSE  cd.quantite  END  quantiteRestante " + 
        "FROM commandeDetails cd LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail LEFT JOIN livraisons l ON ld.idLivraison = l.id " +
        "GROUP BY cd.id, idComm, cd.idProduit, cd.quantite";
    
//    private static final String SELECT_ALL_QUERY2 = 
//        "SELECT cd.id AS commandeDetailId, cd.idCommande, cd.idProduit, cd.remise AS remise, cd.quantite AS quantiteCommandee, " +
//        "IFNULL(SUM(ld.quantiteValide), 0) AS quantiteLivree,  IFNULL(SUM(ld.quantiteRecu), 0) AS quantiteLivree,  " +
//        "(cd.quantite - IFNULL(SUM(ld.quantiteValide), 0)) AS quantiteRestante " +
//        "FROM commandeDetails cd " +
//        "LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail Where idCommande = ? " +
//        "GROUP BY cd.id, cd.idCommande, cd.idProduit, cd.quantite";
          private static final String SELECT_ALL_QUERY2 = 
        "SELECT cd.id AS commandeDetailId, cd.idCommande AS idComm, cd.idProduit, cd.remise AS remise, cd.quantite AS quantiteCommandee, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN IFNULL(SUM(ld.quantiteRecu), 0) ELSE 0 END  quantiteLivree, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN (cd.quantite - IFNULL(SUM(ld.quantiteRecu), 0))  ELSE  cd.quantite  END  quantiteRestante " + 
        "FROM commandeDetails cd LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail LEFT JOIN livraisons l ON ld.idLivraison = l.id WHERE cd.idCommande = ? " +
        "GROUP BY cd.id, idComm, cd.idProduit, cd.quantite";
    
//    private static final String FIND_BY_ID_QUERY = 
//        "SELECT cd.id AS commandeDetailId, cd.idCommande, cd.idProduit, cd.quantite AS quantiteCommandee, " +
//        "IFNULL(SUM(ld.quantiteValide), 0) AS quantiteLivree, " +
//        "(cd.quantite - IFNULL(SUM(ld.quantiteValide), 0)) AS quantiteRestante " +
//        "FROM commandeDetails cd " +
//        "LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail " +
//        "WHERE cd.id = ? " +
//        "GROUP BY cd.id, cd.idCommande, cd.idProduit, cd.quantite";
        private static final String FIND_BY_ID_QUERY =           
        "SELECT cd.id AS commandeDetailId, cd.idCommande AS idComm, cd.idProduit, cd.remise AS remise, cd.quantite AS quantiteCommandee, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN IFNULL(SUM(ld.quantiteValide), 0) ELSE 0 END  quantiteLivree, " +
        "CASE  WHEN l.status IN ('Livrée','Validée') THEN (cd.quantite - IFNULL(SUM(ld.quantiteValide), 0))  ELSE  cd.quantite  END  quantiteRestante " + 
        "FROM commandeDetails cd LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail LEFT JOIN livraisons l ON ld.idLivraison = l.id "
        + "WHERE cd.id = ? " +
        "GROUP BY cd.id, idComm, cd.idProduit, cd.quantite ";

    public CommandeDetailDAOImpl(Connection conn) {
        super(conn);
    }


    public List<CommandeDetail> selectAll() {
        List<CommandeDetail> commandeDetails = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SELECT_ALL_QUERY);
           result = stm.executeQuery();
                while (result.next()) {
                commandeDetails.add( getCommDet(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeDetails;
    }
    public List<CommandeDetail> selectAll(int id) {
        List<CommandeDetail> commandeDetails = new ArrayList<>();
        try {
            stm = conn.prepareStatement(SELECT_ALL_QUERY2);
            stm.setInt(1, id);
           result = stm.executeQuery();
                while (result.next()) {
                    CommandeDetail commDet = getCommDet(result);
                commandeDetails.add(commDet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeDetails;
    }
    @Override
    public CommandeDetail findById(int id) {
        CommandeDetail commandeDetail = null;
        try {
            stm = conn.prepareStatement(FIND_BY_ID_QUERY);

            stm.setInt(1, id);
           result = stm.executeQuery();
                if (result.next()) {
                    commandeDetail = getCommDet(result);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeDetail;
    }
    
    public boolean areAllDetailsDelivered(int commandeId) {
    String query = 
        "SELECT COUNT(*) AS total FROM commandeDetails cd " +
        "LEFT JOIN livraisonDetails ld ON cd.id = ld.idCommandeDetail " +
        "WHERE cd.idCommande = ? AND ld.quantiteValide IS NOT NULL " +
        "GROUP BY cd.id " +
        "HAVING SUM(ld.quantiteValide) < cd.quantite";

    try {
        stm = conn.prepareStatement(query);
        stm.setInt(1, commandeId);
        result = stm.executeQuery();
            return !result.next();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    // En cas d'erreur ou d'exception, retourner false par défaut
    return false;
}
  
    
    private CommandeDetail getCommDet(ResultSet result) throws SQLException{
        
        CommandeDetail comDetail  = new CommandeDetail();
        comDetail.setId(result.getInt("commandeDetailId"));
        comDetail.setIdCommande(result.getInt("idComm"));
        comDetail.setIdProduit(result.getInt("idProduit"));
        comDetail.setQuantite(result.getInt("quantiteCommandee"));
        comDetail.setQuantiteLivree(result.getInt("quantiteLivree"));
        comDetail.setQuantiteRestante(result.getInt("quantiteRestante"));
        comDetail.setRemise(result.getFloat("remise"));
        comDetail.setProduit(produitDAO.find(result.getInt("idProduit")));
        return comDetail;
    
    }

}

