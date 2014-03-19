package dac28.model;

import java.util.HashMap;

public class TreeCreator {
	
	protected static TreeCreator instance = null;
	
	private HashMap<String,Tree> trees = new HashMap<String,Tree>();
	
	protected TreeCreator() {
		addTree("tree124",new Tree124(0));
		addTree("tree1355",new Tree1355(0));
		addTree("tree112111",new Tree112111(0));
	}
	
	public static TreeCreator getInstance() {
		if(instance==null) instance = new TreeCreator();
		return instance;
	}
	
	public void addTree(String uID,Tree tree) {
		trees.put(uID, tree);
	}
	
	public boolean containsUID(String uID) {
		return trees.containsKey(uID);
	}
	
	public Tree getTree(String uID,int goalValue) {
		if(!containsUID(uID)) return null;
		
		Tree tree = trees.get(uID.toLowerCase()).getInstance(goalValue);
		return tree;
	}
	
	
}
