# Breakout Plan
### Yasha Doddabele

## Interesting Breakout Variants

 * Brick Breaker Hero: I enjoyed this variant because it has an element of the blocks "fighting back". In other words, 
there is more than one way to lose, not just by missing the ball, and it feels like there is an enemy with a "boss" 
block fighting back. I think it would be fun to try and incorporate a mystery block per certain iteration that fires a
laser at the paddle.
 * Brick Breaker Escape: I also liked this variant because of the power up additions. Most games have the change within 
the blocks (certain blocks take more hits to break), but the power ups change the ball itself so that it can be bigger, 
slower, etc. It adds more variety to the game. I also thought about a potential idea where a block drops a power-down 
that you have to avoid (like a poison that shrinks the ball for example).


## Paddle Ideas

 * The ball should bounce in a different direction depending on where its hit. If hit in the center, it will bounce 
 * normally: if hit on the left or right, it will bounce the direction it came from.

 * The ball bounce off of the edge when it reaches one, at the opposite direction it came from.


## Block Ideas

 * Blocks take multiple hits to be fully broken

 * Blocks drop power-ups and poisons

 * When some blocks explode, every block around them gets +1 strength by absorbing its pieces


## Power-up Ideas

 * The ball increases in size and is able to cause more damage to blocks

 * The ball slows down 

 * Poison idea: the ball speeds up and shrinks


## Cheat Key Ideas

 * The player gets 1 extra life

 * A power-up is dropped

 * A random block is fully broken

 * The current level fully clears


## Level Descriptions

 * Level 1: Level one is a basic brick-breaker game that has a ball, normal paddle, and blocks that 
take multiple hits to break. It will only have power-ups in the blocks. A few blocks, when exploded, give +1 strength
to its neighbors.

 * Level 2: Ball moves faster, and blocks can now drop power-downs (poisons). Half the blocks, when exploded, gives +1 
strength to its neighbors.

 * Level 3: Ball moves faster, and there are a few blocks that shoot lasers at the ball (2-3). There are still power-ups
and poisons. Half the blocks, when exploded, gives +1.5 strength to its neighbors. No cheat codes can be used.


## Class Ideas

 * Class 1: Block
   * This is a class representing a block object. It will be visually represented as a Rectangle JFX object.
   It has attributes like strength and size. It will need a changeStrength method that decrements its strength every
   time a ball hits it.

 * Class 2: Ball
   * This is a class representing the ball object. It will be visually represented as a Circle JFX object. It has
   attributes like speed and size, as well as currentPower and alive status. It will need methods like changeSize 
   and changeSpeed that are updated when a ball interacts with a power-up or poison.

 * Class 3: Power-Changer
   * This is a class representing power-ups and downs. It will have attributes like timeApplied, and possibly null or 
   zero values of speed/size changes. It will have a method that takes in a Ball object as a parameter and changes the
   size or speed depending on the power-changer's speedChange and/or sizeChange attributes.

 * Class 4: Level
   * This is a class representing the level (1-3) of the current game. It will take attributes such as
   text-file representing block orientation, power-up mappings, etc. Since each level will require a different scene
   and represent a different difficulty level, there will be varying components that need to be encapsulated in a level 
   class. It will require a method that returns a Scene with the required set up for this level, including things like 
   the specified ball for this level, the block mapping, etc. 

