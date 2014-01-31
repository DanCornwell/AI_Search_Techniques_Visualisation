package dac28.controller;

import java.util.LinkedList;

/**
 * The subject interface.
 * Defines methods for attaching observers and for notifying them.
 * 
 * @author Dan Cornwell
 *
 */
public abstract class Subject {
	
	/**
	 * List of observers the subject knows about
	 */
	LinkedList<Observer> observers = new LinkedList<Observer>();
	
	/**
	 * Attaches an observer to this subject.
	 * 
	 * @param observer - observer to be attached
	 */
	protected void attach(Observer observer) {
		
		observers.add(observer);
			
	}
	
	/**
	 * Notifies all observers in the subject's list
	 */
	protected void notifyObservers() {
		
		for(Observer o: observers) {
			o.update();
		}
		
	}

}
