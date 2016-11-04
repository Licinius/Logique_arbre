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
					ArrayList<Expression>>> bst; //Les expressions 
	

	
	public Tree(Expression expr){
		 bst= newArrayList(
				 	newArrayList(
						 newArrayList()));
		bst.get(0).get(0).add(expr);
		
	}
	public Tree(){
		 bst= newArrayList(
				 	newArrayList(
						 newArrayList()));
	}
	
	/**
	 * 
	 * @param expression to add in the ArrayList of Expression
	 */
	public void addExpression(Expression expression,int level,int indexBranch){
		if(level>=this.bst.size()){
			bst.add(
				 	newArrayList(
						 newArrayList()));
		}
		if(indexBranch>=this.bst.get(level).size()){
			bst.add( newArrayList());
		}
		this.bst.get(level).get(indexBranch).add(expression);
	}
	
	public void copyExpressionsExept(Expression expression,int levelBegin,int indexBranchBegin,int indexBranchEnd){
		for(Expression expr : bst.get(levelBegin).get(indexBranchBegin)){
			if(!expr.equals(expression)){
				bst.get(levelBegin+1).get(indexBranchEnd).add(expr);
			}
		}
	}
	public void developExpression(int index,int level,int indexBranch){
		Expression expr = bst.get(level).get(indexBranch).get(index);
		//TO DO REVERSE COMPLEMENT and check only complexe
		switch(expr.getOperator()){
			case AND:
				addExpression(expr.getRightExpression(),level+1,0);
				addExpression(expr.getLeftExpression(),level+1,0);
				copyExpressionsExept(expr,level,0,0);
				break;
			case OR:
				addExpression(expr.getRightExpression(),level+1,0);
				addExpression(expr.getLeftExpression(),level+1,1);
				copyExpressionsExept(expr,level,0,0);
				copyExpressionsExept(expr,level,0,1);
				break;
			case IMPLICATION:
				break;
				
		}

	}
	public String toStringExpressionOfBranch(int level,int indexBranch){
		String res= "";
		int i =0;
		for (Expression expr: bst.get(level).get(indexBranch)){
			res += (i++) + " : " +expr.toString()+"\n";
			
		}
		return res;
	}
	
}
