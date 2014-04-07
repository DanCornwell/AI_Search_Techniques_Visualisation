package dac28.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class TreeRandom extends Tree {

	TreeRandom(String GOAL, Queue<String> values) {
		super(GOAL, values);
	}

	@Override
	protected void construct() {
		
		Random random = new Random();
		final int HIGH = 5;
		final int LOW = 3;
		int defaultValue = 1;

		final int MAX_DEPTH = random.nextInt(HIGH+1-LOW) + LOW;
		final int LEVEL_1_MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;
		
		for(int i=0;i<LEVEL_1_MAX_WIDTH;i++) {
			ROOT.addChild(new Node(String.valueOf(defaultValue++)));
		}
		List<Node> parentList = ROOT.getChildren();
		
		for(int j=0;j<MAX_DEPTH;j++) {
			
			final int MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;
			
			for(int k=0;k<MAX_WIDTH;k++) {
				int index = random.nextInt(parentList.size()-1);
				parentList.get(index).addChild(new Node(String.valueOf(defaultValue++)));
			}
			
			List<Node> temp = new LinkedList<Node>();
			for(Node node: parentList) {
				temp.addAll(node.getChildren());
			}
			parentList = temp;
		}
		
	}

	@Override
	protected void construct(Queue<String> values) {
		
		Random random = new Random();
		final int HIGH = 6;
		final int LOW = 4;

		final int MAX_DEPTH = random.nextInt(HIGH+1-LOW) + LOW;
		final int LEVEL_1_MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;
		
		for(int i=0;i<LEVEL_1_MAX_WIDTH;i++) {
			String value = "Node";
			if(values.peek()!=null) value = values.poll();
			ROOT.addChild(new Node(String.valueOf(value)));
		}
		List<Node> parentList = ROOT.getChildren();
		
		for(int j=0;j<MAX_DEPTH;j++) {
			
			final int MAX_WIDTH = random.nextInt(HIGH+1-LOW) + LOW;
			
			for(int k=0;k<MAX_WIDTH;k++) {
				int index = random.nextInt(parentList.size()-1);
				String value = "Node";
				if(values.peek()!=null) value = values.poll();
				parentList.get(index).addChild(new Node(String.valueOf(value)));
			}
			
			List<Node> temp = new LinkedList<Node>();
			for(Node node: parentList) {
				temp.addAll(node.getChildren());
			}
			parentList = temp;
		}
	}

	@Override
	protected Tree getTree(String goalValue, Queue<String> values) {
		return new TreeRandom(goalValue,values);
	}

}
