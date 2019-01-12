package com.rphegfana;

import PhageEngine.Entity;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Cluster extends Entity{

    private double heat;
    private double x, y;
    private int r, g, b;
    private Color color;
    private ArrayList<Particle> particles;

    public Cluster(double heat, double startX, double startY){
        addObject(this);

        this.heat = heat;
        x = startX;
        y = startY;
        r = 0;
        g = 0;
        b = 0;
        color = Color.rgb(r, g, b);
        particles = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            int x = (i % 4) * 20 - 30;
            int y = Math.floorDiv(i , 4) * 20 - 30;
            System.out.println(x + ", " + y);
            particles.add(new Particle(x, y));
        }
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        r = (int)Math.round(heat * 255);
        b = (int)Math.round((1 - heat) * 255);
        color = Color.rgb(r, g, b);

        for(Particle p : particles){
            p.setColor(color);
            p.setClusterPos(x, y);
            p.draw(gc);
        }
    }
}

