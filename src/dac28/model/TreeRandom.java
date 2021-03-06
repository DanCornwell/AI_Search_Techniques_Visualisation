package dac28.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * The random tree class.
 * Creates a random tree, with a depth between 3 and 5 and a level width between 3 and 5.
 * 
 * @author Dan Cornwell
 *
 */
class TreeRandom extends Tree {

	/** 
	 * The TreeRandom constructor.
	 * Calls the superclass constructor.
	 * 
	 * @param values - the values to set the nodes to, can be empty to use default values
	 */
	TreeRandom(Queue<String> values) {
		super(values);
	}

	@Override
	protected void constructNodes(Queue<String> values) {

		int defaultValue = 1;
		
		Random random = new Random();
		final int HIGH = 5;
		final int LOW = 2;

		final int MAX_DEPTH = random.nextInt(HIGH+1-LOW) + LOW;
		final int LEVEL_1_MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;

		for(int i=0;i<LEVEL_1_MAX_WIDTH;i++) {
			if(values.isEmpty()) values.add(String.valueOf(defaultValue++));
			Node child = new Node(values.poll(),id++);
			root.addChild(child);
		}
		List<Node> parentList = root.getChildren();

		for(int j=0;j<MAX_DEPTH;j++) {

			final int MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;

			for(int k=0;k<MAX_WIDTH;k++) {
				int index = random.nextInt(parentList.size()-1);
				if(values.isEmpty()) values.add(String.valueOf(defaultValue++));
				Node child = new Node(values.poll(),id++);
				parentList.get(index).addChild(child);
			}

			List<Node> temp = new LinkedList<Node>();
			for(Node node: parentList) {
				temp.addAll(node.getChildren());
			}
			parentList = temp;
		}
	}

	@Override
	protected Tree getTree(Queue<String> values) {
		setNodeValues(values);
		return this;
	}

	/**
	 * Recreates the tree using the given values as the new node values.
	 * 
	 * @param values - queue of the strings representing the node values
	 */
	private void setNodeValues(Queue<String> values) {
		
		String rootValue = "Root";
		if(!values.isEmpty()) rootValue = values.poll();
		
		Node newRoot = new Node(rootValue,root.getUID());
		
		Queue<Node> nodes = new LinkedList<Node>();
		Queue<Node> newNodes = new LinkedList<Node>();
		
		for(Node rootChild: root.getChildren()) {
			nodes.add(rootChild);
			String value = "Node";
			if(!values.isEmpty()) value = values.poll();
			
			Node newRootChild = new Node(value,rootChild.getUID());
			newNodes.add(newRootChild);
			newRoot.addChild(newRootChild);
			
		}
		
		while(!nodes.isEmpty()) {
			
			Node node = nodes.poll();
			Node newNode = newNodes.poll();
			for(Node child: node.getChildren()) {
				nodes.add(child);
				String value = "Node";
				if(!values.isEmpty()) value = values.poll();
				Node newChild = new Node(value,child.getUID());
				newNode.addChild(newChild);
				newNodes.add(newChild);
			}
			
		}
		
		root = newRoot;
		
		
	} 
}
