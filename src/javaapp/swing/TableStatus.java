/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class TableStatus extends JLabel {
    
    public TableStatus(){
        setForeground(Color.WHITE);
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2= (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g= new GradientPaint(0, 0, new Color(186,123,247), 0, getHeight(),new Color(102,102,255));
  
    if( getText() != null ){
//    GradientPaint g;
    if( null == getText()){
        g= new GradientPaint(0, 0, new Color(186,123,247), 0, getHeight(),new Color(102,102,255));
    }else switch (getText()) {
            case "Payé":
            case "Payée":
            case "Livrée":
            case "Livré":
//            case "Disponible":
//                g= new GradientPaint(0, 0, Color.decode("#CCFFCC"), 0, getHeight(),Color.decode("#C5FFDE"));
                g= new GradientPaint(0, 0, Color.decode("#24F894"), 0, getHeight(),Color.decode("#00E96D"));
//                setForeground(Color.decode("#009933"));
                this.setPreferredSize(new Dimension(40, 20));
//                this.setFont(new Font(bold));
                break;
            case "En attente":
            case "Payée partiellement":
            case "Stock épuisé":
            case "En préparation":
//                g= new GradientPaint(0, 0, Color.decode("#FFFF99"), 0, getHeight(),Color.decode("#FEFF99"));//FF9900
                g= new GradientPaint(0, 0, Color.decode("#FFCC33"), 0, getHeight(),Color.decode("#FF9900"));
//                setForeground(Color.decode("#E26E04"));
                break;
            case "Non payé":
            case "Non livrée":
            case "Non livré":
            case "Annulé":
            case "Annulée":
//                g= new GradientPaint(0, 0, Color.decode("#FFD5D5"), 0, getHeight(),Color.decode("#FFD5D5"));
                g= new GradientPaint(0, 0, Color.decode("#FF6666"), 0, getHeight(),Color.decode("#FF0033"));
//                setForeground(Color.decode("#FF3131"));
                break;
            case"Brouillon":
               g= new GradientPaint(0, 0, Color.decode("#AEAEE9"), 0, getHeight(),Color.decode("#AEAEE9"));
            break;//FF9900
            case"En cours":
               g= new GradientPaint(0, 0, Color.decode("#CDCDFF"), 0, getHeight(),Color.decode("#CDCDFF"));
            break;
            case"Validé":
               g= new GradientPaint(0, 0, Color.decode("#CDCDFF"), 0, getHeight(),Color.decode("#CDCDFF"));
            break;
            default:
//                g= new GradientPaint(0, 0, Color.decode("#D7D7FE"), 0, getHeight(),Color.decode("#D7D7FE"));
                g= new GradientPaint(0, 0, new Color(186,123,247), 0, getHeight(),new Color(102,102,255));
//                setForeground(Color.decode("#000066"));
                break;
               
        }
     setForeground(Color.decode("#ffffff"));
    }
    
    g2.setPaint(g);

//switch (getText()) {
//            case "Payé":
//            case "Livrée":
//                setBackground(getBgSuccess());
//                break;
//            case "En attente":
//            case "Partiellemnt payé":
//                setBackground(getBgSuccess());
//                break;
//            case "Non payé":
//            case "Non livrée":
//               setBackground(getBgSuccess());
//                break;
//            default:
//                setBackground(getBgSuccess());
//                break;
//}
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
//    g2.fillRect( getWidth()-20, 0, getWidth(), getHeight());       
    super.paintComponent(grphcs);
    }
}
