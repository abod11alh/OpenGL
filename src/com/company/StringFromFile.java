package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StringFromFile {
    public static  CharSequence getString(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st, sum = "";
        while ((st = br.readLine()) != null) {
            sum += st + "\n";
        }

        return sum;
    }
}
