package dac28.model;

import java.util.Queue;

/**
 * The Tree1834 class. Extends the tree class.
 * Has the following structure:
 * 			     	0
 * 		1	2	3	4	5	6	7 	8
 * 			9		10		11
 * 		12		13		14		15
 * 
 * @author Dan Cornwell
 *
 */
class Tree1834 extends Tree {

	/**
	 * The Tree1834 constructor.
	 * Calls the superclass constructor.
	 * 
	 * @param values - the values to set the nodes to, can be empty to use default values
	 */
	Tree1834(Queue<String> values) {
		super(values);
	}
	
	@Override
	protected void constructNodes(Queue<String> values) {
		
		int defaultValue = 1;
		while(values.size() < 15) {
			values.add(String.valueOf(defaultValue++));
		}
		
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
		Node fourteen = new Node(values.poll(),id++);
		Node fifthteen = new Node(values.poll(),id++);
		
		root.addChild(one);
		root.addChild(two);
		root.addChild(three);
		root.addChild(four);
		root.addChild(five);
		root.addChild(six);
		root.addChild(seven);
		root.addChild(eight);
		
		one.addChild(nine);
		five.addChild(ten);
		six.addChild(eleven);
		
		nine.addChild(twelve);
		nine.addChild(thirteen);
		eleven.addChild(fourteen);
		eleven.addChild(fifthteen);
	}

	
	@Override
	protected Tree getTree(Queue<String> values) {
		return new Tree1834(values);
	}



}
