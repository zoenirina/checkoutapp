package javaap.modal;

import javaapp.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.EntreeCaisse;
import javaapp.bean.Facture;
import javaapp.bean.Livraison;
import javaapp.bean.Paiement;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaapp.dao.EntreeCaisseDAO;
import javaapp.dao.FactureDAO;
import javaapp.dao.PaiementDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;

public class PaiementFacture extends javax.swing.JFrame {
    Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    String SELECT_WHERE_ID="SELECT ld.*, p.designation AS designation_produit, ld.idProduit, cd.quantite AS quantite_commandee, ld.dateLivraison, cl.id AS id_client, cl.nom AS nom_client, cl.prenom AS prenom_client, c.id as idCommande, c.refCommande FROM livraisonDetails ld INNER JOIN commandeDetails cd ON ld.idCommandeDetail = cd.id INNER JOIN produits p ON ld.idProduit = p.id INNER JOIN commandes c ON cd.idCommande = c.id INNER JOIN clients cl ON c.idClient = cl.id WHERE ld.id = ?";
    public PreparedStatement smt;
    public ResultSet result;
    UserType usertype;
    boolean trouve=false;
    String idUser;
    String usernameValidated;
    public int niveau;
    Livraison liv;
    int idClientSelected = 0;
    private int idFactureSelected = 0;
    float reste;
     
 
public PaiementFacture(int idFacture) {
        initComponents();
        idFactureSelected = idFacture;
        try {
            populateForm(idFacture);
        } catch (ParseException ex) {
            Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
        }
        resteAPayer.setEditable(false);
        montantAPayer.setEditable(false);
        try {
            //     paie.setDatePaiement(datePaiement.getDate());
            DateConverter.initializeDate(datePaiement, heurePaiement, minutePaiement, null);
        } catch (ParseException ex) {
            Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FormatUtils formater = new FormatUtils();
//        resteAPayer.setText(formater.formatFloat(fac.getMontant() - reste ));
        if(formater.parseFloat(resteAPayer.getText()) > 0) {
            editbtn.setVisible(true);
        }else{
            editbtn.setVisible(false);
        }
    }

    public PaiementFacture() {
        initComponents();
    }

    public void initEvent(ModalActionEvent event, int row) {
        editbtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.print("event déclenché");
//            int res = -1;
            PaiementDAO paieDAO = DAOFactory.getPaiementDAO();
            Paiement paie = new Paiement();
            
            paie.setMontantRecu(Float.parseFloat(montantVerse.getValue().toString()));
            paie.setIdFacture(idFactureSelected);
            paie.setIdClient(Integer.parseInt(idClient.getText()));
            java.util.Date date = datePaiement.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int heure =  Integer.parseInt(heurePaiement.getSelectedItem().toString());
            int minute = Integer.parseInt(minutePaiement.getSelectedItem().toString());
            calendar.set(Calendar.HOUR_OF_DAY, heure);
            calendar.set(Calendar.MINUTE, minute);

            // Formater la date selon le format "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(calendar.getTime());
            paie.setDatePaiement(formattedDate);
//            if(res == 0){
//                if(reste != 0)
                if(paieDAO.create(paie)){
                    
                    EntreeCaisseDAO ecDAO = DAOFactory.getEntreeCaisseDAO();
                    EntreeCaisse entree = new EntreeCaisse();
                    entree.setIdSourceReference(paieDAO.getGeneratedKey());
                    entree.setMontant(Float.parseFloat(montantVerse.getValue().toString()));
                    entree.setDateEntree(formattedDate);
                    entree.setDescription("Paiement de la facture n°"+idFactureSelected+" d'une somme de "+ montantVerse.getValue().toString());
                    ecDAO.create(entree);
                    
                    setVisible(false);
                    event.onEdit(row);
                    System.out.print("Paiement efferctué!");
                    dispose();
                }
//            }
        }
        });
    };
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nomClient = new javax.swing.JTextField();
        editbtn = new javaapp.component.ButtonRadius();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        refFacture = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        montantAPayer = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        datePaiement = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        montantVerse = new javax.swing.JSpinner();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        idClient = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        idFacture = new javax.swing.JLabel();
        editbtn1 = new javaapp.component.ButtonRadius();
        resteAPayer = new javax.swing.JTextField();
        heurePaiement = new javax.swing.JComboBox<>();
        minutePaiement = new javax.swing.JComboBox<>();

        setTitle("Payer une facture");
        setIconImages(null);

        jPanel1.setBackground(new java.awt.Color(241, 248, 240));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(43, 40, 54));
        jLabel2.setText("Client");

        nomClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        nomClient.setForeground(new java.awt.Color(43, 40, 54));
        nomClient.setSelectionColor(new java.awt.Color(153, 255, 255));

        editbtn.setBorder(null);
        editbtn.setForeground(new java.awt.Color(255, 255, 255));
        editbtn.setText("Payer");
        editbtn.setBorderColor(new java.awt.Color(102, 102, 255));
        editbtn.setColor(new java.awt.Color(102, 102, 255));
        editbtn.setColorClick(new java.awt.Color(102, 102, 255));
        editbtn.setColorOver(new java.awt.Color(132, 132, 232));
        editbtn.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbtnActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(43, 40, 54));
        jLabel18.setText("Facture");

        refFacture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refFacture.setForeground(new java.awt.Color(43, 40, 54));
        refFacture.setSelectionColor(new java.awt.Color(153, 255, 255));
        refFacture.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                refFactureComponentShown(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(43, 40, 54));
        jLabel14.setText("Montant à payer");

        montantAPayer.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        montantAPayer.setForeground(new java.awt.Color(43, 40, 54));
        montantAPayer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        montantAPayer.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(43, 40, 54));
        jLabel7.setText("Date de paiement");

        datePaiement.setForeground(new java.awt.Color(43, 40, 54));
        datePaiement.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(43, 40, 54));
        jLabel3.setText("Reste à payer");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(43, 40, 54));
        jLabel8.setText("Montant versé");

        montantVerse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 40, 54));
        jLabel12.setText("N°");

        idClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idClient.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idClient, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idClient, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(43, 40, 54));
        jLabel15.setText("N°");

        idFacture.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idFacture.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idFacture, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        editbtn1.setBorder(null);
        editbtn1.setForeground(new java.awt.Color(102, 102, 255));
        editbtn1.setText("Fermer");
        editbtn1.setBorderColor(new java.awt.Color(204, 204, 255));
        editbtn1.setColor(new java.awt.Color(204, 204, 255));
        editbtn1.setColorClick(new java.awt.Color(204, 204, 255));
        editbtn1.setColorOver(new java.awt.Color(221, 221, 255));
        editbtn1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        resteAPayer.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        resteAPayer.setForeground(new java.awt.Color(43, 40, 54));
        resteAPayer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        resteAPayer.setSelectionColor(new java.awt.Color(153, 255, 255));

        heurePaiement.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        heurePaiement.setForeground(new java.awt.Color(43, 40, 54));

        minutePaiement.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        minutePaiement.setForeground(new java.awt.Color(43, 40, 54));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(editbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refFacture)
                            .addGap(378, 378, 378))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addGap(276, 276, 276)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(montantAPayer, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(resteAPayer, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(datePaiement, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(montantVerse))
                        .addGap(18, 18, 18)
                        .addComponent(heurePaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(minutePaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nomClient)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(refFacture, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(montantAPayer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(resteAPayer, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(45, 45, 45)))
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(montantVerse, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(datePaiement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minutePaiement, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heurePaiement))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void populateForm( int idFac) throws ParseException {
        
        FactureDAO facDAO = DAOFactory.getFactureDAO();
        Facture fac =  facDAO.find(idFac);
        refFacture.setText(fac.getRefFacture());
        idFacture.setText(String.valueOf( fac.getId() ));
        reste = DAOFactory.getPaiementDAO().getMontantVerse(idFac);
        FormatUtils formater = new FormatUtils();
        resteAPayer.setText(formater.formatFloat(fac.getMontant() - reste ));
        montantAPayer.setText(formater.formatFloat(fac.getMontant()));
        idClient.setText(String.valueOf( fac.getIdClient()) );
        datePaiement.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fac.getDateFacture()));
        nomClient.setText(fac.getPersonne().getNom() + " " + fac.getPersonne().getPrenom());        
        
    }
    
    
    private void refFactureComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_refFactureComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_refFactureComponentShown

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editbtnActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaiementFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaiementFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaiementFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaiementFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaiementFacture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser datePaiement;
    private javaapp.component.ButtonRadius editbtn;
    private javaapp.component.ButtonRadius editbtn1;
    private javax.swing.JComboBox<String> heurePaiement;
    private javax.swing.JLabel idClient;
    private javax.swing.JLabel idFacture;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> minutePaiement;
    private javax.swing.JTextField montantAPayer;
    private javax.swing.JSpinner montantVerse;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField refFacture;
    private javax.swing.JTextField resteAPayer;
    // End of variables declaration//GEN-END:variables

 
}
