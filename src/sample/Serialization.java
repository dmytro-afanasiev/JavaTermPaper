package sample;

import objects.micro.Shopper;
import objects.secondMacro.Building;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serialization {

    public static void serializeNow(File file) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("shoppers", Main.shoppers);
            hashMap.put("buildings",Main.buildings);
            encoder.writeObject(hashMap);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
    public static void deserializeNow(File file){
        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

            for (int i = 0 ; i<Main.shoppers.size();i++){
                Shopper shopper = Main.shoppers.get(i--);
                Main.deleteAShopper(shopper);
            }
            for (int i = 0 ; i<Main.buildings.size();i++){
                Building building = Main.buildings.get(i--);
                Main.deleteABuilding(building);
            }
            HashMap<String, Object> hashMap = (HashMap<String, Object>)decoder.readObject();

            for (Building building: (ArrayList<Building>)hashMap.get("buildings")){
                Main.addNewBuilding(building);
            }
            for (Shopper shopper:(ArrayList<Shopper>)hashMap.get("shoppers")){
                Main.addNewShopper(shopper, false);
            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
}
