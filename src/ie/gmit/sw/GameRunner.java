package ie.gmit.sw;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ie.gmit.sw.characters.Enemy;
import ie.gmit.sw.characters.Player;
import ie.gmit.sw.characters.Enemy.SearchType;
import ie.gmit.sw.items.AStarGoal;
import ie.gmit.sw.items.DFSBomb;
import ie.gmit.sw.maze.*;
import ie.gmit.sw.traversers.*;


public class GameRunner implements KeyListener
{
	private static final int MAZE_DIMENSION = 60;
	private Node[][] model;
	private int currentRow;
	private int currentCol;
	private GameView view;
	private Node endGoal;
	private Player player;
	
	//Initialise game objects
	public GameRunner() throws Exception
	{	
		player = new Player();
		RecursiveBackTracker m = new RecursiveBackTracker();
		m.createMaze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
		endGoal = m.getGoalNode();
    	view = new GameView(model);
    	view.addPlayer(player);
    	placePlayer();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        placeEnemies();
	}
	
	private void placePlayer()
	{   	
		//places the player only in a section of the maze with the floor type
		//this is to avoid the deletion of the default node "floor"
		boolean isValid = false;
		while(!isValid)
		{
			currentRow = (int) (MAZE_DIMENSION * Math.random());
			currentCol = (int) (MAZE_DIMENSION * Math.random());
			if(model[currentRow][currentCol].getNodeType() == Nodes.floor)
			{
				isValid = true;
			}
		}
    	model[currentRow][currentCol].setNodeType(Nodes.player);
    	player.setCurrentNode(model[currentRow][currentCol]);
    	updateView(); 		
	}
	
	private void updateView()
	{
		if(player.getHealth() <= 0)
		{
			end();
		}
		else
		{
			view.setCurrentRow(currentRow);
			view.setCurrentCol(currentCol);
			player.setCurrentNode(model[currentRow][currentCol]);
			//System.out.println(player.getCurrentNode());
		}
	}

	public void placeEnemies()
	{
		//Creates 7 different threads each with a new enemy
		//as with player will only spawn enemy on node with floor type
		//relatively evenly assigns iterative deepening and A* algorithms to enemies
		for(int i = 1 ; i <= 7 ; i++)
		{
			Enemy.SearchType search;
			if(i % 2 == 0)
			{
				search = SearchType.ENEMYITERDFS;
			}
			else
			{
				search = SearchType.ENEMYASTAR;
			}
			int tempRow = 2;
			int tempCol = 2;
			boolean isValid = false;
			while(!isValid)
			{
				tempRow = (int) (MAZE_DIMENSION * Math.random());
				tempCol = (int) (MAZE_DIMENSION * Math.random());
				
				if(model[tempRow][tempCol].getNodeType() == Nodes.floor && model[tempRow][tempCol].getNodeType() != Nodes.player )
				{
					isValid = true;
				}
			}
			int finalRow = tempRow;
			int finalCol = tempCol;
			Thread enemy = new Thread() 
			{
			    public void run() 
			    {
			    	Enemy enemy = new Enemy(player, search, model[finalRow][finalCol], model);
			    	while(!enemy.isComplete())
			    	{
				        try 
				        { 
				        	System.out.println("NEW ENEMY : " + search + " TYPE");        	
				        	enemy.initEnemyType();
				        } 
				        catch(Exception e) 
				        {
				            System.out.println(e);
				        }
			    	}
			    	return;
			    }  
			    
			};
			enemy.start();
		}
	}
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow, currentCol + 1)) 
        	{
        		currentCol++;   		
        	}
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) 
        {
        	if (isValidMove(currentRow, currentCol - 1)) 
        	{
        		currentCol--;	
        	}
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) 
        {
        	if (isValidMove(currentRow - 1, currentCol))
    		{
    			currentRow--;
    		}
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) 
        {
        	if (isValidMove(currentRow + 1, currentCol)) 
    		{
    			currentRow++;        	  		
    		}
        }
        else if (e.getKeyCode() == KeyEvent.VK_Z)
        {
        	view.toggleZoom();
        }
        else
        {
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int r, int c)
	{
		//calculates if next node has a floor type, if the node is not of type floor implement a traverser
		if (r <= model.length - 1 && c <= model[r].length - 1 && (model[r][c].getNodeType() == Nodes.floor ||model[r][c].getNodeType() == Nodes.path))
		{
			model[currentRow][currentCol].setNodeType(Nodes.floor);
			model[r][c].setNodeType(Nodes.player);
			return true;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == Nodes.goal)
		{
			end();
			return false;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == Nodes.hint)
		{
			model[r][c].setNodeType(Nodes.hedge);
			Thread help = new Thread() 
			{
			    public void run() 
			    {
			        try 
			        { 
						Traversator helper = new AStarGoal(endGoal);
						helper.traverse(model,model[currentRow][currentCol]);
			        } 
			        catch(Exception e) 
			        {
			            System.out.println(e);
			        }
			        return;
			    } 
			    
			};
			help.start();
			return false;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == Nodes.bomb)
		{
			model[r][c].setNodeType(Nodes.hedge);
			Thread bomb = new Thread() 
			{
			    public void run() 
			    {
			        try 
			        { 
						
						DFSBomb dfsbomb = new DFSBomb(6);
						dfsbomb.traverse(model, model[currentRow][currentCol]);
			        } 
			        catch(Exception e) 
			        {
			            System.out.println(e);
			        }
			        return;
			    } 
			    
			};
			bomb.start();
			return false;
		}
		else if(r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getNodeType() == Nodes.weapon)
		{
			model[r][c].setNodeType(Nodes.hedge);
			player.setArmed(true);
			player.setWeaponInc((player.getWeaponInc() + 1));
			return false;
		}
		else
		{
			return false;
		}
	}
	public void end()
	{
		//ends the game by triggering the end screen and closing the window
		view.triggerEndScreen();
		try 
		{ 
			//Simulate processing each expanded node
			Thread.sleep(5000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		System.exit(0);
		
	}
	public static void main(String[] args) throws Exception 
	{
		new GameRunner();
	}
	
}
