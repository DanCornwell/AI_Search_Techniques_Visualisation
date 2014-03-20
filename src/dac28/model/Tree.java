package dac28.model;

import java.util.LinkedList;
import java.util.Queue;



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
	Tree(final int GOAL) {

		this.GOAL = GOAL;
		ROOT = new Node(0);
		construct();

	}

	/**
	 * Returns the goal value.
	 * 
	 * @return - the goal variable
	 */
	public final int getGoal() {
		return GOAL;
	}

	/**
	 * Returns the root node.
	 * 
	 * @return - the root variable
	 */
	public final Node getRoot() {
		return ROOT;
	}

	/**
	 * Returns the maximum depth of the tree. 
	 * Calls the recursive getTreeDepth method using the root node.
	 * 
	 * @return integer representing the maximum depth of the tree
	 */
	public final int getTreeDepth() {
		return getTreeDepth(ROOT);
	}
	
	/**
	 * Returns the maximum depth from the given node via recursion.
	 * 
	 * @param node - the node we want the depth of
	 * @return integer representing the maximum depth from the node
	 */
	private final int getTreeDepth(Node node) {

		int max = 0;
		for (Node childNode: node.getChildren()) {
			int height = getTreeDepth(childNode);
			if (height > max)
				max = height;
		}
		return max + 1;

	}

	/**
	 * Returns the maximum width of the tree. O(n) time complexity.
	 * 
	 * @return integer representing the maximum width of the tree
	 */
	public final int getTreeWidth() {

		Queue<Node> parents = new LinkedList<Node>();
		Queue<Node> children = new LinkedList<Node>();
		parents.add(ROOT);

		int width = 0;
		while(!parents.isEmpty()) {
			// Removes all parents and add their children to the children list
			while(!parents.isEmpty()) {
				Node parent = parents.remove();
				for(Node child: parent.getChildren()) {
					children.add(child);
				}
			}
			
			if(children.size() > width) width = children.size();
			
			// If children list is empty then parent list will be empty and hence loop terminates
			parents.addAll(children);
			children.clear();
		}
		return width;

	}

	/**
	 * Creates nodes and connects them to the root node.
	 */
	abstract void construct();

	/**
	 * Returns a concrete instance of the subclass tree
	 * 
	 * @param goalValue - goal value to initialise tree with
	 * @return a concrete tree
	 */
	abstract Tree getTree(int goalValue);
}
