package com.company;

public class Encryption {

    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final char[] alphabetLetter = alphabet.toCharArray();

    private char[] decodedText;

    private java.util.List<Character> alphabetList;

    public String encryptText(String originalText, int amountToShift) {

        //Takes in the original String to encrypt
        //Takes in the amount of letters to shift.

        originalText = originalText.toLowerCase();

        char[] orignChar = originalText.toCharArray();

        //ASCII Indexes of chars. a= 97. offset = 25(26 letters in alphabet, counts each of those.(starting at 0))
        int firstCharIndex = 'a';
        int offset = ('z' - 'a') + 1;


        for (int i = 0; i < orignChar.length; i++) {

            //ignores text that isn't letters
            String currentLetter = String.valueOf(orignChar[i]);
            if (alphabet.contains(currentLetter)) {

                //Uses the ASCII table to find out what number the current char is in the alphabet('a' would return 0, 'b' would return 1)
                int oldIndexInAlphabet = orignChar[i] - firstCharIndex;

                //assigns its new index by taking the old index, plussing it with the variable, and modding it by the offset(25)
                int newIndxInAlphabet = (oldIndexInAlphabet + amountToShift) % offset;

                //Converts the two numbers to chars. Taking the 'a'(97) and plussing it with the new index
                char newCharIndex = (char) (firstCharIndex + newIndxInAlphabet);

                //assigns the found char in the string to the new char.
                orignChar[i] = newCharIndex;

            }


        }
        return new String(orignChar);


    }

}




