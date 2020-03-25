package com.company;

import java.awt.*;

public class Rectangle extends GeometricFigure {
    int height, width, x = 0, y = 0;

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

    public Rectangle(int height, int width, int x, int y) {
        super(new Color(
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)
        ));

        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public Rectangle(int height, int width, int x, int y, Color color) {
        super(color);

        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }
}
