package dac28.model;

/**
 * Creates an instance of the Tree124 class with the given goal value.
 * 
 * @author Dan Cornwell
 *
 */
class Tree124Creator extends TreeCreator {

	@Override
	Tree factoryMethod(int GOAL) {
		return new Tree124(GOAL);
	}

}
