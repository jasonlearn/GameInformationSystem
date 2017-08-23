
package a00698160.gis.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;
import a00698160.gis.data.Player;

/**
 * @author Jason Chan, A00698160
 *
 */
public class PlayerDao extends Dao {

	private static Logger LOG = LogManager.getLogger(PlayerDao.class.getName());

	public static final String TABLE_NAME = "Players";
	public static final String ID_COLUMN_NAME = "id";
	public static final String FIRSTNAME_COLUMN_NAME = "firstName";
	public static final String LASTNAME_COLUMN_NAME = "lastName";
	public static final String EMAIL_ADDRESS_COLUMN_NAME = "emailAddress";
	public static final String BIRTHDATE_COLUMN_NAME = "birthDate";
	public static final int MAX_FIRSTNAME_LENGTH = 40;
	public static final int MAX_LASTNAME_LENGTH = 40;
	public static final int MAX_EMAIL_ADDRESS_LENGTH = 40;
	public static final int MAX_GAMERTAG_LENGTH = 20;

	public PlayerDao(Database database) {
		super(database, TABLE_NAME);
	}

	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		String sqlString = String.format("CREATE TABLE %s(%s INTEGER, %s VARCHAR(%d), %s VARCHAR(%d), %s VARCHAR(%d), %s TIMESTAMP, PRIMARY KEY (%s))", TABLE_NAME, ID_COLUMN_NAME,
				FIRSTNAME_COLUMN_NAME, MAX_FIRSTNAME_LENGTH, LASTNAME_COLUMN_NAME, MAX_LASTNAME_LENGTH, EMAIL_ADDRESS_COLUMN_NAME, MAX_EMAIL_ADDRESS_LENGTH, BIRTHDATE_COLUMN_NAME,
				ID_COLUMN_NAME);

		super.create(sqlString);
	}

	public void add(Player player) throws SQLException {
		ZonedDateTime time = player.getBirthDate();
		String sqlString = String.format("INSERT INTO %s values(%d, '%s', '%s', '%s', '%s')", TABLE_NAME, player.getId(), player.getFirstName(), player.getLastName(),
				player.getEmailAddress(), toTimestamp(time));
		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Added %d rows", rowCount));
	}

	/**
	 * Update the player.
	 * 
	 * @param player
	 * @throws SQLException
	 */
	public void update(Player player) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s',  WHERE %s='%d'", TABLE_NAME, FIRSTNAME_COLUMN_NAME, player.getFirstName(),
				LASTNAME_COLUMN_NAME, player.getLastName(), EMAIL_ADDRESS_COLUMN_NAME, player.getEmailAddress(), ID_COLUMN_NAME, player.getId());
		LOG.debug("Update statment: " + sqlString);

		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Updated %d rows", rowCount));
	}

	/**
	 * Delete the player from the database.
	 * 
	 * @param player
	 * @throws SQLException
	 */
	public void delete(Player player) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, ID_COLUMN_NAME, player.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}

	/**
	 * Retrieve all the players from the database
	 *
	 * @return
	 * @throws SQLException
	 */
	public List<String> getPlayer() throws SQLException {
		List<String> playerNames = new ArrayList<String>();

		String selectString = String.format("SELECT * FROM %s", TABLE_NAME);
		LOG.debug(selectString);

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);

			while (resultSet.next()) {
				String currentPlayerFirstName = resultSet.getString(FIRSTNAME_COLUMN_NAME);
				String currentPlayerLastName = resultSet.getString(LASTNAME_COLUMN_NAME);
				playerNames.add(currentPlayerFirstName + " " + currentPlayerLastName);
			}

		} finally {
			close(statement);
		}

		LOG.debug(String.format("Loaded %d gamer tags from the database", playerNames.size()));

		return playerNames;
	}

	// /**
	// * Retrieve all the gamertags from the database
	// *
	// * @return
	// * @throws SQLException
	// */
	// public List<String> getGamertags() throws SQLException {
	// List<String> gamerTags = new ArrayList<String>();
	//
	// String selectString = String.format("SELECT * FROM %s", TABLE_NAME);
	// LOG.debug(selectString);
	//
	// Statement statement = null;
	// ResultSet resultSet = null;
	// try {
	// Connection connection = Database.getConnection();
	// statement = connection.createStatement();
	// resultSet = statement.executeQuery(selectString);
	//
	// while (resultSet.next()) {
	// gamerTags.add(resultSet.getString(GAMERTAG_COLUMN_NAME));
	// }
	//
	// } finally {
	// close(statement);
	// }
	//
	// LOG.debug(String.format("Loaded %d gamer tags from the database", gamerTags.size()));
	//
	// return gamerTags;
	// }

	// /**
	// * @param actionCommand
	// * @return
	// * @throws ApplicationException
	// */
	// public Player getPlayer(String gamerTag) throws Exception {
	// String selectString = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, GAMERTAG_COLUMN_NAME, gamerTag);
	// LOG.debug(selectString);
	//
	// Statement statement = null;
	// ResultSet resultSet = null;
	// try {
	// Connection connection = Database.getConnection();
	// statement = connection.createStatement();
	// resultSet = statement.executeQuery(selectString);
	//
	// int count = 0;
	// while (resultSet.next()) {
	// count++;
	// if (count > 1) {
	// throw new ApplicationException(String.format("Expected one result, got %d", count));
	// }
	//
	// Player player = new Player();
	// player.setId(resultSet.getInt(ID_COLUMN_NAME));
	// player.setFirstName(resultSet.getString(FIRSTNAME_COLUMN_NAME));
	// player.setLastName(resultSet.getString(LASTNAME_COLUMN_NAME));
	// player.setEmailAddress(resultSet.getString(EMAIL_ADDRESS_COLUMN_NAME));
	// Timestamp timestamp = resultSet.getTimestamp(BIRTHDATE_COLUMN_NAME);
	// ZonedDateTime dateTime = ZonedDateTime.of(timestamp.toLocalDateTime(), ZoneId.systemDefault());
	// player.setBirthDate(dateTime);
	//
	// return player;
	// }
	// } finally {
	// close(statement);
	// }
	//
	// return null;
	// }

}
