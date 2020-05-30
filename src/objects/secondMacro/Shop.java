package objects.secondMacro;


import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import objects.micro.OrchestraConductor;
import objects.micro.Shopper;
import sample.windows.buyAnInstrumentWindow.BuyAnInstrumentWindow;

import java.io.IOException;

public class Shop extends Building {
    public Shop(double x, double y) {
        this.xChord = x;
        this.yChord = y;
        this.buildingType = "Shop";
        Building.numberOfBuildings++;
        this.buildingImage = new ImageView(new Image("assets/shop.png"));
        this.buildingImage.setPreserveRatio(true);
        this.buildingImage.setFitHeight(270);
        this.buildingText = new Label("Магазин ");
        this.buildingText.setFont(new Font("Segoe UI Black Italic", 13));
        this.shadow = new Ellipse(175, 40);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(10));
        this.shadow.setEffect(new GaussianBlur(40));

        this.setBuildingInChords();

        this.buildingPicture = new Group(shadow, buildingImage, buildingText);
    }

    @Override
    public void interact(Shopper shopper, boolean isShiftDown) {
        if (shopper.getShopperImage().getBoundsInParent().intersects(this.getBuildingImage().getBoundsInParent())) {
            if (!isShiftDown) {
                if (shopper instanceof OrchestraConductor || shopper.getInstrument() == null) {
                    try {
                        new BuyAnInstrumentWindow().display("Choose an instrument", shopper);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Увага!");
                    alert.setHeaderText("Повертайтеся пізніше");
                    alert.setContentText("Неможливо купити: даний музикант не може володіти більше, ніж одним інструментом!");
                    alert.showAndWait();
                }
            } else {
                shopper.sellAnInstrument();
            }
        }
    }

    @Override
    public void setBuildingInChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.buildingImage.setX(x);
        this.buildingImage.setY(y);
        this.buildingText.setLayoutX(x + 179);
        this.buildingText.setLayoutY(y + 280);
        this.shadow.setLayoutX(x + 90);
        this.shadow.setLayoutY(y + 230);
    }
}
