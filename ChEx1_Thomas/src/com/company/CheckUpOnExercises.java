package com.company;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
    CheckUpOnExercises.java
    is a modern way to read and access the data in form of
       1)  filenames  (exercise solutions delivered by the students
       2)  weights    exercisenames (abbriviated) and weights

    @author     Asger B. Clausen
    @version    1.0


 */


public class CheckUpOnExercises {

    //here the value is an intger, which can change. The key(STRING) is unique and cant change.
    //purpose is to keep the value in integer variables and the people who wrote the assignements unique.
    private static TreeMap<String, Integer> peoplesPoint = new TreeMap<>();
    private static ArrayList<String> wronglyDelivered = new ArrayList<>();


    public static void main(String[] args) {

        String pathName = "D:\\KEA 3. Semester\\SWC\\ExamplesProgramming\\ChEx1_Thomas\\Uploaded20171109";
        String fileName = "ExerciseWeight.txt";

        Path directory = Paths.get(pathName); //directory contains PathName
        File exerciseWeights = new File(fileName); //contains fileName for exercises

        Path path = Paths.get(pathName, fileName);

        System.out.println(path);

        ArrayList<String> delivered = new ArrayList<>();
        Map<String, String> weights;

        weights = readWeights(path);
        int maxPoints = calculateAllPoints(path); //calculates the max points you have have


        try {
            delivered = readSolutions(directory, weights, maxPoints);

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param directory
     * @return the full path of each assignement
     */

    private static ArrayList<String> readSolutions(Path directory, Map<String, String> weight, int maxPoints) throws FileNotFoundException, UnsupportedEncodingException {
        ArrayList<String> deliv = new ArrayList<>();
        //printwriter to make a text document with the information
        PrintWriter writer = new PrintWriter("Students-Points.txt", "UTF-8");


        try (DirectoryStream<Path> directoryStream =
                     Files.newDirectoryStream(directory)) {
            for (Path file : directoryStream) {
                deliv.add(file.toString());

                /**
                 * This is added to asgers original file
                 */
                String fileName = file.getFileName().toString();
                String[] parts = null;
                String contributersName = null;
                String assignmentName = null;
                //final cause they're used in lambda expressions
                final Integer[] value = {null};
                final boolean[] doesPersonExist = {false};

                if (fileName.contains("_")) {

                    parts = fileName.split("_");

                    assignmentName = parts[0]; //saves the name of the assignement in a variable
                    contributersName = parts[1]; //saves the contributer of the assignement in another variable.


                    System.out.println(assignmentName);
                    //removes all ending files, zip, rar or 7z.
                    contributersName = contributersName.replace(".zip", "");
                    contributersName = contributersName.replace(".rar", "");
                    contributersName = contributersName.replace(".7z", "");

                    //System.out.println(weight.keySet());

                    String finalAssignmentName = assignmentName;
                    String finalContributersName1 = contributersName;
                    weight.entrySet()
                            .stream()
                            .forEach(entry -> {

                                //in case a person already exsist in peoplespoint
                                peoplesPoint.entrySet()
                                        .stream()
                                        .forEach(e -> {
                                            if (e.getKey().equals(finalContributersName1)) {
                                                doesPersonExist[0] = true;
                                            }
                                        });


                                //if the delivered assignement name equals any of the names in the .txt file do this
                                if (entry.getKey().equalsIgnoreCase(finalAssignmentName)) {

                                    value[0] = Integer.parseInt(entry.getValue());


                                    //Hvis contributer eksistere, så add value for den opgave han har lavet til hans forrige value
                                    if (doesPersonExist[0]) {
                                        value[0] = peoplesPoint.get(finalContributersName1) + value[0]; //plusses their current value with a new value
                                        peoplesPoint.put(finalContributersName1, value[0]);

                                    } else
                                        peoplesPoint.put(finalContributersName1, value[0]);
                                }


                            });


                } else {
                    wronglyDelivered.add(fileName);

                }


//                int i = 0;
//                for (Map.Entry<String, Integer> test : peoplesPoint.entrySet()) {
//
//                    System.out.println("Key: " + test.getKey() + ". Value: " + test.getValue() + " this is i: " + i );
//                    i++;
//
//                }

                //System.out.println(assignmentName + ", "  + contributersName);

                //System.out.println( file.getFileName() );  // just for check
            }

            // note: %-40s% means that we will left-align the first thing after the comma "Person" in a 40-character placeholder
            System.out.printf("%-40s%-24s%-26s%-28s \n", "Person:", "Points:", "Procent:", "Max point:\n");
            writer.printf("%-40s%-24s%-26s%-28s \n", "Person:", "Points:", "Procent:", "Max point:\n");

            peoplesPoint.entrySet()
                    .stream()
                    .forEach(ent -> {

                        double precentagePoint = ((double) ent.getValue() / maxPoints) * 100;

                        String procentPointFinal = String.format("%.2f", precentagePoint); //limits the decimals to two decimal numbers


                        System.out.printf("%-40s%-24s%-26s%-28s\n", ent.getKey(), ent.getValue(), procentPointFinal + "%", maxPoints);
                        writer.printf("%-40s%-24s%-26s%-28s\n", ent.getKey(), ent.getValue(), procentPointFinal + "%", maxPoints);
                    });

            System.out.println();

            System.out.print("\nNOTE: Følgende opgaver er ikke leveret korrekt. Check selv, om der skal udleveres flere point: \n");
            System.out.println(wronglyDelivered.toString());

            writer.print("\nNOTE: Følgende opgaver er ikke leveret korrekt. Check selv, om der skal udleveres flere point: \n");
            writer.println(wronglyDelivered.toString());


            writer.close();

        } catch (IOException | DirectoryIteratorException ex) {
            ex.printStackTrace();
        }
        return deliv;
    }

    // Notice: The Key in this map is the assignement names
    private static Map<String, String> readWeights(Path path) {
        Map<String, String> weigh = new HashMap<>();

        try (Stream<String> lines = Files.lines(path)) {
            weigh = lines.map(s -> s.toUpperCase())
                    .collect(
                            Collectors.toMap(k -> k.split(" ")[0],
                                    v -> v.split(" ")[1]));
        } catch (IOException e) {
            System.out.println(" This went wrong. ");
        }
        return weigh;
    }

    public static int calculateAllPoints(Path path) {
        final int[] value = {0};

        readWeights(path).entrySet()
                .stream()
                .forEach(values -> {

                    value[0] = value[0] + Integer.parseInt(values.getValue());
                });

        return value[0];

    }

    // public static boolean doesAssignmentExist(String assignmentName, )

} 

