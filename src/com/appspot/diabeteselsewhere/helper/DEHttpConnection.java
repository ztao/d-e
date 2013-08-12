package com.appspot.diabeteselsewhere.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DEHttpConnection {


	public static String downloadFromServer(String... params) {
		String eventJson = null;
		try {
			URL url = new URL("http://diabeteselsewhere.appspot.com"+params[0]);
			HttpURLConnection con = (HttpURLConnection) url
					.openConnection();
			eventJson = readStream(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventJson;		
	}

	private static String readStream(InputStream in) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(in));
		
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
} 
