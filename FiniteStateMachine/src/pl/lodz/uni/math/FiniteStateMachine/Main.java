package pl.lodz.uni.math.FiniteStateMachine;


public class Main {

	public static void main(String[] args) {
	MachinePrefixSufix machine=new MachinePrefixSufix("ababaca");
	machine.searchPatternInText("ababacangodrigdrababacandsorgdnrigdrgababacaababaca");
	}

}
