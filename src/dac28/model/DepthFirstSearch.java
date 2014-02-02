package dac28.model;

import java.util.Stack;

/**
 * The depth first search algorithm.
 * Implements the search algorithm interface.
 * 
 * @author Dan Cornwell
 *
 */
public class DepthFirstSearch extends SearchAlgorithm {
	
	/**
	 * Depth first search algorithm constructor.
	 * Creates the expanded node list and adds the root node to it.
	 * Takes a tree parameter which is used to set the goal and root node.
	 * 
	 * @param TREE - the tree the algorithm will be using
	 */
	DepthFirstSearch(Tree TREE) {
		
		super(TREE);
		// Depth first search uses a stack data structure
		expanded = new Stack<Node>();
		// Add root to expanded list to allow algorithm to start
		((Stack<Node>) expanded).push(ROOT);
		
	}

	@Override
	void step() {
		if(getGoalReached() == false && !expanded.isEmpty()) {
			Node node = ((Stack<Node>) expanded).pop();
			currentNode = node;
			if(atGoal()) {
				setGoalReached(true);
			}
			else {
				visited.add(currentNode);
				if(currentNode.hasChild()) {
					for(int i=currentNode.getChildren().size()-1;i>-1;i--) {
						((Stack<Node>)expanded).push(currentNode.getChildren().get(i));
					}
				}
			}
		}
	}

}
