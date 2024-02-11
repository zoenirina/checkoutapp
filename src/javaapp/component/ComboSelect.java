
package javaapp.component;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ComboSelect extends JComboBox<String> {
//    private final Color backgroundColor = Color.WHITE;
//    private final int borderRadius = 10;
//    private final String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

    public ComboSelect(String[] items) {
        super(items);
        
        init();
    }

    private void init() {
        setOpaque(false);
//        setRenderer(new ComboRenderer());
//        setUI(new ComboUI());
    }

//    private class ComboRenderer extends DefaultListCellRenderer {
//        @Override
//        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//            setBackground(backgroundColor);
//            setOpaque(true);
//            return this;
//        }
//    }
//
//    private class ComboUI extends BasicComboBoxUI {
//        @Override
//        protected JButton createArrowButton() {
//            return new JButton() {
//                @Override
//                public void paint(Graphics g) {
//                    Graphics2D g2 = (Graphics2D) g;
//                    g2.setColor(backgroundColor);
//                    g2.fillRect(0, 0, getWidth(), getHeight());
//                    super.paint(g);
//                }
//            };
//        }
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(backgroundColor);
//        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
//        super.paintComponent(g);
//    }
//
//    @Override
//    protected void paintBorder(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(getForeground());
//        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
//    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("ComboSelect Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
//            ComboSelect comboSelect = new ComboSelect(items);
//
//            JPanel panel = new JPanel();
//            panel.add(comboSelect);
//            frame.getContentPane().add(panel);
//            
//            frame.setSize(300, 200);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//    }
}