package archive;

//from stackoverflow: rjha94
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ebayURLReader {

    public static String read(String address) throws Exception{

        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        String line;
        String response;
        long totalBytes = 0  ;

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            totalBytes += line.getBytes("UTF-8").length ;
        }

        response = builder.toString();

        return response ;
    }

}
