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
		Node fourteen = new Node("14");
		Node fifthteen = new Node("15");
		
		ROOT.addChild(one);
		ROOT.addChild(two);
		ROOT.addChild(three);
		ROOT.addChild(four);
		ROOT.addChild(five);
		ROOT.addChild(six);
		ROOT.addChild(seven);
		ROOT.addChild(eight);
		
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
		Node fourteen = new Node(values.poll());
		Node fifthteen = new Node(values.poll());
		
		ROOT.addChild(one);
		ROOT.addChild(two);
		ROOT.addChild(three);
		ROOT.addChild(four);
		ROOT.addChild(five);
		ROOT.addChild(six);
		ROOT.addChild(seven);
		ROOT.addChild(eight);
		
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
