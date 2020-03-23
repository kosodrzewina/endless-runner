package com.company;

import java.awt.*;

abstract public class GeometricFigure {
    Color color;

    public GeometricFigure(Color color) {
        this.color = color;
    }

    abstract public void paint(Graphics graphics);
    abstract public void paint(int x, int y, Graphics graphics);
}
