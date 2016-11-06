
public class mainArbre {

	public static void main(String[] args) {
		Tree main_tree = new Tree();
		

		
		Expression E1 = new Expression("p",false);
		Expression E2 = new Expression("p",true);
		
		Expression E3 = new Complexe(true,EnumOperator.AND,E1,E2,"E3");
		main_tree.addExpression(E1,0,0);
		main_tree.addExpression(E3,0,0);
		main_tree.addExpression(E2,0,0);
		System.out.println(E3);
		main_tree.developExpression(1, 0,0);
		System.out.println(main_tree.toStringExpressionOfBranch(0, 0));
		System.out.println(main_tree.toStringExpressionOfBranch(1,0));
	}

}
