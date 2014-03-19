package dac28.model;

import java.util.HashMap;

/**
 * The tree creator class. 
 * Singleton class, holds a list of trees and the unique ID's in a HashMap.
 * Registers all known trees upon initialisation. 
 * 
 * @author Dan Cornwell
 *
 */
public class SearchAlgorithmCreator {
	
	/**
	 * The static instance of the tree creator.
	 */
	protected static SearchAlgorithmCreator instance = null;
	/**
	 * The HashMap containing all known trees. 
	 */
	private HashMap<String,SearchAlgorithm> algorithms = new HashMap<String,SearchAlgorithm>();

	/**
	 * Constructor for the tree creator. 
	 * Adds all known trees to the HashMap. 0 is a trivial constructor value for the trees.
	 */
	protected SearchAlgorithmCreator() {
		Tree tree = new Tree124(0);
		addAlgorithm("bfs",new BreadthFirstSearch(tree));
		addAlgorithm("dfs",new DepthFirstSearch(tree));
	}
	
	/**
	 * Returns the tree creator, initialising it if it doesn't exist
	 * 
	 * @return the tree creator
	 */
	public static SearchAlgorithmCreator getInstance() {
		if(instance==null) instance = new SearchAlgorithmCreator();
		return instance;
	}
	
	/**
	 * Adds a tree to the creator hash.
	 * 
	 * @param uID - the key that will be used to receive the tree
	 * @param tree - the tree to store
	 */
	public void addAlgorithm(String uID,SearchAlgorithm algorithm) {
		algorithms.put(uID, algorithm);
	}
	
	/**
	 * Retrieves a tree from the hash.
	 * 
	 * @param uID - id of the tree to get
	 * @param goalValue - the goal value of the tree
	 * @return a concrete tree with a defined goal value
	 */
	public SearchAlgorithm getAlgorithm(String uID,Tree tree) {
		
		if(!algorithms.containsKey(uID)) return null;
		
		return algorithms.get(uID.toLowerCase()).getAlgorithm(tree);
	}
	
}
	
	
