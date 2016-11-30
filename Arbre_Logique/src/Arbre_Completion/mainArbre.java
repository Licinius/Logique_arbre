package Arbre_Completion;

public class mainArbre {

	public static void main(String[] args) {

		Expression E1 = new Literal(false,"p"); // p
		Expression E2 = new Literal(false,"q"); // q
		Expression E3 = new Literal(true,"p"); // ¬p
		Expression E4 = new Literal(true,"q"); // ¬q
		
		//(p → (¬q → ¬(p → q)))
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2); // ¬(p → q)
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1); // (¬q → ¬(p → q))
		Expression Final1 = new Complexe(false,EnumOperator.IMPLICATION,E1,Complexe2);

		//(p → ((p → q) → q)) 
		Expression Complexe3 = new Complexe (false, EnumOperator.IMPLICATION, E1, E2); // (p → q)
		Expression Complexe4 = new Complexe (false, EnumOperator.IMPLICATION, Complexe3, E2); // ((p → q) → q)
		Expression Final2 = new Complexe(false, EnumOperator.IMPLICATION, E1, Complexe4);

		//((¬p → ¬q) → (q → p)) 
		Expression Complexe5 = new Complexe (false, EnumOperator.IMPLICATION, E3, E4); // (¬p → ¬q)
		Expression Complexe6 = new Complexe (false, EnumOperator.IMPLICATION, E2, E1); // (q → p)
		Expression Final3 = new Complexe(false, EnumOperator.IMPLICATION, Complexe5, Complexe6);

		Tree main_tree = new Tree(Complexe3);
		main_tree.developExpression(0, 0);
		
		System.out.println(main_tree.toString());
		
	
	}

}
