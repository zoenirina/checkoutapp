/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class RoundedLabel extends JLabel{
    private int radius=15;
    
    public RoundedLabel(Icon image){
        super(image);
//        setOpaque(false);
setRadius(15);
    }
    public RoundedLabel() {
        super();
//         setOpaque(false);
    }
    @Override
        protected void paintComponent(Graphics g){
            
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
           
//        super.paintComponent(g);
        Graphics2D g2 =bi.createGraphics();
        
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, getRadius(), getRadius());
        Area area = new Area(roundRect);
        g2.setClip(area);
        g2.drawImage(((ImageIcon) getIcon()).getImage(), 0,0,getWidth(), getHeight(), null);
        
        g.drawImage(bi, 0,0,null);
        super.paintComponent(g2);
        g2.dispose();
//        g2.fillRect(getWidth()-20, 0, getWidth(), getHeight());

}

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
}
