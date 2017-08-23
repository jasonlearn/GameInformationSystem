/**
 * Project: Gis
 * File: LeaderboardDataByGame.java
 * Date: Oct 19, 2014
 * Time: 9:43:45 PM
 */

package a00698160.gis.util;

import java.util.Comparator;

import a00698160.gis.data.LeaderboardData;

/**
 * To sort Leaderboard data by Game name,
 * In ascending order - A to Z.
 * 
 * @author Jason Chan, A00698160
 * 
 */
public class LeaderboardDataByGame implements Comparator<LeaderboardData> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(LeaderboardData o1, LeaderboardData o2) {
		return o1.getGameName().compareToIgnoreCase(o2.getGameName());
	}

}
