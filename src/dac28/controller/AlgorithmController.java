package dac28.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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

	public AlgorithmController(SearchAlgorithm searchAlgorithm,AlgorithmDisplay algorithmDisplay) {

		this.searchAlgorithm = searchAlgorithm;
		this.algorithmDisplay = algorithmDisplay;
		
		LinkedList<Integer> initial = new LinkedList<Integer>();
		for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
			initial.add(searchAlgorithm.getExpanded().get(i).getValue());

		}		
		algorithmDisplay.setDisplayList(initial, algorithmDisplay.getExpandedList());
		algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());
		algorithmDisplay.toggleAuto(true);
		algorithmDisplay.toggleReset(true);
		algorithmDisplay.toggleStep(true);
		algorithmDisplay.registerStepListener(new StepListener());
		algorithmDisplay.registerAutoListener(new AutoListener());
		algorithmDisplay.registerUndoListener(new UndoListener());
		algorithmDisplay.registerResetListener(new ResetListener());
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

			buttonLogic();
	
			algorithmDisplay.setNodeAndGoalLabel(String.valueOf(searchAlgorithm.getCurrentNode().getValue()),searchAlgorithm.atGoal());
			
			if(searchAlgorithm.atGoal() || searchAlgorithm.nodesUnexplored()) {
				algorithmDisplay.toggleAuto(false);
				algorithmDisplay.toggleStep(false);
			}
			else {
				algorithmDisplay.toggleAuto(true);
				algorithmDisplay.toggleStep(true);
			}
			if(searchAlgorithm.canUndo()) {
				algorithmDisplay.toggleUndo(true);
			}
			else {
				algorithmDisplay.toggleUndo(false);
			}
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
			algorithmDisplay.addMemento();
			searchAlgorithm.step();
			LinkedList<Integer> expandedValues = new LinkedList<Integer>();
			for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
				expandedValues.add(searchAlgorithm.getExpanded().get(i).getValue());
			}
			algorithmDisplay.setDisplayList(expandedValues, algorithmDisplay.getExpandedList());
			LinkedList<Integer> visitedValues = new LinkedList<Integer>();
			for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
				visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
			}
			algorithmDisplay.setDisplayList(visitedValues, algorithmDisplay.getVisitedList());
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

		@Override
		protected void buttonLogic() {
			searchAlgorithm.auto();
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
			algorithmDisplay.restoreMemento();
			searchAlgorithm.undo();
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
			searchAlgorithm.reset();
		}

	}

}
