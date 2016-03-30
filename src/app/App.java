/**
 * 
 */
package app;

import javax.swing.JFrame;

import app.framework.Application;
import app.view.MenuView;
import app.view.TodoView;

/**
 * @author liuzh
 *
 */
public final class App extends Application {

    /* (non-Javadoc)
	 * @see app.framework.Application#start(javax.swing.JFrame)
	 */
	@Override
	protected void start(JFrame frame)
    {
	    frame.setTitle("EV Simulator");

	    // Set the menu bar of the application frame.
	    frame.setJMenuBar(new MenuView(this).render());

	    // Render and add the main view to the application frame.
	    frame.getContentPane().add(new TodoView(this).render());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
    {
		new App();
	}

}
