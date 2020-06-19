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

    private Map<String,Instrument> instruments;

    public OrchestraConductor(Instrument instrument, boolean isActive, String name, double money){
        super(isActive,  name,  money ,  true);
        this.type = "Orchestra";
        this.playVariable = 2;

        this.shopperImage  = new ImageView(new Image("assets/orchestra.png"));


        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30,0,0, Color.GRAY));


        this.instruments = new HashMap<>();

        if (instrument != null) {
            this.instruments.put(instrument.getType(),instrument);
            this.instrument = instrument;
        } else this.instrument = null;

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

        if (this.instrument != null){
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }
    }

    public OrchestraConductor(boolean isActive, String name) {
        this(null,  isActive, name, 0);
    }
    public OrchestraConductor(boolean isActive, String name, double money){
        this( null, isActive, name, money);
    }
    public OrchestraConductor(String name){
        this( null, true, name, 0);
    }
    public OrchestraConductor(){
        this(null, false,null,0);
    }

    @Override
    public void setShopperInChords() {
        this.shopperImage.setX(this.xChord-40);
        this.shopperImage.setY(this.yChord);

        this.shopperText.setLayoutX(this.xChord-27);
        this.shopperText.setLayoutY(this.yChord -40);
        this.shopperText.setText(this.name + ", money: "+ this.money + "\nNumber of instruments: " +this.instruments.size());

        this.shadow.setLayoutX(this.xChord -10);
        this.shadow.setLayoutY(this.yChord + 170);

        this.triangleAct.setLayoutX(this.xChord+20);
        this.triangleAct.setLayoutY(this.yChord-65);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (this.instrument != null) {
            this.instrument.update(this.xChord, this.yChord);
        }

    }

    public void chooseAnInstrument(double x, double y){
        if (this.shopperImage.getBoundsInParent().contains(new Point2D(x, y)) && !this.stay) {
            try {
                new ChooseAnInstrumentWindow().display( this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeAnInstrument(String currentInstrument){

        if (currentInstrument.equals("Nothing")){
            if (this.instrument != null)
                this.shopperPicture.getChildren().remove(this.instrument.getInstrumentImage());
            this.instrument= null;
        } else {
            if (this.instrument != null)
                this.shopperPicture.getChildren().remove(this.instrument.getInstrumentImage());
            this.instrument = instruments.get(currentInstrument);
            this.shopperPicture.getChildren().add(instrument.getInstrumentImage());
        }
    }


    @Override
    public void buyAnInstrument(String typeOfInstrument) {
        if (this.money >= Instrument.getInstrument(typeOfInstrument).getPrice() && !this.instruments.containsKey(typeOfInstrument)){
            this.instruments.put(typeOfInstrument, Instrument.getInstrument(typeOfInstrument));
            this.money -= Instrument.getInstrument(typeOfInstrument).getPrice();
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
                this.money+= this.instrument.getPrice()* 0.5;
                this.instruments.remove(this.instrument.getType());
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
        this.shopperImage.setOpacity(0);

        ImageView orchestraPlaySprite = new ImageView(new Image("assets/orchestraPlaySprite.png"));
        orchestraPlaySprite.setX(this.shopperImage.getX());
        orchestraPlaySprite.setY(this.shopperImage.getY());
        orchestraPlaySprite.setPreserveRatio(true);
        orchestraPlaySprite.setFitHeight(this.shopperImage.getFitHeight());


        this.getShopperPicture().getChildren().add(4 ,orchestraPlaySprite);
        this.setStay(true);

        Animation playAnimation = new Sprite(orchestraPlaySprite , Duration.millis(2000),4,4,561,683);
        playAnimation.setCycleCount(10);//як довго буде грати

        String musicPath = "";
        switch (this.instrument.getType()){
            case "Guitar":
                musicPath = "src/assets/music/guitarBest.mp3";
                break;
            case "Bayan":
                musicPath = "src/assets/music/bayanBest.mp3";
                break;
            case "Drums":
                musicPath = "src/assets/music/drumsBest.mp3";
                break;
            case "Piano":
                musicPath = "src/assets/music/experience.mp3";
                break;
            case "Trembita":
                musicPath = "src/assets/music/trembitaBest.mp3";
                break;
            case "Violin":
                musicPath = "src/assets/music/violinBest.mp3";
                break;
        }
        Media hit = new Media(Paths.get(musicPath).toUri().toString());
        AudioClip mediaPlayer = new AudioClip(hit.getSource());
        mediaPlayer.setVolume(Preferences.getVOLUME());
        playAnimation.play();
        mediaPlayer.play();



        playAnimation.setOnFinished(event -> {
            this.shopperImage.setOpacity(1);
            this.getShopperPicture().getChildren().remove(orchestraPlaySprite);
            this.setStay(false);
            mediaPlayer.stop();
        });
    }

    public Map<String, Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Map<String, Instrument> instruments) {
        this.instruments = instruments;
    }


}
