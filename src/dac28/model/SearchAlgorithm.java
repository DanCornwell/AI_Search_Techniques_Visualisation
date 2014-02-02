package dac28.model;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Defines the interface for the search algorithms.
 * Defines methods to return and set the current node, evaluate the goal and 
 * iterate through the search algorithm.
 * Also returns the expanded and visited node lists.
 * 
 * @author Dan Cornwell
 *
 */
public abstract class SearchAlgorithm {

	/**
	 * Defines the root node, which is used to move through the tree.
	 */
	protected final Node ROOT;
	/**
	 * The current node which the algorithm is currently at.
	 */
	protected Node currentNode;
	/**
	 * The integer representing where the goal node is.
	 */
	private final int GOAL;
	/**
	 * Boolean representing whether we have reached the goal node.
	 */
	private boolean goalReached;
	/**
	 * List representing the nodes the algorithm has traversed.
	 */
	protected LinkedList<Node> visited;
	
	/**
	 * Constructor for a search algorithm.
	 * Takes a Tree parameter, which is used to retrieve the goal node
	 * and the root node.
	 * Also initiates the visited list and sets the goal reached boolean to false.
	 * 
	 * @param TREE - the tree which the algorithm will traverse on
	 */
	SearchAlgorithm(Tree TREE) {
		
		visited = new LinkedList<Node>();
		goalReached = false;
		
		GOAL = TREE.getGoal();
		ROOT = TREE.getRoot();
		
		// initialise current node here to avoid possible null pointer exceptions
		currentNode = ROOT;
		
	}
	
	/**
	 * Checks whether the current node is the goal node.
	 * 
	 * @return true if the current node is the goal node, false otherwise
	 */
	boolean atGoal() {
		return currentNode.getValue() == GOAL;
	}
	
	/**
	 * Sets the goal reached boolean.
	 * 
	 * @param bool - boolean the goal reached is changed to
	 */
	void setGoalReached(boolean bool) {
		goalReached = bool;
	}
	
	/**
	 * Returns the goal reached boolean.
	 * 
	 * @return goal reached boolean
	 */
	boolean getGoalReached() {
		return goalReached;
	}
	
	/**
	 * Returns the visited list.
	 * 
	 * @return list represented the visited nodes
	 */
	LinkedList<Node> getVisited() {
		return visited;
	}
	
	/**
	 * Repeatedly calls the step method until the goal node is reached.
	 */
	void auto() {
		
		while(!goalReached) {
			step();
		}
		
	}
	
	/**
	 * Returns the list of nodes that the algorithm is in course to evaluate.
	 * 
	 * @return list representing the nodes ready to be evaluated
	 */
	abstract Collection<Node> getExpanded();
	
	/**
	 * Performs the next step of the algorithm.
	 */
	abstract void step();
}
