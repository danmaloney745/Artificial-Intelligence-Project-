package ie.gmit.sw.characters;


import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyFight {
	//Class that initiates fight between the player and enemy nodes
	
		public void fight(Player player, Enemy enemy)
		{
			String fileName = "FCL/Fight.fcl";
	        FIS fis = FIS.load(fileName,true);

	        //if something wrong with file
	        if( fis == null )
	        { 
	            System.err.println("Can't load file: '" + fileName + "'");
	            return;
	        }
	        
	        FunctionBlock functionBlock = fis.getFunctionBlock("fight");

	        // Set inputs
	        fis.setVariable("health", player.getHealth());
	        fis.setVariable("weaponStrength", player.getWeaponInc());

	        // Evaluate the outcome, hopefully things go well
	        fis.evaluate();
	        
	        // Retrieve the damage output
	        Variable damage = functionBlock.getVariable("damage");
	        
	        
	        player.setHealth((int)(damage.getValue()));
	        //this.health -= damage.getValue();
	        //If the health reaches 0, you have died
	        if(player.getHealth() <= 0)
	        {
	        	System.out.println("YOU HAVE DIED");
	        	System.exit(0);
	        }
	        
	        System.out.println(player.getHealth());
	        player.setWeaponInc(0);
	        player.setArmed(false);
		}
	}