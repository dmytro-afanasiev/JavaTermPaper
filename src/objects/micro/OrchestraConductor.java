package objects.micro;

import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import sample.windows.chooseAnInstrumentWindow.ChooseAnInstrumentWindow;
import sample.windows.preferencesWindow.Preferences;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class OrchestraConductor extends  MusicianMaster {

    private Set<Instrument> instruments;

    public OrchestraConductor(Instrument instrument, boolean isActive, String name, double money){
        super(isActive,  name,  money ,  true);
        this.type = "Orchestra";


        this.shopperImage  = new ImageView(new Image("assets/orchestra.png"));


        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30,0,0, Color.GRAY));


        this.instruments = new HashSet<>();

        if (instrument != null) {
            this.currentInstrument = instrument.getType();
            this.instruments.put(currentInstrument,instrument);
        } else currentInstrument = "Nothing";
        this.shopperText = new Label(this.name + ", money: "+ this.money + "\nNumber of instruments: " +this.instruments.size());
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

        this.instrument  =  this.instruments.get(currentInstrument);
        if (currentInstrument != "Nothing"){
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }




    }

    public OrchestraConductor(Instrument instrument, String currentInstrument, boolean isActive, String name, double money) {
        this(instrument, isActive, name, money);
    }
    public OrchestraConductor(boolean isActive, String name) {
        this(null, "Nothing", isActive, name, 0);
    }
    public OrchestraConductor(boolean isActive, String name, double money){
        this( null, "Nothing",isActive, name, money);
    }
    public OrchestraConductor(String name){
        this( null,"Nothing", true, name, 0);
    }
    @Override
    public void updateShopperChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.shopperImage.setX(x-40);
        this.shopperImage.setY(y);

        this.shopperText.setLayoutX(x-27);
        this.shopperText.setLayoutY(y -40);
        this.shopperText.setText(this.name + ", money: "+ this.money + "\nNumber of instruments: " +this.instruments.size());

        this.shadow.setLayoutX(x -10);
        this.shadow.setLayoutY(y + 170);

        this.triangleAct.setLayoutX(x+20);
        this.triangleAct.setLayoutY(y-65);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (!this.instruments.isEmpty()) {
            this.instruments.get(currentInstrument).update(x, y);
        }

    }

    public void chooseAnInstrument(double x, double y){
        Point2D point2D = new Point2D(x, y);
        if (this.shopperImage.getBoundsInParent().contains(point2D)) {
            try {

                new ChooseAnInstrumentWindow().display("Виберіть", this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeAnInstrument(String currentInstrument){

        if (this.instruments.containsKey(currentInstrument)){
            this.shopperPicture.getChildren().remove(this.instruments.get(this.currentInstrument).getInstrumentImage());
            this.currentInstrument= currentInstrument;
            this.instrument = this.instruments.get(this.currentInstrument);
            this.shopperPicture.getChildren().add(instrument.getInstrumentImage());

        } else if (currentInstrument.equals("Nothing")){
            this.shopperPicture.getChildren().remove(this.instruments.get(this.currentInstrument).getInstrumentImage());
            this.instrument = null;
        }
    }


    @Override
    public void buyAnInstrument(String typeOfInstrument) {
        if (this.money >= Instrument.getInstrument(typeOfInstrument).getPrise() && !this.instruments.containsKey(typeOfInstrument)){
            this.instruments.put(typeOfInstrument, Instrument.getInstrument(typeOfInstrument));
            this.money -= Instrument.getInstrument(typeOfInstrument).getPrise();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Увага");
            alert.setHeaderText("Повертайтеся пізніше");
            alert.setContentText("Недостатньо коштів для покупки даного інструмента, або ви вже його маєте");
            alert.showAndWait();
        }
    }

    @Override
    public void sellAnInstrument() {
        if (this.instrument != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Ви дійсно хочете продати поточний інструмент?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                this.shopperPicture.getChildren().remove(this.instrument.getInstrumentImage());
                this.money+= this.instrument.getPrise()* 0.5;
                this.currentInstrument = "Nothing";
                this.instruments.remove(instrument);
                this.instrument = null;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Візьміть у руки інструмент, який хочете продати");
            alert.showAndWait();
        }
    }

    @Override
    public void playAnInstrument() {
        this.getShopperImage().setOpacity(0);

        ImageView orchestraPlaySprite = new ImageView(new Image("assets/orchestraPlaySprite.png"));
        orchestraPlaySprite.setX(this.getShopperImage().getX());
        orchestraPlaySprite.setY(this.getShopperImage().getY());
        orchestraPlaySprite.setPreserveRatio(true);
        orchestraPlaySprite.setFitHeight(this.getShopperImage().getFitHeight());


        this.getShopperPicture().getChildren().add(4 ,orchestraPlaySprite);
        this.setStay(true);

        Animation workAnimation = new Sprite(orchestraPlaySprite , Duration.millis(2000),4,4,0,0,561,683);
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
            this.getShopperPicture().getChildren().remove(orchestraPlaySprite);
            this.setStay(false);
            mediaPlayer.stop();
        });
    }

    public Map<String, Instrument> getInstruments() {
        return instruments;
    }

    public String getCurrentInstrument() {
        return currentInstrument;
    }

}
