package com.coolmodev.latestgists;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GistAdapter extends BaseAdapter {

	private List<Gist> mGists;
	private Context mContext;
	
	public GistAdapter(Context context, List<Gist> gistList) {
		super();
		mGists = gistList;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		int numGists = 0;
		if (mGists != null) {
			numGists = mGists.size();
		}
		return numGists;
	}

	@Override
	public Gist getItem(int position) {
		Gist aGist = null;
		if (mGists != null) {
			aGist = mGists.get(position);
		}
		return aGist;
	}

	@Override
	public long getItemId(int position) {
		long gistId = 0;
		if (mGists != null) {
			gistId = getItem(position).hashCode();
		}
		return gistId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gist_cell, parent, false);
		}
		
		ViewHolder holder = (ViewHolder)convertView.getTag();
		
		if (holder == null) {
			holder = new ViewHolder();
			holder.userLoginText = (TextView)convertView.findViewById(R.id.user_login_text);
			holder.languagesText = (TextView)convertView.findViewById(R.id.languages_text);
			holder.descriptionText = (TextView)convertView.findViewById(R.id.description_text);
			holder.idText = (TextView)convertView.findViewById(R.id.gist_id_text);
			convertView.setTag(holder);
		}
		
		Gist gist = getItem(position);
		if (gist != null) {
			holder.userLoginText.setText(gist.getUserLogin());
			holder.descriptionText.setText(gist.getDescription());
			holder.idText.setText(gist.getId());
			holder.languagesText.setText(gist.getLanguages());
		}
		return convertView;
	}
	
	private class ViewHolder {
		TextView userLoginText;
		TextView descriptionText;
		TextView languagesText;
		TextView idText;
	}

}
