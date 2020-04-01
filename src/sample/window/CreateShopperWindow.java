package sample.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateShopperWindow {



    public boolean display(String title) throws IOException {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);

        Parent alert = FXMLLoader.load(getClass().getResource("createShopperWindow.fxml"));
        window.setScene(new Scene(alert));
        window.getIcons().add(new Image("assets/shopper.png"));


        window.show();

        return true;
    }
}
