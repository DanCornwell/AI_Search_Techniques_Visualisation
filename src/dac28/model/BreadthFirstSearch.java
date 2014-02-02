package dac28.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The breadth first search algorithm. 
 * Implements the search algorithm interface.
 * 
 * @author Dan Cornwell
 *
 */
public class BreadthFirstSearch extends SearchAlgorithm {

	/**
	 * Data structure the algorithm uses to hold expanded nodes.
	 */
	private Queue<Node> expanded;

	/**
	 * Breadth first search algorithm constructor.
	 * Creates the expanded node list and adds the root node to it.
	 * Takes a tree parameter which is used to set the goal and root node.
	 * 
	 * @param TREE - the tree the algorithm will be using
	 */
	BreadthFirstSearch(Tree TREE) {

		super(TREE);
		expanded = new LinkedList<Node>();
		expanded.add(ROOT);

	}

	@Override
	Collection<Node> getExpanded() {
		return expanded;
	}

	@Override
	void step() {
		if(getGoalReached() == false) {
			Node node = expanded.remove();
			currentNode = node;
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
