package com.company;

import java.util.Arrays;

public class Decryption {


    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private  char[] alphabetLetter = alphabet.toCharArray();



public void decryptText(String text) {

    text = text.toLowerCase();
    char[] textChar = text.toCharArray();


    int firstCharIndex = 'a';
    int offset = ('z' - 'a') + 1;

    for (int i = 0; i < 26; i++) {

        for (int j = 0; j < textChar.length ; j++) {

            String currentLetter = String.valueOf(textChar[j]);
            if(alphabet.contains(currentLetter)){

                int oldIndex = textChar[j] - firstCharIndex;
                int newIndex= (oldIndex + i) % offset;
                char newCharIndx = (char) (firstCharIndex + newIndex);

                textChar[j] = newCharIndx;

            }

        }

        System.out.println(new String(textChar) + "\n");
        //ressets text
        textChar = text.toCharArray();
        alphabetLetter = alphabet.toCharArray();



    }



    }


    }
