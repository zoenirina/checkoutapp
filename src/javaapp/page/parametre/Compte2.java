
package javaapp.page.parametre;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.UserType;
import javaapp.bean.Profile;
import javaapp.bean.User;
import javaapp.component.Toast;
import javaapp.dao.GroupeDAO;
import javaapp.dao.ProfileDAO;
import javaapp.dao.UserDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Compte2 extends javax.swing.JPanel {

    public Toast t;
    public String idUserSelected;
    public static UserType usrType;
    private Profile profileSelected;
    private final ProfileDAO profileDAO;
    private final UserDAO userDAO;
    private final GroupeDAO groupeDAO;
    private User userSelected;
    
    public Compte2() {
        initComponents();
        profileDAO = DAOFactory.getProfileDAO();
        userDAO = DAOFactory.getUserDAO();
        groupeDAO = DAOFactory.getGroupeDAO();
        formPanel.setVisible(false);
        refreshTable(profileDAO.select());
        setInputSelect();
       
        scrollTable.getViewport().setBackground(Color.white);     
    }

    public Compte2(UserType usertype1) {
        initComponents();
        profileDAO = DAOFactory.getProfileDAO();
        userDAO = DAOFactory.getUserDAO();
        groupeDAO = DAOFactory.getGroupeDAO();
        usrType=usertype1;
        formPanel.setVisible(false);
        refreshTable(profileDAO.select());
        setInputSelect();
       
        scrollTable.getViewport().setBackground(Color.white);    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listPanel = new javaapp.component.PanelBorderRound();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_add = new javaapp.component.ButtonRadius();
        searchTerm = new javax.swing.JTextField();
        btn_search = new javaapp.component.ButtonRadius();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btn_refresh = new javaapp.component.ButtonRadius();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
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
        jLabel5 = new javax.swing.JLabel();
        adresse = new javax.swing.JTextField();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        createuserpanel = new javax.swing.JLayeredPane();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nomGroupe = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        password1 = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        password2 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        date_naiss = new com.toedter.calendar.JDateChooser();
        btn_delete = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(255, 255, 255));

        listPanel.setBackground(new java.awt.Color(255, 255, 255));
        listPanel.setPreferredSize(new java.awt.Dimension(1070, 783));

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
        table.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        scrollTable.setViewportView(table);

        jLabel1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 36, 36));
        jLabel1.setText("Liste des compte utilisateurs");

        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        btn_add.setBorder(null);
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setText("Mon compte");
        btn_add.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_add.setColor(new java.awt.Color(248, 91, 50));
        btn_add.setColorClick(new java.awt.Color(248, 91, 50));
        btn_add.setColorOver(new java.awt.Color(255, 51, 0));
        btn_add.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_add.setRadius(18);
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        searchTerm.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        btn_search.setBackground(new java.awt.Color(240, 236, 243));
        btn_search.setBorder(null);
        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/search.png"))); // NOI18N
        btn_search.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_search.setColor(new java.awt.Color(240, 236, 243));
        btn_search.setColorClick(new java.awt.Color(240, 236, 243));
        btn_search.setColorOver(new java.awt.Color(236, 232, 240));
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        btn_refresh.setBackground(new java.awt.Color(240, 236, 243));
        btn_refresh.setBorder(null);
        btn_refresh.setForeground(new java.awt.Color(51, 51, 51));
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/refresh.png"))); // NOI18N
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

        panelBorderRound2.setBackground(new java.awt.Color(255, 222, 177));

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(248, 91, 50));
        nombre_ligne.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nombre_ligne.setText("Documents (5)");

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nombre_ligne, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                        .addComponent(searchTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchTerm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addGap(303, 325, Short.MAX_VALUE)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(310, 310, 310))
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollTable)
                        .addGap(63, 63, 63))))
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
        jLabel8.setForeground(new java.awt.Color(36, 36, 36));
        jLabel8.setText("Profile");

        jLabel2.setBackground(new java.awt.Color(210, 210, 210));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Prénom");

        prenom.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        prenom.setForeground(new java.awt.Color(102, 102, 102));
        prenom.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        nom.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nom.setForeground(new java.awt.Color(102, 102, 102));
        nom.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Fonction");

        fonction.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        fonction.setForeground(new java.awt.Color(102, 102, 102));
        fonction.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Tel");

        tel.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        tel.setForeground(new java.awt.Color(102, 102, 102));
        tel.setDisabledTextColor(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Adresse");

        adresse.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        adresse.setForeground(new java.awt.Color(102, 102, 102));
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
        jLabel11.setForeground(new java.awt.Color(36, 36, 36));
        jLabel11.setText("Compte utilisateur");

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

        nomGroupe.setForeground(new java.awt.Color(51, 51, 51));

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

        jLabel3.setFont(new java.awt.Font("Verdana", 2, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Supprimer ce compte");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(255, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(255, 0, 0));

        createuserpanel.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(username, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(nomGroupe, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(password1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(password2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        createuserpanel.setLayer(jSeparator2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout createuserpanelLayout = new javax.swing.GroupLayout(createuserpanel);
        createuserpanel.setLayout(createuserpanelLayout);
        createuserpanelLayout.setHorizontalGroup(
            createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createuserpanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password2)
                    .addComponent(password1)
                    .addComponent(nomGroupe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createuserpanelLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 318, Short.MAX_VALUE)
                        .addGroup(createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        createuserpanelLayout.setVerticalGroup(
            createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createuserpanelLayout.createSequentialGroup()
                .addGroup(createuserpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomGroupe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel13.setText("Date de naissance ");

        date_naiss.setBackground(new java.awt.Color(255, 255, 255));

        btn_delete.setBackground(new java.awt.Color(255, 11, 31));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(255, 255, 255));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 0, 0));
        btn_delete.setColor(new java.awt.Color(255, 11, 31));
        btn_delete.setColorClick(new java.awt.Color(255, 11, 31));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adresse)
                            .addComponent(fonction)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(106, 106, 106))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(createuserpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tel, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_naiss, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(110, 110, 110))))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(adresse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(createuserpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 100, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(formPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        update_datas(); // affichage modifié

        DefaultTableModel model = (DefaultTableModel) table.getModel(); 
        int i = table.getSelectedRow();
        userSelected = userDAO.getUserActive();
        profileSelected = profileDAO.findByUtilisateur(userSelected.getIdUtilisateur());
        if (profileSelected != null) {
            nom.setText(profileSelected.getNom());
            prenom.setText(profileSelected.getPrenom());
         try {
             date_naiss.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(profileSelected.getDateNaiss()));
         } catch (ParseException ex) {
             Logger.getLogger(Compte1.class.getName()).log(Level.SEVERE, null, ex);
         }
            fonction.setText(profileSelected.getFonction());
            tel.setText(profileSelected.getTel());
            adresse.setText(profileSelected.getAdresse());
            
            if (userSelected != null) {
                username.setText(userSelected.getNomUtilisateur());
                nomGroupe.setSelectedItem(userSelected.getIdGroupe());
                password1.setText(userSelected.getPassword());
                password2.setText(userSelected.getPassword());
                idUserSelected = String.valueOf(userSelected.getIdUtilisateur());
            }
        }
    }//GEN-LAST:event_btn_addActionPerformed
  
    //modification de l'affichage pendant la modification des données
    public void update_datas(){
        listPanel.setVisible(false);
        formPanel.setVisible(true);
    }   
    
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer ce profil? \nVous allez perdre toutes les informations sur ce compte","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
        if (profileSelected != null && profileDAO.delete(profileSelected)) {
            refreshTable(profileDAO.select());
            setEmptyForm();
        }
        }      
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        listPanel.setVisible(true);
        formPanel.setVisible(false);
        refreshTable(profileDAO.select());
    }//GEN-LAST:event_btn_backActionPerformed

    private boolean validatedForm(){
    if(!nom.getText().isEmpty() && !prenom.getText().isEmpty() && date_naiss.getDate() != null && !prenom.getText().isEmpty() && !fonction.getText().isEmpty() && !tel.getText().isEmpty() && !adresse.getText().isEmpty()){
        return true; 
    }else{
    return false;
    }
//    }
       
    
    }
    
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
            if(validatedForm()){
            if (JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir sauvegarder les modifications?", "Mise à jour", JOptionPane.YES_NO_OPTION) == 0) {
//                        User existingUser = userDAO.find(Integer.parseInt(idUserSelected));
                        if (userSelected != null) {
                            userSelected.setNomUtilisateur(username.getText());
                            userSelected.setPassword(new String(password1.getPassword()));
                            userSelected.setIdGroupe( groupeDAO.find( nomGroupe.getSelectedItem().toString()).getIdGroupe());
                            userDAO.update(userSelected);
                        }

//                        Profile existingProfile = profileDAO.find(Integer.parseInt(idSelected));
                        if (profileSelected != null) {
                            profileSelected.setNom(nom.getText());
                            profileSelected.setPrenom(prenom.getText());
                            profileSelected.setDateNaiss(dateFormat.format(date_naiss.getDate()));
                            profileSelected.setFonction(fonction.getText());
                            profileSelected.setTel(tel.getText());
                            profileSelected.setAdresse(adresse.getText());
                            profileSelected.setIdUtilisateur(Integer.parseInt(idUserSelected));
                            profileDAO.update(profileSelected);
                        }

                        JOptionPane.showMessageDialog(null, "Modification enregistrée!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
            }
                    
//                 stm = conn.prepareStatement("insert into utilisateur(nomUtilisateur,password, idGroupe,dateCreation) values (?,?,(select idGroupe from groupe where nomGroupe=?),?)");
//                       stm.setString(1, username.getText());
//                        stm.setString(2, new String(password1.getPassword()));
//                        stm.setString(3, nomGroupe.getSelectedItem().toString());
//                        stm.setString(4, dtFormat.format(datetimenow));
//                        stm.executeUpdate();   
//                    
//                    
//                stm = conn.prepareStatement("update profile set idProfile=?, nom=?, prenom=?,  dateNaiss=?,fonction=?,tel=?,adresse=?,idUtilisateur=(select idUtilisateur from utilisateur where nomUtilisateur=?) where idUtilisateur=?");
//                stm.setInt(1, (int) idProfile.getValue());
//                stm.setString(2, nom.getText());
//                stm.setString(3, prenom.getText());
//                stm.setString(4, (String) dateFormat.format(date_naiss.getDate()));
//                stm.setString(5, fonction.getText());
//                stm.setString(6, tel.getText());
//                stm.setString(7, adresse.getText());
//                stm.setString(8, username.getText());
//                stm.setString(9, idUserSelected);
//                stm.executeUpdate();
                
            
            }else{
            JOptionPane.showMessageDialog(null, "Veuiller remplir correctement tous les champs","Erreur",JOptionPane.INFORMATION_MESSAGE);
            }
            refreshTable(profileDAO.select());
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        refreshTable(profileDAO.findByName( searchTerm.getText()));
    }//GEN-LAST:event_btn_searchActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
         if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer ce compte utilisateur?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
        if (profileSelected != null && profileDAO.delete(profileSelected)) {
            refreshTable(profileDAO.select());
            setEmptyForm();
        }
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        searchTerm.setText("");
        refreshTable(profileDAO.select());
    }//GEN-LAST:event_btn_refreshActionPerformed
 
    private void refreshTable( List<Profile> profiles ) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Effacer les lignes existantes
        for (Profile profile : profiles) {
            User user = userDAO.find(profile.getIdUtilisateur());
            if (user != null) {
                Object[] rowData = {
                    profile.getIdProfile(),
                    profile.getNom(),
                    profile.getPrenom(),
                    profile.getDateNaiss(),
                    profile.getFonction(),
                    profile.getTel(),
                    profile.getAdresse(),
                    user.getNomUtilisateur(),
                    user.getIdGroupe()
                };
                model.addRow(rowData);
            }
        }
}
    
   private void setEmptyForm(){
        nom.setText("");
        prenom.setText("");
        tel.setText("");
        fonction.setText("");
        adresse.setText("");
        date_naiss.setDate(null);
         username.setText("");
           nomGroupe.setSelectedItem(null);
           password1.setText("");
           password2.setText("");
    }

    private void setInputSelect(){
           for( javaapp.bean.Groupe groupe : groupeDAO.select()){
            nomGroupe.addItem(groupe.getNomGroupe());
        }
    }
    public void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
    //controle de l'affichage si

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresse;
    private javaapp.component.ButtonRadius btn_add;
    private javaapp.component.ButtonRadius btn_back;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_refresh;
    private javaapp.component.ButtonRadius btn_save;
    private javaapp.component.ButtonRadius btn_search;
    private javax.swing.JLayeredPane createuserpanel;
    private com.toedter.calendar.JDateChooser date_naiss;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField fonction;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javaapp.component.PanelBorderRound listPanel;
    private javax.swing.JTextField nom;
    private javax.swing.JComboBox<String> nomGroupe;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JPasswordField password1;
    private javax.swing.JPasswordField password2;
    private javax.swing.JTextField prenom;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JTextField searchTerm;
    private javaapp.component.Table table;
    private javax.swing.JTextField tel;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
