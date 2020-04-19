package com.company;

import java.awt.*;

public class Ground {
    private int level;
    private static Color color;

    public Ground(int level, GroundPalette type) {
        this.level = level;

        switch (type) {
            case FOREGROUND:
                color = Color.darkGray;
                break;

            case BACKGROUND:
                color = Color.lightGray;
                break;
        }
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(0, level, Toolkit.getDefaultToolkit().getScreenSize().width, 100);
    }
}
