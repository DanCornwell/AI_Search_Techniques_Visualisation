package dac28.model;


/**
 * The Tree124 class. Implements the tree interface.
 * This tree has the structure as followed:
 * 					0
 * 				1	2	3
 * 			4	5	6	7	8
 * 			  9	  10  11  12
 * 
 * @author Dan Cornwell
 *
 */
class Tree1354 extends Tree {

	/**
	 * Tree124 constructor.
	 * Takes a goal parameter and calls the super constructor using it.
	 * 
	 * @param GOAL - the goal value
	 */
	Tree1354(final int GOAL) {
		 
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
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		Node eleven = new Node(11);
		Node twelve = new Node(12);
		Node thirteen = new Node(13);
		
		// Create child links
		one.addChild(four);
		two.addChild(five);
		two.addChild(six);
		three.addChild(seven);
		three.addChild(eight);
		five.addChild(nine);
		five.addChild(ten);
		six.addChild(eleven);
		seven.addChild(twelve);
		eight.addChild(thirteen);
		
		// Create child links for root node
		ROOT.addChild(one);
		ROOT.addChild(two);
		ROOT.addChild(three);
		
	}
	
}
