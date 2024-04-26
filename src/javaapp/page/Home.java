
package javaapp.page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapp.UserType;
import javaapp.component.Toast;

public class Home extends javax.swing.JPanel {
public String title="Acceuil";
Connection conn = null;
    public String url = "jdbc:sqlite:caisse.db";
    public String query=null;
    public PreparedStatement stm ;
    public ResultSet resultSet;
    
//    public Home() {
//        initComponents();
//        initTransaction();
//    }

    public Home(UserType usertype) {
        initComponents();
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
            type1.setText(resultSet.getString("type"));
            montant1.setText(resultSet.getString("montant"));
            idCreateur1.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction1.setText(resultSet.getString("dateTransaction"));
            break;
            
            case 1:
            type2.setText(resultSet.getString("type"));
            montant2.setText(resultSet.getString("montant"));
            idCreateur2.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction2.setText(resultSet.getString("dateTransaction"));
            break;
            
            case 2:
            type3.setText(resultSet.getString("type"));
            montant3.setText(resultSet.getString("montant"));
            idCreateur3.setText(resultSet.getString("nomUtilisateur"));
            dateTransaction3.setText(resultSet.getString("dateTransaction"));
            break;
            case 3:
            type4.setText(resultSet.getString("type"));
            montant4.setText(resultSet.getString("montant"));
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
     
     private void getSoldeInitiale(){
          try {
                connectiondb();
                stm = conn.prepareStatement("SELECT (select soldeInitiale from exercice where status=1)+(SELECT sum(entree_caisse.montant) FROM entree_caisse, exercice  where entree_caisse.idExercice=exercice.id and exercice.status=1 ) - (SELECT sum(sortie_caisse.montant) FROM sortie_caisse, exercice  where sortie_caisse.idExercice=exercice.id and exercice.status=1 )  as soldeInitiale");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    soldeActuel.setText(resultSet.getString("soldeInitiale")+" Ar");
                }
                stm = conn.prepareStatement("SELECT soldeInitiale from exercice where status=1");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    soldeInit.setText(resultSet.getString("soldeInitiale")+" Ar");
                }
                stm = conn.prepareStatement("SELECT count(id) as nbr_magasin from magasin");
                resultSet = stm.executeQuery();
                while (resultSet.next()) {
                    nbr_magasin.setText(resultSet.getString("nbr_magasin"));
                }
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
        type1 = new javax.swing.JLabel();
        montant1 = new javax.swing.JLabel();
        idCreateur1 = new javax.swing.JLabel();
        dateTransaction1 = new javax.swing.JLabel();
        transac2 = new javaapp.component.PanelBorderRound();
        montant2 = new javax.swing.JLabel();
        type2 = new javax.swing.JLabel();
        dateTransaction2 = new javax.swing.JLabel();
        idCreateur2 = new javax.swing.JLabel();
        transac3 = new javaapp.component.PanelBorderRound();
        dateTransaction3 = new javax.swing.JLabel();
        idCreateur3 = new javax.swing.JLabel();
        montant3 = new javax.swing.JLabel();
        type3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        transac4 = new javaapp.component.PanelBorderRound();
        montant4 = new javax.swing.JLabel();
        type4 = new javax.swing.JLabel();
        dateTransaction4 = new javax.swing.JLabel();
        idCreateur4 = new javax.swing.JLabel();
        card1 = new javaapp.component.PanelBorderRound();
        jLabel6 = new javax.swing.JLabel();
        soldeInit = new javax.swing.JLabel();
        buttonRadius2 = new javaapp.component.ButtonRadius();
        card2 = new javaapp.component.PanelBorderRound();
        jLabel7 = new javax.swing.JLabel();
        soldeActuel = new javax.swing.JLabel();
        buttonRadius1 = new javaapp.component.ButtonRadius();
        card3 = new javaapp.component.PanelBorderRound();
        jLabel8 = new javax.swing.JLabel();
        nbr_magasin = new javax.swing.JLabel();
        buttonRadius3 = new javaapp.component.ButtonRadius();
        sayHello = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 236, 243));
        setPreferredSize(new java.awt.Dimension(1020, 696));

        panelBorderRound1.setBackground(new java.awt.Color(255, 255, 255));

        transac1.setBackground(new java.awt.Color(193, 228, 255));

        type1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        type1.setText("-----");

        montant1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant1.setText("------");

        idCreateur1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur1.setText("-");

        dateTransaction1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction1.setText("---- -- -- --:--:--");

        javax.swing.GroupLayout transac1Layout = new javax.swing.GroupLayout(transac1);
        transac1.setLayout(transac1Layout);
        transac1Layout.setHorizontalGroup(
            transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(montant1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(idCreateur1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                .addComponent(dateTransaction1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        transac1Layout.setVerticalGroup(
            transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(transac1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCreateur1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant1)
                    .addComponent(dateTransaction1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        transac2.setBackground(new java.awt.Color(246, 246, 246));

        montant2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant2.setText("------");

        type2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        type2.setText("-----");

        dateTransaction2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction2.setText("---- -- -- --:--:--");

        idCreateur2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur2.setText("-");

        javax.swing.GroupLayout transac2Layout = new javax.swing.GroupLayout(transac2);
        transac2.setLayout(transac2Layout);
        transac2Layout.setHorizontalGroup(
            transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(montant2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(idCreateur2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        transac2Layout.setVerticalGroup(
            transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(transac2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCreateur2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant2)
                    .addComponent(dateTransaction2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        transac3.setBackground(new java.awt.Color(193, 228, 255));

        dateTransaction3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction3.setText("---- -- -- --:--:--");

        idCreateur3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur3.setText("-");

        montant3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant3.setText("------");

        type3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        type3.setText("-----");

        javax.swing.GroupLayout transac3Layout = new javax.swing.GroupLayout(transac3);
        transac3.setLayout(transac3Layout);
        transac3Layout.setHorizontalGroup(
            transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(montant3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(idCreateur3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        transac3Layout.setVerticalGroup(
            transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(transac3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCreateur3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant3)
                    .addComponent(dateTransaction3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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

        transac4.setBackground(new java.awt.Color(246, 246, 246));

        montant4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        montant4.setText("------");

        type4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        type4.setText("-----");

        dateTransaction4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        dateTransaction4.setText("---- -- -- --:--:--");

        idCreateur4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        idCreateur4.setText("-");

        javax.swing.GroupLayout transac4Layout = new javax.swing.GroupLayout(transac4);
        transac4.setLayout(transac4Layout);
        transac4Layout.setHorizontalGroup(
            transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162)
                .addComponent(montant4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addComponent(idCreateur4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateTransaction4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        transac4Layout.setVerticalGroup(
            transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transac4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(transac4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(type4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idCreateur4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(montant4)
                    .addComponent(dateTransaction4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout panelBorderRound1Layout = new javax.swing.GroupLayout(panelBorderRound1);
        panelBorderRound1.setLayout(panelBorderRound1Layout);
        panelBorderRound1Layout.setHorizontalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181)
                        .addComponent(jLabel5)
                        .addGap(149, 149, 149)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(130, 130, 130))
                    .addGroup(panelBorderRound1Layout.createSequentialGroup()
                        .addGroup(panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(transac4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(transac2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(transac1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(transac3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelBorderRound1Layout.setVerticalGroup(
            panelBorderRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderRound1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel13)
                .addGap(32, 32, 32)
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
                .addGap(18, 18, 18)
                .addComponent(transac4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        card1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Solde initiale");

        soldeInit.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        soldeInit.setForeground(new java.awt.Color(51, 51, 51));
        soldeInit.setText("34000 Ar");

        buttonRadius2.setBackground(new java.awt.Color(254, 184, 72));
        buttonRadius2.setBorder(null);
        buttonRadius2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/soldeInitial.png"))); // NOI18N
        buttonRadius2.setBorderColor(new java.awt.Color(254, 184, 72));
        buttonRadius2.setColor(new java.awt.Color(254, 184, 72));
        buttonRadius2.setColorClick(new java.awt.Color(254, 184, 72));
        buttonRadius2.setColorOver(new java.awt.Color(254, 184, 72));
        buttonRadius2.setRadius(50);

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(buttonRadius2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soldeInit, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRadius2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addComponent(soldeInit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        card2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Solde actuel");

        soldeActuel.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        soldeActuel.setForeground(new java.awt.Color(51, 51, 51));
        soldeActuel.setText("40");

        buttonRadius1.setBackground(new java.awt.Color(107, 255, 107));
        buttonRadius1.setBorder(null);
        buttonRadius1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/soldeActuel.png"))); // NOI18N
        buttonRadius1.setBorderColor(new java.awt.Color(107, 255, 107));
        buttonRadius1.setColor(new java.awt.Color(107, 255, 107));
        buttonRadius1.setColorClick(new java.awt.Color(107, 255, 107));
        buttonRadius1.setColorOver(new java.awt.Color(107, 255, 107));
        buttonRadius1.setRadius(50);

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(buttonRadius1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(soldeActuel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card2Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRadius1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(card2Layout.createSequentialGroup()
                        .addComponent(soldeActuel)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        card3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Magasin");

        nbr_magasin.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        nbr_magasin.setForeground(new java.awt.Color(51, 51, 51));
        nbr_magasin.setText("3");

        buttonRadius3.setBackground(new java.awt.Color(248, 91, 50));
        buttonRadius3.setBorder(null);
        buttonRadius3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapp/icon/pointdevente.png"))); // NOI18N
        buttonRadius3.setBorderColor(new java.awt.Color(248, 91, 50));
        buttonRadius3.setColor(new java.awt.Color(248, 91, 50));
        buttonRadius3.setColorClick(new java.awt.Color(248, 91, 50));
        buttonRadius3.setColorOver(new java.awt.Color(248, 91, 50));
        buttonRadius3.setRadius(50);

        javax.swing.GroupLayout card3Layout = new javax.swing.GroupLayout(card3);
        card3.setLayout(card3Layout);
        card3Layout.setHorizontalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(buttonRadius3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nbr_magasin, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        card3Layout.setVerticalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addGroup(card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(nbr_magasin)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(card3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(buttonRadius3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sayHello.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        sayHello.setForeground(new java.awt.Color(51, 51, 51));
        sayHello.setText("Salut, Solofo!");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Voici quelques chiffres clés à propos de votre Business.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(card3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(sayHello, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBorderRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sayHello, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(card2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(card1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(card3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(panelBorderRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.ButtonRadius buttonRadius1;
    private javaapp.component.ButtonRadius buttonRadius2;
    private javaapp.component.ButtonRadius buttonRadius3;
    private javaapp.component.PanelBorderRound card1;
    private javaapp.component.PanelBorderRound card2;
    private javaapp.component.PanelBorderRound card3;
    private javax.swing.JLabel dateTransaction1;
    private javax.swing.JLabel dateTransaction2;
    private javax.swing.JLabel dateTransaction3;
    private javax.swing.JLabel dateTransaction4;
    private javax.swing.JLabel idCreateur1;
    private javax.swing.JLabel idCreateur2;
    private javax.swing.JLabel idCreateur3;
    private javax.swing.JLabel idCreateur4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel montant1;
    private javax.swing.JLabel montant2;
    private javax.swing.JLabel montant3;
    private javax.swing.JLabel montant4;
    private javax.swing.JLabel nbr_magasin;
    private javaapp.component.PanelBorderRound panelBorderRound1;
    private javax.swing.JLabel sayHello;
    private javax.swing.JLabel soldeActuel;
    private javax.swing.JLabel soldeInit;
    private javaapp.component.PanelBorderRound transac1;
    private javaapp.component.PanelBorderRound transac2;
    private javaapp.component.PanelBorderRound transac3;
    private javaapp.component.PanelBorderRound transac4;
    private javax.swing.JLabel type1;
    private javax.swing.JLabel type2;
    private javax.swing.JLabel type3;
    private javax.swing.JLabel type4;
    // End of variables declaration//GEN-END:variables
}
