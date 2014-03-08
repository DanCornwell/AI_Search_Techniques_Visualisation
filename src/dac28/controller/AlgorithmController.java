package dac28.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;

import dac28.model.SearchAlgorithm;
import dac28.view.AlgorithmDisplay;

/**
 * Controller for the algorithm display. 
 * Defines the listeners for algorithm display panels buttons.
 * 
 * @author Dan Cornwell
 *
 */
public class AlgorithmController {

	/**
	 * The algorithm display panel.
	 */
	private AlgorithmDisplay algorithmDisplay;
	/**
	 * The search algorithm being used.
	 */
	private SearchAlgorithm searchAlgorithm;
	/**
	 * The tree controller thats controls the tree display.
	 */
	private TreeController treeController;

	private final String SEARCH_TYPE;

	public AlgorithmController(TreeController treeController,SearchAlgorithm searchAlgorithm,AlgorithmDisplay algorithmDisplay) {

		this.treeController = treeController;
		this.searchAlgorithm = searchAlgorithm;
		this.algorithmDisplay = algorithmDisplay;

		initialiseExpandedList();

		this.algorithmDisplay.toggleAuto(true);
		this.algorithmDisplay.toggleReset(true);
		this.algorithmDisplay.toggleStep(true);
		this.algorithmDisplay.toggleSkip(true);
		this.algorithmDisplay.registerStepListener(new StepListener());
		this.algorithmDisplay.registerUndoListener(new UndoListener());
		this.algorithmDisplay.registerResetListener(new ResetListener());
		this.algorithmDisplay.registerSkipListener(new SkipListener());

		AutoListener auto = new AutoListener();
		this.algorithmDisplay.registerAutoListener(auto);
		this.algorithmDisplay.registerPauseListener(new PauseListener(auto));

		this.SEARCH_TYPE = this.searchAlgorithm.getClass().getName();
	} 

	/**
	 * Initialise the expanded list with its values.
	 * Called within the constructor and reset method.
	 * A visited list equivalent is not needed due to that list being empty on start. 
	 */
	private void initialiseExpandedList() {

		if(searchAlgorithm.getExpanded().size() > algorithmDisplay.getExpandedList().size()) {
			return;
		}

		// Reset the algorithm and display
		searchAlgorithm.reset();
		algorithmDisplay.reset();

		algorithmDisplay.getExpandedList().get(0).setText(String.valueOf(searchAlgorithm.getExpanded().get(0).getValue()));
		algorithmDisplay.getExpandedList().get(0).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(139, 0, 255)));

	}

	/**
	 * Defines a listener for the algorithm display panel.
	 * Calls a button specific method, then gets the expanded and visited lists 
	 * from the search algorithm and updates the display with them.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private abstract class DisplayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Perform the subclass button's logic.
			buttonLogic();

			// Set the buttons to enable or disabled depending on where we are.
			if(searchAlgorithm.atGoal() || !searchAlgorithm.nodesToExplore()) {
				algorithmDisplay.toggleAuto(false);
				algorithmDisplay.toggleStep(false);
				algorithmDisplay.toggleSkip(false);
			}
			else {
				algorithmDisplay.toggleAuto(true);
				algorithmDisplay.toggleStep(true);
				algorithmDisplay.toggleSkip(true);
			}
			if(searchAlgorithm.canUndo()) {
				algorithmDisplay.toggleUndo(true);
			}
			else {
				algorithmDisplay.toggleUndo(false);
			}

			// Update the tree.
			treeController.drawTree();
		}

		/**
		 * Performs the logic associated with the button being pressed
		 */
		protected abstract void buttonLogic();

	}

	/**
	 * Step button listener.
	 * Calls the search algorithms step method.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class StepListener extends DisplayListener {

		@Override
		protected void buttonLogic() {	
			// Create a memento based on the current state.
			algorithmDisplay.addMemento();
			// Perform a step of the algorithm.
			searchAlgorithm.step();
			// Get the new values of the expanded and visited lists.
			LinkedList<Integer> expandedValues = new LinkedList<Integer>();
			for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
				expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
			}
			LinkedList<Integer> visitedValues = new LinkedList<Integer>();
			for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
				visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
			}
			// Update the algorithm display to the new values of expanded and visited lists.
			algorithmDisplay.setLabelValues(expandedValues, visitedValues);
			// Set the label backgrounds.
			algorithmDisplay.setLabelBackgrounds();
			// Set the current node and at goal labels.
			algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());
			
			// If we are using DFS, and hence a stack 
			if(!searchAlgorithm.getExpanded().isEmpty() && SEARCH_TYPE == "dac28.model.DepthFirstSearch") {
				algorithmDisplay.setHeadTopBorder(searchAlgorithm.getExpanded().size()-1);
			}
			else algorithmDisplay.setHeadTopBorder(0);
			
		}

	}

	/**
	 * Auto button listener.
	 * Calls the search algorithms auto method.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class AutoListener extends DisplayListener {

		/**
		 * The thread used to automatically step through the algorithm.
		 */
		AutoThread thread = null;

		@Override
		protected void buttonLogic() {
			thread = new AutoThread();
			thread.start();
		}


	}

	/**
	 * Pause button listener.
	 * Takes an AutoListener which it uses to get the current thread running.
	 * Provides way to pause this thread.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class PauseListener extends DisplayListener {

		/**
		 * The AutoListener starting the thread
		 */
		AutoListener auto = null;

		PauseListener(AutoListener auto) {
			this.auto = auto;
		}

		/**
		 * Returns the current active thread.
		 * 
		 * @return an instance of AutoThread, a subclass of Thread
		 */
		private AutoThread getThread() {
			return auto.thread;
		}

		@Override
		protected void buttonLogic() {

			// Gets thread from the auto listener, and stops it if it exists
			AutoThread thread = getThread();
			if(thread!=null)thread.stopAuto();
		}

	}

	/**
	 * Undo button listener.
	 * Calls the search algorithms undo method.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class UndoListener extends DisplayListener {

		@Override
		protected void buttonLogic() {
			// Restores the expanded and visited display lists to the most current mementos.
			algorithmDisplay.restoreMemento();
			// Undoes a step in the algorithm.
			searchAlgorithm.undo();
			// Sets the current node and at goal labels.
			algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());
		}

	}

	/**
	 * Reset button listener.
	 * Calls the search algorithms reset method.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class ResetListener extends DisplayListener {

		@Override
		protected void buttonLogic() {

			// Initialises the display as if it were new.
			initialiseExpandedList();
		}

	}

	/**
	 * Skip button listener.
	 * Calls the auto method in the search algorithm, which takes the algorithm
	 * as far as it can (be it finding the goal or run out of nodes).
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class SkipListener extends DisplayListener {

		@Override
		protected void buttonLogic() {
			// Skips the algorithm to its final state and then updates the display just like in the step button.
			searchAlgorithm.auto();
			LinkedList<Integer> expandedValues = new LinkedList<Integer>();
			for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
				expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
			}
			LinkedList<Integer> visitedValues = new LinkedList<Integer>();
			for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
				visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
			}
			algorithmDisplay.setLabelValues(expandedValues, visitedValues);
			algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());

		}

	}

	/**
	 * Thread for the auto complete button.
	 * Extends thread interface, and so uses run method to run the thread.
	 * Defines a method to stop the thread from executing.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class AutoThread extends Thread {

		/**
		 * Boolean to start/stop the algorithm.
		 */
		volatile boolean running = true;

		/**
		 * Stops the thread.
		 */
		public void stopAuto() {
			running = false;
		}

		/**
		 * Runs the thread.
		 */
		public void run() {

			while(running) {
				// Disable all buttons.
				algorithmDisplay.toggleAuto(false);
				algorithmDisplay.toggleStep(false);
				algorithmDisplay.toggleReset(false);
				algorithmDisplay.toggleSkip(false);
				algorithmDisplay.toggleUndo(false);
				// Enable pause button.
				algorithmDisplay.togglePause(true);

				// If search is at goal or out of nodes, stop.
				if(searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) stopAuto();
				// Peform a step in the algorithm.
				algorithmDisplay.addMemento();
				searchAlgorithm.step();
				LinkedList<Integer> expandedValues = new LinkedList<Integer>();
				for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
					expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
				}
				LinkedList<Integer> visitedValues = new LinkedList<Integer>();
				for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
					visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
				}
				algorithmDisplay.setLabelValues(expandedValues, visitedValues);
				algorithmDisplay.setLabelBackgrounds();
				algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());		
				
				// If we are using DFS, and hence a stack 
				if(!searchAlgorithm.getExpanded().isEmpty() && SEARCH_TYPE == "dac28.model.DepthFirstSearch") {
					algorithmDisplay.setHeadTopBorder(searchAlgorithm.getExpanded().size()-1);
				}
				else algorithmDisplay.setHeadTopBorder(0);
				
				// Update display tree.
				treeController.drawTree();

				if(searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) stopAuto();

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					return;
				}

				if(searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) stopAuto();

			}
			// Disable pause.
			algorithmDisplay.togglePause(false);

			// Set the buttons to enable or disabled depending on where we are.
			if(searchAlgorithm.atGoal() || !searchAlgorithm.nodesToExplore()) {
				algorithmDisplay.toggleAuto(false);
				algorithmDisplay.toggleStep(false);
				algorithmDisplay.toggleSkip(false);
			}
			else {
				algorithmDisplay.toggleAuto(true);
				algorithmDisplay.toggleStep(true);
				algorithmDisplay.toggleSkip(true);
			}
			if(searchAlgorithm.canUndo()) {
				algorithmDisplay.toggleUndo(true);
			}
			else {
				algorithmDisplay.toggleUndo(false);
			}
			algorithmDisplay.toggleReset(true);

		}

	}

}
