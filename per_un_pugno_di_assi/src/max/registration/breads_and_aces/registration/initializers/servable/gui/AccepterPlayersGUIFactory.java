package breads_and_aces.registration.initializers.servable.gui;

import java.util.concurrent.CountDownLatch;

public interface AccepterPlayersGUIFactory {
	AccepterPlayersGUI create(CountDownLatch latch);
}
