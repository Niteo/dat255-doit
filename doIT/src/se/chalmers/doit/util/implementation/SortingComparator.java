package se.chalmers.doit.util.implementation;

import java.util.Comparator;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.util.IComparatorStrategy;

public class SortingComparator implements Comparator<ITask> {

	private final Comparator<ITask> primary, secondary, tertiary;

	public SortingComparator(final IComparatorStrategy primary,
			final IComparatorStrategy secondary,
			final IComparatorStrategy tertiary) {

		this.primary = primary;
		this.secondary = secondary;
		this.tertiary = tertiary;

	}

	private int sortPrimary(final ITask t1, final ITask t2) {
		if (primary.compare(t1, t2) > 0) {
			return 1;
		} else if (primary.compare(t1, t2) < 0) {
			return -1;
		} else {
			return sortSecondary(t1, t2);
		}
	}

	private int sortSecondary(final ITask t1, final ITask t2) {
		if (secondary.compare(t1, t2) > 0) {
			return 1;
		} else if (secondary.compare(t1, t2) < 0) {
			return -1;
		} else {
			return sortTertiary(t1, t2);
		}
	}

	private int sortTertiary(final ITask t1, final ITask t2) {
		if (tertiary.compare(t1, t2) > 0) {
			return 1;
		} else if (tertiary.compare(t1, t2) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public int compare(final ITask t1, final ITask t2) {

		return sortPrimary(t1, t2);
	}

}
