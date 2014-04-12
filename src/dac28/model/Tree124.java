package dac28.model;

import java.util.Queue;




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
	 * @param values - the values to set the nodes to, can be empty to use default values 
	 */
	Tree124(String GOAL, Queue<String> values) {
		super(GOAL, values);
	}

	@Override
	protected void construct() {

		// Create nodes
		Node one = new Node("1",id++);
		Node two = new Node("2",id++);
		Node three = new Node("3",id++);
		Node four = new Node("4",id++);
		Node five = new Node("5",id++);
		Node six = new Node("6",id++);

		// Create child links
		one.addChild(three);
		one.addChild(four);
		two.addChild(five);
		two.addChild(six);

		// Create child links for root node
		root.addChild(one);
		root.addChild(two);

	}

	@Override
	protected void construct(Queue<String> values) {

		while(values.size() < 6) {
			values.add("Node");
		}
		
		// Create nodes
		Node one = new Node(values.poll(),id++);
		Node two = new Node(values.poll(),id++);
		Node three = new Node(values.poll(),id++);
		Node four = new Node(values.poll(),id++);
		Node five = new Node(values.poll(),id++);
		Node six = new Node(values.poll(),id++);

		// Create child links
		one.addChild(three);
		one.addChild(four);
		two.addChild(five);
		two.addChild(six);

		// Create child links for root node
		root.addChild(one);
		root.addChild(two);
	}

	@Override
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new Tree124(goalValue,values);
	}

}
