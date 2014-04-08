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
		
		Color boxColour = Color.white;
		if(value.equals(tree.getGoal())) boxColour = GOAL_NODE;
		if(!searchAlgorithm.canUndo()) {
			g.setColor(boxColour);
			treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
		}
		else if(value.equals(searchAlgorithm.getCurrentNode().getValue()) && value.equals(dualSearchAlgorithm.getCurrentNode().getValue())) {

			g.setColor(currentNode);
			treePanel.fillBox(g,xPos, yPos, (boxWidth/2)+1, boxHeight);

			g.setColor(dualCurrentNode);
			treePanel.fillBox(g,xPos+(boxWidth/2), yPos, boxWidth/2, boxHeight);


		}
		else {
			if(value.equals(searchAlgorithm.getCurrentNode().getValue())) boxColour = currentNode;
			else if(value.equals(dualSearchAlgorithm.getCurrentNode().getValue())) boxColour = dualCurrentNode;

			g.setColor(boxColour);
			treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
		}
		
		g.drawRect(xPos, yPos, boxWidth, boxHeight);
		g.drawString(value, xPos+(boxWidth/2), yPos+(boxHeight/2));
	}
	
}

