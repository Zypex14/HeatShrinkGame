package com.rphegfana;

import PhageEngine.GameApp;
import PhageEngine.GameSettings;
import PhageEngine.Stats;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Main extends GameApp {

    private double cursorCool;

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
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(Stats.getScreenX(), Stats.getScreenY(), Stats.getScreenWidth(), Stats.getScreenHeight());

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
