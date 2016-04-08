package ie.gmit.sw.traversers;

import java.util.*;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;

public class AStarFinder extends AStar 
{

	private List<Node> finalList;
	private List<Node> newList = new ArrayList<Node>();
	private Node finalNode = new Node(0, 0);
	private Node firstNode;
	
	public AStarFinder(Node goal)
	{
		super(goal);
	}
	public void updateGoalNode(Node goal)
	{
		super.updateGoalNode(goal);
	}
	public void traverse(Node[][] maze, Node node)
	{
		this.finalNode = node;
		super.traverse(maze, node);
		finalList = super.returnList();
		go();
	}
	public void go()
	{
		//resets to zero to avoid enemies attacking player when the player is not set
		finalNode.setHasPlayer(false);
		Node oldNode;
		Node curNode;
		
		if(finalList.size() > 0)
		{
			curNode = finalList.get(finalList.size()-1);
		}
		else
		{
			//if the list is empty start from the beginning node
			curNode = firstNode;
		}
		while(curNode != null)
		{
	
			newList.add(curNode);
			curNode = curNode.getParent();
		}

		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			curNode = newList.get(i);
			if(curNode != null)
			{
				if(curNode.getNodeType() == Nodes.player || curNode.getNodeType() == Nodes.fire)
				{
					if(curNode.getNodeType() == Nodes.player)
					{
						curNode.setHasPlayer(true);
					}
					finalNode = curNode;
					break;
				}
				curNode.setNodeType(Nodes.enemy);
			}

			try 
			{ 
				//Simulate processing each expanded node
				Thread.sleep(500);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			oldNode = curNode;
			if(oldNode.getNodeType() != Nodes.hedge && oldNode.getNodeType() != Nodes.goal && oldNode.getNodeType() != Nodes.path && oldNode.getNodeType() != Nodes.fire)
			{

				oldNode.setNodeType(Nodes.floor);
			}
		}
		//reset all nodes and lists
		super.clearAll();
		newList.clear();
	}
	public Node returnFinalNode()
	{
		return finalNode;
	}
}