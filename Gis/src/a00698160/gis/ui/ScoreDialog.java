package a00698160.gis.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.Database;

@SuppressWarnings("serial")
public class ScoreDialog extends JDialog {

	private static final Logger LOG = LogManager.getLogger(ScoreDialog.class);

	public final String TABLE_NAME = "Scores";
	public static final String PERSONA_ID_COLUMN_NAME = "personaId";
	public static final String GAME_ID_COLUMN_NAME = "gameId";
	public static final String GAME_WIN_COLUMN_NAME = "gameWin";

	JButton okButton;
	JButton cancelButton;

	private JScrollPane scrollPane;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the score dialog.
	 * 
	 * @param list
	 * 
	 * 
	 * @throws SQLException
	 * @throws ApplicationException
	 */
	public ScoreDialog() {

		setTitle("Game Information System Score Display");
		setSize(450, 300);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		{
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BorderLayout());
			getContentPane().add(myPanel);
			{
				table = new JTable(0, 3);
				scrollPane = new JScrollPane(table);
				myPanel.add(scrollPane, BorderLayout.CENTER);
				table.getColumnModel().getColumn(0).setHeaderValue("Persona Id");
				table.getColumnModel().getColumn(1).setHeaderValue("Game Id");
				table.getColumnModel().getColumn(2).setHeaderValue("Game Result");

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				{
					Statement statement = null;
					ResultSet resultSet = null;

					String selectString = String.format("SELECT * FROM %s", TABLE_NAME);

					try {
						Connection connection = Database.getConnection();
						statement = connection.createStatement();
						resultSet = statement.executeQuery(selectString);

						int numberOfScores = 0;
						while (resultSet.next()) {
							String p_id = resultSet.getString(PERSONA_ID_COLUMN_NAME);
							String g_id = resultSet.getString(GAME_ID_COLUMN_NAME);
							String win = resultSet.getString(GAME_WIN_COLUMN_NAME);
							if (win.equalsIgnoreCase("true")) {
								win = "WIN";
							} else {
								win = "LOSS";
							}
							model.insertRow(table.getRowCount(), new Object[] { p_id, g_id, win });
							numberOfScores++;
						}

						LOG.info("Loaded " + numberOfScores + " into the ScoreDialog table");

					} catch (SQLException e) {
						LOG.error(e.getMessage());
					}
				}
			}

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
