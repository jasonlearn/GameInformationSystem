package a00698160.gis.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.Database;

@SuppressWarnings("serial")
public class PersonaDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(PersonaDialog.class);

	public static final String GAME_TAG_COLUMN_NAME = "gameTag";
	public static final String TABLE_NAME = "LeaderboardData";

	JTextField persona;

	JButton okButton;
	JButton cancelButton;

	JScrollPane pane;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 * 
	 * @param allLeaderList
	 * 
	 * @param selectedPersona
	 * 
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public PersonaDialog() {

		persona = new JTextField(30);

		setTitle("Gis Report - Persona Display");
		setSize(450, 300);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JPanel myPanel = new JPanel();
		getContentPane().add(myPanel);
		myPanel.setLayout(new FlowLayout());

		Statement statement = null;
		ResultSet resultSet = null;

		String selectString = String.format("SELECT * FROM %s", TABLE_NAME);

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			List<String> gamerTags = new ArrayList<String>();
			while (resultSet.next()) {
				String tag = null;
				tag = resultSet.getString(GAME_TAG_COLUMN_NAME);
				gamerTags.add(tag);
			}
			for (String tags : gamerTags) {
				persona = new JTextField(25);
				persona.setText(tags);
				persona.setEditable(false);
				persona.setFocusable(true);
				persona.addMouseListener(new CustomMouseListener());
				myPanel.add(persona);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
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
			} else if (e.getSource().equals(cancelButton)) {
				setVisible(false);
			}
		}
	}

	void eventOutput(String eventDescription, MouseEvent e) {

	}

	public class CustomMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				String clickedPersona = persona.getText();
				System.out.println(clickedPersona);
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
}
