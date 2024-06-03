/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaap.modal;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.bean.CommandeDetailFournisseur;
import javaapp.bean.CommandeF;
import javaapp.bean.MouvementStock;
import javaapp.bean.Produit;
import javaapp.bean.Reception;
import javaapp.bean.ReceptionDetail;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.dao.CommandeFournisseurDAO;
import javaapp.dao.MouvementStockDAO;
import javaapp.dao.ProduitDAO;
import javaapp.dao.ReceptionDAO;
import javaapp.dao.ReceptionDetailDAO;
import javaapp.factory.DAOFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ZOENIRINA
 */
public class ModalBonReception extends javax.swing.JFrame {
    private FormatUtils formater = new FormatUtils();
    int idCommSelected = 0;
    private ReceptionDAO receptionDAO;
    private CommandeFournisseurDAO commandeDAO;
    private ReceptionDetailDAO recepDetailDAO;
    private ProduitDAO produitDAO;
    private MouvementStockDAO mouveStockDAO;
    
    public ModalBonReception(int idCommande) {
        initComponents();
        receptionDAO =    DAOFactory.getReceptionDAO(); 
        commandeDAO = DAOFactory.getCommandeFournisseurDAO();
        recepDetailDAO = DAOFactory.getReceptionDetailDAO();
        produitDAO = DAOFactory.getProduitDAO();
        mouveStockDAO = DAOFactory.getMouvementStockDAO();
        
        populateFields(idCommande);
        this.idCommSelected = idCommande;
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        updateTotals();
    }
    
    public ModalBonReception() {
        initComponents();
    }

    private void populateFields(int idCommande) {
        CommandeF comm = commandeDAO.find(idCommande);
        
        fourniss_nom.setText(comm.getFournisseur().getNom()+" "+comm.getFournisseur().getPrenom());
        fourniss_adresse.setText(comm.getFournisseur().getAdresse());
        fourniss_contact.setText(comm.getFournisseur().getTel1());
        idComm.setText(String.valueOf(comm.getId()));
        refCommande.setText(comm.getRefCommande());
        dateCommande.setText("Effectué le : "+DateConverter.convertToDate(comm.getDateCommande()));
        try {
                DateConverter.initializeDate(dateLivraison, heureLivraison, minuteLivraison, null);
            } catch (ParseException ex) {
                Logger.getLogger(ModalBonReception.class.getName()).log(Level.SEVERE, null, ex);
            }
        idFournisseur.setText(String.valueOf(comm.getFournisseur().getId()));
        
        String[] column = {"Article","Réference","PU HT", "TVA (%)","Remise",  "Qté commandé", "Qté à expédier","Total HT", "Entrepôt"};
   
        DefaultTableModel model = new DefaultTableModel(null, column);
        
           List <CommandeDetailFournisseur> commDet = commandeDAO.find(idCommande).getCommandeDetails();
           if(commDet != null){
               
            float ttPUHT = 0.0f ;
            float ttTVA = 0.0f;
            float ttTTC = 0.0f, ttRemise = 0.0f;    
               
           for( CommandeDetailFournisseur commandeDet : comm.getCommandeDetails()){
            Object[] data = new Object[9];
            data[0] = commandeDet.getProduit().getDesignation();
            data[1] = commandeDet.getProduit().getRefProduit();
            data[2] = formater.formatFloat(commandeDet.getProduit().getPUHT());
            data[3] = commandeDet.getProduit().getTVA();
            data[4] = commandeDet.getProduit().getTVA();
            data[5] = commandeDet.getQuantite();
            data[6] = 0;
            data[7] = 0;
            data[8] = " ";
            
            float PUHT = commandeDet.getProduit().getPUHT();
            float TVA = commandeDet.getProduit().getTVA();
            float PUTTC = PUHT + (PUHT*TVA/100);
            float PURemise = PUTTC*commandeDet.getRemise()/100;
            
            ttPUHT += (PUHT*commandeDet.getQuantite());
            ttTVA += TVA*commandeDet.getQuantite();
            ttTTC += PUTTC*commandeDet.getQuantite();
            ttRemise += PURemise*commandeDet.getQuantite();
            
            model.addRow(data);
            }
           System.out.print(
                    "ttPUHT="+formater.formatFloat(ttPUHT)+
                    "ttTTC="+formater.formatFloat(ttTTC)+
                    "ttTVA="+ttTVA+"ttRemise="+ttRemise+
                    "net"+ttTTC+ttRemise
           );
                totalHTField.setText(formater.formatFloat(ttPUHT));
                totalTTCField.setText(formater.formatFloat(ttTTC));
                totalTVA.setText(formater.formatFloat(ttTVA));
                totalRemiseField.setText(formater.formatFloat(ttRemise));
                totalNetAPayerField.setText(formater.formatFloat(ttTTC+ttRemise));
                
           }
           
           
           
        listCommande.setModel(model);
        
    }
    
    public void getResume(){
        TableModel model = listCommande.getModel();
        
    float totalHT = 0.0f;
        float totalTVA = 0.0f;
        float totalRemise = 0.0f; // If you have a discount column or logic, calculate accordingly
        float totalTTC = 0.0f;
        float totalNetAPayer = 0.0f;

        for (int i = 0; i < model.getRowCount(); i++) {
            float puHT = (float) model.getValueAt(i, 2); // PU HT
            float tva = (float) model.getValueAt(i, 3); // TVA (%)
            int qteAExpedier = (int) model.getValueAt(i, 5); // Qté à expédier
            float totalHTPerItem = (float) model.getValueAt(i, 6); // Total HT for the item

            float tvaAmount = (totalHTPerItem * tva) / 100;
            float totalTTCPerItem = totalHTPerItem + tvaAmount;
            float netAPayerPerItem = totalTTCPerItem; // Adjust if there's any discount logic

            totalHT += totalHTPerItem;
            totalTVA += tvaAmount;
            totalTTC += totalTTCPerItem;
            totalNetAPayer += netAPayerPerItem;
        }

        // Print totals
        System.out.println("Total HT: " + totalHT);
        System.out.println("Total TVA: " + totalTVA);
        System.out.println("Total Remise: " + totalRemise); // If applicable
        System.out.println("Total TTC: " + totalTTC);
        System.out.println("Total Net à Payer: " + totalNetAPayer);
    
    }
    
    public int insererReception(){
        Reception recep = new Reception();
        java.util.Date date = dateLivraison.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int heure =  Integer.parseInt(heureLivraison.getSelectedItem().toString());
        int minute = Integer.parseInt(minuteLivraison.getSelectedItem().toString());
        calendar.set(Calendar.HOUR_OF_DAY, heure);
        calendar.set(Calendar.MINUTE, minute);

        // Formater la date selon le format "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(calendar.getTime());
        
        int enregistrer = -1;
        
        recep.setDateReception(formattedDate);
        recep.setDescription(description.getText());
        recep.setRefReception(refReception.getText());
        recep.setFrais(Float.valueOf(frais.getValue().toString()));
        recep.setIdCommande(Integer.valueOf(idComm.getText()));
        float montant = formater.parseFloat(totalNetAPayerField.getText()) + Float.parseFloat(frais.getValue().toString());
        recep.setMontant(montant);
        recep.setIdFournisseur(Integer.parseInt(idFournisseur.getText()));
//        recep.setStatus(status.getSelectedItem().toString());
        recep.setStatus("Créée");
        if(receptionDAO.create(recep)){
            return receptionDAO.getGeneratedKey();
        }else{
            return -1;
        }
        
    }
    
    private boolean insererReceptionDetail(int idReception){
        ReceptionDetail nouvelleReception = new ReceptionDetail();
        for(int i = listCommande.getRowCount()-1 ; i >= 0; i--){
            
        CommandeDetailFournisseur commDet = DAOFactory.getCommandeDetailFournisseurDAO().findByCommandeProduit(idCommSelected, listCommande.getModel().getValueAt(i, 1).toString());
        nouvelleReception.setIdCommandeDetailFournisseur(commDet.getId());
        float montant = Float.parseFloat(listCommande.getModel().getValueAt(i, 7).toString());
        String refMagSelected = listCommande.getModel().getValueAt(i, 8).toString();
        int qteColisse =  Integer.parseInt(listCommande.getModel().getValueAt(i, 6).toString());
        
        nouvelleReception.setIdProduit(commDet.getIdProduit());
        nouvelleReception.setQuantiteRecu(qteColisse);
        nouvelleReception.setQuantiteValide(0);
        nouvelleReception.setMontant(montant);
        nouvelleReception.setIdReception(idReception);
        nouvelleReception.setStatus("Créée");
        nouvelleReception.setIdCommandeDetailFournisseur(commDet.getId());
        recepDetailDAO.create(nouvelleReception);
        
        insererMouvementStock( commDet.getIdProduit(),  qteColisse, DAOFactory.getMagasinDAO().findByRef( refMagSelected ).getId(), recepDetailDAO.getGeneratedKey(), "Non");
        
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
        fourniss_nom = new javax.swing.JLabel();
        fourniss_adresse = new javax.swing.JLabel();
        fourniss_contact = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        idFournisseur = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listCommande = new javaapp.component.Table();
        modifier_ligne = new javaapp.component.ButtonRadius();
        supprimer_ligne = new javaapp.component.ButtonRadius();
        jLabel18 = new javax.swing.JLabel();
        refReception = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        dateLivraison = new com.toedter.calendar.JDateChooser();
        dateCommande = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        frais = new javax.swing.JSpinner();
        ajouterLigne = new javaapp.component.ButtonRadius();
        idComm = new javax.swing.JLabel();
        refCommande = new javax.swing.JLabel();
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
        jSeparator8 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        totalRemiseField = new javax.swing.JLabel();
        heureLivraison = new javax.swing.JComboBox<>();
        minuteLivraison = new javax.swing.JComboBox<>();
        numRefCommande1 = new javax.swing.JLabel();
        btn_create_BL = new javaapp.component.ButtonRadius();
        btn_create_BL1 = new javaapp.component.ButtonRadius();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Créer un bon de livraison");
        setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(241, 248, 240));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Entreprise");

        e_nom.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_nom.setForeground(new java.awt.Color(43, 40, 54));
        e_nom.setText("LOMELLY ENTREPRISE");

        e_adresse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_adresse.setForeground(new java.awt.Color(43, 40, 54));
        e_adresse.setText("Adresse: Lot/233 Zgjhh");

        e_email.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        e_email.setForeground(new java.awt.Color(43, 40, 54));
        e_email.setText("Email: dohnjjj@gmail.fr");

        jPanel10.setBackground(new java.awt.Color(102, 102, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(0, 4));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(e_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(e_adresse, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(e_nom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_nom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_adresse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(e_email)
                .addGap(21, 21, 21))
        );

        jPanel4.setBackground(new java.awt.Color(241, 248, 240));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Fournisseur n°");

        fourniss_nom.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fourniss_nom.setForeground(new java.awt.Color(43, 40, 54));
        fourniss_nom.setText("Nom complet");

        fourniss_adresse.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fourniss_adresse.setForeground(new java.awt.Color(43, 40, 54));
        fourniss_adresse.setText("Adresse");

        fourniss_contact.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        fourniss_contact.setForeground(new java.awt.Color(43, 40, 54));
        fourniss_contact.setText("Contact");

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

        idFournisseur.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        idFournisseur.setForeground(new java.awt.Color(51, 51, 255));
        idFournisseur.setText("12");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fourniss_contact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fourniss_adresse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idFournisseur, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fourniss_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(idFournisseur))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fourniss_nom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fourniss_adresse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fourniss_contact)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(43, 40, 54));
        jLabel14.setText("Note");

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText("Ligne du bon de réception");

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

        modifier_ligne.setBackground(new java.awt.Color(102, 102, 255));
        modifier_ligne.setBorder(null);
        modifier_ligne.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/iconModify.png"))); // NOI18N
        modifier_ligne.setBorderColor(new java.awt.Color(102, 102, 255));
        modifier_ligne.setColor(new java.awt.Color(102, 102, 255));
        modifier_ligne.setColorClick(new java.awt.Color(153, 153, 255));
        modifier_ligne.setColorOver(new java.awt.Color(51, 51, 255));
        modifier_ligne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifier_ligneActionPerformed(evt);
            }
        });

        supprimer_ligne.setBackground(new java.awt.Color(255, 204, 204));
        supprimer_ligne.setBorder(null);
        supprimer_ligne.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/btnDelete.png"))); // NOI18N
        supprimer_ligne.setBorderColor(new java.awt.Color(255, 204, 204));
        supprimer_ligne.setColor(new java.awt.Color(255, 204, 204));
        supprimer_ligne.setColorClick(new java.awt.Color(255, 204, 204));
        supprimer_ligne.setColorOver(new java.awt.Color(255, 142, 142));
        supprimer_ligne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimer_ligneActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(43, 40, 54));
        jLabel18.setText("Reference");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(43, 40, 54));
        jLabel19.setText("Date prévue");

        dateLivraison.setBackground(new java.awt.Color(255, 255, 255));

        dateCommande.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateCommande.setForeground(new java.awt.Color(43, 40, 54));
        dateCommande.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateCommande.setText("Date: 23/03/2024");

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(43, 40, 54));
        jLabel20.setText("Frais");

        ajouterLigne.setBorder(null);
        ajouterLigne.setForeground(new java.awt.Color(255, 255, 255));
        ajouterLigne.setText("+");
        ajouterLigne.setBorderColor(new java.awt.Color(102, 102, 255));
        ajouterLigne.setColor(new java.awt.Color(102, 102, 255));
        ajouterLigne.setColorClick(new java.awt.Color(153, 153, 255));
        ajouterLigne.setColorOver(new java.awt.Color(51, 51, 255));
        ajouterLigne.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        ajouterLigne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterLigneActionPerformed(evt);
            }
        });

        idComm.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        idComm.setForeground(new java.awt.Color(51, 51, 255));
        idComm.setText("3");

        refCommande.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        refCommande.setForeground(new java.awt.Color(51, 51, 255));
        refCommande.setText("C0001");

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

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(43, 40, 54));
        jLabel16.setText("Remise");

        totalRemiseField.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        totalRemiseField.setForeground(new java.awt.Color(43, 40, 54));
        totalRemiseField.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalRemiseField.setText("0");

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
            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalRemiseField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
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
                    .addComponent(jLabel12)
                    .addComponent(totalTTCField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(totalRemiseField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(totalNetAPayerField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 230, Short.MAX_VALUE)))
        );

        heureLivraison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18" }));

        minuteLivraison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        numRefCommande1.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        numRefCommande1.setForeground(new java.awt.Color(51, 51, 255));
        numRefCommande1.setText("Propriété de la réception");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idComm, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ajouterLigne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modifier_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(supprimer_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addComponent(heureLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(refReception))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(numRefCommande1)
                        .addGap(61, 61, 61)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(dateCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                            .addComponent(frais)))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateCommande)
                    .addComponent(numRefCommande1))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refReception, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(frais, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateLivraison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(heureLivraison)
                        .addComponent(minuteLivraison, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel19))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modifier_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supprimer_ligne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ajouterLigne, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(idComm)
                        .addComponent(refCommande)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        btn_create_BL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_BLActionPerformed(evt);
            }
        });

        btn_create_BL1.setBackground(new java.awt.Color(255, 255, 255));
        btn_create_BL1.setBorder(null);
        btn_create_BL1.setForeground(new java.awt.Color(82, 82, 251));
        btn_create_BL1.setText("Fermer");
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
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1124, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btn_create_BL1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_create_BL, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1124, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void modifier_ligneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifier_ligneActionPerformed
        JFrame frame = new JFrame("Gestion des Commandes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        
        int selectedRow = listCommande.getSelectedRow();
        DefaultTableModel tableModel = (DefaultTableModel) listCommande.getModel();
        if (selectedRow != -1) {
            // Récupérer les valeurs de la ligne sélectionnée
            String designation = (String) tableModel.getValueAt(selectedRow, 0);
            String refProduit = (String) tableModel.getValueAt(selectedRow, 1);
            String puHT = (String) tableModel.getValueAt(selectedRow, 2);
            float remise = (float) tableModel.getValueAt(selectedRow, 4);
            int quantiteCommandee = (int) tableModel.getValueAt(selectedRow, 5);
            int quantiteExpediee = (int) tableModel.getValueAt(selectedRow, 6);
            String entrepot = (String) tableModel.getValueAt(selectedRow, 8);


            // Formulaire de modification
            JTextField refProduitField = new JTextField(refProduit);
            JTextField PUProduitField = new JTextField(puHT);
            JSpinner remiseSpinner = new JSpinner(new SpinnerNumberModel(remise, 0, 100, 1));
            JSpinner quantiteExpedieeSpinner = new JSpinner(new SpinnerNumberModel(quantiteExpediee, 0, quantiteCommandee, 1));
//            String[] entrepots = {"Entrepôt 1", "Entrepôt 2", "Entrepôt 3"};
            JComboBox<String> entrepotComboBox = new JComboBox<>();
//            entrepotComboBox.setSelectedItem(entrepot);

            PUProduitField.setEditable(false);
            refProduitField.setEditable(false);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Référence Produit:"));
            panel.add(refProduitField);
            panel.add(new JLabel("Prix unitaire HT:"));
            panel.add(PUProduitField);
            panel.add(new JLabel("Remise (%):"));
            panel.add(remiseSpinner);
            panel.add(new JLabel("Quantité Expédiée:"));
            panel.add(quantiteExpedieeSpinner);
            panel.add(new JLabel("Entrepôt:"));
            panel.add(entrepotComboBox);
            
//        int selectedRow = listeArticle.getSelectedRow();
        String refProduitSelected = tableModel.getValueAt(selectedRow, 0).toString();
        
        List<MouvementStock> ms = DAOFactory.getSortieStockDAO().find(refProduit);
        entrepotComboBox.removeAllItems();

        for(javaapp.bean.MouvementStock msItem : ms){
            entrepotComboBox.addItem(msItem.getMagasin().getRefMagasin() + " (Stock: " + msItem.getQuantiteStock()+")");
        }
        entrepotComboBox.setSelectedIndex(0);

            panel.add(entrepotComboBox);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Modifier Commande", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
//                if( ms.get( entrepotComboBox.getSelectedIndex() ).getQuantiteStock() >= Integer.parseInt(quantiteExpedieeSpinner.getValue().toString())){
                 // Mettre à jour la ligne avec les nouvelles valeurs
                tableModel.setValueAt(refProduitField.getText(), selectedRow, 1);
                tableModel.setValueAt(Float.parseFloat(remiseSpinner.getValue().toString()), selectedRow, 4);
                tableModel.setValueAt((int) quantiteExpedieeSpinner.getValue(), selectedRow, 6);
            
//                tableModel.setValueAt((int) quantiteExpedieeSpinner.getValue(), selectedRow, 5);
                float res = Integer.parseInt( quantiteExpedieeSpinner.getValue().toString()) * formater.parseFloat(PUProduitField.getText());
                tableModel.setValueAt( formater.formatFloat( res ), selectedRow, 7);
                tableModel.setValueAt(ms.get(entrepotComboBox.getSelectedIndex()).getRefMagasin(), selectedRow, 8);
           
                float totalHTPerItem = formater.parseFloat(puHT) * Integer.parseInt(quantiteExpedieeSpinner.getValue().toString());
            float remiseAmount = totalHTPerItem * (Float.parseFloat( remiseSpinner.getValue().toString()) / 100);
            float totalHTAfterRemise = totalHTPerItem - remiseAmount;
            float tva = Float.parseFloat(tableModel.getValueAt(selectedRow, 3).toString());
            float tvaAmount = totalHTAfterRemise * (tva / 100);
            float totalTTC = totalHTAfterRemise + tvaAmount;

            tableModel.setValueAt(totalHTAfterRemise, selectedRow, 7);

            // Mise à jour des totaux
            updateTotals();
            
//            }else{
//                JOptionPane.showMessageDialog(null, "La ressource demandée est insuffisante pour effectuer cette opération.\nLa quantité diponible dans le magasin [ "+ms.get(entrepotComboBox.getSelectedIndex()).getRefMagasin()+" ] : "+ms.get(entrepotComboBox.getSelectedIndex()).getQuantiteStock(), "Erreur", JOptionPane.ERROR_MESSAGE);
//            }
                
                }
        } else {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } 
        listCommande.clearSelection();
    }//GEN-LAST:event_modifier_ligneActionPerformed

    private void updateTotals() {
    DefaultTableModel model = (DefaultTableModel) listCommande.getModel();

    float total_HT = 0.0f;
    float total_TVA = 0.0f;
    float total_Remise = 0.0f;
    float total_TTC = 0.0f;

    for (int i = 0; i < model.getRowCount(); i++) {
        float puHT = formater.parseFloat((String) model.getValueAt(i, 2));
        float tva = (float) model.getValueAt(i, 3);
        float remise = Float.parseFloat( model.getValueAt(i, 4).toString() );
        int qteAExpedier = (int) model.getValueAt(i, 6);
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
    totalHTField.setText(formater.formatFloat(total_HT));
    totalTVA.setText(formater.formatFloat(total_TVA));
    totalRemiseField.setText(String.valueOf(total_Remise));
    totalTTCField.setText(formater.formatFloat(total_TTC));
    totalNetAPayerField.setText(formater.formatFloat(total_TTC - total_Remise));
}
    
    private void ajouterLigneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajouterLigneActionPerformed
        // Reset entrepotComboBox
//    entrepotComboBox.removeAllItems();
    
    // Première étape : récupérer les informations du produit, de la remise et de la quantité commandée
    JFrame frame = new JFrame("Gestion des Commandes");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);
    
    DefaultTableModel tableModel = (DefaultTableModel) listCommande.getModel();
    JComboBox<String> refProduitField = new JComboBox<>();
    List<Produit> produits = produitDAO.select();
    for (javaapp.bean.Produit produit : produits) {
            refProduitField.addItem(produit.getRefProduit());
    }
    
    JTextField designationField = new JTextField();
    JSpinner quantiteCommandeeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    JSpinner remiseSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
    JTextField puHTField = new JTextField();
    
    JPanel panel1 = new JPanel(new GridLayout(0, 1));
    panel1.add(new JLabel("Référence Produit:"));
    panel1.add(refProduitField);
//    panel1.add(new JLabel("Prix Unitaire HT:"));
//    panel1.add(puHTField);
    panel1.add(new JLabel("Remise (%):"));
    panel1.add(remiseSpinner);
    panel1.add(new JLabel("Quantité Commandée:"));
    panel1.add(quantiteCommandeeSpinner);

    int result1 = JOptionPane.showConfirmDialog(frame, panel1, "Ajouter Ligne - Étape 1", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result1 == JOptionPane.OK_OPTION) {
        // Seconde étape : sélectionner l'entrepôt
        String refProduit = (String) refProduitField.getSelectedItem();
        List<MouvementStock> ms = DAOFactory.getSortieStockDAO().find(refProduit);

        JComboBox<String> entrepotComboBox = new JComboBox<>();
        for (javaapp.bean.MouvementStock msItem : ms) {
            entrepotComboBox.addItem(msItem.getMagasin().getRefMagasin() + " (Stock: " + msItem.getQuantiteStock() + ")");
        }
        entrepotComboBox.setSelectedIndex(0);
        
        JPanel panel2 = new JPanel(new GridLayout(0, 1));
        panel2.add(new JLabel("Entrepôt:"));
        panel2.add(entrepotComboBox);
        
        int result2 = JOptionPane.showConfirmDialog(frame, panel2, "Ajouter Ligne - Étape 2", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result2 == JOptionPane.OK_OPTION) {
            
            Produit produit = produitDAO.find(refProduit);
            float totalHT = produit.getPUHT()+ (produit.getPUHT()*Integer.parseInt(quantiteCommandeeSpinner.getValue().toString())/100);
            Object[] newRow = {
                produit.getDesignation(),
                refProduitField.getSelectedItem().toString(),
                formater.formatFloat(produit.getPUHT()),
                produit.getTVA(), // TVA initialement 0
                (int) remiseSpinner.getValue(),
                (int) quantiteCommandeeSpinner.getValue(),
                (int) quantiteCommandeeSpinner.getValue(), // Quantité Expédiée initialement 0
                formater.formatFloat(totalHT), // Total HT initialement 0
                ms.get(entrepotComboBox.getSelectedIndex()).getRefMagasin()
            };

            tableModel.addRow(newRow);
            updateTotals();
        }
    }
        updateTotals();
    }//GEN-LAST:event_ajouterLigneActionPerformed

    private void supprimer_ligneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimer_ligneActionPerformed
        JFrame frame = new JFrame("Gestion des Commandes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        int selectedRow = listCommande.getSelectedRow();
        DefaultTableModel tableModel = (DefaultTableModel) listCommande.getModel();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ligne à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        listCommande.clearSelection();
        updateTotals();
    }//GEN-LAST:event_supprimer_ligneActionPerformed

    private void btn_create_BLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_BLActionPerformed
        boolean vide = false;
        for(int i = listCommande.getRowCount()-1 ; i >= 0; i--){
            if(Integer.parseInt(listCommande.getModel().getValueAt(i, 6).toString()) == 0 || listCommande.getModel().getValueAt(i, 8).toString().isEmpty()){
                vide= true;
                JOptionPane.showMessageDialog(null, "Veuillez indiquer correctement la quantité à colisser et le magasin servant de dépôt", "Message", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
        if(!vide){
            int i = insererReception();
            insererReceptionDetail(i);
            this.dispose();
        }
    }//GEN-LAST:event_btn_create_BLActionPerformed

    private void btn_create_BL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_create_BL1ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(ModalBonReception.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModalBonReception.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModalBonReception.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModalBonReception.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModalBonReception().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius ajouterLigne;
    private javaapp.component.ButtonRadius btn_create_BL;
    private javaapp.component.ButtonRadius btn_create_BL1;
    private javax.swing.JLabel dateCommande;
    private com.toedter.calendar.JDateChooser dateLivraison;
    private javax.swing.JTextArea description;
    private javax.swing.JLabel e_adresse;
    private javax.swing.JLabel e_email;
    private javax.swing.JLabel e_nom;
    private javax.swing.JLabel fourniss_adresse;
    private javax.swing.JLabel fourniss_contact;
    private javax.swing.JLabel fourniss_nom;
    private javax.swing.JSpinner frais;
    private javax.swing.JComboBox<String> heureLivraison;
    private javax.swing.JLabel idComm;
    private javax.swing.JLabel idFournisseur;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javaapp.component.Table listCommande;
    private javax.swing.JComboBox<String> minuteLivraison;
    private javaapp.component.ButtonRadius modifier_ligne;
    private javax.swing.JLabel numRefCommande1;
    private javax.swing.JLabel refCommande;
    private javax.swing.JTextField refReception;
    private javaapp.component.ButtonRadius supprimer_ligne;
    private javax.swing.JLabel totalHTField;
    private javax.swing.JLabel totalNetAPayerField;
    private javax.swing.JLabel totalRemiseField;
    private javax.swing.JLabel totalTTCField;
    private javax.swing.JLabel totalTVA;
    // End of variables declaration//GEN-END:variables

    private void insererMouvementStock(int idProduit, int quantite, int idMagasin, int generatedKey, String valide) {
        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        MouvementStock mouvement = new MouvementStock();
        mouvement.setDescription("Expédition du produit "+idProduit);
        mouvement.setIdProduit(idProduit);
        mouvement.setQuantite(quantite);
        mouvement.setDateMouvement(now.format(dtFormat));
        mouvement.setIdMagasin(idMagasin);
        mouvement.setIdSourceEntree(generatedKey);
        mouvement.setEstValide(valide);
        
        DAOFactory.getMouvementStockDAO().createIN(mouvement);
        
    }
    
}
