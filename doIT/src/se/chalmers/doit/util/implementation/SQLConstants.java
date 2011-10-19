package se.chalmers.doit.util.implementation;

public final class SQLConstants {

	// SQL Database name
	public static final String DATABASE_NAME = "database";
	
	// List SQL table constants
	public static final String LIST_TABLE_NAME = "listtable";
	public static final String LIST_ID = "_id";
	public static final String LIST_NAME = "name";
	
	
	public static final String TASK_COMPLETED = "completed";
	public static final String TASK_CONNECTED_LIST_ID = "listid";
	public static final String TASK_CUSTOMPOS = "custompos";
	public static final String TASK_DESCRIPTION = "description";
	public static final String TASK_DUEDATE = "duedate";
	public static final String TASK_ID = "_id";
	public static final String TASK_NAME = "name";
	public static final String TASK_PRIORITY = "priority";
	public static final String TASK_REMINDERDATE = "reminderdate";
	public static final String TASK_TABLE_NAME = "tasktable";
	
	// Interactive SQL statement
	public static String taskMove(final int taskID, final int toListID) {
		return "UPDATE " + TASK_TABLE_NAME + " SET " + TASK_CONNECTED_LIST_ID
				+ "=" + toListID + " WHERE " + TASK_ID + "=" + taskID;
	}
	
	// SQL queries
	public static final String SELECT_ALL_LISTS = "SELECT * FROM "
			+ LIST_TABLE_NAME;
	public static final String SELECT_ALL_TASKS = "SELECT * FROM "
			+ TASK_TABLE_NAME;
	
	// SQL statements
	public static final String CREATE_TABLE_LISTS = "CREATE TABLE IF NOT EXISTS "
			+ LIST_TABLE_NAME
			+ " ("
			+ LIST_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ LIST_NAME
			+ " TEXT NOT NULL);";

	public static final String CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS "
			+ TASK_TABLE_NAME
			+ " ("
			+ TASK_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ TASK_CONNECTED_LIST_ID
			+ " INTEGER NOT NULL,"
			+ TASK_NAME
			+ " TEXT NOT NULL,"
			+ TASK_DESCRIPTION
			+ " TEXT NOT NULL,"
			+ TASK_PRIORITY
			+ " INTEGER NOT NULL,"
			+ TASK_DUEDATE
			+ " INTEGER,"
			+ TASK_REMINDERDATE
			+ " INTEGER,"
			+ TASK_CUSTOMPOS
			+ " INTEGER NOT NULL," + TASK_COMPLETED + " INTEGER NOT NULL);";
	
}
