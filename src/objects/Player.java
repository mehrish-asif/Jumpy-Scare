package src.objects;

import java.awt.*;

public class Player {
    public int x, y, width, height;
    public int velY = 0, velX = 0; // Gravity aur Jump ke liye
    public int gravity = 1;
    public int jumpPower = -13;
    public int moveSpeed = 6;
    public boolean isJumping = false;
    public boolean isFacingright = false;

    public boolean leftPressed = false;
    public boolean rightPressed = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
    }

    public void update() {

        if (this.y + this.height > 500) {
            // Falling state mein movement block kar dein
            this.leftPressed = false;
            this.rightPressed = false;
            this.velX = 0; // Horizontal speed khatam
        }
        // Horizontal Movement
        if (leftPressed) {
            velX = -moveSpeed;
            isFacingright = false;
        } else if (rightPressed) {
            velX = moveSpeed;
            isFacingright = true;
        } else
            velX = 0;

        x += velX;

        // Gravity & Jump
        velY += gravity;
        y += velY;
    }

    public void jump() {
        if (y + height <= 500 && !isJumping) {
            velY = jumpPower;
            isJumping = true;
        }
    }

    public void resetPosition(int newx, int newy) {
        this.x = newx;
        this.y = newy;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.RED);
        g2.fillRoundRect(x, y, width, height, 10, 10);

        // Eyes
        int eyeY = y + 10;
        int pupilY = y + 12;
        if (isFacingright) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x + 8, eyeY, 8, 8);
            g2.fillRect(x + 24, eyeY, 8, 8);
            g2.setColor(Color.BLACK);
            g2.fillRect(x + 10, pupilY, 4, 4);
            g2.fillRect(x + 26, pupilY, 4, 4);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(x + -2, eyeY, 8, 8);
            g2.fillRect(x + 14, eyeY, 8, 8);
            g2.setColor(Color.BLACK);
            g2.fillRect(x + 0, pupilY, 4, 4);
            g2.fillRect(x + 16, pupilY, 4, 4);
        }
    }
}