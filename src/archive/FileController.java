package archive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileController {
	File file;
	
	public FileController(String filename){
		file = new File(filename);
		
		try {
			file.createNewFile();
			clearFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String str){
		// creates the file
	    try {
	    	// creates a FileWriter Object
		      FileWriter writer = new FileWriter(file, true); 
		      
		      // Writes the content to the file
		      writer.write(str); 
		      writer.flush();
		      writer.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	      
	}
	
	private void clearFile() throws IOException{
		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
	}
}
