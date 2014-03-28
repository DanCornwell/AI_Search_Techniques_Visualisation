package dac28.model;

import java.util.Queue;



/**
 * The Tree124 class. Implements the tree interface.
 * This tree has the structure as followed:
 * 					0
 * 				1	2	3
 * 			4	5	6	7	8
 * 		 9	  10  11  12 13
 * 
 * @author Dan Cornwell
 *
 */
class Tree1355 extends Tree {

	/**
	 * Calls the tree superclass.
	 * 
	 * @param GOAL - the goal value for tree
	 * @param values - the values to set the nodes to, can be empty to use default values
	 */
	Tree1355(String GOAL, Queue<String> values) {
		super(GOAL, values);
	}

	@Override
	void construct() {
		
		// Create nodes
		Node one = new Node("1");
		Node two = new Node("2");
		Node three = new Node("3");
		Node four = new Node("4");
		Node five = new Node("5");
		Node six = new Node("6");
		Node seven = new Node("7");
		Node eight = new Node("8");
		Node nine = new Node("9");
		Node ten = new Node("10");
		Node eleven = new Node("11");
		Node twelve = new Node("12");
		Node thirteen = new Node("13");
		
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
	
	@Override
	void construct(Queue<String> values) {
	
		if(values.size() < 13) return;
		
		// Create nodes
		Node one = new Node(values.poll());
		Node two = new Node(values.poll());
		Node three = new Node(values.poll());
		Node four = new Node(values.poll());
		Node five = new Node(values.poll());
		Node six = new Node(values.poll());
		Node seven = new Node(values.poll());
		Node eight = new Node(values.poll());
		Node nine = new Node(values.poll());
		Node ten = new Node(values.poll());
		Node eleven = new Node(values.poll());
		Node twelve = new Node(values.poll());
		Node thirteen = new Node(values.poll());
		
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

	@Override
	Tree getTree(String goalValue, Queue<String> values) {
		return new Tree1355(goalValue,values);
	}
	
}
