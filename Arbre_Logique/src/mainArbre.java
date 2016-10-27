
public class mainArbre {

	public static void main(String[] args) {
		Tree main_tree = new Tree();
		
		Branch main_Branch = new Branch();
		
		Expression E1 = new Expression("p",false);
		Expression E2 = new Expression("p",true);
		
		Expression E3 = new Expression(true,EnumOperator.AND,E1,E2,"E3");
		main_Branch.addExpression(E1);
		main_Branch.addExpression(E3);
		main_Branch.addExpression(E2);
		System.out.println(main_Branch.isFalse());
	}

}
