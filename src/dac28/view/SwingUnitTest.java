package dac28.view;

import junit.framework.TestCase;

import org.uispec4j.UISpec4J;

/**
 * Defines interface needed to test swing components with UISpec4J.
 * See http://www.uispec4j.org/getting-started for information on UISpec4J.
 * 
 * @author Dan Cornwell
 *
 */
abstract class SwingUnitTest extends TestCase {


	/**
	 * Initialises the class for use in swing unit testing.
	 */
	static{
		UISpec4J.init();
	}


}
