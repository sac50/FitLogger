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
			+ "text not null, comment text);";
	private static final String CREATE_EXERCISES_TABLE = 
			"create table exercises (_id integer primary key autoincrement, "
			+ "name text not null, type text not null, sets integer, "
			+ "time integer, time_type text, is_countdown boolean, distance real,"
			+ "distance_type text, intervals integer, interval_sets integer, "
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
	
	public long createWorkout(Workout workout) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", workout.getName());
		initialValues.put("workout_type",workout.getType());
		// Initial Workout has blank exercise sequence
		initialValues.put("exercise_Sequence", "");
		//initialValues.put("comment", workout.getComment());
		
		return db.insert(DATABASE_TABLE_WORKOUT, null, initialValues);
	}
	
	public Cursor getAllWorkouts() { 
		return db.rawQuery("select * from workouts", new String [0]);
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
		String columns [] = {"_id", "name", "type", "sets", "time", "is_countdown", "distance", "distance_type", "intervals", "interval_sets", "comment", "deleted"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, null, null, null, null, null);
		if (cursor == null) { Log.d("Steve", "Cursor for exercises is null");}
		return cursor;
	}
	
	public Cursor getAllUndeletedExercises() {
		String columns [] = {"_id", "name", "type", "sets", "time", "is_countdown", "distance", "distance_type", "intervals", "interval_sets", "comment", "deleted"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, "deleted = 0", null, null, null, "name");
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
	
	public Exercise getExerciseFromName(String name) {
		String query = "select * from exercises where name = '" + name
				+ "' and deleted = 0";
		Cursor cursor = db.rawQuery(query, null);

		Exercise ex = new Exercise();
		while (cursor.moveToNext()) {
			ex.setId(cursor.getLong(cursor.getColumnIndex("_id")));
			ex.setName(cursor.getString(cursor.getColumnIndex("name")));
			ex.setType(cursor.getString(cursor.getColumnIndex("type")));
			ex.setSets(cursor.getInt(cursor.getColumnIndex("sets")));
			ex.setIsCountdown(cursor.getInt(cursor.getColumnIndex("is_countdown")) == 1);
			ex.setTime(cursor.getLong(cursor.getColumnIndex("time")));
			ex.setTimeType(cursor.getString(cursor.getColumnIndex("time_type")));
			ex.setDistance(cursor.getDouble(cursor.getColumnIndex("distance")));
			ex.setDistanceType(cursor.getString(cursor.getColumnIndex("distance_type")));
			ex.setIntervals(cursor.getInt(cursor.getColumnIndex("intervals")));
			ex.setIntervalSets(cursor.getInt(cursor.getColumnIndex("interval_sets")));
			ex.setComment(cursor.getString(cursor.getColumnIndex("comment")));
		}
		
		cursor.close();
		return ex;
	}

	public long createExercise(Exercise ex) throws IllegalArgumentException {
		
		if (ex.getName() == null || ex.getName().length() == 0
				|| ex.getType() == null || ex.getType().length() == 0) {
			throw new IllegalArgumentException("Exercise name and type required.");
		}
		Exercise exists = getExerciseFromName(ex.getName());
		if (exists.getId() != null && exists.getId() > 0L) {
			throw new IllegalArgumentException("An exercise with this name already exists.");
		}
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", ex.getName());
		initialValues.put("type", ex.getType());

		if (ex.getSets() > 0) {
			initialValues.put("sets", ex.getSets());
		}

		initialValues.put("is_countdown", ex.getIsCountdown());
		
		if (ex.getIsCountdown() && ex.getTime() > 0) {
			initialValues.put("time", ex.getTime());
			initialValues.put("time_type", ex.getTimeType());
		}

		if (ex.getDistance() > 0) {
			initialValues.put("distance", ex.getDistance());
			initialValues.put("distance_type", ex.getDistanceType());
		}
		
		if (ex.getIntervals() > 0) {
			initialValues.put("intervals", ex.getIntervals());
			initialValues.put("interval_sets", ex.getIntervalSets());
		}

		initialValues.put("comment", ex.getComment() != null ? ex.getComment()
				: null);
		initialValues.put("deleted", 0);

		return db.insert(DATABASE_TABLE_EXERCISE, null, initialValues);
	}
	
	public int updateExercise(Exercise ex) {
		ContentValues newValues = new ContentValues();
		newValues.put("_id", ex.getId());
		newValues.put("name", ex.getName());
		newValues.put("type", ex.getType());

		if (ex.getSets() > 0) {
			newValues.put("sets", ex.getSets());
		}

		newValues.put("is_countdown", ex.getIsCountdown());
		
		if (ex.getIsCountdown() && ex.getTime() > 0) {
			newValues.put("time", ex.getTime());
			newValues.put("time_type", ex.getTimeType());
		}

		if (ex.getDistance() > 0) {
			newValues.put("distance", ex.getDistance());
			newValues.put("distance_type", ex.getDistanceType());
		}
		
		if (ex.getIntervals() > 0) {
			newValues.put("intervals", ex.getIntervals());
			newValues.put("interval_sets", ex.getIntervalSets());
		}

		newValues.put("comment", ex.getComment() != null ? ex.getComment()
				: null);
		newValues.put("deleted", 0);
		
		String[] arguments = {ex.getId().toString()};
		return db.update(DATABASE_TABLE_EXERCISE, newValues, "_id = ?", arguments);
	}

	public boolean deleteExercise(long exID) {
		ContentValues args = new ContentValues();
		args.put("deleted", 1);

		return db.update(DATABASE_TABLE_EXERCISE, args, "_id = " + exID, null) > 0;
	}
	
	public boolean trueDeleteExercise(long exID) {
		return db.delete(DATABASE_TABLE_EXERCISE, "_id = " + exID, null) > 0;
	}

	public Cursor getSetsForExercise(long exId) {
		String columns[] = {"_id", "exercise_id", "weight", "reps"};
		String selection = "exercise_id = ?";
		String orderBy = "_id";
		String[] selectionArgs = {Long.toString(exId)};
		Cursor cursor = db.query(DATABASE_TABLE_SET, columns, selection, selectionArgs, null, null, orderBy);
		if (cursor == null) { Log.d("Steve", "Cursor for sets is null");}
		return cursor;
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
	
	public Cursor getIntervalsForExercise(long exId) {
		String columns[] = {"_id", "exercise_id", "name", "time", "time_type"};
		String selection = "exercise_id = ?";
		String selectionArgs[] = {Long.toString(exId)};
		String orderBy = "_id";
		Cursor cursor = db.query(DATABASE_TABLE_INTERVAL, columns, selection, selectionArgs, null, null, orderBy);
		if (cursor == null) { Log.d("Steve", "Cursor for intervals is null");}
		return cursor;
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
