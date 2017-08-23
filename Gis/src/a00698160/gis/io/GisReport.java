/**
 * Project: Gis
 * File: GisReport.java
 * Date: Oct 19, 2014
 * Time: 1:49:24 PM
 */

package a00698160.gis.io;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import a00698160.gis.data.AllData;
import a00698160.gis.data.AllGamesLeaderboardData;
import a00698160.gis.data.Game;
import a00698160.gis.data.LeaderboardData;
import a00698160.gis.data.Options;
import a00698160.gis.data.Persona;
import a00698160.gis.util.LeaderboardDataByCount;
import a00698160.gis.util.LeaderboardDataByCountDesc;
import a00698160.gis.util.LeaderboardDataByGame;
import a00698160.gis.util.LeaderboardDataByGameDesc;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class GisReport {

	private static final String HEADER = "Win:Loss Game Name          Gamertag        Platform";
	private static final String SEPARATOR = "----------------------------------------------------";
	private List<LeaderboardData> leaderboard = new ArrayList<>();

	/**
	 * @param allData
	 */
	public GisReport(AllData allData) {
		leaderboard = new ArrayList<>();
		for (AllGamesLeaderboardData data : allData.getAllGamesLeaderboard().getLeaderboard().values()) {
			Game game = allData.getGames().get(data.getGameId());
			Persona persona = allData.getPersonas().get(data.getPersonaId());
			LeaderboardData leaderboardData = new LeaderboardData(data.getWins(), data.getLosses(), game.getName(), persona.getGamerTag(), persona.getPlatform());
			leaderboard.add(leaderboardData);
		}
	}

	/**
	 * Prints the Game Information System report, which is effectively a leaderboard. An interesting fact is that
	 * OutputStream, which is the parent of FilterOutputStream, which is the parent of PrintStream, was written by
	 * Arthur van Hoff. See http://en.wikipedia.org/wiki/Arthur_van_Hoff for an interesting story of where the guy who
	 * wrote OutputStream.java went.
	 * 
	 * @param out
	 * @param options
	 */
	public void print(PrintStream out, Options options) {
		boolean naturalOrder = true;
		if (options.isSortByCountOptionSet() || options.isSortByGameOptionSet()) {
			naturalOrder = false;
		}

		Comparator<LeaderboardData> comparable = null;
		if (naturalOrder) {
			Collections.sort(leaderboard);
		} else if (options.isSortByCountOptionSet()) {
			if (options.isSortDescendingOptionSet()) {
				comparable = new LeaderboardDataByCountDesc();
			} else {
				comparable = new LeaderboardDataByCount();
			}
		} else if (options.isSortByGameOptionSet()) {
			if (options.isSortDescendingOptionSet()) {
				comparable = new LeaderboardDataByGameDesc();
			} else {
				comparable = new LeaderboardDataByGame();
			}
		}

		if (comparable != null) {
			Collections.sort(leaderboard, comparable);
		}

		out.println(HEADER);
		out.println(SEPARATOR);

		Map<String, Integer> totals = new HashMap<>();

		for (LeaderboardData row : leaderboard) {
			String gameName = row.getGameName();
			if (!totals.containsKey(gameName)) {
				totals.put(gameName, 0);
			}
			int value = totals.get(gameName);

			if (options.getPlatform() == null) {
				out.println(String.format("%-8s %-18s %-15s %s", row.getWinLossString(), row.getGameName(), row.getGamerTag(), row.getPlatform()));
				totals.put(gameName, value += (row.getWinCount() + row.getLossCount()));
			} else {
				if (options.getPlatform().equals(row.getPlatform())) {
					out.println(String.format("%-8s %-18s %-15s %s", row.getWinLossString(), row.getGameName(), row.getGamerTag(), row.getPlatform()));
					totals.put(gameName, value += (row.getWinCount() + row.getLossCount()));
				}
			}
		}

		if (options.isPrintTotalOptionSet()) {
			out.println(SEPARATOR);
			for (Entry<String, Integer> entry : totals.entrySet()) {
				out.println(String.format("%-18s %d", entry.getKey(), entry.getValue()));
			}
		}

		out.println(SEPARATOR);
	}

}
