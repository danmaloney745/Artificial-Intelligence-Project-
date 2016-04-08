package ie.gmit.sw.characters;

import ie.gmit.sw.maze.Node;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Player 
{
	private Node currentNode;
	private int health = 100;
	private int playerSteps = 1;
	private boolean isArmed;
	private int incWeapon;

    public int getSteps()
	{
		return playerSteps;
	}
	public Node getCurrentNode()
	{
		return currentNode;
	}
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
	public int getWeaponInc() {
		return incWeapon;
	}
	public void setWeaponInc(int incWeapon) {
		this.incWeapon = incWeapon;
	}
}
