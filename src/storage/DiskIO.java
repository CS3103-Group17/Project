package storage;

/* @@author A0124995R */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.Cities;
import model.City;
import model.History;
import model.Page;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;

public class DiskIO {
	private File citiesSaveFile;
	private File historySaveFile;
	private GsonBuilder builder;
	private Gson gson;
	private static final Logger logger = Logger.getLogger(DiskIO.class.getName());

	public DiskIO() {
		builder = new GsonBuilder().setPrettyPrinting();
		/*builder.registerTypeAdapter(ObjectProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(StringProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(IntegerProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(BooleanProperty.class, new PropertyTypeAdapter());*/
		gson = builder.create();
		setSaveFile("storage");
	}
	
	public DiskIO(String filename) {
		builder = new GsonBuilder().setPrettyPrinting();
		gson = builder.create();
		setSaveFile(filename);
	}

	// Sets save file to be used for saving/loading
	public void setSaveFile(String saveFileName) {
		citiesSaveFile = new File(saveFileName+"city.json");
		historySaveFile = new File(saveFileName+"history.json");
	}

	// Return save file
	public File getCitiesSaveFile() {
		return citiesSaveFile;
	}

	public Cities loadCities() {
		BufferedReader reader = null;
		try {
			// Reads data from file
			reader = new BufferedReader(new FileReader(citiesSaveFile));
			// Converts read data back into Java types
			return gson.fromJson(reader, Cities.class);
		} catch (Exception e) {
			return new Cities();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	public History loadHistory() {
		BufferedReader reader = null;
		try {
			// Reads data from file
			reader = new BufferedReader(new FileReader(historySaveFile));
			// Converts read data back into Java types
			return gson.fromJson(reader, History.class);
		} catch (Exception e) {
			return new History();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean save(Cities cities, History history) {
		String citiesJSON = gson.toJson(cities);
		String historyJSON = gson.toJson(history);

		return writeJSONToFile(citiesJSON, citiesSaveFile) && writeJSONToFile(historyJSON, historySaveFile);
	}

    private boolean writeJSONToFile(String jsonString, File file) {
    	assert jsonString != null;
    	logger.entering("Travel Crawler", "writeJSONToFile", new Object[] { jsonString, file });
        try {
            // Create new file if it doesn't exist
            if (!file.exists()) {
            	File parent = file.getParentFile();
            	if (parent != null && !parent.exists()) {
            		parent.mkdirs();
            	}
            	file.createNewFile();
            }
            // Write the JSON to saved file
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(file));

			writer.write(StringEscapeUtils.unescapeXml(jsonString));
			writer.close();
		} catch (IOException e) {
			logger.log(Level.INFO, "\"{0}\" saving JSON to file. JSON: \"{1}\"", new Object[] { e, jsonString });
			return false;
		}

		return true;
	}
    
    public boolean deleteFile(){
    	return citiesSaveFile.delete() && historySaveFile.delete();
    }
}
