package src.objects;

import java.awt.*;

public class FakeFloor {
    public int x, y, width, height;
    private int originalX;
    public int stopX;
    public boolean isTriggered = false;
    private int speed = 13; // Change this for faster/slower "pixel by pixel" movement

    public FakeFloor(int x, int y, int width, int height, int stopX) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originalX = x;
        this.stopX = stopX;
    }

    public void update(int playerX) {

        // 1. Distance from Left Edge
        int distLeft = Math.abs(playerX - this.x);

        // 2. Distance from Right Edge (x + width)
        int distRight = Math.abs(playerX - (this.x + this.width));

        // 1. Trigger: Agar player floor ke 40 pixels qareeb aye
        if (distLeft < 40 || distRight < 40) {
            isTriggered = true;
        }

        // 2. Animation: Pixel by pixel right side move karein
        if (isTriggered && this.x < stopX) {
            x += speed;

            if(x > stopX){
                x = stopX;
            }
        }
    }

    public void move() {
    if (isTriggered && x < stopX) {
        x += speed;
        if (x > stopX) x = stopX;
    }
}

    public void draw(Graphics g) {
        g.setColor(new Color(99, 1, 1)); // Floor color
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Level reset ke waqt x ko wapis apni jagah lane ke liye
    public void reset() {
        this.x = originalX;
        this.isTriggered = false;
    }
}