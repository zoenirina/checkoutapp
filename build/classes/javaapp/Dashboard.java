
package javaapp;

import java.awt.Color;
import javaapp.event.EventMenuSelected;
import javaapp.form.Dashboard_Form;
import javaapp.form.Form1;
import javaapp.form.Form2;
import javaapp.form.Form3;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Dashboard extends javax.swing.JFrame {

    public Dashboard() {
        initComponents();
        setBackground(new Color(0,0,0,0));
        menu.addEventMenuSelected(new EventMenuSelected(){
        @Override
        public void selected(int index){
//             JOptionPane.showMessageDialog(null, "index:"+index,"Sucess",JOptionPane.INFORMATION_MESSAGE); 
        if(index == 0){
            setForm(new Dashboard_Form());
        }else if(index == 1){
            setForm(new Form1());
        }else if(index == 2){
            setForm(new Form2());
        }else if(index == 3){
             setForm(new Form3());   
        }
        }
      
        });
        setForm(new Dashboard_Form());
    }
    
    private void setForm(JComponent com){
    mainPanel.removeAll();
    mainPanel.add(com);
    mainPanel.repaint();
    mainPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorderRound2 = new javaapp.PanelBorderRound();
        menu = new javaapp.Menu();
        panelBorderRound3 = new javaapp.PanelBorderRound();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelBorderRound2.setBackground(new java.awt.Color(255, 228, 255));
        panelBorderRound2.setBorder(new javax.swing.border.MatteBorder(null));
        panelBorderRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelBorderRound2.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 930));

        panelBorderRound3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBorderRound3Layout = new javax.swing.GroupLayout(panelBorderRound3);
        panelBorderRound3.setLayout(panelBorderRound3Layout);
        panelBorderRound3Layout.setHorizontalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1120, Short.MAX_VALUE)
        );
        panelBorderRound3Layout.setVerticalGroup(
            panelBorderRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        panelBorderRound2.add(panelBorderRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1120, 70));

        jScrollPane3.setBackground(new java.awt.Color(255, 228, 255));
        jScrollPane3.setBorder(null);

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane3.setViewportView(mainPanel);

        panelBorderRound2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 1120, 840));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorderRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    private javaapp.Menu menu;
    private javaapp.PanelBorderRound panelBorderRound2;
    private javaapp.PanelBorderRound panelBorderRound3;
    // End of variables declaration//GEN-END:variables
}
