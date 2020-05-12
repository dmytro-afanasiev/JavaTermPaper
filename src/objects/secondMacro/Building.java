package objects.secondMacro;


import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;

public abstract class Building {
    protected double xChord;
    protected double yChord;

    protected ImageView buildingImage;
    protected Label buildingText;
    protected Ellipse shadow;

    protected Group buildingPicture;
    protected static int numberOfBuildings = 0;

    protected String type = "Nothing";

    public Group getBuildingPicture() {
        return this.buildingPicture;
    }

    public void setXYCords(double xChord, double yChord) {
        this.xChord = xChord;
        this.yChord = yChord;
    }

    public double getXCord() {
        return xChord;
    }

    public double getYChord() {
        return yChord;
    }

    public static int getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public static void setNumberOfBuildings(int numberOfBuildings) {
        Building.numberOfBuildings = numberOfBuildings;
    }

    public String getType() {
        return this.type;
    }



    public abstract void setBuildingInChords();
}
