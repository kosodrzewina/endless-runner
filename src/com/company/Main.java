package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends Frame {
    private boolean switchTime = true;
    private boolean environmentState = true;
    private int groundLevel;
    private Button timeSwitch;
    private Label scoreLabel;
    private int score = -10;

    public Player player;
    private GeometricFigureList obstacles = new GeometricFigureList();

    public void paint(Graphics graphics) {
        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        Color[] environment = setEnvironment(environmentState);
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
        player.y = groundLevel - player.height;

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.getAt(i).y = groundLevel - 100;
            obstacles.getAt(i).paint(graphics);
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
        scoreLabel = new Label();
        scoreLabel.setText("0");

        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        scoreLabel.setBounds(getWidth() - 120, 60, 85, 20);
        panel.add(timeSwitch);
        panel.add(scoreLabel);
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
                    player.jump();
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
            long time = 5000;

            while (true) {
                // generate new obstacle every 5s
                if (System.currentTimeMillis() - time >= 5000) {
                    obstacles.add(new Rectangle(100, 200, getWidth(), groundLevel - 100));
                    time = System.currentTimeMillis();

                    scoreLabel.setText(String.valueOf(score += 10));
                }

                repaint();

                try {
                    Thread.sleep(100);
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
