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
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import objects.firstMacro.*;
import objects.micro.MusicianMaster;
import objects.micro.OrchestraConductor;
import objects.micro.Shopper;
import objects.secondMacro.Building;
import objects.secondMacro.Factory;
import objects.secondMacro.School;
import objects.secondMacro.Shop;
import sample.windows.buyAnInstrumentWindow.BuyAnInstrumentWindow;
import sample.windows.createShopperWindow.CreateShopperWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    public static ArrayList<Shopper> shoppers = new ArrayList<>(5);
    public static ArrayList<Building> buildings = new ArrayList<>(5);
    public static Random random = new Random(new Date().getTime());

    private static Pane root = new Pane();
    private static ScrollPane scrollPane = new ScrollPane(root);
    private static Scene scene = new Scene(scrollPane, 1920, 1080);
    private static MiniMap miniMap;

    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    public static MiniMap getMiniMap() {
        return miniMap;
    }

    private static double scrollX;
    private static double scrollY;

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }


    public static void addNewShopper(Shopper shopper, boolean rand) {
        Main.shoppers.add(shopper);
        if (rand)
            shopper.setXYCords(random.nextInt((int) (scene.getWidth() + scrollX)), random.nextInt((int) (scene.getHeight() + scrollY)));
        shopper.updateShopperChords();
        root.getChildren().add(Building.getNumberOfBuildings(),shopper.getShopperPicture());
        Main.miniMap.addShopper(shopper);
    }

    public static void deleteAShopper(Shopper shopper) {
        Main.miniMap.deleteAShopper(shopper);
        root.getChildren().remove(shopper.getShopperPicture());
        shoppers.remove(shopper);
        Shopper.setNumberOfShoppers(Shopper.getNumberOfShoppers() - 1);
    }

    public static void addNewBuilding(Building building) {
        Main.buildings.add(building);
        root.getChildren().add(0,building.getBuildingPicture());
        Main.miniMap.addBuilding(building);
    }

    public static void deleteABuilding(Building building) {
        Main.miniMap.deleteABuilding(building);
        root.getChildren().remove(building.getBuildingPicture());
        buildings.remove(building);
        Building.setNumberOfBuildings(Building.getNumberOfBuildings() - 1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root.setMinWidth(4800);
        root.setMinHeight(2700);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //scrollPane.pannableProperty().set(true);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //root.getChildren().add(new ImageView(new Image("assets/back.jpg")));

        miniMap = new MiniMap();


        Main.addNewBuilding(new Factory(2000, 1000));
        Main.addNewBuilding(new Factory(2000, 400));
        Main.addNewBuilding(new Shop(600, 400));
        Main.addNewBuilding(new School(1000, 1000));


        Main.addNewShopper(new Shopper(false, "Dima", 5000, true),true);
        Main.addNewShopper(new Shopper(false, "Dima", 500, true),true);
        Main.addNewShopper(new Shopper(false, "Dima", 500, true),true);
        Main.addNewShopper(new OrchestraConductor(new Tremb(), false, "master", 1000),true);

        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getChildren().add(parent);
        root.getChildren().add(miniMap.getPane());


        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds>
                                        observable, Bounds oldBounds,
                                Bounds bounds) {
                double scrollWidth;
                double scrollHeight;
                Main.scrollX = -1 * (int) bounds.getMinX();
                scrollWidth = -1 * (int) bounds.getMinX() + (int) bounds.getWidth();
                Main.scrollY = -1 * (int) bounds.getMinY();
                scrollHeight = -1 * (int) bounds.getMinY() + bounds.getHeight();

                //постійний здвиг стрічки меню при прокручуванні
                parent.setLayoutX(scrollX);
                parent.setLayoutY(scrollY);

                // постійни здвиг карти при прокручуванні
                miniMap.getPane().setLayoutX(scrollX + 10);
                miniMap.getPane().setLayoutY(scrollY + scene.getHeight() - miniMap.getPane().getHeight() - 25);
                miniMap.getMainArea().setLayoutX(scrollX*MiniMap.getSCALE());
                miniMap.getMainArea().setLayoutY(scrollY*MiniMap.getSCALE());

                //просто показує координати в даний момент
                System.out.println(" X from " + Main.scrollX + " to " +
                        scrollWidth + "; Y from " + Main.scrollY + " to " +
                        scrollHeight);
            }
        });

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Let's play this instrument!");
        primaryStage.getIcons().add(new Image("assets/icon.png"));
        scene.setOnKeyPressed(new KeyPressedHandler());

        root.setOnMousePressed(event -> {
            for (Shopper shopper : shoppers) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    shopper.mouseClick(event.getX(), event.getY());
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (shopper instanceof OrchestraConductor) {
                        ((OrchestraConductor) shopper).chooseAnInstrument(event.getX(), event.getY());
                    }
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (Shopper shopper : shoppers) {
                    shopper.freeRun();
                    shopper.updateShopperChords();
                }
                miniMap.updateMap();
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
                    Shopper shopper = shoppers.get(i);
                    if (shopper.isActive()) {
                        Main.deleteAShopper(shopper);
                    }

                }
                System.out.println(Shopper.getNumberOfShoppers() + "----");
                System.out.println(shoppers.size());
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                for (Shopper shopper : shoppers) {
                    if (shopper.isActive()) {
                        shopper.setActive(false);
                    }
                }
            }
            if (event.getCode().equals(KeyCode.J)) {
                for (Shopper shopper : shoppers) {
                    if (shopper.isActive()) {
                        for (Building b : buildings) {
                            b.interact(shopper, event.isShiftDown());
                        }
                    }
                }
            }
            if (event.getCode().equals(KeyCode.K)) {
                for (Shopper shopper : shoppers) {
                    if (shopper.isActive() && shopper.getInstrument() != null) {
                        shopper.playAnInstrument();
                        break;
                    }
                }
            }
            if (event.getCode().equals(KeyCode.W) && !event.isShiftDown()) {
                for (Shopper shopper : shoppers) {
                    shopper.up(1);
                }
            } else if (event.getCode().equals(KeyCode.S) && !event.isShiftDown()) {
                for (Shopper shopper : shoppers) {
                    shopper.down(1);
                }
            } else if (event.getCode().equals(KeyCode.D) && !event.isShiftDown()) {
                for (Shopper shopper : shoppers) {
                    shopper.right(1);
                }
            } else if (event.getCode().equals(KeyCode.A) && !event.isShiftDown()) {
                for (Shopper shopper : shoppers) {
                    shopper.left(1);
                }
            } else if (event.getCode().equals(KeyCode.W)) {
                for (Shopper shopper : shoppers) {
                    shopper.up(2);
                }
            } else if (event.getCode().equals(KeyCode.S)) {
                for (Shopper shopper : shoppers) {
                    shopper.down(2);
                }
            } else if (event.getCode().equals(KeyCode.D)) {
                for (Shopper shopper : shoppers) {
                    shopper.right(2);
                }
            } else if (event.getCode().equals(KeyCode.A)) {
                for (Shopper shopper : shoppers) {
                    shopper.left(2);
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
