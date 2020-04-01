package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import objects.micro.Shopper;
import sample.window.CreateShopperWindow;

import java.io.IOException;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{



        Pane root = new Pane();
        Scene scene = new Scene(root, 800,650);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Lab № 4");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());

        Shopper shopper = new Shopper(new Image("assets/shopper.png"), new Image("assets/guitar.png"),true);
        shopper.setShopperCoords(100,100);

        root.getChildren().addAll(shopper.getShopperImage(),shopper.getInstrumentImage(),shopper.getShopperText());

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shopper.mouseClick(event.getX(),event.getY());
            }
        });



        primaryStage.show();

    }

    private class KeyPressedHandler implements EventHandler<KeyEvent>{


        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.C)){
                try {
                   new CreateShopperWindow().display("window");
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
