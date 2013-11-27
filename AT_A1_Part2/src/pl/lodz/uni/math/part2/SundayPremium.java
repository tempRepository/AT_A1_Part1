package pl.lodz.uni.math.part2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SundayPremium {
    static int[] skipTable = new int[256];
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
    
    private static void createSkipTable(String pattern) {
        for (int i = 0; i < skipTable.length; i++) {
            skipTable[i] = pattern.length() + 1;
        }

        for (int i = 0; i < pattern.length(); i++) {
            skipTable[pattern.charAt(i)] = pattern.length() - i;
        }

    }

    private static boolean myEquals(String text, String pattern) {

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

    public static boolean search(String pattern, String text, Boolean quiet) {
        boolean flag = false;
        createSkipTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (myEquals(text.substring(i, i + pattern.length()), pattern)) {
                if (!quiet) {
                    System.out.println("I found occurence at position " + i);
                }
                flag = true;
            }
            if (text.length() > (i + pattern.length())) {
                if (skipTable['?'] == pattern.length() + 1) {
                    i += skipTable[text.charAt(i + pattern.length())];
                } else {
                    if (skipTable[text.charAt(i + pattern.length())] < skipTable['?']) {
                        i += skipTable[text.charAt(i + pattern.length())];
                    } else {
                        i += skipTable['?'];
                    }
                }

            } else {
                break;
            }

        }
        return flag;
    }

    public static int searchWithPosition(String pattern, String text) {
        createSkipTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (myEquals(text.substring(i, i + pattern.length()), pattern)) {
                return i;
            }
            if (text.length() > (i + pattern.length())) {
                if (skipTable['?'] == pattern.length() + 1) {
                    i += skipTable[text.charAt(i + pattern.length())];
                } else {
                    if (skipTable[text.charAt(i + pattern.length())] < skipTable['?']) {
                        i += skipTable[text.charAt(i + pattern.length())];
                    } else {
                        i += skipTable['?'];
                    }
                }

            } else {
                break;
            }

        }
        return -1;
    }

    public static boolean starSearch(String pattern, String text, boolean quiet) {
        boolean flag = false;
        String output = "";
        int starPosition = pattern.indexOf('*');
        if (starPosition == -1) {
            return search(pattern, text, quiet);
        } else {
            for (int i = 0; i < text.length()
                    - (pattern.length() - starCounter(pattern)) + 1; i++) {
                String tempPattern = pattern.substring(0, starPosition);
                int tempPatternPosition = searchWithPosition(tempPattern, text);
                if (tempPatternPosition != -1) {
                    boolean answer = starSearch(
                            pattern.substring(starPosition + 1),
                            text.substring(tempPatternPosition
                                    + tempPattern.length()), true);
                    if (!quiet) {
                        output += "I found occurence at position: "
                                + text.indexOf(tempPattern) + "\n";

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
        String book=loadBook("book");
        System.out.println(SundayPremium.starSearch("bird",
                book, false));
        System.out.println(peeksCounter);

    }

}
