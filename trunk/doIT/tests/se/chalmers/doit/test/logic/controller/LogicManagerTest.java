package se.chalmers.doit.test.logic.controller;

import se.chalmers.doit.logic.controller.implementation.LogicManager;
import android.test.AndroidTestCase;

public class LogicManagerTest extends AndroidTestCase {

	public void testInitLogic() {
		LogicManager.initLogic();
	}

	public void testGetLogicController() {
		assertNull(LogicManager.getLogicController());
		LogicManager.initLogic();
		assertNotNull(LogicManager.getLogicController());
	}

}
