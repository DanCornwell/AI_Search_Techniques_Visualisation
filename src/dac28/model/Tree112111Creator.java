package dac28.model;


/**
 * Creates an instance of the Tree112111 class with the given goal value.
 * 
 * @author Dan Cornwell
 *
 */
public class Tree112111Creator implements TreeCreator {

	@Override
	public Tree getTree(int GOAL) {
		return new Tree112111(GOAL);
	}

}
