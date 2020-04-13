package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;

public class Main extends Frame {
    private boolean switchTime = true;
    private boolean environmentState = true;
    private int groundLevel;
    private Button timeSwitch;
    private Label scoreLabel;
    private JLabel gameOverLabel;
    private int score = -10;
    private BufferedImage bufferedImage;
    private boolean inAir = false;
    private int jumpLimit = 250;
    private boolean goBack = false;
    private boolean running = true;

    public Player player;
    private GeometricFigureList obstacles = new GeometricFigureList();
    
    public boolean checkCollision(Player player, GeometricFigure geometricFigure) {
        Point topLeftPlayer = new Point(player.x, player.y);
        Point bottomRightPlayer = new Point(player.x + player.width, player.y + player.height);

        Point topLeftFigure = new Point(geometricFigure.x, geometricFigure.y);
        Point bottomRightFigure = new Point(geometricFigure.x + geometricFigure.width,
                geometricFigure.y + geometricFigure.height);

        return (topLeftPlayer.x < bottomRightFigure.x && bottomRightPlayer.x > topLeftFigure.x
                && topLeftPlayer.y < bottomRightFigure.y && bottomRightPlayer.y > topLeftFigure.y);
    }

    public void gameOver() {
        running = false;
        gameOverLabel.setVisible(true);
        scoreLabel.setBackground(Color.red);
        timeSwitch.setEnabled(false);

        // saving score
        File file = new File("scores.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(score + ": " +  LocalDateTime.now() + "\n");
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void drawElements(Graphics2D graphics2D) {
        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        scoreLabel.setBounds(getWidth() - 120, 60, 85, 20);

        Color[] environment = setEnvironment(environmentState);
        groundLevel = getSize().height - 100;

        if (switchTime) {
            setBackground(environment[0]);
            scoreLabel.setBackground(environment[1]);
            switchTime = false;
        }

        Ground ground = new Ground(groundLevel);
        ground.paint(graphics2D);

        Circle sunMoon = new Circle(200, environment[1], getWidth() - 200, -200);
        sunMoon.paint(graphics2D);

        player.paint(graphics2D);

        // jumping
        if (!inAir)
            player.y = groundLevel - player.height;
        else if (!goBack && groundLevel - player.y < jumpLimit)
            player.y -= 10;
        else if (!goBack)
            goBack = true;

        if (goBack && groundLevel - player.y > getHeight() - groundLevel - player.height)
            player.y += 10;
        else if (goBack) {
            goBack = false;
            inAir = false;
        }

        // drawing obstacles on the ground
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.getAt(i).y = groundLevel - obstacles.getAt(i).height;
            obstacles.getAt(i).paint(graphics2D);
        }
    }

    public Graphics2D createGraphics(Graphics graphics) {
        Graphics2D graphics2D = null;
        if (bufferedImage == null || bufferedImage.getWidth() != getWidth() ||
                bufferedImage.getHeight() != getHeight())
            bufferedImage = (BufferedImage) createImage(getWidth(), getHeight());

        if (bufferedImage != null) {
            graphics2D = bufferedImage.createGraphics();
            graphics2D.setBackground(getBackground());
        }

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        graphics2D.clearRect(0, 0, getWidth(), getHeight());

        return graphics2D;
    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = createGraphics(graphics);
        drawElements(graphics2D);
        graphics2D.dispose();

        if (bufferedImage != null && isShowing())
            graphics.drawImage(bufferedImage, 0, 0, this);
    }

    public Color[] setEnvironment(boolean time) {
        Color[] colors = new Color[2];

        if (time) {
            colors[0] = Palette.skyDay;
            colors[1] = Palette.sun;
        } else {
            colors[0] = Palette.skyNight;
            colors[1] = Palette.moon;
        }

        return colors;
    }

    public Main() {
        setTitle("Runner");
        setSize(1280, 720);
        groundLevel = getSize().height - 100;
        player = new Player(groundLevel);

        JPanel panel = new JPanel();
        timeSwitch = new Button("switch to night");
        scoreLabel = new Label();
        gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        scoreLabel.setText("0");

        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        scoreLabel.setBounds(getWidth() - 120, 60, 85, 20);
        gameOverLabel.setBounds(0, 0, getWidth(), getHeight());
        gameOverLabel.setFont(new Font("Cambria", Font.PLAIN, 100));
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBackground(Color.red);
        gameOverLabel.setVisible(false);

        panel.add(timeSwitch);
        panel.add(scoreLabel);
        panel.add(gameOverLabel);

        timeSwitch.setFocusable(false);
        add(panel);

        panel.setLayout(null);
        setVisible(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    inAir = true;

                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    gameOver();
                }
            }
        });

        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                }
        );

        timeSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                environmentState = !environmentState;
                switchTime = true;
                repaint();

                timeSwitch.setLabel((environmentState) ? "switch to night" : "switch to day");
            }
        });

        Thread gameLoop = new Thread(() -> {
            long time = 2000;

            while (running) {
                // generate new obstacle every 2s
                if (System.currentTimeMillis() - time >= 2000) {
                    obstacles.add(new Rectangle(80, 120, getWidth(), groundLevel - 100));
                    time = System.currentTimeMillis();

                    scoreLabel.setText(String.valueOf(score += 10));
                }

                repaint();

                if (checkCollision(player, obstacles.getAt(0)))
                    gameOver();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameLoop.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
