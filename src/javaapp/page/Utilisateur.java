
package javaapp.page;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javaapp.component.Toast;
/**
 *
 * @author ZOENIRINA
 */
public class Utilisateur extends javax.swing.JPanel {

    public String getIdSelected() {
        return idSelected;
    }

    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }
    public boolean newUser;
      public  Connection conn = null;
     public  String url = "jdbc:sqlite:caisse.db";
     public String   query ="select utilisateur.idUtilisateur as idUtilisateur,utilisateur.nomUtilisateur as nomUtilisateur,utilisateur.dateCreation as dateCreation,utilisateur.status as status,groupe.nomGroupe as nomGroupe from utilisateur,groupe where utilisateur.idGroupe=groupe.idGroupe";
     public Toast t;  
     private int rowSelected;
     private String idSelected = null;
     public PreparedStatement stm ;
     public ResultSet resultSet;
     public String btnSaveState;
     public String title="Comptes utilisateus";
//       public String query ="select * from utilisateur";
     
    public Utilisateur() {
        
        initComponents();
          
        formPanel.setVisible(false);
        connectiondb(); 
        query ="select utilisateur.idUtilisateur as idUtilisateur,utilisateur.nomUtilisateur as nomUtilisateur,utilisateur.dateCreation as dateCreation,utilisateur.status as status,groupe.nomGroupe as nomGroupe from utilisateur,groupe where utilisateur.idGroupe=groupe.idGroupe";
        refreshTable();
        setInputSelect();
       
        scrollTable.getViewport().setBackground(Color.white);     
    }
    
    private  void connectiondb(){
       
        try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection("jdbc:sqlite:caisse.db");
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnnewuser = new javaapp.component.ButtonRadius();
        jTextField1 = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        roundedLabel1 = new javaapp.component.RoundedLabel();
        formPanel = new javax.swing.JPanel();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nomGroupe = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnsave = new javaapp.component.ButtonRadius();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel9 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        matricule = new javax.swing.JComboBox<>();
        password = new javax.swing.JTextField();
        btn_cancel = new javaapp.component.ButtonRadius();
        jLabel2 = new javax.swing.JLabel();
        btndelete = new javaapp.component.ButtonRadius();
        jPanel4 = new javax.swing.JPanel();
        num_facture = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeArticleCommande = new javaapp.component.Table();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        numClient = new javax.swing.JLabel();
        nomClient = new javax.swing.JLabel();
        telClient = new javax.swing.JLabel();
        emailClient = new javax.swing.JLabel();
        adresseClient = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        montantCommande = new javax.swing.JLabel();
        montantTotal1 = new javax.swing.JLabel();
        doit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        refCommande = new javax.swing.JLabel();
        numCommande = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        montantTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        montantCommande1 = new javax.swing.JLabel();
        doit1 = new javax.swing.JLabel();
        btn_print2 = new javaapp.component.ButtonRadius();
        saveLivraisonDetail2 = new javaapp.component.ButtonRadius();
        btndelete1 = new javaapp.component.ButtonRadius();

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1075, 800));

        scrollTable.setBackground(new java.awt.Color(255, 255, 255));
        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollTable.setForeground(new java.awt.Color(255, 255, 255));

        table.setForeground(new java.awt.Color(51, 51, 51));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom utilisateur", "Matricule", "Nom complet", "Date de création", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 36, 36));
        jLabel1.setText("Liste des utilisateurs");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        btnnewuser.setBackground(new java.awt.Color(248, 91, 50));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Ajouter");
        btnnewuser.setBorderColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorClick(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorOver(new java.awt.Color(255, 105, 82));
        btnnewuser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnnewuser.setRadius(18);
        btnnewuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewuserActionPerformed(evt);
            }
        });

        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(240, 236, 243));

        roundedLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/FFFF.png"))); // NOI18N

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(listPanelLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(roundedLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(roundedLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
        );

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 36, 36));
        jLabel4.setText("Créer un compte utilisateur");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Matricule de l'employé");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Rôle");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Mot de passe");

        btnsave.setBackground(new java.awt.Color(102, 102, 255));
        btnsave.setBorder(null);
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Enregistrer");
        btnsave.setBorderColor(new java.awt.Color(102, 102, 255));
        btnsave.setColor(new java.awt.Color(102, 102, 255));
        btnsave.setColorClick(new java.awt.Color(102, 102, 255));
        btnsave.setColorOver(new java.awt.Color(98, 88, 232));
        btnsave.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btn_back.setBackground(new java.awt.Color(255, 255, 255));
        btn_back.setBorder(null);
        btn_back.setForeground(new java.awt.Color(51, 51, 51));
        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/prev2.png"))); // NOI18N
        btn_back.setText("Retour");
        btn_back.setBorderColor(new java.awt.Color(255, 255, 255));
        btn_back.setColor(new java.awt.Color(255, 255, 255));
        btn_back.setColorClick(new java.awt.Color(255, 255, 255));
        btn_back.setColorOver(new java.awt.Color(247, 251, 246));
        btn_back.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Nom utilisateur");

        username.setForeground(new java.awt.Color(102, 102, 102));
        username.setSelectionColor(new java.awt.Color(102, 102, 102));

        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/createaccount2.png"))); // NOI18N

        btndelete.setBackground(new java.awt.Color(255, 11, 31));
        btndelete.setBorder(null);
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("Supprimer");
        btndelete.setBorderColor(new java.awt.Color(255, 0, 0));
        btndelete.setColor(new java.awt.Color(255, 11, 31));
        btndelete.setColorClick(new java.awt.Color(255, 11, 31));
        btndelete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));

        num_facture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        num_facture.setForeground(new java.awt.Color(51, 51, 51));
        num_facture.setText("Facture n° ");

        jScrollPane2.setBorder(null);

        listeArticleCommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(listeArticleCommande);

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 227, 227)));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Client");

        numClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        numClient.setForeground(new java.awt.Color(255, 102, 0));
        numClient.setText("2334");

        nomClient.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        nomClient.setForeground(new java.awt.Color(51, 51, 51));
        nomClient.setText("John DOE");

        telClient.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        telClient.setForeground(new java.awt.Color(51, 51, 51));
        telClient.setText("John DOE");

        emailClient.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        emailClient.setForeground(new java.awt.Color(51, 51, 51));
        emailClient.setText("John DOE");

        adresseClient.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        adresseClient.setForeground(new java.awt.Color(51, 51, 51));
        adresseClient.setText("John DOE");

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Client N°");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numClient))
                    .addComponent(nomClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(telClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailClient, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adresseClient)
                    .addComponent(jLabel8))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numClient)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailClient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adresseClient)
                .addGap(21, 21, 21))
        );

        montantCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantCommande.setForeground(new java.awt.Color(51, 51, 51));
        montantCommande.setText("57000");

        montantTotal1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantTotal1.setForeground(new java.awt.Color(51, 51, 51));
        montantTotal1.setText("Frais");

        doit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        doit.setForeground(new java.awt.Color(51, 51, 51));
        doit.setText("57000");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Identification du vendeur");

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("nom_vendeur");

        refCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refCommande.setForeground(new java.awt.Color(51, 51, 51));

        numCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        numCommande.setForeground(new java.awt.Color(51, 51, 51));

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("tel_vendeur");

        jLabel18.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("email_vendeur");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Date: 12/04/2024");

        jPanel2.setBackground(new java.awt.Color(33, 31, 45));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        montantTotal.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantTotal.setForeground(new java.awt.Color(255, 255, 255));
        montantTotal.setText("TOTAL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(montantTotal)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(montantTotal)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(33, 31, 45));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        montantCommande1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantCommande1.setForeground(new java.awt.Color(255, 255, 255));
        montantCommande1.setText("NET A PAYER");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(montantCommande1)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(montantCommande1)
                .addContainerGap())
        );

        doit1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        doit1.setForeground(new java.awt.Color(51, 51, 51));
        doit1.setText("57000");

        btn_print2.setBackground(new java.awt.Color(240, 236, 243));
        btn_print2.setBorder(null);
        btn_print2.setForeground(new java.awt.Color(51, 51, 51));
        btn_print2.setText("Programmer une livraison");
        btn_print2.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_print2.setColorClick(new java.awt.Color(240, 240, 240));
        btn_print2.setColorOver(new java.awt.Color(240, 240, 240));
        btn_print2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_print2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(num_facture)
                                .addComponent(jLabel19)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(montantTotal1)
                                .addGap(143, 143, 143))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(96, 96, 96)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(doit1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(montantCommande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(doit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(doit1)
                        .addGap(173, 173, 173))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(num_facture)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addGap(42, 42, 42)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(montantCommande)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(montantTotal1)
                                    .addComponent(doit))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_print2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        saveLivraisonDetail2.setBackground(new java.awt.Color(255, 222, 177));
        saveLivraisonDetail2.setBorder(null);
        saveLivraisonDetail2.setForeground(new java.awt.Color(248, 91, 50));
        saveLivraisonDetail2.setText("Choisir");
        saveLivraisonDetail2.setBorderColor(new java.awt.Color(255, 222, 177));
        saveLivraisonDetail2.setColor(new java.awt.Color(255, 222, 177));
        saveLivraisonDetail2.setColorClick(new java.awt.Color(255, 222, 177));
        saveLivraisonDetail2.setColorOver(new java.awt.Color(255, 222, 177));
        saveLivraisonDetail2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        saveLivraisonDetail2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLivraisonDetail2ActionPerformed(evt);
            }
        });

        btndelete1.setBackground(new java.awt.Color(255, 222, 177));
        btndelete1.setBorder(null);
        btndelete1.setForeground(new java.awt.Color(248, 91, 50));
        btndelete1.setText("Annuler la livraison");
        btndelete1.setBorderColor(new java.awt.Color(255, 222, 177));
        btndelete1.setColor(new java.awt.Color(255, 222, 177));
        btndelete1.setColorClick(new java.awt.Color(255, 222, 177));
        btndelete1.setColorOver(new java.awt.Color(255, 222, 177));
        btndelete1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btndelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelete1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(nomGroupe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username)
                    .addComponent(matricule, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(password))
                .addGap(49, 49, 49))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorderRound1Layout.createSequentialGroup()
                    .addGap(335, 335, 335)
                    .addComponent(saveLivraisonDetail2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(33, 33, 33)
                    .addComponent(btndelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(335, Short.MAX_VALUE)))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matricule, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(nomGroupe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(1236, 1236, 1236))))
            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBorderRound1Layout.createSequentialGroup()
                    .addGap(642, 642, 642)
                    .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btndelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saveLivraisonDetail2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(643, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        listPanel.setVisible(false);
        setEmptyForm();
        formPanel.setVisible(true);
        btnSaveState="insert";
    }//GEN-LAST:event_btnnewuserActionPerformed
//        listPanel.setVisible(true);
//        formPanel.setVisible(false);
//        refreshTable();
    
//            listPanel.setVisible(true);
//        formPanel.setVisible(false);
//        refreshTable();
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
       listPanel.setVisible(false);
        formPanel.setVisible(true);
        btnSaveState="update"; 
        DefaultTableModel model=(DefaultTableModel) table.getModel();
        int i = table.getSelectedRow();
        setIdSelected(model.getValueAt(i,0).toString());
        
        username.setText(model.getValueAt(i,1).toString());
        nomGroupe.setSelectedItem(model.getValueAt(i, 3).toString());
        matricule.setSelectedItem(model.getValueAt(i, 2).toString());
//        nomGroupe.setSelectedItem(model.getValueAt(i, 3).toString());

  if(   "update".equals(btnSaveState)){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT password,idGroupe FROM utilisateur where idUtilisateur=?");
                stm.setString(1, getIdSelected());
//                JOptionPane.showMessageDialog(null, getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE); 
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    password.setText(resultSet.getString("password"));
                }
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
       
    }//GEN-LAST:event_tableMouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
if(validatedForm()){
              try {
             
            DateTimeFormatter dtFormat;
            dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime datetimenow = LocalDateTime.now();
                connectiondb();
                String idGroupe = null;
                
                stm = conn.prepareStatement("SELECT idGroupe FROM groupe where nomGroupe=?");
                stm.setString(1, (String) nomGroupe.getSelectedItem());
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    idGroupe =  resultSet.getString("idGroupe");
                }
                switch(btnSaveState){
                    case "insert":
                        stm = conn.prepareStatement("insert into utilisateur(idUtilisateur,nomUtilisateur,password, idGroupe,idProfile,dateCreation,status) values (null,?,?,?,?,?,0)");
                       stm.setString(1, username.getText());
                        stm.setString(2, password.getText());
                        stm.setString(3, (String) matricule.getSelectedItem());
                        stm.setString(4, idGroupe);
                        stm.setString(5, dtFormat.format(datetimenow));
                        stm.executeUpdate();
                        t = new Toast("Ajout d'un utilisateur effectué!", this.getLocationOnScreen().x+this.getWidth()-320, this.getLocationOnScreen().y+7);
                        t.showtoast();
                       
                            break;
                    case "update":
                        if(JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder vos modification?","Mise à jour",JOptionPane.YES_NO_OPTION) == 0){
                        stm = conn.prepareStatement("update utilisateur set nomUtilisateur=?, password=?,idProfile=?, idGroupe=? where idUtilisateur=?");
                       stm.setString(1, username.getText());
                        stm.setString(2, password.getText());
                        stm.setString(3, (String) matricule.getSelectedItem());
                        stm.setString(4, idGroupe);
                        stm.setString(5, getIdSelected());  
                        stm.executeUpdate();
                        t = new Toast("Modification enregistrée", this.getLocationOnScreen().x+this.getWidth()-320, this.getLocationOnScreen().y+7);
                        t.showtoast();
                        }
//                        JOptionPane.showMessageDialog(null, "modification enregistrée"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                            break;
                }
                
//                stm.executeUpdate();
//                JOptionPane.showMessageDialog(null, "insertION R2USSIE? IDSELECTED"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
             }else{
              JOptionPane.showMessageDialog(null, "Veuillez vérifier que tous les champs sont correctement remplis!"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
             }
        
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
           connectiondb(); 
           stm = conn.prepareStatement("delete from utilisateur where idUtilisateur=?"); 
            stm.setString(1, idSelected);
            stm.executeUpdate();
            stm.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Suppression réussie"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);}
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_print2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_print2ActionPerformed

    private void saveLivraisonDetail2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLivraisonDetail2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveLivraisonDetail2ActionPerformed

    private void btndelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelete1ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce client?", "Suppression", JOptionPane.YES_NO_OPTION);
        //        if (confirm == JOptionPane.YES_OPTION) {
            //            this.dispose();
            //        }
    }//GEN-LAST:event_btndelete1ActionPerformed
 
    private void refreshTable(){
        String column[]= {"ID","Nom utilisateur","Rôle","Date de création","Status"};
        Object[] data = new Object[5];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery(query);
        
           while(resultSet.next()){
                data[0]=resultSet.getString("idUtilisateur");
                data[1]=resultSet.getString("nomUtilisateur");
//                data[2]=resultSet.getString("idProfile");
                data[2]=resultSet.getString("nomGroupe");
                data[3]=resultSet.getString("dateCreation");
                data[4]=resultSet.getString("status");
                model.addRow(data);
       };
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setEmptyForm(){
        matricule.setSelectedItem(null);
        username.setText("");
        nomGroupe.setSelectedItem("");
        password.setText("");
    }
        private boolean validatedForm(){
     if( matricule.getSelectedItem() != null && !username.getText().isEmpty() && nomGroupe.getSelectedItem() != null && !password.getText().isEmpty() ){
        return true;
        }else{
        return false;
     }
    }
    private void setInputSelect(){
            try {
                connectiondb();
                stm = conn.prepareStatement("SELECT idGroupe,nomGroupe FROM groupe");
                resultSet = stm.executeQuery();
               nomGroupe.addItem("");
                while (resultSet.next()) {
                    nomGroupe.addItem(resultSet.getString("nomGroupe"));
                }
                stm = conn.prepareStatement("SELECT idProfile FROM profile");
                resultSet = stm.executeQuery();
                matricule.addItem("");
                while (resultSet.next()) {
                    matricule.addItem(resultSet.getString("idProfile"));
                }
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adresseClient;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_print2;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btndelete;
    private javaapp.component.ButtonRadius btndelete1;
    private javaapp.component.ButtonRadius btnnewuser;
    private javaapp.component.ButtonRadius btnsave;
    private javax.swing.JLabel doit;
    private javax.swing.JLabel doit1;
    private javax.swing.JLabel emailClient;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javaapp.component.PanelBorderRound listPanel;
    private javaapp.component.Table listeArticleCommande;
    private javax.swing.JComboBox<String> matricule;
    private javax.swing.JLabel montantCommande;
    private javax.swing.JLabel montantCommande1;
    private javax.swing.JLabel montantTotal;
    private javax.swing.JLabel montantTotal1;
    private javax.swing.JLabel nomClient;
    private javax.swing.JComboBox<String> nomGroupe;
    private javax.swing.JLabel numClient;
    private javax.swing.JLabel numCommande;
    private javax.swing.JLabel num_facture;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JTextField password;
    private javax.swing.JLabel refCommande;
    private javaapp.component.RoundedLabel roundedLabel1;
    private javaapp.component.ButtonRadius saveLivraisonDetail2;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JLabel telClient;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
}
