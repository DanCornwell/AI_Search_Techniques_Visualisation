package dac28.model;

import java.util.Queue;

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
	 * @param values - the values to set the nodes to, can be empty to use default values
	 */
	Tree112111(String GOAL, Queue<String> values) {
		super(GOAL, values);
	}

	@Override
	void construct() {
		
		Node one = new Node("1");
		Node two = new Node("2");
		Node three = new Node("3");
		Node four = new Node("4");
		Node five = new Node("5");
		Node six = new Node("6");
		
		one.addChild(two);
		one.addChild(three);
		two.addChild(four);
		four.addChild(five);
		five.addChild(six);
		
		ROOT.addChild(one);
	}

	@Override
	void construct(Queue<String> values) {

		Node one = new Node(values.poll());
		Node two = new Node(values.poll());
		Node three = new Node(values.poll());
		Node four = new Node(values.poll());
		Node five = new Node(values.poll());
		Node six = new Node(values.poll());
		
		one.addChild(two);
		one.addChild(three);
		two.addChild(four);
		four.addChild(five);
		five.addChild(six);
		
		ROOT.addChild(one);
	}

	@Override
	Tree getTree(String goalValue, Queue<String> values) {
		return new Tree112111(goalValue,values);
	}
	
}
