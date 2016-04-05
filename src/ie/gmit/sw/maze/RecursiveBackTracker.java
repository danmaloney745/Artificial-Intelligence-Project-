package ie.gmit.sw.maze;

import java.util.*;

import ie.gmit.sw.maze.Node.Direction;

public class RecursiveBackTracker implements MazeGenerator {

	private Node[][] maze;
	private Random rand = new Random();
	private List<Node> startingCells = new ArrayList<Node>();
	private Node goalNode;
	
	public void setGoalNode() {
		
	}

	public Node getGoalNode() {
		return goalNode;
	}

	public Node[][] getMaze() {
		
		return this.maze;
	}

	public void generateMaze(int rows, int cols) {
		maze = new Node[rows][cols];
		init();
		int randNode = rand.nextInt(startingCells.size()-1);
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
				Node next = validNeighbours.get(rand.nextInt(validNeighbours.size()));
				
				Node wall = getWall(thisNode, maze[next.getRow()][next.getCol()]);
				wall.setNodeType(' ');
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
		
		features(rows, cols);
		addGoal();
	}
	
	private void addGoal() {
		boolean isValid = false;
		int row = 1;
		int col = 1;
		while(!isValid)
		{
			row = (int)(maze.length * Math.random());
			col = (int) (maze[0].length * Math.random());
			
			if(maze[row][col].getNodeType() != ' ')
			{
				isValid = true;
			}
		}
		maze[row][col].setGoalNode(true);
		maze[row][col].setNodeType('G');
		goalNode = maze[row][col];
		
	}
	
	private Node getWall(Node n1, Node n2)
	{
		Node wall;
		if (n1.getRow() == n2.getRow()) 
		{
			if (n1.getCol() < n2.getCol())
			{
				wall = maze[n2.getRow()][n2.getCol()-1];
				n1.addPath(Direction.West);
				n1.setPathCost(1);
			}
			else
			{
				wall = maze[n2.getRow()][n2.getCol()+1];
				n1.addPath(Direction.East);
				n1.setPathCost(1);
			}
			
		} 
		else 
		{
			if (n1.getRow() < n2.getRow())
			{
				wall = maze[n2.getRow()-1][n2.getCol()];
				n1.addPath(Direction.North);
				n1.setPathCost(1);

			}
			else
			{
				wall = maze[n2.getRow()+1][n2.getCol()];
				n1.addPath(Direction.South);
				n1.setPathCost(1);
			}
		}		
		return wall;
	}

	private void features(int rows, int cols)
	{
		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature('W', 'X', featureNumber);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
	}
	
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getNodeType() != ' '){
				maze[row][col].setNodeType(feature);
				counter++;
			}
		}
	}
	private void init() {
		for (int row = 0; row < maze.length; row ++)
		{
			for (int col = 0; col < maze[row].length; col++)
			{
				maze[row][col] = new Node(row, col);
				if(row % 2 == 0 || col % 2 == 0)
				{
					maze[row][col].setNodeType('X');
				}
				else
				{
					maze[row][col].setNodeType(' ');
					maze[row][col].setStart(true);
					startingCells.add(maze[row][col]);
				}
			}
		}	
	}

}
