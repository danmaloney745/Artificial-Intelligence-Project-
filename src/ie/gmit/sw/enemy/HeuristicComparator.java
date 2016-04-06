package ie.gmit.sw.enemy;

import ie.gmit.sw.maze.Node;

public class HeuristicComparator {
	
	public int compare(Node node1, Node node2) {
		if (node1.getScore() > node2.getScore()){
			return 1;
		}else if (node1.getScore() < node2.getScore()){
			return -1;
		}else{
			return 0;
		}
	}
}
