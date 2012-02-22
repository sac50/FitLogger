package com.cwru.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cwru.model.Exercise;
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
			+ "time integer, time_type string, is_countdown boolean, distance real,"
			+ "distance_type text, interval integer, comment text, deleted boolean not null);";
	private static final String CREATE_SETS_TABLE = 
			"create table sets (_id integer primary key autoincrement, "
			+ "exercise_id integer not null, reps integer not null, "
			+ "weight real not null);";
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
		Log.d("Workout in DB CREATE STATEMENT Name: ", workout.getName());
		Log.d("Type: ", workout.getType());
		Log.d("Sequence", workout.getExerciseSequence());
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", workout.getName());
		initialValues.put("workout_type",workout.getType());
		initialValues.put("exercise_Sequence",workout.getExerciseSequence());
		//initialValues.put("comment", workout.getComment());
		
		return db.insert(DATABASE_TABLE_WORKOUT, null, initialValues);
	}
	
	public Cursor getAllWorkouts() { 
		return db.rawQuery("select * from workouts", new String [0]);
	}
	
	public Cursor getAllExercises() { 
		String columns [] = {"name"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, null, null, null, null, null);
		if (cursor == null) { Log.d("Steve", "Cursor for exercises is null");}
		return cursor;
		//return db.rawQuery("select * from exercises", new String [0]);
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

		return db
				.update(DATABASE_TABLE_SET, args, "_id = " + set.getId(), null) > 0;
	}

}