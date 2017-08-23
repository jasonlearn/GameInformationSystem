/**
 * 
 */
package a00698160.gis.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.io.GameReader;
import a00698160.gis.io.PersonaReader;
import a00698160.gis.io.PlayerReader;
import a00698160.gis.io.ScoreReader;

/**
 * @author Jason Chan, A00698160
 *
 */
public class AllData {

	public static final String PLATFORMS[] = { "AN", "IO", "PC", "PS", "XB" };

	private static final Logger LOG = LogManager.getLogger(AllData.class);

	private Set<String> platforms = new HashSet<String>();
	private Map<String, Game> games;
	private Map<Long, Player> players;
	private Map<Long, Persona> personas;
	private List<Score> scores;
	private AllGamesLeaderboard allGamesLeaderboard;

	public AllData() {
		platforms = new HashSet<String>(Arrays.asList(PLATFORMS));
		allGamesLeaderboard = new AllGamesLeaderboard();
	}

	/**
	 * @throws ApplicationException
	 * 
	 */
	public void loadData() throws ApplicationException {
		LOG.debug("loading the data");
		games = GameReader.read();
		players = PlayerReader.read();
		personas = PersonaReader.read();
		scores = ScoreReader.read();

		// add the scores to the personas
		for (Score score : scores) {
			Long personasId = score.getPersonaId();
			Persona persona = personas.get(personasId);
			persona.addScore(score);

			allGamesLeaderboard.update(score);
		}

		LOG.debug(String.format("Added %d scores to %d personas", scores.size(), personas.size()));
		LOG.debug(String.format("Leaderboard has %d entries", allGamesLeaderboard.getLeaderboard().size()));
	}

	/**
	 * @return the platforms
	 */
	public Set<String> getPlatforms() {
		return platforms;
	}

	/**
	 * @return the games
	 */
	public Map<String, Game> getGames() {
		return games;
	}

	/**
	 * @return the players
	 */
	public Map<Long, Player> getPlayers() {
		return players;
	}

	/**
	 * @return the personas
	 */
	public Map<Long, Persona> getPersonas() {
		return personas;
	}

	/**
	 * @return the scores
	 */
	public List<Score> getScores() {
		return scores;
	}

	/**
	 * @return the allGamesLeaderboard
	 */
	public AllGamesLeaderboard getAllGamesLeaderboard() {
		return allGamesLeaderboard;
	}

}
