package objects.micro;


import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import objects.firstMacro.Instrument;
import sample.Main;


public class Shopper  {


    protected double speed;
    protected double xChord, yChord;
    protected byte startDirection;

    protected Ellipse shadow;

    protected boolean isActive;
    protected Polygon triangleAct;


    protected ImageView shopperImage;
    protected boolean isMan;
    protected Label shopperText;

    protected Instrument instrument= null;
    protected Group shopperPicture;

    protected double money;
    protected String name;
    protected static int numberOfShoppers = 0;



    public Shopper(Instrument instrument, boolean isActive, String name, double money , boolean isMan) {
        Shopper.numberOfShoppers++;
        this.name = name;
        this.money=money;
        this.speed = 10;
        this.startDirection = (byte)Main.random.nextInt(8);

        if (isMan){
            this.shopperImage  = new ImageView(new Image("assets/shopper.png"));
        } else this.shopperImage  = new ImageView(new Image("assets/girlShopper.png"));

        this.isActive = isActive;
        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30,0,0,Color.GRAY));

        this.instrument = instrument;

        this.shopperText = new Label(this.name + ", money: "+ this.money);
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

        if (this.instrument != null){
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }
    }

    public Shopper(boolean isActive, String name, double money, boolean isMan) {
        this(null, isActive, name, money,isMan);
    }
    public Shopper( Instrument instrument,boolean isActive, String name) {
        this(instrument, isActive, name, 0,true);
    }
    public Shopper( boolean isActive, String name) {
        this(null, isActive, name, 0, true);
    }
    public Shopper(boolean isActive, String name, double money){
        this(null, isActive, name, money, true);
    }
    public Shopper(String name){
        this(null, true, name, 0,true);
    }


    public void updateShopperChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.shopperImage.setX(x);
        this.shopperImage.setY(y);

        this.shopperText.setLayoutX(x-15);
        this.shopperText.setLayoutY(y - 10);
        this.shopperText.setText(this.name + ", money: "+ this.money);

        this.shadow.setLayoutX(x -10);
        this.shadow.setLayoutY(y + 170);

        this.triangleAct.setLayoutX(x+30);
        this.triangleAct.setLayoutY(y-40);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (this.instrument != null) {
            this.instrument.update(x, y);
        }

    }

    public void freeRun(){
        if (!this.isActive()) {
            switch (this.startDirection) {
                case 0:
                    this.yChord -= speed/5;
                    break;
                case 1:
                    this.yChord -= speed/5;
                    this.xChord += speed/5;
                    break;
                case 2:
                    this.xChord += speed/5;
                    break;
                case 3:
                    this.xChord += speed/5;
                    this.yChord += speed/5;
                    break;
                case 4:
                    this.yChord += speed/5;
                    break;
                case 5:
                    this.yChord += speed/5;
                    this.xChord -= speed/5;
                    break;
                case 6:
                    this.xChord -= speed/5;
                    break;
                case 7:
                    this.xChord -= speed/5;
                    this.yChord -= speed/5;
                    break;
            }
        }
        if (this.shopperImage.getX()+100>= Main.getScene().getWidth()+Main.getScrollX()){
                this.startDirection = (byte)(Main.random.nextInt(3)+5);
        }
        else if (this.shopperImage.getX()<= 0+Main.getScrollX()){
            this.startDirection = (byte)(Main.random.nextInt(3)+1);
        }
         else if (this.shopperImage.getY()<= 0+Main.getScrollY()){
            this.startDirection = (byte)(Main.random.nextInt(3)+3);
        }
        else if (this.shopperImage.getY()+ 170>= Main.getScene().getHeight()+Main.getScrollY()){
            this.startDirection = (byte)Main.random.nextInt(2);
        }

    }

    public Group getShopperPicture() {
        return this.shopperPicture;
    }

    public double getXChord() {
        return xChord;
    }

    public double getYChord() {
        return yChord;
    }


    public void setXYCords(double xChord, double yChord) {
        this.xChord = xChord;
        this.yChord = yChord;
    }

    public void mouseClick(double x, double y) {
        Point2D point2D = new Point2D(x, y);
        if (this.shopperImage.getBoundsInParent().contains(point2D)) {
            this.isActive = !this.isActive;
        }
    }

    public void left(double boost) {
        if (isActive && xChord>= 0) {
            xChord -= speed* boost;
        }
    }

    public void right(double boost) {
        if (isActive && xChord<= Main.getRoot().getWidth()- 100) {
            xChord += speed* boost;
        }
    }

    public void up(double boost) {
        if (isActive && yChord>=0) {
            yChord -= speed*boost;
        }
    }

    public void down(double boost) {
        if (isActive && yChord <= Main.getRoot().getHeight() -200) {
            yChord += speed* boost;
        }
    }



    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static int getNumberOfShoppers() {
        return numberOfShoppers;
    }

    public static void setNumberOfShoppers(int numberOfShoppers) {
        Shopper.numberOfShoppers = numberOfShoppers;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}







