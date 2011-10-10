package se.chalmers.doit.util.implementation;

public final class SQLConstants {

	// SQL Database name
	public static final String DATABASE_NAME = "database";
	
	// List SQL table constants
	public static final String LIST_TABLE_NAME = "listtable";	
	public static final String LIST_ID = "_id";
	public static final String LIST_NAME = "name";

	// Task SQL table constants
	public static final String TASK_TABLE_NAME = "tasktable";
	public static final String TASK_ID = "_id";
	public static final String TASK_CONNECTED_LIST_ID = "listid";
	public static final String TASK_NAME = "name";
	public static final String TASK_DESCRIPTION = "description";
	public static final String TASK_PRIORITY = "priority";
	public static final String TASK_DUEDATE = "duedate";
	public static final String TASK_REMINDERDATE = "reminderdate";
	
	
	public static final String CREATE_TABLE_LISTS =
		"create table if not exists " + LIST_TABLE_NAME +
		" (" + LIST_ID + " integer primary key autoincrement," +
		 LIST_NAME + " text not null);";
	
	public static final String CREATE_TABLE_TASKS =
		"create table if not exists " + TASK_TABLE_NAME +
		" (" + TASK_ID + " integer primary key autoincrement," +
		TASK_CONNECTED_LIST_ID + " integer not null," +
		TASK_NAME + " text not null," +
		TASK_DESCRIPTION + " text not null," +
		TASK_PRIORITY + " integer not null," +
		TASK_DUEDATE + " integer," +
		TASK_REMINDERDATE + " integer );";
	
}
