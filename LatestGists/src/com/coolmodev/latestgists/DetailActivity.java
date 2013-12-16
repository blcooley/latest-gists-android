package com.coolmodev.latestgists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailActivity extends Activity {

	private TextView mDetailText;
	private ProgressBar mProgressBar;
	private GetFileTextTask mGetFileTask;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		setContentView(R.layout.activity_detail);
		mDetailText = (TextView)findViewById(R.id.detail_text);
		mDetailText.setMovementMethod(new ScrollingMovementMethod());
	
		mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
		
		mGetFileTask = new GetFileTextTask();
		mGetFileTask.execute((Void)null);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mGetFileTask != null) {
			mGetFileTask.cancel(true);
		}
	}
	
	private class GetFileTextTask extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			if (mProgressBar != null) {
				mProgressBar.setVisibility(View.VISIBLE);
			}
		}
		
		@Override
		protected String doInBackground(Void... params) {
			URL url = null;
			String fileText = null;
			try {
				String urlString = getIntent().getStringExtra(Constants.GIST_FILE_URL_KEY);
				if (urlString != null) {
					url = new URL(urlString);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();

					InputStream stream = con.getInputStream();

					BufferedReader reader = null;
					StringBuilder fileBuilder = new StringBuilder();
					try {
						reader = new BufferedReader(new InputStreamReader(stream));
						String line = reader.readLine();
						while (line != null) {
							fileBuilder.append(line);
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
					fileText = fileBuilder.toString();
				}
			} catch (MalformedURLException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
			return fileText;
		}
		
		protected void onPostExecute(String result) {
			if (mDetailText != null && result != null) {
				mDetailText.setText(result);
				if (mProgressBar != null) {
					mProgressBar.setVisibility(View.GONE);
				}
			} else if (result == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
				builder.setTitle("Error")
				.setMessage("Could not retrieve file.")
				.create()
				.show();
				
			}
		}
	}
}
