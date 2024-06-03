package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A class representing a paddle game item that is visually represented by a JavaFX rectangle. It is used to bounce the ball
 * and prevent it from falling down.
 * @author: Yasha Doddabele
 */
public class Paddle extends GameElement {
    private int width;
    private int height;
    private double positionY; //this should never change after initialization
    private double positionX;
    private Rectangle paddle;

    /*
    Constructor for the Paddle.
     */
    public Paddle(double speedX, double speedY, Color color, int width, int height, double positionY, double positionX) {
        super(speedX, speedY, color);
        this.width = width;
        this.height = height;
        this.positionY = positionY;
        this.positionX = positionX;
        paddle = new Rectangle(positionX, positionY, width, height);
        paddle.setFill(color);
    }
    /*
    Returns the width of the paddle.
     */
    public int getWidth() {
        return this.width;
    }
    /*
    Sets the width of the paddle.
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
        paddle.setWidth(newWidth);
    }
    /*
    Gets the X position of this paddle.
     */
    public double getPositionX() {
        return this.positionX;
    }
    /*
    Sets the X position of this paddle.
     */
    public void setPositionX(double newPosition) {
        this.positionX = newPosition;
        paddle.setX(newPosition);
    }
    /*
    Gets the Y position of the paddle.
     */
    public double getPositionY() {
        return this.positionY;
    }
    /*
    Returns the JavaFX rectangle for this paddle.
     */
    public Rectangle getShape() { return this.paddle;}
    /*
    A custom feature of the paddle; if the paddle moves off the screen, it warps to the other side.
    */
    public void checkPaddleBounds(double elapsedTime, int size, double paddlevelocity) {
        double newPositionX = this.getPositionX() + paddlevelocity * elapsedTime;

        if (newPositionX < 0) {
            newPositionX = size - this.getWidth();
        } else if (newPositionX > size - this.getWidth()) {
            newPositionX = 0;
        }
        this.setPositionX(newPositionX);
    }
}
