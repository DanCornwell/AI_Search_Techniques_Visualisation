package dac28.model;

import java.util.Stack;



/**
 * The depth first search algorithm.
 * Implements the search algorithm interface.
 * 
 * @author Dan Cornwell
 *
 */
class DepthFirstSearch extends SearchAlgorithm {

	/**
	 * Depth first search algorithm constructor.
	 * Calls the superclass constructor.
	 * 
	 * Initialises the expanded list as a Stack and pushes the root onto it.
	 * 
	 * @param TREE - the tree which the algorithm will traverse on
	 * @param GOAL - the goal value that algorithm will search for
	 */
	DepthFirstSearch(Tree TREE,String GOAL) {

		super(TREE,GOAL);
		// Depth first search uses a stack data structure
		expanded = new Stack<Node>();
		// Add root to expanded list to allow algorithm to start
		((Stack<Node>) expanded).push(ROOT);

	}

	@Override
	protected void algorithmStepLogic() {

		currentNode = ((Stack<Node>) expanded).pop();
		if(atGoal()) {
			// Goal reached so stop
			return;
		}
		else {
			visited.add(currentNode);
			if(currentNode.hasChild()) {
				for(int i=currentNode.getChildren().size()-1;i>-1;i--) {
					((Stack<Node>) expanded).push(currentNode.getChildren().get(i));
				}
			}
		}
	}

	@Override
	protected SearchAlgorithm getAlgorithm(Tree tree,String goal) {
		return new DepthFirstSearch(tree,goal);
	}

}
