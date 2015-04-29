package breads_and_aces.game.model.controller;

import breads_and_aces.game.model.oracle.actions.Action;
import breads_and_aces.services.rmi.utils.communicator.Communicator;

public interface ICommunication {
	abstract public boolean exec(Communicator communicator, String me, Action action);
}
