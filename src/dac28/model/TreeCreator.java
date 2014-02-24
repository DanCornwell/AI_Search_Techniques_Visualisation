package dac28.model;



/**
 * Interface for the tree creators.
 * 
 * @author Dan Cornwell
 *
 */
public interface TreeCreator {

	/**
	 * Factory method for the tree creators. 
	 * Subclasses of this class override this method to return specific trees.
	 * Takes a goal parameter which will become the tree's goal node.
	 * 
	 * @param GOAL - value the corresponds to the goal node
	 * @return a search tree
	 */
	public Tree getTree(int GOAL);

}
