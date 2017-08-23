/**
 * Project: Gis
 * File: PlayerReportData.java
 * Date: Oct 19, 2014
 * Time: 1:59:38 PM
 */

package a00698160.gis.data;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class PlayerReportData {

	private long id;
	private String name;
	private String emailAddress;
	private long age;
	private int totalGamesPlayed;
	private int totalWins;

	public PlayerReportData() {

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the age
	 */
	public long getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(long age) {
		this.age = age;
	}

	/**
	 * @return the totalGamesPlayed
	 */
	public int getTotalGamesPlayed() {
		return totalGamesPlayed;
	}

	/**
	 * @param totalGamesPlayed
	 *            the totalGamesPlayed to set
	 */
	public void setTotalGamesPlayed(int totalGamesPlayed) {
		this.totalGamesPlayed = totalGamesPlayed;
	}

	/**
	 * @return the totalWins
	 */
	public int getTotalWins() {
		return totalWins;
	}

	/**
	 * @param totalWins
	 *            the totalWins to set
	 */
	public void setTotalWins(int totalWins) {
		this.totalWins = totalWins;
	}

}
