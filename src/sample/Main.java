package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import objects.micro.Shopper;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
       //  Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        Group root = new Group();
        Scene scene = new Scene(root, 800,650);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab № 4");
        primaryStage.getIcons().add(new Image("assets/icon.png"));


        scene.setOnKeyPressed(new KeyPressedHandler());


        primaryStage.show();

    }

    private class KeyPressedHandler implements EventHandler<KeyEvent>{


        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.C)){
                try {
                    CreateShopperWindow.display("window","efe");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public static void main(String[] args) {
        launch(args);

    }
}
