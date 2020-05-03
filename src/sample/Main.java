package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.firstMacro.*;
import objects.micro.Shopper;
import sample.windows.createShopperWindow.CreateShopperWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    public static ArrayList<Shopper> shoppers;
    public static Random random = new Random(new Date().getTime());
    private static Pane root = new Pane();
    private static Scene scene = new Scene(root, 1580, 900);

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        shoppers = new ArrayList<>(5);
        shoppers.add(new Shopper( false, "Dima", 500, true));

        for (Shopper s : shoppers) {
            s.setXYCords(random.nextInt((int) scene.getWidth() - 100), random.nextInt((int) scene.getHeight() - 100));
            s.setShopperInCoords();
            root.getChildren().add(s.getShopperPicture());
        }

        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getChildren().add(parent);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Let's play this instrument!");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());

        scene.setOnMousePressed(event -> {
            for (Shopper s : shoppers) {
                s.mouseClick(event.getX(), event.getY(), event.getButton());
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Shopper s : shoppers) {
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
            if (event.getCode().equals(KeyCode.M)) {
                try {
                    new CreateShopperWindow().display("Let's create new Shopper!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (event.getCode().equals(KeyCode.DELETE)) {
                for (int i = 0; i < shoppers.size(); i++) {
                    Shopper s = shoppers.get(i);
                    if (s.isActive()) {
                        root.getChildren().remove(s.getShopperPicture());
                        s = null;
                        shoppers.remove(i--);
                        Shopper.setNumberOfShoppers(Shopper.getNumberOfShoppers() - 1);
                    }

                }
                System.out.println(Shopper.getNumberOfShoppers()+"----");
                System.out.println(shoppers.size());
            }
            if (event.getCode().equals(KeyCode.ESCAPE)){
                for(Shopper s: shoppers){
                    if (s.isActive()) {
                        s.setActive(false);
                    }
                }
            }
            if (event.getCode().equals(KeyCode.UP)) {
                for (Shopper s : shoppers) {
                    s.up();
                }
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                for (Shopper s : shoppers) {
                    s.down();
                }
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                for (Shopper s : shoppers) {
                    s.right();
                }
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                for (Shopper s : shoppers) {
                    s.left();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
