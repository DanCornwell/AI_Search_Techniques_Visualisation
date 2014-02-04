package dac28.model.search_algorithm;

import java.util.LinkedList;
import java.util.List;

import dac28.model.node.Node;
import dac28.model.tree.Tree;

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
	 * List representing the nodes the algorithm will analyse.
	 */
	protected List<Node> expanded;
		
	/**
	 * Constructor for a search algorithm.
	 * Takes a Tree parameter, which is used to retrieve the goal node
	 * and the root node.
	 * Also initiates the visited list and sets the goal reached boolean to false.
	 * Subclasses must add ROOT node to their respective expanded lists in order for 
	 * the algorithm to work.
	 * 
	 * @param TREE - the tree which the algorithm will traverse on
	 */
	protected SearchAlgorithm(Tree TREE) {
		
		visited = new LinkedList<Node>();
		goalReached = false;
		
		GOAL = TREE.getGoal();
		ROOT = TREE.getRoot();

	}
	
	/**
	 * Checks whether the current node is the goal node.
	 * 
	 * @return true if the current node is the goal node, false otherwise
	 */
	protected final boolean atGoal() {
		return currentNode.getValue() == GOAL;
	}
	
	/**
	 * Sets the goal reached boolean.
	 * 
	 * @param bool - boolean the goal reached is changed to
	 */
	protected final void setGoalReached(boolean bool) {
		goalReached = bool;
	}
	
	/**
	 * Returns the goal reached boolean.
	 * 
	 * @return goal reached boolean
	 */
	protected final boolean getGoalReached() {
		return goalReached;
	}
	
	/**
	 * Returns the visited list.
	 * 
	 * @return list represented the visited nodes
	 */
	final LinkedList<Node> getVisited() {
		return visited;
	}
	
	/**
	 * Repeatedly calls the step method until the goal node is reached
	 * or the expanded queue is empty.
	 */
	final void auto() {
		
		while(!goalReached && !expanded.isEmpty()) {
			step();
		}
		
	}
	
	/**
	 * Returns the list of nodes that the algorithm is in course to evaluate.
	 * 
	 * @return list representing the nodes ready to be evaluated
	 */
	final List<Node> getExpanded() {
		return expanded;
	}
	
	/**
	 * Resets the search algorithm.
	 * Clears the visited and expanded list.
	 * Sets the current node to the root.
	 */
	final void reset() {
		visited.clear();
		expanded.clear();
		currentNode = ROOT;
	}
	
	/**
	 * Performs the next step of the algorithm.
	 */
	protected abstract void step();
	
}
