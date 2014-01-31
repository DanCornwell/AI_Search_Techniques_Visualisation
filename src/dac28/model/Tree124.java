package dac28.model;

public class Tree124 extends Tree {
	
	Tree124(int GOAL) {
		
		super(GOAL);
		
	}

	@Override
	void construct() {
		
		// Create nodes
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		
		// Create child links
		one.addChild(three);
		one.addChild(four);
		two.addChild(five);
		two.addChild(six);
		
		// Create child links for root node
		ROOT.addChild(one);
		ROOT.addChild(two);
		
	}

}
