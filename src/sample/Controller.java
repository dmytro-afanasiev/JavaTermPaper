package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import objects.micro.Shopper;
import sample.windows.aboutWindow.AboutWindow;
import sample.windows.createShopperWindow.CreateShopperWindow;
import sample.windows.preferencesWindow.PreferencesWindow;

import java.io.IOException;

public class Controller {

    @FXML
    private MenuItem menuFileOpen;

    @FXML
    private MenuItem menuFileClose;

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
                        Shopper s = Main.shoppers.get(i);
                        if (s.isActive()) {
                            Main.deleteAShopper(s);
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
        menuFileClose.setOnAction(menuHandler);
        menuFileExit.setOnAction(menuHandler);
        menuEditCreate.setOnAction(menuHandler);
        menuEditDelete.setOnAction(menuHandler);
        menuEditCancel.setOnAction(menuHandler);
        menuEditProperties.setOnAction(menuHandler);
        menuWindowsCreate.setOnAction(menuHandler);
        menuWindowsProperties.setOnAction(menuHandler);
        menuMoreAbout.setOnAction(menuHandler);
    }
}
