package objects.micro;


import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.firstMacro.Instrument;
import sample.Sprite;
import sample.windows.preferencesWindow.Preferences;

import java.nio.file.Paths;

public class MusicianMaster extends Shopper {

    public MusicianMaster(Instrument instrument, boolean isActive, String name, double money , boolean isMan){
        super(instrument, isActive,  name,  money ,  isMan);
        this.type = "Master";
        if (isMan){
            this.shopperImage  = new ImageView(new Image("assets/master.png"));
        } else this.shopperImage  = new ImageView(new Image("assets/girlMaster.png"));

        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30,0,0, Color.GRAY));

        this.instrument = instrument;

        this.shopperText = new Label(this.name + ", money: "+ this.money);
        this.shopperText.setFont(new Font("Segoe UI Black Italic", 13));

        this.shadow = new Ellipse(95, 20);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.9);
        this.shadow.getTransforms().add(new Rotate(20));
        this.shadow.setEffect(new GaussianBlur(40));

        this.triangleAct = new Polygon();
        triangleAct.getPoints().addAll(0.0, 0.0,
                30.0, 0.0,
                15.0, 25.0);
        this.triangleAct.setFill(Color.BLACK);

        if (this.instrument != null){
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }




    }
    public MusicianMaster(boolean isActive, String name, double money, boolean isMan) {
        this(null, isActive, name, money,isMan);
    }
    public MusicianMaster( Instrument instrument,boolean isActive, String name) {
        this(instrument, isActive, name, 0,true);
    }
    public MusicianMaster( boolean isActive, String name) {
        this(null, isActive, name, 0, true);
    }
    public MusicianMaster(boolean isActive, String name, double money){
        this(null, isActive, name, money, true);
    }
    public MusicianMaster(String name){
        this(null, true, name, 0,true);
    }

    @Override
    public void updateShopperChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.shopperImage.setX(x);
        this.shopperImage.setY(y);

        this.shopperText.setLayoutX(x-15);
        this.shopperText.setLayoutY(y -17);
        this.shopperText.setText(this.name + ", money: "+ this.money);

        this.shadow.setLayoutX(x -10);
        this.shadow.setLayoutY(y + 170);

        this.triangleAct.setLayoutX(x+30);
        this.triangleAct.setLayoutY(y-40);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (this.instrument != null) {
            this.instrument.update(x, y);
        }

    }

    @Override
    public Shopper education(boolean first, boolean second, double allPrice) {
        if (second && !first && this.money >= allPrice){
            if (this.isMan){
                return new OrchestraConductor(Instrument.getInstrument(this.instrument.getType()), true, this.name, this.money-allPrice);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Жінки не можуть бути дирижорами");
                alert.showAndWait();
            }
        } else if (first && !second && this.money >= allPrice)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ви уже пройшли початкову школу");
            alert.showAndWait();
        } else if (first && second && this.money >= allPrice){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ви уже пройшли початкову школу");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Недостатньо коштів");
            alert.showAndWait();
        }
        return this;
    }

    @Override
    public void playAnInstrument() {

        this.getShopperImage().setOpacity(0);
        ImageView masterPlaySprite;
        if (this.isMan) {
             masterPlaySprite = new ImageView(new Image("assets/masterPlaySprite.png"));
        } else {
             masterPlaySprite = new ImageView(new Image("assets/girlMasterPlaySprite.png"));
        }
        masterPlaySprite.setX(this.getShopperImage().getX());
        masterPlaySprite.setY(this.getShopperImage().getY());
        masterPlaySprite.setPreserveRatio(true);
        masterPlaySprite.setFitHeight(this.getShopperImage().getFitHeight());


        this.getShopperPicture().getChildren().add(4 ,masterPlaySprite);
        this.setStay(true);

        Animation workAnimation = new Sprite(masterPlaySprite , Duration.millis(2000),4,4,0,0,431,683);
        workAnimation.setCycleCount(10);//як довго буде грати

        String musicPath = "";
        switch (this.instrument.getType()){
            case "Guitar":
                musicPath = new String("src/assets/music/guitarBad.wav");
                break;
            case "Bayan":
                break;
            case "Drums":
                break;
            case "Piano":
                break;
            case "Trembita":
                musicPath = new String("src/assets/music/trembitaBad.mp3");
                break;
            case "Violin":
                break;
        }
        Media hit = new Media(Paths.get(musicPath).toUri().toString());
        AudioClip mediaPlayer = new AudioClip(hit.getSource());
        mediaPlayer.setVolume(Preferences.getVOLUME());
        workAnimation.play();
        mediaPlayer.play();



        workAnimation.setOnFinished(event -> {
            this.getShopperImage().setOpacity(1);
            this.getShopperPicture().getChildren().remove(masterPlaySprite);
            this.setStay(false);
            mediaPlayer.stop();
        });

    }


}

