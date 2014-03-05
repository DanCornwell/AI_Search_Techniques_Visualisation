package dac28.model;


/**
 * The Tree112111 class. Implements the tree interface.
 * This tree has the structure as followed:
 * 					0	
 * 					1
 * 				2		3
 * 				4
 * 				5		
 * 				6
 * 
 * @author Dan Cornwell
 *
 */
class Tree112111 extends Tree {

	/**
	 * Tree112111 constructor.
	 * Takes a goal parameter and calls the super constructor using it.
	 * 
	 * @param GOAL - the goal value
	 */
	Tree112111(final int GOAL) {
		super(GOAL);
	}

	@Override
	void construct() {
		
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		
		one.addChild(two);
		one.addChild(three);
		two.addChild(four);
		four.addChild(five);
		five.addChild(six);
		
		ROOT.addChild(one);
	}

}
