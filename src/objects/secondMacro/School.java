package objects.secondMacro;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import objects.micro.Shopper;

public class School extends Building{
    public School(double x, double y) {
        this.xChord = x;
        this.yChord = y;
        this.buildingType = "School";
        School.numberOfBuildings++;
        this.buildingImage = new ImageView(new Image("assets/school.png"));
        this.buildingImage.setPreserveRatio(true);
        this.buildingImage.setFitHeight(360);
        this.buildingText = new Label("School");
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
    public void interact(Shopper shopper) {

    }

    @Override
    public void setBuildingInChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.buildingImage.setX(x);
        this.buildingImage.setY(y);
        this.buildingText.setLayoutX(x + 179);
        this.buildingText.setLayoutY(y + 330);
        this.shadow.setLayoutX(x + 90);
        this.shadow.setLayoutY(y + 270);
    }
}
