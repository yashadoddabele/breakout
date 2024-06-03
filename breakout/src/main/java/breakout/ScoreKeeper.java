package breakout;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * A class that keeps track of all information regarding Level number, score count, and lives count.
 * Also handles displaying this information in text/screens.
 * @author: Yasha Doddabele
 */
public class ScoreKeeper {
    private int lives;
    private int score;
    private Text scoreInfo;
    private Text livesInfo;
    private int level;

    /*
    Constructor for the keeper object. You specify the number of the level here, so the keeper must be initialized before
    the Level object.
     */
    public ScoreKeeper(int lives, int score, int level) {
        this.lives = lives;
        this.score = score;
        this.level = level;
        livesInfo = new Text("Lives: 3");
        scoreInfo = new Text("Score: 0");
    }
    /*
    Returns the number of the level this keeper keeps track of.
     */
    public int getLevel() {
        return this.level;
    }
    /*
    Sets the level of this keeper.
     */
    public void setLevel(int newLevel) {
        this.level = newLevel;
    }
    /*
    Returns the Text representing information on the lives left of the user for this level.
     */
    public Text getLivesInfo() {
        return this.livesInfo;
    }
    /*
    Returns the Text representing information on the score of the user for this level.
     */
    public Text getScoreInfo() {
        return this.scoreInfo;
    }
    /*
    Updates the Text information on the lives when the lives are changed.
     */
    public void updateLivesInfo() {
        livesInfo.setText("Lives: " + this.lives);
    }
    /*
    Updates the Text information on the score when the score is changed.
     */
    public void updateScoreInfo() {
        scoreInfo.setText("Score: " + this.score);
    }
    /*
    Returns the lives left of the user for this level.
     */
    public int getLives() {
        return this.lives;
    }
    /*
    Sets the lives of the user for this level.
     */
    public void setLives(int newLives) {
        this.lives = newLives;
    }
    /*
    Returns the score of the user for this level.
     */
    public int getScore() {
        return this.score;
    }
    /*
    Sets the score of the user for this level.
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }
    /*
    Generates and returns a splash screen for this level upon completion.
     */
    public Scene getSplashScreen(Color bg_color, Screens screens, int level) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text title = new Text("You win Level " + level + "!\n\nPress space to continue to the next level.\n\n");
        Text stats = new Text("Score: " + this.score + "\n\nLives: " + this.lives);

        screens.formatInfo(Color.WHITE, 0, 0, title, 30);
        screens.formatInfo(Color.LIGHTSKYBLUE, 0, 0, stats, 20);

        layout.getChildren().addAll(title, stats);
        return new Scene(layout, screens.getSize(), screens.getSize(), bg_color);
    }
    /*
    Generates and returns a win screen.
     */
    public Scene getWinScreen(Color bg_color, Screens screens) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text title = new Text("You win! Press the Space Bar to play again! :)\n\n");
        Text stats = new Text("Score: " + this.score + "\n\nLives: " + this.lives);

        screens.formatInfo(Color.WHITE, 0, 0, title, 30);
        screens.formatInfo(Color.LIGHTSKYBLUE, 0, 0, stats, 20);

        layout.getChildren().addAll(title, stats);

        return new Scene(layout, screens.getSize(), screens.getSize(), bg_color);
    }
}
