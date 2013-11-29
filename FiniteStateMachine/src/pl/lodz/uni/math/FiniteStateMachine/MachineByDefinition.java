package pl.lodz.uni.math.FiniteStateMachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MachineByDefinition {
	private int actualState;
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
	public void setActualState(int actualState) {
		this.actualState = actualState;
	}

	private String pattern;
	private int[][] transitionArray;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getActualState() {
		return actualState;
	}

	public int[][] getTransitionArray() {
		return transitionArray;
	}

	public void makeTransitionArray() {
		// filling blank array
		transitionArray = new int[pattern.length() + 1][256];
		for (int i = 0; i < transitionArray.length; i++) {
			for (int j = 0; j < transitionArray[i].length; j++) {
				transitionArray[i][j] = 0;
			}
		}

		// filling specific fields
		for (int i = 0; i < pattern.length(); i++) {
			transitionArray[i][pattern.charAt(i)] = i + 1;
		}

		// all suffix, prefix stuff
		String tempText = "";
		for (int i = 1; i < transitionArray.length; i++) {
			for (int j = 0; j < transitionArray[i].length; j++) {
				tempText = pattern.substring(0, i) + Character.toChars(j)[0];
				for (int k = pattern.length(); k > 0; k--) {
					
					if (tempText.endsWith(pattern.substring(0, k))) {
						transitionArray[i][j] = k;
						break;
					}
				}

			}
		}
	};

	private int transitionFunction(int state, char letter) {
		return transitionArray[state][letter];
	}

	public void searchPatternInText(String text) {
		actualState = 0;
		for (int i = 0; i < text.toCharArray().length; i++) {
			actualState = transitionFunction(actualState, text.charAt(i));
			if (actualState == pattern.length()) {
				System.out.println("Znalazlem wzorzec na pozycji"
						+ (i - (pattern.length() - 1)));
			}
		}

	}

	public MachineByDefinition(String pattern) {
		this.pattern = pattern;
		actualState = 0;
		makeTransitionArray();
	}
	
	   public static void main(String[] args) {
	       
	       //pattern1 ajfvl
	        //pattern2 jfkdlopruj
	        String book = loadBook("book6000");
	        String pattern="12345678901234567890123456789012345678901234567890123456789012345678901234567890";
	        MachineByDefinition machine=new MachineByDefinition(pattern);
	        long avgTime=0;
	        for (int i = 0; i < 500; i++) {
	            TimeCounter.start();
	            machine.makeTransitionArray();
	            avgTime+=TimeCounter.stopTime();
	        }
	        System.out.println("Avg time "+(avgTime/500));
	       
	       }

}
