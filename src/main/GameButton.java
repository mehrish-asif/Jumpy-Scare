package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameButton extends JButton {
    
    private Color normalColor = Color.RED;
    private Color hoverColor = new Color(150, 0, 0); 
    private Color borderColor = new Color(100, 0, 0); 
    private int arcWidth = 20;  // Border radius ki value
    private int arcHeight = 20;

    public GameButton(String text) {
        super(text);
        
        this.setFocusable(false);
        this.setContentAreaFilled(false); // Default background hatao
        this.setBorderPainted(false);     // Default border hatao
        this.setOpaque(false);            // Custom painting handle karenge
        this.setForeground(Color.CYAN);
        this.setFont(new Font("Arial", Font.BOLD, 15));
        this.setBackground(normalColor);

        // Hover Effect
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Button Background (Rounded)
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        // 2. Border (Rounded)
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(3)); // Border thickness
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arcWidth, arcHeight);

        super.paintComponent(g);
    }
}