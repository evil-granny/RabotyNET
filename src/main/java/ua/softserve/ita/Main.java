package ua.softserve.ita;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String [] args) throws IOException {
        Scanner scanner = new Scanner(new File("src/main/resources/names2.txt")).useDelimiter("\\s");
        String word;
        int i = 0;
        StringBuilder str = new StringBuilder();
        while (scanner.hasNext()){
            if((word = scanner.next()).matches(".*[A-Z].*")){
                String low = word.toLowerCase();
                char[] spell = low.toCharArray();
                char spellUp = Character.toUpperCase(spell[0]);
                spell[0] = spellUp;
                String lastName = String.valueOf(spell);
                str.append(lastName + " ");
            }
        }
        log.info(str.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\test\\resources\\names2.txt"));
        writer.write(str.toString());
        writer.close();
    }
}
