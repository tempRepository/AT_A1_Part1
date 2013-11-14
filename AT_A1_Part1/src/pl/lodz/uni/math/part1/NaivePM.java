package pl.lodz.uni.math.part1;

public class NaivePM {
    String text;

    public NaivePM(String text) {
        this.text = text;
    }

   public static boolean myEquals(String text, String pattern) {
        boolean equals = true;
        for (int i = 0; i < text.length() && equals; i++) {
            if ((text.charAt(i) != pattern.charAt(i)) && pattern.charAt(i)!="?".toCharArray()[0]) {
                equals = false;
            }
        }

        return equals;
    }

    public void search(String pattern) {
        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            
                if (myEquals(text.substring(i, i + pattern.length()), pattern)) {
                    System.out.println("I found occurence at position: " + i);
                }
            

        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //dopisać zgodność dla ? jako spacji!
        NaivePM naive = new NaivePM("alamakotaalala");
        naive.search("a?a");
    }

}
