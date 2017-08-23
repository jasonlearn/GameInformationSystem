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
 * This class sorts Game by count, high to low
 * 
 * @author Jason Chan, A00698160
 * 
 */
public class LeaderboardDataByCount implements Comparator<LeaderboardData> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(LeaderboardData o1, LeaderboardData o2) {
		return o1.getWinLoss() - o2.getWinLoss();
	}

}
