package PhageEngine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public abstract class GameApp extends Application {

    private double h, w, b1w, b1h, b2x, b2y, b2w, b2h;
    private HashMap<KeyCode, Boolean> key;
    private Scene scene;
    private boolean mouseMoving;

    public abstract void initSettings(GameSettings gs);

    public abstract void initGame();

    @Override
    public void start(Stage primaryStage) {

        GameSettings gs = new GameSettings();
        initSettings(gs);


        Group root = new Group();
        Canvas canvas = new Canvas(gs.getWidth(), gs.getHeight());
        root.getChildren().add(canvas);


        key = new HashMap<>();

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(
                (e) -> key.put(e.getCode(), true)
        );
        canvas.setOnKeyReleased(
                (d) -> key.put(d.getCode(), false)
        );

        Stats.setScreenHeight(gs.getHeight());
        Stats.setScreenWidth(gs.getWidth());

        // The Graphics object where we will be drawing to
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create the game loop: run the draw function multiple times
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds((double) 1 / gs.getFps()),
                (ActionEvent event) -> {

                    Stats.update();
                    gc.clearRect( 0, 0, w, h);

                    onUpdate(gc);
                    Entity.updateObjects(gc, key);

                    if(gs.getFullscreen()) {

                        gc.setFill(Color.BLACK);
                        gc.fillRect(0, 0, b1w, b1h);
                        gc.fillRect(b2x, b2y, b2w, b2h);

                    }

                    mouseMoving = false;
                }
        );

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        // Setup the Scene and Stage
        Scene scene = new Scene(root, gs.getWidth(), gs.getHeight());
        this.scene = scene;

        mouseMoving = false;
        scene.setOnMouseMoved(mouseHandler);

        primaryStage.setTitle(gs.getTitle());
        primaryStage.setScene(scene);

        if (gs.getFullscreen()) {

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            canvas.setHeight(bounds.getHeight());
            canvas.setWidth(bounds.getWidth());

            primaryStage.setHeight(bounds.getHeight());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

            h = bounds.getHeight();
            w = bounds.getWidth();

            double sRatio = bounds.getWidth() / bounds.getHeight();

            if(sRatio > gs.getRatio()){
                Stats.setScreenHeight(bounds.getHeight());
                Stats.setScreenY(0);

                Stats.setScreenWidth(gs.getRatio() * bounds.getHeight());
                Stats.setScreenX((bounds.getWidth() - Stats.getScreenWidth()) * 0.5);

                Stats.update();

                b1w = Stats.getScreenX();
                b1h = Stats.getScreenHeight();
                b2x = Stats.getScreenMaxX();
                b2y = 0;
                b2w = Stats.getScreenX();
                b2h = Stats.getScreenHeight();

            } else if(sRatio < gs.getRatio()){
                Stats.setScreenWidth(bounds.getWidth());
                Stats.setScreenX(0);

                Stats.setScreenHeight(Math.pow(gs.getRatio(), -1) * bounds.getWidth());
                Stats.setScreenY((bounds.getHeight() - Stats.getScreenHeight()) * 0.5);

                Stats.update();

                b1w = Stats.getScreenWidth();
                b1h = Stats.getScreenY();
                b2x = 0;
                b2y = Stats.getScreenMaxY();
                b2w = Stats.getScreenWidth();
                b2h = Stats.getScreenY();

            } else{
                Stats.setScreenHeight(bounds.getHeight());
                Stats.setScreenWidth(bounds.getWidth());
            }

        }

        Stats.setMaxX(gs.getaWidth());
        Stats.setMaxY(gs.getaHeight());

        primaryStage.show();


        initGame();
        System.out.println("Game init");
    }

    private EventHandler<MouseEvent> mouseHandler = event -> mouseMoving = true;

    public abstract void onUpdate(GraphicsContext gc);

    public boolean isKeyHeld(KeyCode key) {
        this.key.putIfAbsent(key, false);
        return this.key.get(key);
    }

    public boolean isMouseMoving() {
        return mouseMoving;
    }

    public void setCursor(Cursor cursor){
        scene.setCursor(cursor);
    }
}


