/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public abstract class DAO<T> {
  protected Connection conn = null;
  protected ResultSet result;
  protected PreparedStatement stm;
            
  public DAO(Connection conn){
    this.conn = conn;
  }

  public abstract List<T> select();
  
  public abstract boolean create(T obj);
 
  public abstract boolean delete(T obj);
 
  public abstract boolean update(T obj);
 
  public abstract T find(int id);
}
