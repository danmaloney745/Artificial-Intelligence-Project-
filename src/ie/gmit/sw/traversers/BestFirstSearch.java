package ie.gmit.sw.traversers;

import java.util.LinkedList;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;

public class BestFirstSearch implements Traversator{

	private Node node;
	private Node finalNode = new Node(0, 0);

	public BestFirstSearch(Node node){
		this.node = node;
	}
	
	public void traverse(Node[][] maze, Node start) {
		
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		int vistied = 0;
		
		while(!queue.isEmpty()){
			//take from the top
			queue.poll();
			node.setVisited(true);
			
			vistied ++;
			
			if (node.getNodeType() == Nodes.floor  ||  node.getNodeType() == Nodes.goal || node.getNodeType() == Nodes.enemy  ||  node.getNodeType() == Nodes.player){
				break;
			}
				
			Node[] children = node.children(maze);								
				
			for (int i = 0; i < children.length; i++) {
				if (!children[i].isVisited()){
					queue.addFirst(children[i]);
					children[i].setParent(node);
				}
			}
				
			node = queue.getFirst();
			node.setVisited(true);
		}
	}
	
	
	public Node returnFinalNode()
	{
		return finalNode;	
	}

	public void updateGoalNode(Node node)
	{
		this.node = node;
		
	}
}