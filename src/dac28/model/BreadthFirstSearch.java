package dac28.model;

import java.util.LinkedList;



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
	 * Calls the superclass constructor.
	 * 
	 * Initialises the expanded list as a LinkedList and adds the root onto it.
	 * 
	 * @param TREE - the tree which the algorithm will traverse on
	 * @param GOAL - the goal value that algorithm will search for
	 */
	BreadthFirstSearch(Tree TREE,String GOAL) {

		super(TREE,GOAL);
		// LinkedList is a queue type data structure
		expanded = new LinkedList<Node>();
		// Add root to expanded list to allow algorithm to start
		((LinkedList<Node>)expanded).add(ROOT);

	}

	@Override
	protected void algorithmStepLogic() {
		
		currentNode = ((LinkedList<Node>)expanded).remove();
		if(atGoal()) {
			// Goal reached so stop
			return;
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

	@Override
	protected SearchAlgorithm getAlgorithm(Tree tree,String goal) {
		return new BreadthFirstSearch(tree,goal);
	}


}
