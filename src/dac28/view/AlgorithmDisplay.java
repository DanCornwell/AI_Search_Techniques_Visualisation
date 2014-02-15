package dac28.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlgorithmDisplay {

	/**
	 * JButtons that will be used to control the search algorithm
	 */
	private JButton step,auto,reset,undo;
	/**
	 * Labels representing the current node and whether it is the goal node
	 */
	private JLabel node,atGoal;
	/**
	 * Lists containing elements that visualise a search algorithms expanded and visited lists
	 */
	private LinkedList<ListCell> expandedList, visitedList;

	/**
	 * Initialises the JPanel displaying algorithm data and returns it.
	 * 
	 * @param WIDTH - width of the whole application
	 * @param HEIGHT - height of the whole application
	 * @return JPanel containing fields to display a search algorithm's data
	 */
	JPanel initialiseAlgorithm(int WIDTH, int HEIGHT) {

		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setPreferredSize(new Dimension(WIDTH/2,HEIGHT-30));

		// Initialise the display components
		final JLabel expandedLabel = new JLabel("Expanded");
		final JLabel visitedLabel = new JLabel("Visited");
		final JLabel currentNode = new JLabel("Current Node: ");
		final JLabel goalLabel = new JLabel("At Goal? : ");
		node = new JLabel("");
		atGoal = new JLabel("");
		atGoal.setPreferredSize(new Dimension(30,30));
		node.setBorder(BorderFactory.createLineBorder(Color.black));
		node.setPreferredSize(new Dimension(30,30));
		expandedList = new LinkedList<ListCell>();
		visitedList = new LinkedList<ListCell>();

		// Buttons
		step = new JButton("Step");
		step.setEnabled(false);
		auto = new JButton("Auto");
		auto.setEnabled(false);
		reset = new JButton("Reset");
		reset.setEnabled(false);
		undo = new JButton("Undo");
		undo.setEnabled(false);

		// Create holding panels and add items to them
		// the width and height are chosen so that the holding jpanels will fit within the algorithm display panel
		final int panelWidth = (WIDTH/2)-20;
		final int panelHeight = (HEIGHT-40)/7;
		// Panel 1 - holds the expanded label
		JPanel p1 = getHoldingPanel(panelWidth,panelHeight-20);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(expandedLabel);
		// Panel 2 - holds the expanded list
		JPanel p2 = getHoldingPanel(panelWidth,panelHeight);
		p2.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		for(int i=0;i<10;i++) {
			ListCell listCell = this.new ListCell();
			expandedList.add(listCell);
			p2.add(listCell.getListCell());
		}
		// Panel 3 - holds the visited label
		JPanel p3 = getHoldingPanel(panelWidth,panelHeight-20);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(visitedLabel);
		// Panel 4 - holds the visited list
		JPanel p4 = getHoldingPanel(panelWidth,panelHeight);
		p4.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		for(int i=0;i<10;i++) {
			ListCell listCell = this.new ListCell();
			visitedList.add(listCell);
			p4.add(listCell.getListCell());
		}
		// Panel 5 - holds the current node labels and at goal labels
		JPanel p5 = getHoldingPanel(panelWidth,panelHeight);
		p5.add(currentNode);
		p5.add(node);
		p5.add(Box.createRigidArea(new Dimension(20,0)));
		p5.add(goalLabel);
		p5.add(atGoal);
		// Panel 6 - holds step, auto and undo buttons
		JPanel p6 = getHoldingPanel(panelWidth,panelHeight);
		p6.add(step);
		p6.add(auto);
		p6.add(undo);
		// Panel 7 - holds the reset button
		JPanel p7 = getHoldingPanel(panelWidth,panelHeight);
		p7.add(reset);

		// Add the holding panels to the algorithm panel
		algorithmPanel.add(p1);
		algorithmPanel.add(p2);
		algorithmPanel.add(p3);
		algorithmPanel.add(p4);
		algorithmPanel.add(p5);
		algorithmPanel.add(p6);
		algorithmPanel.add(p7);

		return algorithmPanel;
	}

	/**
	 * Enables or disables the step button
	 * 
	 * @param bool - true or false
	 */
	public void toggleStep(boolean bool) {
		step.setEnabled(bool);
	}
	
	/**
	 * Enables or disables the auto button
	 * 
	 * @param bool - true or false
	 */
	public void toggleAuto(boolean bool) {
		auto.setEnabled(bool);
	}
	
	/**
	 * Enables or disables the undo button
	 * 
	 * @param bool - true or false
	 */
	public void toggleUndo(boolean bool) {
		undo.setEnabled(bool);
	}
	
	/**
	 * Enables or disables the reset button
	 * 
	 * @param bool - true or false
	 */
	public void toggleReset(boolean bool) {
		reset.setEnabled(bool);
	}
	
	/**
	 * Returns the expanded list
	 * 
	 * @return a ListCell list
	 */
	public LinkedList<ListCell> getExpandedList() {
		return expandedList;
	}
	
	/**
	 * Returns the visited list
	 * 
	 * @return a ListCell list
	 */
	public LinkedList<ListCell> getVisitedList() {
		return visitedList;
	}
	
	
	/**
	 * Sets cell values of a given list of ListCells to be the integers from a given list.
	 * 
	 * @param list - the integer values that the cell values will become
	 * @param targetList - ListCell list whose values will be changed
	 */
	public void setDisplayList(List<Integer> list,LinkedList<ListCell> targetList) {

		// Adds more ListCells if the list isn't big enough
		// NOTE: this will not change anything visually
		if(targetList.size() < list.size()) {
			while(targetList.size()< list.size()) {
				ListCell listCell = this.new ListCell();
				targetList.add(listCell);
			}
		}

		// List containing any elements which will be new to the target list
		LinkedList<String> newElements = new LinkedList<String>();
		// The values retrieved from the targetList's ListCells, in a string format
		LinkedList<String> listValues = new LinkedList<String>();

		for(ListCell cell: targetList) {
			listValues.add(cell.value.getText().toString());
		}

		// If item in integer list is not in the list values list then add it to new elements list
		for(int k=0;k<list.size();k++) {
			if(!listValues.contains(list.get(k).toString())) {
				newElements.add(list.get(k).toString());
			}
		}
		// Reset all ListCell values to be blank
		for(ListCell cell: targetList) {
			cell.setLabelValue("");
		}
		// Sets all the cell values to their new values
		for(int i=0;i<list.size();i++) {
			targetList.get(i).setLabelValue(list.get(i).toString());
			// If a cell value is new to the target list, set background to yellow else white
			if(newElements.contains(list.get(i).toString())) {
				targetList.get(i).setCellBackground(Color.yellow);
			}
			else {
				targetList.get(i).setCellBackground(Color.white);
			}
		}
	}

	/**
	 * Returns a panel used to hold components.
	 * 
	 * @param width - width of the holding panel
	 * @param height - height of the holding panel
	 * @return a specified sized JPanel
	 */
	private JPanel getHoldingPanel(int width,int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width,height));
		return panel;
	}

	/**
	 * Resets the display
	 */
	public void reset() {
		for(ListCell cell: expandedList) {
			cell.setCellBackground(Color.white);
			cell.setLabelValue("");
		}
		for(ListCell cell: visitedList) {
			cell.setCellBackground(Color.white);
			cell.setLabelValue("");
		}
		node.setText("");
		atGoal.setText("");
	}
	
	/**
	 * Registers the step button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerStepListener(ActionListener act) {
		step.addActionListener(act);
	}
	/**
	 * Registers the auto button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerAutoListener(ActionListener act) {
		auto.addActionListener(act);
	}
	/**
	 * Registers the undo button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerUndoListener(ActionListener act) {
		undo.addActionListener(act);
	}
	/**
	 * Registers the reset button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerResetListener(ActionListener act) {
		reset.addActionListener(act);
	}
	
	/**
	 * Class used to display nodes within a search algorithm expanded and visited lists.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class ListCell {

		/**
		 * Value of the node
		 */
		private JLabel value;
		/**
		 * Panel the value is contained in
		 */
		private JPanel panel;

		ListCell() {
			value = new JLabel("");
			panel = new JPanel(new BorderLayout());
		}

		/**
		 * Returns a panel with its value.
		 * 
		 * @return JPanel with a JLabel in it
		 */
		JPanel getListCell() {
			value.setPreferredSize(new Dimension(10,10));
			panel.setBackground(Color.white);
			panel.setPreferredSize(new Dimension(30,30));
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(value,BorderLayout.EAST);
			return panel;

		}

		/**
		 * Sets the ListCell's JLabel
		 * 
		 * @param value - new value the JLabel will take
		 */
		void setLabelValue(String value) {
			this.value.setText(value);
		}

		/**
		 * Sets the JPanels background colour.
		 * 
		 * @param color - colour to set the background to
		 */
		void setCellBackground(Color color) {
			panel.setBackground(color);
		}

	}


}
