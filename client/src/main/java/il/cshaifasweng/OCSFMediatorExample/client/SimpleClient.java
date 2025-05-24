package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("Received message from server: " + msg);
		if (msg instanceof Warning) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		// Check for List<Flower>
		else if (msg instanceof List<?>) {
			List<?> list = (List<?>) msg;
			if (!list.isEmpty() && list.get(0) instanceof Flower) {
				// Update TableView on JavaFX thread
				Platform.runLater(() -> {
					if (PrimaryController.instance != null) {
						PrimaryController.instance.updateCatalog((List<Flower>) list);
					}
					if (SecondaryController.instance != null) {
						SecondaryController.instance.updateCatalog((List<Flower>) list);
					}
				});
			} else {
				// Handle empty flower list
				Platform.runLater(() -> {
					if (PrimaryController.instance != null) {
						PrimaryController.instance.updateCatalog(java.util.Collections.emptyList());
					}
					if (SecondaryController.instance != null) {
						SecondaryController.instance.updateCatalog(java.util.Collections.emptyList());
					}
				});

			}
		} else {
			// Handle plain string or other types
			System.out.println(msg);
		}
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}
}
