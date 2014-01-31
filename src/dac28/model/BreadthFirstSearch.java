package dac28.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;


class BreadthFirstSearch extends SearchAlgorithm {
	
	private Queue<Node> expanded;
	
	BreadthFirstSearch(Tree TREE) {
		
		super(TREE);
		expanded = new LinkedList<Node>();
		expanded.add(ROOT);
		
	}

	@Override
	Collection<Node> getExpanded() {
		return expanded;
	}

	@Override
	void step() {
		
		Node node = expanded.remove();
		setCurrentNode(node);
		if(atGoal()) {
			setGoalReached(true);
			notifyObservers();
			return;
		}
		else {
			visited.add(node);
			if(node.hasChild()) {
				for(int i=0;i<node.getChildren().size();i++) {
					expanded.add(node.getChildren().get(i));
				}
			}
		}
		notifyObservers();
	}

}
