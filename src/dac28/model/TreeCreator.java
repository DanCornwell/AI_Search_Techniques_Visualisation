package dac28.model;

/**
 * Interface for the tree creators.
 * 
 * @author Dan Cornwell
 *
 */
abstract class TreeCreator {

	/**
	 * Factory method for the tree creators. 
	 * Subclasses of this class override this method to return specific trees.
	 * Takes a goal parameter which will become the tree's goal node.
	 * 
	 * @param GOAL - value the corresponds to the goal node
	 * @return a search tree
	 */
	abstract Tree getTree(int GOAL);

}
