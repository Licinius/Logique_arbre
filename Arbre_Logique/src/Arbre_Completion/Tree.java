package Arbre_Completion;

import java.util.ArrayList;
import java.util.Iterator;

public class Tree {
	ArrayList<Expression> expressions = new ArrayList<Expression>();
	private Tree leftSon;
	private Tree rightSon;
	int identifiant;
	private static int cpt = 0;
	private boolean blocked;
	
	public Tree(Expression expr){
		leftSon = null;
		rightSon = null;
		blocked=false;
		expressions.add(expr);
		identifiant = cpt++;
	}

	public Tree(){
		leftSon = null;
		rightSon = null;
		blocked=false;
		identifiant = cpt++;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public ArrayList<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(ArrayList<Expression> expressions) {
		this.expressions = expressions;
	}

	public Tree getLeftSon() {
		return leftSon;
	}

	public void setLeftSon(Tree leftSon) {
		this.leftSon = leftSon;
	}

	public Tree getRightSon() {
		return rightSon;
	}

	public void setRightSon(Tree rightSon) {
		this.rightSon = rightSon;
	}
	public boolean getBlocked(){
		return blocked;
	}

	public int getNbLevel() {
		if (leftSon == null && rightSon != null)
			return 1 + rightSon.getNbLevel();
		else if (rightSon == null && leftSon != null)
			return 1 + leftSon.getNbLevel();
		else if (rightSon == null && leftSon == null)
			return 0;
		return 1 + Math.max(leftSon.getNbLevel(), rightSon.getNbLevel());
	}

	public void addExpression(Expression expression) {
		expressions.add(expression);
	}

	public void copyExpressionsExept(Expression expression, boolean left) {
		if (left) {
			if (leftSon == null)
				leftSon = new Tree();
			for (Expression expr : expressions) {
				if (!expr.equals(expression))
					this.leftSon.addExpression(expr);
			}
		} else {
			if (rightSon == null)
				rightSon = new Tree();
			for (Expression expr : expressions) {

				if (!expr.equals(expression)){
					this.rightSon.addExpression(expr);}
			}
		}
	}

	public void developExpression(int id, int indexExpression) {
		Tree tr = rechercheTree(id);
		if (!tr.expressions.get(indexExpression).isLiteral()) {
			Complexe expr = new Complexe((Complexe) tr.expressions.get(indexExpression));
			if (expr.getComplement()) {
				expr = expr.ComplementaryOfExpression();
			}
			tr.blocked = true;
			switch (expr.getOperator()) {
			case AND:
				tr.leftSon = new Tree();
				tr.leftSon.addExpression(expr.getRightExpression());
				tr.leftSon.addExpression(expr.getLeftExpression());
				tr.copyExpressionsExept(tr.expressions.get(indexExpression), true);
				break;
			case OR:
				tr.leftSon = new Tree();
				tr.rightSon = new Tree();
				tr.leftSon.addExpression(expr.getLeftExpression());
				tr.rightSon.addExpression(expr.getRightExpression());
				copyExpressionsExept(tr.expressions.get(indexExpression), true);
				copyExpressionsExept(tr.expressions.get(indexExpression), false);
				break;
			case IMPLICATION:
				tr.leftSon = new Tree();
				tr.rightSon = new Tree();
				expr.getLeftExpression().reverseComplement();
				tr.leftSon.addExpression(expr.getLeftExpression());
				tr.rightSon.addExpression(expr.getRightExpression());
				tr.copyExpressionsExept(tr.expressions.get(indexExpression), true);
				tr.copyExpressionsExept(tr.expressions.get(indexExpression), false);
				break;
			}
		}

	}


	private Tree rechercheTree(int id) {
		if (id == identifiant) {
			return this;
		}
		if (leftSon != null && rightSon == null)
			return leftSon.rechercheTree(id);
		if (leftSon == null && rightSon != null)
			return rightSon.rechercheTree(id);
		if (leftSon != null && rightSon != null)
			return isNotNull(rightSon.rechercheTree(id), leftSon.rechercheTree(id));
		return null;
	}
	public boolean setBlocked(int indexBranch,int indexExpression1, int indexExpression2){
		Tree tr = rechercheTree(indexBranch);
		if (tr.expressions.get(indexExpression1) instanceof Literal && tr.expressions.get(indexExpression2) instanceof Literal){
			setBlocked(((Literal)tr.expressions.get(indexExpression1)).isComplementary((Literal)tr.expressions.get(indexExpression2)));
		}
		return blocked;
	}

	private Tree isNotNull(Tree rechercheTree, Tree rechercheTree2) {
		if (rechercheTree != null && rechercheTree2 == null)
			return rechercheTree;
		if (rechercheTree == null && rechercheTree2 != null)
			return rechercheTree2;
		return null;
	}

	public String toStringExpressionOfBranch(int id) {
		Tree tr = rechercheTree(id);
		String res = "";
		int i = 0;
		for (Expression expr : tr.expressions) {
			res += (i++) + " : " + expr.toString() + "\t";

		}
		return res;
	}

	public String[] toStringExpression(int id) {
		Tree tr = rechercheTree(id);
		String[] res = null;
		int i = 0;
		if (tr == null) return null;
		res = new String[tr.expressions.size()];
		for (Expression expr : tr.expressions) {
			res[i++] = expr.toString();
		}

		return res;
	}
	public String[] toStringExpression() {
		String[] res = null;
		int i = 0;
		res = new String[this.expressions.size()];
		for (Expression expr : this.expressions) {
			res[i++] = expr.toString();
		}
		return res;
	}
	
	public Expression[] getExpressionsInArray() {
		Expression[] res = null;
		int i = 0;
		res = new Expression[this.expressions.size()];
		for (Expression expr : this.expressions) {
			res[i++] = expr;
		}
		return res;
	}
	public String toString(){
		return this.toString(0);
	}
	public String toString(int niveau) {
		String res = "";
		for (Expression expr : this.expressions) {
			res+="niveau  "+niveau + ": "+ expr.toString();
		}
		res+="\n";
		niveau++;
		if (leftSon != null && rightSon == null)
			res+=leftSon.toString(niveau);
		if (leftSon == null && rightSon != null)
			res+=rightSon.toString(niveau);
		if (leftSon != null && rightSon != null)
			 res+=leftSon.toString(niveau)+ " right : " + rightSon.toString(niveau);
		return res;
	}

	public String getRootExpression() {
		return this.expressions.get(0).toString();
	}
	
	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
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
	
	public boolean equals(Tree t){
		return t.identifiant == this.identifiant;
	}
}
