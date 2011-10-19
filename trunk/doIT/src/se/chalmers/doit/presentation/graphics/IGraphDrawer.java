package se.chalmers.doit.presentation.graphics;

import java.util.Collection;

import se.chalmers.doit.core.implementation.StatisticalData;

/**
 * Interface for drawing a graph from task data
 * 
 * @author Kaufmann
 * 
 */
public interface IGraphDrawer {
	/**
	 * Sets the data to draw
	 * 
	 * @param data
	 *            the data to set
	 */
	public void setData(Collection<StatisticalData> data);

	/**
	 * Sets the interval to use when drawing data
	 * 
	 * @param interval
	 *            stating for how many days you want data. Pass -1 for all time
	 */
	public void setInterval(int interval);
}