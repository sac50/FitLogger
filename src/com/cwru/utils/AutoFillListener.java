package com.cwru.utils;

import java.util.List;

import com.cwru.R;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AutoFillListener {
	
	public void repsAutoFillListener(EditText repsText, final List<LinearLayout> inflatedLayouts) {
		repsText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(
					Editable arg0) {

			}

			@Override
			public void beforeTextChanged(
					CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			// when text is changed in first set's reps param
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				LinearLayout inflatedSet;
				String replace = s.toString();
				// for every inflated set after the first
				for (int i = 1; i < inflatedLayouts.size(); i++) {
					inflatedSet = inflatedLayouts.get(i);
					String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps))
							.getText().toString();
					// if the set's reps value is null or empty
					if (old == null|| old.length() < 1
							// OR the first set's reps value is notempty
							// AND equal to candidate set's reps value, minus the new char
							|| replace.length() > 0 && old.equals(replace.substring(0,replace.length() - 1))
							// OR equal to first set's reps value before a digit was deleted
							|| old.substring(0, old.length() - 1).equals(replace)) {
						// set the weight text equal to the first set's weight value
						EditText replaceReps = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseReps);
						replaceReps.setText(replace);
					}
				}
			}
		});
	}
	
	public void weightAutoFillListener(EditText weightText, final List<LinearLayout> inflatedLayouts) {
		weightText.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(
					Editable arg0) {

			}

			@Override
			public void beforeTextChanged(
					CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			// when text is changed in first set's weight param
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				LinearLayout inflatedSet;
				String replace = s.toString();
				// for every inflated set after the first
				for (int i = 1; i < inflatedLayouts.size(); i++) {
					inflatedSet = inflatedLayouts.get(i);
					String old = ((EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight))
							.getText().toString();
					// if the set's weight value is null or empty
					if (old == null|| old.length() < 1
							// OR the first set's weight value is notempty
							// AND equal to candidate set's weight value, minus the new char
							|| replace.length() > 0 && old.equals(replace.substring(0,replace.length() - 1))
							// OR equal to first set's weight value before a digit was deleted
							|| old.substring(0, old.length() - 1).equals(replace)) {
						// set the weight text equal to the first set's weight value
						EditText replaceWeight = (EditText) inflatedSet.findViewById(R.id.etCreateExerciseWeight);
						replaceWeight.setText(replace);
					}
				}
			}
		});
	}
}