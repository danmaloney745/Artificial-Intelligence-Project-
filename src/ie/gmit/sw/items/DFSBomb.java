package ie.gmit.sw.items;

import java.util.*;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;

public class DFSBomb
{
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private List<Node> finalList = new ArrayList<Node>();
	
	public DFSBomb(int limit){
		this.limit = limit;
	}
	
	public void traverse(Node[][] maze, Node node) 
	{
		this.maze = maze;
		System.out.println("Search with limit " + limit);
		dfs(node, 1);
		unvisit();
	}
	
	private void dfs(Node node, int depth)
	{
		if (!keepRunning || depth > limit) 
		{
			return;
		}
		
		node.setVisited(true);	

		ArrayList<Node> children = node.adjacentNodes(maze);
		
		for (Node child : children) 
		{
			if(child.getNodeType() == Nodes.floor  ||  child.getNodeType() == Nodes.goal || child.getNodeType() == Nodes.enemy  ||  child.getNodeType() == Nodes.player)
			{
				//System.out.println("VALID");
				if (child != null && !child.isVisited())
				{	
					child.setParent(node);
					finalList.add(child);

					dfs(child, limit + 1);
					System.out.println("WORKING STUFF");
				}
			}
		}
	}
	private void unvisit()
	{
		
		for(Node n : finalList)
		{
			System.out.println(n.toString());
			n.setNodeType(Nodes.fire);
		}
		try 
		{ 
			//Simulate processing each expanded node
			Thread.sleep(250);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		for(Node n : finalList)
		{
			
			n.setNodeType(Nodes.floor);
		}
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
				//maze[i][j].setNodeType(' ');
			}
		}
		finalList.clear();
	}
}
