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
public class TreeCreator {
	
	/**
	 * The static instance of the tree creator.
	 */
	protected static TreeCreator instance = null;
	/**
	 * The HashMap containing all known trees. 
	 */
	private HashMap<String,Tree> trees = new HashMap<String,Tree>();

	/**
	 * Constructor for the tree creator. 
	 * Adds all known trees to the HashMap. 0 is a trivial constructor value for the trees.
	 */
	protected TreeCreator() {
		addTree("tree124",new Tree124(0));
		addTree("tree1355",new Tree1355(0));
		addTree("tree112111",new Tree112111(0));
	}
	
	/**
	 * Returns the tree creator, initialising it if it doesn't exist
	 * 
	 * @return the tree creator
	 */
	public static TreeCreator getInstance() {
		if(instance==null) instance = new TreeCreator();
		return instance;
	}
	
	/**
	 * Adds a tree to the creator hash.
	 * 
	 * @param uID - the key that will be used to receive the tree
	 * @param tree - the tree to store
	 */
	public void addTree(String uID,Tree tree) {
		trees.put(uID, tree);
	}
	
	/**
	 * Retrieves a tree from the hash.
	 * 
	 * @param uID - id of the tree to get
	 * @param goalValue - the goal value of the tree
	 * @return a concrete tree with a defined goal value
	 */
	public Tree getTree(String uID,int goalValue) {
		
		if(!trees.containsKey(uID)) return null;
		
		return trees.get(uID.toLowerCase()).getTree(goalValue);
	}
	
	
}
