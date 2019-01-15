package com.rphegfana;

import PhageEngine.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ParticleAnimation extends Entity {

    private ArrayList<Cluster> snaps;
    private Cluster player;
    private ArrayList<IndParticle> particles;
    private String state;

    public ParticleAnimation(ArrayList<Cluster> snaps, Cluster player){
        this.player = player;
        this.snaps = snaps;
        state = "none";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void startDeath(){
        state = "death";
        for(Particle p : player.getParticles()) {
            particles.add(new IndParticle(p.getX(), p.getY(), player.getColor()));
        }

        for(Cluster c : snaps){
            for (Particle p : c.getParticles()){
                particles.add(new IndParticle(p.getX(), p.getY(), c.getColor()));
            }
        }
    }

    public void startRegen(){
        state = "regen";
    }

    @Override
    public void onUpdate(GraphicsContext gc) {

        switch (state){
            case "death":
                for (IndParticle p : particles){
                    p.draw(gc);
                }
                break;

        }

    }
}
