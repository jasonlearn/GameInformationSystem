/**
 * 
 */
package a00698160.gis.data;

import java.util.Arrays;

import a00698160.gis.ApplicationException;

/**
 * Extract the program options from the commandline arguments and store them for safekeeping.
 * 
 * @author Jason Chan, A00698160
 */
public class Options {

	private static final String TOTAL_OPTION = "total";
	private static final String BY_GAME_OPTION = "by_game";
	private static final String BY_COUNT_OPTION = "by_count";
	private static final String PLATFORM_OPTION = "platform";
	private static final String DESCENDING_OPTION = "desc";
	private static final String PLAYERS_OPTION = "players";

	private boolean printTotalOptionSet;
	private boolean sortByGameOptionSet;
	private boolean sortByCountOptionSet;
	private boolean sortDescendingOptionSet;
	private boolean playersOptionSet;
	private String platform;

	private AllData allData;

	public Options(AllData allData) {
		this.allData = allData;
	}

	/**
	 * Process the commandline arguments and set the program options.
	 * 
	 * @param args
	 * @throws ApplicationException
	 */
	public void process(String[] args) throws ApplicationException {
		if (args.length > 4) {
			throw new ApplicationException(String.format("Expected a maximum of 4 commandline options, but get %d", args.length));
		}

		if (args.length == 1 && PLAYERS_OPTION.equals(args[0].trim())) {
			playersOptionSet = true;
			return;
		}

		for (String arg : args) {
			arg = arg.toLowerCase();
			if (TOTAL_OPTION.equals(arg)) {
				printTotalOptionSet = true;
			} else if (DESCENDING_OPTION.equals(arg)) {
				sortDescendingOptionSet = true;
			} else if (arg.contains(PLATFORM_OPTION)) {
				String[] elements = arg.split("=");
				if (elements.length != 2) {
					throw new ApplicationException(
							String.format("Encountered an error processing the platform option. Expected two elements for %s but got %d", arg, elements.length));
				}

				if (allData.getPlatforms().contains(elements[1].toUpperCase())) {
					platform = elements[1].toUpperCase();
				} else {
					throw new ApplicationException(String.format("Invalid platform '%s' - expected one of %s", elements[1], Arrays.toString(AllData.PLATFORMS)));
				}
			} else if (BY_GAME_OPTION.equals(arg)) {
				sortByGameOptionSet = true;
			} else if (BY_COUNT_OPTION.equals(arg)) {
				sortByCountOptionSet = true;
			} else {
				throw new ApplicationException(String.format("Invalid commandline option - '%s'", arg));
			}
		}

		if (sortByGameOptionSet && sortByCountOptionSet) {
			throw new ApplicationException("One one of by_game or by_count option is allowed.");
		}
	}

	/**
	 * @return the totalOption
	 */
	public static String getTotalOption() {
		return TOTAL_OPTION;
	}

	/**
	 * @return the byGameOption
	 */
	public static String getByGameOption() {
		return BY_GAME_OPTION;
	}

	/**
	 * @return the byCountOption
	 */
	public static String getByCountOption() {
		return BY_COUNT_OPTION;
	}

	/**
	 * @return the platformOption
	 */
	public static String getPlatformOption() {
		return PLATFORM_OPTION;
	}

	/**
	 * @return the descendingOption
	 */
	public static String getDescendingOption() {
		return DESCENDING_OPTION;
	}

	/**
	 * @return the playersOption
	 */
	public static String getPlayersOption() {
		return PLAYERS_OPTION;
	}

	/**
	 * @return the printTotalOptionSet
	 */
	public boolean isPrintTotalOptionSet() {
		return printTotalOptionSet;
	}

	/**
	 * @return the sortByGameOptionSet
	 */
	public boolean isSortByGameOptionSet() {
		return sortByGameOptionSet;
	}

	/**
	 * @return the sortByCountOptionSet
	 */
	public boolean isSortByCountOptionSet() {
		return sortByCountOptionSet;
	}

	/**
	 * @return the sortDescendingOptionSet
	 */
	public boolean isSortDescendingOptionSet() {
		return sortDescendingOptionSet;
	}

	/**
	 * @return the playersOptionSet
	 */
	public boolean isPlayersOptionSet() {
		return playersOptionSet;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @return the allData
	 */
	public AllData getAllData() {
		return allData;
	}

}
