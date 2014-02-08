package dac28.model.search_algorithm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dac28.model.node.Node;
import dac28.model.search_algorithm.concrete_algorithm.BreadthFirstSearchCreator;
import dac28.model.search_algorithm.concrete_algorithm.DepthFirstSearchCreator;
import dac28.model.tree.Tree;
import dac28.model.tree.Tree124Creator;
import dac28.model.tree.TreeCreator;

/**
 * Tests the implemented search algorithm methods.
 * 
 * @author Dan Cornwell
 *
 */
public class SearchAlgorithmTest {

	private SearchAlgorithm bfs,dfs;

	@Before
	public void setup() {
		TreeCreator creator = new Tree124Creator();
		Tree tree = creator.getTree(4);
		SearchAlgorithmCreator bfsCreator = new BreadthFirstSearchCreator();
		bfs = bfsCreator.getSearchAlgorithm(tree);
		SearchAlgorithmCreator dfsCreator = new DepthFirstSearchCreator();
		dfs = dfsCreator.getSearchAlgorithm(tree);
		
	}

	@Test
	public void testConstructorAssignsRoot() {
		//BFS test
		assertEquals("Root node value did not match expected node value",0,bfs.ROOT.getValue());
		//DFS test
		assertEquals("Root node value did not match expected node value",0,dfs.ROOT.getValue());
	}

	@Test
	public void testNodeIsGoal() {
		//BFS test
		bfs.currentNode = bfs.ROOT;
		assertFalse("Incorret node was the goal node",bfs.atGoal());
		bfs.currentNode = new Node(4);
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		//DFS test
		dfs.currentNode = dfs.ROOT;
		assertFalse("Incorret node was the goal node",dfs.atGoal());
		dfs.currentNode = new Node(4);
		assertTrue("Correct node was not the goal node",dfs.atGoal());
	}

	@Test
	public void testAlgorithmsStopWhenNoNodesLeft() {
		TreeCreator creator = new Tree124Creator();
		Tree bad_tree = creator.getTree(10);
		//BFS test
		SearchAlgorithmCreator bfsCreator = new BreadthFirstSearchCreator();
		SearchAlgorithm other_bfs = bfsCreator.getSearchAlgorithm(bad_tree);
		other_bfs.auto();
		assertFalse("Current node was goal node",other_bfs.atGoal());
		//DFS test
		SearchAlgorithmCreator dfsCreator = new DepthFirstSearchCreator();
		SearchAlgorithm other_dfs = dfsCreator.getSearchAlgorithm(bad_tree);
		other_dfs.auto();
		assertFalse("Current node was goal node",other_dfs.atGoal());
	}

	@Test
	public void testStep() {
		//BFS test
		bfs.step();
		assertEquals("Node value did not match expected node value",0,bfs.currentNode.getValue());
		bfs.step();
		assertEquals("Node value did not match expected node value",1,bfs.currentNode.getValue());
		//DFS test
		dfs.step();
		assertEquals("Node value did not match expected node value",0,dfs.currentNode.getValue());
		dfs.step();
		assertEquals("Node value did not match expected node value",1,dfs.currentNode.getValue());
	}

	@Test
	public void testAutoAndStepReachesGoal() {
		// As auto repeatedly calls step(), passing this test verifies both methods reach the goal

		//BFS test
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		//DFS test
		dfs.auto();
		assertEquals("Node value did not match expected node value",4,dfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",dfs.atGoal());
	}

	@Test
	public void testStepDoesNotGoPassGoal() {
		//BFS test
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		bfs.step();
		assertEquals("Step moved from the goal node",4,bfs.currentNode.getValue());
		assertTrue("No longer at the goal node",bfs.atGoal());
		//DFS test
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
	
	@Test
	public void testUndoAndMemento() {
		//BFS test
		bfs.step();
		bfs.step();
		bfs.undo();
		assertTrue("Not back at goal node",bfs.currentNode.getValue() == 0);
		bfs.undo();
		assertTrue("Undo at root node doesn't do anything",bfs.currentNode.getValue() == 0);
		bfs.auto();
		assertTrue("Goal node was not reached",bfs.atGoal());
		bfs.undo();
		assertFalse("Did not undo",bfs.atGoal());
		bfs.step();
		assertTrue("Step did not go to goal node",bfs.atGoal());
		for(int i=0;i<10;i++) {
			bfs.undo();
		}
		assertTrue("undo did not go back to root node",bfs.currentNode.getValue()==0);
		//DFS test
		dfs.step();
		dfs.step();
		dfs.undo();
		assertTrue("Not back at goal node",dfs.currentNode.getValue() == 0);
		dfs.undo();
		assertTrue("Undo at root node doesn't do anything",dfs.currentNode.getValue() == 0);
		dfs.auto();
		assertTrue("Goal node was not reached",dfs.atGoal());
		dfs.undo();
		assertFalse("Did not undo",dfs.atGoal());
		dfs.step();
		assertTrue("Step did not go to goal node",dfs.atGoal());
		for(int i=0;i<10;i++) {
			dfs.undo();
		}
		assertTrue("undo did not go back to root node",dfs.currentNode.getValue()==0);
	}
	
}
