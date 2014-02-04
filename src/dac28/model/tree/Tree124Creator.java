package dac28.model.tree;


/**
 * Creates an instance of the Tree124 class with the given goal value.
 * 
 * @author Dan Cornwell
 *
 */
public class Tree124Creator extends TreeCreator {

	@Override
	public Tree getTree(int GOAL) {
		return new Tree124(GOAL);
	}

	

}
