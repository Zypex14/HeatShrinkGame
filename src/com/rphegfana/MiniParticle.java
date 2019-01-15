package com.rphegfana;

import PhageEngine.Entity;
import PhageEngine.Stats;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class MiniParticle extends Entity {

    private ArrayList<Particle> p;
    private Color c;
    private Cluster player;
    private Random R;
    private double speed;

    public MiniParticle(ArrayList<Particle> p, Color c, Cluster player){
        addObject(this);
        this.p = p;
        this.c = c;
        this.player = player;
        R = new Random();
        int id = R.nextInt(16);
        setX(p.get(id).getX());
        setY(p.get(id).getY());
        speed = (1200 - Math.abs(Stats.getScreenX() + 150 - getX())) * 0.05;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        translateX(-speed);
        if(getX() < Stats.getScreenX() + 175){
            removeObject(this);
        } else {
            gc.setFill(c);
            gc.fillOval(getX() - 2, getY() - 2, 4, 4);
        }
    }
}
