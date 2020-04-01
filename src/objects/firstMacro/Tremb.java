package objects.firstMacro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tremb extends Instrument {
    public Tremb(){
        this.instrumentImage= new ImageView(new Image("assets/tremb.png"));
        this.instrumentImage.setPreserveRatio(true);
        this.instrumentImage.setFitHeight(80);
        this.type = "guitar";
    }
    @Override
    public void update(double x, double y) {
        this.instrumentImage.setX(x+35);
        this.instrumentImage.setY(y-20);
    }
}
