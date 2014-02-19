package dac28.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

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

		initialiseExpandedList();

		this.algorithmDisplay.toggleAuto(true);
		this.algorithmDisplay.toggleReset(true);
		this.algorithmDisplay.toggleStep(true);
		this.algorithmDisplay.toggleSkip(true);
		this.algorithmDisplay.registerStepListener(new StepListener());
		this.algorithmDisplay.registerAutoListener(new AutoListener());
		this.algorithmDisplay.registerUndoListener(new UndoListener());
		this.algorithmDisplay.registerResetListener(new ResetListener());
		this.algorithmDisplay.registerPauseListener(new PauseListener());
		this.algorithmDisplay.registerSkipListener(new SkipListener());
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

		for(int i=0;i<searchAlgorithm.getExpanded().size();i++) {
			algorithmDisplay.getExpandedList().get(i).setText(String.valueOf(searchAlgorithm.getExpanded().get(i).getValue()));
			algorithmDisplay.getExpandedList().get(i).setBackground(Color.yellow);
		}
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

			if(searchAlgorithm.atGoal() || searchAlgorithm.nodesUnexplored()) {
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
			LinkedList<Integer> visitedValues = new LinkedList<Integer>();
			for(int j=0;j<searchAlgorithm.getVisited().size();j++) {
				visitedValues.add(searchAlgorithm.getVisited().get(j).getValue());
			}
			algorithmDisplay.setLabelValues(expandedValues, visitedValues);
			algorithmDisplay.setLabelBackgrounds();
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

		@Override
		public void actionPerformed(ActionEvent e) {
			buttonLogic();
		}

		@Override
		protected void buttonLogic() {

			new Thread(){

				public void run() {

					while(!searchAlgorithm.atGoal() || searchAlgorithm.getExpanded().isEmpty()) {

						algorithmDisplay.toggleAuto(false);
						algorithmDisplay.toggleStep(false);
						algorithmDisplay.toggleReset(false);
						algorithmDisplay.toggleSkip(false);
						algorithmDisplay.togglePause(true);

						try {
							sleep(1000);
						} catch (InterruptedException e) {
							return;
						}

						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
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

							}
						});
					}

					algorithmDisplay.togglePause(false);

					if(searchAlgorithm.atGoal() || searchAlgorithm.nodesUnexplored()) {
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

			}.start();	
		}
	}

	private class PauseListener extends DisplayListener {

		@Override
		protected void buttonLogic() {
		
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
			searchAlgorithm.reset();
			algorithmDisplay.reset();
			initialiseExpandedList();
		}

	}

	private class SkipListener extends DisplayListener {

		@Override
		protected void buttonLogic() {
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

}
