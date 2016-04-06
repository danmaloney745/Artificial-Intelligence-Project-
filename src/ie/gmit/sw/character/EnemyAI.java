package ie.gmit.sw.character;

import ie.gmit.sw.maze.Node;

public interface EnemyAI
{
	public void search(Node[][] maze, Node start);
	
	public void updateNode(Node goal);
	
	public Node getNode();
}
