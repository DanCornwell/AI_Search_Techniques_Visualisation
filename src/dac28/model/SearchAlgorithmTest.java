package dac28.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SearchAlgorithmTest {

	private Tree tree;
	
	@Before
	public void setup() {
		tree = new Tree124(4);
	}
	
	@Test
	public void testConstructorAssignsGoal() {
		SearchAlgorithm bfs = new BreadthFirstSearch(tree);
	}

}
