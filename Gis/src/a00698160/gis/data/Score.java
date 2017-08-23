/**
 * Project: Gis
 * File: Score.java
 * Date: Oct 18, 2014
 * Time: 1:24:54 PM
 */

package a00698160.gis.data;

/**
 * @author Jason Chan, A00698160
 * 
 */
public class Score implements GisData {

	public static final String WIN = "WIN";
	public static final String LOSS = "LOSS";

	private long personaId;
	private String gameId;
	private boolean win;

	/**
	 * Default constructor.
	 */
	public Score() {
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
	 * @return the win
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * @param win
	 *            the win to set
	 */
	public void setWin(boolean win) {
		this.win = win;
	}

	/*
	 * (non-Javadoc)
	 * @see a00123456.gis.data.GisData#getAttributeCount()
	 */
	@Override
	public int getAttributeCount() {
		return 3;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Score [personaId=" + personaId + ", gameId=" + gameId + ", win=" + win + "]";
	}

}
