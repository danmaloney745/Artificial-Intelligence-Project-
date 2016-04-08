package ie.gmit.sw.traversers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;

public class AStar implements Traversator
{
	
	private Node goal;
	private List<Node> closed = new ArrayList<Node>();
	private List<Node> finalList = new ArrayList<Node>();
	private List<Node> allNodes = new ArrayList<Node>();
	private PriorityQueue<Node> open ;
	
	public AStar(Node goal)
	{
		//assigns/updates goal node allowing for multiple searches if player is not found
		updateGoalNode(goal);
	}
	public void traverse(Node[][] maze, Node node)
	{	
		//assigns new open queue in order to avoid StackOverflow exceptions
		open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
        //clones instance of maze so as to avoid the editing of nodes used by other searches
		//not entirely efficient
		maze = maze.clone();
		open.offer(node);
		node.setPathCost(0);
		
		while(!open.isEmpty())
		{
			//System.out.println("LOOKING");
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	
			//System.out.println(node.toString());
			
			if (node.equals(goal))
			{
				//System.out.println(goal);
				//System.out.println("FOUND");
				break;
			}
			//Process adjacent nodes
			ArrayList<Node> children = node.adjacentNodes(maze);
			for (Node child : children) 
			{
				allNodes.add(child);
				//System.out.println(child.toString() + " :::: " + child.getNodeType());
				if(child.getNodeType() != Nodes.hedge && child.getNodeType() != Nodes.weapon && child.getNodeType() != Nodes.bomb && child.getNodeType() != Nodes.hbomb && child.getNodeType() != Nodes.hint)//(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' ||  child.getNodeType() == 'V' ||  child.getNodeType() == 'E')
				{
					int score = node.getPathCost() + 1 + child.getHeuristic(goal);
					int existing = child.getPathCost() + child.getHeuristic(goal);
					
					if ((open.contains(child) || closed.contains(child)) && existing < score)
					{
						continue;
					}
					else
					{
						open.remove(child);
						closed.remove(child);
						child.setParent(node);
						finalList.add(child);
						child.setPathCost(node.getPathCost() + 1);
						open.add(child);
					}
				}		
			}
		}
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
		//returns list of nodes visited
		return finalList;
	}
	@Override
	public void updateGoalNode(Node goal) 
	{
		this.goal = goal;
		
	}
	@Override
	public Node returnFinalNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
