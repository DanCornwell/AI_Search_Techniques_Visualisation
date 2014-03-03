package dac28.model;


/**
 * Creator for the 1354 tree.
 * 
 * @author Dan Cornwell
 *
 */
public class Tree1354Creator implements TreeCreator {

	@Override
	public Tree getTree(int GOAL) {
		return new Tree1354(GOAL);
	}

}
