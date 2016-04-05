package ie.gmit.sw.maze;

public interface MazeGenerator {
	public void setGoalNode();
	public Node getGoalNode();
	public Node[][] getMaze();
	public void generateMaze(int rows, int cols);
}
