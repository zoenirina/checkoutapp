
package javaapp.page;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
     public String   query ="select utilisateur.idUtilisateur as idUtilisateur,utilisateur.nomUtilisateur as nomUtilisateur,utilisateur.idProfile as idProfile,utilisateur.dateCreation as dateCreation,utilisateur.actif as actif,groupe.nomGroupe as nomGroupe from utilisateur,groupe where utilisateur.idGroupe=groupe.idGroupe";
       
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
        query ="select utilisateur.idUtilisateur as idUtilisateur,utilisateur.idProfile as idProfile,utilisateur.nomUtilisateur as nomUtilisateur,utilisateur.dateCreation as dateCreation,utilisateur.actif as actif,groupe.nomGroupe as nomGroupe from utilisateur,groupe where utilisateur.idGroupe=groupe.idGroupe";
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
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jLabel3 = new javax.swing.JLabel();
        btndelete = new javaapp.component.ButtonRadius();
        jTextArea1 = new javax.swing.JTextArea();

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1075, 710));

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
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Liste des utilisateurs");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        btnnewuser.setBackground(new java.awt.Color(255, 105, 82));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Ajouter");
        btnnewuser.setBorderColor(new java.awt.Color(255, 105, 82));
        btnnewuser.setColor(new java.awt.Color(255, 105, 82));
        btnnewuser.setColorClick(new java.awt.Color(255, 105, 82));
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

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(listPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 566, Short.MAX_VALUE)
                            .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator1)
                        .addComponent(scrollTable))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Informations utilisateurs");

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

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(matricule, 0, 683, Short.MAX_VALUE)
                    .addComponent(username)
                    .addComponent(nomGroupe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
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
                .addComponent(nomGroupe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Supprimer ce compte");

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

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(102, 102, 102));
        jTextArea1.setRows(5);
        jTextArea1.setText("Cette action inclue une suppression définitive et irreversible de toutes vos données conçernant ce \ncompte utilisateur");

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(266, Short.MAX_VALUE))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(listPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        btnSaveState="update"; DefaultTableModel model=(DefaultTableModel) table.getModel(); 
        
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
                        stm = conn.prepareStatement("insert into utilisateur(idUtilisateur,nomUtilisateur,password, idGroupe,idProfile,dateCreation,actif) values (null,?,?,?,?,?,'inactif')");
                       stm.setString(1, username.getText());
                        stm.setString(2, password.getText());
                        stm.setString(3, (String) matricule.getSelectedItem());
                        stm.setString(4, idGroupe);
                        stm.setString(5, dtFormat.format(datetimenow));
                        stm.executeUpdate();
                        JOptionPane.showMessageDialog(null, "insertION réussie"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                            break;
                    case "update":
                        stm = conn.prepareStatement("update utilisateur set nomUtilisateur=?, password=?,idProfile=?, idGroupe=? where idUtilisateur=?");
                       stm.setString(1, username.getText());
                        stm.setString(2, password.getText());
                        stm.setString(3, (String) matricule.getSelectedItem());
                        stm.setString(4, idGroupe);
                        stm.setString(5, getIdSelected());  
                        stm.executeUpdate();
                        JOptionPane.showMessageDialog(null, "modification enregistrée"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                            break;
                }
                
//                stm.executeUpdate();
//                JOptionPane.showMessageDialog(null, "insertION R2USSIE? IDSELECTED"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
                refreshTable();            
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
           connectiondb(); stm = conn.prepareStatement("delete from utilisateur where idUtilisateur=?"); 
           
            
            stm.setString(1, idSelected);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Suppression réussie"+getIdSelected(),"Sucess",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable();
    }//GEN-LAST:event_btn_backActionPerformed
 
    private void refreshTable(){
        String column[]= {"ID","Nom utilisateur","Matricule","Rôle","Date de création","Status"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery(query);
        
           while(resultSet.next()){
                data[0]=resultSet.getString("idUtilisateur");
                data[1]=resultSet.getString("nomUtilisateur");
                data[2]=resultSet.getString("idProfile");
                data[3]=resultSet.getString("nomGroupe");
                data[4]=resultSet.getString("dateCreation");
                data[5]=resultSet.getString("actif");
                model.addRow(data);
       };
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setEmptyForm(){
     username.setText("");
        nomGroupe.setSelectedItem("");
        password.setText("");
    }
        private void checkEmptyForm(){
     if(username.getText().isEmpty() || nomGroupe.getSelectedItem() != "" || password.getText().isEmpty() ){
        
        }
    }
    private void setInputSelect(){
            try {
                connectiondb();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT idGroupe,nomGroupe FROM groupe");
                ResultSet resultSet = preparedStatement.executeQuery();
               nomGroupe.addItem("");
                while (resultSet.next()) {
                    nomGroupe.addItem(resultSet.getString("nomGroupe"));
                }
                preparedStatement = conn.prepareStatement("SELECT idProfile FROM profile");
                resultSet = preparedStatement.executeQuery();
                matricule.addItem("");
                while (resultSet.next()) {
                    matricule.addItem(resultSet.getString("idProfile"));
                }
                
              
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btndelete;
    private javaapp.component.ButtonRadius btnnewuser;
    private javaapp.component.ButtonRadius btnsave;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JComboBox<String> matricule;
    private javax.swing.JComboBox<String> nomGroupe;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JTextField password;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
}
