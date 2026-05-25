package src.main;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        this.setTitle("Jumpy Scare");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        showStartScreen(); // Start screen load karne ke liye method call kiya
        this.setVisible(true);
    }

    public void showStartScreen() {
        this.getContentPane().removeAll();

        StartScreen startScreen = new StartScreen(() -> {
            this.getContentPane().removeAll();
            
            // GamePanel ko 'this' (GameFrame) pass kar rahe hain
            GamePanel gamePanel = new GamePanel(this); 
            this.add(gamePanel);
            
            this.revalidate();
            this.repaint();

            new Thread(() -> {
                try { Thread.sleep(100); } catch (Exception ignored) {}
                gamePanel.requestFocusInWindow();
            }).start();
        });

        this.add(startScreen);
        this.revalidate();
        this.repaint();
        startScreen.requestFocusInWindow();
    }
}