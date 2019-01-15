package PhageEngine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Entity {

    private static ArrayList<Entity> instances = new ArrayList<>(), deleteQueue = new ArrayList<>();
    private static HashMap<KeyCode, Boolean> key = new HashMap();
    private double height = 0, width = 0, collisionHeight = 0, collisionWidth = 0, x = 0, y = 0, centerX = 0, centerY = 0, rightX = 0, bottomY = 0;

    public static void updateObjects(GraphicsContext gc, HashMap<KeyCode, Boolean> key) {


//        Deletes game objects without throwing an exception
        deleteQueue.forEach(object -> instances.remove(object));
        deleteQueue.clear();

//        This will update all game objects
        instances.forEach(object -> object.onUpdate(gc));

//        Updates all the key inputs
        Entity.key = key;
//        System.out.println(isKeyHeld(KeyCode.D));
    }


    public static void removeObject(Entity o) {
        deleteQueue.add(o);
    }

    public static void addObject(Entity o) {
        instances.add(o);
    }

    public boolean isKeyHeld(KeyCode key) {
        this.key.putIfAbsent(key, false);
        return this.key.get(key);
    }

    public double getHeight() {
        return height;
    }

    public double getScreenHeight(){
        return (height / Stats.getMaxY()) * Stats.getScreenHeight();
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getScreenWidth(){
        return (width / Stats.getMaxX()) * Stats.getScreenWidth();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getCollisionHeight() {
        return collisionHeight;
    }

    public void setCollisionHeight(double collisionHeight) {
        this.collisionHeight = collisionHeight;
    }

    public double getCollisionWidth() {
        return collisionWidth;
    }

    public void setCollisionWidth(double collisionWidth) {
        this.collisionWidth = collisionWidth;
    }

    public double getX() {
        return x;
    }

    public void translateX(double x){
        setX(getX() + x);
    }

    public void translateY(double y){
        setY(getY() + y);
    }

    public double getScreenX(){
        return (x / Stats.getMaxX()) * Stats.getScreenWidth() + Stats.getScreenX();
    }

    public void setX(double x) {
        this.x = x;
        this.centerX = x + (width / 2);
        this.rightX = x + width;
    }

    public double getY() {
        return y;
    }

    public double getScreenY(){
        return (y / Stats.getMaxY()) * Stats.getScreenHeight() + Stats.getScreenY();
    }

    public void setY(double y) {
        this.y = y;
        this.centerY = y + (height / 2);
        this.bottomY = y + height;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        setX(centerX - width / 2);
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        setY(centerY - height / 2);
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        setX(rightX - width);
    }

    public double getBottomY() {
        return bottomY;
    }

    public void setBottomY(double bottomY) {
        setY(bottomY - height);
    }

    public void changeX(double x) {
        setX(this.x + x);
    }

    public void changeY(double y) {
        setY(this.y + y);
    }

    public abstract void onUpdate(GraphicsContext gc);

    protected static class DirectionalMovement {
        public static double getX(boolean up, boolean right, boolean down, boolean left) {
            if((right ? -1 : 0) + (left ? 1 : 0) == 0){
                return 0;
            }
            else {
                return Math.cos(Math.atan2((up ? 1 : 0) + (down ? -1 : 0), (right ? -1 : 0) + (left ? 1 : 0)));
            }
        }

        public static double getX(double up, double right, double down, double left) {
            if(right + left == 0) {
                return 0;
            } else{
                return Math.cos(Math.atan2(up - down, left - right));
            }
        }

        public static double getY(boolean up, boolean right, boolean down, boolean left) {
            if((up ? -1 : 0) + (down ? 1 : 0) == 0){
                return 0;
            }
            else {
                return -1 * Math.sin(Math.atan2((up ? 1 : 0) + (down ? -1 : 0), (right ? -1 : 0) + (left ? 1 : 0)));
            }
        }

        public static double getY(double up, double right, double down, double left) {
            if(up + down == 0){
                return 0;
            } else {
                return Math.sin(Math.atan2(up - down, left - right));
            }
        }
    }
}
