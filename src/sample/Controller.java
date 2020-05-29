package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import objects.micro.Shopper;
import sample.windows.aboutWindow.AboutWindow;
import sample.windows.createShopperWindow.CreateShopperWindow;
import sample.windows.preferencesWindow.PreferencesWindow;
import java.io.*;


public class Controller {

    @FXML
    private MenuItem menuFileOpen;

    @FXML
    private MenuItem menuFileSave;

    @FXML
    private MenuItem menuFileExit;

    @FXML
    private MenuItem menuEditCreate;

    @FXML
    private MenuItem menuEditDelete;

    @FXML
    private MenuItem menuEditCancel;

    @FXML
    private MenuItem menuEditProperties;

    @FXML
    private MenuItem menuWindowsCreate;

    @FXML
    private MenuItem menuWindowsProperties;

    @FXML
    private MenuItem menuMoreAbout;

    @FXML
    void initialize() {
        EventHandler<ActionEvent> menuHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String buttonName = ((MenuItem)event.getTarget()).getText();
                if (buttonName.equals("Зберегти")){
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Виберіть директорію для збереження");
                    File file = directoryChooser.showDialog(Main.getScene().getWindow());
                    //цей момент бажано доробити!
                    Serialization.serializeNow(file.getAbsolutePath() + "\\data.xml");
                } else if (buttonName.equals("Відкрити")){
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Виберіть файл");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML-збереження", "*.xml"),
                            new FileChooser.ExtensionFilter("TXT-збереження", "*.txt"));
                    File file = fileChooser.showOpenDialog(Main.getScene().getWindow());
                    Serialization.deserializeNow(file);
                }

                else if (buttonName.equals("Вийти")){
                    Platform.exit();
                } else if (buttonName.equals("Створити персонажа")) {
                    try {
                        new CreateShopperWindow().display("Let's create new Shopper!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Видалити персонажей")){
                    for (int i = 0; i < Main.shoppers.size(); i++) {
                        Shopper shopper = Main.shoppers.get(i);
                        if (shopper.isActive()) {
                            Main.deleteAShopper(shopper);
                            i--;
                        }

                    }
                    System.out.println(Shopper.getNumberOfShoppers()+"----");
                    System.out.println(Main.shoppers.size());
                } else if (buttonName.equals("Відмінити вибір")){
                    for(Shopper s: Main.shoppers){
                        if (s.isActive()) {
                            s.setActive(false);
                        }
                    }
                } else if (buttonName.equals("Налаштування")){
                    try {
                        new PreferencesWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Створення нового персонажа")){
                    try {
                        new CreateShopperWindow().display("Let's create new Shopper!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Вікно налаштувань")){
                    try {
                        new PreferencesWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Про програму")){
                    try {
                        new AboutWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        menuFileOpen.setOnAction(menuHandler);
        menuFileOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        menuFileSave.setOnAction(menuHandler);
        menuFileSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuFileExit.setOnAction(menuHandler);
        menuFileExit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        menuEditCreate.setOnAction(menuHandler);
        menuEditCreate.setAccelerator(KeyCombination.keyCombination("f12"));
        menuEditDelete.setOnAction(menuHandler);
        menuEditDelete.setAccelerator(KeyCombination.keyCombination("Delete"));
        menuEditCancel.setOnAction(menuHandler);
        menuEditCancel.setAccelerator(KeyCombination.keyCombination("Esc"));
        menuEditProperties.setOnAction(menuHandler);
        menuEditProperties.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
        menuWindowsCreate.setOnAction(menuHandler);
        menuWindowsCreate.setAccelerator(KeyCombination.keyCombination("f12"));
        menuWindowsProperties.setOnAction(menuHandler);
        menuWindowsProperties.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
        menuMoreAbout.setOnAction(menuHandler);
        menuMoreAbout.setAccelerator(KeyCombination.keyCombination("f1"));
    }
}
