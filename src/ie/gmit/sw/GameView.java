package ie.gmit.sw;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.characters.Player;
import ie.gmit.sw.maze.Node;
import ie.gmit.sw.maze.Nodes;

public class GameView extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private static final int IMAGE_COUNT = 12;
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Node maze[][];
	private BufferedImage[] images;
	private int enemy_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int size = DEFAULT_VIEW_SIZE/cellspan;
	private int imageIndex = -1;
	private Player player;
	private HUD hud = new HUD(DEFAULT_VIEW_SIZE);
	
	public GameView(Node[][] maze) throws Exception
	{
		//construct the game (maze and threads.
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	
	public void addPlayer(Player player)
	{
		//assigns the player instance from runner
		this.player = player;
	}
	
	public void setCurrentRow(int row) 
	{
		if (row < cellpadding)
		{
			currentRow = cellpadding;
		}
		else if (row > (maze.length - 1) - cellpadding)
		{
			currentRow = (maze.length - 1) - cellpadding;
		}
		else
		{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col)
	{
		if (col < cellpadding)
		{
			currentCol = cellpadding;
		}
		else if (col > (maze[currentRow].length - 1) - cellpadding)
		{
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}
		else
		{
			currentCol = col;
		}
	}
	
	public void defineMapColours(int row, int col, Graphics2D g2)
	{
		//defines the nodes colours for when the maze is zoomed out
		//different from the main view colours to emphasise paths
		int x1 = col * size;
		int y1 = row * size;
		Nodes ch = maze[row][col].getNodeType();
		
   		if (ch == Nodes.hedge)
		{        			
   			g2.setColor(Color.GREEN);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.floor)
		{
			g2.setColor(Color.BLACK);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.weapon)
		{
			g2.setColor(Color.BLUE);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.hint)
		{
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.bomb)
		{
			g2.setColor(Color.RED);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.hbomb)
		{
			g2.setColor(Color.WHITE);
			g2.fillRect(x1, y1, size, size);
		}
		else if (ch == Nodes.player)
		{
			g2.setColor(Color.ORANGE);
			g2.fillRect(x1, y1, size, size);  			
		}
		else if (ch == Nodes.goal)
		{
			g2.setColor(Color.YELLOW);
			g2.fillRect(x1, y1, size, size);  			
		}
		else if (ch == Nodes.enemy)
		{
			g2.setColor(Color.PINK);
			g2.fillRect(x1, y1, size, size);    			
		}
		else if (ch == Nodes.path)
		{
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(x1, y1, size, size);   			
		}
		else
		{
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(x1, y1, size, size);
		}
	}
	
	public void paintComponent(Graphics g)
	{	
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        cellspan = zoomOut ? maze.length : 5;         
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) 
        {
        	for (int col = 0; col < cellspan; col++)
        	{  
        		size = DEFAULT_VIEW_SIZE/cellspan;
        		int x1 = col * size;
        		int y1 = row * size;
        		//assigns the default character to wall (which is the hedge node)
        		Nodes ch = Nodes.hedge;
       		
        		if (zoomOut)
        		{
        			ch = maze[row][col].getNodeType();
        			size = DEFAULT_VIEW_SIZE/cellspan;
        			defineMapColours(row, col, g2);
        		}
        		else
        		{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
               		if (ch == Nodes.hedge)
            		{        			
            			imageIndex = 0;;
            		}
            		else if (ch == Nodes.floor)
            		{
            			imageIndex = 7;;
            		}
            		else if (ch == Nodes.weapon)
            		{
            			imageIndex = 1;;
            		}
            		else if (ch == Nodes.hint)
            		{
            			imageIndex = 2;;
            		}
            		else if (ch == Nodes.bomb)
            		{
            			imageIndex = 3;;
            		}
            		else if (ch == Nodes.hbomb)
            		{
            			imageIndex = 4;;
            		}
            		else if (ch == Nodes.player)
            		{
            			imageIndex = enemy_state;;       			
            		}
            		else if (ch == Nodes.goal)
            		{
            			imageIndex = 8;;     			
            		}
            		else if (ch == Nodes.enemy)
            		{
            			imageIndex = 9;;     			
            		}
            		else if (ch == Nodes.path)
            		{
            			imageIndex = 10;;     			
            		}
            		else if (ch == Nodes.fire)
            		{
            			imageIndex = 11;;     			
            		}
            		else
            		{
            			imageIndex = -1;
            		}
            		
            		if (imageIndex >= 0)
            		{
            			g2.drawImage(images[imageIndex], x1, y1, null);
            		}
            		else
            		{
            			g2.setColor(Color.LIGHT_GRAY);
            			g2.fillRect(x1, y1, size, size);
            		} 
        		}
        		if(!(zoomOut))
        		{
        			//will show the HUD's health bar when not zoomed in
        			hud.showHealth(player, g2); 
        		}
        		
      		
        	}
        }
	}
	public void triggerEndScreen()
	{
		//triggers the end screen when the goal node is reached
		hud.setGameOver(true);
	}
	
	public void toggleZoom()
	{
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) 
	{	
		if (enemy_state < 0 || enemy_state == 5)
		{
			enemy_state = 6;
		}
		else
		{
			enemy_state = 5;
		}
		
		this.repaint();
	}
	
	//Initialise the resources
	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/hedge.png"));
		images[1] = ImageIO.read(new java.io.File("resources/sword.png"));		
		images[2] = ImageIO.read(new java.io.File("resources/help.png"));
		images[3] = ImageIO.read(new java.io.File("resources/bomb.png"));
		images[4] = ImageIO.read(new java.io.File("resources/h_bomb.png"));
		images[5] = ImageIO.read(new java.io.File("resources/spider_down.png"));
		images[6] = ImageIO.read(new java.io.File("resources/spider_up.png"));
		images[7] = ImageIO.read(new java.io.File("resources/walls.png"));
		images[8] = ImageIO.read(new java.io.File("resources/exit.png"));
		images[9] = ImageIO.read(new java.io.File("resources/enemy.png"));
		images[10] = ImageIO.read(new java.io.File("resources/clue.png"));
		images[11] = ImageIO.read(new java.io.File("resources/fire.png"));
	}
}