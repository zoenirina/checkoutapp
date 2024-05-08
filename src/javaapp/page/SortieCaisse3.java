
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
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class SortieCaisse3 extends javax.swing.JPanel {
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String idSelected;
    public String datetime1;
    public LocalTime timepart;
    public JPanel pan;
    public Toast t;
    private final String SELECT_ALL = "select sortie_caisse.*,utilisateur.nomUtilisateur, tm.* from sortie_caisse inner join utilisateur on sortie_caisse.idCreateur=utilisateur.idUtilisateur "
            + "left join type_mouvement tm on sortie_caisse.idType= tm.code_type where idExercice in (select id from exercice where status=1)";
    private final String SELECT_ALL_ORDERED = SELECT_ALL+"ORDER BY sortie_caisse.dateSortie DESC";

    public SortieCaisse3(JPanel panel) {
        initComponents();
        pan= panel;
//        query="select * from sortie_caisse where idExercice in (select id from exercice where status=1) order by dateSortie DESC";
        query=SELECT_ALL_ORDERED;
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
//        btn_save_state= "insert";
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        btn_filter = new javaapp.component.ButtonRadius();
        jLabel7 = new javax.swing.JLabel();
        idExerciceActif = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        idSearch = new javax.swing.JTextField();
        btn_search1 = new javaapp.component.ButtonRadius();
        btn_refresh = new javaapp.component.ButtonRadius();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));

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

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Filtre par date:");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("à");

        btn_filter.setBackground(new java.awt.Color(240, 236, 243));
        btn_filter.setBorder(null);
        btn_filter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_filter.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_filter.setColor(new java.awt.Color(240, 236, 243));
        btn_filter.setColorClick(new java.awt.Color(255, 255, 255));
        btn_filter.setColorOver(new java.awt.Color(232, 230, 230));
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Exercice N°");

        idExerciceActif.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExerciceActif.setForeground(new java.awt.Color(248, 91, 50));
        idExerciceActif.setText("00012");

        jLabel8.setBackground(new java.awt.Color(255, 102, 0));
        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Transaction sortante");

        idSearch.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idSearch.setForeground(new java.awt.Color(102, 102, 102));

        btn_search1.setBackground(new java.awt.Color(240, 236, 243));
        btn_search1.setBorder(null);
        btn_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search1.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search1.setColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search1.setColorOver(new java.awt.Color(232, 230, 230));
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setForeground(new java.awt.Color(51, 51, 51));
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setText("refresh");
        btn_refresh.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(232, 230, 230));
        btn_refresh.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Numéro ou reference");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(535, 535, 535)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollTable)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(nombre_ligne)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(idSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10)
                            .addGap(18, 18, 18)
                            .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idExerciceActif, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombre_ligne)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(idSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(11, 11, 11))
                        .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
       SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        if( date2.getDate() == null){
        query="select * from sortie_caisse where dateSortie like '"+(String) dateFormat.format(date1.getDate())+"%' and idExercice in (select id from exercice where status=1)";
        }else{
        query="select * from sortie_caisse where dateSortie between '"+(String) dateFormat.format(date1.getDate())+"' AND '"+(String) dateFormat.format(date2.getDate())+"' and idExercice in (select id from exercice where status=1) order by dateSortie DESC";
        }
        refreshTable();
    }//GEN-LAST:event_btn_filterActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
        try{
            int number = Integer.parseInt(idSearch.getText());
           query="select * from sortie_caisse where id = "+idSearch.getText()+" and idExercice in (select id from exercice where status=1) order by dateSortie DESC"; 
        }catch(NumberFormatException e){
        query="select * from sortie_caisse where idSourceReference like '%"+idSearch.getText()+"%' and idExercice in (select id from exercice where status=1) order by dateSortie DESC";
        }
        refreshTable();
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        query="select * from sortie_caisse where idExercice in (select id from exercice where status=1) order by dateSortie DESC";
        date1.setDate(null);
        date2.setDate(null);
        idSearch.setText("");
        
        refreshTable();
    }//GEN-LAST:event_btn_refreshActionPerformed

@SuppressWarnings("empty-statement")
    public void refreshTable(){
        String[] column={"ID","Nature","Réference","Montant","Createur","Date de sortie"};
        String[] data= new String[6];
        DefaultTableModel model= new DefaultTableModel(null,column);
        try{
        connectiondb();
//        stm =conn.prepareStatement("select * from sortie_caisse");
//        resultSet = stm.executeQuery();
  Statement statement =(Statement)conn.createStatement();
       resultSet = statement.executeQuery(query);
       while(resultSet.next()){
           data[0]=resultSet.getString("id");
           data[1]=(resultSet.getString("idType") != null)?resultSet.getString("idType") :"inconnu";
           data[2]=resultSet.getString("idSourceReference");
           data[3]=resultSet.getString("montant");
           data[4]=resultSet.getString("nomUtilisateur");
           data[5]=resultSet.getString("dateSortie");

           model.addRow(data);
           rowcount();
       };
       //inserer le tableau dans le panel
      table.setModel(model);
      conn.close();
        }catch(SQLException e){
;}
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_filter;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JLabel idExerciceActif;
    private javax.swing.JTextField idSearch;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables

    private void rowcount() {
        nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
}
