package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import objects.micro.Shopper;
import objects.secondMacro.Building;
import objects.thirdMacro.World;
import sample.windows.preferencesWindow.Preferences;

import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    private Pane pane;
    private HashMap<Shopper, ImageView> shoppersMap;
    private HashMap<Building, ImageView> buildingsMap;
    private Rectangle mainArea;

    public MiniMap() {
        this.pane = new Pane();
        this.pane.setMinWidth(World.getRootWidth() * MiniMap.SCALE);
        this.pane.setMinHeight(World.getRootHeight() * MiniMap.SCALE);
        shoppersMap = new HashMap<>();
        buildingsMap = new HashMap<>();


        Rectangle rectangle = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        rectangle.setFill(Color.LIGHTGREY);
        Rectangle border = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        border.setFill(Color.TRANSPARENT);
        border.setStrokeWidth(2);
        border.setStroke(Color.BLACK);
        Label label = new Label("Map");
        label.setFont(new Font("Segoe UI Black Italic", 16));
        label.setLayoutX(pane.getMinWidth() / 2.1);

        mainArea = new Rectangle(0, 0, Main.getSceneWidth() * MiniMap.SCALE, Main.getSceneHeight() * MiniMap.SCALE - 10);//Цієї "10" не повинно бути, вона виправляє якийсь баг в коді, бо я не знаю, де його найти, щоб виправити по-справжньому
        mainArea.setFill(Color.TRANSPARENT);
        mainArea.setStrokeWidth(2);
        mainArea.setStroke(Color.YELLOW);
        this.pane.getChildren().addAll(rectangle, border, label, mainArea);

        this.pane.setOnMousePressed(event -> {
            this.moveTo(event.getX(), event.getY());
        });
    }

    public void moveTo(double x, double y) {
        if (x < mainArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(0);
        } else if (x > pane.getWidth() - mainArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(1);
        } else Main.getScrollPane().setHvalue(x / pane.getWidth());

        if (y < mainArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(0);
        } else if (y > pane.getHeight() - mainArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(1);
        } else Main.getScrollPane().setVvalue(y / pane.getHeight());
    }

    public Pane getPane() {
        return pane;
    }

    public Rectangle getMainArea() {
        return mainArea;
    }

    public static double getSCALE() {
        return SCALE;
    }

    public void addShopper(Shopper shopper) {
        ImageView imageView;
        switch (shopper.getType()) {
            case "Orchestra":
                imageView = new ImageView(new Image("assets/orchestra.png"));
                break;
            case "Master":
                imageView = new ImageView(new Image("assets/master.png"));
                break;
            default:
                imageView = new ImageView(new Image("assets/shopper.png"));
                break;
        }

        imageView.setLayoutX(shopper.getXChord() * MiniMap.SCALE);
        imageView.setLayoutY(shopper.getYChord() * MiniMap.SCALE);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(190 * MiniMap.SCALE);
        shoppersMap.put(shopper, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteAShopper(Shopper shopper) {
        pane.getChildren().remove(shoppersMap.get(shopper));
        shoppersMap.remove(shopper);
    }

    public void addBuilding(Building building) {
        ImageView imageView;
        switch (building.getType()) {
            case "Factory":
                imageView = new ImageView(new Image("assets/factory.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(building.getBuildingImage().getFitHeight() * MiniMap.SCALE);
                break;
            case "Shop":
                imageView = new ImageView(new Image("assets/shop.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(building.getBuildingImage().getFitHeight() * MiniMap.SCALE);
                break;
            case "Underpass":
                imageView = new ImageView(new Image("assets/underpass.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(building.getBuildingImage().getFitHeight() * MiniMap.SCALE);
                break;
            default:
                imageView = new ImageView(new Image("assets/school.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(building.getBuildingImage().getFitHeight() * MiniMap.SCALE);
                break;
        }
        imageView.setLayoutX(building.getXChord() * MiniMap.SCALE);
        imageView.setLayoutY(building.getYChord() * MiniMap.SCALE);

        buildingsMap.put(building, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteABuilding(Building building) {
        pane.getChildren().remove(buildingsMap.get(building));
        buildingsMap.remove(building);
    }

    public void updateMap() {
        if (Preferences.isMAP()) {
            pane.setOpacity(1);
        } else pane.setOpacity(0);
        for (Shopper shopper : Main.getWorld().getShoppers()) {
            ImageView imageView = shoppersMap.get(shopper);
            imageView.setLayoutX(shopper.getXChord() * MiniMap.SCALE);
            imageView.setLayoutY(shopper.getYChord() * MiniMap.SCALE);
        }
    }
}
