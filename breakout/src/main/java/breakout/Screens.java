package breakout;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class that handles generation and retrieval of game screens such as game over and win. Also helps with formatting text.
 * @author: Yasha Doddabeel
 */
public class Screens {
    private int size;

    /*
    Constructor for the object: takes in the size of the screen as a param.
     */
    public Screens(int size) {
        this.size = size;
    }
    /*
    Returns the screen size.
     */
    public int getSize() {
        return this.size;
    }
    /*
    Formats the Text information provided to be displayed on a screen.
     */
    protected void formatInfo(Color color, int XPosition, int YPosition, Text info, int size) {
        info.setFill(color);
        info.setFont(Font.font("Georgia", FontWeight.BOLD, size));
        info.setLayoutX(XPosition);
        info.setLayoutY(YPosition);
    }
    /*
    Generates and returns a home screen with directions.
     */
    public Scene getHomeScreen(Color bg_color) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text title = new Text("Welcome to Breakout! \nPress the space bar to begin playing.\n");
        Text description = new Text("Rules: \n 1. Keep the ball alive by bouncing it on the paddle at the bottom of \n" +
                "the screen. Break all the blocks by hitting the ball against them \n to progress to the next level. \n\n" +
                "2. You only have 3 lives per level; if you lose them all, you lose the \ngame. If you progress through " +
                "all three levels, you win!\n\n 3. Some blocks drop poisons and power-ups. You obtain them \nby catching them with " +
                "your ball. \n\n 4. Move the paddle by pressing the arrow keys.");

        formatInfo(Color.WHITE, 0, 0, title, 30);
        formatInfo(Color.LIGHTSKYBLUE, 0, 0, description, 20);

        layout.getChildren().addAll(title, description);
        return new Scene(layout, this.size, this.size, bg_color);
    }
    /*
    Generates and returns a game over screen.
     */
    public Scene getGameOverScene(Color bg_color) {
        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        Text title = new Text("Game Over :( Press the Space Bar to try again! ");
        formatInfo(Color.WHITE, 0, 0, title, 35);
        layout.getChildren().add(title);

        return new Scene(layout, size, size, bg_color);
    }

}
