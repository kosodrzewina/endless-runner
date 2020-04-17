package com.company;

import java.awt.*;

public class Rectangle extends GeometricFigure {

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
        coordinate.x = x;
        coordinate.y = y;
    }

    public Rectangle(int height, int width, int x, int y, Color color) {
        super(color);

        this.height = height;
        this.width = width;
        coordinate.x = x;
        coordinate.y = y;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(coordinate.x, coordinate.y, width, height);
    }

    @Override
    void move() {
        coordinate.x -= 10;
    }
}
