/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page.vente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javaapp.bean.Paiement;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaap.modal.ModalBonLivraison;
import javaapp.PrinterPDF.PdfReceiptGenerator;
import javaapp.component.Toast;
import javaapp.dao.PaiementDAO;
import javaapp.factory.DAOFactory;
import javaapp.pagination.PaginationItemRenderStyle1;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Reglement extends javax.swing.JPanel {
    private ModalActionEvent event;
private final int borderRadius = 10;
 Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    int pageCount=1;
    private final int activeMenu=1;
    private PdfReceiptGenerator pdfPrinter;
    private FormatUtils formater;
    private javaapp.bean.CommandeC commandeSelected = null;
    ModalBonLivraison modal = null;
    private final PaiementDAO paieDAO;

    public Reglement(JPanel panel) {
        initComponents();
        paieDAO = DAOFactory.getPaiementDAO();
        pan= panel;
        init();
    }
    
    public void init(){
        formater= new FormatUtils();
        refreshTable(paieDAO.select());
        scrollTable.getViewport().setBackground(Color.white);
        pdfPrinter= new PdfReceiptGenerator();
        displayRows(0,13);
        initialisePagination();
        pagination1.addEventPagination((int page) -> {
            System.out.print(page);
            displayRows((page - 1) * 13, page*13);
        });
//        tableOutput.getColumnModel().getColumn(6).setCellRenderer(new TableStatusCellRender());

        event =  new ModalActionEvent(){
            @Override
            public void onEdit(int row){
//                btn_creer_facture.setVisible(false);
                t = new Toast("Création de la facture réussie!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
                System.out.print("Evenement dans commande"+row);
            }

        };
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

        page1 = new javax.swing.JPanel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable = new javax.swing.JScrollPane();
        tableOutput = new javaapp.component.Table();
        btn_search = new javaapp.component.ButtonRadius();
        nomClientKeySearch = new javax.swing.JTextField();
        menu5 = new javax.swing.JLabel();
        scrollTable1 = new javax.swing.JScrollPane();
        tableInput = new javaapp.component.Table();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new javaapp.pagination.Pagination();
        date1 = new com.toedter.calendar.JDateChooser();
        date2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_search1 = new javaapp.component.ButtonRadius();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        nombre_ligne = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1577));

        page1.setBackground(new java.awt.Color(255, 255, 255));
        page1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(240, 236, 243));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        page1.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 90, 37, 37));

        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tableOutput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tableOutput.setForeground(new java.awt.Color(255, 255, 255));
        tableOutput.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableOutput.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        tableOutput.setRowHeight(45);
        scrollTable.setViewportView(tableOutput);

        page1.add(scrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 950, 610));

        btn_search.setBackground(new java.awt.Color(240, 236, 243));
        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_search.setColor(new java.awt.Color(240, 236, 243));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(240, 236, 243));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        page1.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, 37, 37));
        page1.add(nomClientKeySearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 270, 37));

        menu5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        menu5.setForeground(new java.awt.Color(51, 51, 51));
        menu5.setText("Mot clé");
        page1.add(menu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, -1, -1));

        scrollTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tableInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tableInput.setForeground(new java.awt.Color(255, 255, 255));
        tableInput.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInputMouseClicked(evt);
            }
        });
        scrollTable1.setViewportView(tableInput);

        page1.add(scrollTable1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 910, 892, 0));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        pagination1.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        page1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 760, 910, -1));

        date1.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 180, 37));

        date2.setBackground(new java.awt.Color(255, 255, 255));
        page1.add(date2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 170, 37));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("au");
        page1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 20, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Période du");
        page1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        btn_search1.setBackground(new java.awt.Color(240, 236, 243));
        btn_search1.setBorder(null);
        btn_search1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search1.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColor(new java.awt.Color(240, 236, 243));
        btn_search1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search1.setColorOver(new java.awt.Color(240, 236, 243));
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });
        page1.add(btn_search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 90, 37, 37));

        panelBorderRound1.setBackground(new java.awt.Color(227, 227, 255));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 255));
        nombre_ligne.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        page1.add(panelBorderRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 20, 150, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(page1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshPagination(){
        pageCount= (tableInput.getRowCount()%13 == 0) ? tableInput.getRowCount()/13 : tableInput.getRowCount()/13+1 ;
        displayRows(0,13);
        pagination1.setPagegination(1, pageCount);
    }
    private void initialisePagination(){
        pageCount= (tableInput.getRowCount()%13 == 0) ? tableInput.getRowCount()/13 : tableInput.getRowCount()/13+1 ;
        pagination1.setPaginationItemRender(new PaginationItemRenderStyle1());
        pagination1.setPagegination(1, pageCount);
    }
    
    
    private void displayRows(int start, int end) {
        for(int i = tableOutput.getRowCount()-1 ; i >= 0; i--){
        ((DefaultTableModel) tableOutput.getModel()).removeRow(i);
        }
    DefaultTableModel model = (DefaultTableModel) tableInput.getModel();
    DefaultTableModel newModel = new DefaultTableModel();

    // Copie des noms de colonnes
    for (int i = 0; i < model.getColumnCount(); i++) {
        newModel.addColumn(model.getColumnName(i));
    }

    // Ajout des lignes de la plage spécifiée
    for (int i = start; i < end && i < model.getRowCount(); i++) {
        Object[] row = new Object[model.getColumnCount()];
        for (int j = 0; j < model.getColumnCount(); j++) {
            row[j] = model.getValueAt(i, j);
        }
        newModel.addRow(row);
    }

    // Appliquer le nouveau modèle à JTable
    tableOutput.setModel(newModel);
}

 
    
    public void modifierPaiement(Connection connection, int idPaiement, String datePaiement, String refPaiement,
                             int idFacture, int idClient, float montantRecu, float restePaye) throws SQLException {
    String query = "UPDATE paiements SET datePaiement = ?, refPaiement = ?, idFacture = ?, idClient = ?, " +
            "montantRecu = ?, restePaye = ? WHERE id = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setString(1, datePaiement);
        pstmt.setString(2, refPaiement);
        pstmt.setInt(3, idFacture);
        pstmt.setInt(4, idClient);
        pstmt.setFloat(5, montantRecu);
        pstmt.setFloat(6, restePaye);
        pstmt.setInt(7, idPaiement);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("La modification du paiement a échoué, aucune ligne affectée.");
        }
    }
}

        
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        if(date1.getDate() != null && date2 != null){
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            refreshTable(paieDAO.findBetweenDates( (String) dateFormat.format(date1.getDate()), (String) dateFormat.format(date2.getDate())));
        }
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
//        query = getActiveQuery();
        refreshTable(paieDAO.select());
        nomClientKeySearch.setText("");
        date1.setDate(null);
        date2.setDate(null);
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tableInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInputMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableInputMouseClicked

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
        refreshTable(paieDAO.filter(nomClientKeySearch.getText()));
        
    }//GEN-LAST:event_btn_search1ActionPerformed
 
    
    public void refreshTable(List<Paiement> paiements){
    String[] column = {"Réference", "Date paiement","Client", "Facture","Total TTC","Montant réçu",  "Total versé"};
    Object[] data = new Object[8];
    DefaultTableModel model = new DefaultTableModel(null, column);
   
       for(Paiement paie : paiements){
            data[0] = paie.getRefPaiement();
            data[1] = paie.getDatePaiement();
            data[2] = paie.getClient().getNom()+" "+paie.getClient().getPrenom();
            data[3] = paie.getFacture().getRefFacture();
//            data[4] = formater.formatFloat(paie.getFacture().getMontant());
            data[4]= formater.formatFloat(paie.getFacture().getMontant());
            data[5]= formater.formatFloat(paie.getMontantRecu());
//            data[6]= formater.formatFloat(paie.getFacture().getMontant() - paieDAO.getMontantVerse(paie.getIdFacture()));
            data[6]= formater.formatFloat(paieDAO.getMontantVerse(paie.getIdFacture()));
//            data[7]= formater.formatFloat(paie.getRestePaye());
            
            model.addRow(data);
        }
        tableInput.setModel(model);
        displayRows(0,13);
        nombre_ligne.setText("Règlements ("+tableInput.getRowCount()+") ");

    }
 
    public void setEmptyForm(){
//        nomCreateur.setText("");
        btn_save_state="insert";
    }        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btn_search1;
    private com.toedter.calendar.JDateChooser date1;
    private com.toedter.calendar.JDateChooser date2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel menu5;
    private javax.swing.JTextField nomClientKeySearch;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JPanel page1;
    private javaapp.pagination.Pagination pagination1;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JScrollPane scrollTable1;
    private javaapp.component.Table tableInput;
    private javaapp.component.Table tableOutput;
    // End of variables declaration//GEN-END:variables

  
}
