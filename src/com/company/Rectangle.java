package com.company;

import java.awt.*;

public class Rectangle extends GeometricFigure {
    int height, width;

    public Rectangle(int height, int width, Color color) {
        super(color);

        this.height = height;
        this.width = width;
    }

    public Rectangle(int height, int width) {
        super(new Color(
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)
        ));

        this.height = height;
        this.width = width;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(10, 10, width, height);
    }

    @Override
    public void paint(int x, int y, Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }
}
