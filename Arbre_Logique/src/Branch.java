import java.util.ArrayList;
import java.util.Iterator;

public class Branch {
	private ArrayList<Expression> expressions; 
	
	public Branch(ArrayList<Expression> p_Expressions){
		expressions = new ArrayList<Expression>();
		for (Expression expr : p_Expressions){
			expressions.add(expr);
		}
	}
	
	public Branch (){
		expressions = new ArrayList<Expression>();
	}
	
	
	/**
	 * Check if the arrayList contain a complementary
	 * @return If the branch is false 
	 */
	public boolean isFalse(){
		boolean res = false;
		for (int i = 0; i < expressions.size()-1; i++) {
			for (int j = i+1; j < expressions.size(); j++) {
			   if(expressions.get(i).isComplementary(expressions.get(j))){
				   res = true;
			   }
			}
		}
		return res;
	}
	/**
	 * 
	 * @param expression to add in the ArrayList of Expression
	 */
	public void addExpression(Expression expression){
		expressions.add(expression);
	}
}
