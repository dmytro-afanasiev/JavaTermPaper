package objects.firstMacro;



import javafx.scene.image.ImageView;

public abstract class Instrument implements Cloneable{
     protected String name;
     protected double prise;
     protected ImageView instrumentImage = null;
     protected String type = "-abstract-";



    public ImageView getInstrumentImage() {
        return instrumentImage;
    }

    public String getType() {
        return type;
    }

    public abstract void update(double x, double y);


    @Override
    public Instrument clone() throws CloneNotSupportedException {
        return (Instrument) super.clone();
    }

}
