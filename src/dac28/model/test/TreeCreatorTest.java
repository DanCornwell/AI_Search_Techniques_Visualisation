package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	public void testGetTreeWithValidInput() {

		final int ID = 5;
		
		@SuppressWarnings("unchecked")
		HashMap<Integer,Tree> hash = mock(HashMap.class);
		when(hash.containsKey(ID)).thenReturn(true);
		when(hash.get(ID)).thenReturn(mock(Tree.class));
		Whitebox.setInternalState(creator, "trees", hash);

		creator.getTree(ID, "0",new LinkedList<String>());
		verify(hash).containsKey(ID);
		verify(hash).get(ID);
	}
	
	@Test
	public void testGetTreeWithInvalidInput() {
		
		int ID = 5;
		@SuppressWarnings("unchecked")
		HashMap<Integer,Tree> hash = mock(HashMap.class);
		when(hash.containsKey(ID)).thenReturn(false);
		Whitebox.setInternalState(creator, "trees", hash);

		assertTrue(creator.getTree(ID, "0",new LinkedList<String>()) == null);
		
		// verify contains key is called but get is not
		verify(hash).containsKey(ID);
		verify(hash,never()).get(any(int.class));
	}
	
}
