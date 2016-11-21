package Arbre_Completion;

public class mainArbre {

	public static void main(String[] args) {
		Tree main_tree = new Tree();
		

		
		Expression E1 = new Literal(false,"p");
		Expression E2 = new Literal(false,"q");
		
		Expression E4 = new Literal(true,"q");
		
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2);
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1);
		
		Expression Final = new Complexe(false,EnumOperator.IMPLICATION,E1,Complexe2);
		
		System.out.println(Complexe2.getNom());
		
		System.out.println(Final);
		
	}

}
