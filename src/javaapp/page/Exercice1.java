/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Exercice1 extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;

    public Exercice1(JPanel panel) {
        initComponents();
        pan= panel;
        query="select * from exercice ORDER BY dateCreation DESC";
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
    }
    
    public void connectiondb(){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
     @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        g2.setColor(getBackground());
        g2.fill(roundRect);
        super.paintComponent(g);
        g2.setColor(getForeground());
        g2.draw(roundRect);
        g2.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        nombre_ligne = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1277));

        jLabel9.setBackground(new java.awt.Color(255, 102, 0));
        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(36, 36, 36));
        jLabel9.setText("Liste des exercices");

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        scrollTable.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollTable)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombre_ligne))
                        .addGap(0, 628, Short.MAX_VALUE)))
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombre_ligne)
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
 
    public void refreshTable(){
        String[] column={"ID","Solde initial","Createur","Date de création","Début","Fin","Status"};
        String[] data= new String[8];
        DefaultTableModel model= new DefaultTableModel(null,column);
        try{
            
        connectiondb();
        Statement statement =(Statement)conn.createStatement();
        ResultSet result = statement.executeQuery(query);
  
       while(result.next()){
           data[0]=result.getString("id");
           data[1]=result.getString("soldeInitiale");
           data[2]=result.getString("idCreateur");
           data[3]=result.getString("dateCreation");
           data[4]=result.getString("moisDebut")+" "+result.getString("anneeDebut");
           data[5]=result.getString("moisFin")+" "+result.getString("anneeFin");
           data[6]=(result.getInt("Status") == 1)?"actif":"innactif";
           model.addRow(data);
       }
       //inserer le tableau dans le panel
      table.setModel(model);
      result.close();
      statement.close();
      conn.close();
      rowcount();
        }catch(SQLException e){
            
}
}
         private void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables
}
