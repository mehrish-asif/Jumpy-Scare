package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

import src.objects.Door;
import src.objects.FakeFloor;
import src.objects.Player;
import src.objects.Traps;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    // ArrayList<Traps> platforms = new ArrayList<>();
    private JComboBox<String> levelSelector; // Dropdown class level par declare kiya
    private ArrayList<Traps> traps = new ArrayList<>();
    private ArrayList<Rectangle> solidFloors = new ArrayList<>();
    private ArrayList<FakeFloor> fakeFloors = new ArrayList<>();
    private int currentLevel = 1;
    private Door door;
    Player player;
    Timer gameTimer;
    private boolean isDead = false;
    private boolean showLevelStartMessage = false;
    private String levelStartMessage = "";

    // Physics constants
    int gravity = 1;
    int jumpPower = -15;
    int walkStepCounter = 0;

    private AudioManager audio = new AudioManager();
    private String currentFunnyMessage = "";
    private boolean showDeathMessage = false;
    private Random random = new Random();
    private String[] deathSounds = { "src/audio/falldeath.wav" };
    private String[] funnyMessages = { "Seriously? 😏", "Again? Really! 😁", "Nice Try... Not! 👎",
            "RIP (Rest In Pit) ⚰️", "404: Floor Not Found 🔍" };

    public GamePanel(GameFrame frame) {
        this.setFocusable(true);
        this.addKeyListener(this);
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        // Objects initialization
        player = new Player(100, 400);
        door = new Door(900, 420);

        // Timer start (60 FPS approx)
        gameTimer = new Timer(20, this);
        gameTimer.start();

        // 1. Back Button
        GameButton backBtn = new GameButton("Back");
        backBtn.setBounds(10, 10, 80, 35);
        backBtn.addActionListener(e -> frame.showStartScreen());
        this.add(backBtn);

        // 2. Level Selector (Dropdown)
        // 2. Level Selector (Dropdown)
        String[] levelNames = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5" };
        levelSelector = new JComboBox<>(levelNames);
        levelSelector.addActionListener(e -> {
            String selected = (String) levelSelector.getSelectedItem();

            switch (selected) {
                case "Level 1":
                    loadLevel(1);
                    break;
                case "Level 2":
                    loadLevel(2);
                    break;
                case "Level 3":
                    loadLevel(3);
                    break;
                case "Level 4":
                    loadLevel(4);
                case "Level 5":
                    loadLevel(5);
                    break;
            }
        });
        levelSelector.setBounds(100, 10, 110, 35);
        levelSelector.setFocusable(false);

        // --- Styling Logic (Bilkul GameButton jaisa) ---
        levelSelector.setBackground(Color.RED);
        levelSelector.setForeground(Color.CYAN);
        levelSelector.setFont(new Font("Arial", Font.BOLD, 15));
        levelSelector.setBorder(BorderFactory.createLineBorder(new Color(100, 0, 0), 2));

        // Dropdown ke andar wali list ko style karein
        levelSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setBackground(isSelected ? new Color(150, 0, 0) : Color.RED); // Hover effect
                label.setForeground(Color.CYAN);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return label;
            }
        });

        // Removing defult Mac/Windows Style Button
        for (int i = 0; i < levelSelector.getComponentCount(); i++) {
            Component c = levelSelector.getComponent(i);
            if (c instanceof JButton) {
                c.setBackground(Color.RED);
                ((JButton) c).setBorderPainted(false);
            }
        }
        this.add(levelSelector);

        audio.startAlternateBGM();
        // Pehla level load karein
        loadLevel(currentLevel);

    }

    public void loadLevel(int level) {
        this.isDead = false;
        traps.clear();
        fakeFloors.clear();
        solidFloors.clear();
        if (level < 1)
            level = 1;
        this.currentLevel = level; // Global variable update karein
        player.leftPressed = false;
        player.rightPressed = false;
        player.velX = 0;
        player.velY = 0;
        player.isJumping = false;

        this.isDead = false;
        this.showDeathMessage = false;

        switch (level) {
            case 1:
                player.resetPosition(50, 450);
                door.setPosition(700, 420);
                solidFloors.add(new Rectangle(0, 500, 300, 300)); // Left part
                solidFloors.add(new Rectangle(400, 500, 600, 300)); // Right part
                fakeFloors.add(new FakeFloor(300, 500, 400, 300, 1000));
                break;
            case 2:
                player.resetPosition(50, 450);
                door.setPosition(705, 420);
                solidFloors.add(new Rectangle(0, 500, 400, 300)); // Pehla solid piece
                fakeFloors.add(new FakeFloor(400, 500, 100, 300, 1000)); // Pehla dhoka

                solidFloors.add(new Rectangle(500, 500, 100, 300)); // Darmiyan ka piece
                fakeFloors.add(new FakeFloor(600, 500, 100, 300, 1000)); // Dusra dhoka

                solidFloors.add(new Rectangle(700, 500, 300, 300));
                break;
            case 3:
                player.resetPosition(900, 450);
                door.setPosition(50, 420);
                solidFloors.add(new Rectangle(0, 500, 110, 300)); // Left part
                fakeFloors.add(new FakeFloor(-300, 500, 410, 300, 10));
                fakeFloors.add(new FakeFloor(210, 500, 300, 300, 550));
                solidFloors.add(new Rectangle(510, 500, 490, 300)); // Right part

                break;

            case 4:
                player.resetPosition(50, 450);
                door.setPosition(900, 420);
                solidFloors.add(new Rectangle(0, 500, 100, 300)); // Left part
                fakeFloors.add(new FakeFloor(100, 500, 100, 300, 1000));
                solidFloors.add(new Rectangle(200, 500, 100, 300));
                fakeFloors.add(new FakeFloor(300, 500, 100, 300, 1000));
                solidFloors.add(new Rectangle(400, 500, 400, 300));
                fakeFloors.add(new FakeFloor(800, 500, 100, 300, 1000));
                solidFloors.add(new Rectangle(900, 500, 100, 300));
                break;

            case 5:
                player.resetPosition(50, 450);
                door.setPosition(900, 420);
                solidFloors.add(new Rectangle(0, 500, 1000, 300));
                this.levelStartMessage = "BEWARE: The Ground Has Teeth! 😈";
                this.showLevelStartMessage = true;
                
                Timer messageTimer = new Timer(3000, e -> {
                    showLevelStartMessage = false;
                    repaint();
                    ((Timer) e.getSource()).stop();
                });
                messageTimer.setRepeats(false);
                messageTimer.start();
                traps.add(new Traps(200, 470, 60, 30, 2, 50));
                traps.add(new Traps(600, 470, 60, 30, 1, 70));
                traps.add(new Traps(400, 510, 60, 30, 0, 40));
                break;

            default:
                currentLevel = 1;
                loadLevel(1);
                return;
        }

        // Syncing dropdown with curent level
        if (levelSelector != null && levelSelector.getSelectedIndex() != level - 1) {
            levelSelector.setSelectedIndex(level - 1);
        }

        player.velY = 0;
        player.velX = 0; 

        this.requestFocusInWindow();

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isDead) {
            player.update();

            if ((player.leftPressed || player.rightPressed) && !player.isJumping) {
                walkStepCounter++;
                if (walkStepCounter >= 10) { // 15 frames baad ek step ki awaz (approx 0.3s)
                    audio.playEffect("src/audio/walking.wav");
                    walkStepCounter = 0; // Reset counter
                }
            } else {
                walkStepCounter = 0;
            }
            checkCollisions();

            // Trigger Logic
            boolean triggerAll = (currentLevel == 3);
            boolean anyTriggered = false;

            for (FakeFloor ff : fakeFloors) {
                if (Math.abs(player.x - (ff.x + ff.width)) < 45 || (player.x > ff.x && player.x < ff.x + ff.width)) {
                    if (triggerAll)
                        anyTriggered = true;
                    else
                        ff.isTriggered = true;
                }
            }

            if (triggerAll && anyTriggered) {
                for (FakeFloor ff : fakeFloors)
                    ff.isTriggered = true;
            }

            // Movement & Collision
            boolean onSolidFloor = false;
            for (Rectangle rect : solidFloors) {
                if (player.getBounds().intersects(rect))
                    onSolidFloor = true;
            }
            for (FakeFloor ff : fakeFloors) {
                ff.move();
                if (player.getBounds().intersects(ff.getBounds()))
                    onSolidFloor = true;
            }

            // Gravity/Landing
            if (player.y + player.height > 500) {
                if (onSolidFloor) {
                    player.y = 500 - player.height;
                    player.velY = 0;
                    player.isJumping = false;
                }
            }

            // Death Check
            if (player.y > 800) {
                isDead = true;
                resetPlayer();
            }

            for (Traps trap : traps) {
                trap.update(player.x, player.y);

                if (player.getBounds().intersects(trap.getBounds())) {
                    isDead = true;
                    audio.playEffect("src/audio/falldeath.wav");
                    resetPlayer();
                }
            }
        } else {
            door.update();
        }
        repaint();
    }

    public void resetPlayer() {
        audio.stopBGM();
        audio.playSound(deathSounds[random.nextInt(deathSounds.length)]);
        currentFunnyMessage = funnyMessages[random.nextInt(funnyMessages.length)];
        showDeathMessage = true;

        // Timer for Respawn
        Timer respawnTimer = new Timer(1500, e -> {
            loadLevel(currentLevel);
            System.out.println("Timer finished: Starting music"); // Debug line
            audio.startAlternateBGM();
            ((Timer) e.getSource()).stop();
        });
        respawnTimer.setRepeats(false);
        respawnTimer.start();
        this.requestFocusInWindow();
    }

    public void checkCollisions() {
        if (!isDead && player.getBounds().intersects(door.getBounds())) {
            isDead = true; // Input block
            door.isClosing = true; // SHUTTER DOWN!

            player.x = door.x + (door.width / 2) - (player.width / 2);
            player.y = door.y + door.height - player.height;
            player.velX = 0;
            player.velY = 0;
            // audio.stopBGM();
            audio.playSound("src/audio/Level-Passed-2.wav");

            // 3 Seconds Wait
            Timer winTimer = new Timer(1500, t -> {
                currentLevel++;
                loadLevel(currentLevel); // LoadLevel mein door reset ho jayega
                System.out.println("Level passed: Starting music"); // Debug line
                audio.startAlternateBGM();
                ((Timer) t.getSource()).stop();
            });
            winTimer.setRepeats(false);
            winTimer.start();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(99, 1, 1));

        for (Traps trap : traps) {
            trap.draw(g);
        }
        // 1. Draw ALL Solid Pieces (Automatic!)
        for (Rectangle rect : solidFloors) {
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }

        // 2. Draw ALL Fake Floors
        for (FakeFloor ff : fakeFloors) {
            ff.draw(g);
        }

        player.draw(g);
        door.draw(g);

        if (showDeathMessage) {
            g.setColor(new Color(0, 0, 0, 150)); // black background
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("YOU DIED! 💀", 350, 250);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.ITALIC, 25));
            g.drawString(currentFunnyMessage, 380, 310);
        }
        if (showLevelStartMessage) {
            g.setColor(new Color(0, 0, 0, 100)); // translucent background
            g.fillRect(250, 150, 500, 60);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(levelStartMessage, 230, 90);
        }
    }

    // --- Keyboard Input Handling ---
    public void keyPressed(KeyEvent e) {
        if (isDead)
            return;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            player.leftPressed = true;

        if (key == KeyEvent.VK_RIGHT)
            player.rightPressed = true;

        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) {
            if (!player.isJumping) {
                audio.playEffect("src/audio/jump.wav");
                player.jump();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)
            player.leftPressed = false;
        if (key == KeyEvent.VK_RIGHT)
            player.rightPressed = false;
    }

    public void keyTyped(KeyEvent e) {
    }
}