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
	protected void construct() {
		
		// Create nodes
		Node one = new Node("1",id++);
		Node two = new Node("2",id++);
		Node three = new Node("3",id++);
		Node four = new Node("4",id++);
		Node five = new Node("5",id++);
		Node six = new Node("6",id++);
		Node seven = new Node("7",id++);
		Node eight = new Node("8",id++);
		Node nine = new Node("9",id++);
		Node ten = new Node("10",id++);
		Node eleven = new Node("11",id++);
		Node twelve = new Node("12",id++);
		Node thirteen = new Node("13",id++);
		
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
		root.addChild(one);
		root.addChild(two);
		root.addChild(three);
		
	}
	
	@Override
	protected void construct(Queue<String> values) {
	
		while(values.size() < 13) {
			values.add("Node");
		}
		
		// Create nodes
		Node one = new Node(values.poll(),id++);
		Node two = new Node(values.poll(),id++);
		Node three = new Node(values.poll(),id++);
		Node four = new Node(values.poll(),id++);
		Node five = new Node(values.poll(),id++);
		Node six = new Node(values.poll(),id++);
		Node seven = new Node(values.poll(),id++);
		Node eight = new Node(values.poll(),id++);
		Node nine = new Node(values.poll(),id++);
		Node ten = new Node(values.poll(),id++);
		Node eleven = new Node(values.poll(),id++);
		Node twelve = new Node(values.poll(),id++);
		Node thirteen = new Node(values.poll(),id++);
		
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
		root.addChild(one);
		root.addChild(two);
		root.addChild(three);
	}

	@Override
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new Tree1355(goalValue,values);
	}
	
}
