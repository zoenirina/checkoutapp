/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {

    private int borderRadius = 10;

    public RoundedTextField() {
        super();
        init();
    }

    public RoundedTextField(int columns) {
        super(columns);
        init();
    }

    private void init() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Optional: Ajoute une marge interne
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        g2.setColor(getBackground());
        g2.fill(roundRect);
        super.paintComponent(g);
        g2.setColor(getForeground());
        g2.draw(roundRect);

        g2.dispose();
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("RoundedTextField Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            RoundedTextField textField = new RoundedTextField(20);
//
//            JPanel panel = new JPanel();
//            panel.add(textField);
//            frame.getContentPane().add(panel);
//
//            frame.setSize(300, 200);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}