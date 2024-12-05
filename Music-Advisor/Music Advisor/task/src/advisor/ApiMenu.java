package advisor;

import java.util.*;
import advisor.apiFunctions.*;

public class ApiMenu {
    static Scanner scanner = new Scanner(System.in);
    public static boolean authenticated = false;
    Scheme action;
    public void menu(){
        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case "auth" -> {
                    Authenticator authentication = new Authenticator();
                    authentication.getAccessCode();
                    authentication.getAccessToken();
                    authenticated = true;
                }
                case "new" -> {
                    if (authenticated) {
                        action = new NewReleases();
                        action.obtain();
                    }else{
                        System.out.println("Please, provide access for application.");
                    }
                }
                case "featured" -> {
                    if (authenticated) {
                        action = new Featured();
                        action.obtain();
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                }
                case "categories" -> {
                    if (authenticated) {
                        action = new Category();
                        action.obtain();
                    } else {
                        System.out.println("Please, provide access for application.");
                    }
                }
//                case "exit" -> {
//                    System.out.println("---GOODBYE!---");
//                    return;
//                }
                case "next" -> action.next();
                case "prev" -> action.prev();
                default -> {
                    if (input.matches("playlists .+")) {
                        if (authenticated) {
                            String categoryName = input.substring(10);
                            action = new CategoryPlaylist(categoryName);
                            action.obtain();
                        }else{
                            System.out.println("Please, provide access for application.");
                        }
                    }
                }
            }
        }
    }
}
