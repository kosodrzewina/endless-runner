package com.company;

import java.awt.*;

public class Ground extends GeometricFigure {
    private int level;
    private static Color color = Color.darkGray;

    public Ground(int level) {
        super(color);

        this.level = level;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(0, level, (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY);
    }
}
