# Breakout Design
## NAME: Yasha Doddabele


## Design Goals
* I wanted to make it easy to add new levels and make each level easily configurable. Since there are so many ways to add 
variation to a game, I wanted one wrapper in main where you could add all differences for the game in one line (other than 
the text file for the level). Plus, it is easy to incorporate as many levels as you want in the actual game play until the 
user wins the whole game.
* I also wanted to make it easy to create magic items. These can have so much variation, so I wanted to add functionality
to keep that variation but also require you only adjust them in one line.
* Classes/objects should follow inheritance. This makes it easy to add new game items by just inheriting the high-level 
GameElement class.
* It should also be easy to use and create new screens that are formatted correctly, which the Screens class provides.

## High-Level Design
* The Ball, Block, MagicItem, and Paddle classes extend the GameElement class. These are all movable objects that the user 
interacts with by moving the paddle.
* The Level class initializes the entire set up and interaction of all game elements for the level. 
* The ScoreKeeper class contains information about the level, including its number, user's lives, and user's score. It is
initialized first to then be used to create the Level object for the level number, which it obtains from the ScoreKeeper.
* The Screens class is simply a class that holds methods to initialize and return Scene objects for the game.
* Balls can interact with paddles and blocks. When a ball hits a paddle, a collision is computed and the angle of its 
movement is then changed to its velocity. The paddle area is divided into sixths to add more specificity to the velocity of a
ball collision. When balls hit blocks, they decrement the hits left on the block and bounce off of them.
* Paddles can interact with magic items and balls. When a paddle hits a magic item, it collects it and its effects are applied
to the appropriate game element(s).
* The Main class iterates through the game through all states of gameplay – mainscreen, level, splash screen, game over, and
win. It contains the step function for all states and also a step function that updates game elements per elapsedTime. It 
interacts with the Level object, which contains all game elements, and the Screens class to display the appropriate scenes 
for each state.

## Assumptions or Simplifications
* I made assumptions that the Level would only have user-keys specified in the Level clas, which limited my ability to add
all cheat codes–especially ones that would need to be implemented in the main class.
* I also made simplifications regarding design so that all GameElements would use JavaFX shapes instead of images. This helped
me not only standardize methods/inheritance features for all of the classes, but it also made it easier to add new elements
such as the MagicItems class which uses a JavaFX circle.

## Changes from the Plan
* Paddle ideas: all plan ideas were implemented. Additionally, the paddle warps from one edge of the screen to another.
* Block ideas: all plan ideas were implemented.
* Power-up ideas: all plan ideas were implemented. Additionally, power-ups can change the width of the paddle.
* Cheat keys: two of these ideas were implemented: the extra life (L key), and the current level fully clears (W). The other two 
were not implemented, but two other cheat keys were implemented: extra points (P), and resetting ball/paddle positions (R).
Unfortunately, I couldn't implement all 6 cheat keys, so I was only able to do four.
* Level descriptions: All blocks, when exploded, give +1 hits to their surrounding neighbor blocks. This was one change from
the plan. Another change is that there is no variation of this hit amount: it is +1 hit regardless of what level the block
exploded in. Furthermore, level 3 does not have blocks that shoot lasers. The changes between levels are as follows:
  * Level 1: Blocks have hit values from 1-5. They can only have power-ups, and there are 2 power-ups. Power-ups are distributed
  randomly between all the blocks in the level for each game.
  * Level 2: Blocks have hit values from 1-5. There are 2 power-ups and 1 poison, both of which are indistinguishable from 
  one another. The ball is faster and the paddle is smaller.
  * Level 3: Blocks have hit values from 1-6. There are 2 power-ups and 2 poisons. The ball is faster and the paddle is smaller.s
  * All levels have unique block configurations.
* Class ideas: all class ideas were implemented. The Ball functionality is different in that it doesn't have an "alive" characteristic.
The Power-up/MagicItem class does not take in a Ball as a parameter, and the level class is mostly the same.

## How to Add New Levels
* You simply add a new line of code within the generateLevelObjects() function in Main that is a call to the configureLevel
function. Once you do this call and specify your parameters for the level, the rest of the program handles implementing it into
the game and generating a scene.

