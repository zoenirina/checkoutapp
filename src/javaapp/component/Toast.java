package javaapp.component;
import java.awt.*; 
import javax.swing.*; 
public class Toast extends JFrame { 
	String message; //message
	JWindow w;

	public Toast(String message, int x, int y) 
	{ 
		w = new JWindow(); 
		w.setBackground(new Color(255, 255, 255, 250));
                w.setBounds(10, 10, 10, 10);
                
		// créer un panel
		JPanel p = new JPanel() { 
                        @Override
			public void paintComponent(Graphics g) 
			{  
                            g.setColor(Color.decode("#4cb30b")); 
                            g.setFont(new java.awt.Font("Verdana", 1, 14));
                            g.drawString(message, 25, 27);  
			} 
		}; 
                p.setBorder(BorderFactory.createLineBorder(Color.decode("#4cb30b"), 2));
		w.add(p); 
		w.setLocation(x, y); 
		w.setSize(300, 50); 
	} 
        
	// affichage du popup
	public void showtoast() 
	{ 
            try { 
		w.setOpacity(1); 
		w.setVisible(true); 
		Thread.sleep(800); // temps avant exécution

			// faire disparaitre lentement
		for (double d = 1.0; d > 0.2; d -= 0.2) { 
                    Thread.sleep(100); 
                    w.setOpacity((float)d); 
			} 
                    w.setVisible(false); 
		} 
		catch (Exception e) { 
			System.out.println(e.getMessage()); 
		} 
	} 
} 

