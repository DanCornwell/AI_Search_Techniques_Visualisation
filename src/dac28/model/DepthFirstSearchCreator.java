package dac28.model;


/**
 * Creates a depth first search algorithm class.
 * 
 * @author Dan Cornwell
 *
 */
public class DepthFirstSearchCreator implements SearchAlgorithmCreator {

	@Override
	public SearchAlgorithm getSearchAlgorithm(Tree tree) {
		return new DepthFirstSearch(tree);
	}

}
