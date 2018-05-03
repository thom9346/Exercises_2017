package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here


        ReadFiles readFiles = new ReadFiles();
        System.out.println(readFiles.wordsInArray());


        System.out.println();
        System.out.println("After its been sorted: ");
        System.out.println(readFiles.sortArrayList());
        System.out.println();


        System.out.println(readFiles.readWithStream());
        System.out.println();
        System.out.println(readFiles.readWithParallelStream());
















        /**
         * Punkt 5 i opgaven, beder om at sammenligne de 3 måder at gøre det på.
         *
         * Parallel stream er væsentlig hurtigere end de andre to måder,
         * streams og Collection metoden er noglelunde samme hastighed.
         * For det meste er streams lidt hurtigere, men i dette tilfælde(i min kode) sker der NOGLE GANGE at collections faktisk er hurtigere end streams
         * for det meste viser programmet dog, at streams er en (lille smule) hurtigere
         * tilgengæld bliver eksekveringstiden mere end halveret ved parallestreams(POWER OF THREADS RIGHT HERE)
         */


    }
}
