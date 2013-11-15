package com.tastesync.dataextraction.db.queries;

public interface FactualCrosswalkQueries {
	public static String FACTUAL_IDS_SELECT_SQL = "SELECT FACTUAL_ID "
	+ "FROM   RESTAURANT "
	+ "WHERE  FACTUAL_ID NOT IN (SELECT RESTAURANT_RESERVATIONS.FACTUAL_ID "
	+ "                             FROM   RESTAURANT_RESERVATIONS) "
	+ "ORDER BY FACTUAL_ID";

	
	public static String OPENTABLE_FACTUAL_ID_INSERT_SQL = ""
			+ "INSERT INTO RESTAURANT_RESERVATIONS "
			+ "            (RESTAURANT_RESERVATIONS.FACTUAL_ID, "
			+ "             RESTAURANT_RESERVATIONS.RESERVATION_SOURCE, "
			+ "             RESTAURANT_RESERVATIONS.RESERVATION_SOURCE_ID, "
			+ "             RESTAURANT_RESERVATIONS.RESERVATION_URL, "
			+ "             RESTAURANT_RESERVATIONS.RESTAURANT_ID, "
			+ "             RESTAURANT_RESERVATIONS.UPDATED_DATETIME) "
			+ "VALUES      ( ?, "
			+ "              ?, "
			+ "              ?, "
			+ "              ?, "
			+ "              ?, "
			+ "              ? ) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "RESTAURANT_RESERVATIONS.RESERVATION_SOURCE = ?, "
			+ "RESTAURANT_RESERVATIONS.RESERVATION_SOURCE_ID = ?, "
			+ "RESTAURANT_RESERVATIONS.RESERVATION_URL = ?, "
			+ "RESTAURANT_RESERVATIONS.UPDATED_DATETIME = ?";
	
	public static String INVALID_OPENTABLE_FACTUAL_ID_INSERT_SQL = ""
			+ "INSERT INTO RESTAURANT_RESERVATIONS "
			+ "            (RESTAURANT_RESERVATIONS.FACTUAL_ID, "
			+ "             RESTAURANT_RESERVATIONS.RESTAURANT_ID, "
			+ "             RESTAURANT_RESERVATIONS.UPDATED_DATETIME) "
			+ "VALUES      ( ?, "
			+ "              ?, "
			+ "              ? ) "
			+ "ON DUPLICATE KEY UPDATE "
			+ "RESTAURANT_RESERVATIONS.UPDATED_DATETIME = ?";
	

}
