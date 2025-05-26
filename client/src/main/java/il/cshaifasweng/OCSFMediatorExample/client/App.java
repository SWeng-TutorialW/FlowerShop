package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static SimpleClient client;
    private static Stage mainStage;

    public static SimpleClient getClient() {
        return client;
    }

    @Override
    // the start function is used to start everything by setting up the event bus and listening between the
    // client and server (imagine a box you put a message in and another one removes the message)
    public void start(Stage stage) throws IOException {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("Server IP");
        dialog.setHeaderText("Connect to Server");
        dialog.setContentText("Please enter the server IP address:");
        String serverIP = dialog.showAndWait().orElse("localhost");

        EventBus.getDefault().register(this);
        client = new SimpleClient(serverIP, 3050);
        client.openConnection();

        scene = new Scene(loadFXML("primary"), 700, 480);
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }


    public static Stage getMainStage() {
        return mainStage;
    }




    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // loads all the needed resources
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    @Override
    // stops everything and exits
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
        client.sendToServer("remove client");
        client.closeConnection();
		super.stop();
	}

    // what this does is because we have 2 windows 1 for admin mode and 1 for user mode we use this when we want
    // to switch is just basically calls the code of the seconday controller (admin mode)
    public static void switchToAdminMode(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(App.class.getResource("/il/cshaifasweng/OCSFMediatorExample/client/secondary.fxml"))
        );
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    // same as above but switches from admin mode to user mode by calling primary controller
    public static void switchToUserMode(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(App.class.getResource("/il/cshaifasweng/OCSFMediatorExample/client/primary.fxml"))
        );
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }



    @Subscribe
    public void onWarningEvent(WarningEvent event) {
    	Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.WARNING,
        			String.format("Message: %s\nTimestamp: %s\n",
        					event.getWarning().getMessage(),
        					event.getWarning().getTime().toString())
        	);
        	alert.show();
    	});
    	
    }

	public static void main(String[] args) {
        launch();
    }

}