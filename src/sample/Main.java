package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import objects.firstMacro.Tremb;
import objects.micro.OrchestraConductor;
import objects.micro.Shopper;
import objects.secondMacro.Factory;
import objects.secondMacro.School;
import objects.secondMacro.Shop;
import objects.secondMacro.Underpass;
import objects.thirdMacro.World;

import java.util.Date;
import java.util.Random;

public class Main extends Application {


    final private static int SCENE_WIDTH = 1920;
    final private static int SCENE_HEIGHT = 1080;

    public static Random random = new Random(new Date().getTime());

    private static World world = new World();
    private static ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static Scene scene = new Scene(scrollPane, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);


    private static double scrollX;
    private static double scrollY;

    public static int getSceneWidth() {
        return SCENE_WIDTH;
    }
    public static int getSceneHeight() {
        return SCENE_HEIGHT;
    }


    public static World getWorld() {
        return world;
    }
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }
    public static Scene getScene() {
        return scene;
    }


    public static double getScrollX() {
        return scrollX;
    }
    public static double getScrollY() {
        return scrollY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //scrollPane.pannableProperty().set(true);
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //root.getChildren().add(new ImageView(new Image("assets/back.jpg")));

        world.addNewBuilding(new Factory());
        world.addNewBuilding(new Factory());
        world.addNewBuilding(new Shop());
        world.addNewBuilding(new School());
        world.addNewBuilding(new Underpass());

        world.addNewShopper(new Shopper(false, "Dima", 5000, true),true);
        world.addNewShopper(new Shopper(false, "Dima", 500, true),true);
        world.addNewShopper(new Shopper(false, "Dima", 500, true),true);
        world.addNewShopper(new OrchestraConductor(new Tremb(), false, "Вася", 1000),true);

        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        world.getRoot().getChildren().add(parent);


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
                world.getMiniMap().getPane().setLayoutX(scrollX + 10);
                world.getMiniMap().getPane().setLayoutY(scrollY + scene.getHeight() - world.getMiniMap().getPane().getHeight() - 25);
                world.getMiniMap().getMainArea().setLayoutX(scrollX*MiniMap.getSCALE());
                world.getMiniMap().getMainArea().setLayoutY(scrollY*MiniMap.getSCALE());

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


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (Shopper shopper : world.getShoppers()) {
                    shopper.freeRun();
                    shopper.updateShopperChords();
                }
                world.getMiniMap().updateMap();
            }
        };
        timer.start();

        primaryStage.show();
    }

    private class KeyPressedHandler implements EventHandler<KeyEvent> {

        //З ПИТАННЯМИ, ЧОМУ ТУТ ПОЛОВИНА КОДУ ВИКЛЮЧЕНА, ЗВЕРТАТИСЯ ДО МЕНЕ!!!

        @Override
        public void handle(KeyEvent event) {
            /*if (event.getCode().equals(KeyCode.M)) {
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
                        i--;
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
            }*/
            if (event.getCode().equals(KeyCode.W) && !event.isShiftDown()) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.up(1);
                }
            } else if (event.getCode().equals(KeyCode.S) && !event.isShiftDown()) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.down(1);
                }
            } else if (event.getCode().equals(KeyCode.D) && !event.isShiftDown()) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.right(1);
                }
            } else if (event.getCode().equals(KeyCode.A) && !event.isShiftDown()) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.left(1);
                }
            } else if (event.getCode().equals(KeyCode.W)) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.up(2);
                }
            } else if (event.getCode().equals(KeyCode.S)) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.down(2);
                }
            } else if (event.getCode().equals(KeyCode.D)) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.right(2);
                }
            } else if (event.getCode().equals(KeyCode.A)) {
                for (Shopper shopper : world.getShoppers()) {
                    shopper.left(2);
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
