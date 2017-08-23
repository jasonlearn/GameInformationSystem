/**
 * Project: Gis
 * File: Reader.java
 * Date: Oct 18, 2014
 * Time: 10:04:15 PM
 */

package a00698160.gis.io;

import java.util.Arrays;

import a00698160.gis.ApplicationException;
import a00698160.gis.data.GisData;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class Reader {

	public static final String RECORD_DELIMITER = ":";
	public static final String FIELD_DELIMITER = "\\|";

	/**
	 * @param row
	 * @return
	 * @throws ApplicationException
	 */
	public static String[] getElements(String row, GisData gisData) throws ApplicationException {
		String[] elements = row.split(FIELD_DELIMITER);
		if (elements.length != gisData.getAttributeCount()) {
			throw new ApplicationException(String.format("Expected %d but got %d: %s", gisData.getAttributeCount(), elements.length, Arrays.toString(elements)));
		}

		return elements;
	}
}
