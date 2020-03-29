package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {
    boolean environmentState = true;
    int groundLevel;
    Button timeSwitch;

    Rectangle rectangle;

    public void paint(Graphics graphics) {
        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        Color[] environment = setEnvironment(environmentState);

        setBackground(environment[0]);

        Ground ground = new Ground(groundLevel);
        ground.paint(graphics);

        Circle sunMoon = new Circle(200, environment[1], getWidth() - 200, -200);
        sunMoon.paint(graphics);

        rectangle.paint(graphics);
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

        JPanel panel = new JPanel();
        timeSwitch = new Button("switch to night");

        timeSwitch.setBounds(getWidth() - 120, 30, 85, 20);
        panel.add(timeSwitch);
        add(panel);

        panel.setLayout(null);
        setVisible(true);

        rectangle = new Rectangle(100, 200, getWidth(), groundLevel - 100);

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
                repaint();

                timeSwitch.setLabel((environmentState) ? "switch to night" : "switch to day");
            }
        });

        new Thread() {
            public void run() {
                while (true) {
                    repaint();

                    try {
                        Thread.sleep(200);
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
