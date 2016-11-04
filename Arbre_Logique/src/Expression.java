
public class Expression{
	private String name;
	private boolean complement;
	
	public Expression(String name, boolean complement){
		this.setName(name);
		this.setComplement(complement);

	}

	public boolean getComplement() {
		return complement;
	}

	public void setComplement(boolean complement) {
		this.complement = complement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public boolean isLeaf(){
		return true;
	}
	public String getNotationComplement(){
		String res= "";
		if(getComplement()){
			res = "¬";
		}
		return res;
	}
	public boolean isComplementary(Expression expression) {
		return isLeaf() && getName().equals(expression.getName()) && getComplement() != expression.getComplement();
	}
	public String toString(){
		return  getNotationComplement() + getName();
	}

	public EnumOperator getOperator() {
		return null;
	}
	public Expression getRightExpression(){
		return null;
	}
	public Expression getLeftExpression(){
		return null;
	}
	
	public boolean equals(Expression expr){
		return name.equals(expr.getName()) && complement==expr.getComplement();
	}

	public void reverseComplement() {
		if(complement){
			complement=false;
		}else{
			complement=true;
		}
		
		
	}

}
