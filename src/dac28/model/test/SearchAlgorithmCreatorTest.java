package dac28.model.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
	public void testGetAlgorithmCallHashMethods() {

		final int ID = 5;
		
		@SuppressWarnings("unchecked")
		HashMap<Integer,SearchAlgorithm> hash = Mockito.mock(HashMap.class);
		Mockito.when(hash.containsKey(ID)).thenReturn(true);
		Mockito.when(hash.get(ID)).thenReturn(Mockito.mock(SearchAlgorithm.class));
		Whitebox.setInternalState(creator, "algorithms", hash);

		creator.getAlgorithm(ID, Mockito.mock(Tree.class));
		Mockito.verify(hash).containsKey(ID);
		Mockito.verify(hash).get(ID);
	}
	
	@Test
	public void testGetTreeReturnsNullWithInvalidID() {
		
		int id = 5;
		HashMap<Integer,SearchAlgorithm> hash = Whitebox.getInternalState(creator, "algorithms");
		while(hash.containsKey(id)) {
			id++;
		}
		
		assertTrue(creator.getAlgorithm(id, Mockito.mock(Tree.class)) == null);
		
	}
	
}
