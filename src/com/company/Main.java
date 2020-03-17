package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {

    public void paint(Graphics graphics) {
        Color[] environment = setEnvironment(false);
        int groundLevel = getSize().height - 100;

        setBackground(environment[0]);

        Ground ground = new Ground(groundLevel);
        ground.paint(graphics);

        Circle sunMoon = new Circle(100, environment[1]);
        sunMoon.paint(graphics);
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
    }

    public static void main(String[] args) {
        new Main();
    }
}
