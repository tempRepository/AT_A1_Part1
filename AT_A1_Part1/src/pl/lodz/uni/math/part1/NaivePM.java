package pl.lodz.uni.math.part1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class NaivePM {
    public static int peeksCounter = 0;

    public static String loadBook(String name) {
        Scanner in = null;
        String temp = "";

        try {
            in = new Scanner(new FileReader(name));
            while (in.hasNextLine()) {
                temp += in.nextLine();
            }
            in.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return temp;
    }

    public static boolean myEquals(String text, String pattern) {

        boolean equals = true;
        for (int i = 0; i < text.length() && equals; i++) {
            peeksCounter++;
            if ((text.charAt(i) != pattern.charAt(i))
                    && pattern.charAt(i) != "?".toCharArray()[0]) {
                equals = false;
            }
        }
        return equals;

    }

    public static int starCounter(String pattern) {
        int counter = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '*') {
                counter++;
            }
        }
        return counter;
    }

    public static int simpleSearchWithPosition(String pattern, String text) {
        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            if (myEquals(text.substring(i, i + pattern.length()), pattern)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean search(String pattern, String text, boolean quiet) {
        boolean flag = false;
        String output = "";
        int starPosition = pattern.indexOf("*");
        if (starPosition == -1) {
            for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
                if (myEquals(text.substring(i, i + pattern.length()), pattern)) {
                    if (!quiet) {
                        output += "I found occurence at position: " + i + "\n";
                    }
                    flag = true;
                }
            }
        } else {
            for (int i = 0; i < text.length()
                    - (pattern.length() - starCounter(pattern)) + 1; i++) {
                String tempPattern = pattern.substring(0, starPosition);
                int tempPatternPosition = simpleSearchWithPosition(tempPattern,
                        text);
                if (tempPatternPosition != -1) {
                    boolean answer = search(
                            pattern.substring(starPosition + 1),
                            text.substring(tempPatternPosition
                                    + tempPattern.length()), true);
                    if (!quiet) {
                        output += "I found occurence at position: "
                                + tempPatternPosition + "\n";

                    }
                    if (answer) {
                        System.out.println(output);
                    }
                    return answer;
                } else {
                    return false;
                }
            }
        }
        if (flag) {
            System.out.println(output);
        }
        return flag;
    }

    public static void main(String[] args) {
        //pattern1 ajfvl
        //pattern2 jfkdlopruj
        String book = loadBook("book2000");
        String pattern="jfkdlopruj";
        long avgTime=0;
        for (int i = 0; i < 10; i++) {
            TimeCounter.start();
            NaivePM.search(pattern, book, true);
            avgTime+=TimeCounter.stopTime();
        }
        System.out.println("Avg time "+(avgTime/10));
        NaivePM.peeksCounter=0;
        System.out.println(NaivePM.search(pattern, book, false));
        System.out.println(NaivePM.peeksCounter);

    }

}
