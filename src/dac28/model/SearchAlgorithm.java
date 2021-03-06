package dac28.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


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
	 * The String representing where the goal node is.
	 */
	private final String GOAL;
	/**
	 * List representing the nodes the algorithm has traversed.
	 */
	protected LinkedList<Node> visited;
	/**
	 * List representing the nodes the algorithm will analyse.
	 */
	protected List<Node> expanded;
	/**
	 * Holds a stack of mementos.
	 */
	private Stack<Memento> mementos;

	/**
	 * Constructor for a search algorithm.
	 * Takes a Tree parameter, which is used to retrieve the tree root.
	 * Takes a String parameter, which is used as the goal value.
	 * Also initiates the visited list and sets the goal reached boolean to false.
	 * Subclasses must add ROOT node to their respective expanded lists in order for 
	 * the algorithm to work.
	 * 
	 * @param TREE - the tree which the algorithm will traverse on
	 * @param GOAL - the goal value that algorithm will search for
	 */
	protected SearchAlgorithm(final Tree TREE,final String GOAL) {

		visited = new LinkedList<Node>();
		mementos = new Stack<Memento>();

		ROOT = TREE.getRoot();
		this.GOAL = GOAL;
		
		currentNode = ROOT;

	}

	/**
	 * Returns a copy of the current node.
	 * The value and unique id will be the same as the values in the current node.
	 * 
	 * @return node representing the current node
	 */
	public final Node getCurrentNode() {
		return new Node(currentNode.getValue(),currentNode.getUID());
	}
	
	/**
	 * Checks whether the current node is the goal node.
	 * 
	 * @return true if the current node is the goal node, false otherwise
	 */
	public final boolean atGoal() {
		return currentNode.getValue().equals(GOAL);
	}

	/**
	 * Returns the visited list.
	 * 
	 * @return list represented the visited nodes
	 */
	public final LinkedList<Node> getVisited() {
		return new LinkedList<Node>(visited);
	}

	/**
	 * Returns the list of nodes that the algorithm is in course to evaluate.
	 * 
	 * @return list representing the nodes ready to be evaluated
	 */
	public final List<Node> getExpanded() {
		return Collections.unmodifiableList(expanded);
	}

	/**
	 * Resets the search algorithm.
	 * Clears the visited and expanded list.
	 * Sets the current node to the root and adds it to the expanded list.
	 */
	public final void reset() {
		visited.clear();
		expanded.clear();
		currentNode = ROOT;
		expanded.add(ROOT);
		mementos.clear();
		algorithmResetLogic();
	}
	
	/**
	 * Method to allow subclasses to define specific actions during the reset method.
	 * Subclasses that do not need specific actions should not override this method.
	 */
	protected void algorithmResetLogic() {
	}

	/**
	 * Returns whether we can undo a step.
	 * 
	 * @return true if the mementos list is not empty, false otherwise
	 */
	public final boolean canUndo() {
		return !mementos.isEmpty();
	}
	
	/**
	 * Undoes a step in the algorithm.
	 */
	public final void undo() {
		if(canUndo()) {
			Memento memento = mementos.pop();
			currentNode = memento.STATE_CURRENT_NODE;
			// As expanded list is determined by the subclass, clear the expanded list
			// and add the elements from the state expanded list
			expanded.clear();
			for(int i=0;i<memento.STATE_EXPANDED.size();i++) {
				expanded.add(memento.STATE_EXPANDED.get(i));
			}
			visited = memento.STATE_VISITED;
			algorithmUndoLogic();
		}
	}
	
	/**
	 * Method to allow subclasses to define specific actions during the undo method.
	 * Subclasses that do not need specific actions should not override this method.
	 */
	protected void algorithmUndoLogic() {
	}
	
	/**
	 * Performs a step of the algorithm.
	 * Adds a memento to the memento stack if we can.
	 */ 
	public final void step() {

		if(!atGoal() && !expanded.isEmpty()) {
			mementos.push(new Memento());
			algorithmStepLogic();
		}
	}
	
	/**
	 * Defines the logic that the algorithm uses to carry out a step.
	 * In order for the search algorithm subclass to terminate, this 
	 * method should call a method that removes an element from expanded.
	 * E.g expanded.pop() is the case of expanded being a stack
	 */
	protected abstract void algorithmStepLogic();
	
	/**
	 * Inner class for a search algorithm memento.
	 * Stores the current node, visited list and expanded list.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class Memento {

		/**
		 * Current node that will be stored.
		 */
		private final Node STATE_CURRENT_NODE;
		/**
		 * Expanded list that will be stored.
		 */
		private final List<Node> STATE_EXPANDED;
		/**
		 * Visited list that will be stored.
		 */
		private final LinkedList<Node> STATE_VISITED;

		/**
		 * Builds a memento based on the current search algorithm data.
		 */
		Memento() {

			STATE_CURRENT_NODE = currentNode;
			// As expanded list is determined by the subclass, add all expanded elements into a 
			// new linked list
			STATE_EXPANDED = new LinkedList<Node>();
			for(int i=0;i<expanded.size();i++) {
				STATE_EXPANDED.add(expanded.get(i));
			}
			// Use a copy constructor to get the state visited list
			STATE_VISITED = getVisited();

		}

	}
	
	/**
	 * Returns a concrete search algorithm
	 * 
	 * @param tree - tree instance the algorithm will search upon
	 * @param goal - the goal value the algorithm will search for 
	 * @return a concrete search algorithm
	 */
	protected abstract SearchAlgorithm getAlgorithm(Tree tree,String goal);

}
