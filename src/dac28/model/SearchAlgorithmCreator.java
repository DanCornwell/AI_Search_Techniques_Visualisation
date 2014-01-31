package dac28.model;

/**
 * Interface for the search tree creators.
 * 
 * @author Dan Cornwell
 *
 */
abstract class SearchAlgorithmCreator {
	
	/**
	 * Returns a search algorithm.
	 * 
	 * @return a search algorithm
	 */
	SearchAlgorithm getSearchAlgorithm() {
		
		return factoryMethod();
	}
	
	/**
	 * Returns a specific search algorithm.
	 * 
	 * @return a search algorithm
	 */
	abstract SearchAlgorithm factoryMethod();

}
