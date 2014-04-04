package dac28.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Uniform cost search class.
 * Takes user input for the path costs and uses that to expand nodes.
 * Shortest costs nodes are expanded first.
 * 
 * @author Dan Cornwell
 *
 */
class UniformCostSearch extends SearchAlgorithm {

	/**
	 * HashMap of node keys with the cost to reach that node as its value.
	 */
	private HashMap<Node,Integer> pathCosts;
	
	/**
	 * Constructor.
	 * Initialises the expanded list and adds the root to it.
	 * Initialises the path costs HashMap and puts root onto it with value 0.
	 * 
	 * @param TREE - the tree to search upon
	 */
	protected UniformCostSearch(Tree TREE) {
	
		super(TREE);
		// LinkedList is a queue type data structure
		expanded = new LinkedList<Node>();
		// Add root to expanded list to allow algorithm to start
		((LinkedList<Node>)expanded).add(ROOT);

		// Creates the HashMap and adds root to it. Has a value of 0 since it starts at the root.
		pathCosts = new HashMap<Node,Integer>();
		pathCosts.put(ROOT, 0);
		
		// Takes the path costs from the tree and uses it to create path costs between nodes.
		setup(TREE.getPathCosts());
	}

	@Override
	protected void algorithmStepLogic() {
		
		currentNode = ((LinkedList<Node>)expanded).remove();
		if(atGoal()) {
			// Goal reached so stop
			return;
		}
		else {
			visited.add(currentNode);
			if(currentNode.hasChild()) {
				for(int i=0;i<currentNode.getChildren().size();i++) {
					expanded.add(currentNode.getChildren().get(i));
				}
			}
		}
		Node nextNodeToExpanded = null;
		int cost = Integer.valueOf(Integer.MAX_VALUE);
		for(Node node: expanded) {
			
			if(pathCosts.get(node) < cost) {
				nextNodeToExpanded = node;
				cost = pathCosts.get(node);
			}
			
		}
		if(nextNodeToExpanded!=null) {
			expanded.remove(nextNodeToExpanded);
			expanded.add(0, nextNodeToExpanded);
		}
		
	}
	
	@Override
	SearchAlgorithm getAlgorithm(Tree tree) {
		return new UniformCostSearch(tree);
	}

	/**
	 * 'Sets up' the nodes in the tree so that they have a path cost between each node.
	 * Takes a path cost queue from the tree to do this.
	 * The values should be read from right to left. i.e for a root with 2 children
	 * the first value is for the left child, 2nd the right child.
	 * 
	 * @param values - a queue of integers that represent the path costs between nodes.
	 */
	private void setup(Queue<Integer> values) {
				
		Queue<Node> nodes = new LinkedList<Node>();

		nodes.add(ROOT);
		
		// Terminates when no nodes left, i.e has expanded all the leaf nodes
		while(!nodes.isEmpty()) {
			
			Node parent = nodes.poll();

			for(Node child: parent.getChildren()) {
				
				nodes.add(child);
				
				if(!values.isEmpty()){
					pathCosts.put(child, values.poll() + pathCosts.get(parent));
				}
				
				// not enough values within the values queue so use default cost of 1
				else pathCosts.put(child, 1 + pathCosts.get(parent));
			}
		
		}
		
	}
	
}
