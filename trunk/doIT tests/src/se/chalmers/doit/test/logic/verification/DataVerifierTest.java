package se.chalmers.doit.test.logic.verification;

import java.util.ArrayList;
import java.util.Collection;

import se.chalmers.doit.core.ITask;
import se.chalmers.doit.core.ITaskCollection;
import se.chalmers.doit.core.implementation.Task;
import se.chalmers.doit.core.implementation.TaskCollection;
import se.chalmers.doit.logic.verification.IDataVerifier;
import se.chalmers.doit.logic.verification.implementation.DataVerifier;
import android.test.AndroidTestCase;

public class DataVerifierTest extends AndroidTestCase {

	ITask shortTask;
	ITask longTask;

	@Override
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
		final IDataVerifier dataVerifier = new DataVerifier();
		Collection<ITaskCollection> verifiedCollections = new ArrayList<ITaskCollection>();
		final ITaskCollection verifiedCollection = new TaskCollection("Verified");
		final ITaskCollection newCollection = new TaskCollection("New");

		verifiedCollections.add(verifiedCollection);

		assertTrue(dataVerifier.verifyList(newCollection, verifiedCollections));
		assertFalse(dataVerifier.verifyList(verifiedCollection, verifiedCollections));

	}

	public void testVerifyTask() {

		final IDataVerifier dataVerifier = new DataVerifier();

		assertTrue(dataVerifier.verifyTask(shortTask));

		assertFalse(dataVerifier.verifyTask(longTask));

	}

}
