
public class Expression {


	private boolean complement;
	private EnumOperator operator;
	private Expression rightExpression;
	private Expression leftExpression;
	private String name;
	
	public Expression(String name, boolean complement){
		this.name = name;
		this.complement = complement;
		this.rightExpression = null;
		this.leftExpression = null;
		this.operator = null;
	}
	public Expression(boolean complement, EnumOperator operator, Expression rightExpression, Expression leftExpression,
			String name) {
		this.complement = complement;
		this.operator = operator;
		this.rightExpression = rightExpression;
		this.leftExpression = leftExpression;
		this.name = name;
	}
	public boolean isComplementary(Expression expression) {
		
		return name.equals(expression.name) && complement != expression.complement;
	}
	
	public void addExpression(Expression expression){
		
	}
	

}
