package dac28.model;

import static org.junit.Assert.*;

import java.util.LinkedList;

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
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		assertEquals("Root node value did not match expected node value",0,bfs.ROOT.getValue());
	}

	@Test
	public void testNodeIsGoal() {
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.currentNode = bfs.ROOT;
		assertFalse("Incorret node was the goal node",bfs.atGoal());
		bfs.currentNode = new Node(4);
		assertTrue("Correct node was not the goal node",bfs.atGoal());
	}
	
	@Test
	public void testSetAndGetForGoalReached() {
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		assertFalse("boolean was incorrect upon initialisation",bfs.getGoalReached());
		bfs.setGoalReached(true);
		assertTrue("boolean did not change on setter",bfs.getGoalReached());
	}
	
	@Test
	public void testStep() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.step();
		assertEquals("Node value did not match expected node value",0,bfs.currentNode.getValue());
		bfs.step();
		assertEquals("Node value did not match expected node value",1,bfs.currentNode.getValue());
	}
	
	@Test
	public void testAutoReachesGoal() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.currentNode = bfs.ROOT;
		assertEquals("Node value did not match expected node value",0,bfs.currentNode.getValue());
		assertFalse("Incorret node was the goal node",bfs.atGoal());
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
	}
	
	@Test
	public void testStepDoesNotGoPassedGoal() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.currentNode.getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		bfs.step();
		assertEquals("Step moved from the goal node",4,bfs.currentNode.getValue());
		assertTrue("No longer at the goal node",bfs.atGoal());
	}
	
	@Test
	public void testFinalExpandedAndVisitedLists() {
		//BFS test
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
		bfs.auto();
		assertEquals("Goal node not reached",4,bfs.currentNode.getValue());
		Node[] expVisited = {new Node(0),new Node(1),new Node(2),new Node(3)};
		Node[] expExpanded = {new Node(5),new Node(6)};
		assertEquals("Visited list was not the expected size",expVisited.length,bfs.visited.size());
		assertEquals("Expected list was not the expected size",expExpanded.length,bfs.getExpanded().size());
		//safety check
		if(expVisited.length == bfs.visited.size()) {
			for(int i=0;i<expVisited.length;i++) {
				assertEquals("List element did not match",expVisited[i].getValue(),bfs.visited.get(i).getValue());
			}
		}
		if(expExpanded.length == bfs.getExpanded().size()) {
			for(int i=0;i<expExpanded.length;i++) {
				assertEquals("List element did not match",expExpanded[i].getValue(),((LinkedList<Node>) bfs.getExpanded()).get(i).getValue());
			}
		}
	}
}
