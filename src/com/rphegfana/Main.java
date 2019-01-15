package com.rphegfana;

import PhageEngine.*;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Main extends GameApp {

    private Timer cursorCool;
    private Cluster player;
    private double heat, heatTranslate;
    private String state;
    private Point2D[] translate, pos;
    private Random R;
    private Timer clusterGen;
    private int difficulty;
    private ArrayList<Cluster> snaps, deleteSnaps;

    //    This is a function that can be used to change the settings of the game
    @Override
    public void initSettings(GameSettings gs) {
        gs.setHertz(60);
        gs.setTitle("Thermal Snap");
        gs.setFullscreen(true);
        gs.setAspectRatio(16, 9);
    }

    //    This is called the instant the game starts
    @Override
    public void initGame() {
        cursorCool = new Timer();
        cursorCool.start();

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
        clusterGen = new Timer();
        clusterGen.start();
        clusterGen.setTime(5000);

        snaps = new ArrayList<>();
        deleteSnaps = new ArrayList<>();
        difficulty = 1;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(Stats.getScreenX(), Stats.getScreenY(), Stats.getScreenWidth(), Stats.getScreenHeight());
        renderTemperature(gc);

//        Hide mouse if not moving for one second
        if (isMouseMoving()) {
            cursorCool.reset();
        }
        if (cursorCool.getTime() > 1000) {
            setCursor(Cursor.NONE);
        } else {
            setCursor(Cursor.DEFAULT);
        }

        if(clusterGen.getTime() > 3000 / difficulty){
            snaps.add(0, new Cluster(R.nextInt(120) * 0.01 + 0.1, Stats.getScreenMaxX() + 200, Stats.getScreenY() + Stats.getScreenHeight() / 2));
            clusterGen.reset();
            difficulty *= 1.1;
        }


        deleteSnaps.forEach(object -> snaps.remove(object));
        deleteSnaps.clear();

        for (Cluster c: snaps) {

            double distance = Math.abs(Stats.getScreenX() + 150 - c.getX());
            c.setPos(pos);

            if (Math.abs(heat - c.getHeat()) < 0.1 && distance < 200) {

                c.setHeat(c.getHeat() + (heat - c.getHeat()) * 0.05);
                c.setClusterPos(c.getX() + (Stats.getScreenX() + 150 - c.getX()) * 0.2, c.getY());

                if(distance < 10){
                    Cluster.removeObject(c);
                    deleteSnaps.add(c);
                }

            } else {
                c.setClusterPos(c.getX() - 5, c.getY());

                if (Stats.getScreenX() + 150 - c.getX() > 0) {
                    Cluster.removeObject(c);
                    System.exit(0);
                }
            }

            if(distance < 1000 && R.nextInt((int)(Math.abs(heat - c.getHeat()) * 1000) + 1) == 0 && Math.abs(heat - c.getHeat()) < 0.1 && !(Stats.getScreenX() + 150 - c.getX() > 0)){
                new MiniParticle(c.getParticles(), c.getColor(), player);
            }
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

        }

        player.setHeat(heat);

    }

    private void renderTemperature(GraphicsContext gc){

        double maxX = Stats.getScreenMaxX();
        double maxY = Stats.getScreenMaxY();

        gc.setFill(Color.rgb(100, 10, 10));
        gc.fillRect(maxX - 100, maxY - 20, 100, 20);

        gc.setFill(Color.rgb(75, 20, 110));
        gc.fillRect(maxX - 160, maxY - 20, 60, 20);

        gc.setFill(Color.rgb(20, 75, 150));
        gc.fillPolygon(new double[]{maxX - 160, maxX - 160, maxX - 200}, new double[]{maxY, maxY - 20, maxY}, 3);

        gc.setFill(Color.WHITE);
        double heat2temp = heat * 400d/3d;
        gc.fillPolygon(new double[]{maxX - 200 + heat2temp, maxX - 210 + heat2temp, maxX - 190 + heat2temp}, new double[]{maxY - 20, maxY - 30, maxY - 30}, 3);

    }
}
