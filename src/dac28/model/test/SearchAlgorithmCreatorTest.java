package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.model.SearchAlgorithm;
import dac28.model.SearchAlgorithmCreator;
import dac28.model.Tree;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Tree.class,SearchAlgorithm.class})
@PowerMockIgnore("org.apache.log4j.*")
public class SearchAlgorithmCreatorTest {

	SearchAlgorithmCreator creator;
	
	@Before
	public void setup() {
		creator = SearchAlgorithmCreator.getInstance();
	}
	
	@Test
	public void testSingletonInstance() {
		PowerMockito.verifyStatic();
		assertTrue(creator==SearchAlgorithmCreator.getInstance());
	}

	@Test
	public void testGetAlgorithmWithValidInput() {

		final int ID = 5;
		
		@SuppressWarnings("unchecked")
		HashMap<Integer,SearchAlgorithm> hash = mock(HashMap.class);
		when(hash.containsKey(ID)).thenReturn(true);
		when(hash.get(ID)).thenReturn(mock(SearchAlgorithm.class));
		Whitebox.setInternalState(creator, "algorithms", hash);

		creator.getAlgorithm(ID, mock(Tree.class),"");
		verify(hash).containsKey(ID);
		verify(hash).get(ID);
	}
	
	@Test
	public void testGetTreeWithInvalidInPut() {
		
		final int ID = 5;

		@SuppressWarnings("unchecked")
		HashMap<Integer,SearchAlgorithm> hash = mock(HashMap.class);
		when(hash.containsKey(ID)).thenReturn(false);
		when(hash.get(ID)).thenReturn(mock(SearchAlgorithm.class));
		Whitebox.setInternalState(creator, "algorithms", hash);
		
		assertTrue(creator.getAlgorithm(ID, mock(Tree.class),"") == null);
		
		// verify contains key is called but get is not
		verify(hash).containsKey(ID);
		verify(hash,never()).get(any(int.class));

	}
	
}
