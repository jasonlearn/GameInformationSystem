/**
 * Project: Gis
 * File: PlayersReport.java
 * Date: Oct 19, 2014
 * Time: 1:47:36 PM
 */

package a00698160.gis.io;

import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.Gis;
import a00698160.gis.data.AllData;
import a00698160.gis.data.Persona;
import a00698160.gis.data.Player;
import a00698160.gis.data.PlayerReportData;
import a00698160.gis.data.Score;

/**
 * @author Fred Fish, A00123456
 *
 */
public class PlayersReport {

	private static final Logger LOG = LogManager.getLogger(Gis.class);
	private static final String HEADER = "Player ID  Full name            Email                     Age  Total games played   Total Wins";
	private static final String SEPARATOR = "----------------------------------------------------------------------------------------------";
	private List<PlayerReportData> playerReportItems;

	/**
	 * Generate the lines for the player report.
	 * 
	 * @param allData
	 */
	public PlayersReport(AllData allData) {
		playerReportItems = new ArrayList<>();
		for (Player player : allData.getPlayers().values()) {
			PlayerReportData data = new PlayerReportData();
			data.setId(player.getId());
			data.setName(player.getFirstName() + " " + player.getLastName());
			data.setEmailAddress(player.getEmailAddress());
			ZonedDateTime birthdate = player.getBirthDate();

			data.setAge(calculateAge(birthdate));

			int wins = 0;
			int gamesPlayed = 0;
			
			for (Persona persona : allData.getPersonas().values()) {
				if (persona.getPlayerId() == player.getId()) {
					List<Score> scores = persona.getGamesPlayed();
					for (Score score : scores) {
						LOG.debug(score);
						gamesPlayed++;
						wins += score.isWin() ? 1 : 0;
					}

					data.setTotalGamesPlayed(gamesPlayed);
					data.setTotalWins(wins);
				}
			}

			playerReportItems.add(data);
		}

	}

	/**
	 * Prints the Game Information System report, which is effectively a leaderboard. An interesting fact is that
	 * OutputStream, which is the parent of FilterOutputStream, which is the parent of PrintStream, was written by
	 * Arthur van Hoff. See http://en.wikipedia.org/wiki/Arthur_van_Hoff for an interesting story of where the guy who
	 * wrote OutputStream.java went.
	 * 
	 * @param out
	 * @param gis
	 */
	public void print(PrintStream out, Gis gis) {
		out.println(HEADER);
		out.println(SEPARATOR);

		for (PlayerReportData data : playerReportItems) {
			out.println(String.format("%9d  %-20s %-25s %3d %19d %12d", data.getId(), data.getName(), data.getEmailAddress(),
					data.getAge(), data.getTotalGamesPlayed(), data.getTotalWins()));
		}
		out.println(SEPARATOR);
	}

	/**
	 * Calculate the age of a person given their date of birth
	 * 
	 * @param date the person's date of birth
	 * @return the age
	 */
	private static long calculateAge(ZonedDateTime date) {
		return ChronoUnit.YEARS.between(date, ZonedDateTime.now());
	}
}
