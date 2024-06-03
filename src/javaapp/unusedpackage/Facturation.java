/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.unusedpackage;

import javaapp.page.parametre.Groupe;
import javaapp.page.Caisse.SortieCaisse;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class Facturation extends javax.swing.JPanel {
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

    public Facturation(JPanel panel) {
        initComponents();
        pan= panel;
//        setOpaque(false);
        query="select * from exercice ORDER BY dateCreation DESC";
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        setInputSelect();
        getSoldeInitiale();
        btn_delete.setVisible(false);
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

        moisDebut = new com.toedter.calendar.JMonthChooser();
        anneeDebut = new com.toedter.calendar.JYearChooser();
        description = new javax.swing.JTextField();
        moisFin = new com.toedter.calendar.JMonthChooser();
        anneeFin = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nomCreateur = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        soldeInit = new javax.swing.JSpinner();
        idExercice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nombre_ligne = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        jLabel10 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_delete = new javaapp.component.ButtonRadius();
        status = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        nomClient = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        btn_modify_invoice_item2 = new javaapp.component.ButtonRadius();
        jLabel52 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btn_modify_invoice_item3 = new javaapp.component.ButtonRadius();
        fac_id_client = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1004, 1277));

        moisDebut.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        description.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        description.setForeground(new java.awt.Color(102, 102, 102));

        moisFin.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Début");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Fin");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Description");

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(102, 102, 102));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Nom utilisateur");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Solde actuelle");

        soldeInit.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        idExercice.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExercice.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Numéro d'exercice");

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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        jLabel10.setBackground(new java.awt.Color(255, 102, 0));
        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(36, 36, 36));
        jLabel10.setText("Enregistrer un nouvel exercice");

        btn_save.setBackground(new java.awt.Color(248, 91, 50));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_save.setColor(new java.awt.Color(248, 91, 50));
        btn_save.setColorClick(new java.awt.Color(248, 91, 50));
        btn_save.setColorOver(new java.awt.Color(255, 51, 0));
        btn_save.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(240, 236, 243));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(255, 51, 0));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 51, 0));
        btn_delete.setColor(new java.awt.Color(255, 255, 255));
        btn_delete.setColorClick(new java.awt.Color(255, 255, 255));
        btn_delete.setColorOver(new java.awt.Color(255, 248, 246));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        status.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        status.setForeground(new java.awt.Color(102, 102, 102));
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactif", "Actif" }));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Status");

        jPanel2.setBackground(new java.awt.Color(241, 248, 240));

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        nomClient.setBackground(new java.awt.Color(241, 248, 240));
        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(102, 102, 255));
        nomClient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 240), 2));

        jLabel43.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(134, 134, 253));
        jLabel43.setText("Client n°");

        btn_modify_invoice_item2.setBackground(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item2.setBorder(null);
        btn_modify_invoice_item2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item2.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item2.setColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item2.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item2.setColorOver(new java.awt.Color(51, 51, 255));
        btn_modify_invoice_item2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_item2ActionPerformed(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(134, 134, 253));
        jLabel52.setText("Etat de la commande");

        jComboBox1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(102, 102, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_modify_invoice_item3.setBackground(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item3.setBorder(null);
        btn_modify_invoice_item3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        btn_modify_invoice_item3.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item3.setColor(new java.awt.Color(102, 102, 255));
        btn_modify_invoice_item3.setColorClick(new java.awt.Color(255, 255, 255));
        btn_modify_invoice_item3.setColorOver(new java.awt.Color(51, 51, 255));
        btn_modify_invoice_item3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modify_invoice_item3ActionPerformed(evt);
            }
        });

        fac_id_client.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fac_id_client.setForeground(new java.awt.Color(102, 102, 255));
        fac_id_client.setText("001");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fac_id_client, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nomClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_modify_invoice_item2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(fac_id_client))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify_invoice_item2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_modify_invoice_item3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(status, 0, 753, Short.MAX_VALUE)
                            .addComponent(description)
                            .addComponent(idExercice)
                            .addComponent(soldeInit)
                            .addComponent(nomCreateur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(moisDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(anneeDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(moisFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(anneeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(scrollTable)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombre_ligne))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(53, 53, 53))
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nomCreateur, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idExercice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(soldeInit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(anneeDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moisDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(moisFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(anneeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombre_ligne)
                .addGap(63, 63, 63)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(263, 263, 263)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

        connectiondb();

        try {
            switch(btn_save_state){
                    case "insert":
                if(status.getSelectedIndex() == 1){
                stm = conn.prepareStatement("update exercice set status=0");
                stm.executeUpdate();
                }
                  DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                String id=(idExercice.getText().trim().isEmpty())?null:idExercice.getText();
                stm = conn.prepareStatement("insert into exercice(id,soldeInitiale,idCreateur,moisDebut,anneeDebut,moisFin,anneeFin,description,status,dateCreation) values (?,?,(select idUtilisateur from utilisateur where nomUtilisateur=?),?,?,?,?,?,?,?)");
                stm.setString(1,id );
                stm.setFloat(2, (float) soldeInit.getValue());
                stm.setString(3, (String) nomCreateur.getSelectedItem());
                stm.setInt(4, moisDebut.getMonth()+1);
                stm.setInt(5, anneeDebut.getYear());
                stm.setInt(6, moisFin.getMonth()+1);
                stm.setInt(7, anneeFin.getYear());
                stm.setString(8, description.getText());
                stm.setInt(9, status.getSelectedIndex());
                stm.setString(10, (String) now.format(dtFormat));
                stm.executeUpdate();
                t = new Toast("Insertion réussie", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
//                JOptionPane.showMessageDialog(null, idExercice.getText()+(String) soldeInit.getValue()+(String) nomCreateur.getSelectedItem()+moisDebut.getMonth()+anneeDebut.getYear()+status.getSelectedIndex(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
            break;
                    case "update":
                        if(status.getSelectedIndex() == 1){
                stm = conn.prepareStatement("update exercice set status=0");
                stm.executeUpdate();
                }
                stm = conn.prepareStatement("update exercice set id=?, soldeInitiale=?, idCreateur=(select idUtilisateur from utilisateur where nomUtilisateur =?), moisDebut=?, anneeDebut=?, moisFin=?, anneeFin=?, description=?, status=? WHERE id=?");
                stm.setString(1, idExercice.getText());
                stm.setFloat(2, (float) soldeInit.getValue());
                stm.setString(3, (String) nomCreateur.getSelectedItem());
                stm.setInt(4, moisDebut.getMonth()+1);
                stm.setInt(5, anneeDebut.getYear());
                stm.setInt(6, moisFin.getMonth()+1);
                stm.setInt(7, anneeFin.getYear());
                stm.setString(8, description.getText());
                stm.setInt(9, status.getSelectedIndex());
                stm.setString(10, idSelected);
                stm.executeUpdate();
//                btn_save_state="insert";
                t = new Toast("Modification enregistrée", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
                t.showtoast();
//                JOptionPane.showMessageDialog(null, "modification réussie!","Sucess",JOptionPane.INFORMATION_MESSAGE);
             break;
            }
            conn.close();
            refreshTable();

        } catch (SQLException ex) {
            Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        connectiondb();
        try{stm= conn.prepareStatement("delete from exercice where id=?");
            stm.setString(1, idSelected);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "modification réussie!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        }catch(Exception e){
            e.printStackTrace(); ;}
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        btn_delete.setVisible(true);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int i = table.getSelectedRow();
        btn_save_state = "update";
        idSelected = model.getValueAt(i, 0).toString();
//        nomGroupe.setText(model.getValueAt(i, 1).toString());
//        description.setText(model.getValueAt(i, 2).toString());
        
           try {
            connectiondb();
            stm =conn.prepareStatement("select exercice.*,utilisateur.nomUtilisateur as nomUtilisateur  from exercice,utilisateur  where exercice.id=? and exercice.idCreateur = utilisateur.idUtilisateur");
            stm.setString(1, idSelected);
            resultSet = stm.executeQuery();
  
           while(resultSet.next()){
           idExercice.setText(resultSet.getString("id"));
           soldeInit.setValue(resultSet.getFloat("soldeInitiale"));
           nomCreateur.setSelectedItem(resultSet.getString("nomUtilisateur"));
           moisDebut.setMonth(resultSet.getInt("moisDebut")-1);
//moisDebut.setMonth(5);
           anneeDebut.setYear(resultSet.getInt("anneeDebut"));
           moisFin.setMonth(resultSet.getInt("moisFin")-1);
//moisFin.setMonth(8);
           anneeFin.setYear(resultSet.getInt("anneeFin"));
           description.setText(resultSet.getString("description"));
           status.setSelectedIndex(resultSet.getInt("status"));
           }
//           typeMouvement.setSelectedItem(null);
           resultSet.close();
           stm.close();
           conn.close();   
           } catch (SQLException ex) {
            Logger.getLogger(SortieCaisse.class.getName()).log(Level.SEVERE, null, ex);
//           } catch (ParseException ex) {
//            Logger.getLogger(SortieCaisse.class.getName()).log(Level.SEVERE, null, ex);
           }
    }//GEN-LAST:event_tableMouseClicked

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        setEmptyForm();
        btn_delete.setVisible(false);
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_modify_invoice_item2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_item2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_invoice_item2ActionPerformed

    private void btn_modify_invoice_item3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modify_invoice_item3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_modify_invoice_item3ActionPerformed
 
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
        }catch(Exception e){
        e.printStackTrace();}
}
         private void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
    public void setEmptyForm(){
           idExercice.setText("");
//           soldeInit.setValue(0);
           nomCreateur.setSelectedItem(null);
           moisDebut.setMonth(0);
           anneeDebut.setYear(2024);
           moisFin.setMonth(0);
           anneeFin.setYear(2024);
           description.setText("");
           status.setSelectedIndex(0);
           getSoldeInitiale();
           btn_save_state="insert";
    }
    
        private void setInputSelect(){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT utilisateur.idUtilisateur as idUtilisateur, utilisateur.nomUtilisateur as nomUtilisateur FROM utilisateur, groupe where utilisateur.idGroupe=groupe.idGroupe and groupe.idDroit=1");
                resultSet = stm.executeQuery();
               nomCreateur.addItem("");
                while (resultSet.next()) {
                    nomCreateur.addItem(resultSet.getString("nomUtilisateur"));
                }
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        private int getSoldeInitiale(){
          try {
                connectiondb();
//                stm = conn.prepareStatement("SELECT sum(entree_caisse.montant) as montantEntree, sum(sortie_caisse.montant) as montantSortie, FROM entree_caisse, sortie_caisse where utilisateur.idGroupe=groupe.idGroupe and groupe.idDroit=1");
                stm = conn.prepareStatement("SELECT (select soldeInitiale from exercice where status=1)+(SELECT sum(entree_caisse.montant) FROM entree_caisse, exercice  where entree_caisse.idExercice=exercice.id and exercice.status=1 ) - (SELECT sum(sortie_caisse.montant) FROM sortie_caisse, exercice  where sortie_caisse.idExercice=exercice.id and exercice.status=1 )  as soldeInitiale");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    soldeInit.setValue(resultSet.getFloat("soldeInitiale"));
                }
                //SELECT exercice.soldeInitiale + sum(entree_caisse.montant) - sum(sortie_caisse.montant) as soldeInitiale FROM entree_caisse, sortie_caisse, exercice where entree_caisse.idExercice=exercice.id and sortie_caisse.idExercice=exercice.id and exercice.status=1
               resultSet.close();
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return 0;
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser anneeDebut;
    private com.toedter.calendar.JYearChooser anneeFin;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_modify_invoice_item2;
    private javaapp.component.ButtonRadius btn_modify_invoice_item3;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JTextField description;
    private javax.swing.JLabel fac_id_client;
    private javax.swing.JTextField idExercice;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private com.toedter.calendar.JMonthChooser moisDebut;
    private com.toedter.calendar.JMonthChooser moisFin;
    private javax.swing.JTextField nomClient;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JSpinner soldeInit;
    private javax.swing.JComboBox<String> status;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables
}
