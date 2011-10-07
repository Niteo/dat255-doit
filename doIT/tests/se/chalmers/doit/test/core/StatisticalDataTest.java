package se.chalmers.doit.test.core;

import java.util.Date;

import org.junit.Before;

import se.chalmers.doit.core.implementation.StatisticalData;
import android.test.AndroidTestCase;

public class StatisticalDataTest extends AndroidTestCase {

	private StatisticalData data1, data2, data3;
	private Date d1, d2, d3;

	@Override
	@Before
	public void setUp() throws Exception {
		d1 = new Date(2011, 12, 12);
		d2 = new Date(2011, 12, 11);
		d3 = new Date(2011, 12, 12);

		data1 = new StatisticalData(d1);
		data2 = new StatisticalData(d2);
		data3 = new StatisticalData(d3);

	}

	public void testStatisticalData() {
		new StatisticalData(new Date());
	}

	public void testGetFinishedTasks() {
		assertTrue(data1.getFinishedTasks() == 0);
		assertTrue(data2.getFinishedTasks() == 0);
	}

	public void testGetCreatedTasks() {
		assertTrue(data1.getCreatedTasks() == 0);
		assertTrue(data2.getCreatedTasks() == 0);
	}

	public void testGetDeletedTasks() {
		assertTrue(data1.getDeletedTasks() == 0);
		assertTrue(data2.getDeletedTasks() == 0);
	}

	public void testGetOverdueTasks() {
		assertTrue(data1.getOverdueTasks() == 0);
		assertTrue(data2.getOverdueTasks() == 0);
	}

	public void testGetCreatedLists() {
		assertTrue(data1.getCreatedLists() == 0);
		assertTrue(data2.getCreatedLists() == 0);
	}

	public void testGetDeletedLists() {
		assertTrue(data1.getDeletedLists() == 0);
		assertTrue(data2.getDeletedLists() == 0);
	}

	public void testIncrementFinishedTasks() {
		data1.incrementFinishedTasks(0);
		assertTrue(data1.getFinishedTasks() == 0);

		data1.incrementFinishedTasks(1);
		assertTrue(data1.getFinishedTasks() == 1);

		data1.incrementFinishedTasks(2);
		assertTrue(data1.getFinishedTasks() == 3);

		data1.incrementFinishedTasks(3);
		assertTrue(data1.getFinishedTasks() == 6);
	}

	public void testIncrementCreatedTasks() {
		data1.incrementCreatedTasks(0);
		assertTrue(data1.getCreatedTasks() == 0);

		data1.incrementCreatedTasks(1);
		assertTrue(data1.getCreatedTasks() == 1);

		data1.incrementCreatedTasks(2);
		assertTrue(data1.getCreatedTasks() == 3);

		data1.incrementCreatedTasks(3);
		assertTrue(data1.getCreatedTasks() == 6);
	}

	public void testIncrementDeletedTasks() {
		data1.incrementDeletedTasks(0);
		assertTrue(data1.getDeletedTasks() == 0);

		data1.incrementDeletedTasks(1);
		assertTrue(data1.getDeletedTasks() == 1);

		data1.incrementDeletedTasks(2);
		assertTrue(data1.getDeletedTasks() == 3);

		data1.incrementDeletedTasks(3);
		assertTrue(data1.getDeletedTasks() == 6);
	}

	public void testIncrementOverdueTasks() {
		data1.incrementOverdueTasks(0);
		assertTrue(data1.getOverdueTasks() == 0);

		data1.incrementOverdueTasks(1);
		assertTrue(data1.getOverdueTasks() == 1);

		data1.incrementOverdueTasks(2);
		assertTrue(data1.getOverdueTasks() == 3);

		data1.incrementOverdueTasks(3);
		assertTrue(data1.getOverdueTasks() == 6);
	}

	public void testIncrementCreatedLists() {
		data1.incrementCreatedLists(0);
		assertTrue(data1.getCreatedLists() == 0);

		data1.incrementCreatedLists(1);
		assertTrue(data1.getCreatedLists() == 1);

		data1.incrementCreatedLists(2);
		assertTrue(data1.getCreatedLists() == 3);

		data1.incrementCreatedLists(3);
		assertTrue(data1.getCreatedLists() == 6);
	}

	public void testIncrementDeletedLists() {
		data1.incrementDeletedLists(0);
		assertTrue(data1.getDeletedLists() == 0);

		data1.incrementDeletedLists(1);
		assertTrue(data1.getDeletedLists() == 1);

		data1.incrementDeletedLists(2);
		assertTrue(data1.getDeletedLists() == 3);

		data1.incrementDeletedLists(3);
		assertTrue(data1.getDeletedLists() == 6);
	}

	public void testGetDate() {
		assertTrue(data1.getDate().equals(d1));
		assertTrue(data2.getDate().equals(d2));
		assertTrue(data3.getDate().equals(d3));
	}

	public void testSameDay() {
		assertTrue(data1.sameDay(data3.getDate()));

		assertFalse(data1.sameDay(data2.getDate()));
	}

}
