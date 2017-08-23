
package a00698160.gis.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;
import a00698160.gis.data.Game;

/**
 * @author Jason Chan, A00698160
 *
 */
public class GameDao extends Dao {

	private static Logger LOG = LogManager.getLogger(PersonaDao.class.getName());

	public static final String TABLE_NAME = "Games";
	public static final String GAME_ID_COLUMN_NAME = "gameId";
	public static final String GAME_NAME_COLUMN_NAME = "gameName";
	public static final String PRODUCER_NAME_COLUMN_NAME = "producerName";
	public static final int MAX_GAME_ID_LENGTH = 4;
	public static final int MAX_GAME_NAME_LENGTH = 20;
	public static final int MAX_PRODUCER_NAME_NAME_LENGTH = 20;

	public GameDao(Database database) {
		super(database, TABLE_NAME);
	}

	/**
	 * Create table game.
	 * (non-Javadoc)
	 * 
	 * @see a00698160.gis.dao.Dao#create()
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		String sqlString = String.format("CREATE TABLE %s(%s VARCHAR(%d), %s VARCHAR(%d), %s VARCHAR(%d), PRIMARY KEY (%s))", TABLE_NAME, GAME_ID_COLUMN_NAME, MAX_GAME_ID_LENGTH,
				GAME_NAME_COLUMN_NAME, MAX_GAME_NAME_LENGTH, PRODUCER_NAME_COLUMN_NAME, MAX_PRODUCER_NAME_NAME_LENGTH, GAME_ID_COLUMN_NAME);

		super.create(sqlString);
	}

	/**
	 * Add a game.
	 * 
	 * @param game
	 * @throws SQLException
	 */
	public void add(Game game) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values( '%s', '%s', '%s')", TABLE_NAME, game.getId(), game.getName(), game.getProducer());
		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Added %d rows", rowCount));
	}

	/**
	 * Update the game.
	 * 
	 * @param game
	 * @throws SQLException
	 */
	public void update(Game game) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s',  WHERE %s='%d'", TABLE_NAME, GAME_ID_COLUMN_NAME, game.getId(), GAME_NAME_COLUMN_NAME,
				game.getName(), PRODUCER_NAME_COLUMN_NAME, game.getProducer());
		LOG.debug("Update statment: " + sqlString);

		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Updated %d rows", rowCount));
	}

	/**
	 * Delete the game from the database.
	 * 
	 * @param game
	 * @throws SQLException
	 */
	public void delete(Game game) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, GAME_ID_COLUMN_NAME, game.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}
}
