package com.coolmodev.latestgists;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.coolmodev.latestgists.Gist.GistFile;

public class FilesListActivity extends ListActivity implements OnItemClickListener{
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		
		String title = getIntent().getStringExtra(Constants.GIST_TITLE_KEY);
		if (title != null) {
			setTitle(title);
		}
		
		if (MainActivity.selectedGist != null) {
			ArrayList<GistFile> files = MainActivity.selectedGist.getFiles();
			String[] filenames = new String[files.size()];
			for (int i = 0; i < files.size(); i++) {
				filenames[i] = files.get(i).getFilename();
			}
			
			getListView().setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filenames));
			getListView().setOnItemClickListener(this);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> list, View v, int position, long id) {
		if (MainActivity.selectedGist != null) {
			ArrayList<GistFile> files = MainActivity.selectedGist.getFiles();
			if (files != null && position < files.size()) {
				String url = files.get(position).getUrl();
				Intent i = new Intent(this, DetailActivity.class);
				i.putExtra(Constants.GIST_FILE_URL_KEY, url);	
				startActivity(i);
			}
		}
	}
}
