
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

public class Form7 extends javax.swing.JPanel {

    /**
     * @return the idSelected
     */
    public String getIdSelected() {
        return idSelected;
    }

    /**
     * @param idSelected the idSelected to set
     */
    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }
    public boolean newUser;
      public  Connection conn = null;
     public  String url = "jdbc:sqlite:caisse.db";
     public String  query ="select * from profile";
     private int rowSelected;
     private String idSelected = null;
     public PreparedStatement stm ;
     public ResultSet resultSet;
     public String btnSaveState;
     public String title="Pesonnel";
//       public String query ="select * from utilisateur";
     
    public Form7() {
        initComponents();
        formPanel.setVisible(false);
        createuserpanel.setVisible(false);
        connectiondb(); 
        query ="select * from profile";
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
            Logger.getLogger(Form7.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Form7.class.getName()).log(Level.SEVERE, null, ex);
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
        nombre_ligne = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        prenom = new javax.swing.JTextField();
        nom = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fonction = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tel = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        adresse = new javax.swing.JTextField();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        createuserpanel = new javax.swing.JLayeredPane();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nomGroupe1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        password1 = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        password2 = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        date_naiss = new javax.swing.JTextField();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jLabel3 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        btndelete = new javaapp.component.ButtonRadius();

        listPanel.setBackground(new java.awt.Color(255, 255, 255));

        scrollTable.setBackground(new java.awt.Color(255, 255, 255));
        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollTable.setForeground(new java.awt.Color(255, 255, 255));

        table.setForeground(new java.awt.Color(51, 51, 51));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricule", "Nom", "Prénom", "Date de naissance", "Fonction", "Contact", "Adresse"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Liste du personnel");

        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

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

        jTextField1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(240, 236, 243));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(51, 51, 51));
        nombre_ligne.setText("Nombre : 6");

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                        .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
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
                .addGap(33, 33, 33)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Informations sur un membre du personnel");

        jLabel2.setBackground(new java.awt.Color(210, 210, 210));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Prénom");

        prenom.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        prenom.setForeground(new java.awt.Color(51, 51, 51));
        prenom.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        nom.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nom.setForeground(new java.awt.Color(51, 51, 51));
        nom.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Fonction");

        fonction.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        fonction.setForeground(new java.awt.Color(51, 51, 51));
        fonction.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Tel");

        tel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        tel.setForeground(new java.awt.Color(51, 51, 51));
        tel.setDisabledTextColor(new java.awt.Color(102, 102, 102));

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

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Adresse");

        adresse.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        adresse.setForeground(new java.awt.Color(51, 51, 51));
        adresse.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        btn_save.setBackground(new java.awt.Color(102, 102, 255));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_save.setColor(new java.awt.Color(102, 102, 255));
        btn_save.setColorClick(new java.awt.Color(102, 102, 255));
        btn_save.setColorOver(new java.awt.Color(98, 88, 232));
        btn_save.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        jLabel11.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("Assigner un compte utilisateur");

        jPanel2.setBackground(new java.awt.Color(204, 228, 255));

        jPanel3.setBackground(new java.awt.Color(8, 4, 54));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 51));
        jLabel6.setText("Pour accéder à l'application, un membre du personnel a besoin d'un compte utilisateur.");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 51));
        jLabel14.setText("Assignez-le un compte utilisateur déjà existante en sélectionnant le nom d'utilisateur ou créer un nouveau");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Nom utilisateur");

        username.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        username.setForeground(new java.awt.Color(51, 51, 51));
        username.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        username.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Rôle");

        nomGroupe1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Mot de passe");

        password1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        password1.setForeground(new java.awt.Color(51, 51, 51));
        password1.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Mot de passe de confirmation");

        password2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        password2.setForeground(new java.awt.Color(51, 51, 51));
        password2.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        createuserpanel.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(username, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(nomGroupe1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(password1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(password2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout createuserpanelLayout = new javax.swing.GroupLayout(createuserpanel);
        createuserpanel.setLayout(createuserpanelLayout);
        createuserpanelLayout.setHorizontalGroup(
            createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createuserpanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(password2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(password1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(nomGroupe1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(username, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        createuserpanelLayout.setVerticalGroup(
            createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createuserpanelLayout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomGroupe1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Date de naissance   (YYYY-mm-jj)");

        date_naiss.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        date_naiss.setForeground(new java.awt.Color(51, 51, 51));
        date_naiss.setToolTipText("ghgggg");
        date_naiss.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adresse)
                            .addComponent(fonction)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(106, 106, 106))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(createuserpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_naiss, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(110, 110, 110))))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date_naiss, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fonction, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tel)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(adresse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(createuserpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel3.setText("Retirer cet employé");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(102, 102, 102));
        jTextArea1.setRows(5);
        jTextArea1.setText("Cette action inclue une suppression définitive et irreversible de toutes vos données conçernant ce \ncompte utilisateur");

        btndelete.setBackground(new java.awt.Color(255, 11, 31));
        btndelete.setBorder(null);
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("Supprimer");
        btndelete.setBorderColor(new java.awt.Color(255, 0, 0));
        btndelete.setColor(new java.awt.Color(255, 11, 31));
        btndelete.setColorClick(new java.awt.Color(255, 11, 31));
        btndelete.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorderRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorderRound2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        DefaultTableModel model=(DefaultTableModel) table.getModel(); 
        setRowSelected(table.getSelectedRow());
        setIdSelected(model.getValueAt(rowSelected,0).toString());
        listPanel.setVisible(false);
        formPanel.setVisible(true);
        btnSaveState="update";
//        nom.setText(model.getValueAt(rowSelected,1).toString());
        nom.setText(model.getValueAt(rowSelected, 2).toString());
        prenom.setText(model.getValueAt(rowSelected, 3).toString());
        fonction.setText(model.getValueAt(rowSelected, 4).toString());
        tel.setText(model.getValueAt(rowSelected, 5).toString());
        adresse.setText(model.getValueAt(rowSelected, 6).toString());
       
    }//GEN-LAST:event_tableMouseClicked

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
            stm = conn.prepareStatement("delete from utilisateur where idUtilisateur=?"); 
            stm.setString(1, getIdSelected());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Form7.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable();
    }//GEN-LAST:event_btn_backActionPerformed

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
 
    private void refreshTable(){
        String column[]= {"Matricule","Nom","Prénom","Fonction","Contact","Adresse"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        try{
            connectiondb();
            Statement statement =(Statement)conn.createStatement();
            resultSet = statement.executeQuery(query);
        
           while(resultSet.next()){
                data[0]=resultSet.getInt("idProfile");
                data[1]=resultSet.getString("nom");
                data[2]=resultSet.getString("prenom");
                data[3]=resultSet.getString("fonction");
                data[4]=resultSet.getString("tel");
                data[5]=resultSet.getString("adresse");
                model.addRow(data);
            }
       table.setModel(model);
        conn.close();

    } catch (SQLException ex) {
            Logger.getLogger(Form7.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   private void setEmptyForm(){
        nom.setText("");
        prenom.setText("");
        tel.setText("");
        fonction.setText("");
        adresse.setText("");
    }
        private void checkEmptyForm(){
//     if((username.getText() == "") || (nomGroupe.getSelectedItem() == "") || (password1.getPassword() == "") || (password2.getPassword() == "")){
//        
//        }
    }
    private void setInputSelect(){
//            try {
//                connectiondb();
//                PreparedStatement preparedStatement = conn.prepareStatement("SELECT idGroupe,nomGroupe FROM groupe");
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()) {
//                    nomGroupe.addItem(resultSet.getString("nomGroupe"));
//                }
//                conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_save;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btndelete;
    private javaapp.component.ButtonRadius btnnewuser;
    private javax.swing.JLayeredPane createuserpanel;
    private javax.swing.JTextField date_naiss;
    private javax.swing.JTextField fonction;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JTextField nom;
    private javax.swing.JComboBox<String> nomGroupe1;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JPasswordField password1;
    private javax.swing.JPasswordField password2;
    private javax.swing.JTextField prenom;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JTextField tel;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
}
