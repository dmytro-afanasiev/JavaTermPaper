package objects.thirdMacro;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import objects.micro.OrchestraConductor;
import objects.micro.Shopper;
import objects.secondMacro.Building;
import sample.Main;
import sample.MiniMap;

import java.util.ArrayList;

public class World {
    final private static int ROOT_WIDTH = 4800;
    final private static int ROOT_HEIGHT = 2700;
    private Pane root;
    private ArrayList<Shopper> shoppers;
    private ArrayList<Building> buildings;
    private MiniMap miniMap;


    public World(){
        this.root = new Pane();
        this.root.setMinWidth(World.ROOT_WIDTH);
        this.root.setMinHeight(World.ROOT_HEIGHT);
        root.setOnMousePressed(event -> {
            for (Shopper shopper : this.shoppers) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    shopper.mouseClick(event.getX(), event.getY());
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (shopper instanceof OrchestraConductor) {
                        ((OrchestraConductor) shopper).chooseAnInstrument(event.getX(), event.getY());
                    }
                }
            }
        });

        this.shoppers = new ArrayList<>(5);
        this.buildings = new ArrayList<>(5);
        this.miniMap = new MiniMap();
        this.root.getChildren().add(miniMap.getPane());
    }


    public void addNewShopper(Shopper shopper, boolean rand) {
        this.shoppers.add(shopper);
        if (rand) {
            shopper.setXChord(Main.random.nextInt((int) (Main.getScene().getWidth() + Main.getScrollX())));
            shopper.setYChord(Main.random.nextInt((int) (Main.getScene().getHeight() + Main.getScrollX())));
        }
        shopper.updateShopperChords();
        this.root.getChildren().add(Building.getNumberOfBuildings(),shopper.getShopperPicture());
        this.miniMap.addShopper(shopper);
    }

    public void deleteAShopper(Shopper shopper) {
        this.miniMap.deleteAShopper(shopper);
        this.root.getChildren().remove(shopper.getShopperPicture());
        this.shoppers.remove(shopper);
        Shopper.setNumberOfShoppers(Shopper.getNumberOfShoppers() - 1);
    }

    public void addNewBuilding(Building building) {
        this.buildings.add(building);
        building.setBuildingInChords();
        this.root.getChildren().add(0,building.getBuildingPicture());
        this.miniMap.addBuilding(building);
    }

    public void deleteABuilding(Building building) {
        this.miniMap.deleteABuilding(building);
        this.root.getChildren().remove(building.getBuildingPicture());
        this.buildings.remove(building);
        Building.setNumberOfBuildings(Building.getNumberOfBuildings() - 1);
    }


    public Pane getRoot() {
        return root;
    }

    public ArrayList<Shopper> getShoppers() {
        return shoppers;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public MiniMap getMiniMap() {
        return miniMap;
    }

    public static int getRootWidth() {
        return ROOT_WIDTH;
    }

    public static int getRootHeight() {
        return ROOT_HEIGHT;
    }
}