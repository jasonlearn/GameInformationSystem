/**
 * Project: Gis
 * File: GameReader.java
 * Date: Oct 18, 2014
 * Time: 3:42:58 PM
 */

package a00698160.gis.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.Game;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class GameReader extends Reader {

	public static final String FILENAME = "games.dat";
	private static final Logger LOG = LogManager.getLogger(GameReader.class);

	/**
	 * private constructor to prevent instantiation
	 */
	private GameReader() {
	}

	/**
	 * Read the game input data.
	 * 
	 * @param data
	 *            The input data.
	 * @return A collection of games.
	 * @throws ApplicationException
	 */
	public static Map<String, Game> read() throws ApplicationException {
		File file = new File(FILENAME);
		LOG.debug("Reading" + file.getAbsolutePath());
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		}

		Map<String, Game> games = new HashMap<>();
		try {
			// reader past the first row
			if (scanner.hasNext()) {
				scanner.nextLine();
			}
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				Game game = new Game();
				String[] elements = getElements(row, game);

				int index = 0;
				game.setId(elements[index++]);
				game.setName(elements[index++]);
				game.setProducer(elements[index++]);

				games.put(game.getId(), game);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return games;
	}
}
