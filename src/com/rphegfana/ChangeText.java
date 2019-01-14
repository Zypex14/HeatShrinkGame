package com.rphegfana;

import PhageEngine.Entity;
import PhageEngine.GameImage;
import PhageEngine.Stats;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class ChangeText extends Entity {
    private String changeText;
    private double  opacity;
    private double rot, direction;
    private int life;

    public ChangeText(String changeText){
        addObject(this);
        this.changeText = changeText;
        Random R = new Random();
        rot = R.nextInt(90) - 44;
        life = 0;
        opacity = 0;
        setX(Stats.getScreenX() + 131 + R.nextInt(40));
        setY((Stats.getScreenY() + Stats.getScreenMaxY()) / 2 + R.nextInt(40) - 19);
        direction = Math.atan2(getY() - (Stats.getScreenY() + Stats.getScreenMaxY()) / 2, getX() - (Stats.getScreenX() + 150));

    }

    public void drawRotText(double x, double y, double rotation, String text, Color color, GraphicsContext gc) {
        gc.save();
        gc.setFill(color);
        gc.translate(x, y);
        gc.rotate(rotation);
        gc.fillText(text, -(gc.getFont().getSize() * text.length() * 0.25), 0);
        gc.restore();
    }


    @Override
    public void onUpdate(GraphicsContext gc) {
        gc.setFont(Font.font("Makhina", 30));
        life++;

        if(life < 15){
            opacity += (1 - opacity) * 0.1;
        }
        if(life > 15){
            opacity *= 0.9;
        }

        if(life > 60){
            removeObject(this);
        }

        setX(getX() + Math.cos(direction) * 2);
        setY(getY() + Math.sin(direction) * 2);
        drawRotText(getX(), getY(), rot, changeText + "!", Color.rgb(255, 255, 255, opacity), gc);
    }
}
