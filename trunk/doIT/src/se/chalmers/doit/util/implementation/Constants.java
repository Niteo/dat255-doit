package se.chalmers.doit.util.implementation;

/**
 * A class containing various constants used throughout the application
 *
 * @author Robert Kaufmann
 *
 */
public final class Constants {
	public static final byte PRIORITY_VERYLOW = 1;
	public static final byte PRIORITY_LOW = 2;
	public static final byte PRIORITY_MEDIUM = 3;
	public static final byte PRIORITY_HIGH = 4;
	public static final byte PRIORITY_VERYHIGH = 5;
	public static final byte PRIORITY_DEFAULT = PRIORITY_MEDIUM;

	//Priority 5-1
	public static final int GOOD_DEFAULT_PRIMARY = 5;
	//Name A-Z
	public static final int GOOD_DEFAULT_SECONDARY = 2;
	//Due date soon-later
	public static final int GOOD_DEFAULT_TERTIARY = 0;

	public static final String SHARED_PREFERENCES_PRIMARY_SORTING = "current_primary_sorting";
	public static final String SHARED_PREFERENCES_SECONDARY_SORTING = "current_secondary_sorting";
	public static final String SHARED_PREFERENCES_TERTIARY_SORTING = "current_tertiary_sorting";

	public static final String SHARED_PREFERENCES_STATISTICS_NAME = "statSharedPreference";
	
	public static final String DATABASE_NAME = "database";

	public static final String SHARED_PREFERENCES_SORTING = "shared_preferences_sorting";

	public static final int CUSTOMPOSITION_DEFAULT = 0;

	public static final int MILLISECONDS_IN_A_DAY = 86400000;
}