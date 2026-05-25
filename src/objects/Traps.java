package src.objects;

import java.awt.*;
import javax.swing.ImageIcon;

public class Traps {
    public int x, y, width, height;
    private int originalX, originalY;
    public boolean isTriggered = false;
    private int speed = 5; // Speed thori kam rakhi hai taake control rahay
    
    private int trapType; // 0: Up, 1: Left, 2: Right
    private Image spikeImg;

    // --- Naye Variables ---
    private int movedAmount = 0; 
    private int maxDistance; // Jitna aap move karwana chahti hain (e.g., 10px ya 100px)

    public Traps(int x, int y, int width, int height, int type, int maxDistance) {
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.width = width;
        this.height = height;
        this.trapType = type;
        this.maxDistance = maxDistance; // Limit set karein yahan se
        
        try {
            spikeImg = new ImageIcon("src/image/spike.png").getImage();
        } catch (Exception e) {
            System.out.println("Image error: " + e.getMessage());
        }
    }

    public void update(int playerX, int playerY) {
        // Trigger logic
        if (Math.abs(playerX - this.x) < 40) {
            isTriggered = true;
        }

        if (isTriggered && movedAmount < maxDistance) {
            if (trapType == 0) { // Up
                y -= speed;
            } 
            else if (trapType == 1) { // Left
                x -= speed;
            } 
            else if (trapType == 2) { // Right
                x += speed;
            }
            
            // Movement track karein
            movedAmount += speed; 
        }
    }

    public void draw(Graphics g) {
        if (spikeImg != null) {
            g.drawImage(spikeImg, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, width - 10, height - 10);
    }
}