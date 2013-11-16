package pl.lodz.uni.math.part1;

public class NaivePM {
    String text;

    public NaivePM(String text) {
        this.text = text;
    }

    public static boolean myEquals(String text, String pattern) {

        boolean equals = true;
        for (int i = 0; i < text.length() && equals; i++) {
            if ((text.charAt(i) != pattern.charAt(i))
                    && pattern.charAt(i) != "?".toCharArray()[0]) {
                equals = false;
            }
        }
        return equals;
        // doesn't support spaces!
        /*
         * int i = 0, j = 0; while (i < text.length() && j < text.length()) { if
         * (text.charAt(i) != pattern.charAt(j) && pattern.charAt(j) != '?') {
         * return false; } else { if (pattern.charAt(j) == '?') { if
         * ((i+1)<text.length()&& (j+1)<pattern.length() &&
         * text.charAt(i+1)==pattern.charAt(j+1)) { i++; j++; } else { i--; } }
         * i++; j++; }
         * 
         * } return true;
         */
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

    public static boolean search(String pattern, String text, boolean quiet) {
        boolean flag = false;
        String output = "";

        if (!pattern.contains("*")) {
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
                String tempPattern = pattern.substring(0, pattern.indexOf("*"));
                if (text.contains(tempPattern)) {
                    boolean answer = search(
                            pattern.substring(pattern.indexOf("*") + 1),
                            text.substring(text.indexOf(tempPattern)
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
        // TODO Auto-generated method stub
        // dopisać zgodność dla ? jako spacji!

        System.out.println(NaivePM.search("a*a", "aa", false));
    }

}
