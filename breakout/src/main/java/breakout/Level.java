package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that handles all game level mechanics, objects, and gameplay for the specified level. Handles all collisions,
 * changing of positions, and lives/scores for the given iteration.
 * @author: Yasha Doddabele
*/
public class Level {
    private Scene scene;
    private Pane root;
    private ArrayList<Block> blockmap = new ArrayList<>();
    private int SIZE;
    Color bg_color;
    private Ball ball;
    private Paddle paddle;
    private double directionX = 1;
    private double directionY = 1;
    private boolean active;
    private ScoreKeeper keeper;
    private double paddlevelocity = 0.0;
    private Screens screens = new Screens(SIZE);
    private ArrayList<MagicItem> activeItems = new ArrayList<>();
    private int levelNum;

    /*
    Constructor for the class. Depending on the level specified in the ScoreKeeper object for this level, the constructor
    finds the correct text file for the block configuration and populates the blockmap for this level.
     */
    public Level(Color bg_color, int SIZE, int blockheight, int blockwidth, Ball ball, Paddle paddle, ScoreKeeper keeper, int levelNum) {
        this.bg_color = bg_color;
        this.ball = ball;
        this.paddle = paddle;
        this.SIZE = SIZE;
        this.keeper = keeper;
        this.active = true;
        this.levelNum = levelNum;
        keeper.setLevel(levelNum);

        String levelFile = "/level" + this.keeper.getLevel() + ".txt";
        InputStream stream  = Level.class.getResourceAsStream(levelFile);

        if (stream == null)
            System.out.println("help");

        else {
            //Populate the blockmap list from the input text file
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line = reader.readLine();
                int row = 0;
                int positionY;
                int positionX;

                while (line != null) {
                    positionY = (SIZE / 10) * row;
                    for (int i = 0; i < line.length(); i++) {
                        positionX = (SIZE / 10) * i;
                        if (line.charAt(i) != '0' && line.charAt(i) != ' ') {
                            blockmap.add(new Block(blockheight, blockwidth, line.charAt(i) - '0', positionX, positionY, null));
                        }
                    }
                    line = reader.readLine();
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    Returns the number of this level.
     */
    public int getLevelNum() {
        return this.levelNum;
    }
    /*
    Returns the ScoreKeeper for this Level.
     */
    public ScoreKeeper getKeeper() {
        return this.keeper;
    }
    /*
    Returns the Ball for this Level.
     */
    public Ball getBall() {
        return this.ball;
    }
    /*
    Returns the Paddle for this Level.
     */
    public Paddle getPaddle() {
        return this.paddle;
    }
    /*
    Returns the paddle velocity for this level.
     */
    public double getPaddlevelocity() {
        return paddlevelocity;
    }
    /*
    Returns if the current level is active or not.
     */
    public boolean getActive() {
        return this.active;
    }
    /*
    Switches the active status for this level.
     */
    public void switchActive() {
        this.active = !active;
    }
    /*
    Sets up and returns the Scene object representing this level's configurations.
     */
    public Scene setUpScene(List<MagicItem> powers) {
        //Scene needs a new root every time
        this.root = new Pane();

        this.scene = new Scene(this.root, this.SIZE, this.SIZE, this.bg_color);

        //Get score and lives count projected
        screens.formatInfo(Color.WHITE, 40, 60, keeper.getScoreInfo(), 16);

        Text levelInfo = new Text("Level: " + this.levelNum);
        screens.formatInfo(Color.WHITE, 40, 80, levelInfo,16);

        screens.formatInfo(Color.WHITE, 40, 40, keeper.getLivesInfo(), 16);

        //Add elements to pane
        this.root.getChildren().addAll(ball.getShape(), paddle.getShape(), keeper.getLivesInfo(), keeper.getScoreInfo(), levelInfo);

        keeper.updateScoreInfo();
        keeper.updateLivesInfo();


        // Add blocks to the scene
        for (Block block : blockmap) {
            this.root.getChildren().add(block.getShape());
        }

        //Handle key stuff
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));

        this.populateMagicItems(powers);

        return scene;
    }
    /*
    Sets the ball positions for the current iteration depending on the elapsedTime.
     */
    public void setBallPositions(double elapsedTime) {
        ball.setXPosition(ball.getXPosition() + ball.getSpeedX() * directionX * elapsedTime);
        ball.setYPosition(ball.getYPosition() + ball.getSpeedY() * directionY * elapsedTime);
    }
    /*
    Changes the ball directions depending on which walls/objects the ball hits. The input size is the size of the screen.
     */
    public void changeBallDirections(int size) {
        double[] newDirections = ball.checkBallBounds(size, directionX, directionY, keeper, paddle);
        directionX = newDirections[0];
        directionY = newDirections[1];
    }
    /*
    Randomizes the population of magic items within bricks. Items are provided in the input list.
     */
    public void populateMagicItems(List<MagicItem> items) {
        int numBlocks = blockmap.size();
        while (!items.isEmpty()) {
            int index = (int) (Math.random() * numBlocks);
            //Find a random block, and if it doesn't have a magic item, add the current item to it
            if (blockmap.get(index).getItem() == null) {
                Block cur = blockmap.get(index);
                MagicItem curItem = items.remove(0);
                //Set the block to the magic item and vice versa
                cur.setItem(curItem);

                //Temporarily remove block from root so that we can layer it on top of the item
                root.getChildren().remove(cur.getShape());
                curItem.setXPosition(cur.getPositionX()+(cur.getWidth()/2));
                curItem.setYPosition(cur.getPositionY()+(cur.getHeight()/2));

                //Add the magic item to the pane
                root.getChildren().addAll(curItem.getShape(), cur.getShape());
            }
        }
    }
    /*
    Changes the paddle's X movement based on user input (left or right arrow keys). Paddle cannot move up or down.
     */
    private void handleKeyPress (KeyCode code) {
           if (code == KeyCode.RIGHT)
               this.paddlevelocity = this.paddle.getSpeedX();
           else if (code == KeyCode.LEFT)
                this.paddlevelocity = -1 * this.paddle.getSpeedX();
           //Cheat 1: reset ball and paddle positions
           else if (code == KeyCode.R) {
               paddle.setPositionX(SIZE / 2);
               ball.setXPosition(SIZE / 2);
               ball.setYPosition(SIZE /2);
               this.paddlevelocity = 1;
           }
            //Cheat 2: clear the level
           else if (code == KeyCode.W)
               blockmap = new ArrayList<>();
            //Cheat 3: add a life
           else if (code == KeyCode.L) {
               keeper.setLives(keeper.getLives() + 1);
               keeper.updateLivesInfo();
           }
           //Cheat 4: increase points by 100
           else if (code == KeyCode.P) {
               keeper.setScore(keeper.getScore() + 100);
               keeper.updateScoreInfo();
           }
    }
    /*
    Handles what happens when the paddle keys are released. (Paddle stops)
     */
    private void handleKeyRelease (KeyCode code) {
        switch (code) {
            case RIGHT -> this.paddlevelocity = 0.0;
            case LEFT -> this.paddlevelocity = 0.0;
        }
    }
    public void removeAllNodes() {
        this.root.getChildren().clear();
    }
    /*
    Splits the paddle into 6 areas and dynamically changes the ball velocity depending on which area it hit the paddle.
     */
    public void orientBallCollision() {
        Shape intersection = Shape.intersect(ball.getShape(), paddle.getShape());
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            double sixth = paddle.getWidth() / 6.0;
            if (ball.getXPosition() < paddle.getPositionX() + sixth) {
                directionX = -1;
                directionY *= -1;
            } else if (ball.getXPosition() < paddle.getPositionX() + 2 * sixth) {
                directionX = -0.5;
                directionY *= -1;
            } else if (ball.getXPosition() < paddle.getPositionX() + 3 * sixth) {
                directionX = 0;
                directionY *= -1;
            } else if (ball.getXPosition() < paddle.getPositionX() + 4 * sixth) {
                directionX = 0.5;
                directionY *= -1;
            } else if (ball.getXPosition() < paddle.getPositionX() + 5 * sixth) {
                directionX = 1;
                directionY *= -1;
            } else {
                directionX = 1;
                directionY *= -1;
            }
        }
    }
    /*
    Returns true if the player's lives are 0, false otherwise.
     */
    public boolean gameOver() {
        if (this.keeper.getLives() == 0) {
            return true;
        }
        return false;
    }
    /*
    Returns if the user destroyed all blocks in the level or not.
     */
    public boolean wonLevel() {
        return this.blockmap.isEmpty();
    }
    /*
    Checks for paddle collisions with any activated magic items. If there are, the method applies the item's properties
    to whatever applicable objects.
     */
    public void updateItemCollisions(double elapsedTime) {
        //Move all active magic items continuously
        Iterator<MagicItem> iterator = activeItems.iterator();
        while (iterator.hasNext()) {
            MagicItem item = iterator.next();
            if (!item.getActivated()) {
                iterator.remove();
                continue;
            }
            //Check bounds of item, including if it intersected with the ball–in which case we apply the magic
            //to the ball and/or paddle
            item.setYPosition(item.getYPosition() + item.getSpeedY() * elapsedTime);
            item.checkItemBounds(SIZE, root, ball, paddle, iterator);
        }
    }
    /*
    Checks for a collision between the ball and a block, and updates scores/block hits accordingly.
     */
    public void checkBlockCollision() {
        Iterator<Block> iterator = blockmap.iterator();
        while (iterator.hasNext()) {
            Block block = iterator.next();
            Shape collision = Shape.intersect(ball.getShape(), block.getShape());
            if (collision.getBoundsInLocal().getWidth() != -1) {
                // Increment score
                keeper.setScore(keeper.getScore() + 10);
                keeper.updateScoreInfo();
                block.setHits(block.getHits() - 1);
                block.changeFill();
                directionY *= -1;
                directionX *= -1;

                // Remove block from the scene; if it has a power up/down, add the item to the root
                if (block.getHits() == 0) {
                    iterator.remove();
                    this.root.getChildren().remove(block.getShape());
                    //Explode nearby blocks (add +1 hit to all of them)
                    block.explodeBlock(blockmap);
                    if (block.getItem() != null) {
                        // Item is now active
                        block.getItem().switchActivated();
                        activeItems.add(block.getItem());
                    }
                }
            }
        }
    }
}
