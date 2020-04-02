package objects.micro;


import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import objects.firstMacro.Instrument;


import java.util.Comparator;

public class Shopper implements Cloneable /*, Comparable<Shopper>*/ {

    private double xCord, yCord;
    private Ellipse shadow;

    private boolean isActive;
    private Polygon triangleAct;

    private ImageView shopperImage;
    private boolean isMan;
    private Label shopperText;

    private Instrument instrument= null;
    private Group shopperPicture;

    private double money;
    private String name;
    private static int numberOfShoppers = 0;



    public Shopper(Instrument instrument, boolean isActive, String name, double money ,boolean isMan) {
        Shopper.numberOfShoppers++;
        this.name = name;
        this.money=money;

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
        this.triangleAct.setFill(Color.GREEN);

        if (this.instrument != null){
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }
    }
    public Shopper(boolean isActive, String name, double money, boolean isMan) {
        this(null, isActive, "Name", 0,isMan);
    }
    public Shopper( Instrument instrument,boolean isActive) {
        this(instrument, isActive, "Name", 0,true);
    }
    public Shopper( boolean isActive) {
        this(null, isActive, "Name", 0, true);
    }
    public Shopper(boolean isActive, String name, double money){
        this(null, isActive, name, money, true);
    }



    public void setShopperInCoords() {
        double x = xCord;
        double y = yCord;
        this.shopperImage.setX(x);
        this.shopperImage.setY(y);

        this.shopperText.setLayoutX(x-15);
        this.shopperText.setLayoutY(y - 10);

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

    public Group getShopperPicture() {

        return this.shopperPicture;
    }
    public double getXCord() {
        return xCord;
    }

    public double getYCord() {
        return yCord;
    }


    public void setXYCords(double xCord, double yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }


    public boolean isActive() {
        return this.isActive;
    }


    public void mouseClick(double x, double y) {
        Point2D point2D = new Point2D(x, y);
        if (this.shopperImage.getBoundsInParent().contains(point2D)) {
            this.isActive = !this.isActive;

        }
    }


    public void left() {
        if (isActive) {
            xCord -= 5;
        }
    }

    public void right() {
        if (isActive) {
            xCord += 5;
        }
    }

    public void up() {
        if (isActive) {
            yCord -= 5;
        }
    }

    public void down() {
        if (isActive) {
            yCord += 5;
        }
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

    /* private ArrayList<String> skills;



    public ArrayList<String> getSkills() {
        for (String s : skills) {
            System.out.println(s);
        }
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }


    public Shopper(double money, String name, ArrayList<String> skills) {
        System.out.println("_________________________________________");
        System.out.println("Був викликаний конструктор класу Shopper.");
        this.money = money;
        this.name = name;

        this.skills = skills;
        System.out.println("Був створений об'єкт " + this.name + " класу Shopper.");
        System.out.println("_____________________________________________________");
        numberOfShoppers++;

    }


    public Shopper() {

        this(0, "Name is not specified", new ArrayList<String>());
    }


    public void education(String skill) {
        switch (skill) {
            case "Guitar":
                if (this.money >= 30) {
                    System.out.println("Ви навчилися грати на гітарі.");
                    this.skills.add(skill);
                    this.money -= 30;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на гітарі.");
                break;
            case "Piano":
                if (this.money >= 60) {
                    System.out.println("Ви навчилися грати на піаніно.");
                    this.skills.add(skill);
                    this.money -= 60;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на піаніно.");
                break;
            case "Bayan":
                if (this.money >= 90) {
                    System.out.println("Ви навчилися грати на баяні.");
                    this.skills.add(skill);
                    this.money -= 90;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на баяні.");
                break;
            case "Violin":
                if (this.money >= 120) {
                    System.out.println("Ви навчилися грати на скрипці.");
                    this.skills.add(skill);
                    this.money -= 120;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на скрипці.");
                break;
            default:
                System.out.println("На даний момент на такому інструменті неможливо навчитися грати.\nВибирайте із даних: \"Guitar\", \"Piano\", \"Bayan\", \"Violin\"");
                break;


        }
    }*/

   /* public void earnMoney() {
        if (this.skills.contains("Guitar") && (guitar!=null)) {
            Date d = new Date();
            Random r = new Random(d.getTime());
            int kesh = r.nextInt(50);
            String s = Integer.toString(kesh);
            System.out.println("Ви грали цілий день у метро і заробили " + s + " грошей.");
            this.money += kesh;
        } else if (!this.skills.contains("Guitar")) System.out.println("Ви не вмієте грати на гітарі");
        else if (guitar==null) System.out.println("Спочатку придбайте гітару");

    }
    public void buyGuitar(Guitar guitar)
    {
        if (guitar.getCost()<= this.money) {
            this.guitar = guitar;
            this.money-=guitar.getCost();
            System.out.println("Ви успішно придбали гітару.");
        }
            else System.out.println("Недостатньо коштів, щоб придбати гітару.");
    }*/




   /* @Override
    protected Shopper clone() throws CloneNotSupportedException {
        Shopper shopper = (Shopper)super.clone();
        if (guitar !=null)
            shopper.guitar = guitar.clone();

        shopper.name = name;
        shopper.skills = new ArrayList<String>(skills);
        return shopper;
    }
*/





    /*@Override
    public int compareTo(Shopper o) {
        if (o.getAge()>this.getAge())
            return -1;
        else if (o.getAge()<this.getAge())
            return 1;
        return 0;
    }*/
    //переписаний метод для сортування по віку з взятий з інтерфейсу compareble


    public static Comparator<Shopper> moneyComparator = new Comparator<Shopper>() {
        @Override
        public int compare(Shopper o1, Shopper o2) {
            if (o1.money < o2.money)
                return -1;
            else if (o1.money > o2.money)
                return 1;
            return 0;
        }
    };
    public static Comparator<Shopper> nameComparator = new Comparator<Shopper>() {
        @Override
        public int compare(Shopper o1, Shopper o2) {
            return o1.name.compareTo(o2.name);
        }
    };

}







