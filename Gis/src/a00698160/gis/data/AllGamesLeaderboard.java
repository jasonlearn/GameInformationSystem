/**
 * Project: Gis
 * File: ScoreReportData.java
 * Date: Oct 19, 2014
 * Time: 3:01:59 PM
 */

package a00698160.gis.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason Chan, A00698160
 */
public class AllGamesLeaderboard {

	private Map<String, AllGamesLeaderboardData> leaderboard;

	public AllGamesLeaderboard() {
		leaderboard = new HashMap<>();
	}

	/**
	 * @param score
	 */
	public void update(Score score) {
		// generate the key
		String key = String.format("%s+%d", score.getGameId(), score.getPersonaId());
		AllGamesLeaderboardData data = leaderboard.get(key);
		if (data == null) {
			data = new AllGamesLeaderboardData(score);
			leaderboard.put(key, data);
		}

		if (score.isWin()) {
			data.updateWins();
		} else {
			data.updateLosses();
		}
	}

	public Map<String, AllGamesLeaderboardData> getLeaderboard() {
		return leaderboard;
	}

}
