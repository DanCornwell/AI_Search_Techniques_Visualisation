package dac28.model;

/**
 * Creates a breadth first search algorithm class.
 * 
 * @author Dan Cornwell
 *
 */
class BreadthFirstSearchCreator extends SearchAlgorithmCreator {

	@Override
	SearchAlgorithm getSearchAlgorithm(Tree tree) {
		return new BreadthFirstSearch(tree);
	}

	

}
