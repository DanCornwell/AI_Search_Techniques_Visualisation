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
	private final String GOAL;
	/**
	 * The root node, any tree will always have a root node.
	 */
	protected final Node ROOT;
	/**
	 * A queue of integers representing the path cost between nodes.
	 */
	private Queue<Integer> pathCosts;
		
	/**
	 * Constructor.
	 * Sets the goal variable and creates the root node.
	 * If the values supplied have blank strings or duplicates, these are replaced by default values
	 * If the values parameter is empty, then it will be used to set the node values.
	 * 
	 * @param GOAL - String to the set the goal node
	 * @param values - a queue of strings that will set the node values, can empty list to use default values
	 */
	Tree(final String GOAL,Queue<String> values) {

		this.GOAL = GOAL;
		
		if(!values.isEmpty()) {
			
			Queue<String> validatedValues = new LinkedList<String>();
			
			int defaultValue = 1;
			
			while(!values.isEmpty()) {
				
				String value = values.poll();
				
				if(values.contains("N"+String.valueOf(defaultValue))) defaultValue++;
				
				if(value.trim().equals("")) validatedValues.add("N"+String.valueOf(defaultValue++));
				else if(validatedValues.contains(value)) validatedValues.add("N"+String.valueOf(defaultValue++));
				else validatedValues.add(value);
				
			}
			
			ROOT = new Node(validatedValues.poll());
						
			construct(validatedValues);
		}
		
		// Use default number values for node values
		else {
			
			ROOT = new Node("0");
			construct();
		}
		
		pathCosts = new LinkedList<Integer>();
	} 

	/**
	 * Returns the goal value.
	 * 
	 * @return - the goal variable
	 */
	public final String getGoal() {
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
	 * Sets the path cost queue.
	 * This method should be called if user input for path costs is required.
	 * 
	 * @param costs - new path costs for the tree
	 */
	public final void setPathCosts(Queue<Integer> costs) {
		this.pathCosts = costs;
	}
	
	/**
	 * Returns the path cost queue.
	 * 
	 * @return a queue of integers representing the path costs
	 */
	public final Queue<Integer> getPathCosts() {
		return new LinkedList<Integer>(pathCosts);
	}
	
	/**
	 * Creates nodes and connects them to the root node using default number values.
	 */
	protected abstract void construct();

	/**
	 * Creates nodes with specified values and connected them to the root node.
	 * 
	 * @param values - queue of values that can be polled for the values of the nodes
	 */
	protected abstract void construct(Queue<String> values);

	/**
	 * Returns a concrete instance of the subclass tree.
	 * If the values is empty, default values will be used.
	 * 
	 * @param goalValue - goal value to initialise tree with
	 * @param values - values to set the nodes. If this is empty, default values are supplied
	 * @return a concrete tree
	 */
	protected abstract Tree getTree(String goalValue,Queue<String> values);

} 
