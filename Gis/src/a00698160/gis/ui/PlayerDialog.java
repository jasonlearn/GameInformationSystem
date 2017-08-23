package a00698160.gis.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.Database;

@SuppressWarnings("serial")
public class PlayerDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(PlayerDialog.class);

	public static final String TABLE_NAME = "Players";

	public static final String FIRSTNAME_COLUMN_NAME = "firstName";
	public static final String LASTNAME_COLUMN_NAME = "lastName";

	List<String> allNames;
	JTextField name;
	JTextField heading;

	JButton okButton;
	JButton cancelButton;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 * 
	 * @param list
	 * 
	 * @param playerNameList
	 * 
	 * 
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public PlayerDialog() {

		setTitle("Game Information System Player List Display");
		setSize(450, 300);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JPanel myPanel = new JPanel();
		getContentPane().add(myPanel);
		myPanel.setLayout(new FlowLayout());

		heading = new JTextField(25);
		heading.setEnabled(false);
		myPanel.add(heading);
		heading.setFont(new Font(Font.DIALOG, Font.ITALIC, 12));
		String fullName = "First Name Last Name";
		heading.setText(fullName);

		Statement statement = null;
		ResultSet resultSet = null;

		String selectString = String.format("SELECT * FROM %s", TABLE_NAME);

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			List<String> allNames = new ArrayList<String>();
			String firstName = null;
			String lastName = null;
			while (resultSet.next()) {
				firstName = resultSet.getString(FIRSTNAME_COLUMN_NAME);
				lastName = resultSet.getString(LASTNAME_COLUMN_NAME);
				allNames.add(firstName + " " + lastName);
			}

			int playerCount = 0;
			for (String aName : allNames) {
				name = new JTextField(25);
				myPanel.add(name);
				name.setText(aName);
				playerCount++;
			}

			LOG.info("Loaded " + playerCount + " players onto the PlayerDialog");

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

}
