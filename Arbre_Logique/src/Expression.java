
public abstract class Expression {
	private boolean complement;
	
	public Expression(boolean complement){
		this.setComplement(complement);
	}

	public boolean getComplement() {
		return complement;
	}

	public void setComplement(boolean complement) {
		this.complement = complement;
	}
	
	public abstract boolean isLeaf();
	
	public String getNotationComplement(){
		String res= "";
		if(getComplement()){
			res = "�";
		}
		return res;
	}
	
	public boolean equals(Expression expr){
		return this.complement == expr.complement;
	}

	public void reverseComplement() {
		this.complement = complement ? false : true;
	}

}
