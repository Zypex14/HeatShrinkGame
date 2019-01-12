package com.rphegfana;

import PhageEngine.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    private double startX, startY, startZ;
    private double x, y;
    private double heat;

    private double clusterX, clusterY;
    private Color color;

    public Particle(double x, double y){
        startX = x;
        startY = y;
        x = 0;
        y = 0;
        heat = 0;
        clusterX = 0;
        clusterY = 0;
        color = Color.WHITE;
    }

    public void setPos(Point2D pos){
        x = pos.getX();
        y = pos.getY();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setClusterPos(double x, double y){
        clusterX = x;
        clusterY = y;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public void draw(GraphicsContext gc){
        double offX, offY, radians, distance;
        radians = Math.atan2(startY, startX);
        distance = Math.sqrt(Math.pow(startX, 2) + Math.pow(startY, 2));
        offX = distance * (heat * 2 + 1) * 1 * (Math.cos(radians));
        offY = distance * (heat * 2 + 1) * 1 * (Math.sin(radians));

        gc.setFill(color);
        gc.fillOval(clusterX + offX + (x * heat * 20), clusterY + offY + (y * heat * 20), 20, 20);
    }

}
