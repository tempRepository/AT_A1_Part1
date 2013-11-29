package pl.lodz.uni.math.rabinKarp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Engine {
    private static int ourModulo = 0;
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
    public static int getOurModulo() {
        return ourModulo;
    }

    public static void setOurModulo(int ourModulo) {
        Engine.ourModulo = ourModulo;
    }

    public Engine(int ourModulo) {
        this.ourModulo = ourModulo;
    }


    public int computeHash(String myText) {
        int hash = 0;
        for (int i = 0; i < myText.length(); i++) {
            hash += myText.charAt(i)*Math.pow(257, (myText.length()-1)-i) % ourModulo;
        }
  
        return hash;
    }

    void searchForPattern(String text, String pattern) {
        int patternHash = computeHash(pattern);
        System.out.println("Text length " + text.length() + " pattern length "
                + pattern.length());
        int falseCounter = 0;
        int textHash = computeHash(text.substring(0, pattern.length()));
        for (int i = 0; i < text.length() - pattern.length(); i++) {
            //recomputing the hash in constant time
            if (i != 0) {
                //?
                int tempA=(int) (textHash-(text.charAt(i-1)*Math.pow(257, pattern.length() -1)));
                textHash=(int) (257*tempA)+text.charAt(i+pattern.length()-1);
            }
            if (textHash == patternHash) {
                // sprawdzanie rzeczywistej zgodnosci
                if (text.substring(i, i + pattern.length()).equals(pattern)) {
                    System.out.println("I found pattern on position: " + i);
                } else {
                    falseCounter++;
                }
            }
        }
        System.out.println("Fake alarms: " + falseCounter);
    }

    public static void main(String[] args) {
        //pattern1 ajfvl
        //pattern2 jfkdlopruj
        String book = loadBook("book6000");
        String pattern="jfkdlopruj";
        Engine engine = new Engine(Integer.MAX_VALUE / 2);

        
        long avgTime=0;
        for (int i = 0; i < 500; i++) {
            TimeCounter.start();
            engine.searchForPattern(book, pattern);
            avgTime+=TimeCounter.stopTime();
        }
        System.out.println("Avg time "+(avgTime/500));
       
       }
    
     

}
