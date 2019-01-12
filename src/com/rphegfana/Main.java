package com.rphegfana;

import PhageEngine.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Random;

public class Main extends GameApp {

    private double cursorCool;
    private Cluster player;
    private double heat, heatTranslate;
    private String state;
    private Point2D[] translate, pos;
    private Random R;

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
        heat = 0;
        heatTranslate = 0.01;
        state = "solid";

        player = new Cluster(heat,Stats.getScreenX() + 150, (Stats.getScreenY() + Stats.getScreenMaxY()) / 2);
        translate = new Point2D[16];
        pos = new Point2D[16];

//        Fill the arrays with something
        for(int i = 0; i < 16; i++){
            translate[i] = new Point2D(0, 0);
            pos[i] = new Point2D(0, 0);
        }
        R = new Random();
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

//        Update movement behavior of particles
        for(int i = 0; i < pos.length; i++){
            translate[i].setX((translate[i].getX() + R.nextInt(1000) * 0.001) % (Math.PI * 2));
            translate[i].setY((translate[i].getY() + R.nextInt(1000) * 0.001) % (Math.PI * 2));
            pos[i].setX(Math.sin(translate[i].getX()));
            pos[i].setY(Math.sin(translate[i].getY()));
        }

        player.setPos(pos);

        if(isKeyHeld(KeyCode.UP)){
            heatTranslate += 0.01;
        }

        if(isKeyHeld(KeyCode.DOWN)){
            heatTranslate -= 0.01;
        }

        if(heatTranslate < 0){
            heatTranslate = 1.5;
        }

        if(heatTranslate > 1.5){
            heatTranslate = 0;
        }

        heat += (heatTranslate - heat) * 0.1;

        String oldState = state;
        if(heatTranslate < 0.3){
            state = "solid";
        } else if(heatTranslate > 0.75){
            state = "gas";
        } else{
            state = "liquid";
        }

        if(!state.equals(oldState)){
            String changeText = "";
            switch(oldState + state){
                case "gassolid":
                    changeText = "Deposition";
                    break;

                case "solidliquid":
                    changeText = "Melting";
                    break;

                case "liquidgas":
                    changeText = "Evaporation";
                    break;

                case "solidgas":
                    changeText = "Sublimation";
                    break;

                case "gasliquid":
                    changeText = "Condensation";
                    break;
                case "liquidsolid":
                    changeText = "Freezing";
                    break;
            }

            new ChangeText(changeText);
            System.out.println(changeText);

        }

        player.setHeat(heat);

    }
}
