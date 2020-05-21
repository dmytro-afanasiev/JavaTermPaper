package objects.firstMacro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Radio extends Instrument {
    public Radio(){
        this.instrumentImage= new ImageView(new Image("assets/radio.png"));
        this.instrumentImage.setPreserveRatio(true);
        this.instrumentImage.setFitHeight(50);
        this.type = "radio_!Fun!";
        this.prise = 10000000;
    }
    @Override
    public void update(double x, double y) {
        this.instrumentImage.setX(x+40);
        this.instrumentImage.setY(y+40);
    }
}
