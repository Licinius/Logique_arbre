import java.util.ArrayList;
import java.util.Iterator;

public class Tree {
	private ArrayList<Level> bst;

	public class Level{

		public class Branch{
			private ArrayList<Expression> expressions;
			private ArrayList<Boolean> blockedExpressions;
			public Branch(ArrayList<Expression> ArExpression){
				expressions = new ArrayList<Expression>();
				blockedExpressions = new ArrayList<Boolean>();
				for (Expression expr : ArExpression){
					expressions.add(expr);
					if(expr.isLiteral())	blockedExpressions.add(true);
					else	blockedExpressions.add(false);
				}
			}
			public Branch(Expression expr){
				expressions = new ArrayList<Expression>();
				blockedExpressions = new ArrayList<Boolean>();
				expressions.add(expr);
				if(expr.isLiteral())	blockedExpressions.add(true);
				else	blockedExpressions.add(false);
			}

			public Branch (){
				expressions = new ArrayList<Expression>();
				blockedExpressions = new ArrayList<Boolean>();
			}
			public  Expression getExpression(int index){
				return expressions.get(index);
			}
			public void addExpression(Expression expr){
				expressions.add(expr);
				if(expr.isLiteral())	blockedExpressions.add(true);
				else	blockedExpressions.add(false);
			}
			public ArrayList<Expression> getExpressions(){
				return expressions;
			}
			public boolean isExpressionBlocked(int index){
				return blockedExpressions.get(index);
			}
			public boolean isBlocked(){
				Expression expr1,expr2;
				for(Iterator<Expression> ite1 = expressions.iterator();ite1.hasNext();){
					expr1 = ite1.next();
					if (expr1.isLiteral()) {
						for(Iterator<Expression> ite2 = expressions.iterator();ite2.hasNext();){
							expr2= ite2.next();
							if (expr2.isLiteral()) {
								return ((Literal) expr1).isComplementary((Literal) expr2);
							}
						}
					}
				}
				return false;
			}

		}//fin Branch

		private ArrayList<Branch> branchs;

		private ArrayList<Boolean> blockedBranchs;

		public Level(ArrayList<Branch> ArBranch){
			branchs = new ArrayList<Branch>();
			blockedBranchs = new ArrayList<Boolean>();	
			for (Branch br : ArBranch){
				branchs.add(br);
				blockedBranchs.add(br.isBlocked());
			}	
		}
		public Level(Expression expr){
			branchs = new ArrayList<Branch>();
			blockedBranchs = new ArrayList<Boolean>();	
			Branch tmp = new Branch(expr);
			branchs.add(tmp);
			blockedBranchs.add(false);
		}
		public Level(){
			branchs = new ArrayList<Branch>();
			blockedBranchs = new ArrayList<Boolean>();
			Branch tmp = new Branch();
			branchs.add(tmp);
			blockedBranchs.add(false);
		}
		public Branch getBranch(int index){
			return branchs.get(index);
		}

		public boolean isBranchBlocked(int index){
			return blockedBranchs.get(index);
		}

		public void addBranch(){
			Branch tmp = new Branch();
			branchs.add(tmp);
			blockedBranchs.add(false);
		}
		public int size(){
			return branchs.size();
		}


	}//fin Level

	public Tree(Expression expr){
		bst = new ArrayList<Level>();
		Level tmp = new Level(expr);
		bst.add(tmp);
	}
	public Tree(){
		bst = new ArrayList<Level>();
		Level tmp = new Level();
		bst.add(tmp);
	}

	/**
	 * 
	 * @param expression to add in the ArrayList of Expression
	 */
	public void addExpression(Expression expression,int level,int indexBranch){
		if(level>=this.bst.size()){
			Level tmp = new Level();
			bst.add(tmp);
		}
		if(indexBranch>=this.bst.get(level).size()){
			bst.get(level).addBranch();
		}
		this.bst.get(level).getBranch(indexBranch).addExpression(expression);
	}

	public void copyExpressionsExept(Expression expression,int levelBegin,int indexBranchBegin,int indexBranchEnd){
		for(Expression expr : bst.get(levelBegin).getBranch(indexBranchBegin).getExpressions()){
			if(!expr.equals(expression)){
				bst.get(levelBegin+1).getBranch(indexBranchEnd).addExpression(expr);
			}
		}
	}
	public void developExpression(int index,int level,int indexBranch){
		if (!bst.get(level).getBranch(indexBranch).getExpression(index).isLiteral()) {
			Complexe expr = (Complexe) bst.get(level).getBranch(indexBranch).getExpression(index);

			if (!expr.getComplement()) {
				expr = expr.ComplementaryOfExpression();
			}

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
				expr.getLeftExpression().reverseComplement();
				expr.setOperator(EnumOperator.OR);
				break;
			}
		}
	}
	public String toStringExpressionOfBranch(int level,int indexBranch){
		String res= "";
		int i =0;
		for (Expression expr: bst.get(level).getBranch(indexBranch).getExpressions()){
			res += (i++) + " : " +expr.toString()+"\n";

		}
		return res;
	}

}
