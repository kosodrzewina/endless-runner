package com.company;

import java.awt.*;

public class Triangle extends GeometricFigure {
    Point leftApex, rightApex;

    public Triangle(int x, int y, int height, int width) {
        super(new Color(
                (int) Math.random() * 255,
                (int) Math.random() * 255,
                (int) Math.random() * 255
        ));

        coordinate.x = x;
        coordinate.y = y;
        this.height = height;
        this.width = width;

        leftApex = new Point(coordinate.x - (width / 2), coordinate.y + height);
        rightApex = new Point(coordinate.x + (width / 2), coordinate.y + height);
    }

    @Override
    void move() {
        coordinate.x -= 10;
        leftApex.x -= 10;
        rightApex.x -= 10;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawLine(coordinate.x, coordinate.y, leftApex.x, leftApex.y);
        graphics.drawLine(coordinate.x, coordinate.y, rightApex.x, rightApex.y);
        graphics.drawLine(leftApex.x, leftApex.y, rightApex.x, rightApex.y);
    }
}
