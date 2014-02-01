package dac28.model;

/**
 * Interface for the tree creators.
 * 
 * @author Dan Cornwell
 *
 */
abstract class TreeCreator {

	/**
	 * Returns a search tree with a supplied goal node.
	 * 
	 * @param GOAL - the goal node for this search tree
	 * @return search tree with the supplied goal node
	 */
	Tree getTree(int GOAL) {

		return factoryMethod(GOAL);

	}
	
	/**
	 * Creates a specific search tree.
	 * 
	 * @param GOAL - the goal node of the search tree
	 * @return a concrete search tree
	 */
	abstract Tree factoryMethod(int GOAL);


}
