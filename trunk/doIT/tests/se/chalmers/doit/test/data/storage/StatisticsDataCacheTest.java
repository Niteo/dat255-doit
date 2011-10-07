package se.chalmers.doit.test.data.storage;

import java.util.Date;

import org.junit.Before;

import se.chalmers.doit.data.storage.implementation.StatisticsDataCache;
import android.test.AndroidTestCase;

public class StatisticsDataCacheTest extends AndroidTestCase {

	StatisticsDataCache cache;
	Date d1, d2;

	@Override
	@Before
	public void setUp() throws Exception {

		cache = new StatisticsDataCache();

		d1 = new Date(2011, 12, 11);
		d2 = new Date(2011, 12, 12);
	}

	public void testGetStatisticsData() {
		assertTrue(cache.getStatisticsData().isEmpty());
	}

	public void testClearData() {
		cache.clearData();
		assertTrue(cache.getStatisticsData().isEmpty());
	}

	public void testReportFinishedTasks() {
		cache.clearData();
		cache.reportFinishedTasks(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportFinishedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportFinishedTasks(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportFinishedTasks(1, d1);
		cache.reportFinishedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportFinishedTasks(1, d1);
		cache.reportFinishedTasks(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);

	}

	public void testReportCreatedTasks() {
		cache.clearData();
		cache.reportCreatedTasks(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportCreatedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedTasks(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedTasks(1, d1);
		cache.reportCreatedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedTasks(1, d1);
		cache.reportCreatedTasks(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);
	}

	public void testReportDeletedTasks() {
		cache.clearData();
		cache.reportDeletedTasks(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportDeletedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedTasks(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedTasks(1, d1);
		cache.reportDeletedTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedTasks(1, d1);
		cache.reportDeletedTasks(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);
	}

	public void testReportOverdueTasks() {
		cache.clearData();
		cache.reportOverdueTasks(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportOverdueTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportOverdueTasks(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportOverdueTasks(1, d1);
		cache.reportOverdueTasks(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportOverdueTasks(1, d1);
		cache.reportOverdueTasks(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);
	}

	public void testReportCreatedLists() {
		cache.clearData();
		cache.reportCreatedLists(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportCreatedLists(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedLists(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedLists(1, d1);
		cache.reportCreatedLists(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportCreatedLists(1, d1);
		cache.reportCreatedLists(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);
	}

	public void testReportDeletedLists() {
		cache.clearData();
		cache.reportDeletedLists(0, d1);
		assertTrue(cache.getStatisticsData().isEmpty());

		cache.clearData();
		cache.reportDeletedLists(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedLists(2, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedLists(1, d1);
		cache.reportDeletedLists(1, d1);
		assertTrue(cache.getStatisticsData().size() == 1);

		cache.clearData();
		cache.reportDeletedLists(1, d1);
		cache.reportDeletedLists(1, d2);
		assertTrue(cache.getStatisticsData().size() == 2);
	}

}
