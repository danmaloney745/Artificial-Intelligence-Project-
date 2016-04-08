package ie.gmit.sw.items;

import java.util.*;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;
import ie.gmit.sw.traversers.AStar;

public class AStarGoal extends AStar
{
	private List<Node> finalList;
	private List<Node> newList = new ArrayList<Node>();
	public AStarGoal(Node goal)
	{
		super(goal);
	}
	public void traverse(Node[][] maze, Node node)
	{
		super.traverse(maze, node);
		finalList = super.returnList();
		revealPath();
	}
	public void revealPath()
	{
		finalList = super.returnList();

		Node curNode = finalList.get(finalList.size()-1);
		while(curNode != null)
		{
			newList.add(curNode);
			curNode = curNode.getParent();
		}
		System.out.println(newList);
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			curNode = newList.get(i);
			if(curNode != null)
			{
				if(curNode.getNodeType() == Nodes.goal)
				{
					break;
				}
				curNode.setNodeType(Nodes.path);
				//System.out.println(curNode.toString() + " " + curNode.getNodeType());
			}
		}
		try 
		{ 
			//Simulate processing each expanded node
			Thread.sleep(5000);
			//System.out.println("SHTUFF");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			curNode = newList.get(i);
			if(curNode != null)
			{
				if(curNode.getNodeType() != Nodes.goal && curNode.getNodeType() != Nodes.hedge && curNode.getNodeType() != Nodes.player)
				{
					curNode.setNodeType(Nodes.floor);
				}
			}
		}
		super.clearAll();
		newList.clear();
		//Thread.currentThread().interrupt();
	}
}
