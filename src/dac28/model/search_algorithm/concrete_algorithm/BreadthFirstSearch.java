package dac28.model.search_algorithm.concrete_algorithm;

import java.util.LinkedList;

import dac28.model.node.Node;
import dac28.model.search_algorithm.SearchAlgorithm;
import dac28.model.tree.Tree;

/**
 * The breadth first search algorithm. 
 * Implements the search algorithm interface.
 * 
 * @author Dan Cornwell
 *
 */
class BreadthFirstSearch extends SearchAlgorithm {

	/**
	 * Breadth first search algorithm constructor.
	 * Creates the expanded node list and adds the root node to it.
	 * Takes a tree parameter which is used to set the goal and root node.
	 * 
	 * @param TREE - the tree the algorithm will be using
	 */
	BreadthFirstSearch(Tree TREE) {

		super(TREE);
		// LinkedList is a queue type data structure
		expanded = new LinkedList<Node>();
		// Add root to expanded list to allow algorithm to start
		((LinkedList<Node>)expanded).add(ROOT);

	}

	@Override
	protected void step() {
		if(!getGoalReached() && !expanded.isEmpty()) {
			currentNode = ((LinkedList<Node>)expanded).remove();
			if(atGoal()) {
				setGoalReached(true);
			}
			else {
				visited.add(currentNode);
				if(currentNode.hasChild()) {
					for(int i=0;i<currentNode.getChildren().size();i++) {
						expanded.add(currentNode.getChildren().get(i));
					}
				}
			}
		}
	}

}
