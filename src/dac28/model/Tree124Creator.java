package dac28.model;



/**
 * Creates an instance of the Tree124 class with the given goal value.
 * 
 * @author Dan Cornwell
 *
 */
public class Tree124Creator implements TreeCreator {

	@Override
	public Tree getTree(int GOAL) {
		return new Tree124(GOAL);
	}

	

}
