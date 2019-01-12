package com.rphegfana;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {

    private double startX, startY, startZ;
    private double x, y;
    private double clusterX, clusterY;
    private Color color;

    public Particle(double x, double y){
        startX = x;
        startY = y;
        x = 0;
        y = 0;
        clusterX = 0;
        clusterY = 0;
        color = Color.WHITE;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setClusterPos(double x, double y){
        clusterX = x;
        clusterY = y;
    }

    public void draw(GraphicsContext gc){
        gc.setFill(color);
        gc.fillOval(clusterX + startX + x, clusterY + startY + y, 20, 20);
    }

}
