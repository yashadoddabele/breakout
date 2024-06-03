package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Class representing a Block object in game play, visually represented by a JavaFX rectangle.
 * @author: Yasha Doddabele
 */
public class Block extends GameElement {
    private int height;
    private int width;
    private int hits;
    private double positionX;
    private double positionY;
    private Rectangle block;
    private MagicItem item;
    private HashMap<Integer, Color> mapping = new HashMap<>();

    /*
    A GameObject represented by a JavaFX rectangle that signifies a block in game.
     */
    public Block(int height, int width, int hits, double positionX, double positionY, MagicItem item) {
        super(0, 0, Color.GREEN);
        this.height = height;
        this.width = width;
        this.hits = hits;
        this.positionX = positionX;
        this.positionY = positionY;
        this.item = item;
        block = new Rectangle(positionX, positionY, width, height);
        block.setX(positionX);
        block.setY(positionY);

        //Populates a HashMap that maps a color to the number of hits remaining on a block.
        mapping.put(1, Color.RED);
        mapping.put(2, Color.ORANGE);
        mapping.put(3, Color.YELLOW);
        mapping.put(4, Color.CHARTREUSE);
        mapping.put(5, Color.GREEN);
        mapping.put(6, Color.DARKGREEN);

        block.setFill(mapping.get(hits));
    }
    /*
    Returns the height of the block.
     */
    public int getHeight() {
        return this.height;
    }
    /*
    Returns the width of the block.
     */
    public int getWidth() {
        return this.width;
    }
    /*
    Returns the X position of this block.
     */
    public double getPositionX() {
        return this.positionX;
    }
    /*
    Returns the Y position of this block.
     */
    public double getPositionY() {
        return this.positionY;
    }
    /*
    Returns the number of hits left on this block.
     */
    public int getHits() {
        return this.hits;
    }
    /*
    Sets the hit amount of this block to the new number.
     */
    public void setHits(int newHits) {
        this.hits = newHits;
    }
    /*
    Changes the fill of the block based on the HashMap mapping.
     */
    public void changeFill() {
        if (mapping.get(this.hits) == null)
            block.setFill(Color.BLACK);
        else {
            Color cur = mapping.get(this.hits);
            this.setColor(cur);
            block.setFill(cur);
        }
    }
    /*
    Returns the JavaFX rectangle for this block.
     */
    public Rectangle getShape() { return this.block; }
    /*
    Returns the magic item for this block if it has one.
     */
    public MagicItem getItem() {
        return item;
    }
    /*
    Sets the magic item of this block to the input item.
     */
    public void setItem(MagicItem newitem) {
        this.item = newitem;
    }
    /*
    Finds all blocks within a 200px radius of the current block and adds +1 hit to them.
    This is my variation of the exploding block.
     */
    public void explodeBlock(ArrayList<Block> blockmap) {
        double blockX = this.getPositionX();
        double blockY = this.getPositionY();

        for (Block block: blockmap) {
            double curX = block.getPositionX();
            double curY = block.getPositionY();
            double distance = Math.sqrt(Math.pow(curX - blockX, 2) + Math.pow(curY - blockY, 2));

            if (block != this) {
                if (distance <= 200.0) {
                    block.setHits(block.getHits() + 1);
                    block.changeFill();
                }
            }
        }
    }
}
