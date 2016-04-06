package ie.gmit.sw.character;

import ie.gmit.sw.maze.Node;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Player 
{
	//Player class uses fuzzy logic for combat scenarios
	
	private Node currentNode;
	private int health = 100;
	private int playerSteps = 1;
	private boolean isArmed;
	private int numberOfWeapons;
	
    public void setCurrentNode(Node currentNode) 
	{
		this.currentNode = currentNode;
		playerSteps++;
	}
	
    public int getHealth() {
		return health;
	}
	
    public void setHealth(int health) {
		this.health = health;
	}
	
    public boolean isArmed() {
		return isArmed;
	}
	
    public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
	
    public int getNumberOfWeapons() {
		return numberOfWeapons;
	}
	
    public void setNumberOfWeapons(int numberOfWeapons) {
		this.numberOfWeapons = numberOfWeapons;
	}
    
    public int getSteps()
	{
		return playerSteps;
	}
	
    public Node getCurrentNode()
	{
		return currentNode;
	}
	
	//Combat system using fuzzylogic
    public void attack()
	{
    	String fileName = "FCL/fightsOutcome.fcl";
        FIS fis = FIS.load(fileName,true);
        
        // Error while loading?
        if( fis == null )
        { 
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }
       
        //Retrieve function block
        FunctionBlock functionBlock = fis.getFunctionBlock("fight");


        //Set inputs
        fis.setVariable("health", this.health);
        fis.setVariable("numberOfWeapons", this.numberOfWeapons);

        //Evaluate the equations
        fis.evaluate();
        
        //Return the values
        Variable damage = functionBlock.getVariable("damage");
     
        this.health -= damage.getValue();
        System.out.println(health);
        this.numberOfWeapons = 0;
        setArmed(false);
	}
}
