package breakout;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * The general class to represent any game component. All GameElements inherit this class.
 */
public class GameElement {

    private double speedX;
    private double speedY;
    private Color color;

    /*
    Constructor for the object.
     */
    public GameElement(double speedX, double speedY, Color color) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = color;
    }
    /*
    Returns the speed in the X direction for this object.
     */
    public double getSpeedX() {
        return this.speedX;
    }
    /*
    Returns the speed in the Y direction for this object.
     */
    public double getSpeedY() {
        return this.speedY;
    }
    /*
    Sets the X speed for this object.
     */
    public void setSpeedX(double newSpeedX) {
        this.speedX = newSpeedX;
    }
    /*
    Sets the Y speed for this object.
     */
    public void setSpeedY(double newSpeedY) {
        this.speedY = newSpeedY;
    }
    /*
    Sets the color for this object.
     */
    public void setColor(Color newColor) {
        this.color = newColor;
    }
}
