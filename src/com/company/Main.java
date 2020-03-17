package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {

    public void paint(Graphics graphics) {
        int groundLevel = getSize().height - 100;

        Ground ground = new Ground(groundLevel);
        ground.paint(graphics);

        Circle circle = new Circle(100, Color.yellow);
        circle.paint(graphics);
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
