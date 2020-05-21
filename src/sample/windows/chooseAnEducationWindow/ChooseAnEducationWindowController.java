package sample.windows.chooseAnEducationWindow;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import objects.micro.Shopper;
import sample.Main;

public class ChooseAnEducationWindowController {

    private boolean firstEd = false;
    private boolean secondEd = false;
    final public static double FIRST_PRICE = 1000;
    final public static double SECOND_PRICE = 3000;
    private double allPr = 0;

    @FXML
    private Button chooseButton;

    @FXML
    private CheckBox firstEducation;

    @FXML
    private CheckBox secondEducation;

    @FXML
    private Label allPrice;

    @FXML
    void initialize() {
        allPrice.setText(Double.toString(0));
        firstEducation.setOnAction(event -> {
            firstEd = !firstEd;
            if (firstEd && secondEd)
                allPr = FIRST_PRICE+SECOND_PRICE;
            else if (firstEd)
                allPr = FIRST_PRICE;
            else if (secondEd)
                allPr = SECOND_PRICE;
            else allPr = 0;
            allPrice.setText(Double.toString(allPr));
        });
        secondEducation.setOnAction(event -> {
            secondEd = !secondEd;
            if (firstEd && secondEd)
                allPr = FIRST_PRICE+SECOND_PRICE;
            else if (firstEd)
                allPr = FIRST_PRICE;
            else if (secondEd)
                allPr = SECOND_PRICE;
            else allPr = 0;
            allPrice.setText(Double.toString(allPr));
        });
        chooseButton.setOnAction(event -> {
            if (firstEd || secondEd) {
                Shopper old = ChooseAnEducationWindow.getShopper();
                Shopper temp = old.education(firstEd, secondEd, allPr);
                temp.setXYCords(old.getXChord()+20, old.getYChord()+20);
                temp.updateShopperChords();

                Main.getRoot().getChildren().remove(old.getShopperPicture());

                Main.shoppers.remove(old);
                old = null;
                Shopper.setNumberOfShoppers(Shopper.getNumberOfShoppers() - 1);

                Main.shoppers.add(temp);
                Main.getRoot().getChildren().add(temp.getShopperPicture());
                ChooseAnEducationWindow.getWindow().close();
            }
        });
    }

}