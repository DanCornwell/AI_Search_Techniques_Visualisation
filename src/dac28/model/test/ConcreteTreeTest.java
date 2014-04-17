package dac28.model.test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import dac28.model.TextFileReader;
import dac28.model.Tree;
import dac28.model.TreeCreator;

public class ConcreteTreeTest {

	private final String defaultRoot = "ROOT";

	@Test
	public void testTree124() {

		int id = TextFileReader.getTrees().indexOf("Tree124");
		assertTrue(id!=-1);
		Tree tree = TreeCreator.getInstance().getTree(id,new LinkedList<String>());

		assertTrue("Max Width was incorrect",4==tree.getTreeWidth());
		assertTrue("Max Depth was incorrect",3==tree.getTreeDepth());

		// Test default value is used 
		assertTrue(tree.getRoot().getValue().equals(defaultRoot));
		LinkedList<String> list = new LinkedList<String>();
		list.add("test");
		tree = TreeCreator.getInstance().getTree(id,list);
		// Test specified value is set
		assertTrue(tree.getRoot().getValue().equals("test"));	
	}

	@Test
	public void testTree1355() {

		int id = TextFileReader.getTrees().indexOf("Tree1355");
		assertTrue(id!=-1);
		Tree tree = TreeCreator.getInstance().getTree(id,new LinkedList<String>());

		assertTrue("Max Width was incorrect",5==tree.getTreeWidth());
		assertTrue("Max Depth was incorrect",4==tree.getTreeDepth());

		// Test default value is used 
		assertTrue(tree.getRoot().getValue().equals(defaultRoot));
		LinkedList<String> list = new LinkedList<String>();
		list.add("test");
		tree = TreeCreator.getInstance().getTree(id,list);
		// Test specified value is set
		assertTrue(tree.getRoot().getValue().equals("test"));	
	}

	@Test
	public void testTree112111() {

		int id = TextFileReader.getTrees().indexOf("Tree112111");
		assertTrue(id!=-1);
		Tree tree = TreeCreator.getInstance().getTree(id,new LinkedList<String>());

		assertTrue("Max Width was incorrect",2==tree.getTreeWidth());
		assertTrue("Max Depth was incorrect",6==tree.getTreeDepth());

		// Test default value is used 
		assertTrue(tree.getRoot().getValue().equals(defaultRoot));
		LinkedList<String> list = new LinkedList<String>();
		list.add("test");
		tree = TreeCreator.getInstance().getTree(id,list);
		// Test specified value is set
		assertTrue(tree.getRoot().getValue().equals("test"));	
	}

}
