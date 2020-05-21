package objects.firstMacro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bayan extends Instrument {
    public Bayan(){
        this.instrumentImage= new ImageView(new Image("assets/bayan.png"));
        this.instrumentImage.setPreserveRatio(true);
        this.instrumentImage.setFitHeight(80);
        this.type = "Bayan";
        this.prise = 400;
    }
    @Override
    public void update(double x, double y) {
        this.instrumentImage.setX(x+10);
        this.instrumentImage.setY(y+60);
    }
}
