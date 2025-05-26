package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {
	private static SimpleClient client = null;



	public SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	// it basically receives something from the server and acts accordingly
	//for example if we receive a new list after the catalog has been updated it updates tablieview
	//also for verification it receives if the code inputted correct or not and works according to what the server sends
	protected void handleMessageFromServer(Object msg) {
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
		} else if (msg instanceof String) {
			String s = (String) msg;
			if (s.equals("admin_login_success")) {
				Platform.runLater(() -> {
					try {
						// This will swap to the admin (secondary) scene!
						App.switchToAdminMode(App.getMainStage());
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} else if (s.equals("admin_login_failed")) {
				Platform.runLater(() -> {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect admin code.", ButtonType.OK);
					alert.showAndWait();
				});
			} else {
				// Other string messages
				System.out.println(msg);
			}
		}
	}


	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3050);
		}
		return client;
	}
}
