package com.wrecker.sampleweather.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created by xiaoxin on 2016/9/21.
 */
public class Cache {
    public static void writeToSDCard(List list,String fileName){
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);

            fos.close();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static List getSDcardForacastInfo(String fileName){
        List forecastList = new ArrayList();

        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try{
            fin = new FileInputStream(fileName);
            ois = new ObjectInputStream(fin);
            List obj = (List)ois.readObject();

            if(null != obj){
                forecastList = obj;
            }

            fin.close();
            ois.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                fin.close();
                ois.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
