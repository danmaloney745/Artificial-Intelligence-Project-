package ie.gmit.sw;

import java.awt.*;

import ie.gmit.sw.character.Player;

public class HUD
{
	/* HUD Created by Diarmuid Byrne */
	
	private int DEFAULT_VIEW_SIZE;
	public HUD(int DEFAULT_VIEW_SIZE)
	{
		this.DEFAULT_VIEW_SIZE = DEFAULT_VIEW_SIZE;
	}
	public void showHealth(Player p, Graphics2D g2)
	{
		// Implement a health bar based on player's current health from 0-100
		Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
		g2.setFont(font);
		FontMetrics f = g2.getFontMetrics(font);
        g2.setColor(Color.black);
        g2.fillRect(DEFAULT_VIEW_SIZE/2-100, 39, 101*2,12);
        String str = "Health";
        g2.drawString(str, DEFAULT_VIEW_SIZE/2-f.stringWidth(str)/2, 30);
        
        if(p.getHealth() > 30)
        {
        	g2.setColor(Color.green);
        }
        
        else 
    	{
        	g2.setColor(Color.red);
    	}
        
        g2.fillRect(DEFAULT_VIEW_SIZE/2-100, 40, p.getHealth()*2,10);
        
        showNumberOfWeapons(p, g2);
	}
	
	public void showNumberOfWeapons(Player p, Graphics2D g2)
	{
		if (p.getNumberOfWeapons() > 0) 
		{		
			Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
			g2.setFont(font);
			FontMetrics f = g2.getFontMetrics(font);
	        g2.setColor(Color.black);
	        
	        g2.fillRect(DEFAULT_VIEW_SIZE/4-125, 39, 50*2,12);
	        
	        String str = "Weapons Collected";
	        
	        g2.drawString(str, DEFAULT_VIEW_SIZE/4-f.stringWidth(str), 30);
	        
	        if(p.getNumberOfWeapons() > 3) 
        	{
	        	g2.setColor(Color.blue);	
        	}
	        else g2.setColor(Color.red);
	        {
	        	g2.fillRect(DEFAULT_VIEW_SIZE/4-125, 40, p.getNumberOfWeapons()*10,10);
	        }
		}
	}
	public void showEndMenu(Player p , Graphics2D g2)
	{
		//System.out.println("SHOWING STUFF");
		Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
		g2.setFont(font);
		FontMetrics f = g2.getFontMetrics(font);
        g2.setColor(Color.black);
               
        String str = " tetetetet"; //("GAME OVER : \n YOU WALKED\n " + p.getSteps() + " WITH " + p.getHealth() + " HEALTH");
        g2.drawString(str, DEFAULT_VIEW_SIZE/2-f.stringWidth(str), 30);
	}
}
