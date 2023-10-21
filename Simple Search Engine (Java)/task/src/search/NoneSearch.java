package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoneSearch {
   void noneSearch(ArrayList<String> people, Map<String, ArrayList<Integer>> map, String word){
       String[] everyWord = word.split(" +");
       Set<Integer> toRemove = new HashSet<>();
       for(String words: everyWord) {
           words = words.toLowerCase();
           if(map.containsKey(words)){
               toRemove.addAll(map.get(words));
           }
       }
       if(toRemove.isEmpty()) {
           System.out.println("No matching person found.");
       }else{
           System.out.println((people.size() - toRemove.size()) + " persons found:");
           for(int i = 0; i < people.size(); i++){
               if(!toRemove.contains(i)){
                   System.out.println(people.get(i));
               }
           }
       }
    }
}
