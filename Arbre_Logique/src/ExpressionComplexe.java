
public class ExpressionComplexe extends Expression {



	private EnumOperator operator;
	private Expression rightExpression;
	private Expression leftExpression;
	
	

	public ExpressionComplexe(boolean complement, EnumOperator operator, Expression rightExpression, Expression leftExpression,
			String name) {
		super(name,complement);
		this.operator = operator;
		this.rightExpression = rightExpression;
		this.leftExpression = leftExpression;

	}

	public boolean isLeaf() {
		return false;
	}
	
	public EnumOperator getOperator(){

		return operator;
	}
	
	public Expression getLeftExpression(){
		return leftExpression;
	}
	
	public Expression getRightExpression(){
		return rightExpression;
	}
	
	public String toString(){
		if(this.isLeaf()){
			System.out.println("Est feuille");
			return super.toString();
		}
		if( !leftExpression.isLeaf() && !rightExpression.isLeaf()){
			return getNotationComplement()+"((" +leftExpression.getNotationComplement()+"( " +leftExpression.toString()+ " ) " + operator +" "+rightExpression.getNotationComplement() + "( "+rightExpression.toString()+" ))"; 
		}
		if (leftExpression.isLeaf() && !rightExpression.isLeaf()){
			return getNotationComplement()+"(( " +leftExpression.toString()+ " ) " + operator +" "+rightExpression.getNotationComplement() + "( "+rightExpression.toString()+" ))"; 
		}
		if (!leftExpression.isLeaf() && rightExpression.isLeaf()){
			return getNotationComplement()+"((" +leftExpression.getNotationComplement()+"( " +leftExpression.toString()+ " ) " + operator + " ( "+rightExpression.toString()+" ))"; 
		}
		//Right and left expression are leafs
		return getNotationComplement()+"(( " +leftExpression.toString()+ " ) " + operator +" ( "+rightExpression.toString()+" ))"; 

		
	}
	
	

}


	

