package search;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
   static void printAllData(ArrayList<String> data) {
        for (String datum : data) {
            System.out.println(datum);
        }
    }
    static int Menu(){
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
        int menu = scanner.nextInt();
        scanner.nextLine();
        return menu;
    }
    static ArrayList<String> fileReader(File file){
        ArrayList <String> people = new ArrayList<>();
        try(Scanner inFile = new Scanner(file)){
            while (inFile.hasNext()){
                people.add(inFile.nextLine());
            }
            inFile.close();
            file.delete();
        }catch (FileNotFoundException e){
            System.out.println("Not Found");
        }
        return people;
    }
    static Map<String, ArrayList<Integer>> makeMap(ArrayList<String> lines) {
       Map<String, ArrayList<Integer>> persons = new HashMap<>();
       int i = 0;
        for (String line : lines) {
            String[] words = line.split(" +");
            for (String word: words) {
                word = word.toLowerCase();
                if(persons.containsKey(word)){
                    persons.get(word).add(i);
                    persons.put(word, persons.get(word));
                } else{
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(i);
                    persons.put(word, arrayList);
                }
            }
            i++;
        }

       return persons;
    }
    static void searchFor(ArrayList<String> people, Map<String, ArrayList<Integer>> map) {
        System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
        String searchType = scanner.nextLine();
        System.out.println("\nEnter a name or email to search all suitable people.");
        String word = scanner.nextLine();
        switch (searchType) {
            case "ALL" -> {
                AllSearch allSearch = new AllSearch();
                allSearch.allSearch(people, map, word);
            }
            case "ANY" -> {
                AnySearch anySearch = new AnySearch();
                anySearch.anySearch(people, map, word);
            }
            case "NONE" -> {
                NoneSearch noneSearch = new NoneSearch();
                noneSearch.noneSearch(people, map, word);
            }
            default -> {
            }
        }


   }
    public static void main(String[] args) {
        ArrayList<String> people = fileReader(new File(args[1]));
        Map<String, ArrayList<Integer>> map = makeMap(people);
        while (true){
        int menu = Menu();
            switch (menu) {
                case 1 -> {
                    searchFor(people,map);
                }
                case 2 -> {
                    printAllData(people);
                }
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> {
                    System.out.println("Incorrect option! Try again.");
                }
            }
        }
    }
}
