//package com.example.gplxb2;
//import android.content.Context;
//
//import com.google.gson.Gson;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//
//public class JsonReader {
//
//    public static DriverData readDriverDataFromJson(Context context) throws IOException {
//        InputStream inputStream = context.getAssets().open("driver_data.json");
//        Gson gson = new Gson();
//        InputStreamReader reader = new InputStreamReader(inputStream);
//        DriverData driverData = gson.fromJson(reader, DriverData.class);
//        reader.close();
//        return driverData;
//    }
//}
