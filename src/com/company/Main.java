package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {
    private boolean setSky = true;
    private boolean environmentState = true;
    private int groundLevel;
    private Button timeSwitch;

    Player player;
    private GeometricFigureList obstacles = new GeometricFigureList();

    public void paint(Graphics graphics) {
        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        Color[] environment = setEnvironment(environmentState);
        groundLevel = getSize().height - 100;

        if (setSky) {
            setBackground(environment[0]);
            setSky = false;
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
        setSize(1000, 500);
        groundLevel = getSize().height - 100;
        player = new Player(groundLevel);

        JPanel panel = new JPanel();
        timeSwitch = new Button("switch to night");

        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        panel.add(timeSwitch);
        add(panel);

        panel.setLayout(null);
        setVisible(true);

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
                setSky = true;
                repaint();

                timeSwitch.setLabel((environmentState) ? "switch to night" : "switch to day");
            }
        });

        new Thread() {
            public void run() {
                long time = 5000;

                while (true) {
                    // generate new obstacle every 5s
                    if (System.currentTimeMillis() - time >= 5000) {
                        obstacles.add(new Rectangle(100, 200, getWidth(), groundLevel - 100));
                        time = System.currentTimeMillis();
                    }

                    repaint();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
