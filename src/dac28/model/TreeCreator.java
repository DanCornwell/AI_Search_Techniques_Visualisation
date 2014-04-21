package dac28.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


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
	private HashMap<Integer,Tree> trees = new HashMap<Integer,Tree>();

	/**
	 * Constructor for the tree creator. 
	 * Adds all known trees to the HashMap.
	 */
	protected TreeCreator() {

		List<String> treeNames = null;

		treeNames = TextFileReader.getTrees();

		if(treeNames == null) return;

		int id = 0;

		for(String treeName: treeNames) {
			putTree(id++,treeName);
		}
	}

	/**
	 * Returns the tree creator, initialising it if it doesn't exist
	 * 
	 * @return the tree creator
	 */
	public final static TreeCreator getInstance() {
		if(instance==null) instance = new TreeCreator();
		return instance;
	}

	/**
	 * Retrieves a tree from the hash.
	 * 
	 * @param uID - id of the tree to get
	 * @param values - a queue of strings for the node values
	 * @return a concrete tree with a defined goal value
	 */
	public final Tree getTree(int uID,Queue<String> values) {

		if(!trees.containsKey(uID)) return null;

		return trees.get(uID).getTree(values);
	}

	/**
	 * Adds a new tree to the trees hash, using a tree name and unique id key.
	 * 
	 * @param id - the key for the hash
	 * @param treeName - the name of the tree that will be the value for the hash
	 */
	public final void putTree(int id,String treeName) {
		try {

			Class<?> tree = Class.forName("dac28.model.".concat(treeName));
			Tree treeInstance = (Tree) tree.getDeclaredConstructor(Queue.class).newInstance(new LinkedList<String>());
			trees.put(id, treeInstance);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException 
				| NoSuchMethodException | SecurityException e) { 
		}	}
}
