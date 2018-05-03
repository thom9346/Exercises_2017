package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        Encryption encryption = new Encryption();
        ReadFile readFile = new ReadFile();
        Decryption decryption = new Decryption();
        String alice = readFile.readFile(Charset.defaultCharset());

        Random random = new Random();
        int randomNumber = random.nextInt(6)+1;
        System.out.println(randomNumber);

        //System.out.println(alice);


        String encrypedAlice = encryption.encryptText(alice,randomNumber);

        System.out.println(encrypedAlice);

        System.out.println("Above is the encrypted version of alice. Do you wish to try to brute-force the code, and decipher it? Press A for yes");
        System.out.println("Notice there will be multiple different options, and only 1 will be correct. Scroll up til you find the right one");
        Scanner scanner = new Scanner(System.in);

        String answer = scanner.next();

        if (answer.equalsIgnoreCase("a")) {
            System.out.println("below is decrypted:");

            decryption.decryptText(encrypedAlice);
        }

      //  System.out.println(Arrays.toString(encryption.producePlaintext(encryption.encryptText("abcd", 1))));


    }
}
