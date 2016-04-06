package ie.gmit.sw.character;

import ie.gmit.sw.enemy.*;

import ie.gmit.sw.maze.Node;

public class EnemyImpl 
{
	public enum SearchType {ENEMYASTAR, ENEMYITERDFS, ENEMYBEAM};
	private boolean isAlive = true;
	private Player player;
	private SearchType search;
	private Node currentPos;
	private Node[][] maze;
	private EnemyAI enemy = null;
	private boolean complete = false;
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public EnemyImpl(Player player, SearchType search, Node startNode, Node[][] maze)
	{
		setPlayer(player);
		setSearchType(search);
		currentPos = startNode;
		this.maze = maze.clone();
	}
	
	public Node getCurrentPos() {
		return currentPos;
	}
	public void setCurrentPos(Node currentPos)
	{
		this.currentPos = currentPos;
		
		if(currentPos.isPlayerHere() && currentPos.getNodeType() == 'E')
		{
			player.attack();
			setComplete(true);
		}
		else if(currentPos.getNodeType() == 'F')
		{
			setComplete(true);
		}
		else
		{
			searchForPlayer();
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
	public void initEnemy()
	{
		if(search == SearchType.ENEMYASTAR)
		{
			enemy = new EnemyAStarImpl(player.getCurrentNode());
		}
		else if(search == SearchType.ENEMYITERDFS)
		{
			enemy = new EnemyIterDFS();
		}
		
		else if(search == SearchType.ENEMYBEAM)
		{
			enemy = new BeamSearch(player.getCurrentNode(), 1);
		}
		
		searchForPlayer();
	}
	public void searchForPlayer()
	{
		updatePlayerPos();
		enemy.search(maze, currentPos);
		setCurrentPos(enemy.getNode());
	}
	public void updatePlayerPos()
	{
		enemy.updateNode(player.getCurrentNode());
	}
}