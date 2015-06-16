package bread_and_aces.registration.initializers.servable._gui;

import java.util.concurrent.CountDownLatch;

public interface AccepterPlayersGUIFactory {
	AccepterPlayersGUI create(CountDownLatch latch);
}
