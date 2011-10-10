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

	public void testAddFinishedTasks() {
		data1.addFinishedTasks(0);
		assertTrue(data1.getFinishedTasks() == 0);

		data1.addFinishedTasks(1);
		assertTrue(data1.getFinishedTasks() == 1);

		data1.addFinishedTasks(2);
		assertTrue(data1.getFinishedTasks() == 3);

		data1.addFinishedTasks(3);
		assertTrue(data1.getFinishedTasks() == 6);
	}

	public void testAddCreatedTasks() {
		data1.addCreatedTasks(0);
		assertTrue(data1.getCreatedTasks() == 0);

		data1.addCreatedTasks(1);
		assertTrue(data1.getCreatedTasks() == 1);

		data1.addCreatedTasks(2);
		assertTrue(data1.getCreatedTasks() == 3);

		data1.addCreatedTasks(3);
		assertTrue(data1.getCreatedTasks() == 6);
	}

	public void testAddDeletedTasks() {
		data1.addDeletedTasks(0);
		assertTrue(data1.getDeletedTasks() == 0);

		data1.addDeletedTasks(1);
		assertTrue(data1.getDeletedTasks() == 1);

		data1.addDeletedTasks(2);
		assertTrue(data1.getDeletedTasks() == 3);

		data1.addDeletedTasks(3);
		assertTrue(data1.getDeletedTasks() == 6);
	}

	public void testAddOverdueTasks() {
		data1.addOverdueTasks(0);
		assertTrue(data1.getOverdueTasks() == 0);

		data1.addOverdueTasks(1);
		assertTrue(data1.getOverdueTasks() == 1);

		data1.addOverdueTasks(2);
		assertTrue(data1.getOverdueTasks() == 3);

		data1.addOverdueTasks(3);
		assertTrue(data1.getOverdueTasks() == 6);
	}

	public void testAddCreatedLists() {
		data1.addCreatedLists(0);
		assertTrue(data1.getCreatedLists() == 0);

		data1.addCreatedLists(1);
		assertTrue(data1.getCreatedLists() == 1);

		data1.addCreatedLists(2);
		assertTrue(data1.getCreatedLists() == 3);

		data1.addCreatedLists(3);
		assertTrue(data1.getCreatedLists() == 6);
	}

	public void testAddDeletedLists() {
		data1.addDeletedLists(0);
		assertTrue(data1.getDeletedLists() == 0);

		data1.addDeletedLists(1);
		assertTrue(data1.getDeletedLists() == 1);

		data1.addDeletedLists(2);
		assertTrue(data1.getDeletedLists() == 3);

		data1.addDeletedLists(3);
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
