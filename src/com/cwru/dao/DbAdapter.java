package com.cwru.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cwru.model.Exercise;
import com.cwru.model.Interval;
import com.cwru.model.Set;
import com.cwru.model.Workout;

public class DbAdapter {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private static final String TAG = "dbHelper";

	private static final String CREATE_WORKOUTS_TABLE =
			"create table workouts (_id integer primary key autoincrement, "
			+ "name text not null, workout_type text not null, exercise_sequence "
			+ "text not null, repeats text, repeats_sunday int, repeats_monday int," 
			+ "repeats_tuesday int, repeats_wednesday int, repeats_thursday int, " 
			+ "repeats_friday int, repeats_saturday int, comment text);";
	private static final String CREATE_EXERCISES_TABLE = 
			"create table exercises (_id integer primary key autoincrement, "
			+ "name text not null, type text not null, sets integer, "
			+ "time integer, time_type text, is_countdown boolean, distance real,"
			+ "distance_type text, interval_num integer, "
			+ "comment text, deleted boolean not null);";
	private static final String CREATE_SETS_TABLE = 
			"create table sets (_id integer primary key autoincrement, "
			+ "exercise_id integer not null, reps integer not null, "
			+ "weight real not null);";
	
	private static final String CREATE_INTERVALS_TABLE = 
			"create table intervals (_id integer primary key autoincrement, "
			+ "exercise_id integer not null, name text not null, "
			+ "time integer not null, time_type integer not null);";
	
	private static final String CREATE_WORKOUT_RESULTS_TABLE = 
			"create table workout_results (_id integer primary key autoincrement, "
			+ "date text not null, workout_id integer not null, exercise_id "
			+ "integer not null, sets integer, reps integer, weight real, "
			+ "time integer, time_type boolean, distance real, interval"
			+ "integer, comment text);";

	private static final String DATABASE_NAME = "FitLoggerData";
	private static final String DATABASE_TABLE_WORKOUT = "workouts";
	private static final String DATABASE_TABLE_EXERCISE = "exercises";
	private static final String DATABASE_TABLE_SET = "sets";
	private static final String DATABASE_TABLE_INTERVAL = "intervals";
	private static final String DATABASE_TABLE_WORKOUT_RESULT = "workout_results";
	private static final int DATABASE_VERSION = 1;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_WORKOUTS_TABLE);
			db.execSQL(CREATE_EXERCISES_TABLE);
			db.execSQL(CREATE_SETS_TABLE);
			db.execSQL(CREATE_INTERVALS_TABLE);
			db.execSQL(CREATE_WORKOUT_RESULTS_TABLE);
			Log.d("Steve", "DB CREATES");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DbAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(mCtx);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	public void createWorkout(Workout workout) {
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", workout.getName());
		initialValues.put("workout_type",workout.getType());
		// Initial Workout has blank exercise sequence
		initialValues.put("exercise_Sequence", "");
		initialValues.put("repeats", workout.getRepeatWeeks());
		initialValues.put("repeats_sunday", workout.getRepeatSunday());
		initialValues.put("repeats_monday", workout.getRepeatMonday());
		initialValues.put("repeats_tuesday", workout.getRepeatTuesday());
		initialValues.put("repeats_wednesday", workout.getRepeatWednesday());
		initialValues.put("repeats_thursday", workout.getRepeatThursday());
		initialValues.put("repeats_friday", workout.getRepeatFriday());
		initialValues.put("repeats_saturday", workout.getRepeatSaturday());
		// comment defaulted to blank, gets set during a workout
		initialValues.put("comment", "");
		db.insert(DATABASE_TABLE_WORKOUT, null, initialValues);
		close();
	}
	
	public Workout getWorkoutFromName(String workoutName) {
		open();
		String query = "select * from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		Workout workout = null;
		while (cursor.moveToNext() ) {
			String workoutType = cursor.getString(cursor.getColumnIndex("workout_type"));
			String exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
			String repeats = cursor.getString(cursor.getColumnIndex("repeats"));
			int rptSunday = cursor.getInt(cursor.getColumnIndex("repeats_sunday"));
			int rptMonday = cursor.getInt(cursor.getColumnIndex("repeats_monday"));
			int rptTuesday = cursor.getInt(cursor.getColumnIndex("repeats_tuesday"));
			int rptWednesday = cursor.getInt(cursor.getColumnIndex("repeats_wednesday"));
			int rptThursday = cursor.getInt(cursor.getColumnIndex("repeats_thursday"));
			int rptFriday = cursor.getInt(cursor.getColumnIndex("repeats_friday"));
			int rptSaturday = cursor.getInt(cursor.getColumnIndex("repeats_sunday"));
			
			workout = new Workout(workoutName, workoutType, exerciseSequence, repeats, rptSunday, rptMonday, rptTuesday, rptWednesday,
								  rptThursday, rptFriday, rptSaturday);
		}
		return workout;
	}
	
	public Cursor getAllWorkouts() { 
		return db.rawQuery("select * from workouts", new String [0]);
	}
	
	/* Does workout name exist */
	public boolean workoutNameExist(String workoutName) {
		open();
		boolean exists = false;
		String query = "select _id from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			exists = true;
		}
		close();
		return exists;
	}
	
	/** TODO
	 * Enforce that all workout names must be unique
	 * @return
	 */
	public String getExerciseSequence(String workoutName) {
		String query = "select exercise_sequence from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		String exerciseSequence = "";
		while (cursor.moveToNext()) {
			exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
		}
		return exerciseSequence;
	}
	
	public void updateWorkoutExerciseSequence(String exerciseSequence, String workoutName) {
		String query = "update workouts set exercise_sequence = '" + exerciseSequence + "' where name = '" + workoutName + "'";
		Log.d("Update Exercise Sequence", query);
		db.execSQL(query);
	}
	
	public Cursor getAllExercises() { 		
		String columns [] = {"_id", "name", "type", "sets", "time", "type", "is_countdown", "distance", "distance_type", "interval_num", "comment", "deleted"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, null, null, null, null, null);
		if (cursor == null) { Log.d("Steve", "Cursor for exercises is null");}
		return cursor;
	}
	
	public Exercise getExerciseFromId(Long exerciseId) {
		String query = "select * from exercises where _id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		String name = "";
		while (cursor.moveToNext()) {
			name = cursor.getString(cursor.getColumnIndex("name"));
		}
		return new Exercise(exerciseId, name);
	}

	public long createExercise(Exercise ex) {
		
		if (ex.getName() == null || ex.getName().length() == 0
				|| ex.getType() == null || ex.getType().length() == 0)
			return 0L;
		
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", ex.getName());
		initialValues.put("type", ex.getType());

		if (ex.getSets() > 0) {
			initialValues.put("sets", ex.getSets());
		}

		if (!ex.getIsCountdown() && ex.getTime() == 0L) {
			initialValues.put("is_countdown", ex.getIsCountdown());
		} else if (ex.getIsCountdown() && ex.getTime() > 0) {
			initialValues.put("time", ex.getTime());
			initialValues.put("time_type", ex.getTimeType());
			initialValues.put("is_countdown", ex.getIsCountdown());
		}

		if (ex.getDistance() > 0) {
			initialValues.put("distance", ex.getDistance());
			initialValues.put("distance_type", ex.getDistanceType());
		}
		
		if (ex.getIntervalNum() > 0) {
			initialValues.put("interval_num", ex.getIntervalNum());
		}

		initialValues.put("comment", ex.getComment() != null ? ex.getComment()
				: null);
		initialValues.put("deleted", false);

		return db.insert(DATABASE_TABLE_EXERCISE, null, initialValues);
	}

	public boolean deleteExercise(long exID) {
		ContentValues args = new ContentValues();
		args.put("deleted", true);

		return db.update(DATABASE_TABLE_EXERCISE, args, "_id = " + exID, null) > 0;
	}
	
	public boolean trueDeleteExercise(long exID) {
		return db.delete(DATABASE_TABLE_EXERCISE, "_id = " + exID, null) > 0;
	}

	public long createSet(Set set) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("exercise_id", set.getExerciseId());
		initialValues.put("reps", set.getReps());
		initialValues.put("weight", set.getWeight());

		return db.insert(DATABASE_TABLE_SET, null, initialValues);
	}

	public boolean deleteSet(long setID) {
		return db.delete(DATABASE_TABLE_SET, "_id = " + setID, null) > 0;
	}

	public boolean updateSet(Set set) {
		ContentValues args = new ContentValues();
		args.put("exercise_id", set.getExerciseId());
		args.put("reps", set.getReps());
		args.put("weight", set.getWeight());

		return db.update(DATABASE_TABLE_SET, args, "_id = " + set.getId(), null) > 0;
	}
	
	public long createInterval(Interval interval) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("exercise_id", interval.getExerciseId());
		initialValues.put("name", interval.getName());
		initialValues.put("time", interval.getTime());
		initialValues.put("time_type", interval.getTimeType());
		
		return db.insert(DATABASE_TABLE_INTERVAL, null, initialValues);
	}
	
	public boolean deleteInterval(long intervalID) {
		return db.delete(DATABASE_TABLE_INTERVAL, "_id = " + intervalID, null) > 0;
	}
	
	public boolean updateInterval(Interval interval) {
		ContentValues args = new ContentValues();
		args.put("exercise_id", interval.getExerciseId());
		args.put("name", interval.getName());
		args.put("time", interval.getTime());
		args.put("time_type", interval.getTimeType());
		
		return db.update(DATABASE_TABLE_INTERVAL, args, "_id = " + interval.getId(), null) > 0;
	}

}