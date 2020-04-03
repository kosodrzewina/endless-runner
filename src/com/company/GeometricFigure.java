package com.company;

import java.awt.*;

abstract public class GeometricFigure extends Thread {
    Color color;

    public GeometricFigure(Color color) {
        this.color = color;
        start();
    }

    abstract public void paint(Graphics graphics);
    abstract void move();

    public void run() {
        while (true) {
            move();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
