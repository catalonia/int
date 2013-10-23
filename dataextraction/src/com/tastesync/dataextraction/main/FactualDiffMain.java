package com.tastesync.dataextraction.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.factual.driver.DiffsQuery;
import com.factual.driver.DiffsResponse;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.RowQuery;
import com.factual.driver.RowResponse;


public class FactualDiffMain {
    private static String GMAIL_MY_KEY = "OQ2qGesK8mtqsCwqztNT3zDmQprNz4TZyK7uXBvp";
    private static String GMAIL_MY_SECRET = "KAbpT13FQ34FAGm3AaY9qZb9PurqOJEvZdwaUk13";

    private static String MY_KEY = "4VpfS1VNddgzyPfyiPHLvEtfALzqoHonMkzmWEZJ";
    private static String MY_SECRET = "HYWAhQFW47SnX2B7FDveAPdEZeTGDxb56dh7O734";
    		
    /**
     * @param args
     */
    public static void main(String[] args) {
//        // Create an authenticated handle to Factual
//        //Factual factual = new Factual(MY_KEY, MY_SECRET);
//        Factual factual = new Factual(MY_KEY, MY_SECRET, true);
//        // Print 3 random records from Factual's Places table:
//        System.out.println(factual.fetch("places", new Query().limit(3)));
//
//        // Print entities that match a full text search for Sushi in Santa Monica:
//        System.out.println(factual.fetch("places",
//                new Query().search("Sushi Santa Monica")));
//
//        // Print the Factual row:
//        System.out.println(factual.fetchRow("places",
//                "03c26917-5d66-4de9-96bc-b13066173c65"));
        
		// Create an authenticated handle to Factual
		// Factual factual = new Factual(MY_KEY, MY_SECRET);
		// Factual factual = new Factual(MY_KEY, MY_SECRET);
		Factual factual = new Factual(MY_KEY, MY_SECRET,true);

		// Fetch a diffs response
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date start = null;
		try {
			start = sdf.parse("14/05/2013 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date end = null;
		try {
			end = sdf.parse("14/05/2013 12:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// DiffsQuery diffs = new DiffsQuery(d.getTime());
		Date startDate = new Date(start.getTime());
		Date endDate = new Date(end.getTime());
			DiffsQuery diffs = new DiffsQuery().after(startDate).before(endDate);
			
			
			// DiffsResponse resp = factual.fetch("places", diffs);
			 DiffsResponse resp = factual.fetch("restaurants", diffs);
			//factual.debug(true);
			System.out.println(factual.fetch("restaurants", diffs));
			//factual.debug(false);
			Query q = new Query().limit(100).offset(2);
			//System.out.println(factual.fetch("restaurants", q));
		
		//RowQuery q = new RowQuery();
		//RowResponse resp = factual.fetchRow("restaurants", "6e5d71a4-065c-402e-8fb7-c678372afbca", q);
        
    }
}
