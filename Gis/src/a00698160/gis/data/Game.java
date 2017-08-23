/**
 * Project: Gis
 * File: Game.java
 * Date: Oct 18, 2014
 * Time: 1:24:32 PM
 */

package a00698160.gis.data;

/**
 * @author Jason Chan, A00698160
 */
public class Game implements GisData {

	private String id;
	private String name;
	private String producer;

	/**
	 * Default constructor
	 */
	public Game() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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
	 * @return the producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * @param producer
	 *            the producer to set
	 */
	public void setProducer(String producer) {
		this.producer = producer;
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
		return "Game [id=" + id + ", name=" + name + ", producer=" + producer + "]";
	}

}
