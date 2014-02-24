package dac28.model;


/**
 * Interface for the search tree creators.
 * 
 * @author Dan Cornwell
 *
 */
public interface SearchAlgorithmCreator {
	
	/**
	 * Factory method for the search algorithm creators.
	 * Subclasses of this class will override this method to return a specific search algorithm.
	 * 
	 * @param tree - search tree the search algorithm will iterate over
	 * @return a search algorithm
	 */
	public SearchAlgorithm getSearchAlgorithm(Tree tree);
	
}
