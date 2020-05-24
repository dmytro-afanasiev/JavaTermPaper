package sample.windows.chooseAnInstrumentWindow;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ChooseAnInstrumentWindowController {
    private String typeOfInstrument;
    @FXML
    private ListView<String> instrumentsListShopper;

    @FXML
    private Button chooseButton;


    @FXML
    void initialize() {

        ObservableList<String> instruments = FXCollections.observableArrayList(ChooseAnInstrumentWindow.getOrchestraConductor().getInstruments().keySet());
        instrumentsListShopper.getItems().add("Nothing");
        instrumentsListShopper.getItems().addAll(instruments);
        instrumentsListShopper.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                typeOfInstrument = newValue;
                System.out.println(typeOfInstrument);
            }
        });
        chooseButton.setOnAction(event -> {
            ChooseAnInstrumentWindow.getOrchestraConductor().changeAnInstrument(typeOfInstrument);
            ChooseAnInstrumentWindow.getWindow().close();

        });
    }
}


