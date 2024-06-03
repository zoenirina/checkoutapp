package javaap.modal;

import javaapp.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.CommandeC;
import javaapp.bean.Facture;
import javaapp.bean.Livraison;
import javaapp.component.ModalActionEvent;
import javaapp.dao.CommandeCDAO;
import javaapp.dao.FactureDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;

public class ModalFactures extends javax.swing.JFrame {
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
     
 
public ModalFactures(int idCommande) {
        initComponents();
        try {
            populateForm(idCommande);
        } catch (ParseException ex) {
            Logger.getLogger(ModalFactures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModalFactures() {
        initComponents();
    }

    public void initEvent(ModalActionEvent event, int row) {
        editbtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae) {
            int res = -1;
            FactureDAO facDAO = DAOFactory.getFactureDAO();
            Facture fac = new Facture();
            float a = Float.parseFloat(montantCommande.getValue().toString());
            float montantNet = a - (float)(a*Float.parseFloat(remise.getValue().toString()))/100;
            
            fac.setMontant(a);
            fac.setDescription(descriptionFac.getText());
            fac.setIdClient(idClientSelected);
            fac.setPj(pj.getText());
            
            if(liv != null){
                res = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir convertir?","Message de confirmation",JOptionPane.YES_NO_OPTION);
                fac.setIdLivraison(liv.getId());
                montantNet += Float.parseFloat(frais.getValue().toString());
            }else{
                res = JOptionPane.showConfirmDialog(null, "Cette commande n'est pas encore convertis en bon de livraison. \n\nVoulez-vous enregistrer quand même?","Attention",JOptionPane.WARNING_MESSAGE);
            }
            fac.setMontant(montantNet);
            if(res == 0){
                if(facDAO.create(fac)){
                    setVisible(false);
                    event.onEdit(row);
                    System.out.print("Event dans modalFacture!");
                    dispose();
                }
            }
        }
        });
    };
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nomClient = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        refCommande = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        montantCommande = new javax.swing.JSpinner();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        dateCommande = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        remise = new javax.swing.JSpinner();
        annuler = new javaapp.component.ButtonRadius();
        editbtn = new javaapp.component.ButtonRadius();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionFac = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        pj = new javax.swing.JTextField();
        infoNotEmpty = new javax.swing.JLayeredPane();
        jLabel11 = new javax.swing.JLabel();
        refLivraison = new javax.swing.JTextField();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        frais = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        infoEmpty = new javax.swing.JLayeredPane();

        setTitle("Détails de la livraison");
        setIconImages(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Client");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Nom");

        nomClient.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomClient.setForeground(new java.awt.Color(102, 102, 102));
        nomClient.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Reférence de la commande");

        refCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refCommande.setForeground(new java.awt.Color(102, 102, 102));
        refCommande.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Commande");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Date d'écheance");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Montant TTC");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Note");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Remise");

        annuler.setBackground(new java.awt.Color(255, 222, 177));
        annuler.setBorder(null);
        annuler.setForeground(new java.awt.Color(248, 91, 50));
        annuler.setText("Annuler");
        annuler.setBorderColor(new java.awt.Color(255, 222, 177));
        annuler.setColor(new java.awt.Color(255, 222, 177));
        annuler.setColorClick(new java.awt.Color(255, 222, 177));
        annuler.setColorOver(new java.awt.Color(255, 222, 177));
        annuler.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerActionPerformed(evt);
            }
        });

        editbtn.setBackground(new java.awt.Color(255, 105, 82));
        editbtn.setBorder(null);
        editbtn.setForeground(new java.awt.Color(255, 255, 255));
        editbtn.setText("Enregistrer");
        editbtn.setBorderColor(new java.awt.Color(255, 105, 82));
        editbtn.setColor(new java.awt.Color(255, 105, 82));
        editbtn.setColorClick(new java.awt.Color(255, 105, 82));
        editbtn.setColorOver(new java.awt.Color(255, 95, 69));
        editbtn.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Facture");

        descriptionFac.setColumns(20);
        descriptionFac.setRows(5);
        jScrollPane1.setViewportView(descriptionFac);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Pièce jointe (Chemin) ");

        pj.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        pj.setForeground(new java.awt.Color(102, 102, 102));
        pj.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Livraison");

        refLivraison.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refLivraison.setForeground(new java.awt.Color(102, 102, 102));
        refLivraison.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Date de livraison");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Reférence");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Charge");

        infoNotEmpty.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(refLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(dateLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jSeparator3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(frais, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout infoNotEmptyLayout = new javax.swing.GroupLayout(infoNotEmpty);
        infoNotEmpty.setLayout(infoNotEmptyLayout);
        infoNotEmptyLayout.setHorizontalGroup(
            infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(83, 83, 83)
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(frais)
                                    .addComponent(refLivraison)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dateLivraison, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))))
                    .addComponent(jSeparator3))
                .addGap(62, 62, 62))
        );
        infoNotEmptyLayout.setVerticalGroup(
            infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(frais, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout infoEmptyLayout = new javax.swing.GroupLayout(infoEmpty);
        infoEmpty.setLayout(infoEmptyLayout);
        infoEmptyLayout.setHorizontalGroup(
            infoEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );
        infoEmptyLayout.setVerticalGroup(
            infoEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(320, 320, 320))
                            .addComponent(nomClient)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(pj)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(refCommande, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(montantCommande, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(remise)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateCommande, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                    .addComponent(jLabel7)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(editbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(62, 62, 62))
            .addComponent(infoNotEmpty)
            .addComponent(infoEmpty)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(montantCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(remise, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoNotEmpty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pj, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
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

    private void populateForm( int idCommande) throws ParseException {
        
        CommandeCDAO commandeDAO = DAOFactory.getCommandeDAO();
        CommandeC comm =  commandeDAO.find(idCommande);
        refCommande.setText(comm.getRefCommande());
        montantCommande.setValue(comm.getMontant());
        remise.setValue(comm.getRemise());
        dateCommande.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comm.getDateCommande()));
        nomClient.setText(comm.getClient().getNom() + " " + comm.getClient().getPrenom());
        liv = DAOFactory.getLivraisonDAO().findByCommande(idCommande);
        idClientSelected= comm.getIdClient();
        
        if(liv != null){
            refLivraison.setText(liv.getRefLivraison());
            dateLivraison.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(liv.getDateLivraison()));
            frais.setValue(liv.getFrais());
            infoNotEmpty.setVisible(true);
        }else{
            infoNotEmpty.setVisible(false);
        }
        
    }
    
    
    private void annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerActionPerformed
        
        this.dispose();
        
    }//GEN-LAST:event_annulerActionPerformed

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
            java.util.logging.Logger.getLogger(ModalFactures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalFactures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalFactures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalFactures.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new ModalFactures().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius annuler;
    private com.toedter.calendar.JDateChooser dateCommande;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JTextArea descriptionFac;
    private javaapp.component.ButtonRadius editbtn;
    private javax.swing.JSpinner frais;
    private javax.swing.JLayeredPane infoEmpty;
    private javax.swing.JLayeredPane infoNotEmpty;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSpinner montantCommande;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField pj;
    private javax.swing.JTextField refCommande;
    private javax.swing.JTextField refLivraison;
    private javax.swing.JSpinner remise;
    // End of variables declaration//GEN-END:variables

 
}
