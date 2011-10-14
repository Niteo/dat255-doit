package se.chalmers.doit.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Priority;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.util.implementation.DueDateComparatorStrategy;
import se.chalmers.doit.util.implementation.NameComparatorStrategy;
import se.chalmers.doit.util.implementation.PriorityComparatorStrategy;
import se.chalmers.doit.util.implementation.SortingComparator;
import android.test.AndroidTestCase;

/**
 * JUnit class for testing SortingComparator.java
 * 
 * @author Kaufmann
 */
public class SortingComparatorTest extends AndroidTestCase {

	public void testSortingComparator() {
		final DueDateComparatorStrategy dd = new DueDateComparatorStrategy(
				false);
		final NameComparatorStrategy nn = new NameComparatorStrategy(false);
		final PriorityComparatorStrategy pp = new PriorityComparatorStrategy(
				false);
		new SortingComparator(nn, dd, pp);
		new SortingComparator(null, dd, pp);
		new SortingComparator(null, null, pp);
		new SortingComparator(null, null, null);
		new SortingComparator(nn, null, pp);
		new SortingComparator(nn, null, null);
		new SortingComparator(nn, dd, null);
	}

	public void testCompare() {
		// Set up tasks
		final Task t1 = new Task("A", "...", new Priority((byte) 2), new Date(
				100), new Date(0), 0, false);
		final Task t2 = new Task("A", "...", new Priority((byte) 2), new Date(
				30000), new Date(0), 0, false);
		final Task t3 = new Task("A", "...", new Priority((byte) 2), new Date(
				2000), new Date(0), 0, false);

		final Task t4 = new Task("B", "...", new Priority((byte) 3), new Date(
				100), new Date(0), 0, false);
		final Task t5 = new Task("B", "...", new Priority((byte) 1), new Date(
				100), new Date(0), 0, false);
		final Task t6 = new Task("B", "...", new Priority((byte) 2), new Date(
				100), new Date(0), 0, false);

		final Task t7 = new Task("C", "...", new Priority((byte) 1), new Date(
				2000), new Date(0), 0, false);
		final Task t8 = new Task("C", "...", new Priority((byte) 2), new Date(
				100), new Date(0), 0, false);
		final Task t9 = new Task("C", "...", new Priority((byte) 3), new Date(
				100), new Date(0), 0, false);

		// Set up list
		final List<ITask> list = new ArrayList<ITask>();
		list.add(t9);
		list.add(t7);
		list.add(t5);
		list.add(t2);
		list.add(t1);
		list.add(t8);
		list.add(t3);
		list.add(t4);
		list.add(t6);

		// Set up comparators
		final DueDateComparatorStrategy dd = new DueDateComparatorStrategy(
				false);
		final NameComparatorStrategy nn = new NameComparatorStrategy(false);
		final PriorityComparatorStrategy pp = new PriorityComparatorStrategy(
				false);
		final SortingComparator sc = new SortingComparator(nn, dd, pp);

		// Sort and check results
		Collections.sort(list, sc);
		final boolean value = list.indexOf(t1) == 0 && list.indexOf(t2) == 2
				&& list.indexOf(t3) == 1 && list.indexOf(t4) == 3
				&& list.indexOf(t5) == 5 && list.indexOf(t6) == 4
				&& list.indexOf(t7) == 8 && list.indexOf(t8) == 7
				&& list.indexOf(t9) == 6;

		assertTrue(value);
	}
}