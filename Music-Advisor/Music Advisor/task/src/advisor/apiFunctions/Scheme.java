package advisor.apiFunctions;

import advisor.Authenticator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Scheme {
public static String apiPath = "https://api.spotify.com";
public static int releasesPerPage = 5;
static boolean boolCreateCategoriesIDList = false;
static HashMap<String, String> hashMapCategoriesAndIdList = new HashMap<>();

abstract public void next();
abstract public void prev();
abstract public void obtain();
abstract public void printList();
}

class Album {
    String name;
    ArrayList<String> artists;
    String link;

    public Album(String name, ArrayList<String> artists, String link){
        this.name = name;
        this.artists = artists;
        this.link = link;
    }
    static void createCategoriesIDList(){
        String link = Scheme.apiPath + "/v1/browse/categories";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authenticator.ACCESS_TOKEN)
                .uri(URI.create(link))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            JsonArray categories = JsonParser.parseString(response.body()).getAsJsonObject()
                    .getAsJsonObject("categories")
                    .getAsJsonArray("items");
            for(JsonElement category : categories){
                String name = category.getAsJsonObject().get("name").getAsString();
                String id = category.getAsJsonObject().get("id").getAsString();
                Scheme.hashMapCategoriesAndIdList.put(name, id);
            }
            Scheme.boolCreateCategoriesIDList = true;
        } catch (IOException | InterruptedException e) {
            System.out.println("problem in handling a response");
        }
    }

}
class Playlist {
    String name;
    String link;
    public Playlist(String name, String link) {
        this.name = name;
        this.link = link;
    }

}