package com.cwru.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cwru.model.Distance;
import com.cwru.model.Exercise;
import com.cwru.model.Interval;
import com.cwru.model.Set;
import com.cwru.model.Time;
import com.cwru.model.Workout;
import com.cwru.model.WorkoutResults;

/**
 * Handles all database transactions for the application.
 * @author scrilley
 * @author lkissling
 *
 */
public class DbAdapter {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private static final String TAG = "dbHelper";
	
	private static final String CREATE_WORKOUTS_TABLE = 
			"create table workouts (id integer primary key autoincrement, " +
			"name text not null, type text not null, exercise_sequence text not null, " + 
			"repeats text, repeats_sunday boolean, repeats_monday boolean, repeats_tuesday boolean, " + 
			"repeats_wednesday boolean, repeats_thursday boolean, repeats_friday boolean, repeats_saturday boolean, comment text);";
	
	private static final String CREATE_EXERCISES_TABLE = 
			"create table exercises (id integer primary key autoincrement, " + 
			"name text not null, type text not null, comment text, deleted boolean not null);";
	
	private static final String CREATE_SETS_TABLE = 
			"create table sets (id integer primary key autoincrement, " + 
			"exercise_id integer not null, reps integer, weight real);";
	
	private static final String CREATE_SET_RESULT_TABLE = 
			"create table set_result (id integer primary key autoincrement, " + 
			"workout_result_id integer, set_number integer, reps integer, weight real);";
	
	private static final String CREATE_DISTANCE_TABLE = 
			"create table distance (id integer primary key autoincrement, " + 
			"exercise_id integer not null, length real, units text);";
	
	private static final String CREATE_DISTANCE_RESULT_TABLE = 
			"create table distance_result (id integer primary key autoincrement, " + 
			"workout_result_id integer, length real, units text);";
	
	private static final String CREATE_TIME_TABLE = 
			"create table time (id integer primary key autoincrement, " + 
			"exercise_id integer not null, length integer, units text, is_countdown boolean);";
	
	private static final String CREATE_TIME_RESULT_TABLE = 
			"create table time_result (id integer primary key autoincrement, " + 
			"workout_result_id integer, length integer, units text);";
	
	private static final String CREATE_INTERVALS_TABLE = 
			"create table intervals (id integer primary key autoincrement, " + 
			"exercise_id integer not null, name text, length real, type text, units text);";
	
	private static final String CREATE_INTERVALS_RESULT_TABLE = 
			"create table intervals_result (id integer primary key autoincrement, " + 
			"workout_result_id integer, name text, length real, type text, units text);";
	
	private static final String CREATE_WORKOUT_RESULT_TABLE = 
			"create table workout_result (id integer primary key autoincrement, " + 
			"workout_id integer not null, exercise_id integer not null, " + 
			"date text not null);";
				
	private static final String DATABASE_NAME = "FitLoggerData";
	private static final String DATABASE_TABLE_WORKOUT = "workouts";
	private static final String DATABASE_TABLE_EXERCISE = "exercises";
	private static final String DATABASE_TABLE_SET = "sets";
	private static final String DATABASE_TABLE_DISTANCE = "distance";
	private static final String DATABASE_TABLE_TIME = "time";
	private static final String DATABASE_TABLE_INTERVAL = "intervals";
	private static final String DATABASE_TABLE_WORKOUT_RESULT = "workout_results";
	private static final String DATABASE_TABLE_SET_RESULTS = "set_result";
	private static final String DATABASE_TABLE_DISTANCE_RESULT = "distance_result";
	private static final String DATABASE_TABLE_TIME_RESULT = "time_result";
	private static final String DATABASE_TABLE_INTERVAL_RESULT = "intervals_result";
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
			db.execSQL(CREATE_TIME_TABLE);
			db.execSQL(CREATE_DISTANCE_TABLE);
			db.execSQL(CREATE_WORKOUT_RESULT_TABLE);
			db.execSQL(CREATE_SET_RESULT_TABLE);
			db.execSQL(CREATE_INTERVALS_RESULT_TABLE);
			db.execSQL(CREATE_TIME_RESULT_TABLE);
			db.execSQL(CREATE_DISTANCE_RESULT_TABLE);
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
	
	/** TODO Update to fit new database */
	public void storeWorkoutResult(WorkoutResults workoutResult) {
		open();
		// Get Date
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//get current date time with Date()
	    Date date = new Date();
	    String dateString = dateFormat.format(date);
		ContentValues values = new ContentValues();
		/*
		values.put("date", dateString);
		values.put("workout_id", workoutResult.getWorkoutId());
		values.put("exercise_id", workoutResult.getExerciseId());
		values.put("sets", workoutResult.getSetNumber());
		values.put("reps", workoutResult.getReps());
		values.put("weight", workoutResult.getWeight());
		values.put("time", workoutResult.getTime());
		values.put("time_type", workoutResult.getTimeType());
		values.put("distance", workoutResult.getDistance());
		values.put("interval", workoutResult.getInterval());
		values.put("comment", workoutResult.getComment());
		db.insert(DATABASE_TABLE_WORKOUT_RESULT, null, values);
		*/
		close();
	}
	
	/**
	 * Inserts row into the Workout table
	 * @param workout
	 */
	public void createWorkout(Workout workout) {
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", workout.getName());
		initialValues.put("type",workout.getType());
		// Initial Workout has blank exercise sequence
		initialValues.put("exercise_sequence", "");
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
	
	/**
	 * Returns the id for a workout by querying against the name
	 * @param workoutName
	 * @return
	 */
	public int getWorkoutIdFromName(String workoutName) {
		open();
		String query = "select id from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		int workoutId = -1;
		while (cursor.moveToNext()) {
			workoutId = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		close();
		return workoutId;
	}
	
	/** 
	 * Query the workout table for a workout by name
	 * @param workoutName
	 * @return
	 */
	public Workout getWorkoutFromName(String workoutName) {
		open();
		String query = "select * from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		Workout workout = null;
		while (cursor.moveToNext() ) {
			int workoutId = cursor.getInt(cursor.getColumnIndex("id"));
			String workoutType = cursor.getString(cursor.getColumnIndex("type"));
			String exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
			String repeats = cursor.getString(cursor.getColumnIndex("repeats"));
			int rptSunday = cursor.getInt(cursor.getColumnIndex("repeats_sunday"));
			int rptMonday = cursor.getInt(cursor.getColumnIndex("repeats_monday"));
			int rptTuesday = cursor.getInt(cursor.getColumnIndex("repeats_tuesday"));
			int rptWednesday = cursor.getInt(cursor.getColumnIndex("repeats_wednesday"));
			int rptThursday = cursor.getInt(cursor.getColumnIndex("repeats_thursday"));
			int rptFriday = cursor.getInt(cursor.getColumnIndex("repeats_friday"));
			int rptSaturday = cursor.getInt(cursor.getColumnIndex("repeats_sunday"));
			Log.d("Su-" + rptSunday + " | Mo-" + rptMonday + " | Tu-" + rptTuesday + " | We-" + rptWednesday + " | Th-" + rptThursday, "");
			Log.d("STEVE", "Su-" + rptSunday + " | Mo-" + rptMonday + " | Tu-" + rptTuesday + " | We-" + rptWednesday + " | Th-" + rptThursday);
			workout = new Workout(workoutId, workoutName, workoutType, exerciseSequence, repeats, rptSunday, rptMonday, rptTuesday, rptWednesday,
								  rptThursday, rptFriday, rptSaturday);
		}
		cursor.close();
		close();
		return workout;
	}
	
	/**
	 * Update the workout table with new information by querying for initial workout name
	 * @param workout
	 * @param initialWorkoutName
	 */
	public void updateWorkoutInformation(Workout workout, String initialWorkoutName) {
		String query = "update workouts " + 
					   "set name = '" + workout.getName() + "', type = '" + workout.getType() + "', repeats = '" + workout.getRepeatWeeks() + "'," +
					   "repeats_sunday = " + workout.getRepeatSunday() + ", repeats_monday = " + workout.getRepeatMonday() + ", repeats_tuesday = " + workout.getRepeatTuesday() + "," +
					   "repeats_wednesday = " + workout.getRepeatWednesday() + ", repeats_thursday = " + workout.getRepeatThursday() + ", repeats_friday = " + workout.getRepeatFriday() + 
					   ", repeats_saturday = " + workout.getRepeatSaturday() + " where name = '" + initialWorkoutName + "'";
		open();
		db.execSQL(query);
		close();
	}
	
	/**
	 * Get List of all workouts that are created in the database
	 * @return
	 */
	public Workout [] getAllWorkouts() { 
		open();
		ArrayList<Workout> workoutList = new ArrayList<Workout>();
		Cursor cursor = db.rawQuery("select * from workouts", new String [0]);
		//Workout(String workoutName, String workoutType,String workoutRepeatWeeks, int repeatSunday, int repeatMonday,
		//		int repeatTuesday, int repeatWednesday, int repeatThursday,	int repeatFriday, int repeatSaturday)
		while (cursor.moveToNext()) {
			int workoutId = cursor.getInt(cursor.getColumnIndex("id"));
			String workoutName = cursor.getString(cursor.getColumnIndex("name"));
			String workoutType = cursor.getString(cursor.getColumnIndex("type"));
			String workoutRepatWeeks = cursor.getString(cursor.getColumnIndex("repeats"));
			String exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
			int repeatSunday = cursor.getInt(cursor.getColumnIndex("repeats_sunday"));
			int repeatMonday = cursor.getInt(cursor.getColumnIndex("repeats_monday"));
			int repeatTuesday = cursor.getInt(cursor.getColumnIndex("repeats_tuesday"));
			int repeatWednesday = cursor.getInt(cursor.getColumnIndex("repeats_wednesday"));
			int repeatThursday = cursor.getInt(cursor.getColumnIndex("repeats_thursday"));
			int repeatFriday = cursor.getInt(cursor.getColumnIndex("repeats_friday"));
			int repeatSaturday = cursor.getInt(cursor.getColumnIndex("repeats_saturday"));
			Workout workout = new Workout(workoutId, workoutName, workoutType, exerciseSequence, workoutRepatWeeks, repeatSunday, repeatMonday, 
										  repeatTuesday, repeatWednesday, repeatThursday, repeatFriday, repeatSaturday);
			workoutList.add(workout);
		}
		cursor.close();
		close();
		return workoutList.toArray(new Workout [0]);
	}
	
	/**
	 * Does the workout exist.  Queries by name
	 * @param workoutName
	 * @return
	 */
	public boolean workoutNameExist(String workoutName) {
		open();
		boolean exists = false;
		String query = "select id from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			exists = true;
		}
		cursor.close();
		close();
		return exists;
	}
	
	/** TODO
	 * Return the exercise sequence belonging to the workout
	 * @return
	 */
	public String getExerciseSequence(String workoutName) {
		open();
		String query = "select exercise_sequence from workouts where name = '" + workoutName + "'";
		Cursor cursor = db.rawQuery(query, null);
		String exerciseSequence = "";
		while (cursor.moveToNext()) {
			exerciseSequence = cursor.getString(cursor.getColumnIndex("exercise_sequence"));
		}
		cursor.close();
		close();
		return exerciseSequence;
	}
	
	/**
	 * Update the exercise sequence for the workout
	 * @param exerciseSequence
	 * @param workoutName
	 */
	public void updateWorkoutExerciseSequence(String exerciseSequence, String workoutName) {
		open();
		String query = "update workouts set exercise_sequence = '" + exerciseSequence + "' where name = '" + workoutName + "'";
		Log.d("Update Exercise Sequence", query);
		db.execSQL(query);
		close();
	}
	
	/**
	 * Gets ArrayList of all exercises in the exercise database
	 * @return
	 */
	public ArrayList<Exercise> getAllExercises() {
		open();
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		String query = "select id, name, type, comment, deleted from exercises";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			Exercise exercise = new Exercise(id, name, type, comment, deleted);
			exerciseList.add(exercise);
		}
		close();
		return exerciseList;	
	}
	
	/**
	 * Return Array List of exercises that are marked undeleted 
	 * @return
	 */
	public ArrayList<Exercise> getAllUndeletedExercises() {
		open();
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		String query = "select id, name, type, comment, deleted from exercises where deleted = 0";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			Exercise exercise = new Exercise(id, name, type, comment, deleted);
			exerciseList.add(exercise);
		}
		close();
		return exerciseList;
	}
	
	/**
	 * Return Array List of all exercises that are marked deleted in the database
	 * @return
	 */
	public ArrayList<Exercise> getAllDeletedExercises() {
		open();
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		String query = "select id, name, type, comment, deleted from exercises where deleted = 1";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			Exercise exercise = new Exercise(id, name, type, comment, deleted);
			exerciseList.add(exercise);
		}
		close();
		return exerciseList;
	}
	
	/*
	public Cursor getAllExercises() { 		
		String columns [] = {"id", "name", "type", "sets", "time", "is_countdown", "distance", "distance_type", "intervals", "interval_sets", "comment", "deleted"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, null, null, null, null, null);
		if (cursor == null) { Log.d("Steve", "Cursor for exercises is null");}
		return cursor;
	}
	
	
	public Cursor getAllUndeletedExercises() {
		String columns [] = {"id", "name", "type", "sets", "time", "is_countdown", "distance", "distance_type", "intervals", "interval_sets", "comment", "deleted"};
		Cursor cursor = db.query(DATABASE_TABLE_EXERCISE, columns, "deleted = 0", null, null, null, "name");
		if (cursor == null) { Log.d("Steve", "Cursor for exercises is null");}
		return cursor;
	}

	*/
	
	/**
	 * Return Exercise from querying for the exercise id
	 * @param exerciseId
	 * @return
	 */
	public Exercise getExerciseFromId(Long exerciseId) {
		open();
		String query = "select * from exercises where id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		Exercise ex = null;
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			ex = new Exercise(id, name, type, comment, deleted);
		}
		cursor.close();
		close();

		return ex;

	}
	
	/**
	 * Query for the exercise from the exercise name
	 * @param name
	 * @return
	 */
	public Exercise getExerciseFromName(String name) {
		open();
		String query = "select * from exercises where name = '" + name
				+ "' and deleted = 0";
		Cursor cursor = db.rawQuery(query, null);

		Exercise ex = null;
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			int deleted = cursor.getInt(cursor.getColumnIndex("deleted"));
			ex = new Exercise(id, name, type, comment, deleted);
		}
		cursor.close();
		close();
		return ex;
	}
	
	/** 
	 * Query to create the input exercise in the database.  Function returns the id of the newly created exercise.
	 * @param exercise
	 * @return
	 */
	public int createExercise(Exercise exercise) {
		open();
		// Insert into exercise table
		String query = "insert into exercises (name, type, comment, deleted) values " + 
					   "('" + exercise.getName() + "','" + exercise.getType() + 
					   "','" + exercise.getComment() + "',false + ";
		db.execSQL(query);
		// Get id of newly created exercise
		query = "select id from exercises where name = '" + exercise.getName() + "'";
		Cursor cursor = db.rawQuery(query, null);
		int id = -1;
		if (cursor.moveToLast()) { 
			id = cursor.getInt(cursor.getColumnIndex("id"));
			exercise.setId(id);
		} else {
			// if there is a problem in the insert query, cursor.moveToNext will return false.  
			// Return -1 for error in insert
			close();
			cursor.close();
			return -1;
		}
		// Insert exercise specifications according to the exercise mode, set/distance/type/interval
		switch (exercise.getMode()) {
			case Exercise.SET_BASED_EXERCISE:
				createSet(exercise);
				break;
			case Exercise.DISTANCE_BASED_EXERCISE:
				createDistance(exercise);
				break;
			case Exercise.INTERVAL_BASED_EXERCISE:
				createInterval(exercise);
				break;				
			case Exercise.TIME_BASED_EXERCISE:
				createTime(exercise);
				break;
		}
		
		cursor.close();
		close();
		return id;
	}
	
	/**
	 * Exercise passed into this function has the set parameter defined. \n
	 * Inserts row into the set table.
	 * @param exercise
	 */
	private void createSet(Exercise exercise) {
		open();
		ArrayList<Set> setList = exercise.getSets();
		for (int i = 0; i < setList.size(); i++) {
			Set set = setList.get(i);
			String query = "insert into sets (exercise_id, reps, weight) values " + 
						   "(" + exercise.getId() + "," + set.getReps() + "," + set.getWeight() + ")";
			db.execSQL(query);
		}
		close();
	}
	
	/**
	 * Exercise passed into function has the distance parameter defined.  \n
	 * Inserts row into the distance table.
	 * @param exercise
	 */
	private void createDistance(Exercise exercise) {
		open();
		Distance distance = exercise.getDistance();
		String query = "insert into distance (exercise_id, length, units) values " + 
					   "(" + exercise.getId()+ "," + distance.getLength() + ",'" + distance.getUnits() + "')";
		db.execSQL(query);
		close();
	}
	
	/**
	 * Exercise passed into function has the interval parameter defined. \n
	 * Inserts row into the interval table.
	 * @param exercise
	 */
	private void createInterval(Exercise exercise) {
		open();
		ArrayList<Interval> intervalList = exercise.getInterval();
		for (int i = 0; i < intervalList.size(); i++) {
			Interval interval = intervalList.get(i);
			String query = "insert into interval (exercise_id, name, length, type, units) values " + 
					   "(" + exercise.getId() + ",'" + interval.getName() + "'," + interval.getLength() + "," + 
					   "'" + interval.getLength() + "','" + interval.getUnits() + "')";
			db.execSQL(query);					   
		}
		
		close();
	}
	
	/**
	 * Exercise passed into function has the time parameter defined. \n
	 * Inserts row into the Time table
	 * @param exercise
	 */
	private void createTime(Exercise exercise) {
		open();
		Time time = exercise.getTime();
		String query = "insert into time (exercise_id, length, units, is_count_up, is_countdown) values " +
					   "(" + exercise.getId() + "," + time.getLength() + ",'" + time.getUnits() + "'," + time.isCountUp() + "," + time.isCountdown() + ")";
		db.execSQL(query);
		close();
	}
/*
	public long createExercise(Exercise ex) throws IllegalArgumentException {
		
		if (ex.getName() == null || ex.getName().length() == 0
				|| ex.getType() == null || ex.getType().length() == 0) {
			throw new IllegalArgumentException("Exercise name and type required.");
		}
		Exercise exists = getExerciseFromName(ex.getName());
		if (exists != null && exists.getDeleted() == 0) {
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
		
		open();
		Long id = db.insert(DATABASE_TABLE_EXERCISE, null, initialValues);
		close();
		return id;
	}

*/
	
	/**
	 * Update a row in the exercise table.  Also updates the corresponding exercise mode table (distance, interval, time, set).
	 * @param exercise
	 */
	public void updateExercise(Exercise exercise) {
		open();
		// Update row in the exercise table
		String query = "update exercises " + 
					   "set name = '" + exercise.getName() + "', type = '" + exercise.getType() + "',comment='" + exercise.getComment() + "'," + exercise.getDeleted() + " " + 
					   "where id = " + exercise.getId();
		db.execSQL(query);
		
		// Update exercise specifications according to the exercise mode, set/distance/type/interval
		switch (exercise.getMode()) {
			case Exercise.SET_BASED_EXERCISE:
				updateSet(exercise);
				break;
			case Exercise.DISTANCE_BASED_EXERCISE:
				updateDistance(exercise);
				break;
			case Exercise.INTERVAL_BASED_EXERCISE:
				updateInterval(exercise);
				break;				
			case Exercise.TIME_BASED_EXERCISE:
				updateTime(exercise);
				break;
		}
		close();
	}
	
	/**
	 * Updates the set rows in the set table that are related to the exercise being passed in. 
	 * The set parameter of the exercise must have the id value assigned.
	 * @param exercise
	 */
	public void updateSet(Exercise exercise) { 
		open();
		ArrayList<Set> setList = exercise.getSets();
		for (int i = 0; i < setList.size(); i++) {
			Set set = setList.get(i);
			String query = "update sets " + 
						   "set reps = " + set.getReps() + ", weight = " + set.getWeight() + " " + 
						   "where id = " + set.getId();
			db.execSQL(query);
		}		
		close();
	}
	
	/** 
	 * Update the distance row in the distance table that is related to the exercise being passed in.
	 * The distance parameter of the exercise must have the id value assigned.
	 * @param exercise
	 */
	public void updateDistance(Exercise exercise) {
		open();
		Distance distance = exercise.getDistance();
		String query = "update distance " +
					   "set length = " + distance.getLength() + ", units='" + distance.getUnits() + "' " +
					   "where id = " + distance.getId();
		db.execSQL(query);
		close();
	}
	
	/**
	 * Update the interval rows in the interval table that are related to the exercise being passed in.
	 * This interval parameter of the exercise must have the id value assigned for each interval.
	 * @param exercise
	 */
	public void updateInterval(Exercise exercise) { 
		open();
		ArrayList<Interval> intervalList = exercise.getInterval();
		for (int i = 0; i < intervalList.size(); i++) {
			Interval interval = intervalList.get(i);
			String query = "update intervals " + 
						   "set name = '" + interval.getName() + "', length = " + interval.getLength() + " " + 
						   "type = '" + interval.getType() + "', units = '" + interval.getType() + "' " +
						   "where id = " + interval.getId();
			db.execSQL(query);
			close();						   
		}
	}
	
	/**
	 * Update the time rows in the time table that are related to the exercise being passed in.
	 * The time parameter of the exercise must have the id value assigned to the the time.
	 * @param exercise
	 */
	public void updateTime(Exercise exercise) {
		open();
		Time time = exercise.getTime();
		String query = "update time " + 
					   "set length = " + time.getLength() + ", units = '" + time.getUnits() + "' " + 
					   "where id = " + time.getId();
		db.execSQL(query);
		close();
	}
	
	/*
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
*/
	/**
	 * Performs soft delete on the exercise by marking it deleted.
	 * @param exerciseId
	 */
	public void deleteExercise(long exerciseId) {
		open();
		String query = "update exercises set deleted=true where id = " + exerciseId;
		db.execSQL(query);
		close();
	}
	
	/**
	 * Performs a hard delete on an exercies by removing it from all exercise tables.
	 * @param exerciseId
	 */
	public void trueDeleteExercise(long exerciseId) {
		open();
		String query = "delete from exercises where id = " + exerciseId;
		db.execSQL(query);
		query = "delete from sets where exerciseId = " + exerciseId;
		db.execSQL(query);
		query = "delete from distance where exerciseId = " + exerciseId;
		db.execSQL(query);
		query = "delete from time where exerciseId = " + exerciseId;
		db.execSQL(query);
		query = "delete from intervals where exerciseId = " + exerciseId;
		db.execSQL(query);
		close();
	}
	
	/*
	public boolean deleteExercise(long exID) {
		ContentValues args = new ContentValues();
		args.put("deleted", 1);

		return db.update(DATABASE_TABLE_EXERCISE, args, "_id = " + exID, null) > 0;
	}
	*/
	/*
	public boolean trueDeleteExercise(long exID) {
		return db.delete(DATABASE_TABLE_EXERCISE, "_id = " + exID, null) > 0;
	}
	*/
	
	/**
	 * Returns the list of set objects that belong to the input exercise id.
	 * @param exerciseId
	 * @return
	 */
	public ArrayList<Set> getSetsForExercise(int exerciseId) {
		ArrayList<Set> setList = new ArrayList<Set>();
		open();
		String query = "select id, reps, weight from sets where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int reps = cursor.getInt(cursor.getColumnIndex("reps"));
			double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
			Set set = new Set(id, exerciseId, reps, weight);
			setList.add(set);
		}
		cursor.close();
		close();
		return setList;
	}
	
	/**
	 * Returns the list of interval objects that belong to the exercise id
	 * @param exerciseId
	 * @return
	 */
	public ArrayList<Interval> getIntervalsForExercise(int exerciseId) {
		ArrayList<Interval> intervalList = new ArrayList<Interval>();
		open();
		String query = "select id, name, length, type, units from intervals where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			double length = cursor.getDouble(cursor.getColumnIndex("length"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			Interval interval = new Interval(id, exerciseId, name, length, type, units);
			intervalList.add(interval);
		}
		cursor.close();
		close();
		return intervalList;
	}
	
	/**
	 * Returns the distance object that belongs to the exercise id
	 * @param exerciseId
	 * @return
	 */
	public Distance getDistanceForExercise(int exerciseId) {
		Distance distance = null;
		open();
		String query = "select id, length, units from distance where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			double length = cursor.getDouble(cursor.getColumnIndex("length"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			distance = new Distance(id, exerciseId, length, units);
		}
		cursor.close();
		close();
		return distance;
	}
	
	/**
	 * Returns the Time object that belongs to the exercise id.
	 * @param exerciseId
	 * @return
	 */
	public Time getTimeForExercise(int exerciseId) {
		Time time = null;
		open();
		String query = "select id, length, units, is_count_up, is_countdown from time where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int length = cursor.getInt(cursor.getColumnIndex("length"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			boolean isCountUp = cursor.getInt(cursor.getColumnIndex("is_count_up")) > 0;
			boolean isCountdown = cursor.getInt(cursor.getColumnIndex("is_countdown")) > 0;
			time = new Time(id, exerciseId, length, units, isCountUp, isCountdown);
		}
		cursor.close();
		close();
		return time;
	}
	
	
	/*
	public Cursor getSetsForExercise(long exId) {
		String columns[] = {"_id", "exercise_id", "weight", "reps"};
		String selection = "exercise_id = ?";
		String orderBy = "_id";
		String[] selectionArgs = {Long.toString(exId)};
		Cursor cursor = db.query(DATABASE_TABLE_SET, columns, selection, selectionArgs, null, null, orderBy);
		if (cursor == null) { Log.d("Steve", "Cursor for sets is null");}
		return cursor;
	}
	*/
	
	/** TODO
	 * Refactor these two methods
	 * @param exId
	 * @return
	 */
	/*
	public Set[] getSetsForExercise1(long exId) {
		open();
		String columns [] = {"id", "exercise_id", "weight", "reps"};
		String selection = "exercise_id = ?";
		String orderBy = "_id";
		String [] selectionArgs = {Long.toString(exId)};
		Cursor cursor = db.query(DATABASE_TABLE_SET, columns, selection, selectionArgs, null, null, orderBy);
		ArrayList<Set> sets = new ArrayList<Set> ();
		while (cursor.moveToNext()) {
			long id = cursor.getLong(cursor.getColumnIndex("_id"));
			long exerciseId = cursor.getLong(cursor.getColumnIndex("exercise_id"));
			double weight = cursor.getInt(cursor.getColumnIndex("weight"));
			int reps = cursor.getInt(cursor.getColumnIndex("reps"));
			Set set = new Set(id,exerciseId, reps, weight);
			sets.add(set);
		}
		close();
		return sets.toArray(new Set[0]);
	}
	*/
	
	/*
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
	*/
}
