package com.company;

import java.awt.*;

public class Circle extends GeometricFigure {
    int radius;

    public Circle(int radius, Color color) {
        super(color);

        this.radius = radius;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(0, 0, 2 * radius, 2 * radius);
    }

    @Override
    public void paint(int x, int y, Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(x, y, 2 * radius, 2 * radius);
    }
}
