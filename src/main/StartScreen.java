package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreen extends JPanel implements KeyListener {
    private Runnable startGameCallback;
    private Image bg;

    public StartScreen(Runnable startGameCallback) {
        this.startGameCallback = startGameCallback;
        this.setFocusable(true);
        this.addKeyListener(this);
        
        // Null layout use karenge takay button ki position set kar saken
        this.setLayout(null);

        bg = new ImageIcon("assets/demon.jpg").getImage();

        // 1. Play Button Create Karein
        GameButton playBtn = new GameButton("PLAY GAME");
        
        // 2. Button ki position (Center mein set karte hain)
        playBtn.setBounds(370, 370, 200, 50); 
        
        // 3. Button Click Logic (Same as Enter Key)
        playBtn.addActionListener(e -> {
            startGameCallback.run();
        });

        this.add(playBtn);
        requestFocusInWindow();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Comic Sans MS", Font.ITALIC, 40));
        g.drawString("Welcome to JumpyScare", 250, 300);
        // g.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        // g.drawString("Press ENTER to Start", 350, 400);
        
        // Buttons draw karne ke liye super call zaroori hai
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGameCallback.run(); 
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}