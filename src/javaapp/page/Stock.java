
package javaapp.page;

import javaapp.UserType;
import javaapp.component.ScrollPaneContainer;
import javax.swing.JTabbedPane;

public class Stock extends javax.swing.JPanel {
public String title="Caisse";
ScrollPaneContainer produit;
ScrollPaneContainer mouvementStock;

public Stock(UserType usertype) {
        initComponents();
        tabbedPane.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
        
//        switch(usertype.getHierarchie()){
//            case 1:
//                produit = new ScrollPaneContainer(new EtatStock(this));
//                mouvementStock = new ScrollPaneContainer(new EntreeStock(this));
//                break;
//            case 2:
//                produit = new ScrollPaneContainer(new EtatStock(this));
//                mouvementStock = new ScrollPaneContainer(new EntreeStock(this));
//                break;
//            case 3:
//                produit = new ScrollPaneContainer(new EtatStock(this));
//                mouvementStock = new ScrollPaneContainer(new EntreeStock(this));
//                break; 
//        }
          
       tabbedPane.setBounds(50,50,200,200);
       tabbedPane.add("Etat de stock",new ScrollPaneContainer(new EtatStock(this)));
       tabbedPane.add("Entrée", new ScrollPaneContainer(new EntreeStock(this))); 
       tabbedPane.add("Sortie", new ScrollPaneContainer(new SortieStock(this))); 
        tabbedPane.add("Transfert", new ScrollPaneContainer(new TransferStock(this))); 
    }
public Stock() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javaapp.component.TabbedPanel();

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
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.TabbedPanel tabbedPane;
    // End of variables declaration//GEN-END:variables
}














