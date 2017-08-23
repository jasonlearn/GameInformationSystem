
package a00698160.gis.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;
import a00698160.gis.data.Persona;

/**
 * @author Jason Chan, A00698160
 *
 */
public class PersonaDao extends Dao {

	private static Logger LOG = LogManager.getLogger(PersonaDao.class.getName());

	public static final String TABLE_NAME = "Personas";
	public static final String PERSONA_ID_COLUMN_NAME = "personaId";
	public static final String PLAYER_ID_COLUMN_NAME = "playerId";
	public static final String GAMERTAG_COLUMN_NAME = "gamertag";
	public static final String PLATFORM_COLUMN_NAME = "platform";
	public static final int MAX_GAMERTAG_LENGTH = 20;
	public static final int MAX_PLATFORM_LENGTH = 2;

	public PersonaDao(Database database) {
		super(database, TABLE_NAME);
	}

	/**
	 * Create table persona.
	 * (non-Javadoc)
	 * 
	 * @see a00698160.gis.dao.Dao#create()
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		String sqlString = String.format("CREATE TABLE %s(%s INTEGER, %s INTEGER, %s VARCHAR(%d), %s VARCHAR(%d), PRIMARY KEY (%s))", TABLE_NAME, PERSONA_ID_COLUMN_NAME,
				PLAYER_ID_COLUMN_NAME, GAMERTAG_COLUMN_NAME, MAX_GAMERTAG_LENGTH, PLATFORM_COLUMN_NAME, MAX_PLATFORM_LENGTH, PERSONA_ID_COLUMN_NAME);

		super.create(sqlString);
	}

	/**
	 * Add a persona.
	 * 
	 * @param persona
	 * @throws SQLException
	 */
	public void add(Persona persona) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values(%d, %d, '%s', '%s')", TABLE_NAME, persona.getId(), persona.getPlayerId(), persona.getGamerTag(),
				persona.getPlatform());
		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Added %d rows", rowCount));
	}

	/**
	 * Update the persona.
	 * 
	 * @param persona
	 * @throws SQLException
	 */
	public void update(Persona persona) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s',  WHERE %s='%d'", TABLE_NAME, PERSONA_ID_COLUMN_NAME, persona.getId(),
				PLAYER_ID_COLUMN_NAME, persona.getPlayerId(), GAMERTAG_COLUMN_NAME, persona.getGamerTag(), PLATFORM_COLUMN_NAME, persona.getPlatform());
		LOG.debug("Update statment: " + sqlString);

		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Updated %d rows", rowCount));
	}

	/**
	 * Delete the persona from the database.
	 * 
	 * @param persona
	 * @throws SQLException
	 */
	public void delete(Persona persona) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("DELETE FROM %s WHERE %s='%s'", TABLE_NAME, PERSONA_ID_COLUMN_NAME, persona.getId());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}
}
