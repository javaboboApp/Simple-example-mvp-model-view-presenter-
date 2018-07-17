package com.javabobo.projectdemo.Utils;

import com.javabobo.projectdemo.Models.GaleryImage;
import com.javabobo.projectdemo.Models.Task;
import com.javabobo.projectdemo.Models.User;
import com.javabobo.projectdemo.Models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by luis on 14/02/2018.
 */

public class ParseJSON {

    public static Weather readWeather(JSONObject jsonObject) throws JSONException {
        Weather weather = new Weather();
        weather.setCity(jsonObject.getString("name"));
        //Kelvin to C.
        weather.setTemp(String.valueOf((int) (jsonObject.getJSONObject("main").getDouble("temp") - 273.15)));
        return weather;

    }


    public static User readUser(JSONObject jsonObject) throws JSONException {
        User user = new User();

        jsonObject = jsonObject.getJSONObject("user");
        user.setIdUser(jsonObject.getInt("id"));
        user.setUserName(jsonObject.getString("username"));
        user.setEmail(jsonObject.getString("email"));
        user.setPathDir(jsonObject.getString("id_dir"));
        return user;

    }

    public static LinkedList<GaleryImage> readImages(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("images");
        LinkedList<GaleryImage> galeryImages = new LinkedList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            GaleryImage galeryImage = new GaleryImage();
            galeryImage.setNameImage(jsonArray.getString(i));
            galeryImages.add(galeryImage);
        }
        return galeryImages;
    }

    public static LinkedList<Integer> readClothes(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("payload");
        LinkedList<Integer> distri = new LinkedList<>();
        HashMap<String, Integer> hashMap = new LinkedHashMap<>();

        for(int i = 0; i < jsonArray.length(); i++){
             Integer num = hashMap.get(jsonArray.getJSONObject(i).getString("clothe"));
              String key = jsonArray.getJSONObject(i).getString("clothe");
              if( num== null){
                 hashMap.put(key, 1);
              }else hashMap.put(key, num+1);

        }

        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            distri.add((Integer) pair.getValue());
        }


        return distri;
    }

    public static LinkedList<Task> readAllTask(JSONObject response) throws JSONException {
        LinkedList<Task> linkedList = new LinkedList<>();
        JSONArray jsonArray = response.getJSONArray("tasks");
        for (int i= 0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Task task = new Task();
            task.setIdUser(jsonObject.getInt("id_user"));
            task.setTitle(jsonObject.getString("title"));
            task.setFinish(jsonObject.getInt("finish"));
            task.setId(jsonObject.getInt("id_task"));
            linkedList.add(task);
        }
        return linkedList;
    }
}
