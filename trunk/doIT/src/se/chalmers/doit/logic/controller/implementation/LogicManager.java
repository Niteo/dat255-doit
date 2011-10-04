package se.chalmers.doit.logic.controller.implementation;

import se.chalmers.doit.logic.controller.ILogicController;

/**
 *
 * @author Boel
 *
 */
public class LogicManager {

	private static ILogicController logicController;


	/**
	 * Returns the logic controller
	 *
	 * @return The logic controller
	 */
	public static ILogicController getLogicController() {
		return logicController;
	}

	/**
	 * Initializes the logic
	 */
	public static void initLogic() {
		logicController = new LogicController();
	}

}
