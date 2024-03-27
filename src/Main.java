import javax.swing.SwingUtilities;
import classi.LoginPrincipale;

public class Main {

	public static void main(String[] args) {
//		new LoginPrincipale();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				LoginPrincipale gui = new LoginPrincipale();
				gui.setVisible(true);
				
			}
		});
	}

}
