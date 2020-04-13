package com.company;

import java.awt.*;

public class Circle extends GeometricFigure {
    int radius;

    public Circle(int radius, Color color) {
        super(color);

        this.radius = radius;
        height = width = radius * 2;
    }

    public Circle(int radius, int x, int y) {
        super(new Color(
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)
        ));

        this.radius = radius;
        this.x = x;
        this.y = y;
        height = width = radius * 2;
    }

    public Circle(int radius, Color color, int x, int y) {
        super(color);

        this.radius = radius;
        this.x = x;
        this.y = y;
        height = width = radius * 2;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(x, y, 2 * radius, 2 * radius);
    }

    @Override
    void move() {
        x -= 10;
    }
}
