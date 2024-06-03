package javaapp.page.parametre;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javaapp.dao.DroitAccesDAO;
import javaapp.dao.GroupeDAO;
import javaapp.factory.DAOFactory;
import javaapp.swing.ScrollBar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ZOENIRINA
 */
public class Groupe extends javax.swing.JPanel {
    Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public Toast t;  
    public String title="Autres";
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private String idSelected;
    public String btn_save_state= "insert";
    private final GroupeDAO groupeDAO;
    private final DroitAccesDAO droitAccesDAO;
    
    public Groupe() {
        initComponents();
        groupeDAO = DAOFactory.getGroupeDAO();
        droitAccesDAO = DAOFactory.getDroitAccesDAO();
        
        btn_delete.setVisible(false);
        query="select groupe.*, droit_acces.niveau_droit from groupe,droit_acces where groupe.idDroit=droit_acces.id";
        refreshTable();
        btn_delete.setVisible(false);
        scrollTable.setVerticalScrollBar(new ScrollBar());
        scrollTable.getVerticalScrollBar().setBackground(Color.WHITE);
        scrollTable.getViewport().setBackground(Color.WHITE);
        typeDroit.setSelectedItem(null);
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

        jPanel1 = new javax.swing.JPanel();
        panelForm = new javaapp.component.PanelBorderRound();
        description = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nomGroupe = new javax.swing.JTextField();
        btn_delete = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_save = new javaapp.component.ButtonRadius();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        typeDroit = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        nbr_administrateur = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1004, 850));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelForm.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Description");

        jLabel1.setBackground(new java.awt.Color(210, 210, 210));
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Nom");

        btn_delete.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(248, 91, 50));
        btn_delete.setText("Supprimer");
        btn_delete.setBorderColor(new java.awt.Color(248, 91, 50));
        btn_delete.setColor(new java.awt.Color(255, 255, 255));
        btn_delete.setColorClick(new java.awt.Color(255, 255, 255));
        btn_delete.setColorOver(new java.awt.Color(255, 248, 246));
        btn_delete.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_cancel.setBackground(new java.awt.Color(240, 236, 243));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_cancel.setColor(new java.awt.Color(240, 236, 243));
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

        jLabel6.setBackground(new java.awt.Color(255, 102, 0));
        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(36, 36, 36));
        jLabel6.setText("Groupe utilisateur");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Droit d'accès");

        typeDroit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les droits", "Création et Lecture", "Lecture seulement" }));

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFormLayout.createSequentialGroup()
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(45, 45, 45)
                        .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(description)
                            .addComponent(nomGroupe)
                            .addComponent(typeDroit, 0, 667, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormLayout.createSequentialGroup()
                                .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomGroupe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeDroit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setBackground(new java.awt.Color(255, 102, 0));
        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(36, 36, 36));
        jLabel7.setText("Liste des groupes utilisateurs");

        scrollTable.setBackground(new java.awt.Color(255, 255, 255));
        scrollTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollTable.setForeground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nom", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        table.setSelectionBackground(new java.awt.Color(255, 204, 153));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollTable.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(51, 51, 51));
        nombre_ligne.setText("Tous (2)");

        nbr_administrateur.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nbr_administrateur.setForeground(new java.awt.Color(102, 102, 102));
        nbr_administrateur.setText("Administrateur (1)");

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound2Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound2Layout.createSequentialGroup()
                        .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nbr_administrateur))
                    .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nbr_administrateur, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorderRound2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if (validatedForm()) {
            javaapp.bean.Groupe groupe = new javaapp.bean.Groupe();
            groupe.setNomGroupe(nomGroupe.getText());
            groupe.setDescription(description.getText());
            groupe.setIdDroit( droitAccesDAO.selectByDroit(typeDroit.getSelectedItem().toString()).getId() ); // Assuming typeDroit contains the correct droit ID
            if (btn_save_state.equals("insert")) {
                groupeDAO.create(groupe);
                new Toast("Information enregistrée!", this.getLocationOnScreen().x + this.getWidth() - 320, this.getLocationOnScreen().y + 7).showtoast();
            } else if (btn_save_state.equals("update")) {
                if (JOptionPane.showConfirmDialog(null, "Voulez-vous sauvegarder les modifications?", "Confirmation de mise à jour", JOptionPane.YES_NO_OPTION) == 0) {
                    groupe.setIdGroupe(Integer.parseInt(idSelected));
                    groupeDAO.update(groupe);
                    new Toast("Modification enregistrée!", this.getLocationOnScreen().x + this.getWidth() - 320, this.getLocationOnScreen().y + 7).showtoast();
                }
            }
            refreshTable();
            setEmptyForm();
           } else {
               JOptionPane.showMessageDialog(null, "Veuillez vérifier que tous les champs sont correctement remplis", "Message", JOptionPane.INFORMATION_MESSAGE);
           }        
        
    }//GEN-LAST:event_btn_saveActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
      btn_delete.setVisible(true);
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int i = table.getSelectedRow();
    btn_save_state = "update";
    idSelected = model.getValueAt(i, 0).toString();
    nomGroupe.setText(model.getValueAt(i, 1).toString());
    description.setText(model.getValueAt(i, 2).toString());
    typeDroit.setSelectedItem(model.getValueAt(i, 3).toString());

    }//GEN-LAST:event_tableMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        connectiondb();
        try{
            if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer ce groupe?","Confirmation de suppression",JOptionPane.YES_NO_OPTION) == 0){
        stm= conn.prepareStatement("delete from groupe where idGroupe=?");
        stm.setString(1, idSelected);
        stm.executeUpdate();
        stm.close();
        conn.close();
        refreshTable();
            }
//        JOptionPane.showMessageDialog(null, "modification réussie!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
        }catch( SQLException e){
        e.printStackTrace(); ;}
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        setEmptyForm();
    }//GEN-LAST:event_btn_cancelActionPerformed
 
    private void setEmptyForm(){
    btn_delete.setVisible(false);
        nomGroupe.setText("");
        description.setText("");
        typeDroit.setSelectedItem(null);
        idSelected=null;
        btn_save_state="insert";
    }
    
   public void refreshTable() {
    String[] column = {"ID", "Nom", "Description", "Droit"};
    DefaultTableModel model = new DefaultTableModel(null, column);
    
    List <javaapp.bean.Groupe> groupes = groupeDAO.select();
    for (javaapp.bean.Groupe groupe : groupes) {
        Object[] data = {
            groupe.getIdGroupe(),
            groupe.getNomGroupe(),
            groupe.getDescription(),
            groupe.getIdDroit()
        };
        model.addRow(data);
    }
    table.setModel(model);
    nombre_ligne.setText("Tous (" + table.getRowCount() + ")");
}

    private boolean validatedForm(){
        return !nomGroupe.getText().isEmpty() && !description.getText().isEmpty() && typeDroit.getSelectedItem() != null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JTextField description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel nbr_administrateur;
    private javax.swing.JTextField nomGroupe;
    private javax.swing.JLabel nombre_ligne;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javaapp.component.PanelBorderRound panelForm;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JComboBox<String> typeDroit;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the rowSelected
     */
    public String getIdSelected() {
        return idSelected;
    }

    /**
     * @param rowSelected the rowSelected to set
     */
    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }
}
