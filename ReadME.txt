https://github.com/danmaloney745/Artificial-Intelligence-Project-.git

To Run the file navigate to the JAR folder.
Then run the following command: java -cp AIFuzzyMaze.jar ie.gmit.sw.GameRunner

Features:
The game starts by placing a player in a recursive back tracker inspired maze.
A goal node and enemies are then implemented on separate threads to the player.
Iterative Depth First Search:

Recursive BackTracker:
The recursive back tracker algorithm is used to generate the maze, had tried for too long to use ther methods and was advised
to use this algorithm.

AStar Enemy and Path Finding:
Using the AStar algorithm enemies will hunt for the player continuously.
If the player picks up a "hint" pickup the AStar algorithm will be targeted towards
finding the Goal node.

Depth First Search:
If the player picks up a "bomb" the Depth First Algorithm will target close by nodes and eliminate enemies for the player.

Fuzzy Logic:
Fuzzy logic is implemented when an enemy meets the player. The fuzzyfication is based on how many weapons the player has
accumulated.

Known Issues:
Sprites can sometimes not generate when items are picked up.
 
Notes:
Tried to implement additional searches (Beam and Best), I could get them to print to the screen but painting them using the sprites was where I fell short.

References:
Recursive Maze Generator:
http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
