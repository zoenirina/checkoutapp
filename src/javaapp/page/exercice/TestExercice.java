
package javaapp.page.exercice;

import javaapp.page.parametre.Groupe;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javaapp.component.Toast;
import javaapp.dao.ExerciceDAO;
import javaapp.dao.UserDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JPanel;
/**
 *
 * @author ZOENIRINA
 */
public class TestExercice extends javax.swing.JPanel {
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
    private final ExerciceDAO exerciceDAO;
    private javaapp.bean.Exercice exerciceSelected;
    private final UserDAO userDAO;
     
    public TestExercice( JPanel panel) {
        initComponents();
        exerciceDAO = DAOFactory.getExerciceDAO();
        userDAO = DAOFactory.getUserDAO();
        pan= panel;
//        setOpaque(false);
        query="select * from exercice ORDER BY dateCreation DESC";
        refreshTable(exerciceDAO.select());
        scrollTable.getViewport().setBackground(Color.white);
        setInputSelect();
        getSoldeInitiale();
        btn_delete.setVisible(false);  
        jScrollPane1.getViewport().setBackground(Color.white);
    }
    
    private  void connectiondb(){
       
        try {
            Class.forName("org.sqlite.JDBC");
         try {
            conn = DriverManager.getConnection("jdbc:sqlite:caisse.db");
        } catch (SQLException ex) {
            Logger.getLogger(TestExercice.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestExercice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked") 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_save = new javaapp.component.ButtonRadius();
        description = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        moisDebut = new com.toedter.calendar.JMonthChooser();
        soldeInit = new javax.swing.JSpinner();
        anneeFin = new com.toedter.calendar.JYearChooser();
        jLabel6 = new javax.swing.JLabel();
        moisFin = new com.toedter.calendar.JMonthChooser();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        idExercice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        anneeDebut = new com.toedter.calendar.JYearChooser();
        jLabel4 = new javax.swing.JLabel();
        nomCreateur = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btn_delete = new javaapp.component.ButtonRadius();
        nombre_ligne = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(238, 238, 238));
        setPreferredSize(new java.awt.Dimension(1010, 700));

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelBorderRound2.setPreferredSize(new java.awt.Dimension(1065, 710));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelBorderRound1.setPreferredSize(new java.awt.Dimension(650, 1178));

        btn_cancel.setBackground(new java.awt.Color(240, 236, 243));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorClick(new java.awt.Color(240, 240, 240));
        btn_cancel.setColorOver(new java.awt.Color(240, 240, 240));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_save.setBackground(new java.awt.Color(248, 91, 50));
        btn_save.setBorder(null);
        btn_save.setForeground(new java.awt.Color(255, 255, 255));
        btn_save.setText("Enregistrer");
        btn_save.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_save.setColor(new java.awt.Color(248, 91, 50));
        btn_save.setColorClick(new java.awt.Color(248, 91, 50));
        btn_save.setColorOver(new java.awt.Color(255, 51, 0));
        btn_save.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        description.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        description.setForeground(new java.awt.Color(102, 102, 102));

        status.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        status.setForeground(new java.awt.Color(102, 102, 102));
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactif", "Actif" }));

        moisDebut.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        soldeInit.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        anneeFin.setBackground(new java.awt.Color(222, 222, 222));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Numéro d'exercice");

        moisFin.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

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
        table.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Description");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Status");

        jLabel10.setBackground(new java.awt.Color(255, 102, 0));
        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(36, 36, 36));
        jLabel10.setText("Enregistrer un nouvel exercice");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Fin");

        idExercice.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        idExercice.setForeground(new java.awt.Color(102, 102, 102));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Solde actuelle");

        anneeDebut.setBackground(new java.awt.Color(222, 222, 222));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Nom utilisateur");

        nomCreateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomCreateur.setForeground(new java.awt.Color(102, 102, 102));
        nomCreateur.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Début");

        btn_delete.setBackground(new java.awt.Color(255, 222, 177));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(255, 51, 0));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColor(new java.awt.Color(255, 222, 177));
        btn_delete.setColorClick(new java.awt.Color(255, 222, 177));
        btn_delete.setColorOver(new java.awt.Color(255, 222, 177));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        jLabel9.setBackground(new java.awt.Color(255, 102, 0));
        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(36, 36, 36));
        jLabel9.setText("Liste des exercices");

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(status, javax.swing.GroupLayout.Alignment.LEADING, 0, 718, Short.MAX_VALUE)
                                .addComponent(idExercice, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                    .addComponent(moisDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(anneeDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelBorderRound1Layout.createSequentialGroup()
                                    .addComponent(moisFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(26, 26, 26)
                                    .addComponent(anneeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(nomCreateur, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(description, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(soldeInit, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre_ligne)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nomCreateur, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idExercice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(soldeInit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(anneeDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moisDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(moisFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(anneeFin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12)))
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombre_ligne)
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(panelBorderRound1);

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        setEmptyForm();
        btn_delete.setVisible(false);
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
    try {
        javaapp.bean.Exercice exercice = ("insert".equals(btn_save_state))?new javaapp.bean.Exercice():exerciceSelected;
        exercice.setSoldeInitiale(Float.parseFloat(soldeInit.getValue().toString()));
        exercice.setDescription(description.getText());
        exercice.setMoisDebut(moisDebut.getMonth() + 1);
        exercice.setAnneeDebut(anneeDebut.getYear());
        exercice.setMoisFin(moisFin.getMonth() + 1);
        exercice.setAnneeFin(anneeFin.getYear());
        exercice.setStatus(status.getSelectedIndex() == 1);
                
        switch(btn_save_state) {
            case "insert":
                if(status.getSelectedIndex() == 1) {
                    // Met à jour le status de tous les exercices à 0
                    for(javaapp.bean.Exercice ex : exerciceDAO.select()) {
                        ex.setStatus(false);
                        exerciceDAO.update(ex);
                    }
                }
                exercice.setDateCreation(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
                if(exerciceDAO.create(exercice)) {
                    new Toast("Insertion réussie", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7).showtoast();
                }
                break;
            case "update":
                if(status.getSelectedIndex() == 1) {
                    for(javaapp.bean.Exercice ex : exerciceDAO.select()) {
                        ex.setStatus(false);
                        exerciceDAO.update(ex);
                    }
                }
                if(exerciceDAO.update(exercice)) {
                    new Toast("Modification enregistrée", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7).showtoast();
                }
                break;
        }
        conn.close();
        refreshTable(exerciceDAO.select());

    } catch (SQLException ex) {
        Logger.getLogger(Groupe.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
      btn_delete.setVisible(true);
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int i = table.getSelectedRow();
    btn_save_state = "update";
    String id = model.getValueAt(i, 0).toString();
        exerciceSelected = exerciceDAO.find(Integer.parseInt(id));
        if (exerciceSelected != null && exerciceSelected.getId() != 0) {
            idExercice.setText(String.valueOf(exerciceSelected.getId()));
            soldeInit.setValue(exerciceSelected.getSoldeInitiale());
            // Vous devez également définir nomCreateur correctement
            nomCreateur.setSelectedItem(exerciceSelected.getIdCreateur()); // À définir correctement
            moisDebut.setMonth(exerciceSelected.getMoisDebut() - 1);
            anneeDebut.setYear(exerciceSelected.getAnneeDebut());
            moisFin.setMonth(exerciceSelected.getMoisFin() - 1);
            anneeFin.setYear(exerciceSelected.getAnneeFin());
            description.setText(exerciceSelected.getDescription());
            status.setSelectedIndex(exerciceSelected.isStatus() ? 1 : 0);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        if (exerciceSelected != null && exerciceDAO.delete(exerciceSelected)) {
            JOptionPane.showMessageDialog(null, "Suppression réussie!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable(exerciceDAO.select());
        }
  
    }//GEN-LAST:event_btn_deleteActionPerformed

     
   public void refreshTable(List<javaapp.bean.Exercice> exercices) {
        String[] column = {"ID", "Solde initial", "Createur", "Date de création", "Début", "Fin", "Status"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        FormatUtils formater = new FormatUtils();

        try {
            connectiondb();
            for (javaapp.bean.Exercice exercice : exercices) {
                String[] data = {
                    String.valueOf(exercice.getId()),
                    formater.formatFloat(exercice.getSoldeInitiale()),
                    String.valueOf(exercice.getIdCreateur()), // À remplacer par le nom du créateur si nécessaire
                    exercice.getDateCreation(),
                    DateConverter.getMonth(exercice.getMoisDebut()) + " " + exercice.getAnneeDebut(),
                    DateConverter.getMonth(exercice.getMoisFin()) + " " + exercice.getAnneeFin(),
                    exercice.isStatus() ? "actif" : "inactif"
                };
                model.addRow(data);
            }
            table.setModel(model);
            conn.close();
            rowcount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void rowcount() {
    nombre_ligne.setText("Tous (" + table.getRowCount() + ")");
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
             List<javaapp.bean.User> users = userDAO.getUserOrderByStatus();
                for(javaapp.bean.User user : users) {
                    nomCreateur.addItem(user.getNomUtilisateur());
                }
    }
    private float getSoldeInitiale(){
          float soldeInitiale = exerciceDAO.getActiveExerciceSoldeInitiale();
        soldeInit.setValue(soldeInitiale);
    return soldeInitiale;
        }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser anneeDebut;
    private com.toedter.calendar.JYearChooser anneeFin;
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JTextField description;
    private javax.swing.JTextField idExercice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JMonthChooser moisDebut;
    private com.toedter.calendar.JMonthChooser moisFin;
    private javax.swing.JComboBox<String> nomCreateur;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javax.swing.JScrollPane scrollTable;
    private javax.swing.JSpinner soldeInit;
    private javax.swing.JComboBox<String> status;
    private javaapp.component.Table table;
    // End of variables declaration//GEN-END:variables

}
