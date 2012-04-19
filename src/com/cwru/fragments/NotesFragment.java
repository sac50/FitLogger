package com.cwru.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cwru.R;
import com.cwru.dao.DbAdapter;

public class NotesFragment extends Fragment {
	private Context context;
	private DbAdapter mDbHelper;
	private int exerciseId;
	private EditText etNote;
	private Button btnSubmit;
	
	public NotesFragment (Context context, int exerciseId) {
		this.context = context;
		this.exerciseId = exerciseId;
		mDbHelper = new DbAdapter(context);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}	
		
		View view = (LinearLayout) inflater.inflate(R.layout.workout_workflow_notes, container, false);
		etNote = (EditText) view.findViewById(R.id.etWorkoutWorkflowNotesNote);
		String noteText = mDbHelper.getNote(exerciseId);
		if (noteText != null) {
			etNote.setText(noteText);
		}
		btnSubmit = (Button) view.findViewById(R.id.btnWorkoutWorkflowNotesSubmitNote);
		btnSubmit.setOnClickListener(storeNote);
		
		return view;
	}
	
	View.OnClickListener storeNote = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String note = etNote.getText().toString();
			mDbHelper.insertNote(exerciseId, note);			
			int duration = Toast.LENGTH_SHORT;
			String toastText = "Note Updated";
			Toast toast = Toast.makeText(context, toastText, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	};
}
