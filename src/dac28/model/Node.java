package dac28.model;

import java.util.LinkedList;

/**
 * The node class. Defines the nodes the search algorithms will be working on.
 * Provides method to return its value, as well as add children to itself.
 * 
 * @author Dan Cornwell
 *
 */
public class Node {

	/**
	 * Unique id of the node. Should be used to distinguish nodes, particularly in the case of same value nodes.
	 */
	private final int UID;
	/**
	 * The value of the node.
	 */
	private final String VALUE;
	/**
	 * A list containing any children of the node.
	 */
	private LinkedList<Node> children;

	/**
	 * Constructor.
	 * Assigns the value of VALUE and initialises the children list.
	 * Sets the unique id of the node.
	 * 
	 * @param VALUE - the string value of the VALUE variable
	 * @param UID - the unique identifier of the node
	 */
	Node(final String VALUE,final int UID) {

		this.VALUE = VALUE;
		this.UID = UID;
		children = new LinkedList<Node>();

	}

	/**
	 * Returns the VALUE variable.
	 * 
	 * @return the VALUE variable
	 */
	public final String getValue() {
		return VALUE;
	}

	/**
	 * Returns the unique id of this node.
	 * 
	 * @return integer representing the unique id of this node
	 */
	public final int getUID() {
		return UID;
	}
	
	/**
	 * Returns the opposite of whether the children list is empty.
	 * 
	 * @return whether this node has children or not
	 */
	final boolean hasChild() {
		return !children.isEmpty();
	}

	/**
	 * Adds a node to the nodes children list.
	 * 
	 * @param child - node that will be added
	 */
	final void addChild(Node child) {
		children.add(child);
	}

	/**
	 * Returns the node's children.
	 * 
	 * @return list of the child nodes for this node
	 */
	public final LinkedList<Node> getChildren() {
		return new LinkedList<Node>(children);
	}

}
