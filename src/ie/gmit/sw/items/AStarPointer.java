package ie.gmit.sw.items;

import java.util.*;
import ie.gmit.sw.enemy.AStarFinder;
import ie.gmit.sw.maze.Node;

public class AStarPointer extends AStarFinder
{
	//AStarPointer is a class designed to navigate the player to the goal node using the A* algorithm
	
	private List<Node> finalList;
	private List<Node> newList = new ArrayList<Node>();
	
	public AStarPointer(Node goal)
	{
		super(goal);
	}
	
	public void search(Node[][] maze, Node node)
	{
		super.search(maze, node);
		finalList = super.returnList();
		showPath();
	}
	
	public void showPath()
	{
		finalList = super.returnList();

		Node currentNode = finalList.get(finalList.size()-1);
		while(currentNode != null)
		{
			newList.add(currentNode);
			currentNode = currentNode.getParent();
		}
		System.out.println(newList);
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			currentNode = newList.get(i);
			if(currentNode != null)
			{
				if(currentNode.getNodeType() == 'G')
				{
					break;
				}
				currentNode.setNodeType('C');
				
			}
		}
		try 
		{ 
			//Simulate processing each expanded node
			Thread.sleep(5000);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			currentNode = newList.get(i);
			if(currentNode != null)
			{
				if(currentNode.getNodeType() != 'G' && currentNode.getNodeType() != 'X' && currentNode.getNodeType() != 'E')
				{
					currentNode.setNodeType(' ');
				}
			}
		}
		super.clearAll();
		newList.clear();
	}
}
