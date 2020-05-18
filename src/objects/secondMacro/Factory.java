package objects.secondMacro;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.micro.Shopper;
import sample.Main;

public class Factory extends Building{

    private ImageView smokeSprite = new ImageView(new Image("assets/sprite.png"));
    private Animation animation;

    public Factory(double xChord, double yChord){
        this.xChord = xChord;
        this.yChord = yChord;
        Factory.numberOfBuildings++;
        this.buildingType = "Factory";
        this.buildingImage = new ImageView(new Image("assets/factory.png"));
        this.buildingImage.setPreserveRatio(true);
        this.buildingImage.setFitHeight(270);
        this.buildingText = new Label("Factory");
        this.buildingText.setFont(new Font("Segoe UI Black Italic", 13));
        this.shadow = new Ellipse(175, 40);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(10));
        this.shadow.setEffect(new GaussianBlur(40));
        this.smokeSprite.setViewport(new Rectangle2D(0,100,100,100)); // це погане рішення для того щоб зробити спрайт диму невидимий, але воно працює!
        this.smokeSprite.setPreserveRatio(true);
        this.smokeSprite.setFitHeight(60);
        this.animation = new Sprite(smokeSprite, Duration.millis(1000), 10, 10, 0, 0,100,100 );
        this.animation.setCycleCount(Animation.INDEFINITE);

        this.setBuildingInChords();

        this.buildingPicture = new Group(shadow, buildingImage, buildingText, smokeSprite);
    }

    @Override
    public void setBuildingInChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.buildingImage.setX(x);
        this.buildingImage.setY(y);
        this.buildingText.setLayoutX(x+179);
        this.buildingText.setLayoutY(y+280);
        this.shadow.setLayoutX(x+90);
        this.shadow.setLayoutY(y+230);

        this.smokeSprite.setX(x+190);
        this.smokeSprite.setY(y-70);
    }

    public Animation smoke(){
        return this.animation;
    }
    //потребує доробки
    @Override
    public void interact(Shopper shopper) {
        if (shopper.getShopperImage().getBoundsInParent().intersects(this.getBuildingImage().getBoundsInParent())) {
            shopper.getShopperImage().setImage(new Image("assets/shopperWork.png"));
            shopper.setMoney(shopper.getMoney() + Main.random.nextInt(100));
            shopper.updateShopperChords();
            shopper.getShopperImage().setImage(new Image("assets/shopper.png"));

        }
    }

    private class Sprite extends Transition {
        final private ImageView imageView;
        final private int count;
        final private int columns;
        final private int offsetX;
        final private int offsetY;
        final private int width;
        final private int height;

        public Sprite(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
            this.imageView = imageView;
            this.count = count;
            this.columns = columns;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.width = width;
            this.height = height;
            setCycleDuration(duration);

        }

        @Override
        protected void interpolate(double frac) {
            int index = Math.min((int) Math.floor(frac * count), count -1);
            int x = (index % columns) *width + offsetX;
            int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
        }
    }
}
