package dac28.model;


/**
 * Creates a depth first search algorithm class.
 * 
 * @author Dan Cornwell
 *
 */
public class DepthFirstSearchCreator extends SearchAlgorithmCreator {

	@Override
	public SearchAlgorithm getSearchAlgorithm(Tree tree) {
		return new DepthFirstSearch(tree);
	}

}
