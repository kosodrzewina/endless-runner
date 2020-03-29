package com.company;

import java.awt.*;

public class Circle extends GeometricFigure {
    int radius, x = 0, y = 0;

    public Circle(int radius, Color color) {
        super(color);

        this.radius = radius;
    }

    public Circle(int radius, Color color, int x, int y) {
        super(color);

        this.radius = radius;
        this.x = x;
        this.y = y;
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
