package com.company;

import java.awt.*;

public class Player {
    Color color;
    int height = 50, width = 50, x = 70, y;

    public Player (int groundLevel) {
        y = groundLevel - height;

        color = new Color(
                (int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)
        );
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }
}
