https://github.com/danmaloney745/Artificial-Intelligence-Project-.git

To Run the file navigate to the JAR folder.
Then run the folloing command: java -cp AIFuzzyMaze.jar ie.gmit.sw.GameRunner

Features:
Iterative Depth First Search:

Recursive BackTracker:
The recrusive back tracker algorithm is used to generate the maze, had tried for too long to use ther methods and was advised
to use this algorithm.

AStar Enemy and Path Finding:
Using the AStar algortihm eneimes will hunt for the player continuously.
If the player picks up a "hint" pickup the AStar algorithm will be targeted towards
finding the Goal node.

Depth First Search:
If the player picks up a "bomb" the Depth First Algorithm will target close by nodes and eliminate enemies for the player.

Fuzzy Logic:
Fuzzy logic is implmemented when an enemy meets the player. The fuzzyfication is based on how many weapons the player has
accumilated.

Known Issues:
Sprites can sometimes not generate when items are picked up.
 