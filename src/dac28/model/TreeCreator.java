package dac28.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import dac28.io.TextFileReader;

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
	 * Adds all known trees to the HashMap. 0 is a trivial constructor value for the trees.
	 */
	protected TreeCreator() {

		List<String> treeNames = null;

		treeNames = TextFileReader.getTrees();

		if(treeNames == null) return;

		int id = 0;

		for(String treeName: treeNames) {
			try {

				Class<?> tree = Class.forName("dac28.model.".concat(treeName));
				Tree treeInstance = (Tree) tree.getDeclaredConstructor(int.class).newInstance(0);
				trees.put(id, treeInstance);
				id++;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
					| IllegalArgumentException | InvocationTargetException 
					| NoSuchMethodException | SecurityException e) {
			}
		}
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
	 * Retrieves a tree from the hash.
	 * 
	 * @param uID - id of the tree to get
	 * @param goalValue - the goal value of the tree
	 * @return a concrete tree with a defined goal value
	 */
	public Tree getTree(int uID,int goalValue) {

		if(!trees.containsKey(uID)) return null;

		return trees.get(uID).getTree(goalValue);
	}


}
