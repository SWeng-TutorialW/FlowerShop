package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import java.io.IOException;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PrimaryController {

	public static PrimaryController instance;

	public PrimaryController() {
		instance = this;
	}
	private boolean isAdminMode = false;


	@FXML // fx:id="ActionColumn"
	private TableColumn<Flower, Void> ActionColumn; // Value injected by FXMLLoader

	@FXML // fx:id="AddButton"
	private Button AddButton; // Value injected by FXMLLoader

	@FXML // fx:id="AdminButton"
	private Button AdminButton; // Value injected by FXMLLoader

	@FXML // fx:id="CancelButton"
	private Button CancelButton; // Value injected by FXMLLoader

	@FXML // fx:id="Catalog"
	private TableView<Flower> Catalog; // Value injected by FXMLLoader

	@FXML // fx:id="DetailsLabel"
	private Label DetailsLabel; // Value injected by FXMLLoader

	@FXML // fx:id="DetailsArea"
	private TextArea DetailsArea; // Value injected by FXMLLoader

	@FXML // fx:id="ImageLabel"
	private Label ImageLabel; // Value injected by FXMLLoader

	@FXML // fx:id="ImageView"
	private ImageView ImageView; // Value injected by FXMLLoader

	@FXML // fx:id="LabelName"
	private Label LabelName; // Value injected by FXMLLoader

	@FXML // fx:id="NameColumn"
	private TableColumn<Flower, String> NameColumn; // Value injected by FXMLLoader

	@FXML // fx:id="NameText"
	private TextField NameText; // Value injected by FXMLLoader

	@FXML // fx:id="PriceColumn"
	private TableColumn<Flower, Double> PriceColumn; // Value injected by FXMLLoader

	@FXML // fx:id="PriceLabel"
	private Label PriceLabel; // Value injected by FXMLLoader

	@FXML // fx:id="PriceText"
	private TextField PriceText; // Value injected by FXMLLoader

	@FXML // fx:id="QuantityLabel"
	private Label QuantityLabel; // Value injected by FXMLLoader

	@FXML // fx:id="QuantitySpinner"
	private Spinner<Integer> QuantitySpinner; // Value injected by FXMLLoader

	@FXML // fx:id="SaveButton"
	private Button SaveButton; // Value injected by FXMLLoader

	@FXML // fx:id="SkuLabel"
	private Label SkuLabel; // Value injected by FXMLLoader

	@FXML // fx:id="SkuText"
	private TextField SkuText; // Value injected by FXMLLoader

	@FXML // fx:id="TypeColumn"
	private TableColumn<Flower, String> TypeColumn; // Value injected by FXMLLoader

	@FXML
	private TableColumn<Flower, Integer> SkuColumn;

	//@FXML
	//private TableColumn<Flower, String> DescriptionColumn;

	@FXML // fx:id="TypeLabel"
	private Label TypeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="TypeText"
	private TextField TypeText; // Value injected by FXMLLoader


	// this function is used to toggle between the buttons in admin mode and user mode when in user mode its disabled
	public void setAdminMode(boolean isAdmin) {
		isAdminMode = isAdmin;

		// enable/disable text fields and buttons as needed
		NameText.setEditable(isAdmin);
		TypeText.setEditable(isAdmin);
		PriceText.setEditable(isAdmin);
		DetailsArea.setEditable(isAdmin);

		SaveButton.setDisable(!isAdmin);
		CancelButton.setDisable(!isAdmin);

		// update the admin button text
		AdminButton.setText(isAdmin ? "Exit Admin Mode" : "Enter Admin Mode");
	}

	// basic init function we are setting the values and catalog to show the database
	public void initialize() {
		// Set up spinner and columns
		QuantitySpinner.setValueFactory(new javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
		SkuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		// DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description")); // If used

		// Set all fields to uneditable by default (user mode)
		NameText.setEditable(false);
		TypeText.setEditable(false);
		PriceText.setEditable(false);
		DetailsArea.setEditable(false);

		// Set up double-click to view details
		Catalog.setRowFactory(tv -> {
			TableRow<Flower> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 2) {
					Flower clickedFlower = row.getItem();
					viewFlower(clickedFlower);
				}
			});
			return row;
		});

		// Request all flowers from server once UI is ready
		Platform.runLater(() -> {
			try {
				App.getClient().sendToServer("getAllFlowers");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}


	// when we want to update the catalog we call this function which uses an observable list
	// to wrap the original flower list because an observable list reflects the changes isntantly
	public void updateCatalog(java.util.List<Flower> flowerList) {
		ObservableList<Flower> flowers = FXCollections.observableArrayList(flowerList);
		Catalog.setItems(flowers);
		Catalog.refresh();
	}

	// viewing the values of the clicked flower
	public void viewFlower(Flower flower) {
		if (flower == null) return;
		SkuText.setText(String.valueOf(flower.getSku()));
		NameText.setText(flower.getName());
		TypeText.setText(flower.getType());
		PriceText.setText(String.valueOf(flower.getPrice()));
		DetailsArea.setText(flower.getDescription());
	}

	@FXML
	// this handles what happens when you click enter admin mode where it asks you for a password verifies the password
	// and switching us to admin mode
	private void handleAdminToggle() {
		if (!isAdminMode) {
			// Prompt for admin code
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Admin Login");
			dialog.setHeaderText("Enter admin code:");
			dialog.setContentText("Code:");

			dialog.showAndWait().ifPresent(code -> {
				// Send the entered code to the server for verification!
				HashMap<String, Object> msg = new HashMap<>();
				msg.put("command", "verifyAdminCode");
				msg.put("code", code);
				try {
					App.getClient().sendToServer(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} else {
			setAdminMode(false); // Optional: for toggling back to user mode
		}
	}



}
