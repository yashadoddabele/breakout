package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;


/**
 * Main class representing the Game play for Breakout. Manages each state of the game.
 *
 * @author Yasha Doddabele
 */
public class Main extends Application {
    public static final Color BG_COLOR = Color.rgb(71, 51, 51);
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double BALL_SPEED = 400;
    public static final double PADDLE_SPEED = 500;
    private static final int PADDLE_LENGTH = 150 ;
    private static final int SIZE = 1000;
    private static final int BALL_RADIUS = 17;
    private Level[] levels = new Level[3];
    private Scene[] levelScenes = new Scene[3];
    private Stage stage;
    private Screens screens = new Screens(SIZE);
    private Scene currentScene;
    private int curLevel = 1;
    private int totalLevels = 3;
    private ScoreKeeper keeper;
    private String iteration = "MAIN";
    //Can be "START", "MAIN", "GAMEOVER", "LEVEL", "SPLASH", or "WIN"

    /*
     * Initialize what will be displayed. Sets the timeline.
     */
    @Override
    public void start (Stage stage) {
        this.stage = stage;
        this.keeper = new ScoreKeeper(3, 0,  1);

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();

        //Display the home screen and start the game with new levels
        this.currentScene = screens.getHomeScreen(BG_COLOR);
        stage.setScene(currentScene);
        stage.show();
        generateLevelObjects();
        levelScenes[0] = levels[0].setUpScene(generateMagicItems(0));
    }

    /*Step function for the timeline: handles all cases of game play
    */
    public void step (double elapsedTime) {
        switch (iteration) {
            case "MAIN":
                currentScene.setOnKeyPressed(e -> handleKeyPress(e.getCode(), levelScenes[curLevel-1], stage, "LEVEL"));
                break;
            case "LEVEL":
                stepGame(elapsedTime);
                break;
            case "GAMEOVER":
                currentScene.setOnKeyPressed(e -> handleKeyPress(e.getCode(), screens.getHomeScreen(BG_COLOR), stage, "MAIN"));
            case "SPLASH":
                break;
            case "WIN":
                currentScene.setOnKeyPressed(e -> handleKeyPress(e.getCode(), screens.getHomeScreen(BG_COLOR), stage, "MAIN"));
                break;
        }
    }
    /* Conducts a step for each iteration of gameplay when player is in a level.
     Handles all collisions, points, lives, etc. and determines if the player has encountered game over or splash/
     win screen.
     */
    private void stepGame(double elapsedTime) {
        Level level = levels[curLevel-1];
        if (level.getActive()) {
            Paddle paddle = level.getPaddle();

            //Set ball positions for this iteration
            level.setBallPositions(elapsedTime);

            //Check for paddle collision, orient ball properly depending on where it hit the paddle
            level.orientBallCollision();

            //Check for block collision
            level.checkBlockCollision();

            //Check if it hit any walls or went out
            level.changeBallDirections(SIZE);

            //Set paddle position, and check if paddle went out of bounds; then we need to warp
            paddle.checkPaddleBounds(elapsedTime, SIZE, level.getPaddlevelocity());

            //Check if items were encountered
            level.updateItemCollisions(elapsedTime);

            //If game over, we need to stop the timeline and change the scene to GameOver scene
            if (level.gameOver()) {
                level.removeAllNodes();
                this.iteration = "GAMEOVER";
                level.switchActive();
                this.displayGameOver();
            // If level is won, switch to splash screen
            } else if (level.wonLevel()) {
                level.removeAllNodes();
                this.iteration = "SPLASH";
                curLevel++;
                level.switchActive();
                this.displaySplashScreen(level);
            }
        }
    }
    /*
    Configures a level based on a number of controllable specifications. Creates and returns a new Level object
    representing the specific level.
     */
    private Level configureLevel(Color bg_color, int screenSize, double ballSpeed, Color ballColor,
                                 int ballRadius, double paddleSpeed, Color paddleColor, int paddleLength,
                                 int levelNum, ScoreKeeper keeper) {
        //Set up all required specified objects for the level
        Ball ball = new Ball(ballSpeed, ballSpeed, ballColor, ballRadius);
        Paddle paddle = new Paddle(paddleSpeed, paddleSpeed, paddleColor, paddleLength,
                20, screenSize-(screenSize/5), screenSize / 2);

        Level lev = new Level(bg_color, screenSize, screenSize/20, screenSize/10 + 50,
                ball, paddle, keeper, levelNum);
        //Sets the ball to be in the correct start position
        lev.getBall().setXPosition(paddle.getPositionX());
        lev.getBall().setYPosition(paddle.getPositionY() - ball.getRadius());
        return lev;
    }
    /*
    Manages user key changes for this game: for all instances outside of the level state, the key used to control
    moving between states is the space bar.
    Also switches states depending on the String iter, which represents the next state.
     */
    private void handleKeyPress (KeyCode code, Scene level, Stage stage, String iter) {
        if (code == KeyCode.SPACE) {
            //If the next state should be main
            if (iter.equals("MAIN")) {
                //Need to generate a new game
                this.keeper = new ScoreKeeper(3, 0, 1);
                generateLevelObjects();
                levelScenes[0] = levels[0].setUpScene(generateMagicItems(0));
                curLevel = 1;
            }
            //For any, set the current scene to the level of the next scene
            this.currentScene = level;
            stage.setScene(level);
            stage.show();
            this.iteration = iter;
        }
    }
    /*
    Sets up and displays the GameOver scene.
     */
    private void displayGameOver() {
        Scene gameOverScene = screens.getGameOverScene(BG_COLOR);
        this.currentScene = gameOverScene;
        stage.setScene(gameOverScene);

        stage.show();
    }
    /*
        Sets up and displays the Splash scene. Determines based on current level if the user has won the game or not
        and displays the correct scene accordingly.
    */
    private void displaySplashScreen(Level level) {
        Scene splashScreen = level.getKeeper().getSplashScreen(BG_COLOR, this.screens, level.getLevelNum());
        stage.setScene(splashScreen);
        this.currentScene = splashScreen;

        if (curLevel <= 3) {
            levelScenes[curLevel-1] = levels[curLevel-1].setUpScene(generateMagicItems(1));
            splashScreen.setOnKeyPressed(e -> handleKeyPress(e.getCode(), levelScenes[curLevel-1],
                            this.stage, "LEVEL"));
            stage.show();
        }
        else {
            iteration = "WIN";
            displayWinScreen();
        }
    }
    /*
    Displays and generates the win screen.
     */
    private void displayWinScreen() {
        Scene winScreen = keeper.getWinScreen(BG_COLOR, screens);
        stage.setScene(winScreen);
        this.currentScene = winScreen;
        stage.show();
    }
    /*
        Generates all levels based on the number of totalLevels, a private variable for the class. This is where someone
        could add a new level, and this is the only piece of code they would have to alter (unless they wanted more
        powerups. Also sets up the scenes for each level.
     */
    private void generateLevelObjects() {
        for (int level=1; level<=totalLevels; level++) {
            switch (level) {
                case 1:
                    levels[0] = configureLevel(BG_COLOR, SIZE, BALL_SPEED, Color.LIGHTSTEELBLUE, BALL_RADIUS,
                            PADDLE_SPEED, Color.WHITE, PADDLE_LENGTH, 1, keeper);
                    break;
                case 2:
                    levels[1] = configureLevel(BG_COLOR, SIZE, BALL_SPEED * 1.2, Color.LIGHTSTEELBLUE, BALL_RADIUS,
                            PADDLE_SPEED, Color.WHITE, PADDLE_LENGTH - 20, 2, keeper);
                    break;
                case 3:
                    levels[2] = configureLevel(BG_COLOR, SIZE, BALL_SPEED * 1.3, Color.LIGHTSTEELBLUE, BALL_RADIUS,
                            PADDLE_SPEED, Color.WHITE, PADDLE_LENGTH -30, 3, keeper);
            }
        }
    }
    /*
        Generates all magic items to be used for the game level. Someone could add more items if they so wished. The number of
        poisons is determined by the parameter num poisons, which adds the specified number of poisons to the level.
     */
    private ArrayList<MagicItem> generateMagicItems(int numPoisons) {
        ArrayList<MagicItem> powers = new ArrayList<>();
        //A Magic Item that decreases ball speed
        powers.add(new MagicItem(0.0, 100.0, Color.HOTPINK, 0.5, 1, 1,
                9.0, false));
        //A Magic item that increases paddle width
        powers.add(new MagicItem(0.0, 100.0, Color.HOTPINK, 1, 1, 2.0,
                9.0, false));
        //A Magic item that increases ball size
        powers.add(new MagicItem(0.0, 100.0, Color.HOTPINK, 1, 1.5, 1,
                9.0, false));

        //Adds specified number of poisons which increase ball speed and decrease ball size
        while (numPoisons > 0) {
            //Magic item that speeds up ball and decreases radius
            powers.add(new MagicItem(0.0, 100.0, Color.HOTPINK, 1.5, 0.7, 1,
                    9.0, false));
            numPoisons--;
        }
        return powers;
    }
}
