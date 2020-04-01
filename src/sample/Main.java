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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    public static ArrayList<Shopper> shoppers;
    private Random random = new Random(new Date().getTime());


    @Override
    public void start(Stage primaryStage) throws Exception {


        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 900);



        shoppers = new ArrayList<>(5);
        shoppers.add(new Shopper(new Image("assets/shopper.png"), new Image("assets/guitar.png"), false));
        shoppers.add(new Shopper(new Image("assets/shopper.png"), new Image("assets/guitar.png"), false));
        shoppers.add(new Shopper(new Image("assets/shopper.png"), new Image("assets/guitar.png"), false));

        for (Shopper s: shoppers){
            s.setXYCords(random.nextInt((int)scene.getWidth()-100),random.nextInt((int)scene.getHeight()-100));
            s.setShopperInCoords();
            root.getChildren().addAll(s.getShopperImage(), s.getInstrumentImage(),s.getShopperText(),s.getEllipseAct());
        }




        primaryStage.setScene(scene);

        primaryStage.setTitle("Lab № 4");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());







        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Shopper s: shoppers){
                    s.mouseClick(event.getX(), event.getY());
                }

            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Shopper s: shoppers){
                    s.setShopperInCoords();
                }


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
                for (Shopper s: shoppers){
                    s.up();
                }
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                for (Shopper s: shoppers){
                    s.down();
                }
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                for (Shopper s: shoppers){
                    s.right();
                }
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                for (Shopper s: shoppers){
                    s.left();
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}
