package search;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AllSearch {
    Scanner scanner = new Scanner(System.in);
     void allSearch(ArrayList<String> list, Map<String, ArrayList<Integer>> map, String query){
         String[] words = query.split(" +");
         ArrayList<String> found = new ArrayList<>();

         for (String line : list) {

             boolean matchFound = true;

             for (String word : words) {

                 if (!line.toUpperCase().contains(word.toUpperCase())) {
                     matchFound = false;
                     break;
                 }
             }

             if (matchFound) {
                 found.add(line);
             }
         }

         if (found.isEmpty()) {
             System.out.println("No matching person found.");
         } else {

             System.out.println();
             System.out.println(found.size() + " persons found:");
             found.forEach(System.out::println);
         }
    }
}
