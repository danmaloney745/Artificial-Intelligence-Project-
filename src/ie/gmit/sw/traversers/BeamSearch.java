package ie.gmit.sw.traversers;

import java.util.LinkedList;

import ie.gmit.sw.maze.Node;

public class BeamSearch implements Traversator {
	private Node goal;
	private int beamWidth = 1; 
	private boolean keepRunning = false;
	
	public BeamSearch(Node goal, int beamWidth){
		this.goal = goal;
		this.beamWidth = beamWidth;
	}
	
	public void search(Node[][] maze, Node node){
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
    	int visitCount = 0;
    	
		while(!queue.isEmpty()){
			node = queue.poll();
			node.setVisited(true);	
			visitCount++;
			
			Node[] children = node.children(maze);
			
			if (node == goal)
			{
				System.out.println("FOUND GOAL");
				goal = node;
		        keepRunning = false;
				return;
			}
			
			
			int bound = 0;
			if (children.length < beamWidth){
				bound = children.length;
			}else{
				bound = beamWidth;
			}
			
			for (int i = 0; i < bound; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
		}
	}

	public void updateNode(Node goal) {
		// TODO Auto-generated method stub
		
	}

	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void traverse(Node[][] maze, Node start) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGoalNode(Node goal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node returnFinalNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
