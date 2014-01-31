package dac28.model;

/**
 * Interface for the tree creators.
 * 
 * @author Dan Cornwell
 *
 */
abstract class TreeCreator {

	/**
	 * Returns a tree.
	 * 
	 * @return a search tree
	 */
	Tree getTree() {

		return factoryMethod();

	}
	
	/**
	 * Returns a specific tree.
	 * 
	 * @return a search tree
	 */
	abstract Tree factoryMethod();


}
