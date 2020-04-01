package objects.firstMacro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Guitar extends Instrument {

    public Guitar(){
        this.instrumentImage= new ImageView(new Image("assets/guitar.png"));
        this.instrumentImage.setPreserveRatio(true);
        this.instrumentImage.setFitHeight(80);
        this.type = "guitar";
    }
    @Override
    public void update(double x, double y) {
        this.instrumentImage.setX(x+10);
        this.instrumentImage.setY(y+70);
    }
}
