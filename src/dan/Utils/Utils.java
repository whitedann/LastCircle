package dan.Utils;

import java.awt.*;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null)
                builder.append(line + "\n");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static int parseInt(String number){
        try{
           return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }

    }

    public static boolean testIntersection(Shape A, Shape B){
        Area areaA = new Area(A);
        areaA.intersect(new Area(B));
        return !areaA.isEmpty();
   }

}
