package dac28.model;

import dac28.controller.TextFileReader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The search algorithm creator class. 
 * Singleton class, holds a list of search algorithms and their unique ID's in a HashMap.
 * Registers all known search algorithms upon initialisation. 
 * 
 * @author Dan Cornwell
 *
 */
public class SearchAlgorithmCreator {

	/**
	 * The static instance of the search algorithm creator.
	 */
	protected static SearchAlgorithmCreator instance = null;
	/**
	 * The HashMap containing all known search algorithms. 
	 */
	private HashMap<Integer,SearchAlgorithm> algorithms = new HashMap<Integer,SearchAlgorithm>();

	/**
	 * Constructor for the search algorithm creator. 
	 * Creates instances of all the search algorithms defined in algorithm.txt and adds them to the hash map.
	 * To add a new algorithm, add its class name to algorithm.txt.
	 */
	protected SearchAlgorithmCreator() {

		List<String> algorithmNames = null;

		algorithmNames = TextFileReader.getAlgorithms();

		if(algorithmNames == null) return;

		int id = 0;
		Tree tree = new Tree124("0",new LinkedList<String>());

		for(String algorithmName: algorithmNames) {
			try {

				Class<?> algorithm = Class.forName("dac28.model.".concat(algorithmName));
				SearchAlgorithm algorithmInstance = (SearchAlgorithm) algorithm.getDeclaredConstructor(Tree.class).newInstance(tree);
				algorithms.put(id, algorithmInstance);
				id++;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
					| IllegalArgumentException | InvocationTargetException 
					| NoSuchMethodException | SecurityException e) {
			}
		}
	}

	/**
	 * Returns the search algorithm creator, initialising it if it doesn't exist
	 * 
	 * @return the search algorithm creator
	 */
	public static SearchAlgorithmCreator getInstance() {
		if(instance==null) instance = new SearchAlgorithmCreator();
		return instance;
	}

	public void putAlgorithm(int uID,SearchAlgorithm algorithm) {
		algorithms.put(uID, algorithm);
	}
	
	/**
	 * Retrieves a search algorithm from the hash.
	 * 
	 * @param uID - id of the search algorithm to get
	 * @param tree - the tree the search algorithm will search on
	 * @return a concrete search algorithm with a defined tree
	 */
	public SearchAlgorithm getAlgorithm(int uID,Tree tree) {

		if(!algorithms.containsKey(uID)) return null;

		return algorithms.get(uID).getAlgorithm(tree);
	}

}


