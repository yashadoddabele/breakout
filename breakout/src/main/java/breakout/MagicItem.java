package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.util.Iterator;

/**
 * A class to represent both power-ups and poisons. They fall from a specified Block object and can impact both
 * Ball and Paddle objects.
 * @author: Yasha Doddabele
 */
public class MagicItem extends GameElement {
    private double speedImpact;
    private double sizeImpact;
    private double timeApplied;
    private Circle item;
    private double paddleSizeImpact;
    private boolean activated;

    /*
    Constructor for the magic item. There are several factors that can be specified to make both power ups and poisons.
    If a power up simply impacts one feature on one block, you can put in 1 so there are no changes to any other objects/
    factors.
     */
    public MagicItem(double speedX, double speedY, Color color, double speedImpact, double sizeImpact, double
                     paddleSizeImpact, double timeApplied, boolean activated) {
        super(speedX, speedY, color);
        this.speedImpact = speedImpact;
        this.sizeImpact = sizeImpact;
        this.paddleSizeImpact = paddleSizeImpact;
        this.timeApplied = timeApplied;
        item = new Circle(-1, -1, 15);

        //Put the magic item at the position of the block it's in
        item.setFill(color);
        this.activated = false;
    }
    /*
    Sets the X position of this item.
     */
    public void setXPosition(double x) {
        item.setCenterX(x);
    }
    /*
    Sets the Y position of this item.
     */
    public void setYPosition(double y) {
        item.setCenterY(y);
    }
    /*
    Gets the Y position of this item. (You only need this because the item falls straight down)
     */
    public double getYPosition() {
        return item.getCenterY();
    }
    /*
    Returns if the item is activated (released from the block) or not.
     */
    public boolean getActivated() {
        return this.activated;
    }
    /*
    Switches the activeness of the item.
     */
    public void switchActivated() {
        this.activated = !activated;
    }
    /*
    Returns the JavaFX circle for this item.
     */
    public Circle getShape() { return this.item;}

    /*
    Applies the effects of the magic item to the input ball.
     */
    public void applyItemtoBall(Ball ball) {
        ball.setSpeedX(ball.getSpeedX() * this.speedImpact);
        ball.setSpeedY(ball.getSpeedY() * this.speedImpact);

        ball.changeRadius((int) (ball.getRadius() * this.sizeImpact));
    }
    /*
    Applies the effects of the magic item to the input paddle.
     */
    public void applyItemtoPaddle(Paddle paddle) {
        paddle.setWidth((int) (paddle.getWidth() * this.paddleSizeImpact));
    }
    /*
    Item will always only fall vertically
    Checks if an item was missed or was collected by the player/paddle
     */
    public void checkItemBounds(int size, Pane root, Ball ball, Paddle paddle, Iterator<MagicItem> activeItems) {
        int origPaddleWidth = paddle.getWidth();
        double origBallSpeed = ball.getSpeedX();
        int origBallSize = ball.getRadius();

        // Fell out of the screen
        if (this.getYPosition() > size) {
            root.getChildren().remove(item);
            this.switchActivated();
            activeItems.remove();
            return;
        }
        // Interacted with ball: apply it to paddle/ball or both, whichever applies
        Shape intersection = Shape.intersect(paddle.getShape(), item);
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            this.applyItemtoBall(ball);
            this.applyItemtoPaddle(paddle);
            this.switchActivated();
            root.getChildren().remove(item);
            //Create new timeline to apply the effects for the item's duration
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(timeApplied), e -> {
                        // Revert changes after the time is up
                        this.revertAllItems(ball, paddle, origBallSpeed, origBallSize, origPaddleWidth, this);
                    })
            );
            timeline.play();
        }
    }
    /*
    Reverts the effects of the item on all objects it impacted once the duration of the item is over.
     */
    private void revertAllItems(Ball ball, Paddle paddle, double origBallSpeed, int origBallSize, int origPaddleWidth,
                                MagicItem item) {
        ball.changeRadius(origBallSize);
        ball.setSpeedX(origBallSpeed);
        ball.setSpeedY(origBallSpeed);
        paddle.setWidth(origPaddleWidth);
        item.switchActivated();
    }
}
