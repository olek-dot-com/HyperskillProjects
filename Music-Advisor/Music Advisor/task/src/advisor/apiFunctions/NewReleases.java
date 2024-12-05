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

public class NewReleases extends Scheme{
    ArrayList<Album> newReleases;
int start;
int end;
int totalPages;
int currentPage;

public NewReleases(){
    this.newReleases = new ArrayList<>();
}
    void getNewRelease(){
        String requestUri = apiPath + "/v1/browse/new-releases";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authenticator.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray array = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("albums").getAsJsonObject()
                    .getAsJsonArray("items");

            for (JsonElement item : array) {


                String albumName = item.getAsJsonObject().get("name").getAsString();
                String albumLink = item.getAsJsonObject()
                        .get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                ArrayList<String> artistList = new ArrayList<>();

                JsonArray artists = item.getAsJsonObject().get("artists").getAsJsonArray();

                for (JsonElement artist : artists) {
                    artistList.add(artist.getAsJsonObject().get("name").getAsString());
                }
                newReleases.add(new Album(albumName, artistList, albumLink));
                this.start = 0;
                this.end = Math.min(start + releasesPerPage, newReleases.size());
                if(newReleases.size() % releasesPerPage == 0){
                    this.totalPages = newReleases.size()/releasesPerPage;
                }else {
                    this.totalPages = newReleases.size() / releasesPerPage + 1;
                }
                this.currentPage = 1;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("problem with handling the response");
        }
    }
//done
    @Override
    // warunek inaczej niż na gicie
    public void next() {
    if(currentPage == totalPages){
        System.out.println("No more pages");
        return;
    }
    start = end;
    end = Math.min(start + releasesPerPage, newReleases.size());
    currentPage++;
    printList();
    }

    @Override
    // warunek inaczej niż na gicie
    public void prev() {
    if(currentPage == 1){
        System.out.println("No more pages");
        return;
    }
    end = start;
    start = end - releasesPerPage;
    currentPage--;
    printList();
    }

    @Override
    public void obtain() {
    getNewRelease();
    printList();
    }

    @Override
    public void printList() {
    for(int i = start; i<end; i++){
        Album current = newReleases.get(i);
        System.out.println(current.name);
        System.out.println(current.artists);
        System.out.println(current.link);
        System.out.println();
    }
    System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);

    }
}
