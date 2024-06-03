
package javaapp.page;

import javax.swing.JTabbedPane;

public class Caisse extends javax.swing.JPanel {
public String title="Caisse";
    public Caisse() {
        initComponents();
        tabbedPanelleft.setTabLayoutPolicy((JTabbedPane.SCROLL_TAB_LAYOUT));
        EntreeCaisse entree = new EntreeCaisse();
        SortieCaisse sortie = new SortieCaisse();
        Exercice exercice = new Exercice();
       tabbedPanelleft.setBounds(50,50,200,200);
       
       tabbedPanelleft.add("Entrée",entree);
       tabbedPanelleft.add("Sortie",sortie);
        tabbedPanelleft.add("Exercice",exercice);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPanelleft = new javaapp.component.TabbedPanel();

        tabbedPanelleft.setBackground(new java.awt.Color(255, 255, 255));
        tabbedPanelleft.setFont(new java.awt.Font("Verdana", 1, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(tabbedPanelleft, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(tabbedPanelleft, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javaapp.component.TabbedPanel tabbedPanelleft;
    // End of variables declaration//GEN-END:variables
}
