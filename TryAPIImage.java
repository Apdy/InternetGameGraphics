package basicGame;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
 
//outputs url to an image
//original search engine code from http://learn-it-stuff.blogspot.com/2012/09/java-google-custom-search-api.html#uds-search-results
//modified for my final project
 
public class TryAPIImage {
 
        public String doSearch(String searchText) throws IOException {
        searchText = searchText.replaceAll(" ", "+");
        String key="AIzaSyBW_CC4gK6fgfGm3ZerFiln8C50-A7up5I"; //my own engine key
        String cx = "014317680767628517568:rzt7po-xts0"; //my custom search engine ID
        String imgColorType = "color"; //search for color images
        String searchType = "image"; //search for images only
        int num = 1; //only search for one image
        String link = ""; //image url
 
        URL url;
        try {
            url = new URL(
                      "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=" + cx + "&q=" + searchText + "&searchType=" + searchType + "&imgColorType=" + imgColorType + "&num=" + num + "&alt=json");                          
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
 
        while ((output = br.readLine()) != null) {
            if(output.contains("\"link\": \"")){  
                link = output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
                System.out.println(link);
            }    
        }
 
        conn.disconnect();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
                return link;
        }
 
}
 
