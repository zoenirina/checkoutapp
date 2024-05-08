/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.factory;

import java.sql.Connection;
import javaapp.dao.ClientDAO;
import javaapp.dao.CommandeDAO;
import javaapp.dao.ConfigDAO;
import javaapp.dao.EntreeCaisseDAO;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.MagasinDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ProfileDAO;
import javaapp.dao.SortieCaisseDAO;
import javaapp.dao.UniteStockageDAO;
import javaapp.dao.UserDAO;
import javaapp.swing.DBconnection;

public class DAOFactory {
    protected static final Connection conn = DBconnection.getInstance();   

    public static ClientDAO getClientDAO(){
      return new ClientDAO(conn);
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
    
    public static UserDAO getUserDAO(){
      return new UserDAO(conn);
    }
    
     public static ProduitDAO getProduitDAO(){
      return new ProduitDAO(conn);
    }

    public static CommandeDAO getCommandeDAO(){
      return new CommandeDAO(conn);
    }
        public static UniteStockageDAO getUniteStockageDAO(){
      return new UniteStockageDAO(conn);
    }
}
