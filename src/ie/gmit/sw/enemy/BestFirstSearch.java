package ie.gmit.sw.enemy;

import java.util.LinkedList;

import ie.gmit.sw.maze.Node;

public class BestFirstSearch {
	
	private Node node;
	private LinkedList<Node> queue = new LinkedList<Node>();

	HeuristicComparator sorter = new HeuristicComparator();
	
	public void search(Node[][] maze, Node node){
		
		queue.addFirst(node);
		
		while(!queue.isEmpty()){
			queue.poll();
			node.setVisited(true);
			
			int vistied = 0;
			vistied ++;
			
			if (node.isGoalNode()){
				break;
			}else{
				Node[] children = node.children(maze);								
				
				for (int i = 0; i < children.length; i++) {
					if (!children[i].isVisited()){
						queue.addFirst(children[i]);
						children[i].setParent(node);
					}
				}
				
				//Collections.sort(queue, sorter);
				node = queue.getFirst();
				node.setVisited(true);
			}
		}
	}
}
