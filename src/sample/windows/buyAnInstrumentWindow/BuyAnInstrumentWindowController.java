package sample.windows.buyAnInstrumentWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.firstMacro.*;

public class BuyAnInstrumentWindowController {

    private String typeOfInstrument;


    @FXML
    private Button chooseButton;

    @FXML
    private RadioButton radioGuitar;

    @FXML
    private RadioButton radioViolin;

    @FXML
    private RadioButton radioPiano;

    @FXML
    private RadioButton radioBayan;

    @FXML
    private RadioButton radioDrums;

    @FXML
    private RadioButton radioTremb;

    @FXML
    private Label guitarLabel;

    @FXML
    private Label pianoLabel;

    @FXML
    private Label bayanLabel;

    @FXML
    private Label violinLabel;

    @FXML
    private Label drumsLabel;

    @FXML
    private Label trembLabel;

    @FXML
    void initialize() {

        ToggleGroup toggleGroup = new ToggleGroup();
        radioBayan.setToggleGroup(toggleGroup);
        radioDrums.setToggleGroup(toggleGroup);
        radioGuitar.setToggleGroup(toggleGroup);
        radioPiano.setToggleGroup(toggleGroup);
        radioTremb.setToggleGroup(toggleGroup);
        radioViolin.setToggleGroup(toggleGroup);

        radioBayan.setOnAction(event -> {
            typeOfInstrument = "Bayan";
        });
        radioDrums.setOnAction(event -> {
            typeOfInstrument = "Drums";
        });
        radioGuitar.setOnAction(event -> {
            typeOfInstrument = "Guitar";
        });
        radioPiano.setOnAction(event -> {
            typeOfInstrument = "Piano";
        });
        radioTremb.setOnAction(event -> {
            typeOfInstrument = "Trembita";
        });
        radioViolin.setOnAction(event -> {
            typeOfInstrument = "Violin";
        });
        radioGuitar.fire();
        guitarLabel.setText(new Guitar().getType() + " --" + new Guitar().getPrise());
        pianoLabel.setText(new Piano().getType() + " --" + new Piano().getPrise());
        bayanLabel.setText(new Bayan().getType() + " --" + new Bayan().getPrise());
        violinLabel.setText(new Violin().getType() + " --" + new Violin().getPrise());
        drumsLabel.setText(new Drums().getType() + " --" + new Drums().getPrise());
        trembLabel.setText(new Tremb().getType() + " --" + new Tremb().getPrise());


        chooseButton.setOnAction(event -> {
            BuyAnInstrumentWindow.getShopper().buyAnInstrument(typeOfInstrument);
            BuyAnInstrumentWindow.getWindow().close();
        });
    }
}
