package Arbre_Completion;

public class Complexe extends Expression {

	private EnumOperator operator;
	private Expression rightExpression;
	private Expression leftExpression;
	private String code;
	private static int staticCpt = 0;


	public Complexe(boolean complement, EnumOperator operator, Expression rightExpression, Expression leftExpression) {
		super(complement, "E"+staticCpt);
		code = "E"+staticCpt++;
		this.operator = operator;
		this.rightExpression = rightExpression;
		this.leftExpression = leftExpression;

	}

	public Complexe(Complexe complexe) {
		super(complexe.getComplement());
		numOp = complexe.getNumOp();
		this.operator = complexe.getOperator();

		if (complexe.getLeftExpression() instanceof Literal) {
			this.leftExpression = new Literal((Literal) complexe.getLeftExpression()); 
		} else {
			this.leftExpression = new Complexe((Complexe) complexe.getLeftExpression());
		}

		if (complexe.getRightExpression() instanceof Literal) {
			this.leftExpression = new Literal((Literal) complexe.getRightExpression()); 
		} else {
			this.leftExpression = new Complexe((Complexe) complexe.getRightExpression());
		}
	}

	public boolean isLiteral() {
		return false;
	}

	public int getNumOp() {
		return numOp;
	}

	public EnumOperator getOperator(){
		return this.operator;
	}

	public void setOperator(EnumOperator operator) {
		this.operator = operator;
	}

	public Complexe ComplementaryOfExpression(){
		Complexe res = new Complexe(this);
		if (!getComplement()){
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
	
	public boolean equals(Complexe complexe) {
		return super.equals(complexe) && this.numOp == complexe.getNumOp();
	}

}



