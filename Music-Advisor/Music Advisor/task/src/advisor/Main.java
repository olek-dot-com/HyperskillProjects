package advisor;

import advisor.apiFunctions.Scheme;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length>0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-access" -> {
                        i++;
                        Authenticator.SERVER_PATH = args[i];
                    }
                    case "-resource" -> {
                        i++;
                        Scheme.apiPath = args[i];
                    }
                    case "-page" -> {
                        i++;
                        Scheme.releasesPerPage = Integer.parseInt(args[i]);
                    }
                }
            }
        }

        ApiMenu api = new ApiMenu();
        api.menu();
    }
}
