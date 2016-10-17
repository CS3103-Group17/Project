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

import java.lang.reflect.Type;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
	private File saveFile;
	private Type dataListsType;
	private GsonBuilder builder;
	private Gson gson;
	private static final Logger logger = Logger.getLogger(Storage.class.getName());

	public Storage() {
		dataListsType = new TypeToken<City>() {}.getType();
		builder = new GsonBuilder().setPrettyPrinting();
		/*builder.registerTypeAdapter(ObjectProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(StringProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(IntegerProperty.class, new PropertyTypeAdapter());
		builder.registerTypeAdapter(BooleanProperty.class, new PropertyTypeAdapter());*/
		gson = builder.create();
		setSaveFile("storage.json");
	}

	// Sets save file to be used for saving/loading
	public void setSaveFile(String saveFileName) {
		saveFile = new File(saveFileName);
	}

	// Return save file
	public File getSaveFile() {
		return saveFile;
	}

	public Cities load() {
		BufferedReader reader = null;
		try {
			// Reads data from file
			reader = new BufferedReader(new FileReader(saveFile));
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

	public boolean save(Cities cities) {
		String dataListsJSON = gson.toJson(cities);

		return writeJSONToFile(dataListsJSON, saveFile);
	}

    private boolean writeJSONToFile(String jsonString, File file) {
    	assert jsonString != null;
    	logger.entering("JoltStorage", "writeJSONToFile", new Object[] { jsonString, file });
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

			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			logger.log(Level.INFO, "\"{0}\" saving JSON to file. JSON: \"{1}\"", new Object[] { e, jsonString });
			return false;
		}

		return true;
	}
}
