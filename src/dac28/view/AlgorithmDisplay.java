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
	private JButton step,auto,reset,undo;
	/**
	 * Labels representing the current node and whether it is the goal node
	 */
	private JLabel node,atGoal;
	/**
	 * Lists containing elements that visualise a search algorithms expanded and visited lists
	 */
	private LinkedList<ListCell> expandedList, visitedList;
	
	private Stack<LinkedList<ListCellMemento>> expandedMementos, visitedMementos;

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
		
		expandedMementos = new Stack<LinkedList<ListCellMemento>>();
		visitedMementos = new Stack<LinkedList<ListCellMemento>>();

		// Initialise the display components
		final JLabel expandedLabel = new JLabel("Expanded");
		final JLabel visitedLabel = new JLabel("Visited");
		final JLabel currentNode = new JLabel("Current Node: ");
		final JLabel goalLabel = new JLabel("At Goal? : ");
		node = new JLabel("",JLabel.CENTER);
		atGoal = new JLabel("",JLabel.CENTER);
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
		return new LinkedList<ListCell>(expandedList);
	}

	/**
	 * Returns the visited list
	 * 
	 * @return a ListCell list
	 */
	public LinkedList<ListCell> getVisitedList() {
		return new LinkedList<ListCell>(visitedList);
	}

	/**
	 * Sets the current node label 
	 * 
	 * @param value - new value of the current node label
	 */
	public void setCurrentNodeLabel(String value) {
		node.setText(value);
	}

	/**
	 * Sets the at label based on a boolean value. Yes if true, no if false.
	 * 
	 * @param bool - true or false, depending on whether we are at the goal node
	 */
	public void setAtGoalLabel(boolean bool) {
		if(bool) atGoal.setText("Yes");
		else atGoal.setText("No");
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
			cell.panel.setBackground(Color.white);
			cell.value.setText("");
		}
		for(ListCell cell: visitedList) {
			cell.panel.setBackground(Color.white);
			cell.value.setText("");
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
	
	public void addMemento() {
		LinkedList<ListCellMemento> eMemento = new LinkedList<ListCellMemento>();
		for(ListCell cell: expandedList) {
			eMemento.add(new ListCellMemento(cell));
		}
		expandedMementos.push(eMemento);
		LinkedList<ListCellMemento> vMemento = new LinkedList<ListCellMemento>();
		for(ListCell cell: visitedList) {
			vMemento.add(new ListCellMemento(cell));
		}
		visitedMementos.push(vMemento);
	}
	
	public void restoreMemento() {
		if(!expandedMementos.isEmpty() && !visitedMementos.isEmpty()) {
			LinkedList<ListCellMemento> eMemento = expandedMementos.pop();
			for(int i=0;i<expandedList.size();i++) {
				expandedList.get(i).panel.setBackground(eMemento.get(i).COLOR);
				expandedList.get(i).value.setText(eMemento.get(i).VALUE);
			}
			LinkedList<ListCellMemento> vMemento = visitedMementos.pop();
			for(int i=0;i<visitedList.size();i++) {
				visitedList.get(i).panel.setBackground(vMemento.get(i).COLOR);
				visitedList.get(i).value.setText(vMemento.get(i).VALUE);
			}
		}
	}
	
	public void setDisplayList(List<Integer> list,LinkedList<ListCell> targetList) {

		LinkedList<String> newElements = new LinkedList<String>();
		LinkedList<String> listValues = new LinkedList<String>();
		
		for(ListCell cell: targetList) {
			listValues.add(cell.value.getText().toString());
		}
		
		for(int k=0;k<list.size();k++) {
			if(!listValues.contains(list.get(k).toString())) {
				newElements.add(list.get(k).toString());
			}
		}		
		for(ListCell cell: targetList) {
			cell.value.setText("");
			cell.panel.setBackground(Color.white);
		}
		for(int i=0;i<list.size();i++) {
			targetList.get(i).value.setText(list.get(i).toString());
			if(newElements.contains(list.get(i).toString())) {
				targetList.get(i).panel.setBackground(Color.yellow);
			}
		}
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
			value = new JLabel("",JLabel.CENTER);
			panel = new JPanel();
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
			panel.add(value);
			
			return panel;

		}

	}
	
	private class ListCellMemento {
		
		private final Color COLOR;
		private final String VALUE;
		
		ListCellMemento(ListCell listCell) {
			COLOR = listCell.panel.getBackground();
			VALUE = listCell.value.getText();
		}
	}

}
