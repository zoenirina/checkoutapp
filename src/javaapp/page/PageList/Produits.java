
package javaapp.page.PageList;

import javaapp.page.stock.MouvementStock;
import javaapp.page.stock.ProduitEnStock;
import javaapp.UserType;
import javaapp.component.ScrollPaneContainer;
import javax.swing.JTabbedPane;
/**
 *
 * @author ZOENIRINA
 */
public class Produits extends javax.swing.JPanel {
public String title="Caisse";
ScrollPaneContainer produit;
ScrollPaneContainer mouvementStock;

public Produits(UserType usertype) {
        initComponents();
        tabbedPane.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
        
        switch(usertype.getHierarchie()){
            case 1:
                produit = new ScrollPaneContainer(new ProduitEnStock(this));
                mouvementStock = new ScrollPaneContainer(new MouvementStock(this));
                break;
            case 2:
                produit = new ScrollPaneContainer(new ProduitEnStock(this));
                mouvementStock = new ScrollPaneContainer(new MouvementStock(this));
                break;
            case 3:
                produit = new ScrollPaneContainer(new ProduitEnStock(this));
                mouvementStock = new ScrollPaneContainer(new MouvementStock(this));
                break; 
        }
          
       tabbedPane.setBounds(50,50,200,200);
       tabbedPane.add("Liste des produits",produit);
       tabbedPane.add("Mouvement du stock", mouvementStock); 
    }
public Produits() {
        initComponents();
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
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.TabbedPanel tabbedPane;
    // End of variables declaration//GEN-END:variables
}
