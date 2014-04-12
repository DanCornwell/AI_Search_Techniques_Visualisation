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
		
		Node sixteen = new Node("16",id++);
		Node seventeen = new Node("17",id++);
		
		Node eighteen = new Node("18",id++);
		Node nineteen = new Node("19",id++);
		Node twenty = new Node("20",id++);
		
		Node twentyOne = new Node("21",id++);
		Node twentyTwo = new Node("22",id++);
		Node twentyThree = new Node("23",id++);
		Node twentyFour = new Node("24",id++);
		
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
	protected void construct(Queue<String> values) {
		
		while(values.size() < 24) {
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
