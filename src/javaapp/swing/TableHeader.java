
package javaapp.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class TableHeader extends JLabel {
   public TableHeader (String text){
       super(text);
       setOpaque(true); 
       setBackground(Color.WHITE);
       setFont(new Font("sansserif",1,14));
       setForeground(new Color(102,102,102));
       setBorder(new EmptyBorder(10,5,10,5));
   }
   @Override
   protected void paintComponent(Graphics grphcs){
       super.paintComponent(grphcs);
       grphcs.setColor(new Color(210,210,210));
       grphcs.drawLine(0, getHeight()-1, getWidth(),getHeight()-1);
   }
}
