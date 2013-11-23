package pl.lodz.uni.math.rabinKarp;

public class EnginePremium {
    private static int ourModulo = 0;
    public static final int SIGMA = 257;

    public static int getOurModulo() {
        return ourModulo;
    }

    public static void setOurModulo(int ourModulo) {
        EnginePremium.ourModulo = ourModulo;
    }

    public EnginePremium(int ourModulo) {
        this.ourModulo = ourModulo;
    }
    
//computing first hash in a row
    public int computeHash(int patternSize, int[] tempHashes) {
        double hash = 0;

        for (int i = 0; i < patternSize; i++) {
            double a=tempHashes[i];
            double b=Math.pow(SIGMA, (patternSize - 1) - i);
         //   hash += tempHashes[i] * (int)Math.pow(SIGMA, (patternSize - 1) - i);
            hash += (a*b);
        }

        return (int) (hash%ourModulo);
    }

    public void computeTempHashes(int[] tempHashes, char[][] text,
            int patternSize) {
        for (int i = 0; i < text[0].length; i++) {
            double columnHash = 0;
            for (int j = 0; j < patternSize; j++) {
                int a=text[j][i];
                int b= (int) Math.pow(SIGMA, patternSize - (j + 1));
                columnHash += (text[j][i] * Math.pow(SIGMA, patternSize - (j + 1))) % ourModulo;
            }
            tempHashes[i] = (int) columnHash;
        }
    }

    void searchForPattern(char[][] text, char[][] pattern) {
        int falseCounter = 0;
        int patternLength = pattern[0].length;
        int columnsQuantity=text[0].length;
        int[] tempHashes = new int[columnsQuantity];
        computeTempHashes(tempHashes, text, patternLength);
        // computing first window hash
        int tempHash = computeHash(patternLength, tempHashes);
        // computing pattern hash
        int[] patternTempHashes = new int[patternLength];
        computeTempHashes(patternTempHashes, pattern, patternLength);
        int patternHash = computeHash(patternLength, patternTempHashes);

        // further logic part

        for (int i = 0; i <= text[0].length - patternLength; i++) {
            if (i != 0) {
                //recomputing hash
                //bug!
                tempHash=((tempHash-(tempHashes[i-1]*(int)Math.pow(SIGMA, patternLength-1)))*SIGMA+tempHashes[i+(patternLength-1)]) % ourModulo;
            }
            if (tempHash==patternHash) {
                // real equality checking
                boolean identity=true;
                outer:
                for (int j = 0; j < patternLength; j++) {
                    for (int j2 = 0; j2 < patternLength; j2++) {
                        if (pattern[j][j2]!=text[j][j2+i]) {
                            identity=false;
                            break outer;
                        }
                    }
                }
                if (identity) {
                    System.out.println("I found pattern on position: " + i);
                } else {
                    falseCounter++;
                }
            }

        }

        System.out.println("Fake alarms: " + falseCounter);
    }
    
    

    public static void main(String[] args) {
        EnginePremium engine = new EnginePremium(Integer.MAX_VALUE / 2);
        char[][] text={{'1','1','2'}, {'3','3','4'}, {'5','5','6'} };
        char[][] pattern={{'1','2'}, {'3','4'}};

             
        engine.searchForPattern(text, pattern);

    }

}
