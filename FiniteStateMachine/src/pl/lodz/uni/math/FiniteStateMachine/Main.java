package pl.lodz.uni.math.FiniteStateMachine;


public class Main {

	public static void main(String[] args) {
	Machine machine=new Machine("ababaca");
	machine.searchPatternInText("ababacangodrigdrababacandsorgdnrigdrgababacaababaca");
	}

}
