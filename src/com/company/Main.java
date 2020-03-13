package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame {

    public Main() {
        this.setTitle("Runner");

        this.setSize(1000, 500);
        this.setVisible(true);

        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                }
        );
    }

    public static void main(String[] args) {
        new Main();
    }
}
