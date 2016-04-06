package ie.gmit.sw.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import ie.gmit.sw.character.EnemyAI;
import ie.gmit.sw.maze.Node;

public class AStarFinder implements EnemyAI
{
	//Class that is used for all Astar associated classes, all will extend and implement the methods located here.
	private Node goal;
	private List<Node> closed = new ArrayList<Node>();
	private List<Node> finalList = new ArrayList<Node>();
	private List<Node> allNodes = new ArrayList<Node>();
	private PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
	
	public AStarFinder(Node goal)
	{
		updateNode(goal);
	}
		
	public void clearAll()
	{
		for(Node n : allNodes)
		{
			n.setParent(null);
			n.setVisited(false);
		}
		closed.clear();
		finalList.clear();
		open.clear();
	}
	
	public List<Node> returnList()
	{
		return finalList;
	}

	@Override
	public void search(Node[][] maze, Node start) {
		maze = maze.clone();
		open.offer(start);
		start.setPathCost(0);			
		while(!open.isEmpty())
		{
			start = open.poll();		
			closed.add(start);
			start.setVisited(true);	
			
			if (start.equals(goal))
			{
				System.out.println("GOAL NODE FOUND");
				break;
			}
			
			//Process next nodes
			ArrayList<Node> children = start.adjacentNodes(maze);
			for (Node child : children) 
			{
				allNodes.add(child);
				if(child.getNodeType() != 'X' && child.getNodeType() != 'W' && child.getNodeType() != 'B' && child.getNodeType() != 'H'&& child.getNodeType() != '?')//(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' ||  child.getNodeType() == 'V' ||  child.getNodeType() == 'E')
				{
					int score = start.getPathCost() + 1 + child.getHeuristic(goal);
					int existing = child.getPathCost() + child.getHeuristic(goal);
					if ((open.contains(child) || closed.contains(child)) && existing < score)
					{
						continue;
					}
					else
					{
						open.remove(child);
						closed.remove(child);
						child.setParent(start);
						finalList.add(child);
						child.setPathCost(start.getPathCost() + 1);
						open.add(child);
					}
				}		
			}
		}
		
	}
	
	@Override
	public void updateNode(Node goal) {
		// TODO Auto-generated method stub
		this.goal = goal;
	}
	
	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
