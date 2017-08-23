/**
 * Project: Gis
 * File: Persona.java
 * Date: Oct 18, 2014
 * Time: 1:23:37 PM
 */

package a00698160.gis.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Chan, A00698160
 */
public class Persona implements GisData {

	private long id;
	private long playerId;
	private String gamerTag;
	private String platform;

	private List<Score> gamesPlayed;

	/**
	 * Default Constructor
	 */
	public Persona() {
		gamesPlayed = new ArrayList<>();
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
	 * @return the playerId
	 */
	public long getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the gamerTag
	 */
	public String getGamerTag() {
		return gamerTag;
	}

	/**
	 * @param gamerTag
	 *            the gamerTag to set
	 */
	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/*
	 * (non-Javadoc)
	 * @see a00123456.gis.data.GisData#getAttributeCount()
	 */
	@Override
	public int getAttributeCount() {
		return 4;
	}

	/**
	 * 
	 * @param score
	 */
	public void addScore(Score score) {
		gamesPlayed.add(score);
	}

	/**
	 * @return the gamesPlayed
	 */
	public List<Score> getGamesPlayed() {
		return gamesPlayed;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Persona [id=" + id + ", playerId=" + playerId + ", gamerTag=" + gamerTag + ", platform=" + platform + "]";
	}

}
