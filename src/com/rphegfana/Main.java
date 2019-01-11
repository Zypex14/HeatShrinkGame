package com.rphegfana;

import PhageEngine.GameApp;
import PhageEngine.GameSettings;
import PhageEngine.Stats;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Main extends GameApp {

    private double cursorCool;

    @Override
    public void initSettings(GameSettings gs) {
        gs.setTitle("Thermal Snap");
        gs.setFullscreen(true);
        gs.setAspectRatio(16,9);
    }

    @Override
    public void initGame() {
        cursorCool = 0;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(Stats.getScreenX(), Stats.getScreenY(), Stats.getScreenWidth(), Stats.getScreenHeight());

        if(isMouseMoving()) {
            cursorCool = 0;
        } else{
            cursorCool++;
        }
    }
}
