package com.rphegfana;

import PhageEngine.GameApp;
import PhageEngine.GameSettings;
import PhageEngine.Stats;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Main extends GameApp {

    private double cursorCool;
    private Cluster player;
    private Point2D[] translate, pos;

    //    This is a function that can be used to change the settings of the game
    @Override
    public void initSettings(GameSettings gs) {
        gs.setTitle("Thermal Snap");
        gs.setFullscreen(true);
        gs.setAspectRatio(16, 9);
    }

    //    This is called the instant the game starts
    @Override
    public void initGame() {
        cursorCool = 0;
        player = new Cluster(0,Stats.getScreenX() + 100, (Stats.getScreenY() + Stats.getScreenMaxY()) / 2);
        translate = new Point2D[16];
        pos = new Point2D[16];
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(Stats.getScreenX(), Stats.getScreenY(), Stats.getScreenWidth(), Stats.getScreenHeight());


//        Hide mouse if not moving for one second
        if (isMouseMoving()) {
            cursorCool = 0;
        } else {
            cursorCool++;
        }

        if (cursorCool > 60) {
            setCursor(Cursor.NONE);
        } else {
            setCursor(Cursor.DEFAULT);
        }
    }
}
