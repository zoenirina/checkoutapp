package javaapp.component;
import javax.swing.JButton;
import java.awt.Color;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonRadius extends JButton {

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public Color getBorderColor() {
        return borderColor;
    }

  
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    /**
     * @param colorFill the colorFill to set
     */
    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
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
    }
    
    
  public ButtonRadius(){
      
setColor(getBackground());
colorOver= new Color(204, 0, 0);
colorClick= new Color(102, 153, 0);
borderColor=new Color(77, 77, 0);
setContentAreaFilled(false);

addMouseListener(new MouseAdapter(){
@Override
public void mouseEntered(MouseEvent me){
over=true;
setBackground(colorOver);
}

@Override
public void mouseExited(MouseEvent me){
over=false;
setBackground(color);
}

@Override
public void mousePressed(MouseEvent me){
over=true;
setBackground(colorClick);
}

@Override
public void mouseReleased(MouseEvent me){
if(over){
setBackground(colorOver);
}else{
setBackground(color);
}
}
});

  }
    
    private boolean over;
    private Color color;
    private Color borderColor;
    private Color colorOver;
    private Color colorClick;
    private int radius = 10;
    
     
  @Override
  protected void paintComponent(Graphics g){
   Graphics2D g2d = (Graphics2D)g;
   
 // GradientPaint gp = new GradientPaint(0, 0, Color.blue, 0, 20, Color.cyan, true);
   g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 g2d.setColor(borderColor);
   g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
   g2d.setColor(getBackground());
    
   // g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth()/ 2 /4), (this.getHeight() / 2) + 5,getWidth());
     g2d.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, radius, radius);
   
    //  }
    super.paintComponent(g);
  } 
  
    
}
