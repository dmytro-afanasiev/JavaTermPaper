package sample.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class CreateShopperWindow {

    private static Stage window;
    public CreateShopperWindow(){
        window = new Stage();
        window.initModality(APPLICATION_MODAL);
    }


    public boolean display(String title) throws IOException {

        window.setTitle(title);
        Parent alert = FXMLLoader.load(CreateShopperWindow.class.getResource("createShopperWindow.fxml"));
        Scene scene = new Scene(alert);
        window.setScene(scene);
        window.getIcons().add(new Image("assets/windowIcon.png"));
        window.show();

        return true;
    }

    public static Stage getWindow() {
        return window;
    }
}
