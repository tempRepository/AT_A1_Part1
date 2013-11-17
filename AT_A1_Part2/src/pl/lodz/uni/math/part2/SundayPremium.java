package pl.lodz.uni.math.part2;

public class SundayPremium {
    static int[] skipTable = new int[256];

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
                if (!pattern.contains("?")) {
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

    public static boolean starSearch(String pattern, String text, boolean quiet) {
        boolean flag = false;
        String output = "";

        if (!pattern.contains("*")) {
            return search(pattern, text, quiet);
        } else {
            for (int i = 0; i < text.length()
                    - (pattern.length() - starCounter(pattern)) + 1; i++) {
                String tempPattern = pattern.substring(0, pattern.indexOf("*"));
                if (text.contains(tempPattern)) {
                    boolean answer = starSearch(
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
        System.out.println(SundayPremium.starSearch("ala*ala*ala",
                "gdrgrdalamakotaalagrgrgrgrgrgalagrgrg", false));

    }

}
