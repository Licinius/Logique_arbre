import java.util.ArrayList;
import java.util.Collections;

public class Tree {
	
	/*Lists.newArrayList from Guava library*/
	@SafeVarargs
	public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<>(0);
        Collections.addAll(list, elements);
        return list;
    }
	
	private ArrayList< //Le niveau
				ArrayList< //Les branches
					ArrayList<Expression>>> branch; //Les expressions 
	

	
	public Tree(ArrayList<Expression> p_Expressions){
		 branch= newArrayList(
				 	newArrayList(
						 newArrayList()));
		 for (Expression expr : p_Expressions){
			branch.get(0).get(0).add(expr);
		}
	}
	public Tree(){
		 branch= newArrayList(
				 	newArrayList(
						 newArrayList()));
	}
	
	/**
	 * 
	 * @param expression to add in the ArrayList of Expression
	 */
	public void addExpression(Expression expression,int level,int indexBranch){
		if(level>=this.branch.size()){
			branch.add(
				 	newArrayList(
						 newArrayList()));
		}
		if(indexBranch>=this.branch.get(level).size()){
			branch.add( newArrayList());
		}
		this.branch.get(level).get(indexBranch).add(expression);
	}
	
	public void copyExpressionsExept(Expression expression,int levelBegin,int indexBranchBegin,int levelEnd,int indexBranchEnd){
		for(Expression expr : branch.get(levelBegin).get(indexBranchBegin)){
			if(!expr.equals(expression)){
				branch.get(levelEnd).get(indexBranchEnd).add(expr);
			}
		}
	}
	public void developExpression(int index,int level,int indexBranch){
		Expression expr = branch.get(level).get(indexBranch).get(index);
		//TO DO REVERSE COMPLEMENT
		switch(expr.getOperator()){
			case AND:
				addExpression(expr.getRightExpression(),level+1,0);
				addExpression(expr.getLeftExpression(),level+1,0);
				copyExpressionsExept(expr,level,0,level+1,0);
				break;
			case OR:
				addExpression(expr.getRightExpression(),level+1,0);
				addExpression(expr.getLeftExpression(),level+1,1);
				break;
			case EQUIVALENCE:
				
				break;
			case IMPLICATION:
				break;
				
		}

	}
	public String toStringExpressionOfBranch(int level,int indexBranch){
		String res= "";
		int i =0;
		for (Expression expr: branch.get(level).get(indexBranch)){
			res += (i++) + " : " +expr.toString()+"\n";
			
		}
		return res;
	}
	
}
