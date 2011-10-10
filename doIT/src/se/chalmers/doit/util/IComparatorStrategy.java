package se.chalmers.doit.util;

import java.io.Serializable;
import java.util.Comparator;

import se.chalmers.doit.core.ITask;

/**
 * Strategy for comparing classes implementing ITask.
 *
 * @author Robert Kaufmann, Karl Bristav
 */
public interface IComparatorStrategy extends Comparator<ITask>, Serializable {

	@Override
	public int compare(ITask t1, ITask t2);

}