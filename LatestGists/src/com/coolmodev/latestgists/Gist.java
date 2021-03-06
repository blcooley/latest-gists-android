package com.coolmodev.latestgists;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Gist {

	private String mId;
	private String mUserLogin;
	private String mDescription;
	private ArrayList<GistFile> mFiles;
	
	public Gist(JSONObject object) {
		super();

		
		try {
			this.mDescription = object.getString(JSON.DESCRIPTION);
		} catch (JSONException e) {
			this.mDescription = "";
		}

		try {
			this.mId = object.getString(JSON.ID);
		} catch (JSONException e) {
			this.mId = "";
		}

		try {
			JSONObject user = object.getJSONObject(JSON.USER);
			this.mUserLogin = user.getString(JSON.USER_LOGIN);
		} catch (JSONException e) {
			this.mUserLogin = "";
		}

		try {
			JSONObject files = object.getJSONObject(JSON.FILES);
			if (files != null) {
				mFiles = new ArrayList<GistFile>();
				Iterator<?> keys = files.keys();
				while (keys.hasNext()) {
					JSONObject fileJson = (JSONObject) files.get((String)keys.next());
					String filename = fileJson.getString(JSON.FILES_FILENAME);
					String language = fileJson.getString(JSON.FILES_LANGUAGE);
					String url = fileJson.getString(JSON.FILES_RAW_URL);
					GistFile file = new GistFile(filename, language, url);
					mFiles.add(file);
				}
			}
			Log.d("Gist", "Parsed the file(s)");

		} catch (JSONException e) {
			Log.d("Gist", "Failed to parse the file(s)");
		}
	}

	public String getId() {
		return mId;
	}

	public String getUserLogin() {
		return mUserLogin;
	}

	public String getDescription() {
		return mDescription;
	}
	
	public String getLanguages() {
		StringBuilder languagesBuilder = new StringBuilder();
		if (mFiles != null) {
			for (GistFile file : mFiles) {
				if (file.mLanguage != null) {
					languagesBuilder.append(file.mLanguage).append(", ");
				}
			}
			int length = languagesBuilder.length();
			if (length > 1) {
				languagesBuilder.delete(length-2, length);
			}
		}
		return languagesBuilder.toString();
	}
	
	public ArrayList<GistFile> getFiles() {
		// This is a mutable copy, should probably do a deep defensive copy here
		return mFiles;
	}

	public class GistFile {
		private String mFilename;
		private String mLanguage;
		private String mUrl;

		public GistFile(String filename, String language, String url) {
			mFilename = filename;
			mLanguage = language;
			mUrl = url;
		}

		public String getFilename() {
			return mFilename;
		}

		public String getLanguage() {
			return mLanguage;
		}

		public String getUrl() {
			return mUrl;
		}
	}

	public static final class JSON {
		public static final String URL = "url";
		public static final String FORKS_URL = "forks_url";
		public static final String COMMITS_URL = "commits_url";
		public static final String ID = "id";
		public static final String GIT_PULL_URL = "git_pull_url";
		public static final String GIT_PUSH_URL = "git_push_url";
		public static final String HTML_URL = "html_url";
		public static final String FILES = "files";
		public static final String FILES_FILENAME = "filename";
		public static final String FILES_TYPE = "type";
		public static final String FILES_LANGUAGE = "language";
		public static final String FILES_RAW_URL = "raw_url";
		public static final String FILES_SIZE = "size";
		public static final String PUBLIC = "public";
		public static final String CREATED_AT = "created_at";
		public static final String UPDATED_AT = "updated_at";
		public static final String DESCRIPTION = "description";
		public static final String COMMENTS = "comments";
		public static final String USER = "user";
		public static final String USER_LOGIN = "login";
		public static final String USER_ID = "id";
		public static final String USER_AVATAR_URL = "avatar_url";
		public static final String USER_GRAVATAR_ID = "gravatar_id";
		public static final String USER_URL = "url";
		public static final String USER_HTML_URL = "html_url";
		public static final String USER_FOLLOWERS_URL = "followers_url";
		public static final String USER_FOLLOWING_URL = "following_url";
		public static final String USER_GISTS_URL = "gists_url";
		public static final String USER_STARRED_URL = "starred_url";
		public static final String USER_SUBSCRIPTIONS_URL = "subscriptions_url";
		public static final String USER_ORGANIZATIONS_URL = "organizations_url";
		public static final String USER_REPOS_URL = "repos_url";
		public static final String USER_EVENTS_URL = "events_url";
		public static final String USER_RECEIVED_EVENTS_URL = "received_events_url";
		public static final String USER_TYPE = "type";
		public static final String USER_SITE_ADMIN = "site_admin";
		public static final String COMMENTS_URL = "comments_url";
	}
}
