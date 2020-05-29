package sample;

import objects.micro.Shopper;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class Serialization {

    public static void serializeNow(String path) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
            encoder.writeObject(Main.shoppers);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
    public static void deserializeNow(String path){
        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(path)));
            for (int i = 0 ; i<Main.shoppers.size();i++){
                Shopper shopper = Main.shoppers.get(i--);
                Main.deleteAShopper(shopper);
            }
            for(Shopper shopper: (ArrayList<Shopper>) decoder.readObject()){
                Main.addNewShopper(shopper, false);
            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }


    }
}
