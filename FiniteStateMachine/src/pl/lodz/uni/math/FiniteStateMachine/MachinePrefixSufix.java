package pl.lodz.uni.math.FiniteStateMachine;

public class MachinePrefixSufix {
	private int actualState;

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
					// + maybe some condition about length
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

	public MachinePrefixSufix(String pattern) {
		this.pattern = pattern;
		actualState = 0;
		makeTransitionArray();
	}
	
	   public static void main(String[] args) {
	       MachinePrefixSufix machine=new MachinePrefixSufix("ala");
	       machine.searchPatternInText("alamakotaalafenifeifalala");
	      // machine.searchPatternInText("alamakotaalafenifeif");
	       }

}
