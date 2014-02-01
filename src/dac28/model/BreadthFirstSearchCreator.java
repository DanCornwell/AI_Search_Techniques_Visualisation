package dac28.model;

/**
 * Creates a breadth first search algorithm class.
 * 
 * @author Dan Cornwell
 *
 */
class BreadthFirstSearchCreator extends SearchAlgorithmCreator {

	@Override
	SearchAlgorithm factoryMethod() {
		return new BreadthFirstSearch(new Tree124(4));
	}

}
