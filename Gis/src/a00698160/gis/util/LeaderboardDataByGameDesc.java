/**
 * Project: Gis
 * File: LeaderboardDataByGameDesc.java
 * Date: Oct 19, 2014
 * Time: 9:45:41 PM
 */

package a00698160.gis.util;

import java.util.Comparator;

import a00698160.gis.data.LeaderboardData;

/**
 * To sort Leaderboard data by Game name,
 * Descending order - Z to A.
 * 
 * @author Jason Chan, A00698160
 * 
 */
public class LeaderboardDataByGameDesc implements Comparator<LeaderboardData> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(LeaderboardData o1, LeaderboardData o2) {
		return o2.getGameName().compareToIgnoreCase(o1.getGameName());
	}

}
