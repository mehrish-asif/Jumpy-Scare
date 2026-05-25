package src.objects;

import java.awt.*;

public class Door {
    public int x, y, width, height;
    public int shutterHeight = 0;
    public boolean isClosing = false;
    public boolean isOpen = false; // Isay baad mein animation ke liye use karenge
    int thickness = 2;

    // Colors as per your request
    private Color innerBody = new Color(161, 235, 255, 200); // Sky Blue
    private Color border = new Color(245, 94, 7); // Bright Orange

    public Door(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 80;
    }

    public void update() {
        if (isClosing) {
            if (shutterHeight < height) {
                shutterHeight += 3; // Shutter niche aane ki speed
            } else {
                isOpen = false; // Jab shutter pura niche aa jaye toh door close
            }
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Sky Blue Inner Body (Background)
        g2.setColor(innerBody);
        g2.fillRect(x, y, width, height);

        // 2. BLACK SHUTTER (Ye inner body ke upar aana chahiye)
        if (shutterHeight > 0) {
            g2.setColor(new Color(0, 0, 0, 150));
            // Ensure width aur height bilkul door ke barabar hon
            g2.fillRect(x, y, width, shutterHeight);
        }

        // 3. Orange Borders (Ye sab se upar draw karein taake frame nazar aata rahe)
        g2.setColor(border);
        g2.fillRect(x - thickness, y, thickness, height); // Left
        g2.fillRect(x + width, y, thickness, height); // Right
        g2.fillRect(x - thickness, y - thickness, width + (thickness * 2), thickness); // Top
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.shutterHeight = 0; // Naye level mein shutter reset karein
        this.isClosing = false;
        this.isOpen = true;
    }

}