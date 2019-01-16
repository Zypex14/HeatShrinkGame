package com.rphegfana;

import PhageEngine.Entity;
import PhageEngine.Stats;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UI extends Entity {

    private int score;
    private double titleY;
    private double enterY;
    private String gameState;

    public UI(){
        addObject(this);

        score = 0;
        titleY = -150;
        enterY = Stats.getScreenMaxY() + 150;
        gameState = "menu";
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameState(String state){
        gameState = state;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        gc.setFont(Font.font("Makhina", 75));
        gc.setFill(Color.WHITE);

        gc.fillText("" + score, Stats.getScreenX() + Stats.getScreenWidth() / 2 - gc.getFont().getSize() * 0.25 * ("" + score).length(), 100);


        gc.setFont(Font.font("Makhina", 40));
        gc.fillText("press 'enter' to start", Stats.getScreenX() + Stats.getScreenWidth() / 2 - 200, enterY);

        gc.setFont(Font.font("Makhina", 80));
        gc.fillText("phase", Stats.getScreenX() + Stats.getScreenWidth() / 2 - 100, titleY);

        switch (gameState){
            case "play":
                titleY += (-150 - titleY) * 0.1;
                enterY += (Stats.getScreenMaxY() + 150 - enterY) * 0.1;
                break;

            case "menu":
                titleY += (350 - titleY) * 0.1;
                enterY += (500 - enterY) * 0.1;
                break;

        }
    }
}
