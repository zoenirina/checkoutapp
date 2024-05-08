
package javaapp.page;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.UserType;
import javaapp.component.ButtonRadius;
import javaapp.component.FormatUtils;
/**
 *
 * @author ZOENIRINA
 */
public class Home extends javax.swing.JPanel {
public String title="Acceuil";
Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public PreparedStatement stm ;
    public ResultSet resultSet;
    private FormatUtils formater;

    public Home(UserType usertype) {
        initComponents();
        formater= new FormatUtils();
        initTransaction(usertype);
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
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void initTransaction(UserType usertype){
     sayHello.setText("Salut, "+usertype.getUsername()+"!");
     getSoldeInitiale();
     connectiondb();
     try{
        stm= conn.prepareStatement("select 'Entrée' as type, montant, nomUtilisateur, dateEntree as dateTransaction FROM entree_caisse,utilisateur where idExercice in (select id from exercice where status=1) and entree_caisse.idCreateur=utilisateur.idUtilisateur UNION SELECT 'Sortie' as type,montant, nomUtilisateur, dateSortie as dateTransaction FROM sortie_caisse,utilisateur  where idExercice in (select id from exercice where status=1) and sortie_caisse.idCreateur=utilisateur.idUtilisateur ORDER BY dateTransaction DESC LIMIT 4");
        resultSet = stm.executeQuery();
//        String[] data= new String[4];
        int i=0;
        while(resultSet.next()){
//           data[0]=resultSet.getString("type");
//           data[1]=resultSet.getString("montant");
//           data[2]=resultSet.getString("idCreateur");
//           data[3]=resultSet.getString("dateTransaction");
           
           switch(i){
            case 0:
//            type1.setText(resultSet.getString("type"));
            changebtn(type1, resultSet.getString("type"));
//            if("Entrée".equals(resultSet.getString("type")))transac1.setBackground(Color.decode("#6BFF6B"));
//            if("Sortie".equals(resultSet.getString("type")))transac1.setBackground(Color.decode("#FF6666"));
            montant1.setText(formater.formatFloat(resultSet.getFloat("montant")));
//            System.out.print(formater.parseFloat(formater.formatFloat(resultSet.getFloat("montant"))));
            idCreateur1.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction1.setText(resultSet.getString("dateTransaction"));
            break;
            
            case 1:
//            type2.setText(resultSet.getString("type"));
            changebtn(type2, resultSet.getString("type"));
            montant2.setText(formater.formatFloat(resultSet.getFloat("montant")));
            idCreateur2.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction2.setText(resultSet.getString("dateTransaction"));
            break;
            
            case 2:
//            type3.setText(resultSet.getString("type"));
            changebtn(type3, resultSet.getString("type"));
            montant3.setText(formater.formatFloat(resultSet.getFloat("montant")));
            idCreateur3.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction3.setText(resultSet.getString("dateTransaction"));
            break;
            case 3:
//            type4.setText(resultSet.getString("type"));
            changebtn(type4, resultSet.getString("type"));
            montant4.setText(formater.formatFloat(resultSet.getFloat("montant")));
            idCreateur4.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction4.setText(resultSet.getString("dateTransaction"));
            break;
        }
           i=i+1;
           
//           model.addRow(data);
       }
        resultSet.close();
        stm.close();
        conn.close();
//        JOptionPane.showMessageDialog(null, "modification réussie!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
        }catch( SQLException e){
        e.printStackTrace(); ;}
     }
     
     private void changebtn(ButtonRadius btn, String type){
         btn.setColor(Color.decode(("Entrée".equals(type))?"#C5FFC5":"#E3E3FC"));
         btn.setBackground(Color.decode(("Entrée".equals(type))?"#C5FFC5":"#E3E3FC"));
         btn.setColorOver(Color.decode(("Entrée".equals(type))?"#C5FFC5":"#E3E3FC"));
        btn.setForeground(Color.decode(("Entrée".equals(type))?"#009933":"#6666FF"));
         btn.setText(type);
     }
     
     private void getSoldeInitiale(){
          try {
                connectiondb();
                stm = conn.prepareStatement("SELECT (select soldeInitiale from exercice where status=1)+(SELECT sum(entree_caisse.montant) FROM entree_caisse, exercice  where entree_caisse.idExercice=exercice.id and exercice.status=1 ) - (SELECT sum(sortie_caisse.montant) FROM sortie_caisse, exercice  where sortie_caisse.idExercice=exercice.id and exercice.status=1 )  as soldeInitiale");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    soldeActuel.setText(formater.formatFloat(resultSet.getFloat("soldeInitiale"))+" Ar");
                }
                stm = conn.prepareStatement("SELECT soldeInitiale from exercice where status=1");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    soldeInit.setText(formater.formatFloat(resultSet.getFloat("soldeInitiale"))+" Ar");
                }
//                stm = conn.prepareStatement("SELECT count(id) as nbr_magasin from magasin");
//                resultSet = stm.executeQuery();
//                while (resultSet.next()) {
//                    nbr_magasin.setText(resultSet.getString("nbr_magasin"));
//                }
                resultSet.close();
                stm.close();
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound1 = new javaapp.component.PanelBorderRound();
        transac1 = new javaapp.component.PanelBorderRound();
        montant1 = new javax.swing.JLabel();
        idCreateur1 = new javax.swing.JLabel();
        dateTransaction1 = new javax.swing.JLabel();
        type1 = new javaapp.component.ButtonRadius();
        transac2 = new javaapp.component.PanelBorderRound();
        montant2 = new javax.swing.JLabel();
        dateTransaction2 = new javax.swing.JLabel();
        idCreateur2 = new javax.swing.JLabel();
        type2 = new javaapp.component.ButtonRadius();
        transac3 = new javaapp.component.PanelBorderRound();
        dateTransaction3 = new javax.swing.JLabel();
        idCreateur3 = new javax.swing.JLabel();
        montant3 = new javax.swing.JLabel();
        type3 = new javaapp.component.ButtonRadius();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        transac4 = new javaapp.component.PanelBorderRound();
        montant4 = new javax.swing.JLabel();
        dateTransaction4 = new javax.swing.JLabel();
        idCreateur4 = new javax.swing.JLabel();
        type4 = new javaapp.component.ButtonRadius();
        panelBorderRound3 = new javaapp.component.PanelBorderRound();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        card1 = new javaapp.component.PanelBorderRound();
        jLabel6 = new javax.swing.JLabel();
        soldeActuel = new javax.swing.JLabel();
        buttonRadius2 = new javaapp.component.ButtonRadius();
        sayHello = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelBorderRound2 = new javaapp.component.PanelBorderRound();
        jLabel9 = new javax.swing.JLabel();
        btn_search = new javaapp.component.ButtonRadius();
        jComboBox1 = new javax.swing.JComboBox<>();
        panelBorderRound4 = new javaapp.component.PanelBorderRound();
        buttonRadius4 = new javaapp.component.ButtonRadius();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelBorderRound5 = new javaapp.component.PanelBorderRound();
        buttonRadius5 = new javaapp.component.ButtonRadius();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelBorderRound6 = new javaapp.component.PanelBorderRound();
        buttonRadius6 = new javaapp.component.ButtonRadius();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        card4 = new javaapp.component.PanelBorderRound();
        jLabel11 = new javax.swing.JLabel();
        soldeInit = new javax.swing.JLabel();
        buttonRadius7 = new javaapp.component.ButtonRadius();
        card5 = new javaapp.component.PanelBorderRound();
        jLabel24 = new javax.swing.JLabel();
        soldeInit2 = new javax.swing.JLabel();
        buttonRadius8 = new javaapp.component.ButtonRadius();
        card6 = new javaapp.component.PanelBorderRound();
        jLabel25 = new javax.swing.JLabel();
        soldeInit3 = new javax.swing.JLabel();
        buttonRadius9 = new javaapp.component.ButtonRadius();

        setBackground(new java.awt.Color(238, 238, 238));
        setPreferredSize(new java.awt.Dimension(1020, 696));

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        transac1.setBackground(new java.awt.Color(241, 248, 240));
        transac1.setRadius(5);

        montant1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant1.setText("------");

        idCreateur1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur1.setText("-");

        dateTransaction1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction1.setText("---- -- -- --:--:--");

        type1.setBackground(new java.awt.Color(197, 255, 197));
        type1.setForeground(new java.awt.Color(0, 153, 51));
        type1.setText("Entrée");
        type1.setBorderColor(new java.awt.Color(241, 248, 240));
        type1.setColor(new java.awt.Color(197, 255, 197));
        type1.setColorClick(new java.awt.Color(247, 251, 246));
        type1.setColorOver(new java.awt.Color(197, 255, 197));
        type1.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        type1.setPreferredSize(new java.awt.Dimension(81, 30));
        type1.setRadius(20);

        javax.swing.GroupLayout transac1Layout = new javax.swing.GroupLayout(transac1);
        transac1.setLayout(transac1Layout);
        transac1Layout.setHorizontalGroup(
            transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(montant1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idCreateur1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        transac1Layout.setVerticalGroup(
            transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCreateur1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant1)
                    .addComponent(dateTransaction1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        transac2.setBackground(new java.awt.Color(241, 248, 240));
        transac2.setRadius(6);

        montant2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant2.setText("------");

        dateTransaction2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction2.setText("---- -- -- --:--:--");

        idCreateur2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur2.setText("-");

        type2.setBackground(new java.awt.Color(197, 255, 197));
        type2.setForeground(new java.awt.Color(0, 153, 51));
        type2.setText("Entrée");
        type2.setBorderColor(new java.awt.Color(241, 248, 240));
        type2.setColor(new java.awt.Color(197, 255, 197));
        type2.setColorClick(new java.awt.Color(247, 251, 246));
        type2.setColorOver(new java.awt.Color(197, 255, 197));
        type2.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        type2.setPreferredSize(new java.awt.Dimension(81, 30));
        type2.setRadius(20);

        javax.swing.GroupLayout transac2Layout = new javax.swing.GroupLayout(transac2);
        transac2.setLayout(transac2Layout);
        transac2Layout.setHorizontalGroup(
            transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(montant2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(idCreateur2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        transac2Layout.setVerticalGroup(
            transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCreateur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant2)
                    .addComponent(dateTransaction2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        transac3.setBackground(new java.awt.Color(241, 248, 240));
        transac3.setRadius(6);

        dateTransaction3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction3.setText("---- -- -- --:--:--");

        idCreateur3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur3.setText("-");

        montant3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant3.setText("------");

        type3.setBackground(new java.awt.Color(197, 255, 197));
        type3.setForeground(new java.awt.Color(0, 153, 51));
        type3.setText("Entrée");
        type3.setBorderColor(new java.awt.Color(241, 248, 240));
        type3.setColor(new java.awt.Color(197, 255, 197));
        type3.setColorClick(new java.awt.Color(247, 251, 246));
        type3.setColorOver(new java.awt.Color(197, 255, 197));
        type3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        type3.setPreferredSize(new java.awt.Dimension(81, 30));
        type3.setRadius(20);

        javax.swing.GroupLayout transac3Layout = new javax.swing.GroupLayout(transac3);
        transac3.setLayout(transac3Layout);
        transac3Layout.setHorizontalGroup(
            transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(montant3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(idCreateur3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        transac3Layout.setVerticalGroup(
            transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCreateur3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant3)
                    .addComponent(dateTransaction3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Type");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Créateur");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Montant");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Date de transaction");

        jLabel13.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(36, 36, 36));
        jLabel13.setText("Historique des 4 dernières transactions");

        transac4.setBackground(new java.awt.Color(241, 248, 240));
        transac4.setRadius(6);

        montant4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant4.setText("------");

        dateTransaction4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction4.setText("---- -- -- --:--:--");

        idCreateur4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur4.setText("-");

        type4.setForeground(new java.awt.Color(102, 102, 255));
        type4.setText("Sortie");
        type4.setBorderColor(new java.awt.Color(241, 248, 240));
        type4.setColor(new java.awt.Color(197, 255, 197));
        type4.setColorClick(new java.awt.Color(247, 251, 246));
        type4.setColorOver(new java.awt.Color(197, 255, 197));
        type4.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        type4.setPreferredSize(new java.awt.Dimension(81, 30));
        type4.setRadius(20);

        javax.swing.GroupLayout transac4Layout = new javax.swing.GroupLayout(transac4);
        transac4.setLayout(transac4Layout);
        transac4Layout.setHorizontalGroup(
            transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(montant4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(idCreateur4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTransaction4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        transac4Layout.setVerticalGroup(
            transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCreateur4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant4)
                    .addComponent(dateTransaction4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelBorderRound3.setBackground(new java.awt.Color(102, 102, 255));

        jLabel14.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("(5) Factures à payer ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("20000 Ar");

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/PROD.png"))); // NOI18N
        jLabel23.setText("jLabel23");

        javax.swing.GroupLayout panelBorderRound3Layout = new javax.swing.GroupLayout(panelBorderRound3);
        panelBorderRound3.setLayout(panelBorderRound3Layout);
        panelBorderRound3Layout.setHorizontalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorderRound3Layout.setVerticalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorderRound3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13)
                            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel10))
                    .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(transac3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(transac2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(transac1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(transac4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBorderRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderRound1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(panelBorderRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transac1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(transac2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(transac3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(transac4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        card1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(141, 141, 223));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Profit");

        soldeActuel.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        soldeActuel.setForeground(new java.awt.Color(102, 102, 102));
        soldeActuel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        soldeActuel.setText("34000 Ar");

        buttonRadius2.setBackground(new java.awt.Color(255, 255, 255));
        buttonRadius2.setBorder(null);
        buttonRadius2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/profit.png"))); // NOI18N
        buttonRadius2.setBorderColor(new java.awt.Color(255, 255, 255));
        buttonRadius2.setColor(new java.awt.Color(255, 255, 255));
        buttonRadius2.setColorClick(new java.awt.Color(255, 255, 255));
        buttonRadius2.setColorOver(new java.awt.Color(255, 255, 255));
        buttonRadius2.setRadius(15);

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(soldeActuel, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(buttonRadius2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(buttonRadius2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soldeActuel)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        sayHello.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        sayHello.setForeground(new java.awt.Color(141, 141, 223));
        sayHello.setText("Salut, Solofo!");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(119, 119, 119));
        jLabel2.setText("Voici quelques chiffres clés à propos de votre Business.");

        panelBorderRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelBorderRound2.setPreferredSize(new java.awt.Dimension(388, 489));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Top 3 des produits  les plus vendues");

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ce mois", "Ces 3 derniers mois", "Cet année" }));

        panelBorderRound4.setBackground(new java.awt.Color(241, 248, 240));

        buttonRadius4.setBackground(new java.awt.Color(102, 102, 255));
        buttonRadius4.setText("1");
        buttonRadius4.setBorderColor(new java.awt.Color(247, 251, 246));
        buttonRadius4.setColor(new java.awt.Color(102, 102, 255));
        buttonRadius4.setColorClick(new java.awt.Color(102, 102, 255));
        buttonRadius4.setColorOver(new java.awt.Color(102, 102, 255));
        buttonRadius4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        buttonRadius4.setRadius(40);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(88, 93, 96));
        jLabel12.setText("Nom du produit");

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 255));
        jLabel15.setText("Reference");

        jLabel16.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("55");

        javax.swing.GroupLayout panelBorderRound4Layout = new javax.swing.GroupLayout(panelBorderRound4);
        panelBorderRound4.setLayout(panelBorderRound4Layout);
        panelBorderRound4Layout.setHorizontalGroup(
            panelBorderRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound4Layout.createSequentialGroup()
                .addGroup(panelBorderRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(buttonRadius4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound4Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelBorderRound4Layout.setVerticalGroup(
            panelBorderRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBorderRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRadius4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderRound4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        panelBorderRound5.setBackground(new java.awt.Color(241, 248, 240));

        buttonRadius5.setBackground(new java.awt.Color(20, 237, 116));
        buttonRadius5.setText("2");
        buttonRadius5.setBorderColor(new java.awt.Color(247, 251, 246));
        buttonRadius5.setColor(new java.awt.Color(20, 237, 116));
        buttonRadius5.setColorClick(new java.awt.Color(20, 237, 116));
        buttonRadius5.setColorOver(new java.awt.Color(20, 237, 116));
        buttonRadius5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        buttonRadius5.setRadius(40);

        jLabel17.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(88, 93, 96));
        jLabel17.setText("Nom du produit");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(20, 237, 116));
        jLabel18.setText("Reference");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(20, 237, 116));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("55");

        javax.swing.GroupLayout panelBorderRound5Layout = new javax.swing.GroupLayout(panelBorderRound5);
        panelBorderRound5.setLayout(panelBorderRound5Layout);
        panelBorderRound5Layout.setHorizontalGroup(
            panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound5Layout.createSequentialGroup()
                .addGroup(panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(buttonRadius5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound5Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelBorderRound5Layout.setVerticalGroup(
            panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBorderRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRadius5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderRound5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        panelBorderRound6.setBackground(new java.awt.Color(241, 248, 240));

        buttonRadius6.setBackground(new java.awt.Color(248, 91, 50));
        buttonRadius6.setText("3");
        buttonRadius6.setBorderColor(new java.awt.Color(247, 251, 246));
        buttonRadius6.setColor(new java.awt.Color(248, 91, 50));
        buttonRadius6.setColorClick(new java.awt.Color(248, 91, 50));
        buttonRadius6.setColorOver(new java.awt.Color(248, 91, 50));
        buttonRadius6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        buttonRadius6.setRadius(40);

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(88, 93, 96));
        jLabel20.setText("Nom du produit");

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(248, 91, 50));
        jLabel21.setText("Reference");

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(248, 91, 50));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("55");

        javax.swing.GroupLayout panelBorderRound6Layout = new javax.swing.GroupLayout(panelBorderRound6);
        panelBorderRound6.setLayout(panelBorderRound6Layout);
        panelBorderRound6Layout.setHorizontalGroup(
            panelBorderRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound6Layout.createSequentialGroup()
                .addGroup(panelBorderRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(buttonRadius6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorderRound6Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelBorderRound6Layout.setVerticalGroup(
            panelBorderRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelBorderRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRadius6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBorderRound6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20))
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout panelBorderRound2Layout = new javax.swing.GroupLayout(panelBorderRound2);
        panelBorderRound2.setLayout(panelBorderRound2Layout);
        panelBorderRound2Layout.setHorizontalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelBorderRound2Layout.createSequentialGroup()
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelBorderRound6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorderRound5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorderRound4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        panelBorderRound2Layout.setVerticalGroup(
            panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelBorderRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(30, 30, 30)
                .addComponent(panelBorderRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorderRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorderRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        card4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(20, 237, 116));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Solde initiale");

        soldeInit.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        soldeInit.setForeground(new java.awt.Color(102, 102, 102));
        soldeInit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        soldeInit.setText("34000 Ar");

        buttonRadius7.setBackground(new java.awt.Color(255, 255, 255));
        buttonRadius7.setBorder(null);
        buttonRadius7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/soldeActuel.png"))); // NOI18N
        buttonRadius7.setBorderColor(new java.awt.Color(255, 255, 255));
        buttonRadius7.setColor(new java.awt.Color(255, 255, 255));
        buttonRadius7.setColorClick(new java.awt.Color(255, 255, 255));
        buttonRadius7.setColorOver(new java.awt.Color(255, 255, 255));
        buttonRadius7.setRadius(15);

        javax.swing.GroupLayout card4Layout = new javax.swing.GroupLayout(card4);
        card4.setLayout(card4Layout);
        card4Layout.setHorizontalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(soldeInit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addComponent(buttonRadius7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        card4Layout.setVerticalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(buttonRadius7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soldeInit)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        card5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 204, 0));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Transaction");

        soldeInit2.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        soldeInit2.setForeground(new java.awt.Color(102, 102, 102));
        soldeInit2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        soldeInit2.setText("33");

        buttonRadius8.setBackground(new java.awt.Color(255, 255, 255));
        buttonRadius8.setBorder(null);
        buttonRadius8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/transaction.png"))); // NOI18N
        buttonRadius8.setBorderColor(new java.awt.Color(255, 255, 255));
        buttonRadius8.setColor(new java.awt.Color(255, 255, 255));
        buttonRadius8.setColorClick(new java.awt.Color(255, 255, 255));
        buttonRadius8.setColorOver(new java.awt.Color(255, 255, 255));
        buttonRadius8.setRadius(15);

        javax.swing.GroupLayout card5Layout = new javax.swing.GroupLayout(card5);
        card5.setLayout(card5Layout);
        card5Layout.setHorizontalGroup(
            card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card5Layout.createSequentialGroup()
                .addGroup(card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(soldeInit2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(card5Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(buttonRadius8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        card5Layout.setVerticalGroup(
            card5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(buttonRadius8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soldeInit2)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        card6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(247, 108, 72));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Total de commande");

        soldeInit3.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        soldeInit3.setForeground(new java.awt.Color(102, 102, 102));
        soldeInit3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        soldeInit3.setText("55");

        buttonRadius9.setBackground(new java.awt.Color(255, 255, 255));
        buttonRadius9.setBorder(null);
        buttonRadius9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/commande1.png"))); // NOI18N
        buttonRadius9.setBorderColor(new java.awt.Color(255, 255, 255));
        buttonRadius9.setColor(new java.awt.Color(255, 255, 255));
        buttonRadius9.setColorClick(new java.awt.Color(255, 255, 255));
        buttonRadius9.setColorOver(new java.awt.Color(255, 255, 255));
        buttonRadius9.setRadius(15);

        javax.swing.GroupLayout card6Layout = new javax.swing.GroupLayout(card6);
        card6.setLayout(card6Layout);
        card6Layout.setHorizontalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(soldeInit3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonRadius9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );
        card6Layout.setVerticalGroup(
            card6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(buttonRadius9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soldeInit3)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(sayHello, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sayHello, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(card5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(card6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                            .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed

    }//GEN-LAST:event_btn_searchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius btn_search;
    private javaapp.component.ButtonRadius buttonRadius2;
    private javaapp.component.ButtonRadius buttonRadius4;
    private javaapp.component.ButtonRadius buttonRadius5;
    private javaapp.component.ButtonRadius buttonRadius6;
    private javaapp.component.ButtonRadius buttonRadius7;
    private javaapp.component.ButtonRadius buttonRadius8;
    private javaapp.component.ButtonRadius buttonRadius9;
    private javaapp.component.PanelBorderRound card1;
    private javaapp.component.PanelBorderRound card4;
    private javaapp.component.PanelBorderRound card5;
    private javaapp.component.PanelBorderRound card6;
    private javax.swing.JLabel dateTransaction1;
    private javax.swing.JLabel dateTransaction2;
    private javax.swing.JLabel dateTransaction3;
    private javax.swing.JLabel dateTransaction4;
    private javax.swing.JLabel idCreateur1;
    private javax.swing.JLabel idCreateur2;
    private javax.swing.JLabel idCreateur3;
    private javax.swing.JLabel idCreateur4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel montant1;
    private javax.swing.JLabel montant2;
    private javax.swing.JLabel montant3;
    private javax.swing.JLabel montant4;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javaapp.component.PanelBorderRound panelBorderRound2;
    private javaapp.component.PanelBorderRound panelBorderRound3;
    private javaapp.component.PanelBorderRound panelBorderRound4;
    private javaapp.component.PanelBorderRound panelBorderRound5;
    private javaapp.component.PanelBorderRound panelBorderRound6;
    private javax.swing.JLabel sayHello;
    private javax.swing.JLabel soldeActuel;
    private javax.swing.JLabel soldeInit;
    private javax.swing.JLabel soldeInit2;
    private javax.swing.JLabel soldeInit3;
    private javaapp.component.PanelBorderRound transac1;
    private javaapp.component.PanelBorderRound transac2;
    private javaapp.component.PanelBorderRound transac3;
    private javaapp.component.PanelBorderRound transac4;
    private javaapp.component.ButtonRadius type1;
    private javaapp.component.ButtonRadius type2;
    private javaapp.component.ButtonRadius type3;
    private javaapp.component.ButtonRadius type4;
    // End of variables declaration//GEN-END:variables
}
