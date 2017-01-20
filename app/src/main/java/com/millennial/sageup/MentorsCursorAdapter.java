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
import java.util.Random;

public class MentorsCursorAdapter extends ArrayAdapter<Mentor> {
	public MentorsCursorAdapter(Context context, ArrayList<Mentor> users) {
		super(context, 0, users);
	}

	String[] names = {"Rocky Searle", "Waldo Janson", "Cortez Emert", "Cedric Defilippo", "Daron Gigliotti",
	"Horacio Groen", "Otto Lanigan", "Dan Mesa", "Preston Gorrell", "Monte Lembo", "Alex Mak", "Samuel Hornstein",
	"Gilberto Hagwood", "Lou Kinch"};




	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Random generator = new Random();
		int i = generator.nextInt(13);

		Mentor mentor = MasterMentorsActivity.mentor.get(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mentor, parent, false);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.imageViewmentor);
		TextView tvName = (TextView) convertView.findViewById(R.id.textViewMentorName);
		TextView serial = (TextView) convertView.findViewById(R.id.textViewMentorSerial);
		TextView phone = (TextView) convertView.findViewById(R.id.textViewmentorphone);

		// Populate the data into the template view using the data object
		serial.setText(mentor.serial);
		img.setImageResource(R.drawable.default_person);
		tvName.setText(names[i]);
		phone.setText(mentor.sector);
		// Return the completed view to render on screen
		return convertView;
	}



}
