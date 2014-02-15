package dac28.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import dac28.model.SearchAlgorithm;
import dac28.view.AlgorithmDisplay;

public class AlgorithmController {

	private AlgorithmDisplay algorithmDisplay;
	private SearchAlgorithm searchAlgorithm;
	
	AlgorithmController(SearchAlgorithm searchAlgorithm,AlgorithmDisplay algorithmDisplay) {
		
		this.searchAlgorithm = searchAlgorithm;
		this.algorithmDisplay = algorithmDisplay;
		algorithmDisplay.registerStepListener(new StepListener());
		algorithmDisplay.registerAutoListener(new AutoListener());
		algorithmDisplay.registerUndoListener(new UndoListener());
		algorithmDisplay.registerResetListener(new ResetListener());
	}
	
	/**
	 * Listener for the step button.
	 * Listens to the step button and responds accordingly.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class StepListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
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
	 * Listener for the auto button.
	 * Listens to the auto button and responds accordingly.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class AutoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	
	/**
	 * Listener for the undo button.
	 * Listens to the undo button and responds accordingly.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class UndoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	
	/**
	 * Listener for the reset button.
	 * Listens to the reset button and responds accordingly.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class ResetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	
}
