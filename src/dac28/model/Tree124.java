package dac28.model;



/**
 * The Tree124 class. Implements the tree interface.
 * This tree has the structure as followed:
 * 					0
 * 				1		2
 * 			  3	  4   5   6
 * 
 * @author Dan Cornwell
 *
 */
class Tree124 extends Tree {
	
	/**
	 * Tree124 constructor.
	 * Takes a goal parameter and calls the super constructor using it.
	 * 
	 * @param GOAL - the goal value
	 */
	Tree124(int GOAL) {
		 
		super(GOAL);
		
	}

	@Override
	void construct() {
		
		// Create nodes
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		
		// Create child links
		one.addChild(three);
		one.addChild(four);
		two.addChild(five);
		two.addChild(six);
		
		// Create child links for root node
		ROOT.addChild(one);
		ROOT.addChild(two);
		
	}

}
