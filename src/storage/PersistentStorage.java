package storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringEscapeUtils;

import model.SearchHistory;
import model.travels.City;
import storage.CityDeserializer;

public class PersistentStorage {
    public static final PersistentStorage INSTANCE = new PersistentStorage();

    private static final char   JSON_ARRAY_START_CHAR     = '[';
    private static final char   JSON_ARRAY_END_CHAR       = ']';
    private static final char   JSON_OBJECT_END_DELIMITER = '}';
    private static final String JSON_ARRAY_DELIMITER      = ",";
    
    private static final String TRAVELS_STORAGE_FILENAME       = "travels.json";
    private static final String SEARCHHISTORY_STORAGE_FILENAME = "searchHistory.json";

	private Gson gson;
	private GsonBuilder gsonBuilder;

	private PersistentStorage() {
	    gsonBuilder = new GsonBuilder().setPrettyPrinting();
	    gsonBuilder.registerTypeAdapter(City.class, new CityDeserializer());
	    gsonBuilder.registerTypeAdapter(SearchHistory.class, new SearchHistoryDeserializer());
	    
		gson = gsonBuilder.create();
	}
	
	public void loadAllStorage() {
	    if (storageExists(TRAVELS_STORAGE_FILENAME)) {
	        readFromStorage(TRAVELS_STORAGE_FILENAME);
	    }
	    
	    if (storageExists(SEARCHHISTORY_STORAGE_FILENAME)) {
	        readFromStorage(SEARCHHISTORY_STORAGE_FILENAME);
	    }
	}
	
	public boolean storageExists(String filename) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        
        if (new File(filename).exists()) {
            return true;
        }
        
        return false;
	}
	
    public boolean readFromStorage(String filename) {
        DataController dataController = DataController.INSTANCE;
        
        JsonArray jsonArray = getJsonArrayFromStorage(filename);
        if (jsonArray == null) {
            return false;
        }
        
        for (JsonElement element : jsonArray) {
            switch (filename) {
                default:
                    break;
                case TRAVELS_STORAGE_FILENAME:
                    dataController.addCity(gson.fromJson(element, City.class));
                    break;
                    
                case SEARCHHISTORY_STORAGE_FILENAME:
                    dataController.addSearchHistory(gson.fromJson(element, SearchHistory.class));
            }
        }
        
        return true;
    }	
	
	public boolean writeToStorage(Object object) {
	    String json = "";
	    String filename = "";
	    
	    if (object instanceof City) {
	        json = gson.toJson((City) object);
	        json = StringEscapeUtils.unescapeXml(json);
	        
	        filename = TRAVELS_STORAGE_FILENAME;
	    
	    } else if (object instanceof SearchHistory) {
	        json = gson.toJson((SearchHistory) object);
	        
	        filename = SEARCHHISTORY_STORAGE_FILENAME;
	    }
	    
	    try (FileWriter writer = new FileWriter(filename, true);) {
	        writer.write(json);
	        writer.write(System.lineSeparator() + System.lineSeparator());
	        
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return false;
	    }
	    
	    return true;
	}	
	
	private JsonArray getJsonArrayFromStorage(String filename) {
	    StringBuilder stringBuilder = new StringBuilder();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename));) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String trimmedLine = line.trim();
	            
	            if (!trimmedLine.isEmpty()) {
	                if (line.charAt(0) == (JSON_OBJECT_END_DELIMITER)) {
	                    trimmedLine += JSON_ARRAY_DELIMITER;
	                }
	                
	                stringBuilder.append(trimmedLine);
	            }
	        }
	        
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    
	    /* If file is empty and/or file does not exists. */
	    if (stringBuilder.toString().isEmpty()) {
	        return null;
	    }
	    
	    /* Wrap the objects from file with json array delimiters. */
	    stringBuilder.insert(0, JSON_ARRAY_START_CHAR);
	    stringBuilder.setCharAt(stringBuilder.length() - 1, JSON_ARRAY_END_CHAR);
	    
	    try {
	        JsonElement element = new JsonParser().parse(stringBuilder.toString());
	        return element.getAsJsonArray();
	        
	    } catch (JsonParseException ex) {
	        ex.printStackTrace();
	    }
	    
	    return null;
	}
}
