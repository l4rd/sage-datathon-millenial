package com.millennial.sageup;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MentorsCursorAdapter extends ArrayAdapter<Mentor> {
	public MentorsCursorAdapter(Context context, ArrayList<Mentor> users) {
		super(context, 0, users);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Mentor mentor = MasterMentorsActivity.mentor.get(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mentor, parent, false);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.imageViewmentor);
		// Lookup view for data population
		TextView tvName = (TextView) convertView.findViewById(R.id.textViewMentorName);
		// Populate the data into the template view using the data object
		tvName.setText(mentor.serial);
		img.setImageResource(R.drawable.default_person);
		// Return the completed view to render on screen
		return convertView;
	}



}
