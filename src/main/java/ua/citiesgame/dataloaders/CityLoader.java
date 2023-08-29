package ua.citiesgame.dataloaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityLoader {
    private static final Gson gson;
    private static final String CITIES_FILENAME = "src\\main\\java\\ua\\res\\cities.json";

    static {
        gson = new GsonBuilder().create();
    }

    private CityLoader() {

    }

    public static ArrayList<String> getCities() {
        String[] cities;

        try (FileReader reader = new FileReader(CITIES_FILENAME)){
            cities = gson.fromJson(reader, String[].class);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        return new ArrayList<>(List.of(cities));
    }

}


