package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dac28.controller.TextFileReader;
import dac28.model.Tree;
import dac28.model.TreeCreator;

public class ConcreteTreeTest {
	
	private final int GOAL = 4;
	
	@Test
	public void testTree124() {
		
		int id = TextFileReader.getTrees().indexOf("Tree124");
		assertTrue(id!=-1);
		Tree tree = TreeCreator.getInstance().getTree(id, GOAL);
		
		assertTrue("Max Width was incorrect",4==tree.getTreeWidth());
		assertTrue("Max Depth was incorrect",3==tree.getTreeDepth());
		assertTrue("Goal value was not set",GOAL==tree.getGoal());
		
	}
	
	@Test
	public void testTree1355() {
		
		int id = TextFileReader.getTrees().indexOf("Tree1355");
		assertTrue(id!=-1);
		Tree tree = TreeCreator.getInstance().getTree(id, GOAL);
		
		assertTrue("Max Width was incorrect",5==tree.getTreeWidth());
		assertTrue("Max Depth was incorrect",4==tree.getTreeDepth());
		assertTrue("Goal value was not set",GOAL==tree.getGoal());
		
	}
	
	@Test
	public void testTree112111() {
		
		int id = TextFileReader.getTrees().indexOf("Tree112111");
		assertTrue(id!=-1);
		Tree tree124 = TreeCreator.getInstance().getTree(id, GOAL);
		
		assertTrue("Max Width was incorrect",2==tree124.getTreeWidth());
		assertTrue("Max Depth was incorrect",6==tree124.getTreeDepth());
		assertTrue("Goal value was not set",GOAL==tree124.getGoal());
		
	}

}
