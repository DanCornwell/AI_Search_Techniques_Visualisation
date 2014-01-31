package dac28.model;

/**
 * Creates an instance of the Tree124 class.
 * Sets the goal to be the 4 node.
 * 
 * @author Dan Cornwell
 *
 */
class Tree124Creator extends TreeCreator {

	@Override
	Tree factoryMethod() {
		return new Tree124(4);
	}

}
