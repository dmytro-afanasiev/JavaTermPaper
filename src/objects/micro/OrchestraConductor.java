package objects.micro;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import objects.firstMacro.Instrument;
import sample.windows.chooseAnInstrumentWindow.ChooseAnInstrumentWindow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrchestraConductor extends  MusicianMaster {

    private Map<String, Instrument> instruments;
    private String currentInstrument;
    public OrchestraConductor(Instrument instrument, boolean isActive, String name, double money){
        super(isActive,  name,  money ,  true);

        this.speed = 4;


        this.shopperImage  = new ImageView(new Image("assets/orchestra.png"));


        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30,0,0, Color.GRAY));


        this.instruments = new HashMap<>();

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

    public Map<String, Instrument> getInstruments() {
        return instruments;
    }

    public String getCurrentInstrument() {
        return currentInstrument;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrchestraConductor that = (OrchestraConductor) o;
        return Objects.equals(instruments, that.instruments) &&
                Objects.equals(currentInstrument, that.currentInstrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instruments, currentInstrument);
    }
}
