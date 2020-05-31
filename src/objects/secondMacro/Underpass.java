package objects.secondMacro;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.micro.Shopper;
import sample.Main;
import sample.Sprite;
import sample.windows.preferencesWindow.Preferences;

import java.nio.file.Paths;
import java.util.ArrayList;

public class Underpass extends Building {

    private ArrayList<Shopper> shoppersInside;
    private ImageView currentAnimation;


    public Underpass(double x, double y) {
        this.shoppersInside = new ArrayList<>(5);
        this.xChord = x;
        this.yChord = y;
        this.buildingType = "Underpass";
        Building.numberOfBuildings++;
        this.buildingImage = new ImageView(new Image("assets/underpass.png"));
        this.buildingImage.setPreserveRatio(true);
        this.buildingImage.setFitHeight(270);
        this.buildingText = new Label("Піздемний перехід\nВсередині грають: "+shoppersInside.size());
        this.buildingText.setFont(new Font("Segoe UI Black Italic", 13));
        this.shadow = new Ellipse(175, 25);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(10));
        this.shadow.setEffect(new GaussianBlur(40));


        this.buildingPicture = new Group(shadow, buildingImage, buildingText);
    }
    public Underpass(){
        this(0,0);
    }
    @Override
    public void setBuildingInChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.buildingImage.setX(x);
        this.buildingImage.setY(y);
        this.buildingText.setText("Піздемний перехід\nВсередині грають: "+shoppersInside.size());
        this.buildingText.setLayoutX(x + 130);
        this.buildingText.setLayoutY(y + 290);
        this.shadow.setLayoutX(x + 90);
        this.shadow.setLayoutY(y + 250);
    }

    @Override
    public void interact(Shopper shopper, boolean isShiftDown) {
        if (shopper.getShopperImage().getBoundsInParent().intersects(this.getBuildingImage().getBoundsInParent()) ) {
            if (shopper.getInstrument() != null) {
                this.buildingImage.setOpacity(0);
                ImageView underpassSprite = new ImageView(new Image("assets/underpassSprite.png"));
                underpassSprite.setPreserveRatio(true);
                underpassSprite.setFitHeight(this.buildingImage.getFitHeight() + 60);
                underpassSprite.setX(this.buildingImage.getX() - 40);
                underpassSprite.setY(this.buildingImage.getY() - 50);
                if (this.shoppersInside.size() != 0) {
                    currentAnimation.setOpacity(0);
                }
                currentAnimation = underpassSprite;
                this.buildingPicture.getChildren().add(underpassSprite);
                Animation dance = new Sprite(underpassSprite, Duration.millis(700), 13, 13, 0, 0, 485, 326);
                dance.setCycleCount(40);

                this.shoppersInside.add(shopper);
                shopper.getShopperPicture().setOpacity(0);
                shopper.setStay(true);
                this.setBuildingInChords();
                dance.play();

                dance.setOnFinished(event -> {
                    shopper.getShopperPicture().setOpacity(1);
                    shopper.setStay(false);
                    shopper.setMoney(shopper.getMoney() + Preferences.getCOMPLEXITY().getUnderpassSalary() / shoppersInside.size()*shopper.getPlayVariable());

                    this.shoppersInside.remove(shopper);
                    this.buildingPicture.getChildren().remove(underpassSprite);
                    if (this.shoppersInside.size() == 0)
                        this.buildingImage.setOpacity(1);
                    this.setBuildingInChords();
                });

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Візьміть в руки інструмент");
                alert.showAndWait();
            }
        }
    }

    public int getShoppersInsideSize() {
        return shoppersInside.size();
    }
}
