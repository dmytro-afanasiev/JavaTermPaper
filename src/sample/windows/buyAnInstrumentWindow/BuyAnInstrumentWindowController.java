package sample.windows.buyAnInstrumentWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import objects.firstMacro.*;

public class BuyAnInstrumentWindowController {

    private String typeOfInstrument;


    @FXML
    private ListView<String> shopInstrumentsList;

    @FXML
    private Button chooseButton;


    @FXML
    void initialize() {
        ObservableList<String> instruments = FXCollections.observableArrayList("Guitar","Drums","Bayan","Piano","Violin", "Trembita" );
        shopInstrumentsList.getItems().addAll(instruments);
        shopInstrumentsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                typeOfInstrument = newValue;
            }
        });
        chooseButton.setOnAction(event -> {
            BuyAnInstrumentWindow.getShopper().buyAnInstrument(typeOfInstrument);
            BuyAnInstrumentWindow.getWindow().close();
        });
    }
}
