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
        XMLEncoder encoder;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("shoppers", Main.getWorld().getShoppers());
            hashMap.put("buildings",Main.getWorld().getBuildings());
            encoder.writeObject(hashMap);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
    public static void deserializeNow(File file){
        XMLDecoder decoder;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

            for (int i = 0 ; i<Main.getWorld().getShoppers().size();i++){
                Shopper shopper = Main.getWorld().getShoppers().get(i--);
                Main.getWorld().deleteAShopper(shopper);
            }
            for (int i = 0 ; i<Main.getWorld().getBuildings().size();i++){
                Building building = Main.getWorld().getBuildings().get(i--);
                Main.getWorld().deleteABuilding(building);
            }
            HashMap<String, Object> hashMap = (HashMap<String, Object>)decoder.readObject();

            for (Building building: (ArrayList<Building>)hashMap.get("buildings")){
                Main.getWorld().addNewBuilding(building);
            }
            for (Shopper shopper:(ArrayList<Shopper>)hashMap.get("shoppers")){
                Main.getWorld().addNewShopper(shopper, false);
            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
}
