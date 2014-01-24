/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentflashcard;

/**
 * 
 * @author Neel
 */

public class Main extends Object implements Runnable {

	// for thread safety, we're using invoke later on main

	@Override
	public void run() {

		GUI.setNimbusLookAndFeel();

		// creating controller does everything
		Controller c = new Controller();

		// build about dialog in the background so there's no delay when the
		// user loads it
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				About.buildDialogPanel();
			}
		});
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Main());
	}
}
