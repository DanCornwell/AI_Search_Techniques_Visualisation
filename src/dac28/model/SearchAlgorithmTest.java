package dac28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the search algorithm methods.
 * 
 * @author Dan Cornwell
 *
 */
public class SearchAlgorithmTest {

	private Tree tree;

	@Before
	public void setup() {
		TreeCreator creator = new Tree124Creator();
		tree = creator.getTree(4);
	}

	@Test
	public void testConstructorAssignsRoot() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		assertEquals("Root node value did not match expected node value",0,bfs.ROOT.getValue());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		assertEquals("Root node value did not match expected node value",0,dfs.ROOT.getValue());
	}

	@Test
	public void testNodeIsGoal() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.currentNode = bfs.ROOT;
		assertFalse("Incorret node was the goal node",bfs.atGoal());
		bfs.currentNode = new Node(4);
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.currentNode = dfs.ROOT;
		assertFalse("Incorret node was the goal node",dfs.atGoal());
		dfs.currentNode = new Node(4);
		assertTrue("Correct node was not the goal node",dfs.atGoal());
	}

	@Test
	public void testSetAndGetForGoalReached() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		assertFalse("boolean was incorrect upon initialisation",bfs.getGoalReached());
		bfs.setGoalReached(true);
		assertTrue("boolean did not change on setter",bfs.getGoalReached());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		assertFalse("boolean was incorrect upon initialisation",dfs.getGoalReached());
		dfs.setGoalReached(true);
		assertTrue("boolean did not change on setter",dfs.getGoalReached());
	}

	@Test
	public void testAlgorithmsStopWhenNoNodesLeft() {
		TreeCreator creator = new Tree124Creator();
		tree = creator.getTree(10);
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertFalse("Current node was goal node",bfs.atGoal());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.auto();
		assertFalse("Current node was goal node",dfs.atGoal());
	}

	@Test
	public void testStep() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.step();
		assertEquals("Node value did not match expected node value",0,bfs.currentNode.getValue());
		bfs.step();
		assertEquals("Node value did not match expected node value",1,bfs.currentNode.getValue());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.step();
		assertEquals("Node value did not match expected node value",0,dfs.currentNode.getValue());
		dfs.step();
		assertEquals("Node value did not match expected node value",1,dfs.currentNode.getValue());
	}

	@Test
	public void testAutoAndStepReachesGoal() {
		// As auto repeatedly calls step(), passing this test verifies both methods reach the goal

		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.auto();
		assertEquals("Node value did not match expected node value",4,dfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",dfs.atGoal());
	}

	@Test
	public void testStepDoesNotGoPassGoal() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		bfs.step();
		assertEquals("Step moved from the goal node",4,bfs.currentNode.getValue());
		assertTrue("No longer at the goal node",bfs.atGoal());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.auto();
		assertEquals("Node value did not match expected node value",4,dfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",dfs.atGoal());
		dfs.step();
		assertEquals("Step moved from the goal node",4,dfs.currentNode.getValue());
		assertTrue("No longer at the goal node",dfs.atGoal());
	}

	@Test
	public void testFinalExpandedAndVisitedLists() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertEquals("Goal node not reached",4,bfs.currentNode.getValue());
		Node[] bfsExpVisited = {new Node(0),new Node(1),new Node(2),new Node(3)};
		Node[] bfsExpExpanded = {new Node(5),new Node(6)};
		assertEquals("Visited list was not the expected size",bfsExpVisited.length,bfs.visited.size());
		assertEquals("Expected list was not the expected size",bfsExpExpanded.length,bfs.getExpanded().size());
		//safety check
		if(bfsExpVisited.length == bfs.visited.size()) {
			for(int i=0;i<bfsExpVisited.length;i++) {
				assertEquals("List element did not match",bfsExpVisited[i].getValue(),bfs.visited.get(i).getValue());
			}
		}
		if(bfsExpExpanded.length == bfs.getExpanded().size()) {
			for(int i=0;i<bfsExpExpanded.length;i++) {
				assertEquals("List element did not match",bfsExpExpanded[i].getValue(),bfs.getExpanded().get(i).getValue());
			}
		}
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.auto();
		assertEquals("Goal node not reached",4,dfs.currentNode.getValue());
		Node[] dfsExpVisited = {new Node(0),new Node(1),new Node(3)};
		Node[] dfsExpExpanded = {new Node(2)};
		assertEquals("Visited list was not the expected size",dfsExpVisited.length,dfs.visited.size());
		assertEquals("Expected list was not the expected size",dfsExpExpanded.length,dfs.getExpanded().size());
		//safety check
		if(dfsExpVisited.length == dfs.visited.size()) {
			for(int i=0;i<dfsExpVisited.length;i++) {
				assertEquals("List element did not match",dfsExpVisited[i].getValue(),dfs.visited.get(i).getValue());
			}
		}
		if(dfsExpExpanded.length == dfs.getExpanded().size()) {
			for(int i=0;i<dfsExpExpanded.length;i++) {
				assertEquals("List element did not match",dfsExpExpanded[i].getValue(),dfs.getExpanded().get(i).getValue());
			}
		}
	}

	@Test
	public void testReset() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertTrue("Goal node not reached",bfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,bfs.currentNode.getValue());
		assertFalse("Expanded list was empty",bfs.expanded.isEmpty());
		assertFalse("Visited list was empty",bfs.visited.isEmpty());
		bfs.reset();
		assertFalse("Current node did not reset",bfs.atGoal());
		assertEquals("Root node value did not match expected node value",0,bfs.currentNode.getValue());
		assertTrue("Expanded list was not empty",bfs.expanded.isEmpty());
		assertTrue("Visited list was not empty",bfs.visited.isEmpty());
		//DFS test
		SearchAlgorithm dfs = new DepthFirstSearch(tree);
		dfs.auto();
		assertTrue("Goal node not reached",dfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,dfs.currentNode.getValue());
		assertFalse("Expanded list was empty",dfs.expanded.isEmpty());
		assertFalse("Visited list was empty",dfs.visited.isEmpty());
		dfs.reset();
		assertFalse("Current node did not reset",dfs.atGoal());
		assertEquals("Root node value did not match expected node value",0,dfs.currentNode.getValue());
		assertTrue("Expanded list was not empty",dfs.expanded.isEmpty());
		assertTrue("Visited list was not empty",dfs.visited.isEmpty());
	}
}
