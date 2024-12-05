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

public class Featured extends Scheme{
    ArrayList<Playlist> fPlaylists;

    int start;
    int end;
    int totalPages;
    int currentPage;
    public Featured(){
        this.fPlaylists = new ArrayList<>();
    }
    @Override
    public void next() {
        if (end == fPlaylists.size()) {
            System.out.println("No more pages.");
            return;
        }
        start = end;
        end = Math.min(start + releasesPerPage, fPlaylists.size());
        currentPage++;
        printList();
    }


    @Override
    public void prev() {
        if (start == 0) {
            System.out.println("No more pages.");
            return;
        }

        end = start;
        start = end - releasesPerPage;
        currentPage--;
        printList();
    }

    @Override
    public void obtain() {
        getFeatured();
        printList();
    }

    @Override
    public void printList() {
        for (int i = start; i < end; i++){
            Playlist current = fPlaylists.get(i);
            System.out.println(current.name);
            System.out.println(current.link);
            System.out.println();
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    void getFeatured(){
        String link = apiPath + "/v1/browse/featured-playlists";
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authenticator.ACCESS_TOKEN)
                .uri(URI.create(link))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonArray playlists = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("playlists").getAsJsonObject()
                    .getAsJsonArray("items");
            for (JsonElement playlist : playlists) {
                String name = playlist.getAsJsonObject().get("name").getAsString();
                String playlistLink = playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
                fPlaylists.add(new Playlist(name, playlistLink));
            }
            this.start = 0;
            this.end = Math.min(start + releasesPerPage, fPlaylists.size());
            if(fPlaylists.size() % releasesPerPage == 0) this.totalPages = fPlaylists.size() / releasesPerPage;
            else{
                this.totalPages = (fPlaylists.size() / releasesPerPage) + 1;
            }
            this.currentPage = 1;
        }
            catch (IOException | InterruptedException e) {
            System.out.println("problem in handling a response");
        }

    }
}
