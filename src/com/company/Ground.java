package com.company;

import java.awt.*;

public class Ground {
    private int level;
    private static Color color = Color.darkGray;

    public Ground(int level) {
        this.level = level;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(0, level, (int) Double.POSITIVE_INFINITY, 100);
    }
}
