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

public class Category extends Scheme{
    ArrayList<String> categories;

    int start;
    int end;
    int totalPages;
    int currentPage;

    public Category() {

        this.categories = new ArrayList<>();
    }

    void getAllCategories() {

        String requestUri = apiPath + "/v1/browse/categories";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authenticator.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JsonObject object = JsonParser.parseString(response.body()).
                    getAsJsonObject().
                    getAsJsonObject("categories");

            JsonArray array = object.getAsJsonArray("items");

            for (JsonElement item : array) {

                String name = item.getAsJsonObject().get("name").getAsString();
                String id = item.getAsJsonObject().get("id").getAsString();
                hashMapCategoriesAndIdList.put(name, id);
                this.categories.add(name);
            }

            this.start = 0;
            this.end = Math.min(start + releasesPerPage, categories.size());
            this.totalPages = categories.size() % releasesPerPage == 0 ?
                    categories.size() / releasesPerPage :
                    categories.size() / releasesPerPage + 1;
            this.currentPage = 1;
            boolCreateCategoriesIDList = true;

        } catch (InterruptedException | IOException e) {
            System.out.println("Problem in handling category response.");
        }
    }

    @Override
    public void obtain() {
        getAllCategories();
        printList();
    }

    @Override
    public void printList() {

        for (int i = start; i < end; i++) {
            System.out.println(categories.get(i));
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    @Override
    public void next() {

        if (end == categories.size()) {
            System.out.println("No more pages.");
            return;
        }

        start = end;
        end = Math.min(start + releasesPerPage, categories.size());
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
}
