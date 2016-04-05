package ie.gmit.sw.enemy;

import ie.gmit.sw.maze.Node;

public interface EnemyAI {
	
	public void search(Node[] maze, Node startNode);
	
}
