package dac28.model;

import java.util.Queue;

/**
 * The Tree1834 class. Extends the tree class.
 * Has the following structure:
 * 			     	0
 * 		1		2		3		4
 * 			5		6		7
 * 				8		9
 * 		10	11	12	13	14	15	
 * 				16		17
 * 			18		19		20
 * 		21		22		23		24
 * 
 * @author Dan Cornwell
 *
 */
public class Tree14326234 extends Tree {

	Tree14326234(String GOAL, Queue<String> values) {
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
		
		Node sixteen = new Node("16");
		Node seventeen = new Node("17");
		
		Node eighteen = new Node("18");
		Node nineteen = new Node("19");
		Node twenty = new Node("20");
		
		Node twentyOne = new Node("21");
		Node twentyTwo = new Node("22");
		Node twentyThree = new Node("23");
		Node twentyFour = new Node("24");
		
		ROOT.addChild(one);
		ROOT.addChild(two);
		ROOT.addChild(three);
		ROOT.addChild(four);
		
		one.addChild(five);
		two.addChild(six);
		three.addChild(seven);
		
		six.addChild(eight);
		six.addChild(nine);
		
		eight.addChild(ten);
		eight.addChild(eleven);	
		eight.addChild(twelve);
		eight.addChild(thirteen);
		nine.addChild(fourteen);
		nine.addChild(fifthteen);
		
		twelve.addChild(sixteen);
		thirteen.addChild(seventeen);
		
		sixteen.addChild(eighteen);
		seventeen.addChild(nineteen);
		seventeen.addChild(twenty);
		
		eighteen.addChild(twentyOne);
		eighteen.addChild(twentyTwo);
		nineteen.addChild(twentyThree);
		twenty.addChild(twentyFour);

	}

	@Override
	protected void construct(Queue<String> values) {
		
		while(values.size() < 24) {
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
		
		Node sixteen = new Node(values.poll());
		Node seventeen = new Node(values.poll());
		
		Node eighteen = new Node(values.poll());
		Node nineteen = new Node(values.poll());
		Node twenty = new Node(values.poll());
		
		Node twentyOne = new Node(values.poll());
		Node twentyTwo = new Node(values.poll());
		Node twentyThree = new Node(values.poll());
		Node twentyFour = new Node(values.poll());
		
		ROOT.addChild(one);
		ROOT.addChild(two);
		ROOT.addChild(three);
		ROOT.addChild(four);
		
		one.addChild(five);
		two.addChild(six);
		three.addChild(seven);
		
		six.addChild(eight);
		six.addChild(nine);
		
		eight.addChild(ten);
		eight.addChild(eleven);	
		eight.addChild(twelve);
		eight.addChild(thirteen);
		nine.addChild(fourteen);
		nine.addChild(fifthteen);
		
		twelve.addChild(sixteen);
		thirteen.addChild(seventeen);
		
		sixteen.addChild(eighteen);
		seventeen.addChild(nineteen);
		seventeen.addChild(twenty);
		
		eighteen.addChild(twentyOne);
		eighteen.addChild(twentyTwo);
		nineteen.addChild(twentyThree);
		twenty.addChild(twentyFour);
	}

	@Override
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new Tree14326234(goalValue,values);
	}

}
