package se.chalmers.doit.core;

/**
 * Interface representing a comparable priority.
 * @author Kaufmann
 */
public interface IPriority extends Comparable<Integer>{
	/**
	 * Returns the priority value for the priority.
	 * @return byte value [-128, 127] representing the priority of the task
	 */
	public byte getValue();
}
