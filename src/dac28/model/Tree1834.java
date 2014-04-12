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

	Tree1834(String GOAL, Queue<String> values) {
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
		Node seven = new Node("7",id++);
		Node eight = new Node("8",id++);
		
		Node nine = new Node("9",id++);
		Node ten = new Node("10",id++);
		Node eleven = new Node("11",id++);

		Node twelve = new Node("12",id++);
		Node thirteen = new Node("13",id++);
		Node fourteen = new Node("14",id++);
		Node fifthteen = new Node("15",id++);
		
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
	protected void construct(Queue<String> values) {
		
		while(values.size() < 15) {
			values.add("Node");
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
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new Tree1834(goalValue,values);
	}



}
