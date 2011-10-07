package se.chalmers.doit.test.logic.verification;

import org.junit.Before;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;
import android.test.AndroidTestCase;

public class DataVerifierTest extends AndroidTestCase {

	ITask shortTask;
	ITask longTask;

	@Override
	@Before
	public void setUp() throws Exception {

		final StringBuilder sb = new StringBuilder();
		String longString;
		String shortString;

		for (int i = 0; i < 32; i++) {
			sb.append("a");
		}

		shortString = sb.toString();
		longString = sb.toString();

		longString += "a";

		shortTask = new Task(shortString, "", false);
		longTask = new Task(longString, "", false);
	}

	public void testDataVerifier() {
		new DataVerifier();
	}

	public void testVerifyList() {
		// TODO: implement after the proper method is implemented
	}

	public void testVerifyTask() {

		final IDataVerifier dataVerifier = new DataVerifier();

		assertTrue(dataVerifier.verifyTask(shortTask));

		assertFalse(dataVerifier.verifyTask(longTask));

	}

}
