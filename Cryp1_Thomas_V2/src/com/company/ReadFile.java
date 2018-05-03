package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadFile {


    public String readFile(Charset encoding) throws IOException {

        {
            byte[] encoded = Files.readAllBytes(Paths.get("AliceInWonderland.txt"));
            return new String(encoded, encoding);
        }


    }
}
