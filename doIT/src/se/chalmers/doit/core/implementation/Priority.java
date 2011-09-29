package se.chalmers.doit.core.implementation;

import se.chalmers.doit.core.IPriority;

public class Priority implements IPriority {
	private byte value;
	
	public Priority(byte value){
		this.value = value;
	}

	@Override
	public int compareTo(IPriority priority) {
		if (value > priority.getValue()) {
			return 1;
		} else if (value < priority.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public byte getValue() {
		return value;
	}
}