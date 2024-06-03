/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.page.Caisse;

import javaapp.page.parametre.Groupe;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.component.Toast;
import javaapp.dao.TypeMouvementDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class TypeMouvement extends javax.swing.JPanel {
    public String btn_save_state= "insert";
    public JPanel pan;
    public Toast t;
    private TypeMouvementDAO typeMouvementDAO;
    private javaapp.bean.TypeMouvement typeMouvementSelected;
    
    public TypeMouvement(JPanel panel) {
        initComponents();
        typeMouvementDAO = DAOFactory.getTypeMouvementDAO();
        refreshTable();
        pan= panel;
        scrollTable.getViewport().setBackground(Color.white);
        btn_delete.setVisible(false);
    }
     public TypeMouvement() {
        initComponents();
        typeMouvementDAO = DAOFactory.getTypeMouvementDAO();
        refreshTable();
        scrollTable.getViewport().setBackground(Color.white);
        btn_delete.setVisible(false);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_save = new javaapp.component.ButtonRadius();
        btn_cancel = new javaapp.component.ButtonRadius();
        btn_delete = new javaapp.component.ButtonRadius();
        type = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        description = new javax.swing.JTextField();
        scrollTable = new javax.swing.JScrollPane();
        table = new javaapp.component.Table();
        nombre_ligne = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        code = new javax.swing.JSpinner();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(960, 664));

        jLabel10.setBackground(new java.awt.Color(255, 102, 0));
        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(36, 36, 36));
        jLabel10.setText("Nature du mouvement de caisse");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Code");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Type");

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

        btn_cancel.setBackground(new java.awt.Color(240, 236, 243));
        btn_cancel.setBorder(null);
        btn_cancel.setForeground(new java.awt.Color(51, 51, 51));
        btn_cancel.setText("Annuler");
        btn_cancel.setBorderColor(new java.awt.Color(240, 236, 243));
        btn_cancel.setColor(new java.awt.Color(240, 236, 243));
        btn_cancel.setColorClick(new java.awt.Color(240, 236, 243));
        btn_cancel.setColorOver(new java.awt.Color(245, 244, 244));
        btn_cancel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_delete.setBackground(new java.awt.Color(255, 222, 177));
        btn_delete.setBorder(null);
        btn_delete.setForeground(new java.awt.Color(248, 91, 50));
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

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Description");

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

        nombre_ligne.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nombre_ligne.setForeground(new java.awt.Color(102, 102, 102));
        nombre_ligne.setText("Tous (5)");

        jLabel9.setBackground(new java.awt.Color(255, 102, 0));
        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(36, 36, 36));
        jLabel9.setText("Liste des types de mouvement");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                                    .addComponent(type)
                                    .addComponent(code)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(48, 48, 48))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombre_ligne)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel10)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nombre_ligne)
                .addGap(18, 18, 18)
                .addComponent(scrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

    
        switch(btn_save_state) {
            case "insert":
                javaapp.bean.TypeMouvement nouveauTypeMouvement = new javaapp.bean.TypeMouvement();
                nouveauTypeMouvement.setCodeType((int)code.getValue() == 0 ? null : (int)code.getValue());
                nouveauTypeMouvement.setType(type.getText());
                nouveauTypeMouvement.setDescription(description.getText());
                typeMouvementDAO.create(nouveauTypeMouvement);
                setEmptyForm();
                t = new Toast("Enregistrement réussi!", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7);
                t.showtoast();
                break;

            case "update":
                typeMouvementSelected.setCodeType((int) code.getValue());
                typeMouvementSelected.setType(type.getText());
                typeMouvementSelected.setDescription(description.getText());
                typeMouvementDAO.update(typeMouvementSelected);
                setEmptyForm();
                t = new Toast("Modification enregistrée", pan.getLocationOnScreen().x + pan.getWidth() - 320, pan.getLocationOnScreen().y + 7);
                t.showtoast();
                break;
        }
        refreshTable();
    }//GEN-LAST:event_btn_saveActionPerformed

    public void refreshTable() {
        String[] column = {"Code", "Type", "Description"};
        String[] data = new String[3];
        DefaultTableModel model = new DefaultTableModel(null, column);    
        List<javaapp.bean.TypeMouvement> listeTypeMouvements = typeMouvementDAO.select();
        for (javaapp.bean.TypeMouvement tm : listeTypeMouvements) {
            data[0] = String.valueOf(tm.getCodeType());
            data[1] = tm.getType();
            data[2] = tm.getDescription();
            model.addRow(data);
        }
        table.setModel(model);
        rowcount(); // mettre à jour le nombre de lignes
    }

     private void rowcount(){
    nombre_ligne.setText("Tous ("+ table.getRowCount()+")");
    }
     
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed

        typeMouvementDAO.delete(typeMouvementSelected);
        JOptionPane.showMessageDialog(null, "Suppression réussie!", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshTable();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
         DefaultTableModel model=(DefaultTableModel) table.getModel(); 
       int i= table.getSelectedRow();
        btn_save_state="update";
        int id =Integer.parseInt(model.getValueAt(i,0).toString());
        typeMouvementSelected = typeMouvementDAO.find(id);
        code.setValue(typeMouvementSelected.getCodeType());
        type.setText(typeMouvementSelected.getType());
        description.setText(typeMouvementSelected.getDescription());
        btn_delete.setVisible(true);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
       setEmptyForm();
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void setEmptyForm(){
     code.setValue(0);
        type.setText("");
        description.setText("");
        btn_delete.setVisible(false);
        btn_save_state="insert";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_cancel;
    private javaapp.component.ButtonRadius btn_delete;
    private javaapp.component.ButtonRadius btn_save;
    private javax.swing.JSpinner code;
    private javax.swing.JTextField description;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel nombre_ligne;
    private javax.swing.JScrollPane scrollTable;
    private javaapp.component.Table table;
    private javax.swing.JTextField type;
    // End of variables declaration//GEN-END:variables
}
