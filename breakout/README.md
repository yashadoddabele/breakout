# breakout
## Yasha Doddabele


DO NOT FORK THIS REPOSITORY, clone it directly to your computer.


This project implements the game of Breakout with multiple levels.

### Timeline

 * Start Date: 01/16/24

 * Finish Date: 01/23/24

 * Hours Spent: 25


### Running the Program

 * Main class: Main.java. Running this should launch the program.

 * Data files needed: You shouldn't need anything outside of this repository. Outside of any Java files within the breakout file,
you need the text files that hold configurations of blocks for each level found under the resources folder.

 * Key/Mouse inputs: 
   * Left and right arrow keys: move the paddle back and forth.
   * Space bar: navigate through different states of the game
   * There are no mouse inputs other than closing the game screen.

* Cheat codes
    * R: reset the paddle and ball positions (non custom)
    * L: add an extra life (non custom)
    * P: add 100 extra points to the score (custom)
    * W: clear the current level (custom)

### Notes/Assumptions

 * Assumptions or Simplifications:
   * I made assumptions that the Level would only have user-keys specified in the Level clas, which limited my ability to add
          all cheat codes–especially ones that would need to be implemented in the main class. 
   * I also made simplifications regarding design so that all GameElements would use JavaFX shapes instead of images. This helped
  me not only standardize methods/inheritance features for all of the classes, but it also made it easier to add new elements
  such as the MagicItems class which uses a JavaFX circle.

 * Known Bugs:
   * There are no known bugs.

 * Features implemented:
   * All core features were implemented.
   * Score keeping: when a block is hit, a player gains 10 points. The lives, level number, and score count are displayed on
   the game screen.
   * Splash screen: There is a splash screen at the end of each level's completion that details all the lives/points stats.
   * Results screen: There is a winning screen at the end of the game detailing the player's winning stats.
   * Paddle behaviors:
     * The paddle warps from one side to the other. (Custom)
     * The paddle has positional bounces.
   * Block behaviors:
     * When destroyed, blocks give +1 hits to their neighbors. (Custom)
     * Blocks take multiple hits to be destroyed.
     * When a block is destroyed, it releases a power up the user can catch with their paddle.
   * Power ups:
     * Paddle extension
     * Speed up ball + shrink radius
     * Custom (ball goes slower)
   * Cheat keys:
     * L (lives +1)
     * R (reset paddle and ball positions)
     * W (clear the whole level, custom)
     * P (add 100 points to score, custom)

 * Features unimplemented:
   * The remaining two cheat keys. I was able to get only 4/6 completed.

 * Noteworthy Features:
   * Levels obtain block mappings from text files that specify positions and hit amounts. Additionally, magic items are 
   populated randomly to the blocks for every level of the game so a user cannot memorize where they are. 
   * There are splash screens between every level highlighting level stats, as well as game over and win screens.
   * Blocks change colors depending on how many hits they have left.
   * Powerups are indistinguishable from poisons, which makes it hard to predict which is which and increases risk taking!
   * The paddle is split into sixths to increase variation in positional bounces.

