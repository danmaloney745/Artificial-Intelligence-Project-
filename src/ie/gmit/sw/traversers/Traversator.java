package ie.gmit.sw.traversers;

import ie.gmit.sw.maze.Node;

public interface Traversator
{
	public void traverse(Node[][] maze, Node start);
	public void updateGoalNode(Node goal);
	public Node returnFinalNode();
}
