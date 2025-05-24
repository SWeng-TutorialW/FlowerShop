package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class PrimaryController {

	public static PrimaryController instance;

	public PrimaryController() {
		instance = this;
	}

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

	@FXML // fx:id="DetailsText"
	private TextField DetailsText; // Value injected by FXMLLoader

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

	@FXML
	private TableColumn<Flower, String> DescriptionColumn;

	@FXML // fx:id="TypeLabel"
	private Label TypeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="TypeText"
	private TextField TypeText; // Value injected by FXMLLoader



	public void initialize() {
		// Set up the column value factories (connects table columns to Flower fields)
		SkuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		// (Optional) Request all flowers from server when the UI loads:
		try {
			SimpleClient.getClient().sendToServer("getAllFlowers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateCatalog(java.util.List<Flower> flowerList) {
		ObservableList<Flower> flowers = FXCollections.observableArrayList(flowerList);
		Catalog.setItems(flowers);
	}

}
