
package javaapp.page.vente;
import javaapp.component.ScrollPaneContainer;
import javax.swing.JTabbedPane;
/**
 *
 * @author ZOENIRINA
 */
public class Vente extends javax.swing.JPanel {
public String title="Caisse";
ScrollPaneContainer eCommande;
ScrollPaneContainer commande;
ScrollPaneContainer listeCommande;
ScrollPaneContainer livraison;
    private final ScrollPaneContainer reglement;

public Vente() {
    initComponents();
    tabbedPane.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
    eCommande = new ScrollPaneContainer(new EnregistrementCommande(this));
    listeCommande = new ScrollPaneContainer(new Facture(this)); 
    livraison = new ScrollPaneContainer(new Livraison(this));
    commande = new ScrollPaneContainer(new Commande(this));
    reglement = new ScrollPaneContainer(new Reglement(this));   
    
       tabbedPane.setBounds(50,50,200,200);
       tabbedPane.add("Nouveau commande",eCommande);
       tabbedPane.add("Bon de commande",commande);
       tabbedPane.add("Bon de livraison",livraison);
       tabbedPane.add("Facture",listeCommande);
       tabbedPane.add("Règlement",reglement);
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
