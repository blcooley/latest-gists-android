package com.coolmodev.latestgists;

import java.util.List;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

public class MainActivity extends ListActivity implements OnItemClickListener {

	private FetchGistsTask mFetchGistsTask;
	private ProgressBar mProgressBar;
	private List<Gist> mGists;
	
	public static Gist selectedGist;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        getListView().setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	if (isConnected()) {
	    	mFetchGistsTask = new FetchGistsTask();
	    	mFetchGistsTask.execute((Void)null);
    	} else {
    		// contstruct AlertDialog to indicate no network connectivity? or use empty behavior for list?
    	}
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
    
    private boolean isConnected() {
    	ConnectivityManager conn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    	return conn.getActiveNetworkInfo().isConnected();
    }
    
    private class FetchGistsTask extends AsyncTask<Void, Void, Void> {

    	@Override
    	protected void onPreExecute() {
    		startSpinner();
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			mGists = GistParser.fetchGists();
			return null;
		}
 
		@Override
		protected void onPostExecute(Void v) {
			stopSpinner();
			getListView().setAdapter(new GistAdapter(MainActivity.this, mGists));
		}
    }

	@Override
	public void onItemClick(AdapterView<?> list, View v, int position, long id) {
		Gist gist = (Gist)list.getAdapter().getItem(position);
		if (gist.getFiles().size() > 1) {
			selectedGist = gist;
			Intent i = new Intent(this, FilesListActivity.class);
			startActivity(i);
		} else {
			String gistFileUrl = gist.getFiles().get(0).getUrl();
			Intent i = new Intent(this, DetailActivity.class);
			i.putExtra(Constants.GIST_FILE_URL_KEY, gistFileUrl);
			startActivity(i);
		}
	}
}
