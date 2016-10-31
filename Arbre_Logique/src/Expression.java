
public class Expression{
	private String name;
	private boolean complement;
	
	public Expression(String name, boolean complement){
		this.setName(name);
		this.setComplement(complement);

	}

	public boolean isComplement() {
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
		if(isComplement()){
			res = "¬";
		}
		return res;
	}
	public boolean isComplementary(Expression expression) {
		return isLeaf() && getName().equals(expression.getName()) && isComplement() != expression.isComplement();
	}
	public String toString(){
		return  getNotationComplement() + getName();
	}
}
