package com.tastesync.dataextraction.main;

import com.factual.driver.Factual;

import com.tastesync.dataextraction.exception.TasteSyncException;
import com.tastesync.dataextraction.process.FactualCrosswalkData;

import com.tastesync.db.pool.TSDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Date;

public class FactualCrosswalkMain {
    private static String GMAIL_MY_KEY = "OQ2qGesK8mtqsCwqztNT3zDmQprNz4TZyK7uXBvp";
    private static String GMAIL_MY_SECRET = "KAbpT13FQ34FAGm3AaY9qZb9PurqOJEvZdwaUk13";
    private static String MY_KEY = "4VpfS1VNddgzyPfyiPHLvEtfALzqoHonMkzmWEZJ";
    private static String MY_SECRET = "HYWAhQFW47SnX2B7FDveAPdEZeTGDxb56dh7O734";

    /**
     * @param args
     */
    public static void main(String[] args) {
        FactualCrosswalkData factualCrosswalkData = new FactualCrosswalkData();
        boolean sleep = false;

        while (true) {
            // Create an authenticated handle to Factual
            //Factual factual = new Factual(MY_KEY, MY_SECRET, true);
            Factual factual = new Factual(MY_KEY, MY_SECRET, false);
            TSDataSource tsDataSource = TSDataSource.getInstance();
            Connection connection = null;

            try {
                connection = tsDataSource.getConnection();
                factualCrosswalkData.processFactualCrosswalkData(tsDataSource,
                    connection, factual);

                factual = new Factual(GMAIL_MY_KEY, GMAIL_MY_SECRET, false);
                factualCrosswalkData.processFactualCrosswalkData(tsDataSource,
                    connection, factual);

                tsDataSource.closeConnection(connection);
            } catch (TasteSyncException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();

                if (ex.getMessage()
                          .contains("You have exceeded the throttle limit")) {
                    sleep = true;
                }
            } finally {
                tsDataSource.closeConnection(connection);
            }

            if (sleep) {
                // further logics to wait
                Thread.currentThread();

                try {
                    System.out.println("wait for a day , current date=" +
                        new Date());
                    Thread.sleep(3600000 * 24);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            sleep = false;
        }
    }
}
