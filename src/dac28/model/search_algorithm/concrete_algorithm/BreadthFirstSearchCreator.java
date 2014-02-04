package dac28.model.search_algorithm.concrete_algorithm;

import dac28.model.search_algorithm.SearchAlgorithm;
import dac28.model.search_algorithm.SearchAlgorithmCreator;
import dac28.model.tree.Tree;


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
