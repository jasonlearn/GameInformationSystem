/**
 * Project: Gis
 * File: AllGamesLeaderboardData.java
 * Date: Oct 19, 2014
 * Time: 3:13:35 PM
 */

package a00698160.gis.data;

/**
 * 
 * @author Jason Chan, A00698160
 */
public class AllGamesLeaderboardData {

	private int wins;
	private int losses;
	private String gameId;
	private long personaId;

	/**
	 * @param score
	 */
	public AllGamesLeaderboardData(Score score) {
		this.gameId = score.getGameId();
		this.personaId = score.getPersonaId();
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * Update the win count
	 */
	public void updateWins() {
		wins++;
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * Update the losses count
	 */
	public void updateLosses() {
		losses++;
	}

	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return gameId;
	}

	/**
	 * @param gameId
	 *            the gameId to set
	 */
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return the personaId
	 */
	public long getPersonaId() {
		return personaId;
	}

	/**
	 * @param personaId
	 *            the personaId to set
	 */
	public void setPersonaId(long personaId) {
		this.personaId = personaId;
	}

	/**
	 * @param score
	 */
	public void update(Score score) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AllGamesLeaderboardData [wins=" + wins + ", losses=" + losses + ", gameId=" + gameId + ", personaId=" + personaId + "]";
	}

}
