
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

	public ExpressionComplexe(ExpressionComplexe expressionComplexe) {
		super(expressionComplexe.getName(),expressionComplexe.getComplement());
		this.operator = expressionComplexe.operator;
		this.rightExpression = expressionComplexe.rightExpression;
		this.leftExpression = expressionComplexe.leftExpression;
	}

	public boolean isLeaf() {
		return false;
	}
	
	public EnumOperator getOperator(){
		EnumOperator res = operator;
		if (getComplement()){
			switch(operator){
				case AND:
					res = EnumOperator.OR;
					break;
				case OR : 
					res = EnumOperator.AND;
				case IMPLICATION:
					res = EnumOperator.AND;
				
			}
		}
		return res;
	}
	
	public Expression ComplementaryOfExpression(){
		ExpressionComplexe res = new ExpressionComplexe(this);
		if (getComplement()){
			switch(operator){
			case AND:
				res.operator = EnumOperator.OR;
				res.leftExpression.reverseComplement();
				res.rightExpression.reverseComplement();
				break;
			case OR : 
				res.operator = EnumOperator.AND;
				res.leftExpression.reverseComplement();
				res.rightExpression.reverseComplement();
				break;
			case IMPLICATION:
				res.operator = EnumOperator.AND;
				res.rightExpression.reverseComplement();
				break;
			
		}
		}
		return res;
		
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


	

