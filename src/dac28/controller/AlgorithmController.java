package dac28.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

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
	/**
	 * The iteration number of the search algorithm and the maximum size of the search algorithm.
	 */
	private int iterationNumber,maxExpandedSize;
	/**
	 * Stores the expanded list sizes at each iteration.
	 */
	private Stack<Integer> expandedSizes;

	public AlgorithmController(TreeController treeController,SearchAlgorithm searchAlgorithm,AlgorithmDisplay algorithmDisplay) {

		this.treeController = treeController;
		this.searchAlgorithm = searchAlgorithm;
		this.algorithmDisplay = algorithmDisplay;

		resetExpandedList();

		this.algorithmDisplay.toggleAuto(true);
		this.algorithmDisplay.toggleStep(true);
		this.algorithmDisplay.toggleSkip(true);
		this.algorithmDisplay.registerStepListener(new StepListener());
		this.algorithmDisplay.registerUndoListener(new UndoListener());
		this.algorithmDisplay.registerResetListener(new ResetListener());
		this.algorithmDisplay.registerSkipListener(new SkipListener());

		AutoListener auto = new AutoListener();
		this.algorithmDisplay.registerAutoListener(auto);
		this.algorithmDisplay.registerPauseListener(new PauseListener(auto));

		iterationNumber = 0;
		maxExpandedSize = 0;
		expandedSizes = new Stack<Integer>();

	} 

	/**
	 * Resets the expanded list then adds the root nodes value to it, like in the begining of a search.
	 * Called within the constructor and reset method.
	 * A visited list equivalent is not needed due to that list being empty on start. 
	 */
	private void resetExpandedList() {

		// Reset the algorithm and display
		searchAlgorithm.reset();
		algorithmDisplay.reset(searchAlgorithm.getCurrentNode().getValue());
		
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
			if(searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) {
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
				algorithmDisplay.toggleReset(true);
			}
			else {
				algorithmDisplay.toggleUndo(false);
				algorithmDisplay.toggleReset(false);
			}
			
			if(expandedSizes.isEmpty()) {
				algorithmDisplay.setIterationLabel("");
				algorithmDisplay.setMaxExpandedSizeLabel("");
			}
			else {
				algorithmDisplay.setIterationLabel(String.valueOf(iterationNumber));
				algorithmDisplay.setMaxExpandedSizeLabel(String.valueOf(expandedSizes.peek()));
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
			iterationNumber++;
			if(searchAlgorithm.getExpanded().size() > maxExpandedSize) maxExpandedSize = searchAlgorithm.getExpanded().size();
			expandedSizes.push(maxExpandedSize);
			// Get the new values of the expanded and visited lists.
			LinkedList<String> expandedValues = new LinkedList<String>();
			for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
				expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
			}
			LinkedList<String> visitedValues = new LinkedList<String>();
			for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
				visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
			}
			// Update the algorithm display to the new values of expanded and visited lists.
			algorithmDisplay.setListLabels(expandedValues, visitedValues);
			// Set the current node and at goal labels.
			algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());

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
			iterationNumber--;
			maxExpandedSize = expandedSizes.pop();
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

			// Reset the display 
			
			resetExpandedList();

			iterationNumber = 0;
			expandedSizes.clear();
			maxExpandedSize = 0;
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
			// Calls the step button until the end of the search, then updates the display
			while(!searchAlgorithm.atGoal() && !searchAlgorithm.getExpanded().isEmpty()) {
				// Create a memento based on the current state.
				algorithmDisplay.addMemento();
				// Perform a step of the algorithm.
				searchAlgorithm.step();
				iterationNumber++;
				if(searchAlgorithm.getExpanded().size() > maxExpandedSize) maxExpandedSize = searchAlgorithm.getExpanded().size();
				expandedSizes.push(maxExpandedSize);
				LinkedList<String> expandedValues = new LinkedList<String>();
				for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
					expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
				}
				LinkedList<String> visitedValues = new LinkedList<String>();
				for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
					visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
				}
				algorithmDisplay.setListLabels(expandedValues, visitedValues);
				algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());
			} 
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
				iterationNumber++;
				if(searchAlgorithm.getExpanded().size() > maxExpandedSize) maxExpandedSize = searchAlgorithm.getExpanded().size();
				expandedSizes.push(maxExpandedSize);
				LinkedList<String> expandedValues = new LinkedList<String>();
				for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
					expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
				}
				LinkedList<String> visitedValues = new LinkedList<String>();
				for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
					visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
				}
				algorithmDisplay.setListLabels(expandedValues, visitedValues);
				algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());		

				algorithmDisplay.setIterationLabel(String.valueOf(iterationNumber));
				algorithmDisplay.setMaxExpandedSizeLabel(String.valueOf(expandedSizes.peek()));

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
			if(searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) {
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
