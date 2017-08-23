package a00698160.gis.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;

@SuppressWarnings("serial")
public class TotalOptionPane extends JOptionPane {

	private static final Logger LOG = LogManager.getLogger(TotalOptionPane.class);

	public static final String SCORES_TABLE = "Scores";
	public static final String GAME_ID_COLUMN_NAME = "gameId";
	public static final String NUPI_GAME_ID = "nupi";
	public static final String QUFI_GAME_ID = "qufi";
	public static final String CODE_GAME_ID = "code";

	/**
	 * Create the Total OptionPane.
	 */
	public TotalOptionPane() {

		int nupiGameCount = 0;
		int codeGameCount = 0;
		int qufiGameCount = 0;

		Statement statement = null;
		ResultSet resultSet = null;

		String selectString = String.format("SELECT * FROM %s", SCORES_TABLE);

		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				if (resultSet.getString(GAME_ID_COLUMN_NAME).equalsIgnoreCase(NUPI_GAME_ID)) {
					nupiGameCount++;
				}
				if (resultSet.getString(GAME_ID_COLUMN_NAME).equalsIgnoreCase(QUFI_GAME_ID)) {
					qufiGameCount++;
				}
				if (resultSet.getString(GAME_ID_COLUMN_NAME).equalsIgnoreCase(CODE_GAME_ID)) {
					codeGameCount++;
				}
			}

			LOG.info("Accessed " + SCORES_TABLE + " data.");

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		TotalOptionPane.showMessageDialog(TotalOptionPane.this, "Number Picker " + nupiGameCount + "\nQuick Finger " + qufiGameCount + "\nColor Demon " + codeGameCount,
				"Gis Game Count", TotalOptionPane.PLAIN_MESSAGE);
	}

}
