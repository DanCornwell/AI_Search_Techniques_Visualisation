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
	protected void constructNodes(Queue<String> values) {
		
		int defaultValue = 1;
		while(values.size() < 24) {
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
		
		Node sixteen = new Node(values.poll(),id++);
		Node seventeen = new Node(values.poll(),id++);
		
		Node eighteen = new Node(values.poll(),id++);
		Node nineteen = new Node(values.poll(),id++);
		Node twenty = new Node(values.poll(),id++);
		
		Node twentyOne = new Node(values.poll(),id++);
		Node twentyTwo = new Node(values.poll(),id++);
		Node twentyThree = new Node(values.poll(),id++);
		Node twentyFour = new Node(values.poll(),id++);
		
		root.addChild(one);
		root.addChild(two);
		root.addChild(three);
		root.addChild(four);
		
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
