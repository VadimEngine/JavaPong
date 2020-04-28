import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;


public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	int screenWidth = 275;
	int screenHeight = 200;

	int buttonWidth = 100;
	int buttonHeight = 40;

	JButton Play, Quit;
	JCheckBox twoPlayers, limitFrameRate;

	public MainMenu() {
		getContentPane().setLayout(null);

		addButtons();
		addActions();

		Play.setBounds((screenWidth - buttonWidth) / 2, 5, buttonWidth, buttonHeight);
		Quit.setBounds((screenWidth - buttonWidth) / 2, 50, buttonWidth, buttonHeight);
		twoPlayers.setBounds(0, 95, buttonWidth, buttonHeight);
		limitFrameRate.setBounds(0, 140, buttonWidth * 3, buttonHeight);
		
		getContentPane().add(Play);
		getContentPane().add(Quit);
		getContentPane().add(twoPlayers);
		getContentPane().add(limitFrameRate);
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(screenWidth, screenHeight);
		setTitle("pong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	private void addButtons() {
		Play = new JButton("Play");
		Quit = new JButton("Quit");
		twoPlayers = new JCheckBox("2 Players?");
		limitFrameRate = new JCheckBox("Limit Frames/Seconds to Update/Seconds?");

	}

	private void addActions() {

	}
}



