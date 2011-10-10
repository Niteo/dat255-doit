package se.chalmers.doit.data.storage.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import se.chalmers.doit.core.IStatisticalData;
import se.chalmers.doit.core.implementation.StatisticalData;
import se.chalmers.doit.data.storage.IStatisticsDataStorage;

/**
 * Class storing cached statistics data
 * 
 * @author Karl Bristav
 * 
 */
public class StatisticsDataCache implements IStatisticsDataStorage {

	private final Collection<IStatisticalData> data = new ArrayList<IStatisticalData>();

	/**
	 * Checks if there already exists an IStatisticalData object with the
	 * current date in the data cache
	 * 
	 * @param date
	 *            The current date
	 * @return The IStatisticalData object with the current date, null if there
	 *         exists no IStatisticalData with the current date
	 */
	private IStatisticalData _getExistingData(final Date date) {

		IStatisticalData retData = null;

		for (final IStatisticalData d : data) {
			if (d.sameDay(date)) {
				retData = d;
			}
		}

		return retData;
	}

	@Override
	public void reportFinishedTasks(final int numberOfFinishedTasks,
			final Date date) {

		if (numberOfFinishedTasks > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addFinishedTasks(numberOfFinishedTasks);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addFinishedTasks(numberOfFinishedTasks);

				data.add(anotherData);
			}
		}
	}

	@Override
	public void reportCreatedTasks(final int numberOfCreatedTasks,
			final Date date) {

		if (numberOfCreatedTasks > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addCreatedTasks(numberOfCreatedTasks);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addCreatedTasks(numberOfCreatedTasks);

				data.add(anotherData);
			}
		}
	}

	@Override
	public void reportDeletedTasks(final int numberOfDeletedTasks,
			final Date date) {

		if (numberOfDeletedTasks > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addDeletedTasks(numberOfDeletedTasks);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addDeletedTasks(numberOfDeletedTasks);

				data.add(anotherData);
			}
		}
	}

	@Override
	public void reportOverdueTasks(final int numberOfOverdueTasks,
			final Date date) {

		if (numberOfOverdueTasks > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addOverdueTasks(numberOfOverdueTasks);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addOverdueTasks(numberOfOverdueTasks);

				data.add(anotherData);
			}
		}
	}

	@Override
	public void reportCreatedLists(final int numberOfCreatedLists,
			final Date date) {

		if (numberOfCreatedLists > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addCreatedLists(numberOfCreatedLists);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addCreatedLists(numberOfCreatedLists);

				data.add(anotherData);
			}
		}
	}

	@Override
	public void reportDeletedLists(final int numberOfDeletedLists,
			final Date date) {

		if (numberOfDeletedLists > 0) {

			final IStatisticalData newData = _getExistingData(date);

			if (newData != null) {
				newData.addDeletedLists(numberOfDeletedLists);
			} else {
				final StatisticalData anotherData = new StatisticalData(date);
				anotherData.addDeletedLists(numberOfDeletedLists);

				data.add(anotherData);
			}
		}
	}

	@Override
	public Collection<IStatisticalData> getStatisticsData() {
		final Collection<IStatisticalData> ret = new ArrayList<IStatisticalData>();
		for (final IStatisticalData d : data) {
			ret.add(d);
		}
		return ret;
	}

	@Override
	public void clearData() {
		data.clear();

	}

}
