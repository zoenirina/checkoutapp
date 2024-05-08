package javaapp.page;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javaapp.swing.ScrollBar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Groupe1 extends javax.swing.JPanel {
    Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public Toast t;  
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String btn_save_state= "insert";
    
    public Groupe1() {
        initComponents();
        query="select * from groupe";
        refreshTable();
        scrollTable.setVerticalScrollBar(new ScrollBar());
        scrollTable.getVerticalScrollBar().setBackground(Color.WHITE);
        scrollTable.getViewport().setBackground(Color.WHITE);
    }
    
    public void connectiondb(){
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Groupe1.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            Logger.getLogger(Groupe1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        nbr_administrateur = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 782));

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setBackground(new java.awt.Color(255, 102, 0));
        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(36, 36, 36));
        jLabel7.setText("Liste des groupes utilisateurs");

        scrollTable.setBackground(new java.awt.Color(255, 255, 255));
        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollTable.setForeground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nom", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        scrollTable.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(51, 51, 51));
        nombre_ligne.setText("Tous (2)");

        nbr_administrateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nbr_administrateur.setForeground(new java.awt.Color(102, 102, 102));
        nbr_administrateur.setText("Administrateur (1)");

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound2Layout.createSequentialGroup()
                        .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(nbr_administrateur))
                    .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nbr_administrateur, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
 
    public void refreshTable(){
        String[] column={"ID","Nom","Description"};
        String[] data= new String[3];
        DefaultTableModel model= new DefaultTableModel(null,column);
        try{
        connectiondb();
        Statement statement =(Statement)conn.createStatement();
        ResultSet result = statement.executeQuery(query);
  
       while(result.next()){
           data[0]=result.getString("idGroupe");
           data[1]=result.getString("nomGroupe");
           data[2]=result.getString("description");
           model.addRow(data);
       }
       result.close();
      table.setModel(model);
      conn.close();
      nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
        }catch(SQLException e){
        e.printStackTrace(); 
        }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel nbr_administrateur;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables

}
