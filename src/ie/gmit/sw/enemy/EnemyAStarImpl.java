package ie.gmit.sw.enemy;

import java.util.*;

import ie.gmit.sw.maze.Node;

public class EnemyAStarImpl extends AStarFinder 
{
	//Implementation of the parent AStarFinder class.
	
	private List<Node> finalList;
	private List<Node> newList = new ArrayList<Node>();
	private Node finalNode = new Node(0, 0);
	
	
	public EnemyAStarImpl(Node goal)
	{
		super(goal);
	}
	
	public void search(Node[][] maze, Node node)
	{
		super.search(maze, node);
		finalList = super.returnList();
		startSearching();
	}
	
	public void updateNode(Node goal)
	{
		super.updateNode(goal);
	}
	
	public void startSearching()
	{
		finalNode = null;
		Node checkedNode;
		Node currentNode = finalList.get(finalList.size()-1);
		
		while(currentNode != null)
		{
			newList.add(currentNode);
			currentNode = currentNode.getParent();
		}
		for(int i = newList.size() -1 ; i >= 1 ; i--)
		{
			currentNode = newList.get(i);
			if(currentNode != null)
			{
				if(currentNode.getNodeType() == 'E' || currentNode.getNodeType() == 'F')
				{
					if(currentNode.getNodeType() == 'E')
					{
						currentNode.setPlayerHere(true);
					}
					finalNode = currentNode;
					break;
				}
				currentNode.setNodeType('V');
			}

			try 
			{ 
				Thread.sleep(500);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			checkedNode = currentNode;
			if(currentNode.getNodeType() != 'X' && currentNode.getNodeType() != 'G' && currentNode.getNodeType() != 'C' && currentNode.getNodeType() != 'F')
			{
				currentNode.setNodeType(' ');
			}
		}
		super.clearAll();
		newList.clear();
	}
	
	public Node returnFinalNode()
	{
		return finalNode;
	}
}