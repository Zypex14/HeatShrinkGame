package com.rphegfana;

import PhageEngine.Entity;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ParticleAnimation extends Entity {

    private ArrayList<Cluster> snaps;
    private Cluster player;
    private ArrayList<IndParticle> particles, deleteQueue;
    private String state;

    public ParticleAnimation(ArrayList<Cluster> snaps){
        addObject(this);

        this.player = player;
        this.snaps = snaps;
        particles = new ArrayList<>();
        deleteQueue = new ArrayList<>();
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

        for(Cluster c : snaps){
            for (Particle p : c.getParticles()){
                particles.add(new IndParticle(p.getX(), p.getY(), c.getColor()));
            }
        }
    }



    @Override
    public void onUpdate(GraphicsContext gc) {

        deleteQueue.forEach(p -> particles.remove(p));
        deleteQueue.clear();

        switch (state){
            case "death":
                for (IndParticle p : particles){
                    p.draw(gc);
                    if(p.isGone()){
                        deleteQueue.add(p);
                    }
                }
                break;

        }

        System.out.println(state);

    }
}
