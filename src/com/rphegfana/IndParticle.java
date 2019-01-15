package com.rphegfana;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class IndParticle {

    private double x, y;
    private double direction;
    private double xVel, yVel;
    private Color color;

    public IndParticle(double x, double y, Color color){
        this.x = x;
        this.y = y;
        direction = new Random().nextInt(1000) * 0.002 * Math.PI;
        xVel = Math.cos(direction) * 20;
        yVel = Math.sin(direction) * 20;
        this.color = color;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void draw(GraphicsContext gc){

        x += xVel;
        y += yVel;
        yVel -= 1;

        gc.fillOval(x, y, 20 ,20);
    }
}
