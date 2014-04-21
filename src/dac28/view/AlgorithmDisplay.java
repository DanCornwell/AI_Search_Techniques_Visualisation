package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Class used to display the algorithm details.
 * Creates the panel to display algorithm information and provides methods to
 * colour and change this information when asked.
 * 
 * This class should be sub-classed if a different layout for displaying search algorithm information is needed.
 * This implementation assumes the a QUEUE structure is used, and as such should only be used for 
 * QUEUE based algorithms. If a stack is needed use AlgorithmDisplayStack, which extends this class.
 * 
 * @author Dan Cornwell
 *
 */
public class AlgorithmDisplay {

	/**
	 * JButtons that will be used to control the search algorithm
	 */
	protected JButton step,auto,reset,undo,pause,skip;
	/**
	 * Labels representing the current node and whether it is the goal node
	 */
	protected JLabel node,atGoal,title,expandedLabel,visitedLabel,currentNodeLabel,goalLabel;
	/**
	 * Labels representing the iteration number and expanded list size
	 */
	protected JLabel iterationNumber,expandedListSize;
	/**
	 * Lists containing elements that visualise a search algorithms expanded and visited lists
	 */
	protected LinkedList<JLabel> expandedList, visitedList;
	/**
	 * Stack to hold the list mementos
	 */
	protected Stack<LinkedList<ListElementMemento>> expandedMementos, visitedMementos;
	/**
	 * Default background colour for the list boxes.
	 */
	protected final Color DEFAULT = Color.white;
	/**
	 * Border colour for the list boxes.
	 */
	protected final Color BOX_BORDER_COLOUR = Color.black;
	/**
	 * Colour for the goal node box.
	 */
	private final Color GOAL_NODE = Color.green;
	/**
	 * Colour for the new element in list box.
	 */
	private final Color NEW_ELEMENT = new Color(51,255,255);
	/**
	 * Colour for the current node the algorithm is looking at, should match the current node of the tree display.
	 */
	private Color currentNode = Color.yellow;
	/**
	 * Dimension for the list boxes.
	 */
	protected final Dimension BOX_SIZE = new Dimension(30,30);
	/**
	 * JPanels holding the expanded list and visited list.
	 */
	protected JPanel expandedPanel, visitedPanel;
	/**
	 * The maximum number of boxes the expanded list can show at one time.
	 * Subclasses should define this number when they override the initialise method.
	 */
	protected int onscreenExpandedBoxes;
	/**
	 * The maximum number of boxes the visited list can show at one time.
	 * Subclasses should define this number when they override the initialise method.
	 */
	protected int onscreenVisitedBoxes;

	public AlgorithmDisplay() {

		// Initialises the display components
		title = new JLabel("Search Algorithm Data");
		expandedLabel = new JLabel("Expanded List");
		visitedLabel = new JLabel("Visited List");
		currentNodeLabel = new JLabel("Current Node: ");
		goalLabel = new JLabel("At Goal? : ");
		iterationNumber = new JLabel("",JLabel.CENTER);
		iterationNumber.setOpaque(true);
		iterationNumber.setBackground(DEFAULT);
		iterationNumber.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
		iterationNumber.setPreferredSize(BOX_SIZE);
		expandedListSize = new JLabel("",JLabel.CENTER);
		expandedListSize.setOpaque(true);
		expandedListSize.setBackground(DEFAULT);
		expandedListSize.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
		expandedListSize.setPreferredSize(BOX_SIZE);
		node = new JLabel("",JLabel.CENTER);
		node.setOpaque(true);
		node.setBackground(DEFAULT);
		node.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
		node.setPreferredSize(BOX_SIZE);
		atGoal = new JLabel("",JLabel.CENTER);
		atGoal.setOpaque(true);
		atGoal.setBackground(DEFAULT);
		atGoal.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
		atGoal.setPreferredSize(BOX_SIZE);
		expandedList = new LinkedList<JLabel>();
		visitedList = new LinkedList<JLabel>();
		expandedMementos = new Stack<LinkedList<ListElementMemento>>();
		visitedMementos = new Stack<LinkedList<ListElementMemento>>();

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

	}

	/**
	 * Initialises the JPanel displaying algorithm data and returns it.
	 * Subclasses should overwrite this method and put the code for their
	 * specific layout in here. 
	 * 
	 * @param WIDTH - width of the whole application
	 * @param HEIGHT - height of the whole application
	 * @return JPanel containing fields to display a search algorithm's data
	 */
	protected JPanel initialiseAlgorithmPanel(final int WIDTH,final int HEIGHT) {

		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		// Create holding panels and add items to them
		// the width and height are chosen so that the holding jpanels will fit within the algorithm display panel
		final int panelWidth = (WIDTH)-20;
		final int panelHeight = (HEIGHT-20)/8;

		onscreenExpandedBoxes = 11;
		onscreenVisitedBoxes = 11;

		// Title Panel
		JPanel titlePanel = getHoldingPanel(panelWidth,panelHeight-20);
		titlePanel.add(title);
		// Panel 1 - holds the expanded label
		JPanel p1 = getHoldingPanel(panelWidth,panelHeight-30);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(expandedLabel);

		// Expanded panel (Panel 2) - holds the expanded list
		expandedPanel = getHoldingPanel(BOX_SIZE.width*onscreenExpandedBoxes,BOX_SIZE.height+5);
		expandedPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
		for(int i=0;i<onscreenExpandedBoxes;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);
			expandedList.add(label);
			expandedPanel.add(label);
		}

		// Panel 3 - holds the visited label
		JPanel p3 = getHoldingPanel(panelWidth,panelHeight-30);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(visitedLabel);

		// Visited panel (Panel 4) - holds the visited list
		visitedPanel = getHoldingPanel(BOX_SIZE.width*onscreenVisitedBoxes,BOX_SIZE.height+5);
		visitedPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
		for(int i=0;i<onscreenVisitedBoxes;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);
			visitedList.add(label);
			visitedPanel.add(label);
		}

		// Panel 5 - holds the current node labels and at goal labels
		JPanel p5 = getHoldingPanel(panelWidth,panelHeight-20);
		p5.add(currentNodeLabel);
		p5.add(node);
		p5.add(Box.createRigidArea(new Dimension(40,0)));
		p5.add(goalLabel);
		p5.add(atGoal);
		// Panel 6 - holds step, auto and undo buttons
		JPanel p6 = getHoldingPanel(panelWidth,panelHeight-20);
		p6.add(step);
		p6.add(auto);
		p6.add(undo);
		p6.add(pause);
		// Panel 7 - holds the reset button
		JPanel p7 = getHoldingPanel(panelWidth,panelHeight-20);
		p7.add(skip);
		p7.add(reset);
		// Panel 8 - holds expanded list size and iteration size
		JPanel p8 = getHoldingPanel(panelWidth,panelHeight-20);
		p8.add(new JLabel("Max size of expanded: "));
		p8.add(expandedListSize);
		p8.add(Box.createRigidArea(new Dimension(10,0)));
		p8.add(new JLabel("Number of iterations: "));
		p8.add(iterationNumber);

		// Add the holding panels to the algorithm panel
		algorithmPanel.add(titlePanel);
		algorithmPanel.add(p1);
		// Add the expanded list to a scroll pane and add that scroll pane
		JScrollPane expandedScroller = new JScrollPane(expandedPanel);
		expandedScroller.setBorder(null);
		expandedScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		expandedScroller.setPreferredSize(new Dimension(panelWidth,panelHeight));
		algorithmPanel.add(expandedScroller);
		algorithmPanel.add(p3);
		// Add the visited list to a scroll pane and add that scroll pane
		JScrollPane visitedScroller = new JScrollPane(visitedPanel);
		visitedScroller.setBorder(null);
		visitedScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		visitedScroller.setPreferredSize(new Dimension(panelWidth,panelHeight));
		algorithmPanel.add(visitedScroller);
		algorithmPanel.add(p5);
		algorithmPanel.add(p6);
		algorithmPanel.add(p7);
		algorithmPanel.add(p8);

		return algorithmPanel;
	}

	/**
	 * Enables or disables the step button
	 * 
	 * @param bool - true or false
	 */
	public final void toggleStep(boolean bool) {
		step.setEnabled(bool);
	}

	/**
	 * Enables or disables the auto button
	 * 
	 * @param bool - true or false
	 */
	public final void toggleAuto(boolean bool) {
		auto.setEnabled(bool);
	}

	/**
	 * Enables or disables the undo button
	 * 
	 * @param bool - true or false
	 */
	public final void toggleUndo(boolean bool) {
		undo.setEnabled(bool);
	}

	/**
	 * Enables or disables the reset button
	 * 
	 * @param bool - true or false
	 */
	public final void toggleReset(boolean bool) {
		reset.setEnabled(bool);
	}

	/**
	 * Enables or disables the skip button
	 * 
	 * @param bool - true or false
	 */
	public final void toggleSkip(boolean bool) {
		skip.setEnabled(bool);
	}

	/**
	 * Enables or disables the pause button
	 * 
	 * @param bool - true or false
	 */
	public final void togglePause(boolean bool) {
		pause.setEnabled(bool);
	}

	/**
	 * Sets the current node and at goal labels.
	 * Changes the background colour if we are at the goal.
	 * Reset the labels if we are the beginning of the search, i.e only the root is in the expanded list
	 * 
	 * @param value - new value of the current node label
	 * @param bool - true or false, depending on whether we are at the goal node

	 */
	public final void setNodeAndGoalLabel(String value,boolean bool) {

		node.setText(value);
		if(bool) {
			atGoal.setText("Yes");
			atGoal.setBackground(GOAL_NODE);
			node.setBackground(GOAL_NODE);
		}
		else {
			if(expandedMementos.isEmpty() && visitedMementos.isEmpty()) {
				node.setText("");
				node.setBackground(DEFAULT);
				atGoal.setText("");
				atGoal.setBackground(DEFAULT);
			}
			else {
				atGoal.setText("No");
				atGoal.setBackground(DEFAULT);
				node.setBackground(currentNode);
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
	protected final JPanel getHoldingPanel(int width,int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width,height));
		return panel;
	}

	/**
	 * Resets the display
	 */
	public final void reset(String rootNodeValue) {
		clearLabels();
		expandedList.getFirst().setText(rootNodeValue);
		node.setText("");
		node.setBackground(DEFAULT);
		atGoal.setText("");
		atGoal.setBackground(DEFAULT);

		// Reset the memento stacks
		expandedMementos.clear();
		visitedMementos.clear();

		// Removes any added boxes and the scroll bar until the boxes match the maximum allowed on screen
		while(onscreenExpandedBoxes < expandedList.size()) {

			expandedPanel.remove(expandedList.size()-1);
			expandedList.removeLast();
			expandedPanel.setPreferredSize(new Dimension(expandedList.size()*BOX_SIZE.width,expandedPanel.getHeight()));
			expandedPanel.revalidate();
		}	
		while(onscreenVisitedBoxes < visitedList.size()) {
			visitedPanel.remove(visitedList.size()-1);
			visitedList.removeLast();
			visitedPanel.setPreferredSize(new Dimension(visitedList.size()*BOX_SIZE.width,visitedPanel.getHeight()));
			visitedPanel.revalidate();
		}

	}

	/**
	 * Registers the step button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerStepListener(ActionListener act) {
		step.addActionListener(act);
	}
	/**
	 * Registers the auto button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerAutoListener(ActionListener act) {
		auto.addActionListener(act);
	}
	/**
	 * Registers the undo button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerUndoListener(ActionListener act) {
		undo.addActionListener(act);
	}
	/**
	 * Registers the reset button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerResetListener(ActionListener act) {
		reset.addActionListener(act);
	}
	/**
	 * Registers the skip button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerSkipListener(ActionListener act) {
		skip.addActionListener(act);
	}
	/**
	 * Registers the pause button to its action listener
	 * 
	 * @param act - ActionListener subclass
	 */
	public final void registerPauseListener(ActionListener act) {
		pause.addActionListener(act);
	}

	/**
	 * Creates mementos for both lists, and adds them to the appropriate
	 * memento list.
	 */
	public final void addMemento() {
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
	public final void restoreMemento() {

		if(!expandedMementos.isEmpty() && !visitedMementos.isEmpty()) {
			// Restore expanded list to its most recent memento
			LinkedList<ListElementMemento> eMemento = expandedMementos.pop();
			// If the memento size is bigger or equal to the list set list elements to memento elements
			if(expandedList.size() <= eMemento.size()) {
				for(int i=0;i<expandedList.size();i++) {
					expandedList.get(i).setBackground(eMemento.get(i).COLOR);
					expandedList.get(i).setText(eMemento.get(i).VALUE);
				}
			}
			// Memento size is smaller than the list, so set the list elements to memento elements and make remaining 
			// list elements blank
			else {
				for(int i=0;i<eMemento.size();i++) {
					expandedList.get(i).setBackground(eMemento.get(i).COLOR);
					expandedList.get(i).setText(eMemento.get(i).VALUE);
				}
				for(int j=eMemento.size();j<expandedList.size();j++) {
					expandedList.get(j).setBackground(DEFAULT);
					expandedList.get(j).setText("");
				}
			}
			// Restore visited list to its most recent memento
			LinkedList<ListElementMemento> vMemento = visitedMementos.pop();

			// If the memento size is bigger or equal to the list set list elements to memento elements
			if(visitedList.size() <= vMemento.size()) {
				for(int i=0;i<visitedList.size();i++) {
					visitedList.get(i).setBackground(vMemento.get(i).COLOR);
					visitedList.get(i).setText(vMemento.get(i).VALUE);
				}
			}
			// Memento size is smaller than the list, so set the list elements to memento elements and make remaining 
			// list elements blank
			else {
				for(int i=0;i<vMemento.size();i++) {
					visitedList.get(i).setBackground(vMemento.get(i).COLOR);
					visitedList.get(i).setText(vMemento.get(i).VALUE);
				}
				for(int j=vMemento.size();j<visitedList.size();j++) {
					visitedList.get(j).setBackground(DEFAULT);
					visitedList.get(j).setText("");
				}
			}
		}

		// Remove the added labels if the undo causes the labels to equal the maximum allowed on screen
		if(onscreenExpandedBoxes <= expandedPanel.getComponentCount()-1) {
			expandedPanel.remove(expandedList.size()-1);
			expandedList.removeLast();
			expandedPanel.setPreferredSize(new Dimension(expandedList.size()*BOX_SIZE.width,expandedPanel.getHeight()));
			expandedPanel.revalidate();
		}

		if(onscreenVisitedBoxes <= visitedPanel.getComponentCount()-1) {
			visitedPanel.remove(visitedList.size()-1);
			visitedList.removeLast();
			visitedPanel.setPreferredSize(new Dimension(visitedList.size()*BOX_SIZE.width,visitedPanel.getHeight()));
			visitedPanel.revalidate();
		}

	}

	/**
	 * Updates the two lists values with two given lists of strings.
	 * 
	 * @param expandedValues - new values for the expanded list
	 * @param visitedValues - new values for the visited list
	 */
	public final void setListLabels(List<String> expandedValues, LinkedList<String> visitedValues) {

		// If there are more expanded values than labels, create more expanded labels
		while(expandedValues.size() > expandedList.size()) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);
			expandedList.add(label);
			expandedPanel.add(label);
			expandedPanel.setPreferredSize(new Dimension(expandedList.size()*BOX_SIZE.width,expandedPanel.getHeight()));
			expandedPanel.revalidate();
		}

		// If there are more visited values than labels, create more visited labels
		while(visitedValues.size() > visitedList.size()) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);
			visitedList.add(label);
			visitedPanel.add(label);
			visitedPanel.setPreferredSize(new Dimension(visitedList.size()*BOX_SIZE.width,visitedPanel.getHeight()));
			visitedPanel.revalidate();
			visitedPanel.scrollRectToVisible(new Rectangle(visitedList.size()*BOX_SIZE.width,0,1,1));
		}

		// Clear the list labels
		clearLabels();

		// Sets the lists elements to the new values
		for(int i=0;i<expandedValues.size();i++) {
			expandedList.get(i).setText(String.valueOf(expandedValues.get(i)));
		}
		for(int i=0;i<visitedValues.size();i++) {
			visitedList.get(i).setText(String.valueOf(visitedValues.get(i)));
		}

		// Memento not empty check
		if(expandedMementos.isEmpty() || visitedMementos.isEmpty()) {
			return;
		}

		// Create list of integers from the mementos
		LinkedList<String> previousExpandedValues = new LinkedList<String>();
		for(ListElementMemento element: expandedMementos.peek()) {
			previousExpandedValues.add(element.VALUE);
		}

		// Check the the label values against the memento values
		// If the label values is contained within the memento values
		// Set background to new element colour
		for(JLabel label: expandedList) {

			if(!previousExpandedValues.contains(label.getText())) {
				label.setBackground(NEW_ELEMENT);
			}

		} 

		int firstBlankElement = 0;
		while(firstBlankElement < visitedList.size() &&
				!visitedList.get(firstBlankElement).getText().trim().equals("")) firstBlankElement++;

		visitedList.get(firstBlankElement-1).setBackground(NEW_ELEMENT);


	}

	/**
	 * Clears the list labels
	 */
	protected final void clearLabels() {

		for(JLabel label:expandedList) {
			label.setText("");
			label.setBackground(DEFAULT);
		}
		for(JLabel label:visitedList) {
			label.setText("");
			label.setBackground(DEFAULT);
		}
		
	}

	/**
	 * Sets the title label
	 * 
	 * @param title - new title for the title panel
	 */
	protected final void setTitleLabel(String title) {
		this.title.setText(title);
	}

	/**
	 * Sets the iteration label
	 * 
	 * @param value - new value for the iteration label
	 */
	public final void setIterationLabel(String value) {
		this.iterationNumber.setText(value);
	}

	/**
	 * Sets the expanded list size label
	 * 
	 * @param value - new value for the expanded list size label
	 */
	public final void setMaxExpandedSizeLabel(String value) {
		this.expandedListSize.setText(value);
	}

	/**
	 * Sets the current node colour for this display.
	 * 
	 * @param color - new colour of the current node 
	 */
	public final void setCurrentNodeColor(Color color) {
		this.currentNode = color;
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
	protected class ListElementMemento {

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
