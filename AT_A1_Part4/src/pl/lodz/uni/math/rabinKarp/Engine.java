package pl.lodz.uni.math.rabinKarp;

public class Engine {
    private static int ourModulo = 0;

    public static int getOurModulo() {
        return ourModulo;
    }

    public static void setOurModulo(int ourModulo) {
        Engine.ourModulo = ourModulo;
    }

    public Engine(int ourModulo) {
        this.ourModulo = ourModulo;
    }

    // In this specific example A should be prime number
    public int computeHash(String myText) {
        int hash = 0;
        for (int i = 0; i < myText.length(); i++) {
            hash += myText.charAt(i)*Math.pow(10, (myText.length()-1)-i) % ourModulo;
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
                int tempA=(int) (textHash-(text.charAt(i-1)*Math.pow(10, pattern.length() -1)));
                textHash=(int) (10*tempA)+text.charAt(i+pattern.length()-1);
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
        Engine engine = new Engine(Integer.MAX_VALUE / 2);
        engine.searchForPattern("alamakotaalakota", "ala");

    }

}
