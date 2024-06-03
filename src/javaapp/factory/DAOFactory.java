/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.factory;

import java.sql.Connection;
import javaapp.dao.ClassementProduitDAO;
import javaapp.dao.CommandeCDAO;
import javaapp.dao.CommandeDetailDAO;
import javaapp.dao.CommandeDetailDAOImpl;
import javaapp.dao.CommandeDetailFournisseurDAO;
import javaapp.dao.CommandeDetailFournisseurDAOImpl;
import javaapp.dao.CommandeFournisseurDAO;
import javaapp.dao.ConfigDAO;
import javaapp.dao.DroitAccesDAO;
import javaapp.dao.EntreeCaisseDAO;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.FactureDAO;
import javaapp.dao.FactureFournisseurDAO;
import javaapp.dao.GroupeDAO;
import javaapp.dao.LivraisonDAO;
import javaapp.dao.LivraisonDetailDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.MouvementStockDAO;
import javaapp.dao.PaiementDAO;
import javaapp.dao.PaiementFournisseurDAO;
import javaapp.dao.PersonneDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ProfileDAO;
import javaapp.dao.ReceptionDAO;
import javaapp.dao.ReceptionDetailDAO;
import javaapp.dao.SortieCaisseDAO;
import javaapp.dao.StockDAO;
import javaapp.dao.TypeMouvementDAO;
import javaapp.dao.UniteStockageDAO;
import javaapp.dao.UserDAO;
import javaapp.swing.DBconnection;

public class DAOFactory {
    protected static final Connection conn = DBconnection.getInstance();   


    public static PersonneDAO getClientDAO(){
      return new PersonneDAO(conn, "clients");
    }

  public static ExerciceDAO getExerciceDAO(){
    return new ExerciceDAO(conn);
  }

  public static MagasinDAO getMagasinDAO(){
    return new MagasinDAO(conn);
  }

  public static ProfileDAO getProfileDAO(){
    return new ProfileDAO(conn);
  }   

  public static EntreeCaisseDAO getEntreeCaisseDAO(){
    return new EntreeCaisseDAO(conn);
  }
  
  public static SortieCaisseDAO getSortieCaisseDAO(){
    return new SortieCaisseDAO(conn);
  }
  
    public static ConfigDAO getConfigDAO(){
    return new ConfigDAO(conn);
  }
    public static DroitAccesDAO getDroitAccesDAO(){
        return new DroitAccesDAO(conn);
    }
    
    public static UserDAO getUserDAO(){
      return new UserDAO(conn);
    }
    
    public static GroupeDAO getGroupeDAO(){
      return new GroupeDAO(conn);
    }
    
     public static ProduitDAO getProduitDAO(){
      return new ProduitDAO(conn);
    }

    public static CommandeCDAO getCommandeDAO(){
      return new CommandeCDAO(conn);
    }
    
    public static CommandeFournisseurDAO getCommandeFournisseurDAO(){
      return new CommandeFournisseurDAO(conn);
    }
    
    public static CommandeDetailDAO getCommandeDetailDAO(){
      return new CommandeDetailDAO(conn);
    }
        public static UniteStockageDAO getUniteStockageDAO(){
      return new UniteStockageDAO(conn);
    }
    
    public static MouvementStockDAO getEntreeStockDAO(){
      return new MouvementStockDAO(conn, "Entrée");
    }
    
    public static MouvementStockDAO getSortieStockDAO(){
      return new MouvementStockDAO(conn, "Sortie");
    }
        
    public static PersonneDAO getFournisseurDAO(){
        return new PersonneDAO(conn, "fournisseurs");
    }
      
    public static FactureDAO getFactureDAO(){
        return new FactureDAO(conn);
    }
    
    public static FactureFournisseurDAO getFactureFournisseurDAO(){
        return new FactureFournisseurDAO(conn);
    }
    
    public static LivraisonDAO getLivraisonDAO(){
        return new LivraisonDAO(conn);
    }
    
        public static LivraisonDetailDAO getLivraisonDetailDAO(){
        return new LivraisonDetailDAO(conn);
    }
        
    public static PaiementDAO getPaiementDAO(){
        return new PaiementDAO(conn);
    }
    
    public static CommandeDetailFournisseurDAO getCommandeDetailFournisseurDAO() {
        return new CommandeDetailFournisseurDAO(conn);
    }

    public static ReceptionDAO getReceptionDAO() {
        return new  ReceptionDAO(conn);
        }

    public static ReceptionDetailDAO getReceptionDetailDAO() {
        return new ReceptionDetailDAO(conn);
    }

    public static PaiementFournisseurDAO getPaiementFournisseurDAO() {
        return new PaiementFournisseurDAO(conn);
    }

    public static StockDAO getStockDAO() {
        return new StockDAO(conn); 
    }

    public static SortieCaisseDAO getSortieCaisseDAODAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static MouvementStockDAO getMouvementStockDAO() {
        return new MouvementStockDAO(conn); 
    }
    
        public static ClassementProduitDAO getClassementProduitDAO() {
        return new ClassementProduitDAO(conn); 
    }

    public static CommandeDetailDAOImpl getCommandeDetailDAOImpl() {
        return new CommandeDetailDAOImpl(conn);
    }

    public static CommandeDetailFournisseurDAOImpl getCommandeDetailFournisseurDAOImpl() {
        return new CommandeDetailFournisseurDAOImpl(conn); 
    }

    public static TypeMouvementDAO getTypeMouvementDAO() {
        return new TypeMouvementDAO(conn);
    }
}
