package dac28.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Iterative deepening search.
 * Applies depth first search on each level of the tree, increasing the level if depth first fails to
 * find the goal.
 * Stops when a goal is found or the iteration is larger than the tree depth (i.e all levels have been expanded)
 * 
 * @author Dan Cornwell
 *
 */
class IterativeDeepeningSearch extends SearchAlgorithm {

	/**
	 * HashMap storing the nodes in the tree as keys and their level within the tree as the values.
	 */
	private HashMap<Node,Integer> nodeLevels;
	/**
	 * The current iteration number.
	 */
	private int iteration;
	/**
	 * The depth of the tree the algorithm is searching.
	 */
	private final int TREE_DEPTH;

	protected IterativeDeepeningSearch(Tree TREE) {

		super(TREE);

		// Initialise HashMap
		nodeLevels = new HashMap<Node,Integer>();
		// Initialise expanded list and add root to it
		expanded = new Stack<Node>();
		((Stack<Node>) expanded).push(ROOT);
		// Set the tree depth variable (-1 since getTreeDepth counts level 0 as a level)
		TREE_DEPTH = TREE.getTreeDepth()-1;
		// Set the iteration number to 0 (will iterate over level 0 first)
		iteration = 0;
		// Adds the nodes to the hash map with their values
		setup();

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
			
			// if the next level (current node's children) is bigger than the iteration number and has children
			// add the children as in depth first search
			if(nodeLevels.get(currentNode)+1 <= iteration && currentNode.hasChild()) {
				
				for(int i=currentNode.getChildren().size()-1;i>-1;i--) {
					((Stack<Node>) expanded).push(currentNode.getChildren().get(i));
				}
			}
		}

		// if the expanded list is empty and iteration number is smaller than the tree depth (i.e more level to iterate over)
		if(expanded.isEmpty() && iteration < TREE_DEPTH) {
			iteration++;
			((Stack<Node>) expanded).push(ROOT);
		}
		

	}

	@Override
	SearchAlgorithm getAlgorithm(Tree tree) {
		return new IterativeDeepeningSearch(tree);
	}

	/**
	 * Adds the nodes to the node levels HashMap with their level values.
	 */
	private void setup() {

		Queue<Node> nodes = new LinkedList<Node>();
		nodes.add(ROOT);

		// Add root to the HashMap with its level (root has a level 0)
		nodeLevels.put(ROOT, 0);
		// Terminates when no nodes left, i.e has expanded all the leaf nodes
		while(!nodes.isEmpty()) {

			Node parent = nodes.poll();

			for(Node child: parent.getChildren()) {

				nodes.add(child);
				nodeLevels.put(child, nodeLevels.get(parent)+1);
				nodes.add(child);

			}
		}
	}

}
