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

public class ModalCreerFacture extends javax.swing.JFrame {
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
     
 
public ModalCreerFacture(int idCommande) {
        initComponents();
        try {
            populateForm(idCommande);
        } catch (ParseException ex) {
            Logger.getLogger(ModalCreerFacture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModalCreerFacture() {
        initComponents();
    }

    public void initEvent(ModalActionEvent event, int row) {
        editbtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae) {
            int res = -1;
            FactureDAO facDAO = DAOFactory.getFactureDAO();
            Facture fac = new Facture();
//            float a = Float.parseFloat(montantCommande.getValue().toString());
//            float montantNet = a - (float)(a*Float.parseFloat(remise.getValue().toString()))/100;
            
//            fac.setMontant(a);
            fac.setDescription(descriptionFac.getText());
            fac.setIdClient(idClientSelected);
//            fac.setPj(pj.getText());
            
            if(liv != null){
                res = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir convertir?","Message de confirmation",JOptionPane.YES_NO_OPTION);
                fac.setIdLivraison(liv.getId());
//                montantNet += Float.parseFloat(frais.getValue().toString());
            }else{
                res = JOptionPane.showConfirmDialog(null, "Cette commande n'est pas encore convertis en bon de livraison. \n\nVoulez-vous enregistrer quand même?","Attention",JOptionPane.WARNING_MESSAGE);
            }
//            fac.setMontant(montantNet);
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
        refCommande = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        dateCommande = new com.toedter.calendar.JDateChooser();
        annuler = new javaapp.component.ButtonRadius();
        editbtn = new javaapp.component.ButtonRadius();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionFac = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        infoNotEmpty = new javax.swing.JLayeredPane();
        jLabel11 = new javax.swing.JLabel();
        refLivraison = new javax.swing.JTextField();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        nomClient1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        numLivraison = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        numCommande = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();

        setTitle("Détails de la livraison");
        setIconImages(null);

        jPanel1.setBackground(new java.awt.Color(241, 248, 240));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(43, 40, 54));
        jLabel5.setText("Client");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(43, 40, 54));
        jLabel2.setText("Nom");

        nomClient.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomClient.setForeground(new java.awt.Color(43, 40, 54));
        nomClient.setSelectionColor(new java.awt.Color(153, 255, 255));

        refCommande.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refCommande.setForeground(new java.awt.Color(43, 40, 54));
        refCommande.setSelectionColor(new java.awt.Color(153, 255, 255));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(43, 40, 54));
        jLabel6.setText("Commande");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(43, 40, 54));
        jLabel7.setText("Date d'écheance");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(43, 40, 54));
        jLabel4.setText("Note");

        dateCommande.setForeground(new java.awt.Color(43, 40, 54));

        annuler.setBackground(new java.awt.Color(241, 248, 240));
        annuler.setBorder(null);
        annuler.setForeground(new java.awt.Color(102, 102, 255));
        annuler.setText("Ajouter");
        annuler.setBorderColor(new java.awt.Color(102, 102, 255));
        annuler.setColor(new java.awt.Color(241, 248, 240));
        annuler.setColorClick(new java.awt.Color(204, 204, 255));
        annuler.setColorOver(new java.awt.Color(204, 204, 255));
        annuler.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerActionPerformed(evt);
            }
        });

        editbtn.setBackground(new java.awt.Color(102, 102, 255));
        editbtn.setBorder(null);
        editbtn.setForeground(new java.awt.Color(255, 255, 255));
        editbtn.setText("Créer");
        editbtn.setBorderColor(new java.awt.Color(102, 102, 255));
        editbtn.setColor(new java.awt.Color(102, 102, 255));
        editbtn.setColorClick(new java.awt.Color(102, 102, 255));
        editbtn.setColorOver(new java.awt.Color(132, 132, 232));
        editbtn.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(43, 40, 54));
        jLabel9.setText("Facture");

        descriptionFac.setColumns(20);
        descriptionFac.setForeground(new java.awt.Color(43, 40, 54));
        descriptionFac.setRows(5);
        jScrollPane1.setViewportView(descriptionFac);

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(43, 40, 54));
        jLabel3.setText("Montant d'un acompte");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(43, 40, 54));
        jLabel11.setText("Livraison");

        refLivraison.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        refLivraison.setForeground(new java.awt.Color(43, 40, 54));
        refLivraison.setSelectionColor(new java.awt.Color(153, 255, 255));

        dateLivraison.setForeground(new java.awt.Color(43, 40, 54));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(43, 40, 54));
        jLabel13.setText("Date de livraison");

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(43, 40, 54));
        jLabel14.setText("Reférence");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Adresse");

        nomClient1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        nomClient1.setForeground(new java.awt.Color(43, 40, 54));
        nomClient1.setSelectionColor(new java.awt.Color(153, 255, 255));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        numLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        numLivraison.setForeground(new java.awt.Color(43, 40, 54));
        numLivraison.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numLivraison.setPreferredSize(new java.awt.Dimension(0, 38));

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(43, 40, 54));
        jLabel19.setText("N°");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(numLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        infoNotEmpty.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(refLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(dateLivraison, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jSeparator3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(nomClient1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        infoNotEmpty.setLayer(jPanel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout infoNotEmptyLayout = new javax.swing.GroupLayout(infoNotEmpty);
        infoNotEmpty.setLayout(infoNotEmptyLayout);
        infoNotEmptyLayout.setHorizontalGroup(
            infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel11)
                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomClient1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        infoNotEmptyLayout.setVerticalGroup(
            infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoNotEmptyLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(infoNotEmptyLayout.createSequentialGroup()
                                .addGroup(infoNotEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoNotEmptyLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomClient1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jCheckBox1.setBackground(new java.awt.Color(241, 248, 240));
        jCheckBox1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(43, 40, 54));
        jCheckBox1.setText("Imprimer le document");

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));

        numCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        numCommande.setForeground(new java.awt.Color(43, 40, 54));
        numCommande.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 40, 54));
        jLabel12.setText("N°");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(43, 40, 54));
        jLabel18.setText("Reférence");

        jSpinner1.setEnabled(false);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
            .addComponent(infoNotEmpty)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5)
                        .addGap(105, 105, 105)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel6)))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(refCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(infoNotEmpty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(annuler, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
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
//        montantCommande.setValue(comm.getMontant());
//        remise.setValue(comm.getRemise());
        dateCommande.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(comm.getDateCommande()));
        nomClient.setText(comm.getClient().getNom() + " " + comm.getClient().getPrenom());
        liv = DAOFactory.getLivraisonDAO().findByCommande(idCommande);
        idClientSelected= comm.getIdClient();
        
        if(liv != null){
            refLivraison.setText(liv.getRefLivraison());
            dateLivraison.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(liv.getDateLivraison()));
//            frais.setValue(liv.getFrais());
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
            java.util.logging.Logger.getLogger(ModalCreerFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalCreerFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalCreerFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalCreerFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ModalCreerFacture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius annuler;
    private com.toedter.calendar.JDateChooser dateCommande;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JTextArea descriptionFac;
    private javaapp.component.ButtonRadius editbtn;
    private javax.swing.JLayeredPane infoNotEmpty;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField nomClient;
    private javax.swing.JTextField nomClient1;
    private javax.swing.JLabel numCommande;
    private javax.swing.JLabel numLivraison;
    private javax.swing.JTextField refCommande;
    private javax.swing.JTextField refLivraison;
    // End of variables declaration//GEN-END:variables

 
}
