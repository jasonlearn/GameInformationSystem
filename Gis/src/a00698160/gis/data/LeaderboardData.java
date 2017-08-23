/**
 * Project: Gis
 * File: GisReportData.java
 * Date: Oct 19, 2014
 * Time: 4:17:32 PM
 */

package a00698160.gis.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Jason Chan, A00698160
 */
public class LeaderboardData implements Comparable<LeaderboardData> {

	private static Logger LOG = LogManager.getLogger(LeaderboardData.class.getName());
	private static final int WIN_FACTOR = 2;
	private static final int LOSS_FACTOR = 1;
	private int winCount;
	private int lossCount;
	private String gameName;
	private String gamerTag;
	private String platform;
	public static final String TABLE_NAME = "LeaderboardData";
	public static final String WIN_COLUMN_NAME = "win";
	public static final String LOSS_COLUMN_NAME = "loss";
	public static final String GAME_NAME_COLUMN_NAME = "gameName";
	public static final String PLATFORM_COLUMN_NAME = "platform";
	public static final String PERSONA_ID_COLUMN_NAME = "personaId";
	public static final String GAME_ID_COLUMN_NAME = "gameId";
	public static final String GAME_WIN_COLUMN_NAME = "gameWin";
	public static final String GAMERTAG_COLUMN_NAME = "gamertag";

	/**
	 * @param winLoss
	 * @param gameName
	 * @param gamerTag
	 * @param platform
	 */
	public LeaderboardData(int winCount, int lossCount, String gameName, String gamerTag, String platform) {
		this.winCount = winCount;
		this.lossCount = lossCount;
		this.gameName = gameName;
		this.gamerTag = gamerTag;
		this.platform = platform;
	}

	/**
	 * 
	 */
	public LeaderboardData() {
	}

	/**
	 * @return the winLoss
	 */
	public int getWinLoss() {
		return winCount * WIN_FACTOR + lossCount * LOSS_FACTOR;
	}

	/**
	 * @return the winLoss
	 */
	public String getWinLossString() {
		return String.format("%d:%d", winCount, lossCount);
	}

	/**
	 * @param winLoss
	 *            the winLoss to set
	 */
	public void setWinCount(int win) {
		this.winCount = win;
	}

	/**
	 * @param winLoss
	 *            the winLoss to set
	 */
	public void setLossCount(int count) {
		this.lossCount = count;
	}

	/**
	 * @return the winCount
	 */
	public long getWinCount() {
		return winCount;
	}

	/**
	 * @return the lossCount
	 */
	public int getLossCount() {
		return lossCount;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName
	 *            the gameName to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return the gamerTag
	 */
	public String getGamerTag() {
		return gamerTag;
	}

	/**
	 * @param gamerTag
	 *            the gamerTag to set
	 */
	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LeaderboardData data) {
		return this.gameName.compareToIgnoreCase(data.gameName);
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LeaderboardData [winCount=" + winCount + ", lossCount=" + lossCount + ", gameName=" + gameName + ", gamerTag=" + gamerTag + ", platform=" + platform + "]";
	}

	/**
	 * 
	 * @return LeaderboardData
	 * @throws Exception
	 */
	public List<LeaderboardData> getLeaderboardData() throws Exception {

		List<LeaderboardData> allLeaders = new ArrayList<LeaderboardData>();

		String gamesSelectString = String.format("SELECT * FROM %s", "Games");
		String scoresSelectString = String.format("SELECT * FROM %s", "Scores");
		String personasSelectString = String.format("SELECT * FROM %s", "Personas");

		LOG.debug(gamesSelectString + " " + scoresSelectString + " " + personasSelectString);

		Statement gamesStatement = null;
		Statement scoresStatement = null;
		Statement personasStatement = null;

		ResultSet gamesResultSet = null;
		ResultSet scoresResultSet = null;
		ResultSet personasResultSet = null;

		List<Score> scoreList = new ArrayList<Score>();
		try {
			Connection scoresConnection = Database.getConnection();
			scoresStatement = scoresConnection.createStatement();
			scoresResultSet = scoresStatement.executeQuery(scoresSelectString);

			while (scoresResultSet.next()) {
				Score thisScore = new Score();
				thisScore.setPersonaId(scoresResultSet.getInt(PERSONA_ID_COLUMN_NAME));
				thisScore.setGameId(scoresResultSet.getString(GAME_ID_COLUMN_NAME));
				thisScore.setWin(scoresResultSet.getBoolean(GAME_WIN_COLUMN_NAME));
				scoreList.add(thisScore);
			}
		} finally {
			scoresStatement.close();
		}

		long pid = 0;
		String gid = null;
		String gname = null;
		String tag = null;
		String platform = null;

		for (Score s : scoreList) {

			LeaderboardData leaderData = new LeaderboardData();

			int win = 0;
			int loss = 0;

			pid = s.getPersonaId();
			gid = s.getGameId();

			for (Score winLoss : scoreList) {
				if (pid == winLoss.getPersonaId() && gid.equalsIgnoreCase(winLoss.getGameId())) {
					if (winLoss.isWin()) {
						win++;
					} else {
						loss++;
					}
				}
			}

			try {
				Connection gamesConnection = Database.getConnection();
				gamesStatement = gamesConnection.createStatement();
				gamesResultSet = gamesStatement.executeQuery(gamesSelectString);

				while (gamesResultSet.next()) {
					if (gid.equalsIgnoreCase(gamesResultSet.getString(GAME_ID_COLUMN_NAME))) {
						gname = gamesResultSet.getString(GAME_NAME_COLUMN_NAME);
					}
				}
			} finally {
				gamesStatement.close();
			}
			try {
				Connection personasConnection = Database.getConnection();
				personasStatement = personasConnection.createStatement();
				personasResultSet = personasStatement.executeQuery(personasSelectString);

				while (personasResultSet.next()) {
					if (pid == personasResultSet.getInt(PERSONA_ID_COLUMN_NAME)) {
						tag = (personasResultSet.getString(GAMERTAG_COLUMN_NAME));
						platform = (personasResultSet.getString(PLATFORM_COLUMN_NAME));
					}
				}
			} finally {
				personasStatement.close();
			}

			leaderData.setWinCount(win);
			leaderData.setLossCount(loss);
			leaderData.setGameName(gname);
			leaderData.setGamerTag(tag);
			leaderData.setPlatform(platform);

			allLeaders.add(leaderData);

		}

		return allLeaders;
	}
}
