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
	protected void construct() {
		
		Node one = new Node("1",id++);
		Node two = new Node("2",id++);
		Node three = new Node("3",id++);
		Node four = new Node("4",id++);
		Node five = new Node("5",id++);
		Node six = new Node("6",id++);
		
		one.addChild(two);
		one.addChild(three);
		two.addChild(four);
		four.addChild(five);
		five.addChild(six);
		
		root.addChild(one);
	}

	@Override
	protected void construct(Queue<String> values) {

		while(values.size() < 6) {
			values.add("Node");
		}
		
		Node one = new Node(values.poll(),id++);
		Node two = new Node(values.poll(),id++);
		Node three = new Node(values.poll(),id++);
		Node four = new Node(values.poll(),id++);
		Node five = new Node(values.poll(),id++);
		Node six = new Node(values.poll(),id++);
		
		one.addChild(two);
		one.addChild(three);
		two.addChild(four);
		four.addChild(five);
		five.addChild(six);
		
		root.addChild(one);
	}

	@Override
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new Tree112111(goalValue,values);
	}
	
}
