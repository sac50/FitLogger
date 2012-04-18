package com.cwru.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cwru.model.BodyGoal;
import com.cwru.model.CustomGoal;
import com.cwru.model.Distance;
import com.cwru.model.DistanceResult;
import com.cwru.model.Exercise;
import com.cwru.model.ExerciseGoal;
import com.cwru.model.Interval;
import com.cwru.model.IntervalResult;
import com.cwru.model.IntervalSet;
import com.cwru.model.Set;
import com.cwru.model.SetResult;
import com.cwru.model.Time;
import com.cwru.model.TimeResult;
import com.cwru.model.Workout;
import com.cwru.model.WorkoutResult;
import com.cwru.utils.MeasurementConversions;

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
			"exercise_id integer not null, length integer, units text);";
	
	private static final String CREATE_TIME_RESULT_TABLE = 
			"create table time_result (id integer primary key autoincrement, " + 
			"workout_result_id integer, length integer, units text);";
	
	private static final String CREATE_INTERVAL_SETS_TABLE = 
			"create table interval_sets (id integer primary key autoincrement, " + 
			"interval_id integer not null, name text, length real, type text, units text);";
	
	private static final String CREATE_INTERVALS_TABLE = 
			"create table intervals ( id integer primary key autoincrement, " + 
			"exercise_id integer, num_repeats integer);";
	
	private static final String CREATE_INTERVALS_RESULT_TABLE = 
			"create table interval_result ( id integer primary key autoincrement, " +
			"workout_result_id integer, interval_id integer, " +
			"interval_set_num integer, interval_set_id, " +
			"length real, units text);";
	
	private static final String CREATE_WORKOUT_RESULT_TABLE = 
			"create table workout_result (id integer primary key autoincrement, " + 
			"workout_id integer not null, exercise_id integer not null, " + 
			"date text not null);";
	
	private static final String CREATE_EXERCISE_GOALS_TABLE =
			"create table exercise_goals (id integer primary key autoincrement, " +
			"name text not null, is_cumulative int not null, is_completed int not null, " +
			"mode integer not null, type integer not null, exercise_id integer, goal_one real, " +
			"goal_two real, current_best_one real, current_best_two real, unit integer);";
	
	private static final String CREATE_BODY_GOALS_TABLE =
			"create table body_goals (id integer primary key autoincrement, " +
			"category text not null, unit text not null, started real not null, " +
			"current real not null, goal real not null, is_completed integer not null);";
	
	private static final String CREATE_CUSTOM_GOALS_TABLE =
			"create table custom_goals (id integer primary key autoincrement, " +
			"name text not null, description text not null, is_completed int not null);";
	
	private static final String CREATE_CALENDAR_TABLE = 
			"create table calendar (id integer primary key autoincrement, " + 
			"date text not null, workout_id);";
				
	private static final String DATABASE_NAME = "FitLoggerData";
	private static final String DATABASE_TABLE_WORKOUT = "workouts";
	private static final String DATABASE_TABLE_EXERCISE = "exercises";
	private static final String DATABASE_TABLE_SET = "sets";
	private static final String DATABASE_TABLE_DISTANCE = "distance";
	private static final String DATABASE_TABLE_TIME = "time";
	private static final String DATABASE_TABLE_INTERVAL = "intervals";
	private static final String DATABASE_TABLE_INTERVAL_SET = "interval_sets";
	private static final String DATABASE_TABLE_WORKOUT_RESULT = "workout_results";
	private static final String DATABASE_TABLE_SET_RESULTS = "set_result";
	private static final String DATABASE_TABLE_DISTANCE_RESULT = "distance_result";
	private static final String DATABASE_TABLE_TIME_RESULT = "time_result";
	private static final String DATABASE_TABLE_INTERVAL_RESULT = "intervals_result";
	private static final String DATABASE_TABLE_EXERCISE_GOAL = "exercise_goals";
	private static final String DATABASE_TABLE_BODY_GOAL = "body_goals";
	private static final String DATABASE_TABLE_CUSTOM_GOAL = "custom_goals";
	private static final String DATABASE_TABLE_CALENDAR = "calendar";
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
			db.execSQL(CREATE_INTERVAL_SETS_TABLE);
			db.execSQL(CREATE_TIME_TABLE);
			db.execSQL(CREATE_DISTANCE_TABLE);
			db.execSQL(CREATE_WORKOUT_RESULT_TABLE);
			db.execSQL(CREATE_SET_RESULT_TABLE);
			db.execSQL(CREATE_INTERVALS_RESULT_TABLE);
			db.execSQL(CREATE_TIME_RESULT_TABLE);
			db.execSQL(CREATE_DISTANCE_RESULT_TABLE);
			db.execSQL(CREATE_EXERCISE_GOALS_TABLE);
			db.execSQL(CREATE_BODY_GOALS_TABLE);
			db.execSQL(CREATE_CUSTOM_GOALS_TABLE);
			db.execSQL(CREATE_CALENDAR_TABLE);
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
		/** TODO 
		 * Check to see if db.close removes db related error messages
		 */
		//db.close(); 
	}
	
	/**
	 * Inserts row into the Workout table
	 * @param workout
	 */
	public int createWorkout(Workout workout) {
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
		String selectId = "select id from workouts where name = '" + workout.getName() + "'";
		Cursor cursor = db.rawQuery(selectId, null);
		int workoutId = 0;
		while (cursor.moveToNext()) {
			workoutId = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		close();
		return workoutId;
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
	
	public Workout getWorkoutFromId(int id) {
		open();
		String query = "select * from workouts where id = " + id;
		Cursor cursor = db.rawQuery(query, null);
		Workout workout = null;
		while (cursor.moveToNext() ) {
			int workoutId = cursor.getInt(cursor.getColumnIndex("id"));
			String workoutName = cursor.getString(cursor.getColumnIndex("name"));
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
					   "set name = '" + workout.getName() + "', type = '" + workout.getType() + " where name = '" + initialWorkoutName + "'";
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
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = getExerciseMode(id);
			Exercise exercise = new Exercise(id, name, type, comment, deleted, mode);
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
		String query = "select id, name, type, comment, deleted from exercises where deleted = 0 order by name";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = getExerciseMode(id);
			Exercise exercise = new Exercise(id, name, type, comment, deleted, mode);
			exerciseList.add(exercise);
		}
		cursor.close();
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
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = getExerciseMode(id);
			Exercise exercise = new Exercise(id, name, type, comment, deleted, mode);
			exerciseList.add(exercise);
		}
		close();
		return exerciseList;
	}
	
	/**
	 * Retrieve all exercises that match the provided type and mode
	 * @param type
	 * @param mode
	 * @return ArrayList<Exercise>
	 */
	public ArrayList<Exercise> getAllExercisesForTypeAndMode(String type, int mode) {
		open();
		
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		
		String query = "select * from exercises where type = '" + type + "'";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			if (mode != getExerciseMode(id)) {
				continue;
			}
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			exercises.add(new Exercise(id, name, type, comment, deleted, mode));
		}
		
		cursor.close();
		close();
		return exercises;
	}
	
	/**
	 * Return Exercise from querying for the exercise id
	 * @param exerciseId
	 * @return
	 */
	public Exercise getExerciseFromId(int exerciseId) {
		open();
		String query = "select * from exercises where id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		Exercise ex = null;
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = this.getExerciseMode(id);
			ex = new Exercise(id, name, type, comment, deleted, mode);
			/**
			 * TODO May not be necessary
			 * Query mode table to get distance, set array, interval array or time
			 */
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
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = getExerciseMode(id);
			ex = new Exercise(id, name, type, comment, deleted, mode);
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
	public int createExercise(Exercise exercise) throws IllegalArgumentException {
		open();
		
		//check if an exercise with this name already exists in table.
		String query = "select id from exercises where name = '" + exercise.getName() + "'";
		Cursor  cursor= db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			cursor.close();
			throw new IllegalArgumentException("An exercise with this name already exists.");
		} else {
			cursor.close();
		}
		
		// Insert into exercise table
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", exercise.getName());
		initialValues.put("type", exercise.getType());
		initialValues.put("deleted", 0);
		db.insert(DATABASE_TABLE_EXERCISE, null, initialValues);
//		String query = "insert into exercises (name, type, comment, deleted) values " + 
//					   "('" + exercise.getName() + "','" + exercise.getType() + 
//					   "','" + exercise.getComment() + "',0";
//		db.execSQL(query);
		
		cursor = db.rawQuery(query, null);
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
			case Exercise.COUNTUP_BASED_EXERCISE:
				break;
			case Exercise.COUNTDOWN_BASED_EXERCISE:
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
		Interval interval = exercise.getInterval();
		ContentValues initialValues = new ContentValues();
		initialValues.put("exercise_id", exercise.getId());
		initialValues.put("num_repeats", interval.getNumRepeats());
		interval.setId((int) db.insert(DATABASE_TABLE_INTERVAL, null, initialValues));
		close();
		createIntervalSets(interval);
	}
	
	private void createIntervalSets(Interval interval) {
		open();
		ArrayList<IntervalSet> intervalSets = interval.getIntervalSets();
		for (int i = 0; i < intervalSets.size(); i++) {
			IntervalSet intervalSet = intervalSets.get(i);
			String query = "insert into interval_sets (interval_id, name, length, type, units) values " +
						"(" + interval.getId() + ",'" + intervalSet.getName() + "'," + intervalSet.getLength() + "," +
						"'" + intervalSet.getType() + "','" + intervalSet.getUnits() + "')";
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
		String query = "insert into time (exercise_id, length, units) values " +
					   "(" + exercise.getId() + "," + time.getLength() + ",'" + time.getUnits() + "')";
		db.execSQL(query);
		close();
	}
	
	/**
	 * Inserts row into the Exercise Goal table
	 * 
	 * @param exGoal
	 */
	public void createExerciseGoal(ExerciseGoal exGoal) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", exGoal.getName());
		initialValues.put("is_cumulative", exGoal.getIsCumulative() ? 1 : 0);
		initialValues.put("is_completed", 0);
		initialValues.put("type", exGoal.getType());
		initialValues.put("mode", exGoal.getMode());
		initialValues.put("goal_one", exGoal.getGoalOne());
		if (exGoal.getExerciseId() > 0) {
			initialValues.put("exercise_id", exGoal.getExerciseId());
		}
		if (exGoal.getGoalTwo() > 0) {
			initialValues.put("goal_two", exGoal.getGoalTwo());
		}
		if (exGoal.getUnit() != null && exGoal.getUnit().length() > 0) {
			initialValues.put("unit", exGoal.getUnit());
		}
		
		if (exGoal.getMode() != ExerciseGoal.SET) {
			String exType = new String();
			
			switch (exGoal.getType()) {
			
			case ExerciseGoal.RUN:
				exType = "Cardio - Run";
				break;
			case ExerciseGoal.SWIM:
				exType = "Cardio - Swim";
				break;
			case ExerciseGoal.BIKE:
				exType = "Cardio - Bike";
				break;
			case ExerciseGoal.SKI:
				exType = "Cardio - Ski";
				break;
			case ExerciseGoal.PADDLE:
				exType = "Cardio - Row/Paddle";
				break;
			default:
				break;
			}
			
			initialValues.put("current_best_one", 0);
		}
		
		open();
		db.insert(DATABASE_TABLE_EXERCISE_GOAL, null, initialValues);
		close();
	}
	
	/**
	 * Inserts row into the Body Goal table
	 * 
	 * @param BodyGoal goal
	 */
	public void createBodyGoal(BodyGoal goal) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("category", goal.getCategory());
		initialValues.put("unit", goal.getUnit());
		initialValues.put("started", goal.getStarted());
		initialValues.put("current", goal.getCurrent());
		initialValues.put("goal", goal.getGoal());
		initialValues.put("is_completed", goal.isCompleted());
		
		open();
		db.insert(DATABASE_TABLE_BODY_GOAL, null, initialValues);
		close();
	}
	
	/**
	 * Inserts row into the Custom Goal table
	 * 
	 * @param CustomGoal goal
	 */
	public void createCustomGoal(CustomGoal goal) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("name", goal.getName());
		initialValues.put("description", goal.getDescription());
		initialValues.put("is_completed", goal.getIsCompleted() ? 1 : 0);
		
		open();
		db.insert(DATABASE_TABLE_CUSTOM_GOAL, null, initialValues);
		close();
	}

	/**
	 * Update a row in the exercise table.  Also updates the corresponding exercise mode table (distance, interval, time, set).
	 * @param exercise
	 */
	public void updateExercise(Exercise exercise) {
		open();
		// Update row in the exercise table
		String query = "update exercises " + 
					   "set name = '" + exercise.getName() + "', type = '" + exercise.getType() + "',comment='" + exercise.getComment() + "' " + 
					   "where id = " + exercise.getId();
		db.execSQL(query);
		
		// Update exercise specifications according to the exercise mode, set/distance/type/interval
		switch (exercise.getMode()) {
			case Exercise.SET_BASED_EXERCISE:
				updateSets(exercise);
				break;
			case Exercise.DISTANCE_BASED_EXERCISE:
				updateDistance(exercise);
				break;
			case Exercise.INTERVAL_BASED_EXERCISE:
				updateInterval(exercise);
				break;				
			case Exercise.COUNTUP_BASED_EXERCISE:
				break;
			case Exercise.COUNTDOWN_BASED_EXERCISE:
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
	public void updateSets(Exercise exercise) { 
		open();
		ArrayList<Set> setList = exercise.getSets();
		String query;
		for (int i = 0; i < setList.size(); i++) {
			Set set = setList.get(i);
			if (set.getId() > 0) {
				query = "update sets " + 
							   "set reps = " + set.getReps() + ", weight = " + set.getWeight() + " " + 
							   "where id = " + set.getId();
			} else {
				query = "insert into sets (exercise_id, reps, weight) values " + 
						   "(" + exercise.getId() + "," + set.getReps() + "," + set.getWeight() + ")";
			}
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
	 * TODO: finish this method
	 */
	/**
	 * Update the interval rows in the interval table that are related to the exercise being passed in.
	 * This interval parameter of the exercise must have the id value assigned for each interval.
	 * @param exercise
	 */
	public void updateInterval(Exercise exercise) { 
		open();
		Interval interval = exercise.getInterval();
		String query = "update intervals " +
					"set num_repeats = " + interval.getNumRepeats() + " " +
					"where id = " + interval.getId();
		db.execSQL(query);
		ArrayList<IntervalSet> intervalList = interval.getIntervalSets();
		for (int i = 0; i < intervalList.size(); i++) {
			IntervalSet intervalSet = intervalList.get(i);
			if (intervalSet.getId() > 0) {
				query = "update interval_sets " + 
								"set name = '" + intervalSet.getName() + "', length = " + intervalSet.getLength() + ", " + 
								"type = '" + intervalSet.getType() + "', units = '" + intervalSet.getUnits() + "' " +
								"where id = " + intervalSet.getId();
			} else {
				query = "insert into interval_sets (interval_id, name, length, type, units) values " + 
								"(" + interval.getId() + ", '" + intervalSet.getName() + "', " + intervalSet.getLength() +
								", '" + intervalSet.getType() + "', '" + intervalSet.getUnits() + "')";
			}
			db.execSQL(query);						   
		}
		close();
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
	
	public void deleteSet(int setId) {
		open();
		String query = "delete from sets where id = " + setId;
		db.execSQL(query);
		close();
	}
	
	public void deleteIntervalSet(int intervalSetId) {
		open();
		String query = "delete from intervals where id = " + intervalSetId;
		db.execSQL(query);
		close();
	}
	
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
	 * Returns the interval object that belongs to the exercise id
	 * @param exerciseId
	 * @return
	 */
	public Interval getIntervalForExercise(int exerciseId) {
		Interval interval = new Interval();
		ArrayList<IntervalSet> intervalSets = new ArrayList<IntervalSet>();
		open();
		String query = "select * from intervals where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			interval.setId(cursor.getInt(cursor.getColumnIndex("id")));
			interval.setNumRepeats(cursor.getInt(cursor.getColumnIndex("num_repeats")));
		}
		cursor.close();
		
		query = "select id, name, length, type, units from interval_sets where interval_id = " + interval.getId();
		cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			double length = cursor.getDouble(cursor.getColumnIndex("length"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			IntervalSet intervalSet = new IntervalSet(id, interval.getId(), name, length, type, units);
			intervalSets.add(intervalSet);
		}
		cursor.close();
		close();
		interval.setIntervalSets(intervalSets);
		return interval;
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
		String query = "select id, length, units from time where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int length = cursor.getInt(cursor.getColumnIndex("length"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			time = new Time(id, exerciseId, length, units);
		}
		cursor.close();
		close();
		return time;
	}
	
	/**
	 * TODO CHANGE TABLE NAMES TO USE THE CONSTANTS DEFINED AT TOP
	 */
	/**
	 * Returns the exercise type for the exercise, (Distance, Set, Interval, Time).  
	 * Returns -1 when exercise is not found.
	 * @param exerciseId
	 * @return
	 */
	public int getExerciseMode(int exerciseId) {
		open();
		int mode = -1;
		/* Query all exercise type tables */
		/* Set Table */
		String query = "select id from sets where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			mode = Exercise.SET_BASED_EXERCISE;
			cursor.close();
			close();
			return mode;
		}
		cursor.close();
		/* Time Table */ 
		query = "select id from time where exercise_id = " + exerciseId;
		Cursor cursor1 = db.rawQuery(query, null);
		if (cursor1.moveToNext()) {
			mode = Exercise.COUNTDOWN_BASED_EXERCISE;
			cursor1.close();
			close();
			return mode;
		}
		cursor1.close();
		/* Interval Table */
		query = "select id from intervals where exercise_id = " + exerciseId;
		Cursor cursor2 = db.rawQuery(query, null);
		if (cursor2.moveToNext()) {
			mode = Exercise.INTERVAL_BASED_EXERCISE;
			cursor2.close();
			close();
			return mode;
		}
		cursor2.close();
		/* Distance Table */
		query = "select id from distance where exercise_id = " + exerciseId;
		Cursor cursor3 = db.rawQuery(query, null);
		if (cursor3.moveToNext()) {
			mode = Exercise.DISTANCE_BASED_EXERCISE;
			cursor3.close();
			close();
			return mode;
		}
		cursor3.close();
		close();
		Log.d("Steve", "ERROR IN EXERCISE MODE");
		return Exercise.COUNTUP_BASED_EXERCISE;
		
//		return -1; // Error, exercise id not found
	}
	
	/**
	 * get all exercises of the 'Cardio' type
	 * 
	 * @return
	 */
	public ArrayList<Exercise> getAllCardioExercises() {
		open();
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		
		String query = "select * from exercises where type like '%Cardio%'";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			
			int mode = getExerciseMode(id);
			if (mode == 4) {
				continue;
			}
			
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			exercises.add(new Exercise(id, name, type, comment, deleted, mode));
			
		}
		
		cursor.close();
		close();
		
		return exercises;
	}
	
	/**
	 * get all exercises of the 'Strength' type.
	 * 
	 * @return exercises
	 */
	public ArrayList<Exercise> getAllStrengthExercises() {
		open();
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		
		String query = "select * from exercises where type like '%Strength%'";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String comment = cursor.getString(cursor.getColumnIndex("comment"));
			boolean deleted = cursor.getInt(cursor.getColumnIndex("deleted")) > 0;
			int mode = getExerciseMode(id);
			exercises.add(new Exercise(id, name, type, comment, deleted, mode));
			
		}
		
		cursor.close();
		close();
		
		return exercises;
	}
	
	/**
	 * Get all exercise goals in the database.
	 * 
	 * @return exerciseGoals
	 */
	public ArrayList<ExerciseGoal> getAllExerciseGoals() {
		open();
		ArrayList<ExerciseGoal> exerciseGoals = new ArrayList<ExerciseGoal>();
		
		String query = "select * from exercise_goals order by name";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			boolean isCumulative = cursor.getInt(cursor.getColumnIndex("is_cumulative")) == 1;
			boolean isCompleted = cursor.getInt(cursor.getColumnIndex("is_completed")) == 1;
			int mode = cursor.getInt(cursor.getColumnIndex("mode"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			int exerciseId = cursor.getInt(cursor.getColumnIndex("exercise_id"));
			double goalOne = cursor.getDouble(cursor.getColumnIndex("goal_one"));
			double goalTwo = cursor.getDouble(cursor.getColumnIndex("goal_two"));
			double currentBestOne = cursor.getDouble(cursor.getColumnIndex("current_best_one"));
			double currentBestTwo = cursor.getDouble(cursor.getColumnIndex("current_best_two"));
			String unit = cursor.getString(cursor.getColumnIndex("unit"));
			exerciseGoals.add(new ExerciseGoal(id, name, isCumulative, isCompleted, mode, type, exerciseId, goalOne, goalTwo, currentBestOne, currentBestTwo, unit));
		}
		
		cursor.close();
		close();
		
		return exerciseGoals;
	}
	
	/**
	 * Get all body goals in the database
	 * 
	 * @return ArrayList<BodyGoal>
	 */
	public ArrayList<BodyGoal> getAllBodyGoals() {
		open();
		ArrayList<BodyGoal> bodyGoals = new ArrayList<BodyGoal>();
		
		String query = "select * from body_goals order by category";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String category = cursor.getString(cursor.getColumnIndex("category"));
			String unit = cursor.getString(cursor.getColumnIndex("unit"));
			Double started = cursor.getDouble(cursor.getColumnIndex("started"));
			Double current = cursor.getDouble(cursor.getColumnIndex("current"));
			Double goal = cursor.getDouble(cursor.getColumnIndex("goal"));
			boolean isCompleted = cursor.getInt(cursor.getColumnIndex("is_completed")) == 1 ? true : false;
			bodyGoals.add(new BodyGoal(id, category, unit, started, current, goal, isCompleted));
		}
		cursor.close();
		close();
		
		return bodyGoals;
	}
	
	/**
	 * Get all Custom Goals in the database.
	 * 
	 * @return ArrayList<CustomGoal>
	 */
	public ArrayList<CustomGoal> getAllCustomGoals() {
		open();
		ArrayList<CustomGoal> customGoals = new ArrayList<CustomGoal>();
		
		String query = "select * from custom_goals order by name";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String description = cursor.getString(cursor.getColumnIndex("description"));
			boolean isCompleted = cursor.getInt(cursor.getColumnIndex("is_completed")) == 1;
			customGoals.add(new CustomGoal(id, name, description, isCompleted));
		}
		
		cursor.close();
		close();
		
		return customGoals;
	}
	
	/**
	 * Updates the current best and completed values of the provided exercise goal
	 * 
	 * @param ExerciseGoal goal
	 */
	public void updateExerciseGoal(ExerciseGoal goal) {
		open();
		String query = "update exercise_goals " +
				"set is_completed = " + (goal.getIsCompleted() ? 1 : 0) +
				", current_best_one = " + goal.getCurrentBestOne() +
				", current_best_two = " + goal.getCurrentBestTwo() +
				" where id = " + goal.getId();
		db.execSQL(query);
		close();
	}
	
	public void updateBodyGoal(BodyGoal goal) {
		open();
		String query = "update body_goals " +
				"set current = " + goal.getCurrent() +
				", is_completed = " + (goal.isCompleted() ? 1 : 0) +
				" where id = " + goal.getId();
		db.execSQL(query);
		close();
	}
	
	/**
	 * Updates the custom goal with whether or not the goal has been completed
	 * 
	 * @param CustomGoal goal
	 */
	public void updateCustomGoal(CustomGoal goal) {
		open();
		String query = "update custom_goals " +
				"set is_completed = " + (goal.getIsCompleted() ? 1 : 0) +
				" where id = " + goal.getId();
		db.execSQL(query);
		close();
	}
	
	/**
	 * Deletes the exercise goal corresponding to the provided exercise goal id
	 * 
	 * @param id
	 */
	public void deleteExerciseGoal(int id) {
		open();
		String query = "delete from exercise_goals where id = " + id;
		db.execSQL(query);
		close();
	}
	
	/**
	 * Delete the body goal corresponding to the provided body goal id
	 * 
	 * @param id
	 */
	public void deleteBodyGoal(int id) {
		open();
		String query = "delete from body_goals where id = " + id;
		db.execSQL(query);
		close();
	}
	
	/**
	 * Deletes the custom goal corresponding to the provided custom goal id
	 * 
	 * @param id
	 */
	public void deleteCustomGoal(int id) {
		open();
		String query = "delete from custom_goals where id = " + id;
		db.execSQL(query);
		close();
	}
	
	/**
	 * Receives a newly completed workout result and determines if the completion of that
	 * exercise fulfilled any exercise goals. Fulfilled exercise goals returned in arraylist.
	 * 
	 * @param WorkoutResult result
	 * @return ArrayList<ExerciseGoal>
	 */
	public ArrayList<ExerciseGoal> getNewlyCompletedExerciseGoals(WorkoutResult result) {
		ArrayList<ExerciseGoal> goals = new ArrayList<ExerciseGoal>();
		Exercise ex = getExerciseFromId(result.getExerciseId());
		
		DistanceResult dResult;
		TimeResult tResult;
		double val = 0.0;
		String unit = new String();
		ArrayList<SetResult> sResults = new ArrayList<SetResult>();
		
		int mode = -1;
		switch (result.getMode()) {
		case WorkoutResult.INTERVAL_BASED_EXERCISE:
			return goals;
		case WorkoutResult.TIME_BASED_EXERCISE:
			mode = ExerciseGoal.TIME;
			tResult = getTimeResult(result.getId());
			val = tResult.getLength();
			unit = tResult.getUnits();
			break;
		case WorkoutResult.DISTANCE_BASED_EXERCISE:
			mode = ExerciseGoal.DISTANCE;
			dResult = getDistanceResult(result.getId());
			val = dResult.getLength();
			unit = dResult.getUnits();
			break;
		case WorkoutResult.SET_BASED_EXERCISE:
			mode = ExerciseGoal.SET;
			sResults = getSetResults(result.getId());
			SetResult sResult = sResults.get(sResults.size() - 1);
			String query = "select * from exercise_goals where exercise_id = " + ex.getId();
			open();
			Cursor c = db.rawQuery(query, null);
			while (c.moveToNext()) {
				ExerciseGoal goal = new ExerciseGoal();
				
				int id = c.getInt(c.getColumnIndex("id"));
				boolean isCumulative = c.getInt(c.getColumnIndex("is_cumulative")) == 1;
				boolean isCompleted = c.getInt(c.getColumnIndex("is_completed")) == 1;
				double goalWeight = c.getDouble(c.getColumnIndex("goal_one"));
				int goalReps = (int) c.getDouble(c.getColumnIndex("goal_two"));
				double currentWeight = c.getDouble(c.getColumnIndex("current_best_one"));
				int currentReps = (int) c.getDouble(c.getColumnIndex("current_best_two"));

				goal.setIsCompleted(isCompleted);
				
				if (isCumulative) {
					goal.setId(id);
					goal.setCurrentBestOne(currentWeight + sResult.getWeight() * sResult.getReps());
					
					if (goal.getCurrentBestOne() >= goalWeight && !isCompleted) {
						goal.setIsCompleted(true);
						goal.setName(c.getString(c.getColumnIndex("name")));
						goals.add(goal);
					}
					
					updateExerciseGoal(goal);
				} else if (sResult.getWeight() >= goalWeight && sResult.getReps() >= goalReps && !goal.getIsCompleted()) {
					goal.setId(id);
					goal.setName(c.getString(c.getColumnIndex("name")));
					goal.setCurrentBestOne(sResult.getWeight());
					goal.setCurrentBestTwo(sResult.getReps());
					goal.setIsCompleted(true);
					updateExerciseGoal(goal);
					goals.add(goal);
				} else if (sResult.getWeight() > currentWeight || 
						sResult.getWeight() == currentWeight && sResult.getReps() > currentReps) {
					goal.setId(id);
					goal.setCurrentBestOne(sResult.getWeight());
					goal.setCurrentBestTwo(sResult.getReps());
					goal.setIsCompleted(goal.getIsCompleted());
					updateExerciseGoal(goal);
				}
				
			}
			c.close();
			close();
			return goals;
		}
		
		int type = -1;
		if ("Cardio - Run".equals(ex.getType()))
			type = ExerciseGoal.RUN;
		else if ("Cardio - Bike".equals(ex.getType()))
			type = ExerciseGoal.BIKE;
		else if ("Cardio - Swim".equals(ex.getType()))
			type = ExerciseGoal.SWIM;
		else if ("Cardio - Ski".equals(ex.getType()))
			type = ExerciseGoal.SKI;
		else if ("Cardio - Row/Paddle".equals(ex.getType()))
			type = ExerciseGoal.PADDLE;
		
		open();
		String query = "select * from exercise_goals where exercise_id = " + ex.getId() +
				" or type = " + type + " and mode = " + mode;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			ExerciseGoal goal = new ExerciseGoal();
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			boolean isCumulative = cursor.getInt(cursor.getColumnIndex("is_cumulative")) == 1;
			goal.setIsCompleted(cursor.getInt(cursor.getColumnIndex("is_completed")) == 1);
			double goalVal = cursor.getDouble(cursor.getColumnIndex("goal_one"));
			double currentBestOne = cursor.getDouble(cursor.getColumnIndex("current_best_one"));
			String goalUnit = cursor.getString(cursor.getColumnIndex("unit"));
			val = MeasurementConversions.convert(val, unit, goalUnit);
			
			if (isCumulative) {
				goal.setId(id);
				goal.setCurrentBestOne(val + currentBestOne);
				if (goal.getCurrentBestOne() > goalVal && !goal.getIsCompleted()) {
					goal.setName(cursor.getString(cursor.getColumnIndex("name")));
					goal.setIsCompleted(true);
					goals.add(goal);
				}
				updateExerciseGoal(goal);
			} else if (val > currentBestOne) {
				goal.setId(id);
				goal.setCurrentBestOne(val);
				if (val > goalVal && !goal.getIsCompleted()) {
					goal.setName(cursor.getString(cursor.getColumnIndex("name")));
					goal.setIsCompleted(true);
					goals.add(goal);
				}
				updateExerciseGoal(goal);
			}
		}
		cursor.close();
		close();
		return goals;
	}
	
	/**
	 * Retrieve distance result correlating to the workout result id
	 * @param resultId
	 * @return
	 */
	public DistanceResult getDistanceResult(int resultId) {
		open();
		
		DistanceResult result = null;
		
		String query = "select * from distance_result where workout_result_id = " + resultId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			Double length = cursor.getDouble(cursor.getColumnIndex("length"));
			String unit = cursor.getString(cursor.getColumnIndex("units"));
			result = new DistanceResult(id, resultId, length, unit);
		}
		cursor.close();
		close();
		return result;
	}
	
	/**
	 * Retrieve time result correlating to the workout result id
	 * @param resultId
	 * @return
	 */
	public TimeResult getTimeResult(int resultId) {
		open();
		
		TimeResult result = null;
		
		String query = "select * from time_result where workout_result_id = " + resultId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int length = cursor.getInt(cursor.getColumnIndex("length"));
			String unit = cursor.getString(cursor.getColumnIndex("units"));
			result = new TimeResult(id, resultId, length, unit);
		}
		cursor.close();
		close();
		return result;
	}
	
	/**
	 * Retrieve an arraylist of set results correlating to the workout result id
	 * @param resultId
	 * @return
	 */
	public ArrayList<SetResult> getSetResults(int resultId) {
		open();
		
		ArrayList<SetResult> results = new ArrayList<SetResult>();
		
		String query = "select * from set_result where workout_result_id = " + resultId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int setNum = cursor.getInt(cursor.getColumnIndex("set_number"));
			Double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
			int reps = cursor.getInt(cursor.getColumnIndex("reps"));
			results.add(new SetResult(id, resultId, setNum, reps, weight));
		}
		
		cursor.close();
		close();
		return results;
	}
	
	/**
	 * determine and return the greatest DistanceResult corresponding to provided exercises
	 * @param exercises
	 * @return
	 */
	public DistanceResult getGreatestDistanceResult(ArrayList<Exercise> exercises) {
		open();
		
		Double greatest = 0.0;
		String greatestUnit = "yd.";
		
		for (Exercise ex : exercises) {
			String query = "select d.length, d.units from workout_result w, distance_result d " +
					"where w.id = d.workout_result_id and w.exercise_id = " + ex.getId();
			Cursor cursor = db.rawQuery(query, null);
			while (cursor.moveToNext()) {
				Double num = cursor.getDouble(cursor.getColumnIndex("d.length"));
				String unit = cursor.getString(cursor.getColumnIndex("d.units"));
				greatest = MeasurementConversions.convert(greatest, greatestUnit, unit);
				greatestUnit = unit;
				if (greatest < num) {
					greatest = num;
				}
			}
			cursor.close();
		}
		
		close();
		return new DistanceResult(greatest, greatestUnit);
	}
	
	/**
	 * determine and return the greatest TimeResult corresponding to provided exercises
	 * @param exercises
	 * @return
	 */
	public TimeResult getGreatestTimeResult(ArrayList<Exercise> exercises) {
		open();
		
		int greatest = 0;
		String greatestUnit = "seconds";
		
		for (Exercise ex : exercises) {
			String query = "select t.length, t.units from workout_result w, time_result t " +
					"where w.id = t.workout_result_id and w.exercise_id = " + ex.getId();
			Cursor cursor = db.rawQuery(query, null);
			while (cursor.moveToNext()) {
				int num = cursor.getInt(cursor.getColumnIndex("t.length"));
				String unit = cursor.getString(cursor.getColumnIndex("t.units"));
				greatest = (int) MeasurementConversions.convert((int) greatest, greatestUnit, unit);
				greatestUnit = unit;
				if (greatest < num) {
					greatest = num;
				}
			}
			cursor.close();
		}
		
		close();
		return new TimeResult(greatest, greatestUnit);
	}
	
	/**
	 * Inserts a row into the Workout Result Table.  The date for the result is generated here, not in the calling class
	 * @param workoutResult
	 * @return
	 */
	public int storeWorkoutResult(WorkoutResult workoutResult) {
		int workoutResultId = -1;
		open();
		// Get Date
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//get current date time with Date()
	    Date date = new Date();
	    String dateString = dateFormat.format(date);

	    String query = "insert into workout_result (workout_id, exercise_id, date) values (" + 
	    			   workoutResult.getWorkoutId() + "," + workoutResult.getExerciseId() + ",'" + dateString + "')";
	    db.execSQL(query);
	    // Get the workout_result_id so that the data can be recorded in appropriate table
	    query = "select id from workout_result where workout_id = " + workoutResult.getWorkoutId() + " and exercise_id = " + 
	    		workoutResult.getExerciseId() + " and date ='" + dateString + "'";
	    Cursor cursor = db.rawQuery(query, null);
	    if (cursor.moveToLast()) {
	    	workoutResultId = cursor.getInt(cursor.getColumnIndex("id"));
	    }
	    cursor.close();
		close();
		return workoutResultId;
	}
	
	/**
	 * Inserts a row into the Set Result Table.  It stores the workout data for an exercise that is set based by a user.
	 * @param setResult
	 */
	public void storeSetResult(SetResult setResult) {
		open();
		String query = "insert into set_result (workout_result_id, set_number, reps, weight) values (" + 
					   setResult.getWorkoutResultId() + "," + setResult.getSetNumber() + "," + setResult.getReps() + "," + setResult.getWeight() + ")";
		db.execSQL(query);
		close();
	}
	
	/**
	 * Inserts a row into the Distance Result Table.  It stores the user workout date for an exercise that is distance based.
	 * @param distanceResult
	 */
	public void storeDistanceResult(DistanceResult distanceResult) {
		open();
		String query = "insert into distance_result (workout_result_id, length, units) values (" +
					   distanceResult.getWorkoutResultId() + "," + distanceResult.getLength() + ",'" + distanceResult.getUnits() + "')";
		db.execSQL(query);
		close();
	}
	
	/**
	 * Inserts a row into the TimeResult Table.
	 * @param timeResult
	 */
	public void storeTimeResult(TimeResult timeResult) {
		open();
		String query = "insert into time_result (workout_result_id, length, units) values (" + 
					   timeResult.getWorkoutResultId() + "," + timeResult.getLength() + ",'" + timeResult.getUnits() + "')";
		db.execSQL(query);
		close();
	}
	
	/**
	 * Inserts a row into the Interval Result Table.
	 * @param intervalResult
	 */
	public void storeIntervalResult(IntervalResult intervalResult) {
		open();
		String query = "insert into interval_result (workout_result_id, interval_id, interval_set_num, interval_set_id, length, units) values (" +
					   intervalResult.getWorkoutResultId() + "," + intervalResult.getIntervalId() + "," + intervalResult.getIntervalSetNum() + "," + 
					   intervalResult.getIntervalSetId() + "," + intervalResult.getLength() + ",'" + intervalResult.getUnits() + "')";
		Log.d("Steve", "Query:" + query);
		db.execSQL(query);
		close();
	}
	
	/**
	 * Returns all workout results
	 * 
	 * @return ArrayList<WorkoutResult> list
	 */
	public ArrayList<WorkoutResult> getAllWorkoutResults() {
		ArrayList<WorkoutResult> list = new ArrayList<WorkoutResult>();
		open();
		String query = "select * from workout_result";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int workout_id = cursor.getInt(cursor.getColumnIndex("workout_id"));
			int exercise_id = cursor.getInt(cursor.getColumnIndex("exercise_id"));
			String qDate = cursor.getString(cursor.getColumnIndex("date"));
			int mode = getWorkoutResultMode(exercise_id);
			Log.d("Steve", "=================================================");
			Log.d("Steve", "Mode: " + mode);
			WorkoutResult wr = new WorkoutResult(id, workout_id, exercise_id, qDate, mode);
			wr = getExerciseResultForWorkoutResult(wr);
			list.add(wr);			
		}
		cursor.close();
		close();
		return list;
	}
	
	public ArrayList<WorkoutResult> getWorkoutResultForWorkout(int workoutId, String date) {
		ArrayList<WorkoutResult> workoutResultList = new ArrayList<WorkoutResult>();
		open();
		String query = "select * from workout_result where  workout_id = " + workoutId + " and date = '" + date + "'";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int workout_id = cursor.getInt(cursor.getColumnIndex("workout_id"));
			int exercise_id = cursor.getInt(cursor.getColumnIndex("exercise_id"));
			String qDate = cursor.getString(cursor.getColumnIndex("date"));
			int mode = getWorkoutResultMode(exercise_id);
			Log.d("Steve", "=================================================");
			Log.d("Steve", "Mode: " + mode);
			WorkoutResult wr = new WorkoutResult(id, workout_id, exercise_id, qDate, mode);
			wr = getExerciseResultForWorkoutResult(wr);
			workoutResultList.add(wr);			
		}
		cursor.close();
		close();
		return workoutResultList;
	}
	
	public ArrayList<WorkoutResult> getWorkoutResultsForExercise(int exerciseId) {
		ArrayList<WorkoutResult> workoutResultList = new ArrayList<WorkoutResult>();
		open();
		String query = "select * from workout_result where exercise_id = " + exerciseId + " order by date desc";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int workout_id = cursor.getInt(cursor.getColumnIndex("workout_id"));
			int exercise_id = cursor.getInt(cursor.getColumnIndex("exercise_id"));
			String qDate = cursor.getString(cursor.getColumnIndex("date"));
			int mode = getWorkoutResultMode(exercise_id);
			WorkoutResult wr = new WorkoutResult(id, workout_id, exercise_id, qDate, mode);
			wr = getExerciseResultForWorkoutResult(wr);
			workoutResultList.add(wr);
		}
		cursor.close();
		close();
		return workoutResultList;
		
	}
	
	// Assigns the exercise result to the array list for the exercise
	public WorkoutResult getExerciseResultForWorkoutResult (WorkoutResult workoutResult) {
		WorkoutResult result = workoutResult;
		int mode = workoutResult.getMode();
		switch (mode) {
			case WorkoutResult.TIME_BASED_EXERCISE:
				Log.d("Steve", "Countdown");
				result.setTimeResultList(getTimeResultForWorkoutResult(workoutResult.getId()));
				break;
			case WorkoutResult.DISTANCE_BASED_EXERCISE:
				Log.d("Steve", "Distance");
				result.setDistanceResultList(getDistanceResultForWorkoutResult(workoutResult.getId()));
				break;
			case WorkoutResult.INTERVAL_BASED_EXERCISE:
				Log.d("Steve", "Interval");
				result.setIntervalResultList(getIntervalResultForWorkoutResult(workoutResult.getId()));
				break;
			case WorkoutResult.SET_BASED_EXERCISE:
				Log.d("Steve", "Set");
				result.setSetResultList(getSetResultsForWorkoutResult(workoutResult.getId()));
				break;
		}
		return result;
	}
	
	public ArrayList<SetResult> getSetResultsForWorkoutResult(int workoutResultId) {
		open();
		ArrayList<SetResult> setResultList = new ArrayList<SetResult>();
		String query = "select * from set_result where workout_result_id = " + workoutResultId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int set_number = cursor.getInt(cursor.getColumnIndex("set_number"));
			int reps = cursor.getInt(cursor.getColumnIndex("reps"));
			double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
			SetResult setResult = new SetResult(workoutResultId, set_number, reps, weight);
			setResultList.add(setResult);
		}
		cursor.close();
		close();
		return setResultList;
	}
	
	
	public ArrayList<IntervalResult> getIntervalResultForWorkoutResult(int workoutResultId) {
		open();
		ArrayList<IntervalResult> intervalResultList = new ArrayList<IntervalResult>();
		String query = "select name, interval_result.interval_id, interval_result.interval_set_id, interval_set_num, interval_result.length, " +
		               "interval_result.units " + 
					   "from interval_result " + 
					   "join interval_sets on interval_result.interval_set_id = interval_sets.id and workout_result_id = " + workoutResultId;
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(0);
			int intervalId = cursor.getInt(1);
			int intervalSetId = cursor.getInt(2);
			int intervalSetNum = cursor.getInt(3);
			double length = cursor.getDouble(4);
			String units = cursor.getString(5);
			IntervalResult intervalResult = new IntervalResult(workoutResultId, intervalId, intervalSetNum, intervalSetId, length, units,name);
			intervalResultList.add(intervalResult);			
		}
		cursor.close();
		close();
		return intervalResultList;
	}
	
	
	public ArrayList<DistanceResult> getDistanceResultForWorkoutResult(int workoutResultId) {
		open();
		String query = "select * from distance_result where workout_result_id = " + workoutResultId;
		ArrayList<DistanceResult> distanceResultList = new ArrayList<DistanceResult> ();
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			double length = cursor.getDouble(cursor.getColumnIndex("length"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			DistanceResult distanceResult = new DistanceResult(workoutResultId, length, units);
			distanceResultList.add(distanceResult);
		}
		cursor.close();
		close();
		return distanceResultList;
	}
	
	public ArrayList<TimeResult> getTimeResultForWorkoutResult (int workoutResultId) {
		open();
		String query = "select * from time_result where workout_result_id = " + workoutResultId;
		ArrayList<TimeResult> timeResultList = new ArrayList<TimeResult>();
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			int length = cursor.getInt(cursor.getColumnIndex("length"));
			String units = cursor.getString(cursor.getColumnIndex("units"));
			TimeResult timeResult = new TimeResult(workoutResultId, length, units);
			timeResultList.add(timeResult);
		}
		cursor.close();
		close();
		return timeResultList;		
	}
	
	public int getWorkoutResultMode(int exerciseId) {
		open();
		int mode = -1;
		/* Query all exercise type tables */
		/* Set Table */
		String query = "select id from sets where exercise_id = " + exerciseId;
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			mode = WorkoutResult.SET_BASED_EXERCISE;
			cursor.close();
			close();
			return mode;
		}
		/* Time Table */ 
		query = "select id from time where exercise_id = " + exerciseId;
		cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			mode = WorkoutResult.TIME_BASED_EXERCISE;
			cursor.close();
			close();
			return mode;
		}
		/* Interval Table */
		query = "select id from intervals where exercise_id = " + exerciseId;
		cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			mode = WorkoutResult.INTERVAL_BASED_EXERCISE;
			cursor.close();
			close();
			return mode;
		}
		/* Distance Table */
		query = "select id from distance where exercise_id = " + exerciseId;
		cursor = db.rawQuery(query, null);
		if (cursor.moveToNext()) {
			mode = WorkoutResult.DISTANCE_BASED_EXERCISE;
			cursor.close();
			close();
			return mode;
		}
		cursor.close();
		close();
		if (mode == -1)
			Log.d("Steve", "ERROR IN WORKOUT RESULT MODE: " + exerciseId);
		else 
			Log.d("Steve", "FOUND IN WORKOUT RESULT MODE:" + exerciseId);
		// Countup has no time stored.  so it must be count up which is a time
		return WorkoutResult.TIME_BASED_EXERCISE;
		
//		return -1; // Error, exercise id not found
	}
	
	public String [] getWorkoutDates (int workoutId) {
		open();
		String query = "select distinct date from workout_result where workout_id = " + workoutId + " order by date desc";
		ArrayList<String> workoutDates = new ArrayList<String>();
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			String date = cursor.getString(cursor.getColumnIndex("date"));
			workoutDates.add(date);
		}
		cursor.close();
		close();
		return workoutDates.toArray(new String [0]);		
	}
	
	public void createCalendarEntry(ArrayList<String> records) {
			open();
			Log.d("STEVE", "INSERTS");
			for (int i = 0; i < records.size(); i++) {
				Log.d("Steve", "QUERY: " + records.get(i));
				db.execSQL(records.get(i));
			}
			close();
	}
	
	public Hashtable<String, Boolean> getWorkoutDatesForCalendar(String whereClause) {
		open();
		Hashtable<String, Boolean> hash = new Hashtable<String, Boolean>();
		String query = "select date from calendar " + whereClause;
		Log.d("Steve", query);
		Cursor cursor = db.rawQuery(query, null);
		Log.d("STEVE", "DB QUERY---------------------------------------------------------");
		while (cursor.moveToNext()) {
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Log.d("Steve", "Date: " + date);
			hash.put(date, true);
		}
		cursor.close();
		close();
		return hash;
	}
	
	public ArrayList<String> getWorkoutsForDate(String date) {
		ArrayList<String> workoutList = new ArrayList<String>();
		// select distinct name, workouts.id from calendar join workouts on 
		// calendar.workout_id = workouts.id and date = '2012/04/17'
		String query = "select distinct name from calendar join workouts on " +
					   "calendar.workout_id = workouts.id and date = '" + date + "'";
		open();
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			workoutList.add(name);
		}
		cursor.close();
		close();
		return workoutList;
	}
	
	public boolean isWorkoutScheduled(String date, int workoutId) {
		boolean isScheduled = false;
		open();
		String query = "select id from calendar where workout_id = " + workoutId + " and date = '" + date + "'";
		Cursor cursor = db.rawQuery(query, null);
		while (cursor.moveToNext()) {
			isScheduled = true;
		}
		cursor.close();
		close();
		return isScheduled;
	}
	
}
