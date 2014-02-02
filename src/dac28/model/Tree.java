package dac28.model;

/**
 * Abstract class the defines methods for the search trees.
 * Provides method to return the goal value and root node.
 * Defines a construct method which subclasses will use to build specific trees.
 * 
 * @author Dan Cornwell
 *
 */
public abstract class Tree {
	
	/**
	 * The goal value.
	 */
	private final int GOAL;
	/**
	 * The root node, any tree will always have a root node.
	 */
	protected final Node ROOT;
	
	/**
	 * Constructor.
	 * Sets the goal variable and creates the root node.
	 * Also calls the construct method to construct the tree. This method will be defined
	 * by subclasses. 
	 * 
	 * @param GOAL - integer to set the goal node
	 */
	Tree(int GOAL) {
		
		this.GOAL = GOAL;
		ROOT = new Node(0);
		construct();
		
	}
	
	/**
	 * Returns the goal value.
	 * 
	 * @return - the goal variable
	 */
	final int getGoal() {
		return GOAL;
	}
	
	/**
	 * Returns the root node.
	 * 
	 * @return - the root variable
	 */
	final Node getRoot() {
		return ROOT;
	}
	
	/**
	 * Creates nodes and connects them to the root node.
	 */
	abstract void construct();

}
