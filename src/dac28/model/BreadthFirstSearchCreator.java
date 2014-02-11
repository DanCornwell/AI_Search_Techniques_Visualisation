package dac28.model;



/**
 * Creates a breadth first search algorithm class.
 * 
 * @author Dan Cornwell
 *
 */
public class BreadthFirstSearchCreator extends SearchAlgorithmCreator {

	@Override
	public SearchAlgorithm getSearchAlgorithm(Tree tree) {
		return new BreadthFirstSearch(tree);
	}

	

}
