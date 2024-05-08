
package javaapp.page;

import java.awt.Color;
import java.util.List;
import javaapp.factory.DAOFactory;
import javaapp.UserType;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javaapp.component.Toast;
import javaapp.dao.ClientDAO;
/**
 *
 * @author ZOENIRINA
 */
public class Client extends javax.swing.JPanel {

    public Toast t;  
    private int rowSelected;
    private int selectedClientId = 0;
    public String btnSaveState;
    private javaapp.bean.Client clientSelected;
     
    public Client() {
        initComponents();
        init();     
    }
    
    public Client(UserType usertype1) {
        initComponents();
        init(); 
    }
    
    private void init(){
        formPanel.setVisible(false);
        refreshTable( DAOFactory.getClientDAO().select() );
        scrollTable.getViewport().setBackground(Color.white); 
        
    }
    
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnnewuser = new javaapp.component.ButtonRadius();
        client_key_search = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        jLabel21 = new javax.swing.JLabel();
        btn_refresh = new javaapp.component.ButtonRadius();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnsave = new javaapp.component.ButtonRadius();
        btn_back = new javaapp.component.ButtonRadius();
        jLabel9 = new javax.swing.JLabel();
        prenom = new javax.swing.JTextField();
        nom = new javax.swing.JTextField();
        btn_cancel = new javaapp.component.ButtonRadius();
        btndelete = new javaapp.component.ButtonRadius();
        jLabel10 = new javax.swing.JLabel();
        nif = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        email1 = new javax.swing.JTextField();
        tel1 = new javax.swing.JTextField();
        tel2 = new javax.swing.JTextField();
        tel3 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        email2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        stat = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        adresse = new javax.swing.JTextField();

        setBackground(new java.awt.Color(238, 238, 238));

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1075, 800));

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 36, 36));
        jLabel1.setText("Liste des clients");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 102));

        btnnewuser.setBackground(new java.awt.Color(248, 91, 50));
        btnnewuser.setBorder(null);
        btnnewuser.setForeground(new java.awt.Color(255, 255, 255));
        btnnewuser.setText("Ajouter");
        btnnewuser.setBorderColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColor(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorClick(new java.awt.Color(248, 91, 50));
        btnnewuser.setColorOver(new java.awt.Color(255, 78, 33));
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
        btn_search.setColor(new java.awt.Color(240, 236, 243));
        btn_search.setColorClick(new java.awt.Color(255, 255, 255));
        btn_search.setColorOver(new java.awt.Color(240, 236, 243));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Nom ou prénom du client");

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
        btn_refresh.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_refresh.setColor(new java.awt.Color(240, 236, 243));
        btn_refresh.setColorClick(new java.awt.Color(255, 255, 255));
        btn_refresh.setColorOver(new java.awt.Color(240, 236, 243));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        scrollTable.setBorder(null);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(51, 51, 51));
        nombre_ligne.setText("Tous (0)");

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnewuser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(listPanelLayout.createSequentialGroup()
                                .addComponent(nombre_ligne)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addGroup(listPanelLayout.createSequentialGroup()
                                        .addComponent(client_key_search, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 99, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(client_key_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nombre_ligne)))
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 36, 36));
        jLabel4.setText("Enregistrer un nouveau produit");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Nom");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Téléphone 1");

        btnsave.setBackground(new java.awt.Color(248, 91, 50));
        btnsave.setBorder(null);
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Enregistrer");
        btnsave.setBorderColor(new java.awt.Color(248, 91, 50));
        btnsave.setColor(new java.awt.Color(248, 91, 50));
        btnsave.setColorClick(new java.awt.Color(248, 91, 50));
        btnsave.setColorOver(new java.awt.Color(248, 91, 50));
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
        jLabel9.setText("Prenom");

        prenom.setForeground(new java.awt.Color(102, 102, 102));
        prenom.setSelectionColor(new java.awt.Color(102, 102, 102));

        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        btndelete.setBackground(new java.awt.Color(255, 222, 177));
        btndelete.setBorder(null);
        btndelete.setForeground(new java.awt.Color(248, 91, 50));
        btndelete.setText("Supprimer");
        btndelete.setBorderColor(new java.awt.Color(255, 222, 177));
        btndelete.setColor(new java.awt.Color(255, 222, 177));
        btndelete.setColorClick(new java.awt.Color(255, 222, 177));
        btndelete.setColorOver(new java.awt.Color(255, 222, 177));
        btndelete.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("NIF");

        nif.setForeground(new java.awt.Color(102, 102, 102));
        nif.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Contact");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Information générale");

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Téléphone 2");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Téléphone 3");

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Email 1");

        email1.setForeground(new java.awt.Color(102, 102, 102));
        email1.setSelectionColor(new java.awt.Color(102, 102, 102));

        tel1.setForeground(new java.awt.Color(102, 102, 102));
        tel1.setSelectionColor(new java.awt.Color(102, 102, 102));

        tel2.setForeground(new java.awt.Color(102, 102, 102));
        tel2.setSelectionColor(new java.awt.Color(102, 102, 102));

        tel3.setForeground(new java.awt.Color(102, 102, 102));
        tel3.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Email 2");

        email2.setForeground(new java.awt.Color(102, 102, 102));
        email2.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("STAT");

        stat.setForeground(new java.awt.Color(102, 102, 102));
        stat.setSelectionColor(new java.awt.Color(102, 102, 102));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Adresse");

        adresse.setForeground(new java.awt.Color(102, 102, 102));
        adresse.setSelectionColor(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(45, 45, 45)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(49, 49, 49))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(stat)
                                        .addComponent(tel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel10))
                                            .addGap(53, 53, 53)
                                            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel9)))
                                        .addComponent(nif, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(email1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                            .addComponent(tel1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tel2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel17))
                                            .addGap(2, 2, 2))
                                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(email2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(adresse, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addContainerGap(107, Short.MAX_VALUE))))))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(nif, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(adresse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13))
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(2, 2, 2)
                                .addComponent(tel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(email1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(email2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(380, 380, 380))))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formPanelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewuserActionPerformed
        switchToPage(1);
        btnSaveState="insert";
    }//GEN-LAST:event_btnnewuserActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
       // Masquer le panneau de liste et afficher le panneau de formulaire
        switchToPage(1);
        btnSaveState = "update"; 
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowIndex = table.getSelectedRow();

        if (rowIndex != -1) {
            ClientDAO clientDAO = DAOFactory.getClientDAO();
            selectedClientId = Integer.parseInt(model.getValueAt(rowIndex, 0).toString());
            clientSelected  = clientDAO.find(selectedClientId);

            // Affichage des des informations sur le client selectionné
            nom.setText(clientSelected.getNom());
            prenom.setText(clientSelected.getPrenom());
            email1.setText(clientSelected.getEmail1());
            email2.setText(clientSelected.getEmail2());
            tel1.setText(clientSelected.getTel1());
            tel2.setText(clientSelected.getTel2());
            tel3.setText(clientSelected.getTel3());
            adresse.setText(clientSelected.getAdresse());
            nif.setText(clientSelected.getNIF());
            stat.setText(clientSelected.getStat());
        }
    
    }//GEN-LAST:event_tableMouseClicked

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        ClientDAO clientDAO = DAOFactory.getClientDAO();
        javaapp.bean.Client client = new javaapp.bean.Client();

        switch (btnSaveState) {
            case "insert":
                // Insertion de nouvelles données client
                client = new javaapp.bean.Client(0, nom.getText(), prenom.getText(), email1.getText(), email2.getText(), tel1.getText(), tel2.getText(), tel3.getText(), adresse.getText(), nif.getText(), stat.getText(), null) ;
                    clientDAO.create(client);//créer le nouveau client 
                    JOptionPane.showMessageDialog(null, "Client ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);  
                break;

            case "update":
                // Mise à jour des données client existantes
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder vos modifications ?", "Mise à jour", JOptionPane.YES_NO_OPTION) == 0) {
                client = new javaapp.bean.Client(clientSelected.getId(), nom.getText(), prenom.getText(), email1.getText(), email2.getText(), tel1.getText(), tel2.getText(), tel3.getText(), adresse.getText(), nif.getText(), stat.getText(), null) ;
                clientDAO.update(client);
                }
                break;
        }

        
        refreshTable( clientDAO.select() );  // Mettre à jour la table affichant les clients
    
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce client?", "Suppression", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ClientDAO clientDAO = DAOFactory.getClientDAO();
            javaapp.bean.Client client = new javaapp.bean.Client();
            client = clientDAO.find(selectedClientId);
            if(clientDAO.delete(client))refreshTable( clientDAO.select() );
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        switchToPage(0);
    }//GEN-LAST:event_btn_backActionPerformed

    private void switchToPage(int i){
        listPanel.setVisible(i==0);
        formPanel.setVisible(i!=0);
        setEmptyForm();
    }
    
    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
           refreshTable( DAOFactory.getClientDAO().filter(client_key_search.getText()) );
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        refreshTable( DAOFactory.getClientDAO().select() );  
        client_key_search.setText("");
    }//GEN-LAST:event_btn_refreshActionPerformed
  public void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
  
    private void refreshTable( List < javaapp.bean.Client > clients ){
        String column[]= {"ID", "Nom", "Prénom", "Email 1", "Téléphone 1", "Adresse"};
        Object[] data = new Object[6];
        DefaultTableModel model = new DefaultTableModel(null,column);
        for(javaapp.bean.Client client : clients){
            data[0]=client.getId();
            data[1]=client.getNom();
            data[2]=client.getPrenom();
            data[3]=client.getEmail1();
            data[4]=client.getTel1();
            data[5]=client.getAdresse();
            model.addRow(data);
        }
        table.setModel(model);
        rowcount();
    }
    
    private void setEmptyForm(){
    nom.setText("");
    prenom.setText("");
    nif.setText("");
    email1.setText(""); 
    email2.setText("");
    tel1.setText("");
    tel2.setText(""); 
    tel3.setText("");
    adresse.setText("");
    stat.setText("");

    btnSaveState = "insert";

    }
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius btndelete;
    private javaapp.component.ButtonRadius btnnewuser;
    private javaapp.component.ButtonRadius btnsave;
    private javax.swing.JTextField client_key_search;
    private javax.swing.JTextField email1;
    private javax.swing.JTextField email2;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JTextField nif;
    private javax.swing.JTextField nom;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JTextField prenom;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTextField stat;
    private javaapp.component.Table table;
    private javax.swing.JTextField tel1;
    private javax.swing.JTextField tel2;
    private javax.swing.JTextField tel3;
    // End of variables declaration//GEN-END:variables

    public int getRowSelected() {
        return rowSelected;
    }
    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }
}
