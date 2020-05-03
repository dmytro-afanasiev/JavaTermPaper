package sample.windows.createShopperWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.firstMacro.Instrument;
import objects.micro.Shopper;
import sample.Main;



public class CreateShopperWindowController {


    private boolean isMan ;
    private String typeOfInstrument = "Nothing";
    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldMoney;

    @FXML
    private Button yesButton;

    @FXML
    private RadioButton radioMan;

    @FXML
    private RadioButton radioWoman;

    @FXML
    private ListView<String> instrumentsList;

    @FXML
    void initialize() {

        ToggleGroup toggleGroup = new ToggleGroup();
        radioMan.setToggleGroup(toggleGroup);
        radioWoman.setToggleGroup(toggleGroup);
        radioMan.setOnAction((event -> {
            isMan = true;
        }));

        radioWoman.setOnAction((event -> {
            isMan = false;
        }));

        ObservableList<String> instruments = FXCollections.observableArrayList("Nothing","Guitar","Drums","Bayan","Piano","Violin", "Trembita" );
        instrumentsList.getItems().addAll(instruments);
        instrumentsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                typeOfInstrument = newValue;
            }
        });
        radioMan.fire();
        yesButton.setOnAction(event -> {
            if (!fieldName.getText().isEmpty() && !fieldMoney.getText().isEmpty()){
                Shopper temp = new Shopper(Instrument.getInstrument(typeOfInstrument),true, fieldName.getText(), Integer.parseInt(fieldMoney.getText()),isMan);
                Main.shoppers.add(temp);
                temp.setXYCords(Main.random.nextInt((int) Main.getScene().getWidth() - 100), Main.random.nextInt((int) Main.getScene().getHeight() - 100));
                temp.setShopperInCoords();
                Main.getRoot().getChildren().add(temp.getShopperPicture());
                CreateShopperWindow.getWindow().close();
            }
        });
    }
}
