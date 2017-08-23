/**
 * Project: Gis
 * File: LeaderboardDataDao.java
 * Date: Nov 24, 2015
 * Time: 5:56:49 PM
 */
package a00698160.gis.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.data.Database;
import a00698160.gis.data.LeaderboardData;

/**
 * @author Jason Chan, A00698160
 *
 */
public class LeaderboardDataDao extends Dao {

	private static Logger LOG = LogManager.getLogger(LeaderboardDataDao.class.getName());

	public static final String TABLE_NAME = "LeaderboardData";
	public static final String WIN_COLUMN_NAME = "win";
	public static final String LOSS_COLUMN_NAME = "loss";
	public static final String GAME_NAME_COLUMN_NAME = "gameName";
	public static final String GAME_TAG_COLUMN_NAME = "gameTag";
	public static final String PLATFORM_COLUMN_NAME = "platform";
	public static final int MAX_GAME_NAME_LENGTH = 20;
	public static final int MAX_GAME_TAG_LENGTH = 20;
	public static final int MAX_PLATFORM_NAME_LENGTH = 2;

	/**
	 * 
	 */
	public LeaderboardDataDao(Database database) {
		super(database, TABLE_NAME);
	}

	/**
	 * Create table leaderboard.
	 */
	@Override
	public void create() throws SQLException {
		LOG.debug("Creating database table " + TABLE_NAME);

		String sqlString = String.format("CREATE TABLE %s(%s INTEGER, %s INTEGER, %s VARCHAR(%d), %s VARCHAR(%d), %s VARCHAR(%d))", TABLE_NAME, WIN_COLUMN_NAME, LOSS_COLUMN_NAME,
				GAME_NAME_COLUMN_NAME, MAX_GAME_NAME_LENGTH, GAME_TAG_COLUMN_NAME, MAX_GAME_TAG_LENGTH, PLATFORM_COLUMN_NAME, MAX_PLATFORM_NAME_LENGTH);

		super.create(sqlString);
	}

	/**
	 * Add a leaderboard.
	 * 
	 * @param leaderboard
	 * @throws SQLException
	 */
	public void add(LeaderboardData leaderboardData) throws SQLException {
		String sqlString = String.format("INSERT INTO %s values( %d, %d, '%s', '%s', '%s')", TABLE_NAME, leaderboardData.getWinCount(), leaderboardData.getLossCount(),
				leaderboardData.getGameName(), leaderboardData.getGamerTag(), leaderboardData.getPlatform());
		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Added %d rows", rowCount));
	}

	/**
	 * Update the leaderboard.
	 * 
	 * @param leaderboard
	 * @throws SQLException
	 */
	public void update(LeaderboardData leaderboardData) throws SQLException {
		String sqlString = String.format("UPDATE %s SET %d='%d', %d='%d', %s='%s', %s='%s',%s='%s',  WHERE %s='%s' AND %s='%s'", TABLE_NAME, WIN_COLUMN_NAME,
				leaderboardData.getWinCount(), LOSS_COLUMN_NAME, leaderboardData.getLossCount(), GAME_NAME_COLUMN_NAME, leaderboardData.getGameName(), GAME_TAG_COLUMN_NAME,
				leaderboardData.getGamerTag(), PLATFORM_COLUMN_NAME, leaderboardData.getPlatform(), GAME_NAME_COLUMN_NAME, leaderboardData.getGameName(), GAME_TAG_COLUMN_NAME,
				leaderboardData.getGamerTag());
		LOG.debug("Update statment: " + sqlString);

		int rowCount = super.add(sqlString);
		LOG.debug(String.format("Updated %d rows", rowCount));
	}

	/**
	 * Delete the leaderboard from the database.
	 * 
	 * @param leaderboard
	 * @throws SQLException
	 */
	public void delete(LeaderboardData leaderboardData) throws SQLException {
		Connection connection;
		Statement statement = null;
		try {
			connection = Database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sqlString = String.format("DELETE FROM %s WHERE %s='%s' AND %s='%s'", TABLE_NAME, GAME_NAME_COLUMN_NAME, leaderboardData.getGameName(), GAME_TAG_COLUMN_NAME,
					leaderboardData.getGamerTag());
			LOG.debug(sqlString);
			int rowcount = statement.executeUpdate(sqlString);
			LOG.debug(String.format("Deleted %d rows", rowcount));
		} finally {
			close(statement);
		}
	}
}
