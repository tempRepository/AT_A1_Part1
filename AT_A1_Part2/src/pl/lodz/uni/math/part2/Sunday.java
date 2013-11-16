package pl.lodz.uni.math.part2;

public class Sunday {
int[] skipTable=new int[256];
private void createSkipTable(String pattern)
{
    for (int i = 0; i < skipTable.length; i++) {
        skipTable[i]=pattern.length()+1;
    }
    
    for (int i = 0; i < pattern.length(); i++) {
        skipTable[pattern.charAt(i)]=pattern.length()-i;
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

public void search(String text, String pattern)
{
    createSkipTable(pattern);
    int i=0;
    while (i<=text.length()-pattern.length()) {
        if (myEquals( text.substring(i, i+pattern.length()), pattern)) {
            System.out.println("I found occurence at position "+ i);
        }
        if (text.length()>(i+pattern.length())) {
            i+=skipTable[text.charAt(i+pattern.length())];
        } else {
            break;
        }
        
    }
}
    public static void main(String[] args) {
        Sunday sunday=new Sunday();
        sunday.search("alamakotala", "ala");

    }

}
