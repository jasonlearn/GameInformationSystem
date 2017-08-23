
package a00698160.gis.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;
import a00698160.gis.data.Score;

/**
 * @author Jason Chan, A00698160
 *
 */
public class ScoreDao extends Dao {

	private static Logger LOG = LogManager.getLogger(PersonaDao.class.getName());

	public static final String TABLE_NAME = "Scores";
	public static final String PERSONA_ID_COLUMN_NAME = "personaId";
	public static final String GAME_ID_COLUMN_NAME = "gameId";
	public static final String GAME_WIN_COLUMN_NAME = "gameWin";
	public static final int MAX_PERSONA_ID_COLUMN_NAME = 100;
	public static final int MAX_GAME_ID_LENGTH = 4;

	public ScoreDao(Database database) {
		super(database, TABLE_NAME);
	}

	/**
	 * Create table score.
	 * (non-Javadoc)
	 * 
	 * @see a00698160.gis.dao.Dao#create()
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		String sqlString = String.format("CREATE TABLE %s(%s VARCHAR(%d), %s VARCHAR(%d), %s BOOLEAN)", TABLE_NAME, PERSONA_ID_COLUMN_NAME, MAX_PERSONA_ID_COLUMN_NAME,
				GAME_ID_COLUMN_NAME, MAX_GAME_ID_LENGTH, GAME_WIN_COLUMN_NAME);

		super.create(sqlString);
	}

	/**
	 * Add a score.
	 * 
	 * @param score
	 * @throws SQLException
	 */
	public void add(Score score) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values( '%d', '%s', '%b' )", TABLE_NAME, score.getPersonaId(), score.getGameId(), Boolean.valueOf(score.isWin()));
		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Added %d rows", rowCount));
	}

	/**
	 * Update the score.
	 * 
	 * @param score
	 * @throws SQLException
	 */
	public void update(Score score) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %d='%d', %s='%s', %b='%b',  WHERE %s='%d' AND %s='%d'", TABLE_NAME, PERSONA_ID_COLUMN_NAME, score.getPersonaId(),
				GAME_ID_COLUMN_NAME, score.getGameId(), GAME_WIN_COLUMN_NAME, score.isWin());
		LOG.debug("Update statment: " + sqlString);

		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Updated %d rows", rowCount));
	}

	/**
	 * Delete the score from the database.
	 * 
	 * @param score
	 * @throws SQLException
	 */
	public void delete(Score score) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("DELETE FROM %s WHERE %d='%d' AND %s='%s'", TABLE_NAME, PERSONA_ID_COLUMN_NAME, score.getPersonaId(), GAME_ID_COLUMN_NAME,
					score.getGameId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}
}
