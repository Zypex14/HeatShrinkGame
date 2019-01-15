package PhageEngine;

import javafx.scene.canvas.GraphicsContext;

public class GraphicsProcessor extends Thread{

    private boolean running;
    private GraphicsContext gc;

    public GraphicsProcessor(GraphicsContext gc){
        running = true;
    }

    public void deactivate(){
        running = false;
    }

    public void run(){

        while(running){

        }

    }

}
