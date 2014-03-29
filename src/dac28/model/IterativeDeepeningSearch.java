package dac28.model;

import java.util.HashMap;
import java.util.Stack;

/**
 * Iterative Deepening Search.
 * Works like a depth first search, however will stop at a given limit and
 * expanded the remaining nodes above this limit before moving on.
 * 
 * @author Dan Cornwell
 *
 */
public class IterativeDeepeningSearch extends SearchAlgorithm {

	/**
	 * Integer representing the limit value of iterative deepening search.
	 */
	private final int DEPTH_LIMIT = 2;
	/**
	 * HashMap of node keys and integer values. The values represents the key node's depth within the tree.
	 */
	private HashMap<Node,Integer> nodeLevels;

	/**
	 * Constructor.
	 * Initialises the expanded lists and puts root on to it.
	 * Initialises the HashMap and puts the root with a value of 1 into it.
	 * 
	 * @param TREE - the tree to search upon
	 */
	protected IterativeDeepeningSearch(Tree TREE) {
		
		super(TREE);
		// Depth first search uses a stack data structure
		expanded = new Stack<Node>();
		// Add root to expanded list to allow algorithm to start
		((Stack<Node>) expanded).push(ROOT);
		// Initialise HashMap
		nodeLevels = new HashMap<Node,Integer>();
		// Add root to the HashMap with its level (root has a level 1)
		nodeLevels.put(ROOT, 1);
	}

	@Override
	protected void algorithmLogic() {
		currentNode = ((Stack<Node>) expanded).pop();
		if(atGoal()) {
			// Goal reached so stop
			return;
		}
		else {
			visited.add(currentNode);
			if(currentNode.hasChild()) {
				for(int i=currentNode.getChildren().size()-1;i>-1;i--) {
					Node child = currentNode.getChildren().get(i);
					nodeLevels.put(child, nodeLevels.get(currentNode)+1);
					// if level is not divisible by the depth limit (i.e not at the limit) add node as in DFS
					if(nodeLevels.get(currentNode)%DEPTH_LIMIT != 0) {
						((Stack<Node>) expanded).push(child);
					}
					// if child's level is the limit then add nodes at the back of stack
					// added is a way such that they are 'pushed' at index 0
					else {						
						expanded.add(currentNode.getChildren().size()-i-1, child);
					}
				}
			}
		}
	}

	@Override
	SearchAlgorithm getAlgorithm(Tree tree) {
		return new IterativeDeepeningSearch(tree);
	}
	
}
