/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaap.modal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.Facture;
import javaapp.bean.Livraison;
import javaapp.bean.LivraisonDetail;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.ModalActionEvent;
import javaapp.dao.FactureDAO;
import javaapp.dao.LivraisonDAO;
import javaapp.dao.LivraisonDetailDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class ModalFacture extends javax.swing.JFrame {
    private FormatUtils formater;
    private Livraison liv;
    public ModalFacture(int idLivraisonSelected) {
        initComponents();
        populateFields(idLivraisonSelected);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
    }
    public ModalFacture() {
        initComponents();
    }

    private void populateFields(int idLivraisonSelected) {
        LivraisonDAO livDAO = DAOFactory.getLivraisonDAO();
        liv = livDAO.find(idLivraisonSelected);
        
        cli_nom.setText(liv.getClient().getNom()+" "+liv.getClient().getPrenom());
        cli_adresse.setText(liv.getClient().getAdresse());
        cli_contact.setText(liv.getClient().getTel1());
        idLivraison.setText(String.valueOf(liv.getId()));
        refLivraison.setText(liv.getRefLivraison());
        idCommande.setText(String.valueOf(liv.getIdCommande()));
        refCommande.setText(DAOFactory.getCommandeDAO().find(liv.getIdCommande()).getRefCommande());
        dateLivraison.setText(liv.getDateLivraison());
        idClient.setText(String.valueOf(liv.getClient().getId()));
        frais.setText(String.valueOf(liv.getFrais()));
        try {
            //        dateFacture.setDate(new Date());
            DateConverter.initializeDate(dateFacture, heureFacture, minuteFacture, null);
        } catch (ParseException ex) {
            Logger.getLogger(ModalFacture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String[] column = {"Article","Réference", "Qté commandé", "Qté colissé","PU HT","TVA","Remise (%)", "Total HT", "Total TTC"};
   
        DefaultTableModel model = new DefaultTableModel(null, column);
        
//           List <LivraisonDetail> livDets = DAOFactory.getLivraisonDetailDAO().select(liv.getId());
//           if(livDets != null){
//               liv.get
               int i = 0;
           for( LivraisonDetail livDet : liv.getLivraisonDetails()){
            Object[] data = new Object[9];
            data[0] = livDet.getProduit().getDesignation();
            data[1] = livDet.getProduit().getRefProduit();
            data[2] = livDet.getCommandeDetail().getQuantite();
            data[3] = livDet.getQuantiteRecu();
            
            float ttPUHT = 0.0f ;
            float ttTVA = 0.0f;
            float ttTTC = 0.0f ;
            
//            for(javaapp.bean.CommandeDetail commandeDet : liv.getLivraisonDetails()){
            if(livDet.getCommandeDetail().getIdProduit() == livDet.getIdProduit()){
                float PUHT = livDet.getProduit().getPUHT();
                float TVA = livDet.getProduit().getTVA();
                float PUTTC = PUHT + (PUHT*TVA/100);
                
                ttPUHT += PUHT*livDet.getQuantiteRecu();
                ttTVA += TVA*livDet.getQuantiteRecu();
                ttTTC += PUTTC*livDet.getQuantiteRecu();
                
                data[4] = livDet.getProduit().getPUHT();
                data[5] = livDet.getProduit().getTVA();
                data[6] = livDet.getCommandeDetail().getRemise();
                data[7] = PUHT*livDet.getQuantiteRecu();
                data[8] = PUTTC*livDet.getQuantiteRecu();
            }
//                totalHTField.setText(formater.formatFloat(ttPUHT));
//                totalTTCField.setText(formater.formatFloat(ttTTC));
//                totalTVA.setText(formater.formatFloat(ttTVA));
            model.addRow(data);
            i++;
           }
        listCommande.setModel(model);
         updateTotals();
        
    }
    
        private void updateTotals() {
    DefaultTableModel model = (DefaultTableModel) listCommande.getModel();

    float total_HT = 0.0f;
    float total_TVA = 0.0f;
    float total_Remise = 0.0f;
    float total_TTC = 0.0f;

    for (int i = 0; i < model.getRowCount(); i++) {
        float puHT = Float.parseFloat(model.getValueAt(i, 4).toString());
        float tva = (float) model.getValueAt(i, 5);
        float remise = Float.parseFloat( model.getValueAt(i, 6).toString() );
        int qteAExpedier = (int) model.getValueAt(i, 3);
        float totalHTPerItem = puHT * qteAExpedier;
        float remiseAmount = totalHTPerItem * (remise / 100);
        float totalHTAfterRemise = totalHTPerItem - remiseAmount;
        float tvaAmount = totalHTAfterRemise * (tva / 100);
        float totalTTCPerItem = totalHTAfterRemise + tvaAmount;

        total_HT += totalHTAfterRemise;
        total_TVA += tvaAmount;
        total_Remise += remiseAmount;
        total_TTC += totalTTCPerItem;
    }

    // Mise à jour des champs de totaux
    totalHTField.setText(String.valueOf(total_HT));
    totalTVA.setText(String.valueOf(total_TVA));
    totalRemiseField.setText(String.valueOf(total_Remise));
    totalTTCField.setText(String.valueOf(total_TTC));
    totalNetAPayerField.setText(String.valueOf(total_TTC - total_Remise));
}
    
    public int insererFacture(){
        Facture fac = new Facture();
        
            java.util.Date date = dateFacture.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int heure =  Integer.parseInt(heureFacture.getSelectedItem().toString());
            int minute = Integer.parseInt(minuteFacture.getSelectedItem().toString());
            calendar.set(Calendar.HOUR_OF_DAY, heure);
            calendar.set(Calendar.MINUTE, minute);

            // Formater la date selon le format "yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(calendar.getTime());
            fac.setDateFacture(formattedDate);
            fac.setDescription(description.getText());
            fac.setIdClient(Integer.parseInt(idClient.getText()));
            fac.setIdLivraison(Integer.parseInt(idLivraison.getText()));
            fac.setMontant(Float.parseFloat(totalTTCField.getText()) + liv.getFrais());
//            fac.setRefFacture(refFacture.getText());
//        livraison.setMontant( Float.valueOf(totalTTC.toString()) + Float.valueOf(accompte.getValue().toString()) );
        
        FactureDAO facDAO = DAOFactory.getFactureDAO();
        
        if(facDAO.create(fac)){
            return facDAO.getGeneratedKey();
        }else{
            return -1;
        }
        
    }
    
    private boolean insererLivraisonDetail(int idLivraison){
        LivraisonDetail nouvelleLivraison = new LivraisonDetail();
        for(int i = listCommande.getRowCount()-1 ; i >= 0; i--){
            
        nouvelleLivraison.setIdProduit(1);
        nouvelleLivraison.setQuantiteRecu(100);
        nouvelleLivraison.setQuantiteValide(95);
        nouvelleLivraison.setMontant(500.0f);
        nouvelleLivraison.setIdLivraison(idLivraison);
        LivraisonDetailDAO livDetailDAO = DAOFactory.getLivraisonDetailDAO();
        livDetailDAO.create(nouvelleLivraison);
            
        }
        return false;
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        e_nom = new javax.swing.JLabel();
        e_adresse = new javax.swing.JLabel();
        e_email = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cli_nom = new javax.swing.JLabel();
        cli_adresse = new javax.swing.JLabel();
        cli_contact = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        idClient = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listCommande = new javaapp.component.Table();
        jLabel19 = new javax.swing.JLabel();
        dateFacture = new com.toedter.calendar.JDateChooser();
        currentDate = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        totalHTField = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        totalTVA = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        totalTTCField = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        totalNetAPayerField = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        totalRemiseField = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        frais = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        heureFacture = new javax.swing.JComboBox<>();
        minuteFacture = new javax.swing.JComboBox<>();
        numRefCommande1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        refLivraison = new javax.swing.JTextField();
        dateLivraison = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        idLivraison = new javax.swing.JLabel();
        numCommande = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        idCommande = new javax.swing.JLabel();
        refCommande = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        btn_create_BL = new javaapp.component.ButtonRadius();
        btn_create_BL1 = new javaapp.component.ButtonRadius();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Créer une facture");
        setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(241, 248, 240));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Entreprise");

        e_nom.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_nom.setForeground(new java.awt.Color(43, 40, 54));

        e_adresse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_adresse.setForeground(new java.awt.Color(43, 40, 54));

        e_email.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_email.setForeground(new java.awt.Color(43, 40, 54));

        jPanel10.setBackground(new java.awt.Color(102, 102, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(0, 4));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(e_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(e_adresse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(e_nom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_adresse, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_email, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Client n°");

        cli_nom.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cli_nom.setForeground(new java.awt.Color(43, 40, 54));

        cli_adresse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cli_adresse.setForeground(new java.awt.Color(43, 40, 54));

        cli_contact.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cli_contact.setForeground(new java.awt.Color(43, 40, 54));

        jPanel6.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        idClient.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idClient.setForeground(new java.awt.Color(51, 51, 255));
        idClient.setText("12");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cli_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cli_adresse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idClient, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cli_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(idClient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cli_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cli_adresse, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cli_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(43, 40, 54));
        jLabel14.setText("Adresse");

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText("Ligne de la facture");

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setBorder(null);

        listCommande.setForeground(new java.awt.Color(51, 51, 51));
        listCommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Article", "Reference", "Qté commandé", "Qté receptionné", "Qté livré", "Reste à livrer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jScrollPane3.setViewportView(listCommande);
        if (listCommande.getColumnModel().getColumnCount() > 0) {
            listCommande.getColumnModel().getColumn(6).setPreferredWidth(20);
        }

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(43, 40, 54));
        jLabel19.setText("Date facturation");

        dateFacture.setBackground(new java.awt.Color(255, 255, 255));
        dateFacture.setForeground(new java.awt.Color(43, 40, 54));

        currentDate.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        currentDate.setForeground(new java.awt.Color(43, 40, 54));
        currentDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentDate.setText("Date: 23/03/2024");

        jPanel7.setBackground(new java.awt.Color(241, 248, 240));

        jPanel8.setBackground(new java.awt.Color(153, 153, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(440, 4));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(43, 40, 54));
        jLabel10.setText("Total HT");

        totalHTField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalHTField.setForeground(new java.awt.Color(43, 40, 54));
        totalHTField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalHTField.setText("0");

        totalTVA.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTVA.setForeground(new java.awt.Color(43, 40, 54));
        totalTVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTVA.setText("0");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(43, 40, 54));
        jLabel11.setText("TVA");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(43, 40, 54));
        jLabel12.setText("Total TTC");

        totalTTCField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalTTCField.setForeground(new java.awt.Color(43, 40, 54));
        totalTTCField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalTTCField.setText("0");

        totalNetAPayerField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalNetAPayerField.setForeground(new java.awt.Color(43, 40, 54));
        totalNetAPayerField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalNetAPayerField.setText("0");

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(43, 40, 54));
        jLabel13.setText("Total net à payer");

        totalRemiseField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalRemiseField.setForeground(new java.awt.Color(43, 40, 54));
        totalRemiseField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRemiseField.setText("0");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(43, 40, 54));
        jLabel15.setText("Remise");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(43, 40, 54));
        jLabel16.setText("Frais de livraison");

        frais.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        frais.setForeground(new java.awt.Color(43, 40, 54));
        frais.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        frais.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalHTField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addComponent(jSeparator5)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTVA, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalTTCField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(totalNetAPayerField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalRemiseField, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(frais, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(totalHTField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalTVA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totalRemiseField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalTTCField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(frais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalNetAPayerField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 276, Short.MAX_VALUE)))
        );

        heureFacture.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        minuteFacture.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        numRefCommande1.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        numRefCommande1.setForeground(new java.awt.Color(51, 51, 255));
        numRefCommande1.setText("Propriété de la facture");

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(43, 40, 54));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Imprimer le document");

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(43, 40, 54));
        jLabel22.setText("BL n°");

        refLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refLivraison.setForeground(new java.awt.Color(43, 40, 54));

        dateLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateLivraison.setForeground(new java.awt.Color(43, 40, 54));

        jLabel23.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(43, 40, 54));
        jLabel23.setText("Date de livraison");

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));

        idLivraison.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idLivraison.setForeground(new java.awt.Color(43, 40, 54));
        idLivraison.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(idLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        numCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        numCommande.setForeground(new java.awt.Color(43, 40, 54));
        numCommande.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));

        idCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idCommande.setForeground(new java.awt.Color(43, 40, 54));
        idCommande.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(idCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(idCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        refCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        refCommande.setForeground(new java.awt.Color(43, 40, 54));

        jLabel24.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(43, 40, 54));
        jLabel24.setText("Commande n°");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))
                    .addComponent(numRefCommande1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(currentDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateFacture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(heureFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minuteFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(numRefCommande1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(currentDate)))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(heureFacture)
                                    .addComponent(minuteFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel24)
                                        .addComponent(jLabel19))
                                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(refLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel22))
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(numCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jScrollPane2.setViewportView(jPanel1);

        btn_create_BL.setBackground(new java.awt.Color(102, 102, 255));
        btn_create_BL.setBorder(null);
        btn_create_BL.setForeground(new java.awt.Color(255, 255, 255));
        btn_create_BL.setText("Créer");
        btn_create_BL.setBorderColor(new java.awt.Color(102, 102, 255));
        btn_create_BL.setColor(new java.awt.Color(102, 102, 255));
        btn_create_BL.setColorClick(new java.awt.Color(255, 255, 255));
        btn_create_BL.setColorOver(new java.awt.Color(94, 94, 237));
        btn_create_BL.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        btn_create_BL1.setBackground(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setBorder(null);
        btn_create_BL1.setForeground(new java.awt.Color(82, 82, 251));
        btn_create_BL1.setText("Annuler");
        btn_create_BL1.setBorderColor(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setColor(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setColorClick(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setColorOver(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btn_create_BL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_BL1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(102, 102, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1165, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_create_BL1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_create_BL, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_create_BL, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_create_BL1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_create_BL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_BL1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_create_BL1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ModalFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModalFacture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_create_BL;
    private javaapp.component.ButtonRadius btn_create_BL1;
    private javax.swing.JLabel cli_adresse;
    private javax.swing.JLabel cli_contact;
    private javax.swing.JLabel cli_nom;
    private javax.swing.JLabel currentDate;
    private com.toedter.calendar.JDateChooser dateFacture;
    private javax.swing.JTextField dateLivraison;
    private javax.swing.JTextArea description;
    private javax.swing.JLabel e_adresse;
    private javax.swing.JLabel e_email;
    private javax.swing.JLabel e_nom;
    private javax.swing.JLabel frais;
    private javax.swing.JComboBox<String> heureFacture;
    private javax.swing.JLabel idClient;
    private javax.swing.JLabel idCommande;
    private javax.swing.JLabel idLivraison;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javaapp.component.Table listCommande;
    private javax.swing.JComboBox<String> minuteFacture;
    private javax.swing.JLabel numCommande;
    private javax.swing.JLabel numRefCommande1;
    private javax.swing.JTextField refCommande;
    private javax.swing.JTextField refLivraison;
    private javax.swing.JLabel totalHTField;
    private javax.swing.JLabel totalNetAPayerField;
    private javax.swing.JLabel totalRemiseField;
    private javax.swing.JLabel totalTTCField;
    private javax.swing.JLabel totalTVA;
    // End of variables declaration//GEN-END:variables

    public void initEvent(ModalActionEvent event, int i) {
         btn_create_BL.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(JOptionPane.showConfirmDialog(null, "Une facture sera crée avec un état 'Non payé'","Message",JOptionPane.YES_NO_OPTION ) == 0){
            insererFacture();
            dispose();
        } 
        }});
    }


}
