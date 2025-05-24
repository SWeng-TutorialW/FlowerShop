package il.cshaifasweng.OCSFMediatorExample.client;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PrimaryController {

    @FXML
    void sendWarning(ActionEvent event) {
    	try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@FXML
	void initialize(){
		try {
			SimpleClient.getClient().sendToServer("add client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sample Skeleton for 'primary.fxml' Controller Class
	 */


	@FXML // fx:id="ActionColumn"
	private TableColumn<?, ?> ActionColumn; // Value injected by FXMLLoader

	@FXML // fx:id="AddButton"
	private Button AddButton; // Value injected by FXMLLoader

	@FXML // fx:id="AdminButton"
	private Button AdminButton; // Value injected by FXMLLoader

	@FXML // fx:id="CancelButton"
	private Button CancelButton; // Value injected by FXMLLoader

	@FXML // fx:id="Catalog"
	private TableView<?> Catalog; // Value injected by FXMLLoader

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
	private TableColumn<?, ?> NameColumn; // Value injected by FXMLLoader

	@FXML // fx:id="NameText"
	private TextField NameText; // Value injected by FXMLLoader

	@FXML // fx:id="PriceColumn"
	private TableColumn<?, ?> PriceColumn; // Value injected by FXMLLoader

	@FXML // fx:id="PriceLabel"
	private Label PriceLabel; // Value injected by FXMLLoader

	@FXML // fx:id="PriceText"
	private TextField PriceText; // Value injected by FXMLLoader

	@FXML // fx:id="QuantityLabel"
	private Label QuantityLabel; // Value injected by FXMLLoader

	@FXML // fx:id="QuantitySpinner"
	private Spinner<?> QuantitySpinner; // Value injected by FXMLLoader

	@FXML // fx:id="SaveButton"
	private Button SaveButton; // Value injected by FXMLLoader

	@FXML // fx:id="SkuLabel"
	private Label SkuLabel; // Value injected by FXMLLoader

	@FXML // fx:id="SkuText"
	private TextField SkuText; // Value injected by FXMLLoader

	@FXML // fx:id="TypeColumn"
	private TableColumn<?, ?> TypeColumn; // Value injected by FXMLLoader

	@FXML // fx:id="TypeLabel"
	private Label TypeLabel; // Value injected by FXMLLoader

	@FXML // fx:id="TypeText"
	private TextField TypeText; // Value injected by FXMLLoader




}
