package com.rphegfana;

import PhageEngine.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cluster extends Entity {

    private double heat;
    private int r, g, b;
    private Color color;

    public Cluster(double heat){
        this.heat = heat;
        r = 0;
        g = 0;
        b = 0;
        color = Color.rgb(r, g, b);

        for (int i = 0; i < 16; i++){

        }
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        color = Color.rgb(r, g, b);

    }
}
