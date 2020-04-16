package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;

public class Main extends Frame {
    private boolean switchTime = true;
    private boolean environmentState = true;
    private int groundLevel;
    private Button restart;
    private Button timeSwitch;
    private Label scoreLabel;
    private JLabel gameOverLabel;
    private int score = -10;
    private boolean inAir = false;
    private int jumpLimit = 250;
    private boolean goBack = false;
    private boolean running = true;
    Thread gameLoopThread;
    Color[] environment;

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

        restart.setEnabled(true);
    }

    public void paint(Graphics graphics) {
        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        scoreLabel.setBounds(getWidth() - 120, 90, 85, 20);
        restart.setBounds(getWidth() - 120, 60, 85, 20);
        gameOverLabel.setBounds(0, 0, getWidth(), getHeight());

        if (running) {
            environment = setEnvironment(environmentState);
            groundLevel = getSize().height - 100;

            if (switchTime) {
                setBackground(environment[0]);
                scoreLabel.setBackground(environment[1]);
                switchTime = false;
            }

            Ground ground = new Ground(groundLevel);
            ground.paint(graphics);

            Circle sunMoon = new Circle(200, environment[1], getWidth() - 200, -200);
            sunMoon.paint(graphics);

            player.paint(graphics);

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
                // preventing memory leak
                if (obstacles.getAt(i).x + obstacles.getAt(i).width < 0) {
                    obstacles.deleteFrom(i);
                    i = 0;
                }

                obstacles.getAt(i).y = groundLevel - obstacles.getAt(i).height;
                obstacles.getAt(i).paint(graphics);
            }
        }
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
        restart = new Button("restart");
        scoreLabel = new Label();
        gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        scoreLabel.setText("0");

        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        restart.setBounds(getWidth() - 120, 60, 85, 20);
        scoreLabel.setBounds(getWidth() - 120, 90, 85, 20);
        gameOverLabel.setBounds(0, 0, getWidth(), getHeight());

        gameOverLabel.setFont(new Font("Cambria", Font.PLAIN, 100));
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBackground(Color.red);
        gameOverLabel.setVisible(false);
        restart.setEnabled(false);
        restart.setFocusable(false);

        panel.add(timeSwitch);
        panel.add(restart);
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
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    inAir = true;

                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    gameOver();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

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

        timeSwitch.addActionListener(e -> {
            environmentState = !environmentState;
            switchTime = true;
            repaint();

            timeSwitch.setLabel((environmentState) ? "switch to night" : "switch to day");
        });

        restart.addActionListener(e -> {
            score = -10;
            running = true;
            obstacles.clear();

            timeSwitch.setEnabled(true);
            restart.setEnabled(false);
            scoreLabel.setText(String.valueOf(score));
            gameOverLabel.setVisible(false);
            scoreLabel.setBackground(environment[1]);

            gameLoopThread = new Thread(this::gameLoop);

            gameLoopThread.start();
        });

        gameLoopThread = new Thread(this::gameLoop);
        gameLoopThread.start();
    }

    public void gameLoop() {
        long time = 2000;

        while (running) {
            // generate new obstacle every 2s
            if (System.currentTimeMillis() - time >= 2000) {
                if (Math.random() < 0.3)
                    obstacles.add(new Circle(45, getWidth(), groundLevel - 100));
                else
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
    }

    public static void main(String[] args) {
        new Main();
    }
}
