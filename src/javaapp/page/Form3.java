
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
import javaapp.component.ComboSelect;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Form3 extends javax.swing.JPanel {
    public String title="Groupe";
    public  Connection conn = null;
    public  String url = "jdbc:sqlite:caisse.db";
    public String  query ="select * from profile";
    private int rowSelected;
    private String idSelected = null;
    public PreparedStatement stm ;
    public ResultSet resultSet;
    public String btnSaveState;
    
    public Form3() {
        initComponents();
        refreshTable();
        formPanel.setVisible(false);
        scrollTable.getViewport().setBackground(Color.white); 
        
    }
    
        private  void connectiondb(){
        try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection("jdbc:sqlite:caisse.db");
        } catch (SQLException ex) {
            Logger.getLogger(Form4.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Form4.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        jLabel6 = new javax.swing.JLabel();
        btnnewuser = new javaapp.component.ButtonRadius();
        btn_search = new javaapp.component.ButtonRadius();
        jTextField2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        listPanel1 = new javax.swing.JPanel();
        formPanel = new javaapp.component.PanelBorderRound();
        adresse = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        telephone = new javax.swing.JTextField();
        fonction = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        prenom = new javax.swing.JTextField();
        btn_save = new javaapp.component.ButtonRadius();
        btn_delete = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nomGroupe = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        roundedTextField1 = new javaapp.component.RoundedTextField();
        jComboBox1 = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1101, 1362));

        listPanel.setBackground(new java.awt.Color(255, 255, 255));

        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollTable.setForeground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom complet", "Fonction", "Contact", "Adresse"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        scrollTable.setViewportView(table);

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel6.setText("Liste des employés");

        btnnewuser.setBackground(new java.awt.Color(255, 105, 82));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Nouveau");
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

        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Nombre total d'employé: ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("7");

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(145, 145, 145))
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        formPanel.setBackground(new java.awt.Color(255, 255, 255));
        formPanel.setLayout(null);
        formPanel.add(adresse);
        adresse.setBounds(66, 424, 859, 40);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(73, 73, 73));
        jLabel5.setText("Adresse");
        formPanel.add(jLabel5);
        jLabel5.setBounds(66, 395, 97, 16);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(73, 73, 73));
        jLabel4.setText("Tel");
        formPanel.add(jLabel4);
        jLabel4.setBounds(66, 313, 97, 16);
        formPanel.add(telephone);
        telephone.setBounds(151, 338, 214, 42);
        formPanel.add(fonction);
        fonction.setBounds(66, 267, 859, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(73, 73, 73));
        jLabel3.setText("Fonction");
        formPanel.add(jLabel3);
        jLabel3.setBounds(66, 244, 97, 16);

        jLabel1.setBackground(new java.awt.Color(210, 210, 210));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(73, 73, 73));
        jLabel1.setText("Nom");
        formPanel.add(jLabel1);
        jLabel1.setBounds(66, 153, 82, 26);
        formPanel.add(nom);
        nom.setBounds(66, 186, 301, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(73, 73, 73));
        jLabel2.setText("Prénom");
        formPanel.add(jLabel2);
        jLabel2.setBounds(411, 151, 58, 28);
        formPanel.add(prenom);
        prenom.setBounds(411, 186, 514, 40);

        btn_save.setBackground(new java.awt.Color(102, 102, 255));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_save.setColor(new java.awt.Color(102, 102, 255));
        btn_save.setColorClick(new java.awt.Color(102, 102, 255));
        btn_save.setColorOver(new java.awt.Color(98, 88, 232));
        btn_save.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });
        formPanel.add(btn_save);
        btn_save.setBounds(66, 506, 128, 42);

        btn_delete.setBackground(new java.awt.Color(255, 58, 68));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 58, 68));
        btn_delete.setColor(new java.awt.Color(255, 58, 68));
        btn_delete.setColorClick(new java.awt.Color(255, 58, 68));
        btn_delete.setColorOver(new java.awt.Color(237, 11, 31));
        btn_delete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        formPanel.add(btn_delete);
        btn_delete.setBounds(206, 506, 114, 42);

        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        formPanel.add(btn_cancel);
        btn_cancel.setBounds(332, 506, 111, 42);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("+ 261");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        formPanel.add(jPanel1);
        jPanel1.setBounds(66, 338, 67, 42);

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
        formPanel.add(btn_back);
        btn_back.setBounds(0, 0, 96, 42);

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Enregistrer un nouvel employé");
        formPanel.add(jLabel8);
        jLabel8.setBounds(60, 102, 328, 31);

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Assigner un compte utilisateur");
        formPanel.add(jLabel9);
        jLabel9.setBounds(66, 599, 328, 27);

        formPanel.add(nomGroupe);
        nomGroupe.setBounds(66, 809, 859, 40);

        jLabel10.setBackground(new java.awt.Color(210, 210, 210));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(73, 73, 73));
        jLabel10.setText("Nom du groupe");
        formPanel.add(jLabel10);
        jLabel10.setBounds(66, 776, 111, 26);

        jPanel2.setBackground(new java.awt.Color(193, 228, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 859, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        formPanel.add(jPanel2);
        jPanel2.setBounds(66, 644, 859, 100);

        roundedTextField1.setText("roundedTextField1");
        formPanel.add(roundedTextField1);
        roundedTextField1.setBounds(494, 54, 215, 40);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });
        formPanel.add(jComboBox1);
        jComboBox1.setBounds(324, 20, 64, 22);

        javax.swing.GroupLayout listPanel1Layout = new javax.swing.GroupLayout(listPanel1);
        listPanel1.setLayout(listPanel1Layout);
        listPanel1Layout.setHorizontalGroup(
            listPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanel1Layout.createSequentialGroup()
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        listPanel1Layout.setVerticalGroup(
            listPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanel1Layout.createSequentialGroup()
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(listPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        listPanel.setVisible(false);
        setEmptyForm();
        formPanel.setVisible(true);
        btnSaveState = "insert";
    }//GEN-LAST:event_btnnewuserActionPerformed

    private void setEmptyForm(){
        nom.setText("");
        prenom.setText("");
        telephone.setText("");
        fonction.setText("");
        adresse.setText("");
    }
        
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

        try {
             connectiondb();
           switch(btnSaveState){
                    case "insert":
                        stm = conn.prepareStatement("insert into utilisateur(idUtilisateur,nomUtilisateur,password, idGroupe,dateCreation,actif) values (null,?,?,?,?,'non')");
                        stm.setString(1, nom.getText());
                        stm.setString(2, nom.getText());
                        stm.setString(3, nom.getText());
                        stm.setString(4, nom.getText());
                            break;
                    case "update":
                        stm = conn.prepareStatement("update utilisateur set nomUtilisateur=?, password=?,  idGroupe=? where idUtilisateur=?");
                        stm.setString(1, nom.getText());
                        stm.setString(2, nom.getText());
                        stm.setString(3, nom.getText());
                        stm.setString(4, nom.getText());    
                            break;
                }
                
            stm.executeUpdate();
            conn.close();
            JOptionPane.showMessageDialog(null, "insertion réussie!","Sucess",JOptionPane.INFORMATION_MESSAGE);
            refreshTable();

        } catch (SQLException ex) {
            Logger.getLogger(Form2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable();
    }//GEN-LAST:event_btn_backActionPerformed

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1PropertyChange

    private void refreshTable(){
        String column[]= {"ID","Nom complet","Fonction","Contact","Adresse"};
        Object[] data = new Object[5];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery(query);
        
           while(resultSet.next()){
                data[0]=resultSet.getString("idProfile");
                data[1]=resultSet.getString("nom");
                data[2]=resultSet.getString("fonction");
                data[3]=resultSet.getString("tel");
                data[4]=resultSet.getString("adresse");
                model.addRow(data);
            }
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(Form4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_save;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btnnewuser;
    private javax.swing.JTextField fonction;
    private javaapp.component.PanelBorderRound formPanel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField2;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JPanel listPanel1;
    private javax.swing.JTextField nom;
    private javax.swing.JComboBox<String> nomGroupe;
    private javax.swing.JTextField prenom;
    private javaapp.component.RoundedTextField roundedTextField1;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JTextField telephone;
    // End of variables declaration//GEN-END:variables
}
