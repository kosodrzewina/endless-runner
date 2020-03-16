package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {

    public void paint(Graphics graphics) {
        int level = 400;

        Ground ground = new Ground(level);
        ground.paint(graphics);
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
