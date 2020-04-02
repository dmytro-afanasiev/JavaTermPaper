package sample.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateShopperWindow {

    private static Stage window = new Stage();

    public boolean display(String title) throws IOException {




        window.setTitle(title);

        Parent alert = FXMLLoader.load(getClass().getResource("createShopperWindow.fxml"));
        window.setScene(new Scene(alert));
        window.getIcons().add(new Image("assets/shopper.png"));


        window.show();

        return true;
    }

    public static Stage getWindow() {
        return window;
    }
}
