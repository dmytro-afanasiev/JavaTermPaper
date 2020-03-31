package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateShopperWindow {
    public static boolean display(String title, String messege) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Parent alert = FXMLLoader.load(CreateShopperWindow.class.getResource("createShopperWindow.fxml"));
        window.setScene(new Scene(alert));

        window.show();
        return true;
    }
}
