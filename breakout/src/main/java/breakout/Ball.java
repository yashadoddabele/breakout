package breakout;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A class representing a Ball object for the game. It is represented by a JavaFX circle shape.
 * @author: Yasha Doddabele
 */
public class Ball extends GameElement {
    private int radius;
    private Circle ball;

    /*
    A GameElement object representing a ball that is visualized by a JavaFX circle.
     */
    public Ball(double speedX, double speedY, Color color, int radius) {
        super(speedX, speedY, color);
        this.radius = radius;
        ball = new Circle(0, 0, radius);
        ball.setFill(color);
    }
    /*
    Returns the radius of the ball.
     */
    public int getRadius() {
        return this.radius;
    }
    /*
    Changes the radius of the ball to the input integer.
     */
    public void changeRadius(int newRadius) {
        this.radius = newRadius;
        ball.setRadius(newRadius);
    }
   /*
   Sets the X position of the ball.
    */
    public void setXPosition(double x) {
        ball.setCenterX(x);
    }
    /*
    Sets the Y position of the ball.
     */
    public void setYPosition(double y) {
        ball.setCenterY(y);
    }
    /*
    Gets the X position of the ball.
     */
    public double getXPosition() {
        return ball.getCenterX();
    }
    /*
    Gets the Y position of the ball.
     */
    public double getYPosition() {
        return ball.getCenterY();
    }
    /*
    Returns the JavaFX Circle of the ball.
     */
    public Circle getShape() { return this.ball;}

    /*
    Returns the new directions the ball should go depending on where it hit in the scene.
    Also checks for lives lost and decrements score/live count accordingly
     */
    public double[] checkBallBounds(int size, double directionX, double directionY, ScoreKeeper keeper, Paddle paddle) {
        //Check if the ball fell under the paddle - lose a life
        if (this.getYPosition() > size) {
            keeper.setLives(keeper.getLives() - 1);
            keeper.setScore(keeper.getScore() - 30);
            //Decrement score info
            keeper.updateLivesInfo();
            keeper.updateScoreInfo();
            this.setXPosition(paddle.getPositionX());
            this.setYPosition(paddle.getPositionY() - 100);
            directionX = 1;
            directionY = 1;
        }
        //Else, check if the ball hit any of the walls and change the direction accordingly
        else if (this.getXPosition() < 0 || this.getXPosition() > size) {
            directionX *= -1;
        }
        else if (this.getYPosition() < 0 || this.getYPosition() > size) {
            directionY *= -1;
        }
        return new double[]{directionX, directionY};
    }
}
