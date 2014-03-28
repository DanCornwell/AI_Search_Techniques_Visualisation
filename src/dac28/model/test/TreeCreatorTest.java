package dac28.model.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.model.Tree;
import dac28.model.TreeCreator;



/**
 * Tests the tree creator methods.
 * 
 * @author Dan Cornwell
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Tree.class})
@PowerMockIgnore("org.apache.log4j.*")
public class TreeCreatorTest {

	TreeCreator creator;
	
	@Before
	public void setup() {
		creator = TreeCreator.getInstance();
	}
	
	@Test
	public void testSingletonInstance() {
		PowerMockito.verifyStatic();
		assertTrue(creator==TreeCreator.getInstance());
	}

	@Test
	public void testGetTreeCallHashMethods() {

		final int ID = 5;
		
		@SuppressWarnings("unchecked")
		HashMap<Integer,Tree> hash = Mockito.mock(HashMap.class);
		Mockito.when(hash.containsKey(ID)).thenReturn(true);
		Mockito.when(hash.get(ID)).thenReturn(Mockito.mock(Tree.class));
		Whitebox.setInternalState(creator, "trees", hash);

		creator.getTree(ID, "0",new LinkedList<String>());
		Mockito.verify(hash).containsKey(ID);
		Mockito.verify(hash).get(ID);
	}
	
	@Test
	public void testGetTreeReturnsNullWithInvalidID() {
		
		int id = 5;
		HashMap<Integer,Tree> hash = Whitebox.getInternalState(creator, "trees");
		while(hash.containsKey(id)) {
			id++;
		}
		
		assertTrue(creator.getTree(id, "0",new LinkedList<String>()) == null);
		
	}
	
}
