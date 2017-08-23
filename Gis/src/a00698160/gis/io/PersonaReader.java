/**
 * Project: Gis
 * File: PersonaReader.java
 * Date: Oct 18, 2014
 * Time: 3:43:16 PM
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
import a00698160.gis.data.Persona;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class PersonaReader extends Reader {

	public static final String FILENAME = "personas.dat";
	private static final Logger LOG = LogManager.getLogger(PersonaReader.class);

	/**
	 * private constructor to prevent instantiation
	 */
	private PersonaReader() {
	}

	/**
	 * Read the persona input data.
	 * 
	 * @param data
	 *            The input data.
	 * @return A collection of personas.
	 * @throws ApplicationException
	 */
	public static Map<Long, Persona> read() throws ApplicationException {
		File file = new File(FILENAME);
		LOG.debug("Reading" + file.getAbsolutePath());
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		}

		Map<Long, Persona> personas = new HashMap<>();
		try {
			// read past the first row
			if (scanner.hasNext()) {
				scanner.nextLine();
			}
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				Persona persona = new Persona();
				String[] elements = getElements(row, persona);

				int index = 0;
				persona.setId(Long.parseLong(elements[index++]));
				persona.setPlayerId(Long.parseLong(elements[index++]));
				persona.setGamerTag(elements[index++]);
				persona.setPlatform(elements[index++]);

				personas.put(persona.getId(), persona);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		return personas;
	}

}
