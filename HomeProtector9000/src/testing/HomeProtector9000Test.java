package testing;

import controller.HomeProtectorFrame;
import javax.swing.JFrame;

public class HomeProtector9000Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HomeProtectorFrame application = new HomeProtectorFrame("Protect the Home");
		
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		application.setSize(application.preferredWidth(), application.preferredHeight());
		application.setResizable(false);
		application.setVisible(true);
	}

}
