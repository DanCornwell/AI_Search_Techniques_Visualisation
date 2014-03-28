package dac28.model;

import java.util.Stack;

public class IterativeDeepening extends SearchAlgorithm {

	private final int DEPTH_LIMIT = 1;

	protected IterativeDeepening(Tree TREE) {
		super(TREE);
		// Depth first search uses a stack data structure
		expanded = new Stack<Node>();
		// Add root to expanded list to allow algorithm to start
		((Stack<Node>) expanded).push(ROOT);
	}

	@Override
	protected void algorithmLogic() {
		currentNode = ((Stack<Node>) expanded).pop();
		if(atGoal()) {
			// Goal reached so stop
			return;
		}
		else {
			visited.add(currentNode);
			if(currentNode.hasChild()) {
				for(int i=currentNode.getChildren().size()-1;i>-1;i--) {
					((Stack<Node>) expanded).push(currentNode.getChildren().get(i));
				}
			}
		}
	}

	@Override
	SearchAlgorithm getAlgorithm(Tree tree) {
		return new IterativeDeepening(tree);
	}

}
