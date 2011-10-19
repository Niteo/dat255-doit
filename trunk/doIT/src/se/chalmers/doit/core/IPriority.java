package se.chalmers.doit.core;

/**
 * Interface representing a immutable comparable priority.
 * 
 * @author Kaufmann
 */
public interface IPriority extends Comparable<IPriority> {
	/**
	 * Returns the priority value for the priority.
	 * 
	 * @return byte value [-128, 127] representing the priority of the task
	 */
	public byte getValue();
}
