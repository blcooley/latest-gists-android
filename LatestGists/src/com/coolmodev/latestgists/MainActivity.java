package com.coolmodev.latestgists;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends ListActivity {

	private FetchGistsTask mFetchGistsTask;
	private ProgressBar mProgressBar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	mFetchGistsTask = new FetchGistsTask();
    	mFetchGistsTask.execute((Void)null);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	if (mFetchGistsTask != null) {
    		mFetchGistsTask.cancel(true);
    	}
		stopSpinner();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    private void startSpinner() {
        if (mProgressBar != null) {
        	mProgressBar.setVisibility(View.VISIBLE);
        }
    }
   
    private void stopSpinner() {
    	if (mProgressBar != null) {
        	mProgressBar.setVisibility(View.GONE);    		
    	}
    }
    
    private class FetchGistsTask extends AsyncTask<Void, Void, Void> {

    	@Override
    	protected void onPreExecute() {
    		startSpinner();
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			GistParser.fetchGists();
			return null;
		}
 
		@Override
		protected void onPostExecute(Void v) {
			stopSpinner();
			// TODO update adapter
		}
    }
}
