package a00698160.gis.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.LeaderboardData;
import a00698160.gis.util.LeaderboardDataByCount;
import a00698160.gis.util.LeaderboardDataByCountDesc;
import a00698160.gis.util.LeaderboardDataByGame;
import a00698160.gis.util.LeaderboardDataByGameDesc;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static final Logger LOG = LogManager.getLogger(MainFrame.class);
	private JPanel contentPane;
	private JMenuItem quit;
	private JMenuItem players;
	private JMenuItem personas;
	private JMenuItem scores;
	private JMenuItem total;
	private JCheckBoxMenuItem descending;
	private JMenuItem byGame;
	private JMenuItem byCount;
	private JMenuItem gamertag;
	private JMenuItem about;
	private boolean isDescending;

	public List<LeaderboardData> allLeaderList;

	/**
	 * Create the frame.
	 * 
	 * @param playerList
	 */
	public MainFrame(List<LeaderboardData> listOfLeaders) {

		allLeaderList = listOfLeaders;

		setTitle("Game Information System");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(450, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();

		contentPane.setBorder(new LineBorder(Color.WHITE, 4));

		setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[]", "[]"));

		JMenuBar mainMenuBar = new JMenuBar();
		setJMenuBar(mainMenuBar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		mainMenuBar.add(fileMenu);

		JMenu listMenu = new JMenu("Lists");
		listMenu.setMnemonic('L');
		mainMenuBar.add(listMenu);

		JMenu reportMenu = new JMenu("Reports");
		reportMenu.setMnemonic('R');
		mainMenuBar.add(reportMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		mainMenuBar.add(helpMenu);

		// menu items under File
		quit = new JMenuItem("Quit", KeyEvent.VK_Q);
		quit.addActionListener(new MenuHandler());
		fileMenu.add(quit);

		// menu items under Lists
		players = new JMenuItem("Players", KeyEvent.VK_P);
		players.addActionListener(new MenuHandler());
		listMenu.add(players);

		personas = new JMenuItem("Personas", KeyEvent.VK_E);
		personas.addActionListener(new MenuHandler());
		listMenu.add(personas);

		scores = new JMenuItem("Scores", KeyEvent.VK_S);
		scores.addActionListener(new MenuHandler());
		listMenu.add(scores);

		// menu items under Reports
		total = new JMenuItem("Total", KeyEvent.VK_L);
		total.addActionListener(new MenuHandler());
		reportMenu.add(total);

		descending = new JCheckBoxMenuItem("Descending");
		descending.addItemListener(new ItemHandler());
		descending.setMnemonic(KeyEvent.VK_D);
		reportMenu.add(descending);

		byGame = new JMenuItem("By Game", KeyEvent.VK_G);
		byGame.addActionListener(new MenuHandler());
		reportMenu.add(byGame);

		byCount = new JMenuItem("By Count", KeyEvent.VK_C);
		byCount.addActionListener(new MenuHandler());
		reportMenu.add(byCount);

		gamertag = new JMenuItem("Gamertag", KeyEvent.VK_T);
		gamertag.addActionListener(new MenuHandler());
		reportMenu.add(gamertag);

		// menu items under Help
		about = new JMenuItem("About", (KeyEvent.VK_A));
		about.addActionListener(new MenuHandler());
		helpMenu.add(about);

	}

	public boolean getIsDescending() {
		return isDescending;
	}

	public void setIsDecending() {
		isDescending = false;
	}

	public class MenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(quit)) {
				LOG.info("Exited MainFrame");
				System.exit(0);
			} else if (e.getSource().equals(players)) {
				PlayerDialog playerDisplay = new PlayerDialog();
				playerDisplay.setVisible(true);
				LOG.info("Initiating PlayerDialog.");
			} else if (e.getSource().equals(personas)) {
				PersonaDialog personaDisplay = new PersonaDialog();
				personaDisplay.setVisible(true);
			} else if (e.getSource().equals(scores)) {
				ScoreDialog scoreDisplay = new ScoreDialog();
				scoreDisplay.setVisible(true);
				LOG.info("Initiating Score Dialog.");
			} else if (e.getSource().equals(total)) {
				new TotalOptionPane();
				LOG.info("Initiating Total Games played of each game.");
			} else if (e.getSource().equals(byGame)) {
				Comparator<LeaderboardData> comparable = null;
				if (getIsDescending()) {
					comparable = new LeaderboardDataByGameDesc();
					Collections.sort(allLeaderList, comparable);
					LOG.info("Initiating ByGameDialog with Descending option.");
				} else {
					comparable = new LeaderboardDataByGame();
					Collections.sort(allLeaderList, comparable);
					LOG.info("Initiating ByGameDialog with Asscending option.");
				}
				ByGameDialog byGameDisplay = new ByGameDialog(allLeaderList);
				byGameDisplay.setVisible(true);
			} else if (e.getSource().equals(byCount)) {
				Comparator<LeaderboardData> comparable = null;
				if (getIsDescending()) {
					comparable = new LeaderboardDataByCountDesc();
					Collections.sort(allLeaderList, comparable);
					LOG.info("Initiating ByCountDialog with Descending option.");
				} else {
					comparable = new LeaderboardDataByCount();
					Collections.sort(allLeaderList, comparable);
					LOG.info("Initiating ByCountDialog with Asscending option.");
				}
				ByCountDialog byCountDisplay = new ByCountDialog(allLeaderList);
				byCountDisplay.setVisible(true);
			} else if (e.getSource().equals(gamertag)) {
				String gamerTag = JOptionPane.showInputDialog("Enter a gamer tag");
				GamertagDialog gamertag = new GamertagDialog(gamerTag, allLeaderList);
				gamertag.setVisible(true);
				LOG.info("Initiating GamerTag search dialog.");
			} else if (e.getSource().equals(about)) {
				JOptionPane.showMessageDialog(MainFrame.this, "Game Information System\nBy Jason Chan A00698160", "About Gis", JOptionPane.INFORMATION_MESSAGE);
				LOG.info("Initiating About dialog.");
			}
		}
	}

	public class ItemHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			AbstractButton button = (AbstractButton) e.getItem();
			if (button.isSelected()) {
				isDescending = true;
				LOG.info("Descending option checked");
			} else {
				isDescending = false;
				System.out.println("Descending option unchecked");
			}
		}
	}
	// public void keyPressed(KeyEvent e) {
	// AWTKeyStroke ak = AWTKeyStroke.getAWTKeyStrokeForEvent(e);
	// if (ak.equals(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_DOWN_MASK))) {
	// System.exit(0);
	// LOG.info("Exited MainFrame with Alt + Q.");
	// }
	// }

}
