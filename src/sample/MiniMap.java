package sample;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import objects.micro.Shopper;
import objects.secondMacro.Building;


import java.util.HashMap;
import java.util.Objects;

public class MiniMap {
    final static private double SCALE = 0.13;
    private Pane pane;
    private double xChord;
    private double yChord;
    private HashMap<Shopper, ImageView> shoppersMap;
    private HashMap<Building, ImageView> buildingsMap;

    public MiniMap() {
        this.pane = new Pane();
        this.pane.setMinWidth(Main.getRoot().getMinWidth() * MiniMap.SCALE);
        this.pane.setMinHeight(Main.getRoot().getMinHeight() * MiniMap.SCALE);
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
        label.setLayoutX(pane.getMinWidth()/2.1);

        this.pane.getChildren().addAll(rectangle, border, label);
    }

    public Pane getPane() {
        return pane;
    }

    public void setXChord(double xChord) {
        this.xChord = xChord;
    }

    public void setYChord(double yChord) {
        this.yChord = yChord;
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
        imageView.setFitHeight(190*MiniMap.SCALE);
        shoppersMap.put(shopper, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteAShopper(Shopper shopper){
        pane.getChildren().remove(shoppersMap.get(shopper));
        shoppersMap.remove(shopper);
    }
    public void addBuilding(Building building) {
        ImageView imageView;
        switch (building.getType()) {
            case "Factory":
                imageView = new ImageView(new Image("assets/factory.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(270*MiniMap.SCALE);
                break;
            case "Shop":
                imageView = new ImageView(new Image("assets/shop.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(270* MiniMap.SCALE);
                break;
            default:
                imageView = new ImageView(new Image("assets/school.png"));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(360*MiniMap.SCALE);
                break;
        }
        imageView.setLayoutX(building.getXChord() * MiniMap.SCALE);
        imageView.setLayoutY(building.getYChord() * MiniMap.SCALE);

        buildingsMap.put(building, imageView);
        pane.getChildren().add(imageView);
    }
    public void deleteABuilding(Building building){
        pane.getChildren().remove(buildingsMap.get(building));
        buildingsMap.remove(building);
    }

    public void updateMap() {
        for (Shopper shopper : Main.shoppers) {
            ImageView imageView = shoppersMap.get(shopper);
            imageView.setLayoutX(shopper.getXChord() * MiniMap.SCALE);
            imageView.setLayoutY(shopper.getYChord() * MiniMap.SCALE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniMap miniMap = (MiniMap) o;
        return Double.compare(miniMap.xChord, xChord) == 0 &&
                Double.compare(miniMap.yChord, yChord) == 0 &&
                Objects.equals(pane, miniMap.pane) &&
                Objects.equals(shoppersMap, miniMap.shoppersMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pane, xChord, yChord, shoppersMap);
    }
}
