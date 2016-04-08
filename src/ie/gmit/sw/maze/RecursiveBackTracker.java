package ie.gmit.sw.maze;

import java.util.*;

import ie.gmit.sw.maze.Node.Direction;

public class RecursiveBackTracker
{

	//Found that a recursive backtracker was the best method for generating mazes
			// as it generated long winding corridors

			private Node[][] maze;
			private Random randNum = new Random();
			private List<Node> startingCells = new ArrayList<Node>();
			private Node goalNode;
			
			public Node[][] getMaze() 
			{
				return this.maze;
			}
			public void createMaze(int rows, int cols) 
			{
				maze = new Node[rows][cols];
				init();
				int randNode = randNum.nextInt(startingCells.size() -1 );
				Node startNode = startingCells.get(randNode);
				Node thisNode = startNode;
				Stack<Node> nodes = new Stack<Node>();
				boolean firstNode = true;
				ArrayList<Node> adjNodes = new ArrayList<Node>();
				ArrayList<Node> validNeighbours = new ArrayList<Node>();
				nodes.push(thisNode);
				
				do
				{
					if(thisNode.isStart() == true)
					{
						adjNodes = thisNode.adjacentNodesFirst(maze);
						firstNode = false;
					}
					else
					{
						adjNodes = thisNode.adjacentNodes(maze);
					}			
					for(Node n : adjNodes)
					{
						if(!(n.isVisited()))
						{
							validNeighbours.add(n);
						}
					}
					if(validNeighbours.size() > 0)
					{			
						Node next = validNeighbours.get(randNum.nextInt(validNeighbours.size()));
						
						Node wall = getWall(thisNode, maze[next.getRow()][next.getCol()]);
						wall.setNodeType(Nodes.floor);
						nodes.push(thisNode);
						thisNode.setVisited(true);
						
						thisNode = next;	
						thisNode.setVisited(true);

						validNeighbours.clear();
					}
					else if (!nodes.isEmpty())
					{
						Node newOne = nodes.pop();
						thisNode = newOne;
					}
				} while(thisNode != startNode && !nodes.isEmpty());
				
				doFetures(rows, cols);
				addEndGoal();
			}
			
			private void doFetures(int rows, int cols)
			{	
				addFeature(Nodes.enemy, Nodes.floor, 80);
				addFeature(Nodes.weapon, Nodes.hedge, 70);
				addFeature(Nodes.hint, Nodes.hedge, 50);
				addFeature(Nodes.bomb, Nodes.hedge, 60); //15
				addFeature(Nodes.hbomb, Nodes.hedge, 40); //15
				//addFeature(Nodes.goal, Nodes.hedge, 1);
			}
			
			private void addFeature(Nodes feature, Nodes replace, int number)
			{
				int counter = 0;
				while (counter < number){
					int row = (int) (maze.length * Math.random());
					int col = (int) (maze[0].length * Math.random());
					
					// Ensures that the node with an item isn't surrounded by wall nodes
					int surrounded = 0;
					List<Node> adjacentNodes = maze[row][col].getAdjacentNodes(maze);
					for (Node neighbour : adjacentNodes) {
						if(neighbour.getNodeType() == Nodes.hedge) surrounded++;
					}
					
					if (maze[row][col].getNodeType() == replace && checkBorders(row, col)
							&& surrounded < 4){
						maze[row][col].setNodeType(feature);
						counter++;
					}
				}
			}
			
			private boolean checkBorders(int row, int col) {
				return row > 0 && col > 0 
						&& row < maze.length - 1
						&& col < maze[row].length - 1;
			}
			
			private void addEndGoal()
			{
				boolean isValid = false;
				int row = 1;
				int col = 1;
				while(!isValid)
				{
					row = (int)(maze.length * Math.random());
					col = (int) (maze.length * Math.random());
					
					if(maze[row][col].getNodeType() != Nodes.floor)
					{
						isValid = true;
					}
				}
				maze[row][col].setEndNode(true);
				maze[row][col].setNodeType(Nodes.goal);
				goalNode = maze[row][col];
			}
			
			private Node getWall(Node n1, Node n2) {
				Node wall;
				
				if (n1.getRow() == n2.getRow()) {
					if (n1.getCol() < n2.getCol())
						wall = maze[n2.getRow()][n2.getCol()-1];
					else 
						wall = maze[n2.getRow()][n2.getCol()+1];
					
				} else {
					if (n1.getRow() < n2.getRow()) 
						wall = maze[n2.getRow()-1][n2.getCol()];
					else
						wall = maze[n2.getRow()+1][n2.getCol()];
				}
				return wall;
			}
			
			private void init()
			{
				for (int row = 0; row < maze.length; row ++)
				{
					for (int col = 0; col < maze[row].length; col++)
					{
						maze[row][col] = new Node(row, col);
						if(row % 2 == 0 || col % 2 == 0)
						{
							maze[row][col].setNodeType(Nodes.hedge);
						}
						else
						{
							maze[row][col].setNodeType(Nodes.floor);
							maze[row][col].setStart(true);
							startingCells.add(maze[row][col]);
						}
					}
				}
				
			}

			public Node getGoalNode()
			{
				// TODO Auto-generated method stub
				return goalNode;
			}
}