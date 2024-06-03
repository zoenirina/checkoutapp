
package javaapp.page.parametre;

import javaapp.UserType;
import javaapp.component.ScrollPaneContainer;
import javax.swing.JTabbedPane;

public class Parametre extends javax.swing.JPanel {
public String title="Caisse";
ScrollPaneContainer produit;
ScrollPaneContainer mouvementStock;

public Parametre(UserType usertype) {
        initComponents();
        tabbedPane.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
        tabbedPane.setBounds(50,50,200,200);
//        tabbedPane.add("Etat de stock",new ScrollPaneContainer(new EtatStock(this)));

 switch(usertype.getHierarchie()){
            case 1:
                tabbedPane.add("Compte utilisateur", new ScrollPaneContainer(new Compte1())); 
                tabbedPane.add("Groupe", new ScrollPaneContainer(new Groupe())); 
                tabbedPane.add("Configuration", new ScrollPaneContainer(new Configuration(this))); 
                break;
            case 2:
                tabbedPane.add("Compte utilisateur", new ScrollPaneContainer(new Compte2())); 
                tabbedPane.add("Groupe", new ScrollPaneContainer(new Groupe1())); 
//                tabbedPane.add("Configuration", new ScrollPaneContainer(new Configuration(this))); 
                break;
//            case 3:
//                tabbedPane.add("Compte utilisateur3", new ScrollPaneContainer(new Compte2())); 
//                tabbedPane.add("Groupe3", new ScrollPaneContainer(new Groupe1())); 
////                tabbedPane.add("Configuration", new ScrollPaneContainer(new Configuration(this))); 
//                break; 
        }

//        tabbedPane.add("Compte utilisateur", new ScrollPaneContainer(new Compte1())); 
//        tabbedPane.add("Groupe", new ScrollPaneContainer(new Groupe())); 
//        tabbedPane.add("Configuration", new ScrollPaneContainer(new Configuration(this))); 
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javaapp.component.TabbedPanel();

        setBackground(new java.awt.Color(238, 238, 238));

        tabbedPane.setBackground(new java.awt.Color(255, 255, 255));
        tabbedPane.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.TabbedPanel tabbedPane;
    // End of variables declaration//GEN-END:variables
}














