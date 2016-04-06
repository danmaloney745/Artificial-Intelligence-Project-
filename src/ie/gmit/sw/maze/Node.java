package ie.gmit.sw.maze;

import java.awt.Color;
import java.util.*;

import ie.gmit.sw.enemy.HeuristicCalculator;
public class Node 
{
	public enum Direction {North, South, East, West};
	private Node parent;
	private Color color = Color.BLACK;
	private Direction[] paths = null;
	private Set<Node> nodeSet;
	public boolean visited =  false;
	private boolean isStart = false;
	public boolean goal = false;
	private boolean isEndNode = false;
	private boolean playerHere = false;
	private int row = -1;
	private int col = -1;
	private int distance;
	private int approxDistance;
	private char nodeType;
	

	public void setEndNode(boolean isEndNode) {
		this.isEndNode = isEndNode;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isStartNode() {
		return isStart;
	}
	
	public boolean isEndNode() {
		return isEndNode;
	}
	
	public Node(int row, int col) 
	{
		this.row = row;
		this.col = col;
	}

	public Set<Node> getNodeSet() 
	{
		return nodeSet;
	}
	public void NewSet()
	{
		nodeSet = new HashSet<Node>();
	}
	public void setNodeSet(Set<Node> nodeSet) 
	{
		this.nodeSet = nodeSet;
	}
	public void addNodeToSet(Node n)
	{
		this.nodeSet.add(n);
	}
	public void mergeSets(Set<Node> mSet)
	{
		this.nodeSet.addAll(mSet);
	}
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public char getNodeType() {
		return nodeType;
	}

	public void setNodeType(char nodeType) {
		this.nodeType = nodeType;
	}

	public boolean hasDirection(Direction direction)
	{		
		for (int i = 0; i < paths.length; i++)
		{
			if (paths[i] == direction)
			{
				return true;	
			}
		}
		return false;
	}
	
	public Node[] children(Node[][] maze)
	{		
		java.util.List<Node> children = new java.util.ArrayList<Node>();
				
		if (row > 0 && maze[row - 1][col].hasDirection(Direction.South)) children.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1 && maze[row + 1][col].hasDirection(Direction.North)) children.add(maze[row + 1][col]); //Add South
		if (col > 0 && maze[row][col - 1].hasDirection(Direction.East)) children.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(Direction.West)) children.add(maze[row][col + 1]); //Add East
		
		return (Node[]) children.toArray(new Node[children.size()]);
	}

	public ArrayList<Node> adjacentNodes(Node[][] maze)
	{
		ArrayList<Node> adjacents = new ArrayList<Node>();
		if (row-1 > 0) adjacents.add(maze[row - 1][col]); //Add North
		if (row+1 < maze.length) adjacents.add(maze[row + 1][col]); //Add South
		if (col-1 > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col+1 < maze[row].length) adjacents.add(maze[row][col + 1]); //Add East
		
		return adjacents;
	}
	
	public ArrayList<Node> adjacentNodesFirst(Node[][] maze)
	{
		ArrayList<Node> adjacents = new ArrayList<Node>();
		
		if (row-1 > 0) adjacents.add(maze[row-2][col]); // Add North
		if (row+1 < maze.length - 1) adjacents.add(maze[row + 2][col]); //Add South
		if (col-1> 0) adjacents.add(maze[row][col-2]); // Add West
		if (col+1 < maze[row].length - 1) adjacents.add(maze[row][col + 2]); //Add East

		return adjacents;
	}
	
	public Direction[] getPaths() {
		return paths;
	}

	public void addPath(Direction direction) {
		int index = 0;
		
		if (paths == null)
		{
			paths = new Direction[index + 1];		
		}
		else
		{
			index = paths.length;
			Direction[] temp = new Direction[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
		}
		
		paths[index] = direction;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
	
	// Retrieve player location and set it
	public boolean isPlayerHere() {
		return playerHere;
	}
	public void setPlayerHere(boolean playerIsHere) {
		this.playerHere = playerIsHere;
	}

	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		double d = 1;
		return (int)(d * Math.abs(x1 - x2) - Math.abs(y1 - y2));
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public String toString() {
		return "[" + row + "/" + col + "]";
	}

	public float getScore() {
		// TODO Auto-generated method stub
		return HeuristicCalculator.getHeuristicValue(distance, approxDistance);
	}
	public void addFirst(Node node) {
		// TODO Auto-generated method stub
		
	}

}