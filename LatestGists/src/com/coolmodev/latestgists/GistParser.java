package com.coolmodev.latestgists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class GistParser {

	public static final String GIST_URL = "https://api.github.com/gists/public";

	public static List<Gist> fetchGists() {
		URL url;
		try {
			url = new URL(GIST_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			return readStream(con.getInputStream());
		} catch (MalformedURLException e) {
			return null; // quick and dirty signal to Activity that there is a problem
		} catch (IOException e) {
			return null;
		}
	}
	
	private static List<Gist> readStream(InputStream stream) {
		BufferedReader reader = null;
		StringBuilder jsonBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			String line = reader.readLine();
			while (line != null) {
				jsonBuilder.append(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// no op, not much we can do here
				}
			}
		}
		
		return parseJson(jsonBuilder.toString());
	}
	
	public static List<Gist> parseJson(String json) {
		JSONArray jsonArray = null;
		List<Gist> gists = null;
		try {
			jsonArray = new JSONArray(json);
			gists = new ArrayList<Gist>();
			for (int i = 0; i < jsonArray.length(); i++) {
				Gist gist = new Gist(jsonArray.getJSONObject(i));
				gists.add(gist);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return gists;
	}
}
