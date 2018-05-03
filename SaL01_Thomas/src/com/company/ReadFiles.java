package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFiles {

    private ArrayList<String> allWords = new ArrayList<>();
    private ArrayList<String> sortedWords = new ArrayList<>(); //sorted list for collection method

    private String fileName = "AliceInWonderland.txt";



//punkt 1:
    public ArrayList<String> wordsInArray()
    {

        try {
            Scanner scanner = new Scanner(new File(fileName));

            while (scanner.hasNext()){
                String tempString = scanner.next();
                    allWords.add(tempString);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return allWords;

    }

    //sorts array with collections (punkt 2)
    public ArrayList<String> sortArrayList(){

        long startTime = System.currentTimeMillis();
        int countWords = 0;


        for (int i = 0; i < allWords.size(); i++) {

            String currentWord = allWords.get(i);
            currentWord = currentWord.replaceAll("[^a-zA-Z]", ""); // removes all non-letters

            if (!currentWord.equalsIgnoreCase("")){
                sortedWords.add(currentWord);
                countWords++;
            }
        }

        Collections.sort(sortedWords.subList(0, sortedWords.size())); //sorts arraylist alphabetically

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("This is how many miliseconds it took to execute method using Collections.sort + counting all words: " + totalTime + "ms");
        System.out.println("Number of words(NOTE: SPACES ARE NOT COUNTED AS WORDS): " + countWords);
        return sortedWords;
    }

    //punkt 3
    public List<String> readWithStream(){

        long startTime = System.currentTimeMillis();

       List<String> streamList =
               allWords.stream()
                .map(allWords -> allWords.replaceAll("[^a-zA-Z]", "")) //replaces all non letters with nothing
                .sorted(String::compareTo) //sorts array
                .filter(allWords -> !allWords.equalsIgnoreCase("")) //if theres an empty spot, dont include it in the array
                .collect(Collectors.toList()); //stores arraylist in collect



        System.out.println("This is the amount of words: " + streamList.size());

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("This is how many miliseconds it took to execute method using Stream " + totalTime + "ms");


        return streamList;

    }

    //punkt 4 - parallel streaming. (MUcH FASTER)
    public List<String> readWithParallelStream(){

        long startTime = System.currentTimeMillis();

        List<String> streamList =
                allWords.parallelStream()
                        .map(allWords -> allWords.replaceAll("[^a-zA-Z]", "")) //replaces all non letters with nothing
                        .sorted(String::compareTo) //sorts array
                        .filter(allWords -> !allWords.equalsIgnoreCase("")) //if theres an empty spot, dont include it in the array
                        .collect(Collectors.toList()); //stores arraylist in collect




        System.out.println("This is the amount of words: " + streamList.size());

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("This is how many miliseconds it took to execute method using ParallelStream " + totalTime + "ms");


        return streamList;

    }

}
