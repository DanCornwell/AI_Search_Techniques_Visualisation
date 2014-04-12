package dac28.view;

import java.awt.Color;
import java.awt.Graphics;

import dac28.model.Node;
import dac28.model.SearchAlgorithm;

/**
 * Dual tree display class, a subclass of tree display.
 * Defines how to draw a tree with a two algorithms searching on it.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeDisplayDualAlgorithms extends TreeDisplay {

	/**
	 * The second algorithm searching on the tree.
	 */
	private SearchAlgorithm dualSearchAlgorithm;
	/**
	 * Colour of the current node of dual algorithm in the tree display.
	 */
	private Color dualCurrentNode;

	@Override
	public void setAlgorithm(SearchAlgorithm[] searchAlgorithms) {
		if(searchAlgorithms.length < 2) return;
		this.searchAlgorithm = searchAlgorithms[0];
		this.dualSearchAlgorithm = searchAlgorithms[1];
	}

	@Override
	public void setCurrentNodeColor(Color[] colors) {
		if(colors.length < 2) return;
		this.currentNode = colors[0];
		this.dualCurrentNode = colors[1];

	}

	@Override
	protected void drawTreeBox(Node node,Graphics g,int xPos, int yPos, int boxWidth, int boxHeight) {

		String value = node.getValue();

		g.setColor(Color.white);
		
		// if at goal node
		if(value.equals(tree.getGoal())) {
			g.setColor(GOAL_NODE);
			treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
		}
		// if both algorithms are at the same node
		if(searchAlgorithm.canUndo() && dualSearchAlgorithm.canUndo()) {
			if(node.getUID()==searchAlgorithm.getCurrentNode().getUID() && node.getUID()==dualSearchAlgorithm.getCurrentNode().getUID()) {

				g.setColor(currentNode);
				treePanel.fillBox(g,xPos, yPos, (boxWidth/2)+1, boxHeight);

				g.setColor(dualCurrentNode);
				treePanel.fillBox(g,xPos+(boxWidth/2), yPos, boxWidth/2, boxHeight);

			}
			else {
				// algorithm 1 is at the node
				if(node.getUID()==searchAlgorithm.getCurrentNode().getUID()) {
					g.setColor(currentNode);
					treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
				}
				// algorithm 2 is at the node
				else if(node.getUID()==dualSearchAlgorithm.getCurrentNode().getUID()) {
					g.setColor(dualCurrentNode);
					treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
				}
			}
		}
		
		g.setColor(Color.black);
		g.drawRect(xPos, yPos, boxWidth, boxHeight);
		g.drawString(value, xPos+(boxWidth/2), yPos+(boxHeight/2));
	}

}

