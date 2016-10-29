package storage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import model.ImageData;
import model.SearchField;
import model.SearchHistory;
import model.Section;
import model.travels.City;

class CityDeserializer implements JsonDeserializer<City> {
    @Override
    public City deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject object = element.getAsJsonObject();
        
        City city = new City(UUID.fromString(object.get("cityId").getAsString()));
        city.setName(object.get("name").getAsString());
        
        if (object.get("googleTrendsHTML") != null) {
            city.setGoogleTrendsHTML(object.get("googleTrendsHTML").getAsString());
        }
        
        Type sectionType = new TypeToken<ArrayList<Section>>(){}.getType();
        city.setSections(gson.fromJson(object.get("sections").getAsJsonArray(), sectionType));
        
        Type imageType = new TypeToken<ArrayList<ImageData>>(){}.getType();
        city.setImages(gson.fromJson(object.get("images").getAsJsonArray(), imageType));
        
        return city;
    }
}

class SearchHistoryDeserializer implements JsonDeserializer<SearchHistory> {
    @Override
    public SearchHistory deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject object = element.getAsJsonObject();

        Type searchFieldType = new TypeToken<SearchField>(){}.getType();
        SearchHistory searchHistory = new SearchHistory(gson.fromJson(object.get("searchField").getAsJsonObject(), searchFieldType));
        
        Type idsType = new TypeToken<ArrayList<UUID>>(){}.getType();
        searchHistory.setTravelsIds(gson.fromJson(object.get("travelsIds").getAsJsonArray(), idsType));

        return searchHistory;
    }
}
