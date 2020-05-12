package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.firstMacro.*;
import objects.micro.MusicianMaster;
import objects.micro.OrchestraConductor;
import objects.micro.Shopper;
import objects.secondMacro.Factory;
import sample.windows.createShopperWindow.CreateShopperWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    public static ArrayList<Shopper> shoppers;
    public static Random random = new Random(new Date().getTime());

    private static Pane root = new Pane();
    private static ScrollPane scrollPane = new ScrollPane(root);
    private static Scene scene = new Scene(scrollPane, 1920, 1080);

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    private static double scrollX;
    private static double scrollY;
    public static double getScrollX() {
        return scrollX;
    }
    public static double getScrollY() {
        return scrollY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root.setMinWidth(8000);
        root.setMinHeight(2500);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //scrollPane.pannableProperty().set(true);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //root.getChildren().add(new ImageView(new Image("assets/back.jpg")));
        Factory factory = new Factory(150,150);
        Factory factory2 = new Factory(2000,400);
        root.getChildren().addAll(factory.getBuildingPicture(), factory2.getBuildingPicture());
        factory.smoke().play();




        shoppers = new ArrayList<>(5);
        shoppers.add(new Shopper(false, "Dima", 500, true));
        shoppers.add(new Shopper(false, "Dima", 500, true));
        shoppers.add(new Shopper(false, "Dima", 500, true));
        OrchestraConductor t = new OrchestraConductor(new Tremb(), false, "master", 1000);
        t.getInstruments().put(new Guitar().getType(), new Guitar());
        t.getInstruments().put(new Piano().getType(), new Piano());
        t.getInstruments().put(new Drums().getType(), new Drums());
        shoppers.add(t);

        for (Shopper s : shoppers) {
            s.setXYCords(random.nextInt((int) (scene.getWidth() +scrollX)), random.nextInt((int) (scene.getHeight() + scrollY)));
            s.updateShopperChords();
            root.getChildren().add(s.getShopperPicture());
        }



        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getChildren().add(parent);

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds>
                                        observable, Bounds oldBounds,
                                Bounds bounds) {
                double scrollWidth;
                double scrollHeight;
                Main.scrollX = -1 * (int) bounds.getMinX();
                scrollWidth =  -1 * (int) bounds.getMinX() + (int) bounds.getWidth();
                Main.scrollY = -1 * (int) bounds.getMinY();
                scrollHeight = -1 * (int) bounds.getMinY() + bounds.getHeight();

                parent.setLayoutX(scrollX);
                parent.setLayoutY(scrollY);


                System.out.println(" X from " + Main.scrollX + " to " +
                        scrollWidth + "; Y from " + Main.scrollY + " to " +
                        scrollHeight);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Let's play this instrument!");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());

        root.setOnMousePressed(event -> {
            for (Shopper s : shoppers) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    s.mouseClick(event.getX(), event.getY());
                } else if (event.getButton().equals(MouseButton.SECONDARY)){
                    if (s instanceof OrchestraConductor){
                        ((OrchestraConductor) s).chooseAnInstrument(event.getX(), event.getY());
                    }
                }
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {


                for (Shopper s : shoppers) {
                    s.freeRun();
                    s.updateShopperChords();
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
                System.out.println(Shopper.getNumberOfShoppers() + "----");
                System.out.println(shoppers.size());
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                for (Shopper s : shoppers) {
                    if (s.isActive()) {
                        s.setActive(false);
                    }
                }
            }
            if (event.getCode().equals(KeyCode.W) && !event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.up(1);
                }
            } else if (event.getCode().equals(KeyCode.S) && !event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.down(1);
                }
            } else if (event.getCode().equals(KeyCode.D) && !event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.right(1);
                }
            } else if (event.getCode().equals(KeyCode.A) && !event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.left(1);
                }
            } else if (event.getCode().equals(KeyCode.W) && event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.up(2);
                }
            } else if (event.getCode().equals(KeyCode.S) && event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.down(2);
                }
            } else if (event.getCode().equals(KeyCode.D) && event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.right(2);
                }
            } else if (event.getCode().equals(KeyCode.A) && event.isShiftDown()) {
                for (Shopper s : shoppers) {
                    s.left(2);
                }
            }
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}
