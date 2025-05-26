package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Flower;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SecondaryController {

    public static SecondaryController instance;
    public SecondaryController() {
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

    @FXML // fx:id="DetailsText"
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



    // same init function explain in primary controller
    @FXML
    public void initialize() {
        // Set up the table columns for the catalog
        SkuColumn.setCellValueFactory(new PropertyValueFactory<>("sku"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set up the quantity spinner
        QuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        QuantitySpinner.setDisable(false); // Enable for admin

        // Make all admin-editable fields enabled
        NameText.setEditable(true);
        TypeText.setEditable(true);
        PriceText.setEditable(true);
        DetailsArea.setEditable(true); // If DetailsText is still used

        // Enable and show Save/Cancel buttons
        SaveButton.setDisable(false);
        SaveButton.setVisible(true);
        CancelButton.setDisable(false);
        CancelButton.setVisible(true);

        // Enable Add to Cart button if you want
        AddButton.setDisable(false);

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


        // Load flower catalog from server
        try {
            App.getClient().sendToServer("getAllFlowers");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // same update catalog explained in primary controller
    public void updateCatalog(java.util.List<Flower> flowerList) {
        ObservableList<Flower> flowers = FXCollections.observableArrayList(flowerList);
        Catalog.setItems(flowers);
        Catalog.refresh();
    }

    // same view flower explain in primary controller
    public void viewFlower(Flower flower) {
        if (flower == null) return;
        SkuText.setText(String.valueOf(flower.getSku()));
        NameText.setText(flower.getName());
        TypeText.setText(flower.getType());
        PriceText.setText(String.valueOf(flower.getPrice()));
        DetailsArea.setText(flower.getDescription());
    }

    @FXML
    // press exit > goes back to user mode
    private void handleExitAdminMode(ActionEvent event) {
        try {
            App.switchToUserMode((Stage) AdminButton.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    // same as exit handleAdminMode
    private void handleCancel(ActionEvent event) {
        try {
            App.switchToUserMode((Stage) CancelButton.getScene().getWindow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // same as primary controller but in here the buttons are enabled
    public void setAdminMode(boolean isAdmin) {
        isAdminMode = isAdmin;

        NameText.setEditable(isAdmin);
        TypeText.setEditable(isAdmin);
        PriceText.setEditable(isAdmin);
        DetailsArea.setEditable(isAdmin);

        SaveButton.setDisable(!isAdmin);
        CancelButton.setDisable(!isAdmin);

        AdminButton.setText(isAdmin ? "Exit Admin Mode" : "Enter Admin Mode");
    }


    @FXML
    // when you press save button it takes the info from the data fields updated them or not and then makes a new
    // flower object which in turn then sends to the server so it updates the database
    private void handleSave(ActionEvent event) {
        try {
            //Get the data from the fields
            int sku = Integer.parseInt(SkuText.getText());
            String name = NameText.getText();
            String type = TypeText.getText();
            double price = Double.parseDouble(PriceText.getText());
            String description = DetailsArea.getText();

            // Create a new Flower object
            Flower updatedFlower = new Flower(sku, name, type, price, description);

            //Prepare a command to send to server
            HashMap<String, Object> msg = new HashMap<>();
            msg.put("command", "updateFlower");
            msg.put("flower", updatedFlower);

            App.getClient().sendToServer(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
