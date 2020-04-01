package sample;

import javafx.animation.AnimationTimer;
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

    public static Shopper shopper;


    @Override
    public void start(Stage primaryStage) throws Exception {


        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 650);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Lab № 4");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());


        shopper = new Shopper(new Image("assets/shopper.png"), new Image("assets/guitar.png"), false);
        shopper.setXYCords(100, 100);


        shopper.setShopperInCoords();
        root.getChildren().addAll(shopper.getShopperImage(), shopper.getInstrumentImage(), shopper.getShopperText(), shopper.getEllipseAct());


        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shopper.mouseClick(event.getX(), event.getY());
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                shopper.setShopperInCoords();

            }
        };
        timer.start();


        primaryStage.show();

    }

    private class KeyPressedHandler implements EventHandler<KeyEvent> {


        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.C)) {
                try {
                    new CreateShopperWindow().display("window");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getCode().equals(KeyCode.UP)) {
                shopper.up();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                shopper.down();
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                shopper.right();
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                shopper.left();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}
