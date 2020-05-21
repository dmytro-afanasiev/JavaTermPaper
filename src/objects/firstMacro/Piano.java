package objects.firstMacro;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piano extends Instrument{
    public Piano(){
        this.instrumentImage= new ImageView(new Image("assets/piano.png"));
        this.instrumentImage.setPreserveRatio(true);
        this.instrumentImage.setFitHeight(170);
        this.type = "Piano";
        this.prise = 600;
    }
    @Override
    public void update(double x, double y) {
        this.instrumentImage.setX(x+50);
        this.instrumentImage.setY(y+60);
    }
}
