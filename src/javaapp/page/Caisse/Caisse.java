
package javaapp.page.Caisse;

import javaapp.UserType;
import javaapp.component.ScrollPaneContainer;
import javax.swing.JTabbedPane;
/**
 *
 * @author ZOENIRINA
 */
public class Caisse extends javax.swing.JPanel {
public String title="Caisse";
ScrollPaneContainer sortie;
ScrollPaneContainer entree;
ScrollPaneContainer typeMouvement;
//ScrollPaneContainer exercice;

public Caisse(UserType usertype) {
        initComponents();
        tabbedPane.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
        
        switch(usertype.getHierarchie()){
            case 1:
                sortie = new ScrollPaneContainer(new SortieCaisse(this));
                entree = new ScrollPaneContainer(new EntreeCaisse(this));
//                exercice = new ScrollPaneContainer(new Exercice(this)); 
                break;
            case 2:
                sortie = new ScrollPaneContainer(new SCaisse(this));
                entree = new ScrollPaneContainer(new EntreeCaisse1(this));
//                exercice = new ScrollPaneContainer(new Exercice1(this)); 
                break;
            case 3:
                sortie = new ScrollPaneContainer(new SortieCaisse3(this));
                entree = new ScrollPaneContainer(new EntreeCaisse2(this));
//                exercice = new ScrollPaneContainer(new Exercice1(this)); 
                break; 
        }
        
//        entree = new ScrollPaneContainer(new SortieCaisse(this));
       typeMouvement = new ScrollPaneContainer(new TypeMouvement(this));  
       tabbedPane.setBounds(50,50,200,200);
       tabbedPane.add("Entrée",entree);
       tabbedPane.add("Sortie",sortie);
       tabbedPane.add("Type de mouvement",typeMouvement);
//       tabbedPane.add("Exercice",exercice);
    }
public Caisse() {
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
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.TabbedPanel tabbedPane;
    // End of variables declaration//GEN-END:variables
}
