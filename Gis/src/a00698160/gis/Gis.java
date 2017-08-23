package a00698160.gis;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.dao.GameDao;
import a00698160.gis.dao.LeaderboardDataDao;
import a00698160.gis.dao.PersonaDao;
import a00698160.gis.dao.PlayerDao;
import a00698160.gis.dao.ScoreDao;
import a00698160.gis.data.AllData;
import a00698160.gis.data.Database;
import a00698160.gis.data.Game;
import a00698160.gis.data.LeaderboardData;
import a00698160.gis.data.Options;
import a00698160.gis.data.Persona;
import a00698160.gis.data.Player;
import a00698160.gis.data.Score;
import a00698160.gis.io.GisReport;
import a00698160.gis.io.PlayersReport;
import a00698160.gis.ui.MainFrame;

/**
 * Project: Gis
 * File: Gis.java
 * Date: Oct 18, 2014
 * Time: 1:22:25 PM
 */

/**
 * @author Jason Chan, A00698160
 *
 */
public class Gis {

	private static final String PLAYER_DATA_FILENAME = "players.dat";
	private static final String PERSONA_DATA_FILENAME = "personas.dat";
	private static final String GAME_DATA_FILENAME = "games.dat";
	private static final String SCORE_DATA_FILENAME = "scores.dat";

	private static final String DB_PROPERTIES_FILENAME = "db.properties";
	private static PlayerDao playerDao;
	private static PersonaDao personaDao;
	private static GameDao gameDao;
	private static ScoreDao scoreDao;
	private static LeaderboardDataDao leaderboardDataDao;
	private Database db;

	private static final Logger LOG = LogManager.getLogger(Gis.class);

	private AllData allData;
	private Options options;
	// private LeaderboardData leaderboardData;

	/**
	 * Gis Constructor. Processes the commandline arguments
	 * 
	 * @throws ApplicationException
	 */
	public Gis() throws ApplicationException {

		LOG.info("Created Gis");

		allData = new AllData();
		options = new Options(allData);
		// options.process(args);
	}

	/**
	 * Entry point to GIS
	 * 
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Instant startTime = Instant.now();
		LOG.info(startTime);

		File playerFile = new File(PLAYER_DATA_FILENAME);
		if (!playerFile.exists()) {
			System.out.format("Required '%s' is missing.", PLAYER_DATA_FILENAME);
			System.exit(-1);
		}
		File personaFile = new File(PERSONA_DATA_FILENAME);
		if (!personaFile.exists()) {
			System.out.format("Required '%s' is missing.", PERSONA_DATA_FILENAME);
			System.exit(-1);
		}
		File gameFile = new File(GAME_DATA_FILENAME);
		if (!gameFile.exists()) {
			System.out.format("Required '%s' is missing.", GAME_DATA_FILENAME);
			System.exit(-1);
		}
		File scoreFile = new File(SCORE_DATA_FILENAME);
		if (!scoreFile.exists()) {
			System.out.format("Required '%s' is missing.", SCORE_DATA_FILENAME);
			System.exit(-1);
		}
		// start the Game Information System
		try {
			new Gis().run();
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}

		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	/**
	 * @throws ApplicationException
	 * 
	 */
	private void run() throws ApplicationException {
		LOG.debug("run()");
		allData.loadData();
		// generateReports();

		// Loads all dat files to external JDBC.
		try {
			loadPlayers();
			loadPersonas();
			loadGames();
			loadScores();
			loadLeaderboardData();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		createUI();
	}

	/**
	 * Generate the reports from the input data
	 * 
	 * Method not necessary for Gis Assignment 2.
	 */
	@SuppressWarnings("unused")
	private void generateReports() {
		LOG.debug("generateReports()");
		if (options.isPlayersOptionSet()) {
			PlayersReport playersReport = new PlayersReport(allData);
			playersReport.print(System.out, this);
			try {
				PrintStream out = new PrintStream(new File("players_report.txt"));
				playersReport.print(out, this);
			} catch (FileNotFoundException e) {
				LOG.equals(e);
			}
		} else {
			GisReport gisReport = new GisReport(allData);
			gisReport.print(System.out, options);
			try {
				PrintStream out = new PrintStream(new File("leaderboard_report.txt"));
				gisReport.print(out, options);
			} catch (FileNotFoundException e) {
				LOG.equals(e);
			}
		}
	}

	/**
	 * Load Players to players table.
	 */
	private void loadPlayers() throws IOException, SQLException {
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		db = new Database(dbProperties);
		playerDao = new PlayerDao(db);
		if (!Database.tableExists(PlayerDao.TABLE_NAME)) {
			int playerCount = 0;
			playerDao.create();

			for (Player player : allData.getPlayers().values()) {
				Player thisPlayer = new Player();
				thisPlayer.setId(player.getId());
				thisPlayer.setFirstName(player.getFirstName());
				thisPlayer.setLastName(player.getLastName());
				thisPlayer.setEmailAddress(player.getEmailAddress());
				ZonedDateTime birthdate = player.getBirthDate();
				thisPlayer.setBirthDate(birthdate);

				// inserts current player to a row in player table.
				playerDao.add(thisPlayer);

				playerCount++;
			}
			LOG.debug("Inserted " + playerCount + " players");
		} else {
			LOG.info(PlayerDao.TABLE_NAME + " table already exists in the database.");
		}
	}

	/**
	 * Load Personas to personas table.
	 */
	private void loadPersonas() throws IOException, SQLException, ApplicationException {
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		db = new Database(dbProperties);
		personaDao = new PersonaDao(db);
		if (!Database.tableExists(PersonaDao.TABLE_NAME)) {

			int personaCount = 0;
			personaDao.create();

			for (Persona persona : allData.getPersonas().values()) {
				Persona thisPersona = new Persona();
				thisPersona.setId(persona.getId());
				thisPersona.setPlayerId(persona.getPlayerId());
				thisPersona.setGamerTag(persona.getGamerTag());
				thisPersona.setPlatform(persona.getPlatform());

				// inserts current persona to a row in persona table.
				personaDao.add(thisPersona);

				personaCount++;
			}

			LOG.debug("Inserted " + personaCount + " personas.");
		} else {
			LOG.info(PersonaDao.TABLE_NAME + " table already exists in the database.");
		}
	}

	/**
	 * Load Game to games table.
	 */
	private void loadGames() throws IOException, SQLException, ApplicationException {
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		db = new Database(dbProperties);
		gameDao = new GameDao(db);
		if (!Database.tableExists(GameDao.TABLE_NAME)) {

			int gameCount = 0;
			gameDao.create();

			for (Game game : allData.getGames().values()) {
				Game thisGame = new Game();
				thisGame.setId(game.getId());
				thisGame.setName(game.getName());
				thisGame.setProducer(game.getProducer());

				// inserts current game to a row in game table.
				gameDao.add(thisGame);

				gameCount++;
			}

			LOG.debug("Inserted " + gameCount + " games.");
		} else {
			LOG.info(GameDao.TABLE_NAME + " table already exists in the database.");
		}
	}

	/**
	 * Load Score to scores table.
	 */
	private void loadScores() throws IOException, SQLException, ApplicationException {
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		db = new Database(dbProperties);
		scoreDao = new ScoreDao(db);
		if (!Database.tableExists(ScoreDao.TABLE_NAME)) {

			int scoreCount = 0;
			scoreDao.create();

			for (Score score : allData.getScores()) {
				Score thisScore = new Score();
				thisScore.setPersonaId(score.getPersonaId());
				thisScore.setGameId(score.getGameId());
				thisScore.setWin(score.isWin());

				// inserts current score to a row in Score table.
				scoreDao.add(thisScore);

				scoreCount++;
			}

			LOG.debug("Inserted " + scoreCount + " scores.");
		} else {
			LOG.info(ScoreDao.TABLE_NAME + " table already exists in the database.");
		}
	}

	/**
	 * Load LeaderboardData to Leaderboard table.
	 */
	private void loadLeaderboardData() throws IOException, SQLException, ApplicationException {
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		db = new Database(dbProperties);
		leaderboardDataDao = new LeaderboardDataDao(db);
		if (!Database.tableExists(LeaderboardDataDao.TABLE_NAME)) {

			int leaderboardCount = 0;
			leaderboardDataDao.create();

			String thisGameId = null;
			long thisPersonaId = 0;

			for (Score score : allData.getScores()) {
				LeaderboardData thisLeader = new LeaderboardData();
				thisPersonaId = score.getPersonaId();
				thisGameId = score.getGameId();
				int win = 0;
				int loss = 0;
				for (Score s : allData.getScores()) {
					if (thisPersonaId == s.getPersonaId() && thisGameId.equalsIgnoreCase(s.getGameId())) {
						if (s.isWin()) {
							win++;
						} else {
							loss++;
						}
					}
				}
				String gameName = null;
				for (Game g : allData.getGames().values()) {
					if (thisGameId.equalsIgnoreCase(g.getId())) {
						gameName = g.getName();
					}
				}
				String tag = null;
				String platform = null;
				for (Persona p : allData.getPersonas().values()) {
					if (thisPersonaId == p.getId()) {
						tag = p.getGamerTag();
						platform = p.getPlatform();
					}
				}
				thisLeader.setWinCount(win);
				thisLeader.setLossCount(loss);
				thisLeader.setGameName(gameName);
				thisLeader.setGamerTag(tag);
				thisLeader.setPlatform(platform);

				// inserts current leaderboardData to a row in leaderboardData table.
				leaderboardDataDao.add(thisLeader);
				leaderboardCount++;
			}

			LOG.debug("Inserted " + leaderboardCount + " leaders.");
		} else {
			LOG.info(LeaderboardDataDao.TABLE_NAME + " table already exists in the database.");
		}
	}

	/**
	 * Method to create Gis user interface.
	 */
	public static void createUI() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
				List<LeaderboardData> listOfLeaders = new ArrayList<LeaderboardData>();
				LeaderboardData leaderboard = new LeaderboardData();
				try {
					listOfLeaders = leaderboard.getLeaderboardData();
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
				MainFrame mainFrame = new MainFrame(listOfLeaders);
				mainFrame.setVisible(true);
				LOG.info("Gis MainFrame Initiated");

			}
		});
	}
}
