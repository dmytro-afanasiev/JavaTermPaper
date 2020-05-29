package objects.firstMacro;

import javafx.scene.image.ImageView;

public abstract class Instrument implements Cloneable{
     protected double price;
     protected ImageView instrumentImage = null;
     protected String type = "Nothing";



    public ImageView getInstrumentImage() {
        return instrumentImage;
    }

    public String getType() {
        return this.type;
    }

    public double getPrice() {
        return price;
    }

    public abstract void update(double x, double y);



    public static Instrument getInstrument(String type){
        switch (type) {
            case "Guitar":
                return new Guitar();
            case "Drums":
                return new Drums();
            case "Bayan":
                return new Bayan();
            case "Piano":
                return new Piano();
            case "Trembita":
                return new Tremb();
            case "Violin":
                return new Violin();
            case "Nothing":
                return null;
        }
        return null;
    }
    @Override
    public String toString() {
        return this.getType();
    }

    @Override
    public Instrument clone() throws CloneNotSupportedException {
        return (Instrument)super.clone();
    }
}
