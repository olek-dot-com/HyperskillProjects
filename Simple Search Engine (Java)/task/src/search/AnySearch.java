package search;

import java.util.*;

public class AnySearch {
    void anySearch(ArrayList<String> people, Map<String, ArrayList<Integer>> map, String word){
        String[] everyWord = word.split(" +");
        Set<Integer> printFound = new HashSet<>();
        for(String words: everyWord){
            words = words.toLowerCase();
            if(map.containsKey(words)) {
                printFound.addAll(map.get(words));
            }
        }
        if(printFound.isEmpty()){
            System.out.println("No matching persons found.");
        }else{
            System.out.println("\n" + printFound.size() + " persons found:");
            for (Integer i: printFound){
                System.out.println(people.get(i));
            }
        }
    }
}
