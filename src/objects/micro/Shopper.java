package objects.micro;


import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.firstMacro.Instrument;
import sample.Main;
import sample.Sprite;
import sample.windows.preferencesWindow.Preferences;

import java.nio.file.Paths;


public class Shopper implements Cloneable {

    protected double playVariable;
    protected String type;
    protected double xChord, yChord;
    protected byte startDirection;

    protected Ellipse shadow;

    protected boolean isActive;
    protected boolean stay;
    protected Polygon triangleAct;


    protected ImageView shopperImage;
    protected boolean isMan;
    protected Label shopperText;

    protected Instrument instrument = null;
    protected Group shopperPicture;


    protected double money;
    protected String name;
    protected static int numberOfShoppers = 0;



    public Shopper(Instrument instrument, boolean isActive, String name, double money, boolean isMan) {
        Shopper.numberOfShoppers++;
        this.type = "Shopper";
        this.playVariable = 0.1;
        this.name = name;
        this.money = money;
        this.isMan = isMan;
        this.startDirection = (byte) Main.random.nextInt(8);

        if (isMan) {
            this.shopperImage = new ImageView(new Image("assets/shopper.png"));
        } else this.shopperImage = new ImageView(new Image("assets/girlShopper.png"));

        this.isActive = isActive;
        this.stay = false;
        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30, 0, 0, Color.GRAY));

        this.instrument = instrument;

        this.shopperText = new Label(this.name + ", money: " + this.money);
        this.shopperText.setFont(new Font("Segoe UI Black Italic", 13));

        this.shadow = new Ellipse(75, 20);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(15));
        this.shadow.setEffect(new GaussianBlur(40));

        this.triangleAct = new Polygon();
        triangleAct.getPoints().addAll(0.0, 0.0,
                30.0, 0.0,
                15.0, 25.0);
        this.triangleAct.setFill(Color.BLACK);


        if (this.instrument != null) {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }
    }

    public Shopper(boolean isActive, String name, double money, boolean isMan) {
        this(null, isActive, name, money, isMan);
    }

    public Shopper(Instrument instrument, boolean isActive, String name) {
        this(instrument, isActive, name, 0, true);
    }

    public Shopper(boolean isActive, String name) {
        this(null, isActive, name, 0, true);
    }

    public Shopper(boolean isActive, String name, double money) {
        this(null, isActive, name, money, true);
    }

    public Shopper(String name) {
        this(null, true, name, 0, true);
    }

    public Shopper(){
        this(null, false,null,0,true);
    }


    public void updateShopperChords() {
        this.shopperImage.setX(this.xChord);
        this.shopperImage.setY(this.yChord);

        this.shopperText.setLayoutX(this.xChord - 15);
        this.shopperText.setLayoutY(this.yChord - 25);
        this.shopperText.setText(this.name + ", money: " + this.money);

        this.shadow.setLayoutX(this.xChord - 10);
        this.shadow.setLayoutY(this.yChord + 170);

        this.triangleAct.setLayoutX(this.xChord + 30);
        this.triangleAct.setLayoutY(this.yChord - 40);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (this.instrument != null) {
            this.instrument.update(this.xChord, this.yChord);
        }
    }

    public void freeRun() {
        if (!this.isActive() && !this.stay) {
            if (Main.getCity().isInteractWithPlayerMode()) {
                switch (this.startDirection) {
                    case 0:
                        this.yChord -= Preferences.getSPEED() / 5;
                        break;
                    case 1:
                        this.yChord -= Preferences.getSPEED() / 5;
                        this.xChord += Preferences.getSPEED() / 5;
                        break;
                    case 2:
                        this.xChord += Preferences.getSPEED() / 5;
                        break;
                    case 3:
                        this.xChord += Preferences.getSPEED() / 5;
                        this.yChord += Preferences.getSPEED() / 5;
                        break;
                    case 4:
                        this.yChord += Preferences.getSPEED() / 5;
                        break;
                    case 5:
                        this.yChord += Preferences.getSPEED() / 5;
                        this.xChord -= Preferences.getSPEED() / 5;
                        break;
                    case 6:
                        this.xChord -= Preferences.getSPEED() / 5;
                        break;
                    case 7:
                        this.xChord -= Preferences.getSPEED() / 5;
                        this.yChord -= Preferences.getSPEED() / 5;
                        break;
                }

                if (this.shopperImage.getX() + 100 >= Main.getScene().getWidth() + Main.getScrollX()) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 5);
                } else if (this.shopperImage.getX() <= 0 + Main.getScrollX()) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 1);
                } else if (this.shopperImage.getY() <= 0 + Main.getScrollY()) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 3);
                } else if (this.shopperImage.getY() + 170 >= Main.getScene().getHeight() + Main.getScrollY()) {
                    this.startDirection = (byte) Main.random.nextInt(2);
                }
            } else {
                switch (this.startDirection) {
                    case 0:
                        this.yChord -= Preferences.getSPEED() / 2;
                        break;
                    case 1:
                        this.yChord -= Preferences.getSPEED() / 2;
                        this.xChord += Preferences.getSPEED() / 2;
                        break;
                    case 2:
                        this.xChord += Preferences.getSPEED() / 2;
                        break;
                    case 3:
                        this.xChord += Preferences.getSPEED() / 2;
                        this.yChord += Preferences.getSPEED() / 2;
                        break;
                    case 4:
                        this.yChord += Preferences.getSPEED() / 2;
                        break;
                    case 5:
                        this.yChord += Preferences.getSPEED() / 2;
                        this.xChord -= Preferences.getSPEED() / 2;
                        break;
                    case 6:
                        this.xChord -= Preferences.getSPEED() / 2;
                        break;
                    case 7:
                        this.xChord -= Preferences.getSPEED() / 2;
                        this.yChord -= Preferences.getSPEED() / 2;
                        break;
                }

                if (this.shopperImage.getX() + 100 >= Main.getCity().getRoot().getWidth()) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 5);
                } else if (this.shopperImage.getX() <= 0) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 1);
                } else if (this.shopperImage.getY() <= 0) {
                    this.startDirection = (byte) (Main.random.nextInt(3) + 3);
                } else if (this.shopperImage.getY() + 170 >= Main.getCity().getRoot().getHeight()) {
                    this.startDirection = (byte) Main.random.nextInt(2);
                }
            }
        }
    }

    public void mouseClick(double x, double y) {
        Point2D point2D = new Point2D(x, y);
        if (this.shopperImage.getBoundsInParent().contains(point2D) && !this.stay) {
            this.isActive = !this.isActive;
        }
    }

    public void left(double boost) {
        if (isActive && xChord >= 0) {
            xChord -= Preferences.getSPEED() * boost;
        }
    }

    public void right(double boost) {
        if (isActive && xChord <= Main.getCity().getRoot().getWidth() - 100) {
            xChord += Preferences.getSPEED() * boost;
        }
    }

    public void up(double boost) {
        if (isActive && yChord >= 0) {
            yChord -= Preferences.getSPEED() * boost;
        }
    }

    public void down(double boost) {
        if (isActive && yChord <= Main.getCity().getRoot().getHeight() - 200) {
            yChord += Preferences.getSPEED() * boost;
        }
    }

    public void setStay(boolean stay) {
        this.isActive = false;
        this.stay = stay;
    }

    public void buyAnInstrument(String typeOfInstrument) {
        if (this.money >= Instrument.getInstrument(typeOfInstrument).getPrice()) {
            this.instrument = Instrument.getInstrument(typeOfInstrument);
            this.money -= this.instrument.getPrice();
            this.shopperPicture.getChildren().add(this.instrument.getInstrumentImage());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Увага!");
            alert.setHeaderText("Приходьте пізніше)");
            alert.setContentText("Недостатньо коштів для покупки даного інструмента, прямуйте заробляти");
            alert.showAndWait();
        }
    }
    public void sellAnInstrument(){
        if (this.getInstrument() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Приходьте пізніше");
            alert.setContentText("Будучи початківцев, продавати ваш єдиний інструмент дуже ризиковано.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("У вас немає що продавати");
            alert.showAndWait();
        }
    }

    public Shopper education(boolean first, boolean second, double allPrice) {
        if (first && second && this.money >= allPrice) {// провырку на жынку не забути
            if (this.isMan) {
                return new OrchestraConductor(Instrument.getInstrument(this.instrument.getType()), true, this.name, this.money-allPrice);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Жінки не можуть бути дирижорами");
                alert.showAndWait();
            }
        } else if (first && !second && this.money >= allPrice) {
            return new MusicianMaster(Instrument.getInstrument(this.instrument.getType()), true, this.name,this.money- allPrice,this.isMan);
        } else if (second && !first && this.money>= allPrice) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Спочатку потрібно пройти початкове навчання, а вже потім учитися на дирижора, або ж можна на обох одразу");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Недостаньо коштів");
            alert.showAndWait();
        }
        return this;
    }

    public void playAnInstrument(){

        this.shopperImage.setOpacity(0);

        ImageView shopperPlaySprite;
        if (this.isMan) {
            shopperPlaySprite = new ImageView(new Image("assets/shopperPlaySprite.png"));
        } else {
            shopperPlaySprite = new ImageView(new Image("assets/girlShopperPlaySprite.png"));

        }
        shopperPlaySprite.setX(this.shopperImage.getX());
        shopperPlaySprite.setY(this.shopperImage.getY());
        shopperPlaySprite.setPreserveRatio(true);
        shopperPlaySprite.setFitHeight(this.shopperImage.getFitHeight());


        this.getShopperPicture().getChildren().add(4 ,shopperPlaySprite);
        this.setStay(true);

        Animation playAnimation = new Sprite(shopperPlaySprite , Duration.millis(2000),4,4,431,683);
        playAnimation.setCycleCount(10);//як довго буде грати

        String musicPath = "";
        switch (this.instrument.getType()){
            case "Guitar":
                musicPath = new String("src/assets/music/guitarGood.mp3");
                break;
            case "Bayan":
                break;
            case "Drums":
                break;
            case "Piano":
                musicPath = new String("src/assets/music/terorist.mp3");
                break;
            case "Trembita":
                musicPath = new String("src/assets/music/trembitaBad.mp3");
                break;
            case "Violin":
                break;
        }

        AudioClip mediaPlayer = null;
        try {
            Media hit = new Media(Paths.get(musicPath).toUri().toString());
            mediaPlayer = new AudioClip(hit.getSource());
            mediaPlayer.setVolume(Preferences.getVOLUME());
            mediaPlayer.play();
        } catch (MediaException m){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Помилка загрузки аудіо!!");
            alert.setContentText(m.toString());
            alert.show();
        }
        AudioClip finalMediaPlayer = mediaPlayer;

        playAnimation.play();

        playAnimation.setOnFinished(event -> {
            this.shopperImage.setOpacity(1);
            this.getShopperPicture().getChildren().remove(shopperPlaySprite);
            this.setStay(false);
            if (finalMediaPlayer != null) {
                finalMediaPlayer.stop();
            }
        });

    }

    public double getPlayVariable() {
        return playVariable;
    }

    public String getType() {
        return type;
    }

    public double getXChord() {
        return xChord;
    }

    public void setXChord(double xChord) {
        this.xChord = xChord;
    }

    public double getYChord() {
        return yChord;
    }

    public void setYChord(double yChord) {
        this.yChord = yChord;
    }

    public byte getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(byte startDirection) {
        this.startDirection = startDirection;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ImageView getShopperImage() {
        return shopperImage;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
        if (man)
            this.shopperImage.setImage(new Image("assets/shopper.png"));
        else this.shopperImage.setImage(new Image("assets/girlShopper.png"));
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
        this.shopperPicture.getChildren().add(this.instrument.getInstrumentImage());
    }

    public Group getShopperPicture() {
        return shopperPicture;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getNumberOfShoppers() {
        return numberOfShoppers;
    }

    public static void setNumberOfShoppers(int numberOfShoppers) {
        Shopper.numberOfShoppers = numberOfShoppers;
    }
    @Override
    public Shopper clone() throws CloneNotSupportedException {
        Shopper temp = (Shopper) super.clone();
        temp.instrument = instrument.clone();
        return temp;
    }
}







