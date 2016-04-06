package ie.gmit.sw.enemy;

import java.util.ArrayList;
import java.util.List;

import ie.gmit.sw.character.EnemyAI;
import ie.gmit.sw.maze.Node;

public class EnemyIterDFS implements EnemyAI{
	
	private Node[][] maze;
	private Node goal;
	private boolean keepRunning = true;
	private List<Node> finalList = new ArrayList<Node>();
	private List<Node> newList = new ArrayList<Node>();
	private Node finalNode = new Node(0, 0);

	public void search(Node[][] maze, Node start) 
	{
		this.maze = maze;
		int limit = 2;
		
		while(keepRunning)
		{
			dfs(start, 0, limit);
			
			if (keepRunning)
			{
	      		limit++;       		
	      		uncheck();			
			}
      	}
		
		startSearching();
	}

	private void dfs(Node node, int depth, int limit)
	{
		if (!keepRunning || depth > limit) 
		{
			return;		
		}
		node.setVisited(true);	
		
		if (node == goal)
		{
			System.out.println("FOUND GOAL");
			goal = node;
	        keepRunning = false;
			return;
		}
		
		ArrayList<Node> children = node.adjacentNodes(maze);
		
		for (Node child : children) 
		{
			//System.out.println(child.toString() + " :::: " + child.getNodeType());
			if(child.getNodeType() == ' '  ||  child.getNodeType() == 'G' || child.getNodeType() == 'V'  ||  child.getNodeType() == 'E')
			{
				if (child != null && !child.isVisited())
				{
					child.setParent(node);
					finalList.add(child);
					dfs(child, depth + 1, limit);
				}
			}
		}
	} 
	public void startSearching()
	{
		finalNode.setPlayerHere(false);
		Node checkedNode;
		Node currentNode = goal;
		while(currentNode != null)
		{
			newList.add(currentNode);
			currentNode = currentNode.getParent();
		}
		for(int i = newList.size() -1 ; i >= 0 ; i--)
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
				//Simulate processing each expanded node
				Thread.sleep(500);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			checkedNode = currentNode;
			if(checkedNode.getNodeType() != 'X' && checkedNode.getNodeType() != 'G' && checkedNode.getNodeType() != 'C' && checkedNode.getNodeType() != 'F')
			{
				checkedNode.setNodeType(' ');
			}
		}
	
		uncheck();
	}
		
	private void uncheck()
	{
		newList.clear();
		finalList.clear();
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}
	
	public void updateNode(Node goal)
	{
		this.goal = goal;
	}

	public Node getNode() {
		return null;
	}

}
