package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlgorithmDisplay {

	/**
	 * JButtons that will be used to control the search algorithm
	 */
	private JButton step,auto,reset,undo,pause,skip;
	/**
	 * Labels representing the current node and whether it is the goal node
	 */
	private JLabel node,atGoal;
	/**
	 * Lists containing elements that visualise a search algorithms expanded and visited lists
	 */
	private LinkedList<JLabel> expandedList, visitedList;
	/**
	 * Stack to hold the list mementos
	 */
	private Stack<LinkedList<ListElementMemento>> expandedMementos, visitedMementos;

	/**
	 * Initialises the JPanel displaying algorithm data and returns it.
	 * 
	 * @param WIDTH - width of the whole application
	 * @param HEIGHT - height of the whole application
	 * @return JPanel containing fields to display a search algorithm's data
	 */
	JPanel initialiseAlgorithmPanel(int WIDTH, int HEIGHT) {

		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setPreferredSize(new Dimension(WIDTH/2,HEIGHT-30));

		expandedMementos = new Stack<LinkedList<ListElementMemento>>();
		visitedMementos = new Stack<LinkedList<ListElementMemento>>();

		// Initialise the display components
		final JLabel expandedLabel = new JLabel("Expanded");
		final JLabel visitedLabel = new JLabel("Visited");
		final JLabel currentNode = new JLabel("Current Node: ");
		final JLabel goalLabel = new JLabel("At Goal? : ");
		node = new JLabel("",JLabel.CENTER);
		node.setOpaque(true);
		node.setBackground(Color.white);
		node.setBorder(BorderFactory.createLineBorder(Color.black));
		node.setPreferredSize(new Dimension(30,30));
		atGoal = new JLabel("",JLabel.CENTER);
		atGoal.setOpaque(true);
		atGoal.setBackground(Color.white);
		atGoal.setBorder(BorderFactory.createLineBorder(Color.black));
		atGoal.setPreferredSize(new Dimension(30,30));
		expandedList = new LinkedList<JLabel>();
		visitedList = new LinkedList<JLabel>();

		// Buttons
		step = new JButton("Step");
		step.setEnabled(false);
		auto = new JButton("Auto");
		auto.setEnabled(false);
		reset = new JButton("Reset");
		reset.setEnabled(false);
		undo = new JButton("Undo");
		undo.setEnabled(false);
		pause = new JButton("Pause");
		pause.setEnabled(false);
		skip = new JButton("Skip to End");
		skip.setEnabled(false);

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
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(new Dimension(30,30));
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			label.setBackground(Color.white);
			expandedList.add(label);
			p2.add(label);
		}
		// Panel 3 - holds the visited label
		JPanel p3 = getHoldingPanel(panelWidth,panelHeight-20);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(visitedLabel);
		// Panel 4 - holds the visited list
		JPanel p4 = getHoldingPanel(panelWidth,panelHeight);
		p4.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		for(int i=0;i<10;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(new Dimension(30,30));
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			label.setBackground(Color.white);		
			visitedList.add(label);
			p4.add(label);
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
		p6.add(pause);
		// Panel 7 - holds the reset button
		JPanel p7 = getHoldingPanel(panelWidth,panelHeight);
		p7.add(skip);
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
	 * Enables or disables the skip button
	 * 
	 * @param bool - true or false
	 */
	public void toggleSkip(boolean bool) {
		skip.setEnabled(bool);
	}

	/**
	 * Enables or disables the pause button
	 * 
	 * @param bool - true or false
	 */
	public void togglePause(boolean bool) {
		pause.setEnabled(bool);
	}

	/**
	 * Returns the expanded list
	 * 
	 * @return a list of JLabels
	 */
	public LinkedList<JLabel> getExpandedList() {
		return new LinkedList<JLabel>(expandedList);
	}

	/**
	 * Returns the visited list
	 * 
	 * @return a list of JLabels
	 */
	public LinkedList<JLabel> getVisitedList() {
		return new LinkedList<JLabel>(visitedList);
	}

	/**
	 * Sets the current node and at goal labels.
	 * Changes the background colour if we are at the goal.
	 * 
	 * @param value - new value of the current node label
	 * @param bool - true or false, depending on whether we are at the goal node

	 */
	public void setNodeAndGoalLabel(String value,boolean bool) {
		node.setText(value);
		if(bool) {
			atGoal.setText("Yes");
			atGoal.setBackground(Color.yellow);
			node.setBackground(Color.yellow);
		}
		else {
			atGoal.setText("No");
			atGoal.setBackground(Color.white);
			node.setBackground(Color.white);
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
		resetLabels();
		node.setText("");
		node.setBackground(Color.white);
		atGoal.setText("");
		atGoal.setBackground(Color.white);
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
	 * Registers the skip button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerSkipListener(ActionListener act) {
		skip.addActionListener(act);
	}
	/**
	 * Registers the pause button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public void registerPauseListener(ActionListener act) {
		pause.addActionListener(act);
	}

	/**
	 * Creates mementos for both lists, and adds them to the appropriate
	 * memento list.
	 */
	public void addMemento() {
		// Expanded list memento
		LinkedList<ListElementMemento> eMemento = new LinkedList<ListElementMemento>();
		for(JLabel label: expandedList) {
			eMemento.add(new ListElementMemento(label));
		}
		expandedMementos.push(eMemento);
		// Visited list memento
		LinkedList<ListElementMemento> vMemento = new LinkedList<ListElementMemento>();
		for(JLabel label: visitedList) {
			vMemento.add(new ListElementMemento(label));
		}
		visitedMementos.push(vMemento);
	}

	/**
	 * Restores the most recent memento, assuming one exists
	 */
	public void restoreMemento() {
		if(!expandedMementos.isEmpty() && !visitedMementos.isEmpty()) {
			// Restore expanded list to its most recent memento
			LinkedList<ListElementMemento> eMemento = expandedMementos.pop();
			for(int i=0;i<expandedList.size();i++) {
				expandedList.get(i).setBackground(eMemento.get(i).COLOR);
				expandedList.get(i).setText(eMemento.get(i).VALUE);
			}
			// Restore visited list to its most recent memento
			LinkedList<ListElementMemento> vMemento = visitedMementos.pop();
			for(int i=0;i<visitedList.size();i++) {
				visitedList.get(i).setBackground(vMemento.get(i).COLOR);
				visitedList.get(i).setText(vMemento.get(i).VALUE);
			}
		}
	}

	/**
	 * Updates the two lists values with two given lists of integers.
	 * 
	 * @param expandedValues - new values for the expanded list
	 * @param visitedValues - new values for the visited list
	 */
	public void setLabelValues(List<Integer> expandedValues, LinkedList<Integer> visitedValues) {

		// Length check
		if(expandedValues.size() > expandedList.size() || visitedValues.size() > visitedList.size()) {
			return;
		}

		// Reset the list labels
		resetLabels();
		
		// Sets the lists elements to the new values
		for(int i=0;i<expandedValues.size();i++) {
			expandedList.get(i).setText(String.valueOf(expandedValues.get(i)));
		}
		for(int i=0;i<visitedValues.size();i++) {
			visitedList.get(i).setText(String.valueOf(visitedValues.get(i)));
		}
	}
	
	/**
	 * Sets the label backgrounds of the lists. If an element is new to the list,
	 * (by comparing with a memento) then set background to yellow.
	 */
	public void setLabelBackgrounds() {

		// Memento not empty check
		if(expandedMementos.isEmpty() || visitedMementos.isEmpty()) {
			return;
		}

		// Create list of integers from the mementos
		LinkedList<String> previousExpandedValues = new LinkedList<String>();
		for(ListElementMemento element: expandedMementos.peek()) {
			previousExpandedValues.add(element.VALUE);
		}
		LinkedList<String> previousVisitedValues = new LinkedList<String>();
		for(ListElementMemento element: visitedMementos.peek()) {
			previousVisitedValues.add(element.VALUE);
		}
		
		// Check the the label values against the memento values
		// If the label values is contained within the memento values
		// Set background to yellow
		for(JLabel label: expandedList) {
			if(!previousExpandedValues.contains(label.getText())) {
				label.setBackground(Color.yellow);
			}
		}
		for(JLabel label: visitedList) {
			if(!previousVisitedValues.contains(label.getText())) {
				label.setBackground(Color.yellow);
			}
		}

	}
	
	/**
	 * Resets the list labels
	*/
	public void resetLabels() {
		
		for(int i=0;i<expandedList.size();i++) {
			expandedList.get(i).setText("");
			expandedList.get(i).setBackground(Color.white);
		}
		for(int i=0;i<visitedList.size();i++) {
			visitedList.get(i).setText("");
			visitedList.get(i).setBackground(Color.white);
		}
	}

	/**
	 * Memento class for the list elements.
	 * Holds a colour instance and a string.
	 * When restoring a memento the JLabel's background
	 * becomes COLOR and its text becomes VALUE.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class ListElementMemento {

		/**
		 * A colour relating to a JLabel background
		 */
		private final Color COLOR;
		/**
		 * A string relating to a JLabel text
		 */
		private final String VALUE;

		ListElementMemento(JLabel listElement) {
			COLOR = listElement.getBackground();
			VALUE = listElement.getText();
		}
	}

}
