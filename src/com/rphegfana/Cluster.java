package com.rphegfana;

import PhageEngine.Entity;
import PhageEngine.GameMath;
import PhageEngine.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Cluster extends Entity{

    private double heat;
    private double x, y;
    private int r, g, b;
    private Color color;
    private Point2D[] pos;

    public void setPos(Point2D[] pos) {
        this.pos = pos;
    }

    public void setClusterPos(double x, double y){
        this.x = x;
        this.y = y;
    }

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
            particles.add(new Particle(x, y));
        }

        pos = new Point2D[16];
    }

    public double getX(){
        return x;
    }

    public ArrayList<Particle> getParticles(){
        return particles;
    }

    public Color getColor() {
        return color;
    }

    public double getHeat(){
        return heat;
    }

    public double getY(){
        return y;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        r = GameMath.cap((int)Math.round(heat * 255),0, 255);
        b = GameMath.cap((int)Math.round((1 - heat) * 255), 0 ,255);
        g = GameMath.cap((int)Math.round((1.5 - Math.abs(heat)) * 50), 0, 255);
        color = Color.rgb(r, g, b);

        for(int i = 0; i < particles.size(); i++){
            Particle p = particles.get(i);

            p.setColor(color);
            p.setClusterPos(x, y);
            p.setPos(pos[i]);
            p.setHeat(heat);
            p.draw(gc);
        }
    }
}

