package ie.gmit.sw.characters;

import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;
import ie.gmit.sw.traversers.Traversator;
import ie.gmit.sw.traversers.AStarFinder;
import ie.gmit.sw.traversers.EnemyIterDFS;

public class Enemy 
{
	public enum SearchType {ENEMYASTAR, ENEMYITERDFS};
	private boolean isAlive = true;
	private Player player;
	private SearchType search;
	private Node currentPos;
	private Node[][] maze;
	private Traversator enemy = null;
	private boolean complete = false;
	private FuzzyFight ff;
	
	public Enemy(Player player, SearchType search, Node startNode, Node[][] maze)
	{
		setPlayer(player);
		setSearchType(search);
		currentPos = startNode;
		this.maze = maze.clone();
		ff = new FuzzyFight();
	}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public Node getCurrentPos() {
		return currentPos;
	}
	
	public void setCurrentPos(Node currentPos)
	{
		this.currentPos = currentPos;
		
		if(currentPos.isHasPlayer() && currentPos.getNodeType() == Nodes.player)
		{
			ff.fight(player, this);
			setComplete(true);
		}
		else if(currentPos.getNodeType() == Nodes.fire)
		{
			setComplete(true);
		}
		else
		{
			enemySearch();
		}
	}
	
	public boolean isAlive() 
	{
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) 
	{
		this.isAlive = isAlive;
	}
	
	public Player getPlayer() 
	{
		return player;
	}
	
	public void setPlayer(Player playerPos) 
	{
		this.player = playerPos;
	}
	
	public SearchType getSearchType() 
	{
		return search;
	}
	
	public void setSearchType(SearchType searchType) 
	{
		this.search = searchType;
	}
	
	public void initEnemyType()
	{
		if(search == SearchType.ENEMYASTAR)
		{
			enemy = new AStarFinder(player.getCurrentNode());
		}
		else if(search == SearchType.ENEMYITERDFS)
		{
			enemy = new EnemyIterDFS();
		}
		enemySearch();
	}
	
	
	public void enemySearch()
	{
		updatePlayerPos();
		enemy.traverse(maze, currentPos);
		setCurrentPos(enemy.returnFinalNode());
	}
	
	//Update the enemy with the position of the player
	public void updatePlayerPos()
	{
		enemy.updateGoalNode(player.getCurrentNode());
	}
}