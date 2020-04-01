package objects.firstMacro;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Instrument implements Cloneable{
     protected String name;
     protected double prise;
     protected ImageView instrumentImage = null;
     protected String type = "-abstract-";

    @Override
    public Instrument clone() throws CloneNotSupportedException {
        return (Instrument) super.clone();
    }

    public ImageView getInstrumentImage() {
        return instrumentImage;
    }

    public String getType() {
        return type;
    }

    public abstract void update(double x, double y);


}
