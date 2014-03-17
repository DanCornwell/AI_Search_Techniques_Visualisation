package dac28.controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dac28.view.TopLevelContainer;


public class AppRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				}
                new TopLevelContainer();
            }
        });
	}

}
