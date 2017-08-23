package a00698160.gis.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.LeaderboardData;

@SuppressWarnings("serial")
public class GamertagDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(ByGameDialog.class);

	private final JPanel contentPanel = new JPanel();

	public static final String HEADING = "WIN:LOSS - GAMENAME - GAMERTAG - PLATFORM";

	public boolean validGamerTag;

	JButton okButton;
	JButton cancelButton;
	@SuppressWarnings("rawtypes")
	JList list;
	@SuppressWarnings("rawtypes")
	DefaultListModel model;
	int leaderboardCounter = 0;

	/**
	 * Create the dialog.
	 * 
	 * @param allLeaderList
	 * @param gamerTag
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GamertagDialog(String gamerTag, List<LeaderboardData> allLeaderList) {

		{
			setTitle("Gis Report - All Games listed by Game Name");
			setSize(450, 300);
			setLocationRelativeTo(null);

			getContentPane().setLayout(new BorderLayout());
			contentPanel.setLayout(new FlowLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);

			model = new DefaultListModel();
			list = new JList(model);
			JScrollPane pane = new JScrollPane(list);
			add(pane);
		}
		long win = 0;
		long loss = 0;
		String gameName = null;
		String gameTag = null;
		String platform = null;

		for (LeaderboardData list : allLeaderList) {
			if (gamerTag.isEmpty() || gamerTag.equalsIgnoreCase(list.getGamerTag())) {
				validGamerTag = true;
				break;
			} else {
				validGamerTag = false;
			}
		}
		if (validGamerTag) {
			model.addElement(HEADING);
			for (LeaderboardData leaderList : allLeaderList) {
				win = leaderList.getWinCount();
				loss = leaderList.getLossCount();
				gameName = leaderList.getGameName();
				gameTag = leaderList.getGamerTag();
				platform = leaderList.getPlatform();
				if (gamerTag.isEmpty()) {
					model.addElement(win + ":" + loss + " " + gameName + " " + gameTag + " " + platform);
				} else if (gamerTag.equalsIgnoreCase(leaderList.getGamerTag())) {
					model.addElement(win + ":" + loss + " " + gameName + " " + gameTag + " " + platform);
				}
			}
		} else {
			LOG.info("Warning message  initiated - user entered invalid gamertag: \"" + gamerTag + "\"");
			JOptionPane.showMessageDialog(GamertagDialog.this, gamerTag + " was not found in the database", "Gamer Tag search error", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new MenuHandler());
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new MenuHandler());
				buttonPane.add(cancelButton);
			}
		}
	}

	public class MenuHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(okButton)) {
				setVisible(false);
				LOG.info("Ok button clicked, GamerTag window closed");
			} else if (e.getSource().equals(cancelButton)) {
				setVisible(false);
				LOG.info("Ok button clicked, GamerTag window closed");
			}
		}
	}

}
