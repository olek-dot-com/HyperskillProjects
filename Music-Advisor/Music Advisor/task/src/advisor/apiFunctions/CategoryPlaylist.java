package advisor.apiFunctions;

import advisor.Authenticator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class CategoryPlaylist extends Scheme{
    ArrayList<Playlist> categoryPlaylist;
    String categoryName;
    int start;
    int end;
    int totalPages;
    int currentPage;

    public CategoryPlaylist(String categoryName){
        this.categoryName = categoryName;
        this.categoryPlaylist = new ArrayList<>();
    }

    @Override
    // warunek inaczej niż na gicie
    public void next() {
        if(currentPage == totalPages){
            System.out.println("No more pages");
            return;
        }
        start = end;
        end = Math.min(start + releasesPerPage, categoryPlaylist.size());
        currentPage++;
        printList();
    }

    @Override
    // warunek inaczej niż na gicie
    public void prev() {
        if(currentPage == 0){
            System.out.println("No more pages");
        }
        end = start;
        start = end - releasesPerPage;
        currentPage--;
        printList();
    }

    @Override
    public void obtain() {
        if(getCategories()){
            printList();
        }

    }

    @Override
    public void printList() {
        for(int i = start; i<end; i++){
            Playlist current = categoryPlaylist.get(i);
            System.out.println(current.name);
            System.out.println(current.link);
            System.out.println();
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);

    }
boolean getCategories(){
    if (!Scheme.boolCreateCategoriesIDList) {
        Album.createCategoriesIDList();
        Scheme.boolCreateCategoriesIDList = true;
    }
    String catID = hashMapCategoriesAndIdList.get(categoryName);
    if (!hashMapCategoriesAndIdList.containsKey(categoryName)) {
        System.out.println("Unknown category name.");
        return false;
    }else {
        String requestUri = apiPath + "/v1/browse/categories/" + catID + "/playlists";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authenticator.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.body().contains("error") || response.statusCode() >= 400) {

                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject error = json.getAsJsonObject("error");
                System.out.println(error.get("message").getAsString());
                return false;
            }else {
                JsonArray playlists = JsonParser.parseString(response.body()).getAsJsonObject()
                        .get("playlists").getAsJsonObject().get("items").getAsJsonArray();
                for (JsonElement item : playlists) {

                    String name = item.getAsJsonObject().get("name").getAsString();
                    String link = item.getAsJsonObject().get("external_urls").getAsJsonObject()
                            .get("spotify").getAsString();

                    categoryPlaylist.add(new Playlist(name, link));
                }
                this.start = 0;
                this.end = Math.min(start + releasesPerPage, categoryPlaylist.size());
                if(categoryPlaylist.size() % releasesPerPage == 0){
                    this.totalPages = categoryPlaylist.size() / releasesPerPage;
                }else{
                    this.totalPages = categoryPlaylist.size() / releasesPerPage + 1;
                }
                this.currentPage = 1;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling categorised playlist response.");
            e.printStackTrace();
        }
        return true;
    }

}

}
