/**
 * Project: Gis
 * File: PlayerReader.java
 * Date: Oct 18, 2014
 * Time: 3:43:37 PM
 */

package a00698160.gis.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.Player;

/**
 * @author Jason Chan, A00698160
 *
 */
public class PlayerReader extends Reader {

	public static final String FILENAME = "players.dat";
	private static final Logger LOG = LogManager.getLogger(PlayerReader.class);

	/**
	 * private constructor to prevent instantiation
	 */
	private PlayerReader() {
	}

	/**
	 * Read the persona input data.
	 * 
	 * @param data
	 *            The input data.
	 * @return A collection of players.
	 * @throws ApplicationException
	 */
	public static Map<Long, Player> read() throws ApplicationException {
		File file = new File(FILENAME);
		LOG.debug("Reading" + file.getAbsolutePath());
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		}

		Map<Long, Player> players = new HashMap<>();
		try {
			// reader past the first row
			if (scanner.hasNext()) {
				scanner.nextLine();
			}
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				Player player = new Player();
				String[] elements = getElements(row, player);

				int index = 0;
				player.setId(Long.parseLong(elements[index++]));
				player.setFirstName(elements[index++]);
				player.setLastName(elements[index++]);
				String email = elements[index++];
				if (!Validator.validateEmail(email)) {
					throw new ApplicationException(String.format("Invalid email: %s", email));
				}
				player.setEmailAddress(email);

				String yyyymmdd = elements[index];
				try {
					player.setBirthDate(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)) - 1, Integer.parseInt(yyyymmdd.substring(6, 8)));
				} catch (NumberFormatException e) {
					LOG.error("Invalid date element:" + yyyymmdd);
				}

				players.put(player.getId(), player);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return players;
	}

	private static class Validator {

		private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		private static Pattern pattern;
		private static Matcher matcher;

		private Validator() {
		}

		/**
		 * Validate an email string.
		 * 
		 * @param email
		 *            the email string.
		 * @return true if the email address is valid, false otherwise.
		 */
		public static boolean validateEmail(final String email) {
			if (pattern == null) {
				pattern = Pattern.compile(EMAIL_PATTERN);
			}

			matcher = pattern.matcher(email);
			return matcher.matches();
		}

	}
}
