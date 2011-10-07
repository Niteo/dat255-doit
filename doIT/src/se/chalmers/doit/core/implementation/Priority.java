package se.chalmers.doit.core.implementation;

import se.chalmers.doit.core.IPriority;

public class Priority implements IPriority {
	private final byte value;

	public Priority(final byte value) {
		this.value = value;
	}

	@Override
	public int compareTo(final IPriority priority) {
		if (value > priority.getValue()) {
			return 1;
		} else if (value < priority.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof Priority) {
			return ((Priority) o).getValue() == value;
		}
		return false;
	}

	@Override
	public byte getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		int hash = 101;
		hash = 500501 * hash + value;
		return hash;
	}

	@Override
	public String toString() {
		return new String("" + value);
	}
}