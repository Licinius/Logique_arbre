package Arbre_Completion;
import java.util.ArrayList;
import java.util.Iterator;

public class Tree {

	public class Level{

		public class Branch{
			
			private ArrayList<Expression> expressions;
			private Branch leftSon;
			private Branch rightSon;
			private boolean isBlocked;

			
			//Constructors
			public Branch() {
				expressions = new ArrayList<Expression>();
				leftSon = null;
				rightSon = null;
			}
			
			public Branch(ArrayList<Expression> ArExpression){
				this();
				for (Expression expr : ArExpression){
					expressions.add(expr);
				}
			}
			
			public Branch(Expression expr){
				this();
				expressions.add(expr);
			}
			
			//Getters & Setters
			public Branch getLeftSon() {
				return leftSon;
			}

			public void setLeftSon(Branch leftSon) {
				this.leftSon = leftSon;
			}

			public Branch getRightSon() {
				return rightSon;
			}

			public void setRightSon(Branch rightSon) {
				this.rightSon = rightSon;
			}
			
			public boolean isBlocked() {
				return isBlocked;
			}

			public void setBlocked(boolean isBlocked) {
				this.isBlocked = isBlocked;
			}

			public  Expression getExpression(int index){
				return expressions.get(index);
			}
			
			
			//Methods
			public void addExpression(Expression expr){
				expressions.add(expr);
			}
			
			public ArrayList<Expression> getExpressions(){
				return expressions;
			}

			public boolean setBlocked() {
				Expression expr1,expr2;
				for(Iterator<Expression> ite1 = expressions.iterator();ite1.hasNext();){
					expr1 = ite1.next();
					if (expr1.isLiteral()) {
						for(Iterator<Expression> ite2 = expressions.iterator();ite2.hasNext();){
							expr2= ite2.next();
							if (expr2.isLiteral() && ((Literal) expr1).isComplementary((Literal) expr2)) {
								setBlocked(true);
								return true;
							}
						}
					}
				}
				setBlocked(false);
				return false;
			}

		}//fin Branch

		private ArrayList<Branch> branchs;

		//Constructor
		public Level(ArrayList<Branch> ArBranch){
			branchs = new ArrayList<Branch>();	
			for (Branch br : ArBranch){
				branchs.add(br);
			}	
		}
		public Level(Expression expr){
			branchs = new ArrayList<Branch>();	
			Branch tmp = new Branch(expr);
			branchs.add(tmp);
		}
		public Level(){
			branchs = new ArrayList<Branch>();
		}
		
		//Getters & Setters
		public Branch getBranch(int index){
			return branchs.get(index);
		}
		
		//Methods
		public boolean isBranchBlocked(int index){
			return branchs.get(index).isBlocked();
		}
		
		public void addBranch(Branch branch){
			branchs.add(branch);
		}
		
		public int size(){
			return branchs.size();
		}


	}//fin Level

	private ArrayList<Level> tree;
	
	//Constructors
	public Tree(){
		tree = new ArrayList<Level>();
	}
	
	public Tree(Expression expr){
		tree = new ArrayList<Level>();
		Level tmp = new Level(expr);
		tree.add(tmp);
	}
	
	//Methods
	public void addExpression(Expression expression,int level,int indexBranch){
		if(level>=this.tree.size()){
			Level tmp = new Level();
			tree.add(tmp);
		}
		this.tree.get(level).getBranch(indexBranch).addExpression(expression);
	}

	public void copyExpressionsExept(Expression expression,int levelBegin,int indexBranchBegin,int indexBranchEnd){
		for(Expression expr : tree.get(levelBegin).getBranch(indexBranchBegin).getExpressions()){
			if(!expr.equals(expression)){
				tree.get(levelBegin+1).getBranch(indexBranchEnd).addExpression(expr);
			}
		}
	}
	public void developExpression(int index,int level,int indexBranch){
		if (!tree.get(level).getBranch(indexBranch).getExpression(index).isLiteral()) {
			Complexe expr = new Complexe((Complexe) tree.get(level).getBranch(indexBranch).getExpression(index));

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
		for (Expression expr: tree.get(level).getBranch(indexBranch).getExpressions()){
			res += (i++) + " : " +expr.toString()+"\n";

		}
		return res;
	}

}
